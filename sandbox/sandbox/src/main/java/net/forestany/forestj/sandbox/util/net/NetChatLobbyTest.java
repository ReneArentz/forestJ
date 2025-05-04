package net.forestany.forestj.sandbox.util.net;

public class NetChatLobbyTest {
	private static NetChatLobbyTest.NetChatLobbyConfig o_netConfig;
	
	private static Thread o_threadLobby = null;
	private static Thread o_threadChat = null;
		
	private static net.forestany.forestj.lib.net.sock.com.Communication o_communicationLobby = null;
	private static net.forestany.forestj.lib.net.sock.com.Communication o_communicationChat = null;
	
	private static java.util.Map<java.time.LocalDateTime, String> m_clientLobbyEntries = new java.util.HashMap<java.time.LocalDateTime, String>();
	private static java.time.LocalDateTime o_lastPing = null;
	private static boolean b_connected = false;
	private static net.forestany.forestj.lib.net.msg.MessageBox o_messageBox = new net.forestany.forestj.lib.net.msg.MessageBox(1, 1500);
	private static java.util.Map<java.time.LocalDateTime, Tuple<String, String>> m_chatHistory = new java.util.HashMap<java.time.LocalDateTime, Tuple<String, String>>();
	
	public static void testNetChatLobbyMenu(String p_s_currentDirectory) throws Exception {
		o_netConfig = NetChatLobbyTest.readNetConfig(p_s_currentDirectory);
		
		o_netConfig.hostIp = null;
		o_netConfig.chatUser = net.forestany.forestj.lib.Console.consoleInputString("Own name: ", false);
		o_netConfig.isServer = net.forestany.forestj.lib.Console.consoleInputBoolean("Host chat room[true|false]: ");
		
		if (o_netConfig.isServer) {
			o_netConfig.chatRoom = net.forestany.forestj.lib.Console.consoleInputString("Room name: ", false);
		}
		
		System.out.println("");
		
		System.out.println("++++++++++++++++++++++++++++++++");
		System.out.println(" net Chat Lobby " + ((o_netConfig.isServer) ? "Server" : "Client"));
		System.out.println("++++++++++++++++++++++++++++++++");
			
		if (o_netConfig.isServer) {
			doServer(o_netConfig);
		} else {
			doClient(o_netConfig);
		}
	}
	
