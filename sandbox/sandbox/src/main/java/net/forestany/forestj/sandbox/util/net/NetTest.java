package net.forestany.forestj.sandbox.util.net;

public class NetTest {
	/* sleep multiplier for test cycle executions */
	private static int i_sleepMultiplier = 8;
	
	public static void testNetMenu(String p_s_currentDirectory) throws Exception {
		NetTest.NetConfig o_netConfig = NetTest.readNetConfig(p_s_currentDirectory);
		
		o_netConfig.isServer = net.forestany.forestj.lib.Console.consoleInputBoolean("Run as server[true|false]: ");
		
		System.out.println("");
		
		int i_input = -1;
		
		do {
			System.out.println("++++++++++++++++++++++++++++++++");
			System.out.println("+ test NET " + ((o_netConfig.isServer) ? "Server" : "Client") + "              +");
			System.out.println("++++++++++++++++++++++++++++++++");
			
			System.out.println("");
			
			System.out.println("[1] TCP Socket big file(100 MB) with progress bar");
			System.out.println("[2] TCP Socket handshake");
			
			System.out.println("[3] UDP Communication");
			System.out.println("[4] UDP Multicast Communication");
			System.out.println("[5] UDP Communication with Acknowledge");
			System.out.println("[6] TCP Communication");
			
			System.out.println("[7] TCP Communication with object transmission");
			System.out.println("[8] TCP Communication with answer #1");
			System.out.println("[9] TCP Communication with answer #2");
			
			System.out.println("[10] UDP Communication unidirectional");
			System.out.println("[11] UDP Communication with Acknowledge unidirectional");
			System.out.println("[12] TCP Communication unidirectional");
			
			System.out.println("[13] UDP Communication bidirectional");
			System.out.println("[14] UDP Communication with Acknowledge bidirectional");
			System.out.println("[15] TCP Communication bidirectional");
			
			System.out.println("[16] UDP Communication marshalling small object");
			System.out.println("[17] UDP Communication marshalling small object with Acknowledge");
			System.out.println("[18] TCP Communication marshalling small object");
			System.out.println("[19] UDP Communication marshalling object");
			System.out.println("[20] UDP Communication marshalling object with Acknowledge");
			System.out.println("[21] TCP Communication marshalling object");
			
			System.out.println("[22] UDP Communication marshalling small object shared memory unidirectional");
			System.out.println("[23] UDP Communication marshalling small object with Acknowledge shared memory unidirectional");
			System.out.println("[24] TCP Communication marshalling small object shared memory unidirectional");
			System.out.println("[25] UDP Communication marshalling object shared memory unidirectional");
			System.out.println("[26] UDP Communication marshalling object with Acknowledge shared memory unidirectional");
			System.out.println("[27] TCP Communication marshalling object shared memory unidirectional");
			
			System.out.println("[28] UDP Communication marshalling small object shared memory bidirectional");
			System.out.println("[29] UDP Communication marshalling small object with Acknowledge shared memory bidirectional");
			System.out.println("[30] TCP Communication marshalling small object shared memory bidirectional");
			System.out.println("[31] UDP Communication marshalling object shared memory bidirectional");
			System.out.println("[32] UDP Communication marshalling object with Acknowledge shared memory bidirectional");
			System.out.println("[33] TCP Communication marshalling object shared memory bidirectional");
			
			System.out.println("[34] Run Tests #2 - #14 with current settings");
			System.out.println("[0] quit");
			
			System.out.println("");
			
			i_input = net.forestany.forestj.lib.Console.consoleInputInteger("Enter menu number[1-34;0]: ", "Invalid input.", "Please enter a value[1-34;0].");
			
			/* do handshake before any test for synchronisation purpose, but not [2] and [4] */
			if ((i_input >= 1) && (i_input <= 34) && (!((i_input == 2) || (i_input == 4)))) {
				NetTest.netTCPSockHandshakeTest(o_netConfig);
			}
			
			if (i_input == 1) {
				NetTest.netTCPSockBigFileTest(o_netConfig);
			} else if (i_input == 2) {
				NetTest.netTCPSockHandshakeTest(o_netConfig);
			} else if (i_input == 3) {
				NetTest.netCommunication(false, false, o_netConfig);
			} else if (i_input == 4) {
				NetTest.netCommunicationUDPMulticast(o_netConfig, true);
			} else if (i_input == 5) {
				NetTest.netCommunication(false, true, o_netConfig);
			} else if (i_input == 5) {
				NetTest.netCommunication(true, false, o_netConfig);
			} else if (i_input == 7) {
				NetTest.netCommunicationObjectTransmissionTCP(o_netConfig);
			} else if (i_input == 8) {
				NetTest.netCommunicationWithAnswerOneTCP(o_netConfig);
			} else if (i_input == 9) {
				NetTest.netCommunicationWithAnswerTwoTCP(o_netConfig);
			} else if (i_input == 10) {
				NetTest.netCommunicationSharedMemoryUniDirectional(false, false, o_netConfig);
			} else if (i_input == 11) {
				NetTest.netCommunicationSharedMemoryUniDirectional(false, true, o_netConfig);
			} else if (i_input == 12) {
				NetTest.netCommunicationSharedMemoryUniDirectional(true, false, o_netConfig);
			} else if (i_input == 13) {
				NetTest.netCommunicationSharedMemoryBiDirectional(false, false, o_netConfig);
			} else if (i_input == 14) {
				NetTest.netCommunicationSharedMemoryBiDirectional(false, true, o_netConfig);
			} else if (i_input == 15) {
				NetTest.netCommunicationSharedMemoryBiDirectional(true, false, o_netConfig);
			} else if (i_input == 16) {
				NetTest.netCommunicationMarshallingObject(false, false, true, o_netConfig);
			} else if (i_input == 17) {
				NetTest.netCommunicationMarshallingObject(false, true, true, o_netConfig);
			} else if (i_input == 18) {
				NetTest.netCommunicationMarshallingObject(true, false, true, o_netConfig);
			} else if (i_input == 19) {
				NetTest.netCommunicationMarshallingObject(false, false, false, o_netConfig);
			} else if (i_input == 20) {
				NetTest.netCommunicationMarshallingObject(false, true, false, o_netConfig);
			} else if (i_input == 21) {
				NetTest.netCommunicationMarshallingObject(true, false, false, o_netConfig);
			} else if (i_input == 22) {
				NetTest.netCommunicationMarshallingSharedMemoryUniDirectional(false, false, true, o_netConfig);
			} else if (i_input == 23) {
				NetTest.netCommunicationMarshallingSharedMemoryUniDirectional(false, true, true, o_netConfig);
			} else if (i_input == 24) {
				NetTest.netCommunicationMarshallingSharedMemoryUniDirectional(true, false, true, o_netConfig);
			} else if (i_input == 25) {
				NetTest.netCommunicationMarshallingSharedMemoryUniDirectional(false, false, false, o_netConfig);
			} else if (i_input == 26) {
				NetTest.netCommunicationMarshallingSharedMemoryUniDirectional(false, true, false, o_netConfig);
			} else if (i_input == 27) {
				NetTest.netCommunicationMarshallingSharedMemoryUniDirectional(true, false, false, o_netConfig);
			} else if (i_input == 28) {
				NetTest.netCommunicationMarshallingSharedMemoryBiDirectional(false, false, true, o_netConfig);
			} else if (i_input == 29) {
				NetTest.netCommunicationMarshallingSharedMemoryBiDirectional(false, true, true, o_netConfig);
			} else if (i_input == 30) {
				NetTest.netCommunicationMarshallingSharedMemoryBiDirectional(true, false, true, o_netConfig);
			} else if (i_input == 31) {
				NetTest.netCommunicationMarshallingSharedMemoryBiDirectional(false, false, false, o_netConfig);
			} else if (i_input == 32) {
				NetTest.netCommunicationMarshallingSharedMemoryBiDirectional(false, true, false, o_netConfig);
			} else if (i_input == 33) {
				NetTest.netCommunicationMarshallingSharedMemoryBiDirectional(true, false, false, o_netConfig);
			} else if (i_input == 34) {
				int i_timeoutMillisecondsForHandshake = 60000;
				
				if (!o_netConfig.asymmetricSecurity) {
					NetTest.netTCPSockHandshakeTest(o_netConfig, i_timeoutMillisecondsForHandshake);
					NetTest.netCommunication(false, false, o_netConfig);
					NetTest.netTCPSockHandshakeTest(o_netConfig, i_timeoutMillisecondsForHandshake);
					NetTest.netCommunication(false, true, o_netConfig);
					NetTest.netTCPSockHandshakeTest(o_netConfig, i_timeoutMillisecondsForHandshake);
					NetTest.netCommunicationUDPMulticast(o_netConfig, false);
				}
				
				NetTest.netTCPSockHandshakeTest(o_netConfig, i_timeoutMillisecondsForHandshake);
				NetTest.netCommunication(true, false, o_netConfig);
				NetTest.netTCPSockHandshakeTest(o_netConfig, i_timeoutMillisecondsForHandshake);
				NetTest.netCommunicationObjectTransmissionTCP(o_netConfig);
				NetTest.netTCPSockHandshakeTest(o_netConfig, i_timeoutMillisecondsForHandshake);
				NetTest.netCommunicationWithAnswerOneTCP(o_netConfig);
				NetTest.netTCPSockHandshakeTest(o_netConfig, i_timeoutMillisecondsForHandshake);
				NetTest.netCommunicationWithAnswerTwoTCP(o_netConfig);
				
				if (!o_netConfig.asymmetricSecurity) {
					NetTest.netTCPSockHandshakeTest(o_netConfig, i_timeoutMillisecondsForHandshake);
					NetTest.netCommunicationSharedMemoryUniDirectional(false, false, o_netConfig);
					NetTest.netTCPSockHandshakeTest(o_netConfig, i_timeoutMillisecondsForHandshake);
					NetTest.netCommunicationSharedMemoryUniDirectional(false, true, o_netConfig);
				}
				
				NetTest.netTCPSockHandshakeTest(o_netConfig, i_timeoutMillisecondsForHandshake);
				NetTest.netCommunicationSharedMemoryUniDirectional(true, false, o_netConfig);
				
				if (!o_netConfig.asymmetricSecurity) {
					NetTest.netTCPSockHandshakeTest(o_netConfig, i_timeoutMillisecondsForHandshake);
					NetTest.netCommunicationSharedMemoryBiDirectional(false, false, o_netConfig);
					NetTest.netTCPSockHandshakeTest(o_netConfig, i_timeoutMillisecondsForHandshake);
					NetTest.netCommunicationSharedMemoryBiDirectional(false, true, o_netConfig);
				}
				
				NetTest.netTCPSockHandshakeTest(o_netConfig, i_timeoutMillisecondsForHandshake);
				NetTest.netCommunicationSharedMemoryBiDirectional(true, false, o_netConfig);
			}
			
			if ( (i_input >= 1) && (i_input <= 34) ) {
				System.out.println("");
				
				net.forestany.forestj.lib.Console.consoleInputString("Press any key to continue . . . ", true);
				
				System.out.println("");
			}
			
			System.out.println("");
			
		} while (i_input != 0);
	}
	
	public class NetConfig {
		public boolean isServer;
		public String serverIp;
		public int serverPort;
		public String clientIp;
		public int clientPort;
		public String serverBiIp;
		public int serverBiPort;
		public String clientBiIp;
		public int clientBiPort;
		public String clientLocalIp;
		public int clientLocalPort;
		public int handshakePort;
		public boolean symmetricSecurity128;
		public boolean symmetricSecurity256;
		public boolean asymmetricSecurity;
		public boolean highSecurity;
		public boolean useMarshalling;
		public boolean useMarshallingWholeObject;
		public int marshallingDataLengthInBytes;
		public boolean marshallingUsePropertyMethods;
		public boolean marshallingSystemUsesLittleEndian;
		public int sleepMultiplier;
		public String currentDirectory;
		public int udpMulticastTTL;
		public String udpMulticastNetworkInterfaceName;
		public String udpMulticastIp;
		public int udpMulticastPort;
	}
	
	public static NetConfig readNetConfig(String p_s_currentDirectory) throws Exception {
		String s_currentDirectory = p_s_currentDirectory;
		String s_resourcesNetDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "net" + net.forestany.forestj.lib.io.File.DIR;
		String s_netConfigFile = "netConfig.txt";
		
		if (!net.forestany.forestj.lib.io.File.exists(s_resourcesNetDirectory + s_netConfigFile)) {
			throw new Exception("file[" + s_resourcesNetDirectory + s_netConfigFile + "] does not exists");
		}
		
		net.forestany.forestj.lib.io.File o_netConfigFile = new net.forestany.forestj.lib.io.File(s_resourcesNetDirectory + s_netConfigFile, false);
		
		if (o_netConfigFile.getFileLines() < 11) {
			throw new Exception("invalid config file[" + s_resourcesNetDirectory + s_netConfigFile + "]; must have at least '11 lines', but has '" + o_netConfigFile.getFileLines() + " lines'");
		}
		
		NetTest.NetConfig o_netConfig = new NetTest().new NetConfig();
		
		o_netConfig.symmetricSecurity128 = false;
		o_netConfig.symmetricSecurity256 = false;
		o_netConfig.asymmetricSecurity = false;
		o_netConfig.highSecurity = false;
		o_netConfig.useMarshalling = false;
		o_netConfig.useMarshallingWholeObject = false;
		o_netConfig.marshallingDataLengthInBytes = 1;
		o_netConfig.marshallingUsePropertyMethods = false;
		o_netConfig.marshallingSystemUsesLittleEndian = false;
		o_netConfig.sleepMultiplier = i_sleepMultiplier;
		o_netConfig.currentDirectory = p_s_currentDirectory;
		o_netConfig.udpMulticastTTL = 1;
		
		for (int i = 1; i <= o_netConfigFile.getFileLines(); i++) {
			String s_line = o_netConfigFile.readLine(i);
			
			if (i == 1) {
				if (!s_line.startsWith("serverIp")) {
					throw new Exception("Line #" + i + " does not start with 'serverIp'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'serverIp': '" + s_line + "'");
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(a_split[1].trim())) {
					throw new Exception("Invalid empty value, for 'serverIp'");
				}
				
				o_netConfig.serverIp = a_split[1].trim();
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
				if (!s_line.startsWith("clientIp")) {
					throw new Exception("Line #" + i + " does not start with 'clientIp'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'clientIp': '" + s_line + "'");
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(a_split[1].trim())) {
					throw new Exception("Invalid empty value, for 'clientIp'");
				}
				
				o_netConfig.clientIp = a_split[1].trim();
			} else if (i == 4) {
				if (!s_line.startsWith("clientPort")) {
					throw new Exception("Line #" + i + " does not start with 'clientPort'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'clientPort': '" + s_line + "'");
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger(a_split[1].trim())) {
					throw new Exception("Invalid value for 'clientPort': '" + a_split[1].trim() + "' is not an integer");
				}
				
				o_netConfig.clientPort = Integer.parseInt(a_split[1].trim());
			} else if (i == 5) {
				if (!s_line.startsWith("serverBiIp")) {
					throw new Exception("Line #" + i + " does not start with 'serverBiIp'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'serverBiIp': '" + s_line + "'");
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(a_split[1].trim())) {
					throw new Exception("Invalid empty value, for 'serverBiIp'");
				}
				
				o_netConfig.serverBiIp = a_split[1].trim();
			} else if (i == 6) {
				if (!s_line.startsWith("serverBiPort")) {
					throw new Exception("Line #" + i + " does not start with 'serverBiPort'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'serverBiPort': '" + s_line + "'");
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger(a_split[1].trim())) {
					throw new Exception("Invalid value for 'serverBiPort': '" + a_split[1].trim() + "' is not an integer");
				}
				
				o_netConfig.serverBiPort = Integer.parseInt(a_split[1].trim());
			} else if (i == 7) {
				if (!s_line.startsWith("clientBiIp")) {
					throw new Exception("Line #" + i + " does not start with 'clientBiIp'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'clientBiIp': '" + s_line + "'");
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(a_split[1].trim())) {
					throw new Exception("Invalid empty value, for 'clientBiIp'");
				}
				
				o_netConfig.clientBiIp = a_split[1].trim();
			} else if (i == 8) {
				if (!s_line.startsWith("clientBiPort")) {
					throw new Exception("Line #" + i + " does not start with 'clientBiPort'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'clientBiPort': '" + s_line + "'");
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger(a_split[1].trim())) {
					throw new Exception("Invalid value for 'clientBiPort': '" + a_split[1].trim() + "' is not an integer");
				}
				
				o_netConfig.clientBiPort = Integer.parseInt(a_split[1].trim());
			} else if (i == 9) {
				if (!s_line.startsWith("clientLocalIp")) {
					throw new Exception("Line #" + i + " does not start with 'clientLocalIp'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'clientLocalIp': '" + s_line + "'");
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(a_split[1].trim())) {
					throw new Exception("Invalid empty value, for 'clientLocalIp'");
				}
				
				o_netConfig.clientLocalIp = a_split[1].trim();
			} else if (i == 10) {
				if (!s_line.startsWith("clientLocalPort")) {
					throw new Exception("Line #" + i + " does not start with 'clientLocalPort'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'clientLocalPort': '" + s_line + "'");
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger(a_split[1].trim())) {
					throw new Exception("Invalid value for 'clientLocalPort': '" + a_split[1].trim() + "' is not an integer");
				}
				
				o_netConfig.clientLocalPort = Integer.parseInt(a_split[1].trim());
			} else if (i == 11) {
				if (!s_line.startsWith("handshakePort")) {
					throw new Exception("Line #" + i + " does not start with 'handshakePort'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'handshakePort': '" + s_line + "'");
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger(a_split[1].trim())) {
					throw new Exception("Invalid value for 'handshakePort': '" + a_split[1].trim() + "' is not an integer");
				}
				
				o_netConfig.handshakePort = Integer.parseInt(a_split[1].trim());
			} else if (i == 12) {
				if (!s_line.startsWith("symmetricSecurity128")) {
					throw new Exception("Line #" + i + " does not start with 'symmetricSecurity128'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'symmetricSecurity128': '" + s_line + "'");
				}
				
				o_netConfig.symmetricSecurity128 = Boolean.parseBoolean(a_split[1].trim());
			} else if (i == 13) {
				if (!s_line.startsWith("symmetricSecurity256")) {
					throw new Exception("Line #" + i + " does not start with 'symmetricSecurity256'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'symmetricSecurity256': '" + s_line + "'");
				}
								
				o_netConfig.symmetricSecurity256 = Boolean.parseBoolean(a_split[1].trim());
			} else if (i == 14) {
				if (!s_line.startsWith("asymmetricSecurity")) {
					throw new Exception("Line #" + i + " does not start with 'asymmetricSecurity'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'asymmetricSecurity': '" + s_line + "'");
				}
				
				o_netConfig.asymmetricSecurity = Boolean.parseBoolean(a_split[1].trim());
			} else if (i == 15) {
				if (!s_line.startsWith("highSecurity")) {
					throw new Exception("Line #" + i + " does not start with 'highSecurity'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'highSecurity': '" + s_line + "'");
				}
				
				o_netConfig.highSecurity = Boolean.parseBoolean(a_split[1].trim());
			} else if (i == 16) {
				if (!s_line.startsWith("useMarshalling")) {
					throw new Exception("Line #" + i + " does not start with 'useMarshalling'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'useMarshalling': '" + s_line + "'");
				}
				
				o_netConfig.useMarshalling = Boolean.parseBoolean(a_split[1].trim());
			} else if (i == 17) {
				if (!s_line.startsWith("useMarshallingWholeObject")) {
					throw new Exception("Line #" + i + " does not start with 'useMarshallingWholeObject'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'useMarshallingWholeObject': '" + s_line + "'");
				}
				
				o_netConfig.useMarshallingWholeObject = Boolean.parseBoolean(a_split[1].trim());
			} else if (i == 18) {
				if (!s_line.startsWith("marshallingDataLengthInBytes")) {
					throw new Exception("Line #" + i + " does not start with 'marshallingDataLengthInBytes'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'marshallingDataLengthInBytes': '" + s_line + "'");
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger(a_split[1].trim())) {
					throw new Exception("Invalid value for 'marshallingDataLengthInBytes': '" + a_split[1].trim() + "' is not an integer");
				}
				
				o_netConfig.marshallingDataLengthInBytes = Integer.parseInt(a_split[1].trim());
			} else if (i == 19) {
				if (!s_line.startsWith("marshallingUsePropertyMethods")) {
					throw new Exception("Line #" + i + " does not start with 'marshallingUsePropertyMethods'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'marshallingUsePropertyMethods': '" + s_line + "'");
				}
				
				o_netConfig.marshallingUsePropertyMethods = Boolean.parseBoolean(a_split[1].trim());
			} else if (i == 20) {
				if (!s_line.startsWith("marshallingSystemUsesLittleEndian")) {
					throw new Exception("Line #" + i + " does not start with 'marshallingSystemUsesLittleEndian'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'marshallingSystemUsesLittleEndian': '" + s_line + "'");
				}
				
				o_netConfig.marshallingSystemUsesLittleEndian = Boolean.parseBoolean(a_split[1].trim());
			} else if (i == 21) {
				if (!s_line.startsWith("threadSleepMultiplier")) {
					throw new Exception("Line #" + i + " does not start with 'threadSleepMultiplier'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'threadSleepMultiplier': '" + s_line + "'");
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger(a_split[1].trim())) {
					throw new Exception("Invalid value for 'threadSleepMultiplier': '" + a_split[1].trim() + "' is not an integer");
				}
				
				o_netConfig.sleepMultiplier = Integer.parseInt(a_split[1].trim());
			} else if (i == 22) {
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
			} else if (i == 23) {
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
			} else if (i == 24) {
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
			} else if (i == 25) {
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
		System.out.println("+ NET config read              +");
		System.out.println("++++++++++++++++++++++++++++++++");
			
		System.out.println("");
		
		System.out.println("server ip" + "\t\t" + o_netConfig.serverIp);
		System.out.println("server port" + "\t\t" + o_netConfig.serverPort);
		System.out.println("client ip" + "\t\t" + o_netConfig.clientIp);
		System.out.println("client port" + "\t\t" + o_netConfig.clientPort);
		System.out.println("server bi ip" + "\t\t" + o_netConfig.serverBiIp);
		System.out.println("server bi port" + "\t\t" + o_netConfig.serverBiPort);
		System.out.println("client bi ip" + "\t\t" + o_netConfig.clientBiIp);
		System.out.println("client bi port" + "\t\t" + o_netConfig.clientBiPort);
		System.out.println("client local ip" + "\t\t" + o_netConfig.clientLocalIp);
		System.out.println("client local port" + "\t" + o_netConfig.clientLocalPort);
		System.out.println("handshake port" + "\t\t" + o_netConfig.handshakePort);
		System.out.println("sym security 128" + "\t\t" + o_netConfig.symmetricSecurity128);
		System.out.println("sym security 256" + "\t\t" + o_netConfig.symmetricSecurity256);
		System.out.println("asym security" + "\t\t\t" + o_netConfig.asymmetricSecurity);
		System.out.println("high sym security" + "\t\t" + o_netConfig.highSecurity);
		System.out.println("use marshalling" + "\t\t\t" + o_netConfig.useMarshalling);
		System.out.println("marshl. whole obj." + "\t\t" + o_netConfig.useMarshallingWholeObject);
		System.out.println("marshl. data length" + "\t\t" + o_netConfig.marshallingDataLengthInBytes);
		System.out.println("marshl. use properties" + "\t\t" + o_netConfig.marshallingUsePropertyMethods);
		System.out.println("marshl. system LE" + "\t\t" + o_netConfig.marshallingSystemUsesLittleEndian);
		System.out.println("thread sleep multiplier" + "\t\t" + o_netConfig.sleepMultiplier);
		System.out.println("udp multicast TTL" + "\t\t" + o_netConfig.udpMulticastTTL);
		System.out.println("udp multicast network interface name" + "\t\t" + o_netConfig.udpMulticastNetworkInterfaceName);
		System.out.println("udp multicast ip" + "\t\t" + o_netConfig.udpMulticastIp);
		System.out.println("udp multicast port" + "\t\t" + o_netConfig.udpMulticastPort);
		
		System.out.println("");
		
		return o_netConfig;
	}
	
	private static net.forestany.forestj.lib.net.sock.com.Config getCommunicationConfig(String p_s_currentDirectory, net.forestany.forestj.lib.net.sock.com.Type p_e_comType, net.forestany.forestj.lib.net.sock.com.Cardinality p_e_comCardinality, String p_s_host, int p_i_port, String p_s_localHost, int p_i_localPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_asymmetricSecurity, boolean p_b_highSecurity, boolean p_b_securityTrustAll, boolean p_b_useMarshalling, boolean p_b_useMarshallingWholeObject, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		String s_resourcesDirectory = p_s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "com" + net.forestany.forestj.lib.io.File.DIR;
			
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
		String s_comSecretPassphrase = "z/?J}%KhZGr?6*rKJL,{-rf:^Necj~3M3Msj";
		
		net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = new net.forestany.forestj.lib.net.sock.com.Config(p_e_comType, p_e_comCardinality);
		o_communicationConfig.setSocketReceiveType(net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER);
		o_communicationConfig.setAmount(i_comAmount);
		o_communicationConfig.addMessageBoxLength(i_comMessageBoxLength);
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
		
		return o_communicationConfig;
	}
	
	private static void netTCPSockBigFileTest(NetConfig p_o_config) throws Exception {
		i_sleepMultiplier = p_o_config.sleepMultiplier;
		net.forestany.forestj.lib.ConsoleProgressBar o_consoleProgressBar = new net.forestany.forestj.lib.ConsoleProgressBar();
		
		net.forestany.forestj.lib.net.sock.task.Task.IDelegate itf_delegate = new net.forestany.forestj.lib.net.sock.task.Task.IDelegate() { 
			@Override public void PostProgress(int p_i_bytes, int p_i_totalBytes) {
				o_consoleProgressBar.report((double)p_i_bytes / p_i_totalBytes);
			}
		};
		
		String s_currentDirectory = p_o_config.currentDirectory;
		
		if (p_o_config.isServer) { /* SERVER */
			String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "netTCPSockBigFileTestReceive" + net.forestany.forestj.lib.io.File.DIR;
			
			if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			}
			
			net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
			
			if (!net.forestany.forestj.lib.io.File.folderExists(s_testDirectory)) {
				throw new Exception("directory[" + s_testDirectory + "] does not exists");
			}
			
			String s_destinationFile = s_testDirectory + "destinationFile.txt";
			
			Thread o_threadServer = null;
			net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<java.net.ServerSocket> o_socketReceive = null;
			
			net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket>(net.forestany.forestj.lib.net.sock.Type.TCP) {
				public Class<?> getSocketTaskClassType() {
					return net.forestany.forestj.lib.net.sock.task.Task.class;
				}
				
				public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket> p_o_sourceTask) {
					this.cloneBasicFields(p_o_sourceTask);
				}
				
				protected void runTask() throws Exception {
					try {
						net.forestany.forestj.lib.Global.ilog("Handle incoming socket communication with " + ((java.net.Socket)this.o_socket.getSocket()).getInetAddress().toString());
					
						try {
							int i_amountBytes = this.amountBytesProtocol();				
							net.forestany.forestj.lib.Global.ilog("Amount bytes receiving: " + i_amountBytes);
							
							/* ------------------------------------------------------ */
							
							if (i_amountBytes > 0) {
								o_consoleProgressBar.init("Receive data . . .", "Done.");
								byte[] a_receivedData = this.receiveBytes(i_amountBytes);
								o_consoleProgressBar.close();
								net.forestany.forestj.lib.Global.ilog("Received file with length[" + a_receivedData.length + "]");
								
								try (java.io.FileOutputStream o_fileOutputStream = new java.io.FileOutputStream(s_destinationFile)) {
									o_fileOutputStream.write(a_receivedData);
								}
								net.forestany.forestj.lib.Global.ilog("Wrote bytes into file");
								
								/* ------------------------------------------------------ */
								
								i_amountBytes = this.amountBytesProtocol(a_receivedData.length);				
								net.forestany.forestj.lib.Global.ilog("Amount bytes received: " + i_amountBytes);
								
								this.b_stop = true;
							} else {
								net.forestany.forestj.lib.Global.ilog("Nothing received. Protocol for receiving length failed completely or was not intended (check availability call over TCP)");
							}
						} catch (java.io.IOException o_exc) {
							throw o_exc;
						}
						
						net.forestany.forestj.lib.Global.ilog("Socket communication closed");
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
					}
				}
			};
			
			o_serverTask.setDelegate(itf_delegate);
			o_socketReceive = new net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<java.net.ServerSocket>(
				java.net.ServerSocket.class,						/* class type */
				net.forestany.forestj.lib.net.sock.recv.ReceiveType.SOCKET,	/* socket type */
				p_o_config.serverIp,								/* receiving address */
				p_o_config.serverPort,								/* receiving port */
				o_serverTask,										/* server task */
				30000,												/* timeout milliseconds */
				-1,													/* max. number of executions */
				8192,												/* receive buffer size */
				null												/* ssl context */
			);
			o_threadServer = new Thread(o_socketReceive);
			o_threadServer.start();
			
			o_threadServer.join();
			
			if (o_socketReceive != null) {
				o_socketReceive.stop();
			}
			
			if (net.forestany.forestj.lib.io.File.fileLength(s_destinationFile) != 104857600) {
				net.forestany.forestj.lib.Global.logException(new Exception("destination file length " + net.forestany.forestj.lib.io.File.fileLength(s_destinationFile) + " != 104857600"));
			}
			
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
		
			if (net.forestany.forestj.lib.io.File.folderExists(s_testDirectory)) {
				throw new Exception("directory[" + s_testDirectory + "] does exists");
			}
		} else { /* CLIENT */
			String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "netTCPSockBigFileTestSend" + net.forestany.forestj.lib.io.File.DIR;
			String s_sourceFile;
			
			if (net.forestany.forestj.lib.io.File.exists(s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "104857600_bytes.txt")) {
				s_sourceFile = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "104857600_bytes.txt";
			} else {
				if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
					net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
				}
				
				net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
				
				if (!net.forestany.forestj.lib.io.File.folderExists(s_testDirectory)) {
					throw new Exception("directory[" + s_testDirectory + "] does not exists");
				}
				
				s_sourceFile = s_testDirectory + "sourceFile.txt";
				
				@SuppressWarnings("unused")
				net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_sourceFile, true);
				
				net.forestany.forestj.lib.io.File.replaceFileContent(s_sourceFile, net.forestany.forestj.lib.io.File.generateRandomFileContent_100MB());
			}
			