	private static void doServer(NetChatLobbyConfig p_o_config) throws Exception {
		java.util.List<String> a_states = java.util.Arrays.asList("LOBBY", "CHAT", "SHUTDOWN");
		java.util.List<String> a_returnCodes = java.util.Arrays.asList("START_CHAT", "CLOSE_CHAT", "CHAT_IDLE", "CLOSE_SERVER", "IDLE", "SHUTDOWN");
		net.forestany.forestj.lib.StateMachine o_stateMachine = new net.forestany.forestj.lib.StateMachine(a_states, a_returnCodes);
		
		o_stateMachine.addTransition("LOBBY", "IDLE", "LOBBY");
		o_stateMachine.addTransition("LOBBY", "START_CHAT", "CHAT");
		o_stateMachine.addTransition("CHAT", "CLOSE_CHAT", "LOBBY");
		o_stateMachine.addTransition("CHAT", "CHAT_IDLE", "CHAT");
		o_stateMachine.addTransition("LOBBY", "CLOSE_SERVER", "SHUTDOWN");
		o_stateMachine.addTransition("SHUTDOWN", net.forestany.forestj.lib.StateMachine.EXIT, net.forestany.forestj.lib.StateMachine.EXIT);
		
		/* define state machine methods */
		o_stateMachine.addStateMethod(o_stateMachine.new StateMethodContainer(
			"LOBBY",
			(java.util.List<Object> p_a_genericList) -> {
				if (o_communicationLobby == null) {
					netLobby(p_o_config);
				}
				
				if (o_communicationChat == null) {
					netChat(p_o_config);
				}
				
				if (b_connected) {
					m_chatHistory.clear();
					clearMessageBox();
					return "START_CHAT";
				}
				
				char c_foo = net.forestany.forestj.lib.Console.consoleInputCharacter("stop server by enter[y|n]: ");
					
				if (c_foo == 'y') {
					return "CLOSE_SERVER";
				} else {
					return "IDLE";
				}
			}
		));
		
		o_stateMachine.addStateMethod(o_stateMachine.new StateMethodContainer(
			"CHAT",
			(java.util.List<Object> p_a_genericList) -> {
				clearScreen();
				
				if (o_communicationChat == null) {
					netChat(p_o_config);
				}
				
				if ((!b_connected) || ((o_lastPing != null) && (java.time.Duration.between(o_lastPing, java.time.LocalDateTime.now()).getSeconds() > (5 * 60)))) {
					System.out.println("Other side closed chat or last ping over 5 minutes ago.");
					System.out.println("Leaving chat ...");
					b_connected = false;
					m_chatHistory.clear();
					clearMessageBox();
					return "CLOSE_CHAT";
				}
				
				renderChat(p_o_config);
				
				String s_commandChat = net.forestany.forestj.lib.Console.consoleInputString("Message|R for refresh|EXIT for closing chat: ");
				
				if (s_commandChat.contentEquals("EXIT")) {
					b_connected = false;
					m_chatHistory.clear();
					o_messageBox.enqueueObject(net.forestany.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.now()) + "|" + p_o_config.chatUser + "|%EXIT%");
					Thread.sleep(1500);
					
					if (o_communicationChat != null) {
						try {
							o_communicationChat.stop();
							Thread.sleep(2500);
						} catch (Exception o_exc) {
							
						} finally {
							o_communicationChat = null;
							b_connected = false;
						}
					}
					
					return "CLOSE_CHAT";
				}
				
				if (!s_commandChat.contentEquals("R")) {
					m_chatHistory.put(java.time.LocalDateTime.now(), new Tuple<String, String>(p_o_config.chatUser, s_commandChat));
					o_messageBox.enqueueObject(net.forestany.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.now()) + "|" + p_o_config.chatUser + "|" + s_commandChat);
				}
				
				return "CHAT_IDLE";
			}
		));
		
		o_stateMachine.addStateMethod(o_stateMachine.new StateMethodContainer(
			"SHUTDOWN",
			(java.util.List<Object> p_a_genericList) -> {
				m_chatHistory.clear();
				clearMessageBox();
				b_connected = false;
				o_lastPing = null;
				
				if (o_threadLobby != null) {
					try { o_threadLobby.interrupt(); } catch (Exception o_exc) { }
					o_threadLobby = null;
				}
				
				if (o_communicationLobby != null) {
					try {
						o_communicationLobby.stop();
						Thread.sleep(2500);
					} catch (Exception o_exc) {
						
					} finally {
						o_communicationLobby = null;
					}
				}
				
				if (o_threadChat != null) {
					try { o_threadChat.interrupt(); } catch (Exception o_exc) { }
					o_threadChat = null;
				}
								
				if (o_communicationChat != null) {
					try {
						o_communicationChat.stop();
						Thread.sleep(2500);
					} catch (Exception o_exc) {
						
					} finally {
						o_communicationChat = null;
					}
				}
				
				return net.forestany.forestj.lib.StateMachine.EXIT;
			}
		));
		
		/* execute state machine until EXIT state */
		String s_returnCode = net.forestany.forestj.lib.StateMachine.EXIT;
		String s_currentState = "LOBBY";
		java.util.List<Object> a_genericList = new java.util.ArrayList<Object>();
		
		do {
			s_returnCode = o_stateMachine.executeStateMethod(s_currentState, a_genericList);
    		s_currentState = o_stateMachine.lookupTransitions(s_currentState, s_returnCode);
    	} while (!s_returnCode.contentEquals(net.forestany.forestj.lib.StateMachine.EXIT));
	}
	
	private static void doClient(NetChatLobbyConfig p_o_config) throws Exception {
		java.util.List<String> a_states = java.util.Arrays.asList("LOBBY", "CHAT", "SHUTDOWN");
		java.util.List<String> a_returnCodes = java.util.Arrays.asList("START_CHAT", "CLOSE_CHAT", "CHAT_IDLE", "CLOSE_CLIENT", "IDLE", "SHUTDOWN");
		net.forestany.forestj.lib.StateMachine o_stateMachine = new net.forestany.forestj.lib.StateMachine(a_states, a_returnCodes);
		
		o_stateMachine.addTransition("LOBBY", "IDLE", "LOBBY");
		o_stateMachine.addTransition("LOBBY", "START_CHAT", "CHAT");
		o_stateMachine.addTransition("CHAT", "CLOSE_CHAT", "LOBBY");
		o_stateMachine.addTransition("CHAT", "CHAT_IDLE", "CHAT");
		o_stateMachine.addTransition("LOBBY", "CLOSE_CLIENT", "SHUTDOWN");
		o_stateMachine.addTransition("SHUTDOWN", net.forestany.forestj.lib.StateMachine.EXIT, net.forestany.forestj.lib.StateMachine.EXIT);
		
		/* define state machine methods */
		o_stateMachine.addStateMethod(o_stateMachine.new StateMethodContainer(
			"LOBBY",
			(java.util.List<Object> p_a_genericList) -> {
				clearScreen();
				
				if (o_threadChat != null) {
					try { o_threadChat.interrupt(); } catch (Exception o_exc) { }
					o_threadChat = null;
				}
				
				if (o_communicationChat != null) {
					try {
						o_communicationChat.stop();
						Thread.sleep(2500);
					} catch (Exception o_exc) {
						
					} finally {
						o_communicationChat = null;
					}
				}
				
				o_lastPing = null;
				
				if (o_communicationLobby == null) {
					netLobby(p_o_config);
				}
				
				if (m_clientLobbyEntries.size() > 0) {
					int i = 1;
					
					for (java.util.Map.Entry<java.time.LocalDateTime, String> o_entry : m_clientLobbyEntries.entrySet()) {
						System.out.println("#" + i++ + " - " + o_entry.getValue());
					}
				}
				
				char c_option = net.forestany.forestj.lib.Console.consoleInputCharacter("Choose chat room with '1, 2, 3, ...' or press 'R' for refresh or '0' for stop client: ");
					
				if (c_option == '0') {
					return "CLOSE_CLIENT";
				} else if (c_option == 'R') {
					return "IDLE";
				} else {
					int i_foo = -1;
					
					try {
						i_foo = Integer.parseInt(""+c_option);
					} catch (Exception o_exc) {
						return "IDLE";
					}
					
					if ((i_foo >= 0) && (i_foo <= m_clientLobbyEntries.size())) {
						if (m_clientLobbyEntries.size() > 0) {
							int i = 1;

							for (java.util.Map.Entry<java.time.LocalDateTime, String> o_entry : m_clientLobbyEntries.entrySet()) {
								if (i == i_foo) {
									p_o_config.hostIp = (o_entry.getValue().split("\\|")[1]).split(":")[0];
									break;
								}
							}
						}
						
						clearMessageBox();
						return "START_CHAT";
					} else {
						return "IDLE";
					}
				}
			}
		));
		
		o_stateMachine.addStateMethod(o_stateMachine.new StateMethodContainer(
			"CHAT",
			(java.util.List<Object> p_a_genericList) -> {
				clearScreen();
				
				if (o_threadLobby != null) {
					try { o_threadLobby.interrupt(); } catch (Exception o_exc) { }
					o_threadLobby = null;
				}
				
				if (o_communicationLobby != null) {
					try {
						o_communicationLobby.stop();
						Thread.sleep(2500);
					} catch (Exception o_exc) {
						
					} finally {
						o_communicationLobby = null;
					}
				}
				
				if (o_communicationChat == null) {
					netChat(p_o_config);
					b_connected = true;
				}
				
				if ((!b_connected) || ((o_lastPing != null) && (java.time.Duration.between(o_lastPing, java.time.LocalDateTime.now()).getSeconds() > (5 * 60)))) {
					System.out.println("Other side closed chat or last ping over 5 minutes ago.");
					System.out.println("Leaving chat ...");
					b_connected = false;
					m_chatHistory.clear();
					clearMessageBox();
					return "CLOSE_CHAT";
				}
				
				renderChat(p_o_config);
				
				String s_commandChat = net.forestany.forestj.lib.Console.consoleInputString("Message|R for refresh|EXIT for closing chat: ");
				
				if (s_commandChat.contentEquals("EXIT")) {
					b_connected = false;
					m_chatHistory.clear();
					o_messageBox.enqueueObject(net.forestany.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.now()) + "|" + p_o_config.chatUser + "|%EXIT%");
					Thread.sleep(1000);
					return "CLOSE_CHAT";
				}
				
				if (!s_commandChat.contentEquals("R")) {
					m_chatHistory.put(java.time.LocalDateTime.now(), new Tuple<String, String>(p_o_config.chatUser, s_commandChat));
					o_messageBox.enqueueObject(net.forestany.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.now()) + "|" + p_o_config.chatUser + "|" + s_commandChat);
				}
				
				return "CHAT_IDLE";
			}
		));
		
		o_stateMachine.addStateMethod(o_stateMachine.new StateMethodContainer(
			"SHUTDOWN",
			(java.util.List<Object> p_a_genericList) -> {
				m_chatHistory.clear();
				clearMessageBox();
				b_connected = false;
				o_lastPing = null;
				
				if (o_threadLobby != null) {
					try { o_threadLobby.interrupt(); } catch (Exception o_exc) { }
					o_threadLobby = null;
				}
				
				if (o_communicationLobby != null) {
					try {
						o_communicationLobby.stop();
						Thread.sleep(2500);
					} catch (Exception o_exc) {
						
					} finally {
						o_communicationLobby = null;
					}
				}
				
				if (o_threadChat != null) {
					try { o_threadChat.interrupt(); } catch (Exception o_exc) { }
					o_threadChat = null;
				}
				
				if (o_communicationChat != null) {
					try {
						o_communicationChat.stop();
						Thread.sleep(2500);
					} catch (Exception o_exc) {
						
					} finally {
						o_communicationChat = null;
					}
				}
				
				return net.forestany.forestj.lib.StateMachine.EXIT;
			}
		));
		
		/* execute state machine until EXIT state */
		String s_returnCode = net.forestany.forestj.lib.StateMachine.EXIT;
		String s_currentState = "LOBBY";
		java.util.List<Object> a_genericList = new java.util.ArrayList<Object>();
		
		do {
			s_returnCode = o_stateMachine.executeStateMethod(s_currentState, a_genericList);
    		s_currentState = o_stateMachine.lookupTransitions(s_currentState, s_returnCode);
    	} while (!s_returnCode.contentEquals(net.forestany.forestj.lib.StateMachine.EXIT));
	}
	
	private static void clearScreen() throws java.io.IOException, InterruptedException {
        // Check the operating system
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            // Assuming Unix/Linux/macOS
            System.out.print("\033[H\033[2J"); // ANSI escape code for clearing the screen
            System.out.flush(); 
        }
    }
	
	private class NetChatLobbyConfig {
		public String currentDirectory;
		public String chatUser;
		public String chatRoom;
		public String hostIp;
		public boolean isServer;
		public String localIp;
		public int serverPort;
		public int udpMulticastTTL;
		public String udpMulticastNetworkInterfaceName;
		public String udpMulticastIp;
		public int udpMulticastPort;
	}
	
	private static NetChatLobbyConfig readNetConfig(String p_s_currentDirectory) throws Exception {
		String s_currentDirectory = p_s_currentDirectory;
		String s_resourcesNetDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "net" + net.forestany.forestj.lib.io.File.DIR;
		String s_netConfigFile = "netChatLobbyConfig.txt";
		
		if (!net.forestany.forestj.lib.io.File.exists(s_resourcesNetDirectory + s_netConfigFile)) {
			throw new Exception("file[" + s_resourcesNetDirectory + s_netConfigFile + "] does not exists");
		}
		
		net.forestany.forestj.lib.io.File o_netConfigFile = new net.forestany.forestj.lib.io.File(s_resourcesNetDirectory + s_netConfigFile, false);
		
		if (o_netConfigFile.getFileLines() < 6) {
			throw new Exception("invalid config file[" + s_resourcesNetDirectory + s_netConfigFile + "]; must have at least '6 lines', but has '" + o_netConfigFile.getFileLines() + " lines'");
		}
		
		NetChatLobbyTest.NetChatLobbyConfig o_netConfig = new NetChatLobbyTest().new NetChatLobbyConfig();
		o_netConfig.currentDirectory = p_s_currentDirectory;
		o_netConfig.udpMulticastTTL = 1;
		
		for (int i = 1; i <= o_netConfigFile.getFileLines(); i++) {
			String s_line = o_netConfigFile.readLine(i);
			
			if (i == 1) {
				if (!s_line.startsWith("localIp")) {
					throw new Exception("Line #" + i + " does not start with 'localIp'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'localIp': '" + s_line + "'");
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(a_split[1].trim())) {
					throw new Exception("Invalid empty value, for 'localIp'");
				}
				
				o_netConfig.localIp = a_split[1].trim();
			} else if (i == 2) {
				if (!s_line.startsWith("serverPort")) {
					throw new Exception("Line #" + i + " does not start with 'serverPort'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'serverPort': '" + s_line + "'");
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger(a_split[1].trim())) {
					throw new Exception("Invalid value for 'serverPort': '" + a_split[1].trim() + "' is not an integer");
				}
				
				o_netConfig.serverPort = Integer.parseInt(a_split[1].trim());
			} else if (i == 3) {
				if (!s_line.startsWith("udpMulticastTTL")) {
					throw new Exception("Line #" + i + " does not start with 'udpMulticastTTL'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'udpMulticastTTL': '" + s_line + "'");
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger(a_split[1].trim())) {
					throw new Exception("Invalid value for 'udpMulticastTTL': '" + a_split[1].trim() + "' is not an integer");
				}
				
				o_netConfig.udpMulticastTTL = Integer.parseInt(a_split[1].trim());
			} else if (i == 4) {
				if (!s_line.startsWith("udpMulticastNetworkInterfaceName")) {
					throw new Exception("Line #" + i + " does not start with 'udpMulticastNetworkInterfaceName'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'udpMulticastNetworkInterfaceName': '" + s_line + "'");
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(a_split[1].trim())) {
					throw new Exception("Invalid empty value, for 'udpMulticastNetworkInterfaceName'");
				}
				
				o_netConfig.udpMulticastNetworkInterfaceName = a_split[1].trim();
			} else if (i == 5) {
				if (!s_line.startsWith("udpMulticastIp")) {
					throw new Exception("Line #" + i + " does not start with 'udpMulticastIp'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'udpMulticastIp': '" + s_line + "'");
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(a_split[1].trim())) {
					throw new Exception("Invalid empty value, for 'udpMulticastIp'");
				}
				
				o_netConfig.udpMulticastIp = a_split[1].trim();
			} else if (i == 6) {
				if (!s_line.startsWith("udpMulticastPort")) {
					throw new Exception("Line #" + i + " does not start with 'udpMulticastPort'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'udpMulticastPort': '" + s_line + "'");
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger(a_split[1].trim())) {
					throw new Exception("Invalid value for 'udpMulticastPort': '" + a_split[1].trim() + "' is not an integer");
				}
				
				o_netConfig.udpMulticastPort = Integer.parseInt(a_split[1].trim());
			}
		}
		
		System.out.println("++++++++++++++++++++++++++++++++");
		System.out.println("+ NET chat lobby config read   +");
		System.out.println("++++++++++++++++++++++++++++++++");
			
		System.out.println("");
		
		System.out.println("local ip" + "\t\t" + o_netConfig.localIp);
		System.out.println("server port" + "\t\t" + o_netConfig.serverPort);
		System.out.println("udp multicast TTL" + "\t\t" + o_netConfig.udpMulticastTTL);
		System.out.println("udp multicast network interface name" + "\t\t" + o_netConfig.udpMulticastNetworkInterfaceName);
		System.out.println("udp multicast ip" + "\t\t" + o_netConfig.udpMulticastIp);
		System.out.println("udp multicast port" + "\t\t" + o_netConfig.udpMulticastPort);
		
		System.out.println("");
		
		return o_netConfig;
	}

	private static net.forestany.forestj.lib.net.sock.com.Config getCommunicationConfig(
		String p_s_currentDirectory,
		net.forestany.forestj.lib.net.sock.com.Type p_e_comType,
		net.forestany.forestj.lib.net.sock.com.Cardinality p_e_comCardinality,
		String p_s_host,
		int p_i_port,
		String p_s_localHost,
		int p_i_localPort,
		boolean p_b_symmetricSecurity128,
		boolean p_b_symmetricSecurity256,
		boolean p_b_asymmetricSecurity,
		boolean p_b_highSecurity, boolean
		p_b_securityTrustAll, boolean
		p_b_useMarshalling, boolean
		p_b_useMarshallingWholeObject,
		int p_i_marshallingDataLengthInBytes,
		boolean p_b_marshallingUsePropertyMethods,
		boolean p_b_marshallingSystemUsesLittleEndian
	) throws Exception {
		String s_resourcesDirectory = p_s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "certs" + net.forestany.forestj.lib.io.File.DIR;
			
		if ( (p_b_asymmetricSecurity) && ( !net.forestany.forestj.lib.io.File.folderExists(s_resourcesDirectory) ) ) {
			throw new Exception("cannot find directory '" + s_resourcesDirectory + "' where files are needed for asymmetric security communication");
		} else if ( (p_b_asymmetricSecurity) && (p_b_securityTrustAll) ) {
			/* I don't know why exactly, but it is very important that for local test of TCP bidirectional on the same machine, truststore must be set right here with all trusting certificates */
			System.setProperty("javax.net.ssl.trustStore", s_resourcesDirectory + "all/TrustStore-all.p12");
			System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		}
		
		int i_comAmount = 1;
		int i_comMessageBoxLength = 1500;
		int i_comSenderTimeoutMs = 10000;
		int i_comReceiverTimeoutMs = 10000;
		int i_comSenderIntervalMs = 25;
		int i_comQueueTimeoutMs = 25;
		int i_comUDPReceiveAckTimeoutMs = 300;
		int i_comUDPSendAckTimeoutMs = 125;
		String s_comSecretPassphrase = "4sgsh0ni5uo90dw2bqhn66mokasicqcvjebb";
		
		net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = new net.forestany.forestj.lib.net.sock.com.Config(p_e_comType, p_e_comCardinality);
		o_communicationConfig.setSocketReceiveType(net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER);
		
		if (p_e_comCardinality == net.forestany.forestj.lib.net.sock.com.Cardinality.EqualBidirectional) {
			o_communicationConfig.setAmountSockets(1);
			o_communicationConfig.setAmountMessageBoxes(2);
			o_communicationConfig.addMessageBoxLength(i_comMessageBoxLength);
			o_communicationConfig.addMessageBoxLength(i_comMessageBoxLength);
		} else {
			o_communicationConfig.setAmount(i_comAmount);
			o_communicationConfig.addMessageBoxLength(i_comMessageBoxLength);
		}
		
		o_communicationConfig.addHostAndPort(new java.util.AbstractMap.SimpleEntry<String, Integer>(p_s_host, p_i_port));
		o_communicationConfig.setSenderTimeoutMilliseconds(i_comSenderTimeoutMs);
		o_communicationConfig.setReceiverTimeoutMilliseconds(i_comReceiverTimeoutMs);
		o_communicationConfig.setSenderIntervalMilliseconds(i_comSenderIntervalMs);
		o_communicationConfig.setQueueTimeoutMilliseconds(i_comQueueTimeoutMs);
		o_communicationConfig.setUDPReceiveAckTimeoutMilliseconds(i_comUDPReceiveAckTimeoutMs);
		o_communicationConfig.setUDPSendAckTimeoutMilliseconds(i_comUDPSendAckTimeoutMs);
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_localHost)) {
			o_communicationConfig.setLocalAddress(p_s_localHost);
		}
		
		if (p_i_localPort > 0) {
			o_communicationConfig.setLocalPort(p_i_localPort);
		}
		
		if (p_b_symmetricSecurity128) {
			if (p_b_highSecurity) {
				o_communicationConfig.setCommunicationSecurity(net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH);
			} else {
				o_communicationConfig.setCommunicationSecurity(net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW);
			}
			
			o_communicationConfig.setCommonSecretPassphrase(s_comSecretPassphrase);
		} else if (p_b_symmetricSecurity256) {
			if (p_b_highSecurity) {
				o_communicationConfig.setCommunicationSecurity(net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH);
			} else {
				o_communicationConfig.setCommunicationSecurity(net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW);
			}
			
			o_communicationConfig.setCommonSecretPassphrase(s_comSecretPassphrase);
		} else if (p_b_asymmetricSecurity) {
			if (
				p_e_comType == net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE || 
				p_e_comType == net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK || 
				p_e_comType == net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE || 
				p_e_comType == net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE_WITH_ANSWER
			) {
				o_communicationConfig.addSSLContextToList(net.forestany.forestj.lib.Cryptography.createSSLContextWithOneCertificate(s_resourcesDirectory + "server/KeyStore-srv.p12", "123456", "test_server2"));
				o_communicationConfig.setCommunicationSecurity(net.forestany.forestj.lib.net.sock.com.Security.ASYMMETRIC);
			} else {
				if (!p_b_securityTrustAll) {
					o_communicationConfig.setTrustStoreProperties(s_resourcesDirectory + "client/TrustStore-clt.p12", "123456");
				} else {
					o_communicationConfig.addSSLContextToList(net.forestany.forestj.lib.Cryptography.createSSLContextWithOneCertificate(s_resourcesDirectory + "client/KeyStore-clt.p12", "123456", "test_client"));
				}
				
				o_communicationConfig.setCommunicationSecurity(net.forestany.forestj.lib.net.sock.com.Security.ASYMMETRIC);
			}
		}
		
		o_communicationConfig.setUseMarshalling(p_b_useMarshalling);
		o_communicationConfig.setUseMarshallingWholeObject(p_b_useMarshallingWholeObject);
		o_communicationConfig.setMarshallingDataLengthInBytes(p_i_marshallingDataLengthInBytes);
		o_communicationConfig.setMarshallingUsePropertyMethods(p_b_marshallingUsePropertyMethods);
		o_communicationConfig.setMarshallingSystemUsesLittleEndian(p_b_marshallingSystemUsesLittleEndian);
		
		o_communicationConfig.setDebugNetworkTrafficOn(false);
		
		return o_communicationConfig;
	}
		
	private static void netLobby(NetChatLobbyConfig p_o_config) throws Exception {
		if (o_communicationLobby != null) {
			try {
				o_communicationLobby.stop();
			} catch (Exception o_exc) {
				
			}
		}
		
		o_communicationLobby = null;
		
		boolean b_symmetricSecurity128 = false;
		boolean b_symmetricSecurity256 = true;
		boolean b_asymmetricSecurity = false;
		boolean b_highSecurity = false;
		boolean b_securityTrustAll = false;
		
		boolean b_useMarshalling = true;
		boolean b_useMarshallingWholeObject = false;
		int i_marshallingDataLengthInBytes = 2;
		boolean b_marshallingUsePropertyMethods = false;
		boolean b_marshallingSystemUsesLittleEndian = false;
		
		try {
			/* interrupt and null thread lobby if it is still running */
			if (o_threadLobby != null) {
				try { o_threadLobby.interrupt(); } catch (Exception o_exc) { }
				o_threadLobby = null;
			}

			if (p_o_config.isServer) { /* SERVER */
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_SENDER;
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.udpMulticastIp, p_o_config.udpMulticastPort, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_securityTrustAll, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				o_communicationConfig.setUDPMulticastSenderTTL(p_o_config.udpMulticastTTL);
				o_communicationLobby = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communicationLobby.start();
				
				o_threadLobby = new Thread() {
					public void run() {
						try {
							while (true) {
								while ( !o_communicationLobby.enqueue(
									p_o_config.chatRoom + "|" + p_o_config.localIp + ":" + p_o_config.serverPort
								) ) {
									net.forestany.forestj.lib.Global.ilogWarning("could not enqueue message");
								}
								
								net.forestany.forestj.lib.Global.ilog("message enqueued: '" + p_o_config.chatRoom + "|" + p_o_config.localIp + ":" + p_o_config.serverPort + "'");
								
								Thread.sleep(1000);
							}
						} catch (RuntimeException o_exc) {
							/* ignore if communication is not running */
						} catch (Exception o_exc) {
							net.forestany.forestj.lib.Global.logException(o_exc);
						}
					}
				};
				
				o_threadLobby.start();
			} else { /* CLIENT */
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_RECEIVER;
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.udpMulticastIp, p_o_config.udpMulticastPort, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_securityTrustAll, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				o_communicationConfig.setUDPMulticastReceiverNetworkInterfaceName(p_o_config.udpMulticastNetworkInterfaceName);
				o_communicationLobby = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communicationLobby.start();
				
				o_threadLobby = new Thread() {
					public void run() {
						try {
							while (true) {
								java.util.List<java.time.LocalDateTime> a_deleteEntries = new java.util.ArrayList<java.time.LocalDateTime>();
								
								for (java.util.Map.Entry<java.time.LocalDateTime, String> o_entry : m_clientLobbyEntries.entrySet()) {
									if (java.time.Duration.between(o_entry.getKey(), java.time.LocalDateTime.now()).getSeconds() > 30) {
										a_deleteEntries.add(o_entry.getKey());
									}
								}
								
								if (a_deleteEntries.size() > 0) {
									for (java.time.LocalDateTime o_key : a_deleteEntries) {
										m_clientLobbyEntries.remove(o_key);
									}
								}
								
								String s_connectionInfo = null;
								
								do {
									s_connectionInfo = (String)o_communicationLobby.dequeue();
									
									if (s_connectionInfo != null) {
										net.forestany.forestj.lib.Global.ilog("message received: '" + s_connectionInfo + "'");
										
										if (!s_connectionInfo.contains(":")) {
											continue;
										}
										
										int i_readingPort = Integer.parseInt(s_connectionInfo.split(":")[1]);
										
										if (i_readingPort != p_o_config.serverPort) {
											continue;
										}
										
										if (m_clientLobbyEntries.containsValue(s_connectionInfo)) {
											java.time.LocalDateTime o_key = null;
											
											for (java.util.Map.Entry<java.time.LocalDateTime, String> o_entry : m_clientLobbyEntries.entrySet()) {
												if (o_entry.getValue().contentEquals(s_connectionInfo)) {
													o_key = o_entry.getKey();
												}
											}
											
											if (o_key != null) {
												m_clientLobbyEntries.remove(o_key);
											}
										}
										
										m_clientLobbyEntries.put(java.time.LocalDateTime.now(), s_connectionInfo);
									}
								} while (s_connectionInfo != null);
								
								Thread.sleep(1000);
							}
						} catch (RuntimeException o_exc) {
							/* ignore if communication is not running */
						} catch (Exception o_exc) {
							net.forestany.forestj.lib.Global.logException(o_exc);
						}
					}
				};
				
				o_threadLobby.start();
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
		}
	}

	private static void netChat(NetChatLobbyConfig p_o_config) throws Exception {
		if (o_communicationChat != null) {
			try {
				o_communicationChat.stop();
			} catch (Exception o_exc) {
				
			}
		}
		
		o_communicationChat = null;
		
		boolean b_symmetricSecurity128 = false;
		boolean b_symmetricSecurity256 = true;
		boolean b_asymmetricSecurity = false;
		boolean b_highSecurity = false;
		boolean b_securityTrustAll = false;
		
		boolean b_useMarshalling = true;
		boolean b_useMarshallingWholeObject = false;
		int i_marshallingDataLengthInBytes = 2;
		boolean b_marshallingUsePropertyMethods = false;
		boolean b_marshallingSystemUsesLittleEndian = false;
		
		try {
			/* interrupt and null thread chat receive if it is still running */
			if (o_threadChat != null) {
				try { o_threadChat.interrupt(); } catch (Exception o_exc) { }
				o_threadChat = null;
			}
			
			if (p_o_config.isServer) { /* SERVER */
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE_WITH_ANSWER;
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.localIp, p_o_config.serverPort, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_securityTrustAll, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				
				/* add receive socket task(s) */
				net.forestany.forestj.lib.net.sock.task.Task<?> o_receiveSocketTask = new net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket>(net.forestany.forestj.lib.net.sock.Type.TCP) {
					public Class<?> getSocketTaskClassType() {
						return net.forestany.forestj.lib.net.sock.task.Task.class;
					}
					
					public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket> p_o_sourceTask) {
						this.cloneBasicFields(p_o_sourceTask);
					}
					
					protected void runTask() throws Exception {
						try {
							/* get request object */
							String s_request = (String)this.getRequestObject();
							
							/* evaluate request */
							if (s_request != null) {
								String[] a_messages = s_request.split("~");
								
								for (String s_message : a_messages) {
									net.forestany.forestj.lib.Global.ilog("message received: '" + s_message + "'");
									
									String[] a_messageParts = s_message.split("\\|");
									
									if (a_messageParts.length != 3) {
										continue;
									}
									
									java.time.LocalDateTime o_ldtFoo = net.forestany.forestj.lib.Helper.fromISO8601UTC(a_messageParts[0]);
									String s_foo = a_messageParts[1];
									String s_bar = a_messageParts[2];
									
									if (s_bar.contentEquals("%EXIT%")) {
										m_chatHistory.put(java.time.LocalDateTime.now(), new Tuple<String, String>(o_netConfig.chatUser, "%EXIT%"));
										b_connected = false;
										o_lastPing = null;
										return;
									} else if (s_bar.contentEquals("%PING%")) {
										b_connected = true;
										o_lastPing = java.time.LocalDateTime.now();
									} else {
										m_chatHistory.put(o_ldtFoo, new Tuple<String, String>(s_foo, s_bar));
									}
								}
							}
							
							String s_answer = "";
							
							if ((b_connected) && (o_messageBox.getMessageAmount() > 0)) {
								String s_message = null;
								
								do {
									s_message = (String)o_messageBox.dequeueObject();
									
									if (s_message != null) {
										s_answer += s_message + "~";
									}
								} while (s_message != null);
							}
							
							s_answer += net.forestany.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.now()) + "|" + p_o_config.chatUser + "|%PING%";
							
							/* set answer object */
							this.setAnswerObject(s_answer);
						} catch (Exception o_exc) {
							net.forestany.forestj.lib.Global.logException(o_exc);
						}
					}
				};
				
				o_communicationConfig.addReceiveSocketTask(o_receiveSocketTask);
				
				o_communicationChat = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communicationChat.start();
			} else { /* CLIENT */
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND_WITH_ANSWER;
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.hostIp, p_o_config.serverPort, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_securityTrustAll, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				o_communicationChat = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communicationChat.start();
				
				o_threadChat = new Thread() {
					public void run() {
						try {
							while (true) {
								/* prepare request */
								String s_request = "";
								
								if ((b_connected) && (o_messageBox.getMessageAmount() > 0)) {
									String s_message = null;
									
									do {
										s_message = (String)o_messageBox.dequeueObject();
										
										if (s_message != null) {
											s_request += s_message + "~";
										}
									} while (s_message != null);
								}
								
								s_request += net.forestany.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.now()) + "|" + p_o_config.chatUser + "|%PING%";
								
								/* send request */
								while ( !o_communicationChat.enqueue(
									s_request
								) ) {
									net.forestany.forestj.lib.Global.ilogWarning("could not enqueue message");
								}
								
								net.forestany.forestj.lib.Global.ilogFine("message enqueued");
								
								/* wait for answer */
								Object o_answer = o_communicationChat.dequeueWithWaitLoop(5000);
								
								if (o_answer != null) {
									/* evaluate answer */
									String[] a_messages = ((String)o_answer).split("~");
									
									for (String s_message : a_messages) {
										net.forestany.forestj.lib.Global.ilog("message received: '" + s_message + "'");
										
										if (!b_connected) {
											continue;
										}
										
										String[] a_messageParts = s_message.split("\\|");
										
										if (a_messageParts.length != 3) {
											continue;
										}
										
										java.time.LocalDateTime o_ldtFoo = net.forestany.forestj.lib.Helper.fromISO8601UTC(a_messageParts[0]);
										String s_foo = a_messageParts[1];
										String s_bar = a_messageParts[2];
										
										if (s_bar.contentEquals("%EXIT%")) {
											m_chatHistory.put(java.time.LocalDateTime.now(), new Tuple<String, String>(o_netConfig.chatUser, "%EXIT%"));
											b_connected = false;
										} else if (s_bar.contentEquals("%PING%")) {
											b_connected = true;
											o_lastPing = java.time.LocalDateTime.now();
										} else {
											m_chatHistory.put(o_ldtFoo, new Tuple<String, String>(s_foo, s_bar));
										}
									}
								} else {
									net.forestany.forestj.lib.Global.ilogWarning("could not receive any answer data");
								}
								
								Thread.sleep(1000);
							}
						} catch (RuntimeException o_exc) {
							/* ignore if communication is not running */
						} catch (Exception o_exc) {
							net.forestany.forestj.lib.Global.logException(o_exc);
						}
					}
				};
				
				o_threadChat.start();
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
		}
	}

	private static void renderChat(NetChatLobbyConfig p_o_config) throws Exception {
		java.util.TreeMap<java.time.LocalDateTime, Tuple<String, String>> m_sorted = new java.util.TreeMap<>();
		m_sorted.putAll(m_chatHistory);
		
		for (java.util.Map.Entry<java.time.LocalDateTime, Tuple<String, String>> o_chatEntry : m_sorted.entrySet()) {
			if (o_chatEntry.getValue().x.contentEquals(p_o_config.chatUser)) {
				System.out.println(o_chatEntry.getKey() + " - " + o_chatEntry.getValue().x);
				System.out.println(o_chatEntry.getValue().y);
			} else {
				System.out.println("\t\t\t\t" + o_chatEntry.getKey() + " - " + o_chatEntry.getValue().x);
				System.out.println("\t\t\t\t" + o_chatEntry.getValue().y);
			}
		}
	}
	
	private static void clearMessageBox() {
		if (o_messageBox.getMessageAmount() > 0) {
			Object o_foo = null;
			
			do {
				o_foo = (String)o_messageBox.dequeueObject();
			} while (o_foo != null);
		}
	}
}