			if (net.forestany.forestj.lib.io.File.fileLength(s_sourceFile) != 104857600) {
				throw new Exception("source file length != 104857600");
			}
			
			Thread o_threadClient = null;
			net.forestany.forestj.lib.net.sock.send.SendTCP<java.net.Socket> o_socketSend = null;
			
			net.forestany.forestj.lib.net.sock.task.Task<java.net.Socket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.Task<java.net.Socket>(net.forestany.forestj.lib.net.sock.Type.TCP) {
				public Class<?> getSocketTaskClassType() {
					return net.forestany.forestj.lib.net.sock.task.Task.class;
				}
				
				public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<java.net.Socket> p_o_sourceTask) {
					this.cloneBasicFields(p_o_sourceTask);
				}
				
				protected void runTask() throws Exception {
					try {
						net.forestany.forestj.lib.Global.ilog("Starting socket communication");
				
						byte[] a_buffer = new byte[8192];
						byte[] a_data = null;
						int i_read = 0;
						java.io.ByteArrayOutputStream o_byteArrayOutputStream = new java.io.ByteArrayOutputStream();
						
						try (java.io.FileInputStream o_fileInputStream = new java.io.FileInputStream(s_sourceFile)) {
						    while ((i_read = o_fileInputStream.read(a_buffer)) != -1) {
						    	o_byteArrayOutputStream.write(a_buffer, 0, i_read);
					        }
						} finally {
							try {
								if (o_byteArrayOutputStream != null) {
									a_data = o_byteArrayOutputStream.toByteArray();
									o_byteArrayOutputStream.close();
								}
							} catch (Exception o_exc) {
								/* nothing to do */
							}
						}
						
						if (a_data == null) {
							throw new Exception("Could not read big file, readed data is null");
						}
						
						int i_amountBytes = this.amountBytesProtocol(a_data.length);
						net.forestany.forestj.lib.Global.ilog("Amount bytes sending: " + i_amountBytes);
						
						/* ------------------------------------------------------ */
						
						o_consoleProgressBar.init("Send data . . .", "Done.");
						this.sendBytes(a_data);
						o_consoleProgressBar.close();
						net.forestany.forestj.lib.Global.ilog("Sended data, amount of bytes: " + a_data.length);
						
						net.forestany.forestj.lib.Global.ilog("waiting " + 25 * i_sleepMultiplier * 2 + "ms");
						Thread.sleep(25 * i_sleepMultiplier * 2);
						
						/* ------------------------------------------------------ */
						
						i_amountBytes = this.amountBytesProtocol();				
						net.forestany.forestj.lib.Global.ilog("Amount bytes sended: " + i_amountBytes);
						
						net.forestany.forestj.lib.Global.ilog("Socket communication finished");
						
						this.b_stop = true;
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
					}
				}
			};
			
			o_clientTask.setDelegate(itf_delegate);
			o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendTCP<java.net.Socket>(
				java.net.Socket.class,			/* class type */
				p_o_config.serverIp,			/* destination address */
				p_o_config.serverPort,			/* destination port */
				o_clientTask,					/* client task */
				30000,							/* timeout milliseconds */
				true,							/* check if destination is reachable */
				-1,								/* max. number of executions */
				25,								/* interval for waiting for other communication side */
				8192,							/* buffer size */
				p_o_config.clientLocalIp,		/* sender address */
				0,								/* sender port */
				null							/* ssl context */
			);
			o_threadClient = new Thread(o_socketSend);
			
			o_threadClient.start();
			
			o_threadClient.join();
			
			if (o_socketSend != null) {
				o_socketSend.stop();
			}
			
			if (!net.forestany.forestj.lib.io.File.exists(s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "104857600_bytes.txt")) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			
				if (net.forestany.forestj.lib.io.File.folderExists(s_testDirectory)) {
					throw new Exception("directory[" + s_testDirectory + "] does exists");
				}
			}
		}
	}

	private static void netTCPSockHandshakeTest(NetConfig p_o_config) throws Exception {
		netTCPSockHandshakeTest(p_o_config, 10000);
	}
	
	private static void netTCPSockHandshakeTest(NetConfig p_o_config, int p_i_timeoutMilliseconds) throws Exception {
		if (p_o_config.isServer) { /* SERVER */
			Thread o_threadServer = null;
			net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<java.net.ServerSocket> o_socketReceive = null;
			net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.recv.HandshakeReceive<java.net.ServerSocket>(net.forestany.forestj.lib.net.sock.Type.TCP);
			o_socketReceive = new net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<java.net.ServerSocket>(
				java.net.ServerSocket.class,						/* class type */
				net.forestany.forestj.lib.net.sock.recv.ReceiveType.SOCKET,	/* socket type */
				p_o_config.serverIp,								/* receiving address */
				p_o_config.handshakePort,							/* receiving port */
				o_serverTask,										/* server task */
				p_i_timeoutMilliseconds,							/* timeout milliseconds */
				1,													/* max. number of executions */
				1500,												/* receive buffer size */
				null												/* ssl context */
			);
			o_threadServer = new Thread(o_socketReceive);
			o_threadServer.start();
			
			o_threadServer.join();
			
			if (o_socketReceive != null) {
				o_socketReceive.stop();
			}
		} else { /* CLIENT */
			Thread o_threadClient = null;
			net.forestany.forestj.lib.net.sock.send.SendTCP<java.net.Socket> o_socketSend = null;
			net.forestany.forestj.lib.net.sock.task.Task<java.net.Socket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.send.HandshakeSend<java.net.Socket>(net.forestany.forestj.lib.net.sock.Type.TCP);
			
			o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendTCP<java.net.Socket>(
				java.net.Socket.class,		/* class type */
				p_o_config.serverIp,		/* destination address */
				p_o_config.handshakePort,	/* destination port */
				o_clientTask,				/* client task */
				p_i_timeoutMilliseconds,	/* timeout milliseconds */
				false,						/* check if destination is reachable */
				1,							/* max. number of executions */
				25,							/* interval for waiting for other communication side */
				1500,						/* buffer size */
				null,						/* sender address */
				0,							/* sender port */
				null						/* ssl context */
			);
			o_threadClient = new Thread(o_socketSend);
			o_threadClient.start();
			
			o_threadClient.join();
			
			if (o_socketSend != null) {
				o_socketSend.stop();
			}
		}
	}

	private static void netCommunication(boolean p_b_falseUDPtrueTCP, boolean p_b_udpWithAck, NetConfig p_o_config) throws Exception {
		i_sleepMultiplier = p_o_config.sleepMultiplier;
		int i_comDequeueWaitLoopTimeout = 5000;
		int i_iterations = 10;
		
		if (p_o_config.isServer) { /* SERVER */
			try {
				java.util.List<String> a_serverLog = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE;
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK;
				}
				
				if (p_b_falseUDPtrueTCP) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE;
				}
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.serverIp, p_o_config.serverPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				for (int i = 0; i < i_iterations; i++) {
					if (p_b_falseUDPtrueTCP) {
						String s_message = (String)o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
						
						if (s_message != null) {
							net.forestany.forestj.lib.Global.ilogFine("#" + (i + 1) + " message(" + s_message.length() + ") received: '" + s_message + "'");
							a_serverLog.add("#" + (i + 1) + " message(" + s_message.length() + ") received");
						} else {
							net.forestany.forestj.lib.Global.ilogWarning("could not receive any data");
						}
					} else {
						java.util.Date o_date = (java.util.Date)o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
						
						if (o_date != null) {
							java.text.SimpleDateFormat o_sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss");    
							net.forestany.forestj.lib.Global.ilogFine("#" + (i + 1) + " message received: '" + o_sdf.format(o_date) + "'");
							a_serverLog.add("#" + (i + 1) + " message received");
						} else {
							net.forestany.forestj.lib.Global.ilogWarning("could not receive any data");
						}
					}
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				if (a_serverLog.size() != 10) {
					throw new Exception("server log has not '10' entries, but '" + a_serverLog.size() + "'");
				}
				
				if (p_b_falseUDPtrueTCP) {
					int i_expectedLength = 1438;
					
					if (p_o_config.marshallingDataLengthInBytes == 1) {
						i_expectedLength = 215;
					}
					
					for (int i = 0; i < 10; i++) {
						if (!a_serverLog.get(i).startsWith("#" + (i + 1) + " message(" + ((i == 9) ? (i_expectedLength + 1) : i_expectedLength) + ") received")) {
							throw new Exception("server log entry does not start with '#" + (i + 1) + " message(" + ((i == 9) ? (i_expectedLength + 1) : i_expectedLength) + ") received:', but with '" + a_serverLog.get(i) + "'");
						}
					}
				} else {
					for (int i = 0; i < 10; i++) {
						if (!a_serverLog.get(i).startsWith("#" + (i + 1) + " message received")) {
							throw new Exception("server log entry does not start with '#" + (i + 1) + " message received:', but with '" + a_serverLog.get(i) + "'");
						}
					}
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		} else { /* CLIENT */
			try {
				java.util.List<String> a_clientLog = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND;
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK;
				}
				
				if (p_b_falseUDPtrueTCP) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND;
				}
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.clientIp, p_o_config.clientPort, ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) ? p_o_config.clientLocalIp : null, ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) ? p_o_config.clientLocalPort : 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				for (int i = 0; i < i_iterations; i++) {
					if (p_b_falseUDPtrueTCP) {
						String s_foo = (i + 1) + ": Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.";
						
						if (p_o_config.marshallingDataLengthInBytes == 1) {
							s_foo = (i + 1) + ": Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.";
						}
						
						while ( !o_communication.enqueue(
							s_foo
						) ) {
							net.forestany.forestj.lib.Global.ilogWarning("could not enqueue message");
						}
					} else {
						while ( !o_communication.enqueue(
							new java.util.Date( System.currentTimeMillis() )
						) ) {
							net.forestany.forestj.lib.Global.ilogWarning("could not enqueue message");
						}
					}
					
					net.forestany.forestj.lib.Global.ilogFine("message enqueued");
					a_clientLog.add("message enqueued");
					
					if (i == 4) { /* additional delay for 25 milliseconds times sleep multiplier constant, after the 5th time enqueue has been executed */
						Thread.sleep(25 * i_sleepMultiplier);
					}
					
					Thread.sleep(25 * i_sleepMultiplier + ( ((!p_b_falseUDPtrueTCP) && (p_o_config.highSecurity)) ? 150 : 0 ) + ( ((p_b_falseUDPtrueTCP) && ((p_o_config.highSecurity) || (p_o_config.asymmetricSecurity))) ? 25 : 0 ));
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				if (a_clientLog.size() != 10) {
					throw new Exception("client log has not '10' entries, but '" + a_clientLog.size() + "'");
				}
				
				if (p_b_falseUDPtrueTCP) {
					for (int i = 0; i < 10; i++) {
						if (!a_clientLog.get(i).contentEquals("message enqueued")) {
							throw new Exception("client log entry does not match with 'message enqueued', but is '" + a_clientLog.get(i) + "'");
						}
					}
				} else {
					for (int i = 0; i < 10; i++) {
						if (!a_clientLog.get(i).contentEquals("message enqueued")) {
							throw new Exception("client log entry does not match with 'message enqueued', but is '" + a_clientLog.get(i) + "'");
						}
					}
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		}
	}
	
	private static void netCommunicationUDPMulticast(NetConfig p_o_config, boolean p_b_noAutomatic) throws Exception {
		i_sleepMultiplier = p_o_config.sleepMultiplier;
		int i_comDequeueWaitLoopTimeout = 5000;
		int i_iterations = 10;
		
		if (!p_o_config.isServer) { /* CLIENT */
			try {
				java.util.List<String> a_clientLog = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_RECEIVER;
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.serverIp, p_o_config.serverPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				o_communicationConfig.setUDPMulticastReceiverNetworkInterfaceName(p_o_config.udpMulticastNetworkInterfaceName);
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				for (int i = 0; i < i_iterations; i++) {
					String s_ip = (String)o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
					
					if (s_ip != null) {
						if (!p_b_noAutomatic) {
							net.forestany.forestj.lib.Global.ilogFine("#" + (i + 1) + " message received: '" + s_ip + "'");
							a_clientLog.add("#" + (i + 1) + " message received");
						} else {
							net.forestany.forestj.lib.Global.ilog("#" + (i + 1) + " message received: '" + s_ip + "'");
						}
					} else {
						net.forestany.forestj.lib.Global.ilogWarning("could not receive any data");
					}
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				if (!p_b_noAutomatic) {
					if (a_clientLog.size() != 10) {
						throw new Exception("client log has not '10' entries, but '" + a_clientLog.size() + "'");
					}
					
					for (int i = 0; i < 10; i++) {
						if (!a_clientLog.get(i).startsWith("#" + (i + 1) + " message received")) {
							throw new Exception("client log entry does not start with '#" + (i + 1) + " message received:', but with '" + a_clientLog.get(i) + "'");
						}
					}
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		} else { /* SERVER */
			try {
				java.util.List<String> a_serverLog = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_SENDER;
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.clientIp, p_o_config.clientPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				o_communicationConfig.setUDPMulticastSenderTTL(p_o_config.udpMulticastTTL);
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				String s_ip = "COULD_NOT_GET_DEFAULT_IP";
                java.util.List<java.util.AbstractMap.SimpleEntry<String, String>> a_ips = net.forestany.forestj.lib.Helper.GetNetworkInterfacesIpv4();
                
                if (net.forestany.forestj.lib.Helper.isIpv4Address(p_o_config.clientIp)) {
                	a_ips = net.forestany.forestj.lib.Helper.GetNetworkInterfacesIpv4();
                } else if (net.forestany.forestj.lib.Helper.isIpv6Address(p_o_config.clientIp)) {
                	a_ips = net.forestany.forestj.lib.Helper.GetNetworkInterfacesIpv6();
                }

                if (a_ips.size() > 0)
                {
                    s_ip = a_ips.get(0).getValue();
                }
				
				for (int i = 0; i < i_iterations; i++) {
					while ( !o_communication.enqueue(
						"Join the server|" + s_ip
					) ) {
						net.forestany.forestj.lib.Global.ilogWarning("could not enqueue message");
					}
					
					if (!p_b_noAutomatic) {
						net.forestany.forestj.lib.Global.ilogFine("message enqueued");
						a_serverLog.add("message enqueued");
					} else {
						net.forestany.forestj.lib.Global.ilog("message enqueued: '" + "Join the server|" + s_ip + "'");
					}
					
					if (i == 4) { /* additional delay for 25 milliseconds times sleep multiplier constant, after the 5th time enqueue has been executed */
						Thread.sleep(25 * i_sleepMultiplier);
					}
					
					Thread.sleep(25 * i_sleepMultiplier + ((p_o_config.highSecurity) ? 150 : 0));
					
					if (p_b_noAutomatic) {
					    Thread.sleep(2500);
					}
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				if (!p_b_noAutomatic) {
					if (a_serverLog.size() != 10) {
						throw new Exception("server log has not '10' entries, but '" + a_serverLog.size() + "'");
					}
					
					for (int i = 0; i < 10; i++) {
						if (!a_serverLog.get(i).contentEquals("message enqueued")) {
							throw new Exception("server log entry does not match with 'message enqueued', but is '" + a_serverLog.get(i) + "'");
						}
					}
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		}
	}
		
	private static void netCommunicationObjectTransmissionTCP(NetConfig p_o_config) throws Exception {
		i_sleepMultiplier = p_o_config.sleepMultiplier;
		int i_comDequeueWaitLoopTimeout = 5000;
		int i_iterations = 10;
		
		if (p_o_config.isServer) { /* SERVER */
			try {
				java.util.List<String> a_serverLog = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.serverIp, p_o_config.serverPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				o_communicationConfig.setObjectTransmission(true);
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				for (int i = 0; i < i_iterations; i++) {
					String s_message = (String)o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
					
					if (s_message != null) {
						net.forestany.forestj.lib.Global.ilogFine("#" + (i + 1) + " message(" + s_message.length() + ") received: '" + s_message + "'");
						a_serverLog.add("#" + (i + 1) + " message(" + s_message.length() + ") received");
					} else {
						net.forestany.forestj.lib.Global.ilogWarning("could not receive any data");
					}
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				if (a_serverLog.size() != 10) {
					throw new Exception("server log has not '10' entries, but '" + a_serverLog.size() + "'");
				}
				
				int i_expectedLength = 4308;
				
				if (p_o_config.marshallingDataLengthInBytes == 1) {
					i_expectedLength = 254;
				}
				
				for (int i = 0; i < 10; i++) {
					if (!a_serverLog.get(i).startsWith("#" + (i + 1) + " message(" + ((i == 9) ? (i_expectedLength + 1) : i_expectedLength) + ") received")) {
						throw new Exception ("server log entry does not start with '#" + (i + 1) + " message(" + ((i == 9) ? (i_expectedLength + 1) : i_expectedLength) + ") received:', but with '" + a_serverLog.get(i) + "'");
					}
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		} else { /* CLIENT */
			try {
				java.util.List<String> a_clientLog = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.clientIp, p_o_config.clientPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				o_communicationConfig.setObjectTransmission(true);
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				for (int i = 0; i < i_iterations; i++) {
					String s_foo = (i + 1) + ": Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.";
					
					if (p_o_config.marshallingDataLengthInBytes == 1) {
						s_foo = (i + 1) + ": Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum, sed diam nonumy eirmod tempor invidun.";
					}
					
					while ( !o_communication.enqueue(
						s_foo
					) ) {
						net.forestany.forestj.lib.Global.ilogWarning("could not enqueue message");
					}
					
					net.forestany.forestj.lib.Global.ilogFine("message enqueued");
					a_clientLog.add("message enqueued");
					
					if (i == 4) { /* additional delay for 25 milliseconds times sleep multiplier constant, after the 5th time enqueue has been executed */
						Thread.sleep(25 * i_sleepMultiplier);
					}
					
					Thread.sleep(25 * i_sleepMultiplier + ( (p_o_config.highSecurity || p_o_config.asymmetricSecurity) ? 25 : 0 ));
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				if (a_clientLog.size() != 10) {
					throw new Exception("client log has not '10' entries, but '" + a_clientLog.size() + "'");
				}
				
				for (int i = 0; i < 10; i++) {
					if (!a_clientLog.get(i).contentEquals("message enqueued")) {
						throw new Exception ("client log entry does not match with 'message enqueued', but is '" + a_clientLog.get(i) + "'");
					}
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		}
	}

	private static void netCommunicationWithAnswerOneTCP(NetConfig p_o_config) throws Exception {
		i_sleepMultiplier = p_o_config.sleepMultiplier;
		int i_comDequeueWaitLoopTimeout = 5000;
		int i_iterations = 10;
		
		if (p_o_config.isServer) { /* SERVER */
			try {
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE_WITH_ANSWER, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.serverIp, p_o_config.serverPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				
				/* add receive socket task(s) */
				net.forestany.forestj.lib.net.sock.task.Task<?> o_receiveSocketTask = null;
				
				if (p_o_config.asymmetricSecurity) {
					o_receiveSocketTask = new net.forestany.forestj.lib.net.sock.task.Task<javax.net.ssl.SSLServerSocket>(net.forestany.forestj.lib.net.sock.Type.TCP) {
						public Class<?> getSocketTaskClassType() {
							return net.forestany.forestj.lib.net.sock.task.Task.class;
						}
						
						public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<javax.net.ssl.SSLServerSocket> p_o_sourceTask) {
							this.cloneBasicFields(p_o_sourceTask);
						}
						
						protected void runTask() throws Exception {
							try {
								/* get objects of object list we we want to use them */
								if (this.a_objects.size() != 2) {
									throw new Exception("Objects for answer task are not available, size[" + this.a_objects.size() + "] is not [2]");
								}
								
								/* get '<answer>' from instantiation */
								String s_pre = (String)this.a_objects.get(0);
								/* get '</answer>' from instantiation */
								String s_post = (String)this.a_objects.get(1);
								
								/* get request object as integer value */
								int i_request = (Integer)this.getRequestObject();
								
								/* handle request object */
								String s_answer = null;
								
								switch (i_request) {
									case 1:
										s_answer = "one";
										break;
									case 2:
										s_answer = "two";
										break;
									case 3:
										s_answer = "three";
										break;
									case 4:
										s_answer = "four";
										break;
									case 5:
										s_answer = "five";
										break;
									case 6:
										s_answer = "six";
										break;
									case 7:
										s_answer = "seven";
										break;
									case 8:
										s_answer = "eight";
										break;
									case 9:
										s_answer = "nine";
										break;
									case 10:
										s_answer = "ten";
										break;
									default:
										s_answer = "";
										break;
								}
								
								/* set answer object */
								this.setAnswerObject(s_pre + s_answer + s_post);
							} catch (Exception o_exc) {
								net.forestany.forestj.lib.Global.logException(o_exc);
							}
						}
					};
				} else {
					o_receiveSocketTask = new net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket>(net.forestany.forestj.lib.net.sock.Type.TCP) {
						public Class<?> getSocketTaskClassType() {
							return net.forestany.forestj.lib.net.sock.task.Task.class;
						}
						
						public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket> p_o_sourceTask) {
							this.cloneBasicFields(p_o_sourceTask);
						}
						
						protected void runTask() throws Exception {
							try {
								/* get objects of object list we we want to use them */
								if (this.a_objects.size() != 2) {
									throw new Exception("Objects for answer task are not available, size[" + this.a_objects.size() + "] is not [2]");
								}
								
								/* get '<answer>' from instantiation */
								String s_pre = (String)this.a_objects.get(0);
								/* get '</answer>' from instantiation */
								String s_post = (String)this.a_objects.get(1);
								
								/* get request object as integer value */
								int i_request = (Integer)this.getRequestObject();
								
								/* handle request object */
								String s_answer = null;
								
								switch (i_request) {
									case 1:
										s_answer = "one";
										break;
									case 2:
										s_answer = "two";
										break;
									case 3:
										s_answer = "three";
										break;
									case 4:
										s_answer = "four";
										break;
									case 5:
										s_answer = "five";
										break;
									case 6:
										s_answer = "six";
										break;
									case 7:
										s_answer = "seven";
										break;
									case 8:
										s_answer = "eight";
										break;
									case 9:
										s_answer = "nine";
										break;
									case 10:
										s_answer = "ten";
										break;
									default:
										s_answer = "";
										break;
								}
								
								/* set answer object */
								this.setAnswerObject(s_pre + s_answer + s_post);
							} catch (Exception o_exc) {
								net.forestany.forestj.lib.Global.logException(o_exc);
							}
						}
					};
				}
				
				o_receiveSocketTask.addObject("<answer>");
				o_receiveSocketTask.addObject("</answer>");
				
				o_communicationConfig.addReceiveSocketTask(o_receiveSocketTask);
				
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				Thread.sleep(25 * i_sleepMultiplier * (i_iterations / 2) + ( (p_o_config.highSecurity) ? 3000 : 0 ));
				
				if (o_communication != null) {
					o_communication.stop();
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		} else { /* CLIENT */
			try {
				java.util.List<String> a_serverLog = new java.util.ArrayList<String>();
				java.util.List<String> a_clientLog = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND_WITH_ANSWER, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.clientIp, p_o_config.clientPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				for (int i = 0; i < i_iterations; i++) {
					while ( !o_communication.enqueue(
						Integer.valueOf(i + 1)
					) ) {
						net.forestany.forestj.lib.Global.ilogWarning("could not enqueue message");
					}
					
					net.forestany.forestj.lib.Global.ilogFine("message enqueued");
					a_clientLog.add("message enqueued");
					
					Object o_answer = o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
					
					if (o_answer != null) {
						net.forestany.forestj.lib.Global.ilogFine("#" + (i + 1) + " message(" + o_answer.toString().length() + ") received: '" + o_answer.toString() + "'");
						a_serverLog.add("#" + (i + 1) + " message(" + o_answer.toString().length() + ") received");
					} else {
						net.forestany.forestj.lib.Global.ilogWarning("could not receive any answer data");
					}
					
					if (i == 4) { /* additional delay for 25 milliseconds times sleep multiplier constant, after the 5th time enqueue has been executed */
						Thread.sleep(25 * i_sleepMultiplier);
					}
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				if (a_serverLog.size() != 10) {
					throw new Exception("server log has not '10' entries, but '" + a_serverLog.size() + "'");
				}
				
				if (a_clientLog.size() != 10) {
					throw new Exception("client log has not '10' entries, but '" + a_clientLog.size() + "'");
				}
				
				java.util.List<Integer> a_expectedLength = java.util.Arrays.asList(20, 20, 22, 21, 21, 20, 22, 22, 21, 20);
				
				for (int i = 0; i < 10; i++) {
					if (!a_serverLog.get(i).startsWith("#" + (i + 1) + " message(" + a_expectedLength.get(i) + ") received")) {
						throw new Exception("server log entry does not start with '#" + (i + 1) + " message(" + a_expectedLength.get(i) + ") received:', but with '" + a_serverLog.get(i) + "'");
					}
					
					if (!a_clientLog.get(i).contentEquals("message enqueued")) {
						throw new Exception("client log entry does not match with 'message enqueued', but is '" + a_clientLog.get(i) + "'");
					}
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		}
	}
	
	private static void netCommunicationWithAnswerTwoTCP(NetConfig p_o_config) throws Exception {
		i_sleepMultiplier = p_o_config.sleepMultiplier;
		int i_comDequeueWaitLoopTimeout = 5000;
		int i_iterations = 14;
		
		if (p_o_config.isServer) { /* SERVER */
			try {
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE_WITH_ANSWER, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.serverIp, p_o_config.serverPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				
				/* add receive socket task(s) */
				net.forestany.forestj.lib.net.sock.task.Task<?> o_receiveSocketTask = null;
				
				if (p_o_config.asymmetricSecurity) {
					o_receiveSocketTask = new net.forestany.forestj.lib.net.sock.task.Task<javax.net.ssl.SSLServerSocket>(net.forestany.forestj.lib.net.sock.Type.TCP) {
						public Class<?> getSocketTaskClassType() {
							return net.forestany.forestj.lib.net.sock.task.Task.class;
						}
						
						public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<javax.net.ssl.SSLServerSocket> p_o_sourceTask) {
							this.cloneBasicFields(p_o_sourceTask);
						}
						
						protected void runTask() throws Exception {
							try {
								/* get objects of object list we we want to use them */
								if (this.a_objects.size() != 2) {
									throw new Exception("Objects for answer task are not available, size[" + this.a_objects.size() + "] is not [2]");
								}
								
								/* get '<answer>' from instantiation */
								String s_pre = (String)this.a_objects.get(0);
								/* get '</answer>' from instantiation */
								String s_post = (String)this.a_objects.get(1);
								
								/* get request object */
								java.time.LocalDate o_request = (java.time.LocalDate)this.getRequestObject();
								
								/* set answer object */
								
								if (o_request != null) {
									this.setAnswerObject(s_pre + o_request.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")) + s_post);
								} else { 
									this.setAnswerObject(s_pre + s_post);
								}
							} catch (Exception o_exc) {
								net.forestany.forestj.lib.Global.logException(o_exc);
							}
						}
					};
				} else {
					o_receiveSocketTask = new net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket>(net.forestany.forestj.lib.net.sock.Type.TCP) {
						public Class<?> getSocketTaskClassType() {
							return net.forestany.forestj.lib.net.sock.task.Task.class;
						}
						
						public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket> p_o_sourceTask) {
							this.cloneBasicFields(p_o_sourceTask);
						}
						
						protected void runTask() throws Exception {
							try {
								/* get objects of object list we we want to use them */
								if (this.a_objects.size() != 2) {
									throw new Exception("Objects for answer task are not available, size[" + this.a_objects.size() + "] is not [2]");
								}
								
								/* get '<answer>' from instantiation */
								String s_pre = (String)this.a_objects.get(0);
								/* get '</answer>' from instantiation */
								String s_post = (String)this.a_objects.get(1);
								
								/* get request object */
								java.time.LocalDate o_request = (java.time.LocalDate)this.getRequestObject();
								
								/* set answer object */
								
								if (o_request != null) {
									this.setAnswerObject(s_pre + o_request.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")) + s_post);
								} else { 
									this.setAnswerObject(s_pre + s_post);
								}
							} catch (Exception o_exc) {
								net.forestany.forestj.lib.Global.logException(o_exc);
							}
						}
					};
				}
				
				o_receiveSocketTask.addObject("<answer>");
				o_receiveSocketTask.addObject("</answer>");
				
				o_communicationConfig.addReceiveSocketTask(o_receiveSocketTask);
				
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				Thread.sleep(25 * i_sleepMultiplier * (i_iterations / 2) + ( (p_o_config.highSecurity) ? 4000 : 0 ));
				
				if (o_communication != null) {
					o_communication.stop();
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		} else { /* CLIENT */
			try {
				java.util.List<String> a_serverLog = new java.util.ArrayList<String>();
				java.util.List<String> a_clientLog = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND_WITH_ANSWER, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.clientIp, p_o_config.clientPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				for (int i = 0; i < i_iterations; i++) {
					while ( !o_communication.enqueue(
						java.time.LocalDate.now().plusDays(i)
					) ) {
						net.forestany.forestj.lib.Global.ilogWarning("could not enqueue message");
					}
					
					net.forestany.forestj.lib.Global.ilogFine("message enqueued");
					a_clientLog.add("message enqueued");
					
					Object o_answer = o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
					
					if (o_answer != null) {
						net.forestany.forestj.lib.Global.ilogFine("#" + (i + 1) + " message(" + o_answer.toString().length() + ") received: '" + o_answer.toString() + "'");
						a_serverLog.add("#" + (i + 1) + " message(" + o_answer.toString().length() + ") received - " + java.time.LocalDate.now().plusDays(i).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
					} else {
						net.forestany.forestj.lib.Global.ilogWarning("could not receive any answer data");
					}
					
					if (i == 6) { /* additional delay for 25 milliseconds times sleep multiplier constant, after the 7th time enqueue has been executed */
						Thread.sleep(25 * i_sleepMultiplier);
					}
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				if (a_serverLog.size() != 14) {
					throw new Exception("server log has not '14' entries, but '" + a_serverLog.size() + "'");
				}
				
				if (a_clientLog.size() != 14) {
					throw new Exception("client log has not '14' entries, but '" + a_clientLog.size() + "'");
				}
				
				int i_expectedLength = 27;
				
				for (int i = 0; i < 14; i++) {
					if (!a_serverLog.get(i).startsWith("#" + (i + 1) + " message(" + i_expectedLength + ") received")) {
						throw new Exception("server log entry does not start with '#" + (i + 1) + " message(" + i_expectedLength + ") received:', but with '" + a_serverLog.get(i) + "'");
					}
					
					String s_foo = java.time.LocalDate.now().plusDays(i).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					
					if (!a_serverLog.get(i).contains(s_foo)) {
						throw new Exception("server log entry does not contain '" + s_foo + "', entry value: '" + a_serverLog.get(i) + "'");
					}
					
					if (!a_clientLog.get(i).contentEquals("message enqueued")) {
						throw new Exception("client log entry does not match with 'message enqueued', but is '" + a_clientLog.get(i) + "'");
					}
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		}
	}
	
	private static void netCommunicationSharedMemoryUniDirectional(boolean p_b_falseUDPtrueTCP, boolean p_b_udpWithAck, NetConfig p_o_config) throws Exception {
		i_sleepMultiplier = p_o_config.sleepMultiplier;
		java.util.List<String> a_expectedResults = new java.util.ArrayList<String>();
		
		/* expected result */
		String s_expectedResult = "Id = 42|UUID = a8dfc91d-ec7e-4a5f-9a9c-243edd91e271|ShortText = NULL|Text = Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.|SmallInt = 0|Int = 21|BigInt = 546789546|Timestamp = NULL|Date = NULL|Time = NULL|LocalDateTime = NULL|LocalDate = 2003-03-03|LocalTime = NULL|DoubleCol = 1.2345|Decimal = NULL|Bool = true|Text2 = NULL|ShortText2 = NULL|FloatValue = 0.0|";
		
		for (String s_foo : s_expectedResult.split("\\|")) {
			a_expectedResults.add(s_foo);
		}
		
		if (p_o_config.isServer) { /* SERVER */
			try {
				java.util.List<String> a_serverResults = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE;
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK;
				}
				
				if (p_b_falseUDPtrueTCP) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE;
				}
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_o_config.serverIp, p_o_config.serverPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				
				net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryExample o_sharedMemoryExample = new net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryExample();
				o_sharedMemoryExample.initiateMirrors();
				o_communicationConfig.setSharedMemory(o_sharedMemoryExample);
				o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
				
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				int i_additionalTime = 25;
				
				if (p_o_config.highSecurity) {
					if (p_b_falseUDPtrueTCP) {
						i_additionalTime = 60;
					} else {
						i_additionalTime = 90;	
					}
				}
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					i_additionalTime += 225;
				}
				
				if ( (p_b_falseUDPtrueTCP) && (p_o_config.asymmetricSecurity) ) {
					i_additionalTime = 45;
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 1));
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 4));

				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 6));
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 3));
				
				/* server result */
				for (String s_foo : o_sharedMemoryExample.returnFields().split("\\|")) {
					a_serverResults.add(s_foo);
				}
									
				if (o_communication != null) {
					o_communication.stop();
				}
				
				if (a_expectedResults.size() != a_serverResults.size()) {
					throw new Exception("server result has not the expected amount of fields '" + a_expectedResults.size() + "!='" + a_serverResults.size());
				}
				
				int i_missingFieldsServer = 0;
				
				for (int i = 0; i < a_expectedResults.size(); i++) {
					if (!a_expectedResults.get(i).contentEquals(a_serverResults.get(i))) {
						i_missingFieldsServer++;
						net.forestany.forestj.lib.Global.ilogFiner("server field result not equal expected result:\t" + a_serverResults.get(i) + "\t != \t" + a_expectedResults.get(i));
					}
				}
				
				if (i_missingFieldsServer > 0) {
					net.forestany.forestj.lib.Global.ilog("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " server missing fields: " + i_missingFieldsServer);
				} else {
					net.forestany.forestj.lib.Global.ilogFine("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " server missing fields: fine");
				}
				
				if (i_missingFieldsServer > 5) {
					throw new Exception(i_missingFieldsServer + " server fields not matching expected values, which is greater than '5'");
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		} else { /* CLIENT */
			try {
				java.util.List<String> a_clientResults = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND;
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK;
				}
				
				if (p_b_falseUDPtrueTCP) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND;
				}
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_o_config.clientIp, p_o_config.clientPort, ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) ? p_o_config.clientLocalIp : null, ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) ? p_o_config.clientLocalPort : 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				
				net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryExample o_sharedMemoryExample = new net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryExample();
				o_sharedMemoryExample.initiateMirrors();
				o_communicationConfig.setSharedMemory(o_sharedMemoryExample);
				o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
				
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
		
				int i_additionalTime = 25;
				
				if (p_o_config.highSecurity) {
					if (p_b_falseUDPtrueTCP) {
						i_additionalTime = 60;
					} else {
						i_additionalTime = 90;	
					}
				}
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					i_additionalTime += 225;
				}
				
				if ( (p_b_falseUDPtrueTCP) && (p_o_config.asymmetricSecurity) ) {
					i_additionalTime = 45;
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 1));
				
				o_sharedMemoryExample.setField("Text", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
				o_sharedMemoryExample.setField("LocalDate", java.time.LocalDate.of(2003, 3, 3) );
				o_sharedMemoryExample.setField("Int", 13579);
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 4));
				
				o_sharedMemoryExample.setField("Id", 42);
				o_sharedMemoryExample.setField("UUID", "a8dfc91d-ec7e-4a5f-9a9c-243edd91e271");
				o_sharedMemoryExample.setField("Text", "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
				o_sharedMemoryExample.setField("DoubleCol", 1.2345d);
				o_sharedMemoryExample.setField("Bool", true);
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 6));
				
				o_sharedMemoryExample.setField("Int", 21);
				o_sharedMemoryExample.setField("BigInt", 546789546L);
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 3));
				
				/* client result */
				for (String s_foo : o_sharedMemoryExample.returnFields().split("\\|")) {
					a_clientResults.add(s_foo);
				}
									
				if (o_communication != null) {
					o_communication.stop();
				}
				
				if (a_expectedResults.size() != a_clientResults.size()) {
					throw new Exception("client result has not the expected amount of fields '" + a_expectedResults.size() + "!='" + a_clientResults.size());
				}
				
				int i_missingFieldsClient = 0;
				
				for (int i = 0; i < a_expectedResults.size(); i++) {
					if (!a_expectedResults.get(i).contentEquals(a_clientResults.get(i))) {
						i_missingFieldsClient++;
						net.forestany.forestj.lib.Global.ilogFiner("client field result not equal expected result:\t" + a_clientResults.get(i) + "\t != \t" + a_expectedResults.get(i));
					}
				}
				
				if (i_missingFieldsClient > 5) {
					throw new Exception (i_missingFieldsClient + " client fields not matching expected values, which is greater than '5'");
				}
				
				if (i_missingFieldsClient > 0) {
					net.forestany.forestj.lib.Global.ilog("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " client missing fields: " + i_missingFieldsClient);
				} else {
					net.forestany.forestj.lib.Global.ilogFine("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " client missing fields: fine");
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		}
	}
	
	private static void netCommunicationSharedMemoryBiDirectional(boolean p_b_falseUDPtrueTCP, boolean p_b_udpWithAck, NetConfig p_o_config) throws Exception {
		i_sleepMultiplier = p_o_config.sleepMultiplier;
		java.util.List<String> a_expectedResults = new java.util.ArrayList<String>();
		
		/* expected result */
		String s_expectedResult = "Id = 42|UUID = 26cf332e-3f23-4523-9911-60207c8db7fd|ShortText = NULL|Text = Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum.|SmallInt = 0|Int = 50791|BigInt = 0|Timestamp = NULL|Date = NULL|Time = NULL|LocalDateTime = NULL|LocalDate = 2004-04-04|LocalTime = NULL|DoubleCol = 5.4321|Decimal = NULL|Bool = false|Text2 = At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.|ShortText2 = Mission accomplished.|FloatValue = 2.114014|";
		
		for (String s_foo : s_expectedResult.split("\\|")) {
			a_expectedResults.add(s_foo);
		}
						
		if (p_o_config.isServer) { /* SERVER */
			try {
				java.util.List<String> a_serverResults = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE;
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK;
				}
				
				if (p_b_falseUDPtrueTCP) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE;
				}
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_o_config.serverIp, p_o_config.serverPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, p_b_falseUDPtrueTCP, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				
				net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryExample o_sharedMemoryExample = new net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryExample();
				o_sharedMemoryExample.initiateMirrors();
				o_communicationConfig.setSharedMemory(o_sharedMemoryExample);
				o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
				
				o_communicationConfig.setSharedMemoryBidirectional(
					java.util.Arrays.asList(
						new java.util.AbstractMap.SimpleEntry<String,Integer>(p_o_config.serverBiIp, p_o_config.serverBiPort)
					),
					(p_b_falseUDPtrueTCP) ? o_communicationConfig.getSSLContextList() : null
				);
				
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				int i_additionalTime = 25;
				
				if (p_o_config.highSecurity) {
					if (p_b_falseUDPtrueTCP) {
						i_additionalTime = 60;
					} else {
						i_additionalTime = 90;
					}
				}
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					i_additionalTime += 225;
				}
				
				if ( (p_b_falseUDPtrueTCP) && (p_o_config.asymmetricSecurity) ) {
					i_additionalTime = 45;
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 1));
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 4));
				
				o_sharedMemoryExample.setField("Int", 50791);
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 2));
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 6));
				
				o_sharedMemoryExample.setField("Id", 42);
				o_sharedMemoryExample.setField("ShortText2", "Mission accomplished.");
				o_sharedMemoryExample.setField("Text2", "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
				o_sharedMemoryExample.setField("FloatValue", 2.114014f);
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 5));
				
				/* server result */
				for (String s_foo : o_sharedMemoryExample.returnFields().split("\\|")) {
					a_serverResults.add(s_foo);
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				if (a_expectedResults.size() != a_serverResults.size()) {
					throw new Exception("server result has not the expected amount of fields '" + a_expectedResults.size() + "!='" + a_serverResults.size());
				}
				
				int i_missingFieldsServer = 0;
				
				for (int i = 0; i < a_expectedResults.size(); i++) {
					if (!a_expectedResults.get(i).contentEquals(a_serverResults.get(i))) {
						i_missingFieldsServer++;
						net.forestany.forestj.lib.Global.ilogFiner("server field result not equal expected result:\t" + a_serverResults.get(i) + "\t != \t" + a_expectedResults.get(i));
					}
				}
				
				if (i_missingFieldsServer > 0) {
					net.forestany.forestj.lib.Global.ilog("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " server missing fields: " + i_missingFieldsServer);
				} else {
					net.forestany.forestj.lib.Global.ilogFine("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " server missing fields: fine");
				}
				
				if (i_missingFieldsServer > 4) {
					throw new Exception(i_missingFieldsServer + " server fields not matching expected values, which is greater than '4'");
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		} else { /* CLIENT */
			try {
				java.util.List<String> a_clientResults = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND;
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK;
				}
				
				if (p_b_falseUDPtrueTCP) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND;
				}
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_o_config.clientIp, p_o_config.clientPort, ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) ? p_o_config.clientLocalIp : null, ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) ? p_o_config.clientLocalPort : 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, p_b_falseUDPtrueTCP, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				
				net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryExample o_sharedMemoryExample = new net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryExample();
				o_sharedMemoryExample.initiateMirrors();
				o_communicationConfig.setSharedMemory(o_sharedMemoryExample);
				o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
				
				o_communicationConfig.setSharedMemoryBidirectional(
					java.util.Arrays.asList(
						new java.util.AbstractMap.SimpleEntry<String,Integer>(p_o_config.clientBiIp, p_o_config.clientBiPort)
					),
					(p_b_falseUDPtrueTCP) ? o_communicationConfig.getSSLContextList() : null
				);
				
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
		
				int i_additionalTime = 25;
				
				if (p_o_config.highSecurity) {
					if (p_b_falseUDPtrueTCP) {
						i_additionalTime = 60;
					} else {
						i_additionalTime = 90;
					}
				}
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					i_additionalTime += 225;
				}
				
				if ( (p_b_falseUDPtrueTCP) && (p_o_config.asymmetricSecurity) ) {
					i_additionalTime = 45;
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 1));
				
				o_sharedMemoryExample.setField("Text", "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.");
				o_sharedMemoryExample.setField("LocalDate", java.time.LocalDate.of(2004, 4, 4) );
				o_sharedMemoryExample.setField("Int", 24680);
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 4));
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 2));
				
				o_sharedMemoryExample.setField("Id", 21);
				o_sharedMemoryExample.setField("UUID", "26cf332e-3f23-4523-9911-60207c8db7fd");
				o_sharedMemoryExample.setField("Text", "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum.");
				o_sharedMemoryExample.setField("DoubleCol", 5.4321d);
				o_sharedMemoryExample.setField("Bool", false);
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 6));
				
				Thread.sleep((25 * i_sleepMultiplier) + (i_additionalTime * 5));
				
				/* client result */
				for (String s_foo : o_sharedMemoryExample.returnFields().split("\\|")) {
					a_clientResults.add(s_foo);
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				if (a_expectedResults.size() != a_clientResults.size()) {
					throw new Exception("client result has not the expected amount of fields '" + a_expectedResults.size() + "!='" + a_clientResults.size());
				}
				
				int i_missingFieldsClient = 0;
				
				for (int i = 0; i < a_expectedResults.size(); i++) {
					if (!a_expectedResults.get(i).contentEquals(a_clientResults.get(i))) {
						i_missingFieldsClient++;
						net.forestany.forestj.lib.Global.ilogFiner("client field result not equal expected result:\t" + a_clientResults.get(i) + "\t != \t" + a_expectedResults.get(i));
					}
				}
				
				if (i_missingFieldsClient > 0) {
					net.forestany.forestj.lib.Global.ilog("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " client missing fields: " + i_missingFieldsClient);
				} else {
					net.forestany.forestj.lib.Global.ilogFine("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " client missing fields: fine");
				}
				
				if (i_missingFieldsClient > 4) {
					throw new Exception (i_missingFieldsClient + " client fields not matching expected values, which is greater than '4'");
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		}
	}
	
	private static void netCommunicationMarshallingObject(boolean p_b_falseUDPtrueTCP, boolean p_b_udpWithAck, boolean p_b_smallObject, NetConfig p_o_config) throws Exception {
		i_sleepMultiplier = p_o_config.sleepMultiplier;
		int i_comDequeueWaitLoopTimeout = 5000;
		int i_iterations = 10;
		
		if (p_o_config.isServer) {/* SERVER */
			try {
				java.util.List<Object> a_serverObjects = new java.util.ArrayList<Object>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE;
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK;
				}
				
				if (p_b_falseUDPtrueTCP) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE;
				}
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.serverIp, p_o_config.serverPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, true, false, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				
				if (p_b_falseUDPtrueTCP) {
					o_communicationConfig.setObjectTransmission(true);
				}
				
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				for (int i = 0; i < i_iterations; i++) {
					Object o_object = o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
					
					if (o_object != null) {
						if (!p_b_smallObject) {
							/* correct the small deviation with string null and empty string - empty strings are always interpreted as null */
							((net.forestany.forestj.lib.test.nettest.msg.MessageObject)o_object).getStringArray()[5] = "";
							((net.forestany.forestj.lib.test.nettest.msg.MessageObject)o_object).getStringList().set(5, "");
						}
						
						a_serverObjects.add(o_object);
						net.forestany.forestj.lib.Global.ilogFine("#" + (i + 1) + " object received");
					} else {
						net.forestany.forestj.lib.Global.ilogWarning("could not receive any data");
					}
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				Object o_foo = null;
				
				if (p_b_smallObject) {
					o_foo = new net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject();
					((net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject)o_foo).initAll();
				} else {
					o_foo = new net.forestany.forestj.lib.test.nettest.msg.MessageObject();
					((net.forestany.forestj.lib.test.nettest.msg.MessageObject)o_foo).initAll();
				}
				
				/* strip trailing zeroes for decimal, decimal array and decimal list */
				for (int i = 0; i < a_serverObjects.size(); i++) {
					if (!p_b_smallObject) {
						net.forestany.forestj.lib.test.nettest.msg.MessageObject o_fooo = (net.forestany.forestj.lib.test.nettest.msg.MessageObject) a_serverObjects.get(i);
						
						if ( (o_fooo.getDecimal() != null) && (o_fooo.getDecimal() != new java.math.BigDecimal(0d)) ) {
							o_fooo.setDecimal(o_fooo.getDecimal().stripTrailingZeros());
						}
						
						for (int j = 0; j < o_fooo.getDecimalArray().length; j++) {
							if ( (o_fooo.getDecimalArray()[j] != null) && (o_fooo.getDecimalArray()[j] != new java.math.BigDecimal(0d)) ) {
								o_fooo.getDecimalArray()[j] = o_fooo.getDecimalArray()[j].stripTrailingZeros();
							}
						}
						
						for (int j = 0; j < o_fooo.getDecimalList().size(); j++) {
							if ( (o_fooo.getDecimalList().get(j) != null) && (o_fooo.getDecimal() != new java.math.BigDecimal(0d)) ) {
								o_fooo.getDecimalList().set(j, o_fooo.getDecimalList().get(j).stripTrailingZeros());
							}
						}
				    } else {
				    	net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject o_fooo = (net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject) a_serverObjects.get(i);
				    	
				    	for (int j = 0; j < o_fooo.getDecimalArray().length; j++) {
							if ( (o_fooo.getDecimalArray()[j] != null) && (o_fooo.getDecimalArray()[j] != new java.math.BigDecimal(0d)) ) {
								o_fooo.getDecimalArray()[j] = o_fooo.getDecimalArray()[j].stripTrailingZeros();
							}
						}
				    }
				}
				
				if (!p_b_falseUDPtrueTCP) {
					if (a_serverObjects.size() <= 7) {
						throw new Exception("server object list must have at least '8' entries, but has '" + a_serverObjects.size() + "'");
					}
					
					for (int i = 0; i < a_serverObjects.size(); i++) {
						if ( ! net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_serverObjects.get(i), o_foo, true, true) ) {
							throw new Exception("server object is not equal to expected object");
						}
					}
				} else {
					if (a_serverObjects.size() != 10) {
						throw new Exception("server object list has not '10' entries, but has '" + a_serverObjects.size() + "'");
					}
					
					for (int i = 0; i < 10; i++) {
						if ( ! net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_serverObjects.get(i), o_foo, true, true) ) {
							throw new Exception("server object is not equal to expected object");
						}
					}
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		} else {/* CLIENT */
			try {
				java.util.List<Object> a_clientObjects = new java.util.ArrayList<Object>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND;
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK;
				}
				
				if (p_b_falseUDPtrueTCP) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND;
				}
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_o_config.clientIp, p_o_config.clientPort, ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) ? p_o_config.clientLocalIp : null, ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) ? p_o_config.clientLocalPort : 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, true, false, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				
				if (p_b_falseUDPtrueTCP) {
					o_communicationConfig.setObjectTransmission(true);
				}
				
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				for (int i = 0; i < i_iterations; i++) {
					Object o_foo = null;
					
					if (p_b_smallObject) {
						o_foo = new net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject();
						((net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject)o_foo).initAll();
					} else {
						o_foo = new net.forestany.forestj.lib.test.nettest.msg.MessageObject();
						((net.forestany.forestj.lib.test.nettest.msg.MessageObject)o_foo).initAll();
					}
					
					while ( !o_communication.enqueue(
						o_foo
					) ) {
						net.forestany.forestj.lib.Global.ilogWarning("could not enqueue object");
					}
					
					net.forestany.forestj.lib.Global.ilogFine("object enqueued");
					a_clientObjects.add(o_foo);
					
					if (i == 4) { /* additional delay for 25 milliseconds times sleep multiplier constant, after the 5th time enqueue has been executed */
						Thread.sleep(25 * i_sleepMultiplier);
					}
					
					Thread.sleep(25 * i_sleepMultiplier + ( (p_o_config.highSecurity) ? 50 : 0 ) + ( (p_b_udpWithAck) ? 225 : 0 ));
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				Object o_foo = null;
				
				if (p_b_smallObject) {
					o_foo = new net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject();
					((net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject)o_foo).initAll();
				} else {
					o_foo = new net.forestany.forestj.lib.test.nettest.msg.MessageObject();
					((net.forestany.forestj.lib.test.nettest.msg.MessageObject)o_foo).initAll();
				}
				
				/* strip trailing zeroes for decimal, decimal array and decimal list */
				for (int i = 0; i < a_clientObjects.size(); i++) {
					if (!p_b_smallObject) {
						net.forestany.forestj.lib.test.nettest.msg.MessageObject o_fooo = (net.forestany.forestj.lib.test.nettest.msg.MessageObject) a_clientObjects.get(i);
						
						if ( (o_fooo.getDecimal() != null) && (o_fooo.getDecimal() != new java.math.BigDecimal(0d)) ) {
							o_fooo.setDecimal(o_fooo.getDecimal().stripTrailingZeros());
						}
						
						for (int j = 0; j < o_fooo.getDecimalArray().length; j++) {
							if ( (o_fooo.getDecimalArray()[j] != null) && (o_fooo.getDecimalArray()[j] != new java.math.BigDecimal(0d)) ) {
								o_fooo.getDecimalArray()[j] = o_fooo.getDecimalArray()[j].stripTrailingZeros();
							}
						}
						
						for (int j = 0; j < o_fooo.getDecimalList().size(); j++) {
							if ( (o_fooo.getDecimalList().get(j) != null) && (o_fooo.getDecimal() != new java.math.BigDecimal(0d)) ) {
								o_fooo.getDecimalList().set(j, o_fooo.getDecimalList().get(j).stripTrailingZeros());
							}
						}
				    } else {
				    	net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject o_fooo = (net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject) a_clientObjects.get(i);
				    	
				    	for (int j = 0; j < o_fooo.getDecimalArray().length; j++) {
							if ( (o_fooo.getDecimalArray()[j] != null) && (o_fooo.getDecimalArray()[j] != new java.math.BigDecimal(0d)) ) {
								o_fooo.getDecimalArray()[j] = o_fooo.getDecimalArray()[j].stripTrailingZeros();
							}
						}
				    }
				}
				
				if (!p_b_falseUDPtrueTCP) {
					if (a_clientObjects.size() <= 7) {
						throw new Exception("client object list must have at least '8' entries, but has '" + a_clientObjects.size() + "'");
					}
					
					for (int i = 0; i < a_clientObjects.size(); i++) {
						if ( ! net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_clientObjects.get(i), o_foo, true, true) ) {
							throw new Exception("client object is not equal to expected object");
						}
					}
				} else {
					if (a_clientObjects.size() != 10) {
						throw new Exception("client object list has not '10' entries, but has '" + a_clientObjects.size() + "'");
					}
					
					for (int i = 0; i < 10; i++) {
						if ( ! net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_clientObjects.get(i), o_foo, true, true) ) {
							throw new Exception("client object is not equal to expected object");
						}
					}
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		}
	}
	
	private static void netCommunicationMarshallingSharedMemoryUniDirectional(boolean p_b_falseUDPtrueTCP, boolean p_b_udpWithAck, boolean p_b_smallObject, NetConfig p_o_config) throws Exception {
		i_sleepMultiplier = p_o_config.sleepMultiplier;
		java.util.List<String> a_expectedResults = new java.util.ArrayList<String>();
		
		/* expected result */
		String s_expectedResult = "true | 0 | [ 1, 3, 5, -123, 42, 0, null, -102 ] | 42 | o | [ A, F, K, , U, *, null, ó ] | 0.0 | [ 1.25, 3.5, 5.75, 10.101, 41.998, 0.0, 4984654.5 ] | 0.0 | [ 1.25, 3.5, 5.75, 10.101, 41.998, 0.0, null, 8798546.2154656 ] | 16426 | 16426 | 0 | [ 1, 3, 5, 536870954, 42, 0 ] | [ 1, 3, 5, 536870954, 42, 0, null ] | 536870954 | 1170936177994235946 | [ 1, 3, 5, 1170936177994235946, 42, 0, null ] | 0 | [ 1, 3, 5, 1170936177994235946, 42, 0 ] | Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. | [ Hello World 1!, Hello World 2!, Hello World 3!, Hello World 4!, Hello World 5!, null, null ] | 06:02:03 | 2020-03-04 | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ] | 22:16:06 | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | 2020-03-04T06:02:03 | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ]";
		
		if (p_b_smallObject) {
			s_expectedResult = "true | o | [ 1, 3, 5, 536870954, 42, 0 ] | [ 1, 3, 5, 1170936177994235946, 42, 0, null ] | Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. | 22:16:06 | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ]";
		}
		
		for (String s_foo : s_expectedResult.split("\\|")) {
			a_expectedResults.add(s_foo);
		}
		
		if (p_o_config.isServer) { /* SERVER */
			try {
				java.util.List<String> a_serverResults = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE;
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK;
				}
				
				if (p_b_falseUDPtrueTCP) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE;
				}
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_o_config.serverIp, p_o_config.serverPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				net.forestany.forestj.lib.net.sock.com.SharedMemory<?> o_sharedMemory = null;
				
				if (!p_b_smallObject) {
					o_sharedMemory = new net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryMessageObject();
					((net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryMessageObject)o_sharedMemory).emptyAll();
					o_sharedMemory.initiateMirrors();
					o_communicationConfig.setSharedMemory(o_sharedMemory);
				} else {
					o_sharedMemory = new net.forestany.forestj.lib.test.nettest.sock.com.SharedMemorySmallMessageObject();
					((net.forestany.forestj.lib.test.nettest.sock.com.SharedMemorySmallMessageObject)o_sharedMemory).emptyAll();
					o_sharedMemory.initiateMirrors();
					o_communicationConfig.setSharedMemory(o_sharedMemory);
				}
				
				o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
				
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				int i_additionalTime = 25;
				
				if (p_b_falseUDPtrueTCP) { /* TCP */
					if (p_o_config.highSecurity) {
						i_additionalTime *= 3;
					}
					
					if (p_o_config.asymmetricSecurity) {
						i_additionalTime *= 3;
					}
					
					if (p_o_config.marshallingDataLengthInBytes > 3) {
						i_additionalTime *= (p_o_config.marshallingDataLengthInBytes / 2);
					}
				} else { /* UDP */
					if (p_o_config.highSecurity) {
						i_additionalTime *= 5;
					}
					
					if (p_b_udpWithAck) {
						i_additionalTime += 225;
					}
					
					i_additionalTime *= p_o_config.marshallingDataLengthInBytes;
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);

				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				/* server result */
				for (String s_foo : o_sharedMemory.toString().split("\\|")) {
					a_serverResults.add(s_foo);
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				int i_missingFieldsServer = 0;
				
				for (int i = 0; i < a_serverResults.size(); i++) {
					if (!a_expectedResults.get(i).contentEquals(a_serverResults.get(i))) {
						i_missingFieldsServer++;
						net.forestany.forestj.lib.Global.ilogFiner("server field result not equal expected result:\t" + a_serverResults.get(i) + "\t != \t" + a_expectedResults.get(i));
					}
				}
				
				if (i_missingFieldsServer > 0) {
					net.forestany.forestj.lib.Global.ilog("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + " server missing fields: " + i_missingFieldsServer);
				} else {
					net.forestany.forestj.lib.Global.ilogFine("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + " server missing fields: fine");
				}
				
				if (i_missingFieldsServer > 4) {
					throw new Exception(i_missingFieldsServer + " server fields not matching expected values, which is greater than '4'");
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		} else { /* CLIENT */
			try {
				java.util.List<String> a_clientResults = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND;
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK;
				}
				
				if (p_b_falseUDPtrueTCP) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND;
				}
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_o_config.clientIp, p_o_config.clientPort, ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) ? p_o_config.clientLocalIp : null, ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) ? p_o_config.clientLocalPort : 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, false, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				net.forestany.forestj.lib.net.sock.com.SharedMemory<?> o_sharedMemory = null;
				
				if (!p_b_smallObject) {
					o_sharedMemory = new net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryMessageObject();
					((net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryMessageObject)o_sharedMemory).emptyAll();
					o_sharedMemory.initiateMirrors();
					o_communicationConfig.setSharedMemory(o_sharedMemory);
				} else {
					o_sharedMemory = new net.forestany.forestj.lib.test.nettest.sock.com.SharedMemorySmallMessageObject();
					((net.forestany.forestj.lib.test.nettest.sock.com.SharedMemorySmallMessageObject)o_sharedMemory).emptyAll();
					o_sharedMemory.initiateMirrors();
					o_communicationConfig.setSharedMemory(o_sharedMemory);
				}
				
				o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
				
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
		
				int i_additionalTime = 25;
				
				if (p_b_falseUDPtrueTCP) { /* TCP */
					if (p_o_config.highSecurity) {
						i_additionalTime *= 3;
					}
					
					if (p_o_config.asymmetricSecurity) {
						i_additionalTime *= 3;
					}
					
					if (p_o_config.marshallingDataLengthInBytes > 3) {
						i_additionalTime *= (p_o_config.marshallingDataLengthInBytes / 2);
					}
				} else { /* UDP */
					if (p_o_config.highSecurity) {
						i_additionalTime *= 5;
					}
					
					if (p_b_udpWithAck) {
						i_additionalTime += 225;
					}
					
					i_additionalTime *= p_o_config.marshallingDataLengthInBytes;
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				if (!p_b_smallObject) {
					o_sharedMemory.setField("String", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
					o_sharedMemory.setField("LocalTime", java.time.LocalTime.of(15, 32, 03));
					o_sharedMemory.setField("IntegerArray", new int[] { 1, 3, 5, 536870954, 42, 0 });
					o_sharedMemory.setField("Short", (short)16426);
					o_sharedMemory.setField("UnsignedShort", (short)16426);
					o_sharedMemory.setField("IntegerList", java.util.Arrays.asList( 1, 3, 5, 536870954, 42, 0, null ));
					o_sharedMemory.setField("UnsignedInteger", 536870954);
					o_sharedMemory.setField("Long", 1170936177994235946l);
					o_sharedMemory.setField("UnsignedLongArray", new long[] { 1l, 3l, 5l, 1170936177994235946l, 42l, 0l });
					o_sharedMemory.setField("StringList", java.util.Arrays.asList( "Hello World 1!", "Hello World 2!", "Hello World 3!", "Hello World 4!", "Hello World 5!", "", null ));
					o_sharedMemory.setField("Time", new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("01.01.1970 06:02:03"));
				} else {
					o_sharedMemory.setField("String", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
					o_sharedMemory.setField("LocalTime", java.time.LocalTime.of(15, 32, 03));
					o_sharedMemory.setField("IntegerArray", new int[] { 1, 3, 5, 536870954, 42, 0 });
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				if (!p_b_smallObject) {
					o_sharedMemory.setField("LongList", java.util.Arrays.asList( 1l, 3l, 5l, 1170936177994235946l, 42l, 0l, null ));
					o_sharedMemory.setField("Char", 'o');
					o_sharedMemory.setField("String", "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
					o_sharedMemory.setField("LocalDateArray", new java.time.LocalDate[] { java.time.LocalDate.of(2020, 3, 4), java.time.LocalDate.of(2020, 6, 8), java.time.LocalDate.of(2020, 12, 16), null });
					o_sharedMemory.setField("Bool", true);
					o_sharedMemory.setField("ByteList", java.util.Arrays.asList( (byte)1, (byte)3, (byte)5, (byte)133, (byte)42, (byte)0, null, (byte)-102 ));
					o_sharedMemory.setField("UnsignedByte", (byte)42);
					o_sharedMemory.setField("CharList", java.util.Arrays.asList( (char)65, (char)70, (char)75, (char)133, (char)85, (char)42, null, (char)243 ));
					o_sharedMemory.setField("FloatArray", new float[] { 1.25f, 3.5f, 5.75f, 10.1010f, 41.998f, 0.f, 4984654.5498795465f });
					o_sharedMemory.setField("DoubleList", java.util.Arrays.asList( 1.25d, 3.5d, 5.75d, 10.1010d, 41.998d, 0.d, null, 8798546.2154656d ));
				} else {
					o_sharedMemory.setField("LongList", java.util.Arrays.asList( 1l, 3l, 5l, 1170936177994235946l, 42l, 0l, null ));
					o_sharedMemory.setField("Char", 'o');
					o_sharedMemory.setField("String", "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
					o_sharedMemory.setField("LocalDateArray", new java.time.LocalDate[] { java.time.LocalDate.of(2020, 3, 4), java.time.LocalDate.of(2020, 6, 8), java.time.LocalDate.of(2020, 12, 16), null });
					o_sharedMemory.setField("Bool", true);
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				if (!p_b_smallObject) {
					o_sharedMemory.setField("LocalDateTimeList", java.util.Arrays.asList( java.time.LocalDateTime.of(2020, 3, 4, 6, 2, 3), java.time.LocalDateTime.of(2020, 6, 8, 9, 24, 16), java.time.LocalDateTime.of(2020, 12, 16, 12, 48, 53), null ));
					o_sharedMemory.setField("LocalTime", java.time.LocalTime.of(22, 16, 06));
					o_sharedMemory.setField("Date", new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("04.03.2020 00:00:00"));
					o_sharedMemory.setField("DateTimeArray", new java.util.Date[] {
						new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("04.03.2020 06:02:03"),
						new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("08.06.2020 09:24:16"),
						new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("16.12.2020 12:48:53"),
						null
					});
					o_sharedMemory.setField("LocalDateList", java.util.Arrays.asList( java.time.LocalDate.of(2020, 3, 4), java.time.LocalDate.of(2020, 6, 8), java.time.LocalDate.of(2020, 12, 16), null ));
					o_sharedMemory.setField("LocalDateTime", java.time.LocalDateTime.of(2020, 3, 4, 6, 2, 3));
				} else {
					o_sharedMemory.setField("LocalDateTimeList", java.util.Arrays.asList( java.time.LocalDateTime.of(2020, 3, 4, 6, 2, 3), java.time.LocalDateTime.of(2020, 6, 8, 9, 24, 16), java.time.LocalDateTime.of(2020, 12, 16, 12, 48, 53), null ));
					o_sharedMemory.setField("LocalTime", java.time.LocalTime.of(22, 16, 06));
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				/* client result */
				for (String s_foo : o_sharedMemory.toString().split("\\|")) {
					a_clientResults.add(s_foo);
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				int i_missingFieldsClient = 0;
				
				for (int i = 0; i < a_clientResults.size(); i++) {	
					if (!a_expectedResults.get(i).contentEquals(a_clientResults.get(i))) {
						i_missingFieldsClient++;
						net.forestany.forestj.lib.Global.ilogFiner("client field result not equal expected result:\t" + a_clientResults.get(i) + "\t != \t" + a_expectedResults.get(i));
					}
				}
				
				if (i_missingFieldsClient > 0) {
					net.forestany.forestj.lib.Global.ilog("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + " client missing fields: " + i_missingFieldsClient);
				} else {
					net.forestany.forestj.lib.Global.ilogFine("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + " client missing fields: fine");
				}
				
				if (i_missingFieldsClient > 4) {
					throw new Exception(i_missingFieldsClient + " client fields not matching expected values, which is greater than '4'");
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		}
	}

	private static void netCommunicationMarshallingSharedMemoryBiDirectional(boolean p_b_falseUDPtrueTCP, boolean p_b_udpWithAck, boolean p_b_smallObject, NetConfig p_o_config) throws Exception {
		i_sleepMultiplier = p_o_config.sleepMultiplier;
		java.util.List<String> a_expectedResults = new java.util.ArrayList<String>();
		
		/* expected result */
		String s_expectedResult = "true | 0 | [ 1, 3, 5, -123, 42, 0, null, -102 ] | 42 | o | [ A, F, K, , U, *, null, ó ] | 0.0 | [ 1.25, 3.5, 5.75, 10.101, 41.998, 0.0, 4984654.5 ] | 0.0 | [ 1.25, 3.5, 5.75, 10.101, 41.998, 0.0, null, 8798546.2154656 ] | 16426 | 16426 | 0 | [ 1, 3, 5, 536870954, 42, 0 ] | [ 1, 3, 5, 536870954, 42, 0, null ] | 536870954 | 1170936177994235946 | [ 1, 3, 5, 1170936177994235946, 42, 0, null ] | 0 | [ 1, 3, 5, 1170936177994235946, 42, 0 ] | Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. | [ Hello World 1!, Hello World 2!, Hello World 3!, Hello World 4!, Hello World 5!, null, null ] | 06:02:03 | 2020-03-04 | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ] | 22:16:06 | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | 2020-03-04T06:02:03 | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ]";
		
		if (p_b_smallObject) {
			s_expectedResult = "true | o | [ 1, 3, 5, 536870954, 42, 0 ] | [ 1, 3, 5, 1170936177994235946, 42, 0, null ] | Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. | 22:16:06 | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ]";
		}
		
		for (String s_foo : s_expectedResult.split("\\|")) {
			a_expectedResults.add(s_foo);
		}
		
		if (p_o_config.isServer) { /* SERVER */
			try {
				java.util.List<String> a_serverResults = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE;
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK;
				}
				
				if (p_b_falseUDPtrueTCP) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE;
				}
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_o_config.serverIp, p_o_config.serverPort, null, 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, p_b_falseUDPtrueTCP, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				net.forestany.forestj.lib.net.sock.com.SharedMemory<?> o_sharedMemory = null;
				
				if (!p_b_smallObject) {
					o_sharedMemory = new net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryMessageObject();
					((net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryMessageObject)o_sharedMemory).emptyAll();
					o_sharedMemory.initiateMirrors();
					o_communicationConfig.setSharedMemory(o_sharedMemory);
				} else {
					o_sharedMemory = new net.forestany.forestj.lib.test.nettest.sock.com.SharedMemorySmallMessageObject();
					((net.forestany.forestj.lib.test.nettest.sock.com.SharedMemorySmallMessageObject)o_sharedMemory).emptyAll();
					o_sharedMemory.initiateMirrors();
					o_communicationConfig.setSharedMemory(o_sharedMemory);
				}
				
				o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
				
				o_communicationConfig.setSharedMemoryBidirectional(
					java.util.Arrays.asList(
						new java.util.AbstractMap.SimpleEntry<String,Integer>(p_o_config.serverBiIp, p_o_config.serverBiPort)
					),
					(p_b_falseUDPtrueTCP) ? o_communicationConfig.getSSLContextList() : null
				);
				
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
				
				int i_additionalTime = 25;
				
				if (p_b_falseUDPtrueTCP) { /* TCP */
					if (p_o_config.highSecurity) {
						i_additionalTime *= 3;
					}
					
					if (p_o_config.asymmetricSecurity) {
						i_additionalTime *= 3;
					}
					
					if (p_o_config.marshallingDataLengthInBytes > 3) {
						i_additionalTime *= (p_o_config.marshallingDataLengthInBytes / 2);
					}
				} else { /* UDP */
					if (p_o_config.highSecurity) {
						i_additionalTime *= 5;
					}
					
					if (p_b_udpWithAck) {
						i_additionalTime += 225;
					}
					
					i_additionalTime *= p_o_config.marshallingDataLengthInBytes;
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);

				if (!p_b_smallObject) {
					o_sharedMemory.setField("LongList", java.util.Arrays.asList( 1l, 3l, 5l, 1170936177994235946l, 42l, 0l, null ));
					o_sharedMemory.setField("Char", 'o');
					o_sharedMemory.setField("String", "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
					o_sharedMemory.setField("LocalDateArray", new java.time.LocalDate[] { java.time.LocalDate.of(2020, 3, 4), java.time.LocalDate.of(2020, 6, 8), java.time.LocalDate.of(2020, 12, 16), null });
					o_sharedMemory.setField("Bool", true);
					o_sharedMemory.setField("ByteList", java.util.Arrays.asList( (byte)1, (byte)3, (byte)5, (byte)133, (byte)42, (byte)0, null, (byte)-102 ));
					o_sharedMemory.setField("UnsignedByte", (byte)42);
					o_sharedMemory.setField("CharList", java.util.Arrays.asList( (char)65, (char)70, (char)75, (char)133, (char)85, (char)42, null, (char)243 ));
					o_sharedMemory.setField("FloatArray", new float[] { 1.25f, 3.5f, 5.75f, 10.1010f, 41.998f, 0.f, 4984654.5498795465f });
					o_sharedMemory.setField("DoubleList", java.util.Arrays.asList( 1.25d, 3.5d, 5.75d, 10.1010d, 41.998d, 0.d, null, 8798546.2154656d ));
				} else {
					o_sharedMemory.setField("LongList", java.util.Arrays.asList( 1l, 3l, 5l, 1170936177994235946l, 42l, 0l, null ));
					o_sharedMemory.setField("Char", 'o');
					o_sharedMemory.setField("String", "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
					o_sharedMemory.setField("LocalDateArray", new java.time.LocalDate[] { java.time.LocalDate.of(2020, 3, 4), java.time.LocalDate.of(2020, 6, 8), java.time.LocalDate.of(2020, 12, 16), null });
					o_sharedMemory.setField("Bool", true);
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				/* server result */
				for (String s_foo : o_sharedMemory.toString().split("\\|")) {
					a_serverResults.add(s_foo);
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				int i_missingFieldsServer = 0;
				
				for (int i = 0; i < a_serverResults.size(); i++) {
					if (!a_expectedResults.get(i).contentEquals(a_serverResults.get(i))) {
						i_missingFieldsServer++;
						net.forestany.forestj.lib.Global.ilogFiner("server field result not equal expected result:\t" + a_serverResults.get(i) + "\t != \t" + a_expectedResults.get(i));
					}
				}
				
				if (i_missingFieldsServer > 0) {
					net.forestany.forestj.lib.Global.ilog("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + " server missing fields: " + i_missingFieldsServer);
				} else {
					net.forestany.forestj.lib.Global.ilogFine("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + " server missing fields: fine");
				}
				
				if (i_missingFieldsServer > 6) {
					throw new Exception(i_missingFieldsServer + " server fields not matching expected values, which is greater than '6'");
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		} else { /* CLIENT */
			try {
				java.util.List<String> a_clientResults = new java.util.ArrayList<String>();
				
				net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND;
				
				if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK;
				}
				
				if (p_b_falseUDPtrueTCP) {
					e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND;
				}
				
				net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(p_o_config.currentDirectory, e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_o_config.clientIp, p_o_config.clientPort, ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) ? p_o_config.clientLocalIp : null, ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) ? p_o_config.clientLocalPort : 0, p_o_config.symmetricSecurity128, p_o_config.symmetricSecurity256, p_o_config.asymmetricSecurity, p_o_config.highSecurity, p_b_falseUDPtrueTCP, p_o_config.useMarshalling, p_o_config.useMarshallingWholeObject, p_o_config.marshallingDataLengthInBytes, p_o_config.marshallingUsePropertyMethods, p_o_config.marshallingSystemUsesLittleEndian);
				net.forestany.forestj.lib.net.sock.com.SharedMemory<?> o_sharedMemory = null;
				
				if (!p_b_smallObject) {
					o_sharedMemory = new net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryMessageObject();
					((net.forestany.forestj.lib.test.nettest.sock.com.SharedMemoryMessageObject)o_sharedMemory).emptyAll();
					o_sharedMemory.initiateMirrors();
					o_communicationConfig.setSharedMemory(o_sharedMemory);
				} else {
					o_sharedMemory = new net.forestany.forestj.lib.test.nettest.sock.com.SharedMemorySmallMessageObject();
					((net.forestany.forestj.lib.test.nettest.sock.com.SharedMemorySmallMessageObject)o_sharedMemory).emptyAll();
					o_sharedMemory.initiateMirrors();
					o_communicationConfig.setSharedMemory(o_sharedMemory);
				}
				
				o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
				
				o_communicationConfig.setSharedMemoryBidirectional(
					java.util.Arrays.asList(
						new java.util.AbstractMap.SimpleEntry<String,Integer>(p_o_config.clientBiIp, p_o_config.clientBiPort)
					),
					(p_b_falseUDPtrueTCP) ? o_communicationConfig.getSSLContextList() : null
				);
				
				net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
				o_communication.start();
		
				int i_additionalTime = 25;
				
				if (p_b_falseUDPtrueTCP) { /* TCP */
					if (p_o_config.highSecurity) {
						i_additionalTime *= 3;
					}
					
					if (p_o_config.asymmetricSecurity) {
						i_additionalTime *= 3;
					}
					
					if (p_o_config.marshallingDataLengthInBytes > 3) {
						i_additionalTime *= (p_o_config.marshallingDataLengthInBytes / 2);
					}
				} else { /* UDP */
					if (p_o_config.highSecurity) {
						i_additionalTime *= 5;
					}
					
					if (p_b_udpWithAck) {
						i_additionalTime += 225;
					}
					
					i_additionalTime *= p_o_config.marshallingDataLengthInBytes;
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				if (!p_b_smallObject) {
					o_sharedMemory.setField("String", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
					o_sharedMemory.setField("LocalTime", java.time.LocalTime.of(15, 32, 03));
					o_sharedMemory.setField("IntegerArray", new int[] { 1, 3, 5, 536870954, 42, 0 });
					o_sharedMemory.setField("Short", (short)16426);
					o_sharedMemory.setField("UnsignedShort", (short)16426);
					o_sharedMemory.setField("IntegerList", java.util.Arrays.asList( 1, 3, 5, 536870954, 42, 0, null ));
					o_sharedMemory.setField("UnsignedInteger", 536870954);
					o_sharedMemory.setField("Long", 1170936177994235946l);
					o_sharedMemory.setField("UnsignedLongArray", new long[] { 1l, 3l, 5l, 1170936177994235946l, 42l, 0l });
					o_sharedMemory.setField("StringList", java.util.Arrays.asList( "Hello World 1!", "Hello World 2!", "Hello World 3!", "Hello World 4!", "Hello World 5!", "", null ));
					o_sharedMemory.setField("Time", new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("01.01.1970 06:02:03"));
				} else {
					o_sharedMemory.setField("String", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
					o_sharedMemory.setField("LocalTime", java.time.LocalTime.of(15, 32, 03));
					o_sharedMemory.setField("IntegerArray", new int[] { 1, 3, 5, 536870954, 42, 0 });
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				if (!p_b_smallObject) {
					o_sharedMemory.setField("LocalDateTimeList", java.util.Arrays.asList( java.time.LocalDateTime.of(2020, 3, 4, 6, 2, 3), java.time.LocalDateTime.of(2020, 6, 8, 9, 24, 16), java.time.LocalDateTime.of(2020, 12, 16, 12, 48, 53), null ));
					o_sharedMemory.setField("LocalTime", java.time.LocalTime.of(22, 16, 06));
					o_sharedMemory.setField("Date", new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("04.03.2020 00:00:00"));
					o_sharedMemory.setField("DateTimeArray", new java.util.Date[] {
						new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("04.03.2020 06:02:03"),
						new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("08.06.2020 09:24:16"),
						new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("16.12.2020 12:48:53"),
						null
					});
					o_sharedMemory.setField("LocalDateList", java.util.Arrays.asList( java.time.LocalDate.of(2020, 3, 4), java.time.LocalDate.of(2020, 6, 8), java.time.LocalDate.of(2020, 12, 16), null ));
					o_sharedMemory.setField("LocalDateTime", java.time.LocalDateTime.of(2020, 3, 4, 6, 2, 3));
				} else {
					o_sharedMemory.setField("LocalDateTimeList", java.util.Arrays.asList( java.time.LocalDateTime.of(2020, 3, 4, 6, 2, 3), java.time.LocalDateTime.of(2020, 6, 8, 9, 24, 16), java.time.LocalDateTime.of(2020, 12, 16, 12, 48, 53), null ));
					o_sharedMemory.setField("LocalTime", java.time.LocalTime.of(22, 16, 06));
				}
				
				Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
				
				/* client result */
				for (String s_foo : o_sharedMemory.toString().split("\\|")) {
					a_clientResults.add(s_foo);
				}
				
				if (o_communication != null) {
					o_communication.stop();
				}
				
				int i_missingFieldsClient = 0;
				
				for (int i = 0; i < a_clientResults.size(); i++) {	
					if (!a_expectedResults.get(i).contentEquals(a_clientResults.get(i))) {
						i_missingFieldsClient++;
						net.forestany.forestj.lib.Global.ilogFiner("client field result not equal expected result:\t" + a_clientResults.get(i) + "\t != \t" + a_expectedResults.get(i));
					}
				}
				
				if (i_missingFieldsClient > 0) {
					net.forestany.forestj.lib.Global.ilog("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + " client missing fields: " + i_missingFieldsClient);
				} else {
					net.forestany.forestj.lib.Global.ilogFine("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + " client missing fields: fine");
				}
				
				if (i_missingFieldsClient > 6) {
					throw new Exception(i_missingFieldsClient + " client fields not matching expected values, which is greater than '6'");
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
		}
	}
}
