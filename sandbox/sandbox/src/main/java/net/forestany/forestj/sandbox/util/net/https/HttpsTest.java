package net.forestany.forestj.sandbox.util.net.https;

public class HttpsTest {
	public static void testTinyHttpsMenu(String p_s_currentDirectory) throws Exception {
		int i_input = -1;
		
		HttpsTest.HttpsConfig o_httpsConfig = HttpsTest.readHttpsConfig(p_s_currentDirectory);
		
		do {
			System.out.println("++++++++++++++++++++++++++++++++");
			System.out.println("+ test Tiny Https              +");
			System.out.println("++++++++++++++++++++++++++++++++");
			
			System.out.println("");
			
			System.out.println("[1] run tiny https server");
			System.out.println("[2] run tiny https server with sql pool in background");
			System.out.println("[3] run tiny https client");
			System.out.println("[4] test tiny soap server + client");
			System.out.println("[5] test soap number conversion client");
			System.out.println("[6] test tiny rest server + client");
			System.out.println("[0] quit");
			
			System.out.println("");
			
			i_input = net.forestany.forestj.lib.Console.consoleInputInteger("Enter menu number[1-5;0]: ", "Invalid input.", "Please enter a value.");
		
			System.out.println("");
			
			if (i_input == 1) {
				HttpsTest.runTinyHttpsServer(o_httpsConfig);
			} else if (i_input == 2) {
				HttpsTest.runTinyHttpsServerSqlPool(o_httpsConfig);
			} else if (i_input == 3) {
				HttpsTest.runTinyHttpsClient(o_httpsConfig);
			} else if (i_input == 4) {
				HttpsTest.runTinySoapServerAndClient(o_httpsConfig);
			} else if (i_input == 5) {
				HttpsTest.runTinySoapClientNumberConversion(o_httpsConfig);
			} else if (i_input == 6) {
				HttpsTest.runTinyRestServerAndClient(o_httpsConfig);
			}
			
			System.out.println("");
			
		} while (i_input != 0);
	}
	
	public class HttpsConfig {
		public String serverIp;
		public int serverPort;
		public String clientIp;
		public int clientPort;
		
		public String sqlPoolHost;
		public int sqlPoolPort;
		public String sqlPoolDatasource;
		public String sqlPoolUser;
		public String sqlPoolPassword;
		
		public String currentDirectory;
	}
	
	public static HttpsConfig readHttpsConfig(String p_s_currentDirectory) throws Exception {
		String s_resourcesHttpsDirectory = p_s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "https" + net.forestany.forestj.lib.io.File.DIR;
		String s_httpsConfigFile = "httpsConfig.txt";
		
		if (!net.forestany.forestj.lib.io.File.exists(s_resourcesHttpsDirectory + s_httpsConfigFile)) {
			throw new Exception("file[" + s_resourcesHttpsDirectory + s_httpsConfigFile + "] does not exists");
		}
		
		net.forestany.forestj.lib.io.File o_httpsConfigFile = new net.forestany.forestj.lib.io.File(s_resourcesHttpsDirectory + s_httpsConfigFile, false);
		
		if (o_httpsConfigFile.getFileLines() != 9) {
			throw new Exception("invalid config file[" + s_resourcesHttpsDirectory + s_httpsConfigFile + "]; must have '9 lines', but has '" + o_httpsConfigFile.getFileLines() + " lines'");
		}
		
		HttpsTest.HttpsConfig o_httpsConfig = new HttpsTest().new HttpsConfig();
		o_httpsConfig.currentDirectory = p_s_currentDirectory;
		
		for (int i = 1; i <= o_httpsConfigFile.getFileLines(); i++) {
			String s_line = o_httpsConfigFile.readLine(i);
			
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
				
				o_httpsConfig.serverIp = a_split[1].trim();
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
				
				o_httpsConfig.serverPort = Integer.parseInt(a_split[1].trim());
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
				
				o_httpsConfig.clientIp = a_split[1].trim();
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
				
				o_httpsConfig.clientPort = Integer.parseInt(a_split[1].trim());
			} else if (i == 5) {
				if (!s_line.startsWith("sqlPoolHost")) {
					throw new Exception("Line #" + i + " does not start with 'sqlPoolHost'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'sqlPoolHost': '" + s_line + "'");
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(a_split[1].trim())) {
					throw new Exception("Invalid empty value, for 'sqlPoolHost'");
				}
				
				o_httpsConfig.sqlPoolHost = a_split[1].trim();
			} else if (i == 6) {
				if (!s_line.startsWith("sqlPoolPort")) {
					throw new Exception("Line #" + i + " does not start with 'sqlPoolPort'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'sqlPoolPort': '" + s_line + "'");
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger(a_split[1].trim())) {
					throw new Exception("Invalid value for 'sqlPoolPort': '" + a_split[1].trim() + "' is not an integer");
				}
				
				o_httpsConfig.sqlPoolPort = Integer.parseInt(a_split[1].trim());
			} else if (i == 7) {
				if (!s_line.startsWith("sqlPoolDatasource")) {
					throw new Exception("Line #" + i + " does not start with 'sqlPoolDatasource'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'sqlPoolDatasource': '" + s_line + "'");
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(a_split[1].trim())) {
					throw new Exception("Invalid empty value, for 'sqlPoolDatasource'");
				}
				
				o_httpsConfig.sqlPoolDatasource = a_split[1].trim();
			} else if (i == 8) {
				if (!s_line.startsWith("sqlPoolUser")) {
					throw new Exception("Line #" + i + " does not start with 'sqlPoolUser'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'sqlPoolUser': '" + s_line + "'");
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(a_split[1].trim())) {
					throw new Exception("Invalid empty value, for 'sqlPoolUser'");
				}
				
				o_httpsConfig.sqlPoolUser = a_split[1].trim();
			} else if (i == 9) {
				if (!s_line.startsWith("sqlPoolPassword")) {
					throw new Exception("Line #" + i + " does not start with 'sqlPoolPassword'");
				}
				
				String[] a_split = s_line.split("=");
				
				if (a_split.length != 2) {
					throw new Exception("Invalid key value pair for 'sqlPoolPassword': '" + s_line + "'");
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(a_split[1].trim())) {
					throw new Exception("Invalid empty value, for 'sqlPoolPassword'");
				}
				
				o_httpsConfig.sqlPoolPassword = a_split[1].trim();
			}
		}
		
		System.out.println("++++++++++++++++++++++++++++++++");
		System.out.println("+ HTTPS config read            +");
		System.out.println("++++++++++++++++++++++++++++++++");
			
		System.out.println("");
		
		System.out.println("server ip" + "\t\t" + o_httpsConfig.serverIp);
		System.out.println("server port" + "\t\t" + o_httpsConfig.serverPort);
		System.out.println("client ip" + "\t\t" + o_httpsConfig.clientIp);
		System.out.println("client port" + "\t\t" + o_httpsConfig.clientPort);
		
		System.out.println("");
		
		System.out.println("sql pool host" + "\t\t" + o_httpsConfig.sqlPoolHost);
		System.out.println("sql pool port" + "\t\t" + o_httpsConfig.sqlPoolPort);
		System.out.println("sql pool datasource" + "\t" + o_httpsConfig.sqlPoolDatasource);
		System.out.println("sql pool user" + "\t\t" + o_httpsConfig.sqlPoolUser);
		System.out.println("sql pool password" + "\t" + o_httpsConfig.sqlPoolPassword);
		
		System.out.println("");
		
		return o_httpsConfig;
	}
	
	public static void runTinyHttpsServer(HttpsConfig p_o_httpsConfig) throws Exception {
		String s_certificatesDirectory = p_o_httpsConfig.currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "com" + net.forestany.forestj.lib.io.File.DIR;
		String s_rootDirectory = p_o_httpsConfig.currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "httpsserver" + net.forestany.forestj.lib.io.File.DIR;
		String s_sessionDirectory = p_o_httpsConfig.currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "httpssessions" + net.forestany.forestj.lib.io.File.DIR;
		String s_host = p_o_httpsConfig.serverIp;
		String s_hostPart = s_host.substring(0, s_host.lastIndexOf("."));
		int i_port = p_o_httpsConfig.serverPort;
		
		for (net.forestany.forestj.lib.io.ListingElement o_file : net.forestany.forestj.lib.io.File.listDirectory(s_sessionDirectory)) {
			if (!o_file.getFullName().endsWith("dummy.txt")) {
				net.forestany.forestj.lib.io.File.deleteFile(o_file.getFullName());
			}
		}
		
		/* SERVER */
		
		net.forestany.forestj.lib.net.https.Config o_serverConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.DYNAMIC, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER);
		o_serverConfig.setAllowSourceList( java.util.Arrays.asList(s_hostPart + ".1/24") );
		o_serverConfig.setHost(s_host);
		o_serverConfig.setPort(i_port);
		o_serverConfig.setRootDirectory(s_rootDirectory);
		o_serverConfig.setSessionDirectory(s_sessionDirectory);
		o_serverConfig.setSessionMaxAge(new net.forestany.forestj.lib.DateInterval("PT30M"));
		o_serverConfig.setSessionRefresh(true);
		o_serverConfig.setForestSeed(new net.forestany.forestj.lib.test.nettest.sock.https.TestSeed());
		
		net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket>( o_serverConfig );
		net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket> o_socketReceive = new net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket>(
			javax.net.ssl.SSLServerSocket.class,				/* class type */
			net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER,	/* socket type */
			s_host,												/* receiving address */
			i_port,												/* receiving port */
			o_serverTask,										/* server task */
			30000,												/* timeout milliseconds */
			-1,													/* max. number of executions */
			8192,												/* receive buffer size */
			net.forestany.forestj.lib.Cryptography.createSSLContextWithOneCertificate(s_certificatesDirectory + "server/KeyStore-srv.p12", "123456", "test_server2")		/* ssl context */
		);
		Thread o_threadServer = new Thread(o_socketReceive);
		
		/* START SERVER */
		
		o_threadServer.start();
		
		System.out.println("Server started with '" + s_host + ":" + i_port + "'");
		System.out.println("You can access the site with a browser. Keep in mind it will be an unknown certificate.");
		System.out.println("Try 'https://"+ s_host + ":" + i_port + "' or 'https://"+ s_host + ":" + i_port + "/complete_page'");
		
		net.forestany.forestj.lib.Console.consoleInputString("Please enter any key to stop tiny https server . . . ", true);
				
		/* ------------------------------------------ */
		/* Stop SERVER                                */
		/* ------------------------------------------ */
		
		if (o_socketReceive != null) {
			o_socketReceive.stop();
		}
	}
	
	public static void runTinyHttpsServerSqlPool(HttpsConfig p_o_httpsConfig) throws Exception {
		System.out.println("Be sure that the sql connection parameters in the config file are correct for MariaDB: '"+ p_o_httpsConfig.sqlPoolHost + ":" + p_o_httpsConfig.sqlPoolPort + "'");
		
		String s_certificatesDirectory = p_o_httpsConfig.currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "com" + net.forestany.forestj.lib.io.File.DIR;
		String s_rootDirectory = p_o_httpsConfig.currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "httpsserversql" + net.forestany.forestj.lib.io.File.DIR;
		String s_sessionDirectory = p_o_httpsConfig.currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "httpssessions" + net.forestany.forestj.lib.io.File.DIR;
		String s_host = p_o_httpsConfig.serverIp;
		String s_hostPart = s_host.substring(0, s_host.lastIndexOf("."));
		int i_port = p_o_httpsConfig.serverPort;
		
		for (net.forestany.forestj.lib.io.ListingElement o_file : net.forestany.forestj.lib.io.File.listDirectory(s_sessionDirectory)) {
			if (!o_file.getFullName().endsWith("dummy.txt")) {
				net.forestany.forestj.lib.io.File.deleteFile(o_file.getFullName());
			}
		}
				
		/* START - check MARIADB */
		
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		o_glob.BaseGateway = net.forestany.forestj.lib.sqlcore.BaseGateway.MARIADB;
		o_glob.Base = new net.forestany.forestj.lib.sql.mariadb.BaseMariaDB(p_o_httpsConfig.sqlPoolHost + ":" + p_o_httpsConfig.sqlPoolPort, p_o_httpsConfig.sqlPoolDatasource, p_o_httpsConfig.sqlPoolUser, p_o_httpsConfig.sqlPoolPassword);
		
		PersonRecord o_record = new PersonRecord();
		java.util.List<PersonRecord> a_records = null;
		
		try {
			a_records = o_record.getRecords();
		} catch (Exception o_exc) {
			System.out.println("Could not query 'sys_forestj_person'; creating table . . .");
			
			/* #### CREATE ############################################################################################# */
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create> o_queryCreate = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.CREATE, "sys_forestj_person");
			/* #### Columns ############################################################################################ */
			java.util.List<java.util.Properties> a_columnsDefinition = new java.util.ArrayList<java.util.Properties>();
			
			java.util.Properties o_properties = new java.util.Properties();
			o_properties.put("name", "Id");
			o_properties.put("columnType", "integer [int]");
			o_properties.put("constraints", "NOT NULL;PRIMARY KEY;AUTO_INCREMENT");
			a_columnsDefinition.add(o_properties);
			
			o_properties = new java.util.Properties();
			o_properties.put("name", "PersonalIdentificationNumber");
			o_properties.put("columnType", "integer [int]");
			o_properties.put("constraints", "NOT NULL;UNIQUE");
			a_columnsDefinition.add(o_properties);
			
			o_properties = new java.util.Properties();
			o_properties.put("name", "Name");
			o_properties.put("columnType", "text [255]");
			o_properties.put("constraints", "NOT NULL");
			a_columnsDefinition.add(o_properties);
			
			o_properties = new java.util.Properties();
			o_properties.put("name", "Age");
			o_properties.put("columnType", "integer [int]");
			o_properties.put("constraints", "NOT NULL");
			a_columnsDefinition.add(o_properties);
			
			o_properties = new java.util.Properties();
			o_properties.put("name", "City");
			o_properties.put("columnType", "text [255]");
			o_properties.put("constraints", "NOT NULL");
			a_columnsDefinition.add(o_properties);
			
			o_properties = new java.util.Properties();
			o_properties.put("name", "Country");
			o_properties.put("columnType", "text [36]");
			o_properties.put("constraints", "NOT NULL");
			a_columnsDefinition.add(o_properties);
			
			/* #### Query ############################################################################ */
			
			for (java.util.Properties o_columnDefinition : a_columnsDefinition) {
				net.forestany.forestj.lib.sql.ColumnStructure o_column = new net.forestany.forestj.lib.sql.ColumnStructure(o_queryCreate);
				o_column.columnTypeAllocation(o_columnDefinition.getProperty("columnType"));
				o_column.s_name = o_columnDefinition.getProperty("name");
				o_column.setAlterOperation("ADD");
				
				if (o_columnDefinition.containsKey("constraints")) {
					String[] a_constraints = o_columnDefinition.getProperty("constraints").split(";");
					
					for (int i = 0; i < a_constraints.length; i++) {
						o_column.addConstraint(o_queryCreate.constraintTypeAllocation(a_constraints[i]));
						
						if ( (a_constraints[i].compareTo("DEFAULT") == 0) && (o_columnDefinition.containsKey("constraintDefaultValue")) ) {
							o_column.setConstraintDefaultValue((Object)o_columnDefinition.getProperty("constraintDefaultValue"));
						}
					}
				}
				
				o_queryCreate.getQuery().a_columns.add(o_column);
			}
			
			java.util.List<java.util.LinkedHashMap<String, Object>> a_result = o_glob.Base.fetchQuery(o_queryCreate);
			
			/* check table has been created */
			
			if (a_result.size() != 1) {
				throw new Exception("Result row amount of create query is not '1', it is '" + a_result.size() + "'" );
			}
			
			System.out.println("Table 'sys_forestj_person' created.");
		}
		
		if ((a_records == null) || (a_records.size() != 4)) {
			o_record.truncateTable();
			
			System.out.println("Truncated table 'sys_forestj_person'.");
			
			o_record = new PersonRecord();
			o_record.ColumnPersonalIdentificationNumber = 643532;
			o_record.ColumnName = "John Smith";
			o_record.ColumnAge = 32;
			o_record.ColumnCity = "New York";
			o_record.ColumnCountry = "US";
			o_record.insertRecord();
			
			o_record = new PersonRecord();
			o_record.ColumnPersonalIdentificationNumber = 284255;
			o_record.ColumnName = "Elizabeth Miller";
			o_record.ColumnAge = 21;
			o_record.ColumnCity = "Hamburg";
			o_record.ColumnCountry = "DE";
			o_record.insertRecord();
			
			o_record = new PersonRecord();
			o_record.ColumnPersonalIdentificationNumber = 116974;
			o_record.ColumnName = "Jennifer Garcia";
			o_record.ColumnAge = 48;
			o_record.ColumnCity = "London";
			o_record.ColumnCountry = "UK";
			o_record.insertRecord();
			
			o_record = new PersonRecord();
			o_record.ColumnPersonalIdentificationNumber = 295556;
			o_record.ColumnName = "Jakub Kowalski";
			o_record.ColumnAge = 39;
			o_record.ColumnCity = "Warsaw";
			o_record.ColumnCountry = "PL";
			o_record.insertRecord();
			
			System.out.println("Inserted 4 standard rows into table 'sys_forestj_person'.");
		}
		
		o_glob.Base.closeConnection();
		
		/* END - check MARIADB */
		
		/* SERVER */
		
		net.forestany.forestj.lib.net.sql.pool.HttpsConfig o_serverConfig = new net.forestany.forestj.lib.net.sql.pool.HttpsConfig("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.DYNAMIC, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER);
		o_serverConfig.setAllowSourceList( java.util.Arrays.asList(s_hostPart + ".1/24") );
		o_serverConfig.setHost(s_host);
		o_serverConfig.setPort(i_port);
		o_serverConfig.setRootDirectory(s_rootDirectory);
		o_serverConfig.setSessionDirectory(s_sessionDirectory);
		o_serverConfig.setSessionMaxAge(new net.forestany.forestj.lib.DateInterval("PT30M"));
		o_serverConfig.setSessionRefresh(true);
		o_serverConfig.setForestSeed(new TestSeedSqlPool());
		
		o_serverConfig.setBasePool(
			new net.forestany.forestj.lib.sql.pool.BasePool(
				3,
				new net.forestany.forestj.lib.DateInterval("PT10S"),
				1000,
				o_glob.BaseGateway,
				p_o_httpsConfig.sqlPoolHost + ":" + p_o_httpsConfig.sqlPoolPort,
				p_o_httpsConfig.sqlPoolDatasource,
				p_o_httpsConfig.sqlPoolUser,
				p_o_httpsConfig.sqlPoolPassword
			)
		);
				
		net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket>( o_serverConfig );
		net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket> o_socketReceive = new net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket>(
			javax.net.ssl.SSLServerSocket.class,				/* class type */
			net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER,	/* socket type */
			s_host,												/* receiving address */
			i_port,												/* receiving port */
			o_serverTask,										/* server task */
			30000,												/* timeout milliseconds */
			-1,													/* max. number of executions */
			8192,												/* receive buffer size */
			net.forestany.forestj.lib.Cryptography.createSSLContextWithOneCertificate(s_certificatesDirectory + "server/KeyStore-srv.p12", "123456", "test_server2")		/* ssl context */
		);
		Thread o_threadServer = new Thread(o_socketReceive);
		
		/* START SERVER + BASE POOL */
		
		o_threadServer.start();
		o_serverConfig.getBasePool().start();
		
		System.out.println("Server started with '" + s_host + ":" + i_port + "' and background base pool MARIADB");
		System.out.println("You can access the site with a browser. Keep in mind it will be an unknown certificate.");
		System.out.println("Try 'https://"+ s_host + ":" + i_port + "'");
		
		net.forestany.forestj.lib.Console.consoleInputString("Please enter any key to stop tiny https server . . . ", true);
				
		/* ------------------------------------------ */
		/* Stop SERVER + BASE POOL                    */
		/* ------------------------------------------ */
		
		if (o_socketReceive != null) {
			o_socketReceive.stop();
		}
		
		if (o_serverConfig.getBasePool() != null) {
			o_serverConfig.getBasePool().stop();
		}
	}
	
	public static void runTinyHttpsClient(HttpsConfig p_o_httpsConfig) throws Exception {
		String s_host = p_o_httpsConfig.clientIp;
		int i_port = p_o_httpsConfig.clientPort;
		
		/* CLIENT */
		
		net.forestany.forestj.lib.net.https.Config o_clientConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.NORMAL, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SOCKET);
		net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket>( o_clientConfig );
		
		/* it is wrong to check reachability in this junit test, because server side is not online when creating socket instance for sending */
		boolean b_checkReachability = false;
		
		net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket> o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket>(javax.net.ssl.SSLSocket.class, s_host, i_port, o_clientTask, 30000, b_checkReachability, 1, 25, 1500, java.net.InetAddress.getLocalHost().getHostAddress(), 0, null);
		o_clientConfig.setSendingSocketInstanceForHttpClient(o_socketSend);
		
		String s_url = "https://www.forestany.net";
		net.forestany.forestj.lib.net.http.RequestType e_requestTypeMethod = net.forestany.forestj.lib.net.http.RequestType.GET;
		net.forestany.forestj.lib.net.http.PostType e_postType = null;
		java.util.Map<String,Object> m_requestParamters = new java.util.LinkedHashMap<String,Object>();
		java.util.Map<String,String> m_attachments = new java.util.LinkedHashMap<String,String>();
		
		String s_proxyHost = "";
		int i_proxyPort = 0;
		String s_proxyUser = "";
		String s_proxyPassword = "";
		
		int i_input = -1;
		
		do {
			System.out.println("Testing Tiny Https Client");
			
			System.out.println("");
			
			System.out.println("URL: " + s_url);
			System.out.println("Request type method: " + e_requestTypeMethod);
			System.out.println("Post content type: " + e_postType);
			System.out.println("Request parmaters:");
			
			if (m_requestParamters.size() > 0) {
				for (java.util.Map.Entry<String, Object> o_pair : m_requestParamters.entrySet()) {
					System.out.println("\t" + o_pair.getKey() + " = " + o_pair.getValue());
				}
			}
			
			System.out.println("Request attachment:");
			
			if (m_attachments.size() > 0) {
				for (java.util.Map.Entry<String, String> o_pair : m_attachments.entrySet()) {
					System.out.println("\t" + o_pair.getKey() + " = " + o_pair.getValue());
				}
			}
			
			System.out.println("Proxy: " + s_proxyHost + ":" + i_proxyPort);
			System.out.println("Proxy Auth: " + s_proxyUser + ":******");
			
			System.out.println("");
			
			System.out.println("[1] set url");
			System.out.println("[2] set request type method");
			System.out.println("[3] set post content type");
			System.out.println("[4] add request parameters");
			System.out.println("[5] add attachment");
			System.out.println("[6] add proxy");
			System.out.println("[7] add proxy authentication");
			System.out.println("[8] execute request");
			System.out.println("[0] quit");
			
			System.out.println("");
			
			i_input = net.forestany.forestj.lib.Console.consoleInputInteger("Enter menu number[1-6;0]: ", "Invalid input.", "Please enter a value.");
		
			System.out.println("");
			
			if (i_input == 1) {
				s_url = net.forestany.forestj.lib.Console.consoleInputString("URL: ", false, "Please enter a value.");
			} else if (i_input == 2) {
				boolean b_valid = false;
				
				do {
					String s_foo = net.forestany.forestj.lib.Console.consoleInputString("Request type method: ", false, "Please enter a value.");
					
					switch (s_foo) {
					case "GET":
						e_requestTypeMethod = net.forestany.forestj.lib.net.http.RequestType.GET;
						b_valid = true;
						break;
					case "POST":
						e_requestTypeMethod = net.forestany.forestj.lib.net.http.RequestType.POST;
						b_valid = true;
						break;
					case "PUT":
						e_requestTypeMethod = net.forestany.forestj.lib.net.http.RequestType.PUT;
						b_valid = true;
						break;
					case "DELETE":
						e_requestTypeMethod = net.forestany.forestj.lib.net.http.RequestType.DELETE;
						b_valid = true;
						break;
					default:
						System.out.println("Please enter a valid request type method. [GET|POST|PUT|DELETE]");
						break;
					}
				} while (!b_valid);
			} else if (i_input == 3) {
				boolean b_valid = false;
				
				do {
					String s_foo = net.forestany.forestj.lib.Console.consoleInputString("Post content type: ", false, "Please enter a value.");
					
					switch (s_foo) {
					case "HTML":
						e_postType = net.forestany.forestj.lib.net.http.PostType.HTML;
						b_valid = true;
						break;
					case "HTMLATTACHMENTS":
						e_postType = net.forestany.forestj.lib.net.http.PostType.HTMLATTACHMENTS;
						b_valid = true;
						break;
					default:
						System.out.println("Please enter a valid post content type. [HTML|HTMLATTACHMENTS]");
						break;
					}
				} while (!b_valid);
			} else if (i_input == 4) {
				String s_foo = net.forestany.forestj.lib.Console.consoleInputString("Request parameters(e.g. key1=value1&key2=value2): ", false, "Please enter a value.");
				
				if (s_foo.contains("&")) {
					String[] a_keyValuePairs = s_foo.split("&");
					
					for (String s_foo2 : a_keyValuePairs) {
						if (s_foo2.contains("=")) {
							String[] a_keyValuePair = s_foo2.split("=");
							
							m_requestParamters.put(a_keyValuePair[0], a_keyValuePair[1]);
						}
					}
				} else {
					if (s_foo.contains("=")) {
						String[] a_keyValuePair = s_foo.split("=");
						
						m_requestParamters.put(a_keyValuePair[0], a_keyValuePair[1]);
					}
				}
			} else if (i_input == 5) {
				String s_filename = net.forestany.forestj.lib.Console.consoleInputString("Attachment filename: ", false, "Please enter a value.");
				String s_filepath = net.forestany.forestj.lib.Console.consoleInputString("Attachment local path to file: ", false, "Please enter a value.");
				
				m_attachments.put(s_filename, s_filepath);
			} else if (i_input == 6) {
				s_proxyHost = net.forestany.forestj.lib.Console.consoleInputString("Proxy Address: ", true, "Please enter a value.");
				i_proxyPort = net.forestany.forestj.lib.Console.consoleInputInteger("Proxy Port: ", "Please enter a value.");
			} else if (i_input == 7) {
				s_proxyUser = net.forestany.forestj.lib.Console.consoleInputString("Proxy User: ", true, "Please enter a value.");
				s_proxyPassword = net.forestany.forestj.lib.Console.consoleInputPassword("Proxy Password: ", "Please enter a value.");
			} else if (i_input == 8) {
				o_clientTask.setRequest(s_url, e_requestTypeMethod);
				
				if (e_postType != null) {
					o_clientTask.setContentType(e_postType);
				}
				
				if (m_requestParamters.size() > 0) {
					for (java.util.Map.Entry<String, Object> o_pair : m_requestParamters.entrySet()) {
						o_clientTask.addRequestParameter(o_pair.getKey(), o_pair.getValue());
					}
				}
				
				if (m_attachments.size() > 0) {
					for (java.util.Map.Entry<String, String> o_pair : m_attachments.entrySet()) {
						o_clientTask.addAttachement(o_pair.getKey(), o_pair.getValue());
					}
				}
				
				if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_proxyHost)) {
					o_clientTask.setProxyAddress(s_proxyHost);
				}
				
				if (i_proxyPort > 0) {
					o_clientTask.setProxyPort(i_proxyPort);
				}
				
				if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_proxyUser)) {
					o_clientTask.setProxyAuthenticationUser(s_proxyUser);
				}
				
				if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_proxyPassword)) {
					o_clientTask.setProxyAuthenticationPassword(s_proxyPassword);
				}
				
				o_clientTask.executeRequest();
				
				System.out.println(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage());
				System.out.println(o_clientTask.getResponse());
				
				e_requestTypeMethod = net.forestany.forestj.lib.net.http.RequestType.GET;
				e_postType = null;
				m_requestParamters = new java.util.LinkedHashMap<String,Object>();
				m_attachments = new java.util.LinkedHashMap<String,String>();
				o_clientTask.setRequest(s_url, e_requestTypeMethod);
			}
			
			System.out.println("");
			
		} while (i_input != 0);
		
		/* ------------------------------------------ */
		/* Stop CLIENT                                */
		/* ------------------------------------------ */
		
		if (o_socketSend != null) {
			o_socketSend.stop();
		}
	}

	public static void runTinySoapServerAndClient(HttpsConfig p_o_httpsConfig) throws Exception {
		String s_certificatesDirectory = p_o_httpsConfig.currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "com" + net.forestany.forestj.lib.io.File.DIR;
		String s_rootDirectory = p_o_httpsConfig.currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "soapserver" + net.forestany.forestj.lib.io.File.DIR;
		String s_host = p_o_httpsConfig.serverIp;
		String s_hostPart = s_host.substring(0, s_host.lastIndexOf("."));
		int i_port = p_o_httpsConfig.serverPort;
		String s_clientHost = p_o_httpsConfig.clientIp;
		int i_clientPort = p_o_httpsConfig.clientPort;
		
		/* SERVER */
		
		net.forestany.forestj.lib.net.https.Config o_serverConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.SOAP, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER);
		o_serverConfig.setAllowSourceList( java.util.Arrays.asList(s_hostPart + ".1/24") );
		o_serverConfig.setHost(s_host);
		o_serverConfig.setPort(i_port);
		o_serverConfig.setRootDirectory(s_rootDirectory);
		o_serverConfig.setNotUsingCookies(true);
		
		net.forestany.forestj.lib.net.https.soap.WSDL o_serverWsdl = new net.forestany.forestj.lib.net.https.soap.WSDL(s_rootDirectory + "calculator" + net.forestany.forestj.lib.io.File.DIR + "calculator.wsdl");
		o_serverConfig.setWSDL(o_serverWsdl);
		
		o_serverConfig.getWSDL().setSOAPOperation("Add", net.forestany.forestj.lib.test.nettest.sock.https.CalculatorImpl.Add());
		o_serverConfig.getWSDL().setSOAPOperation("Subtract", net.forestany.forestj.lib.test.nettest.sock.https.CalculatorImpl.Subtract());
		o_serverConfig.getWSDL().setSOAPOperation("Multiply", net.forestany.forestj.lib.test.nettest.sock.https.CalculatorImpl.Multiply());
		o_serverConfig.getWSDL().setSOAPOperation("Divide", net.forestany.forestj.lib.test.nettest.sock.https.CalculatorImpl.Divide());
		
		net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket>( o_serverConfig );
		net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket> o_socketReceive = new net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket>(
			javax.net.ssl.SSLServerSocket.class,				/* class type */
			net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER,	/* socket type */
			s_host,												/* receiving address */
			i_port,												/* receiving port */
			o_serverTask,										/* server task */
			30000,												/* timeout milliseconds */
			-1,													/* max. number of executions */
			8192,												/* receive buffer size */
			net.forestany.forestj.lib.Cryptography.createSSLContextWithOneCertificate(s_certificatesDirectory + "server/KeyStore-srv.p12", "123456", "test_server2")		/* ssl context */
		);
		
		Thread o_threadServer = new Thread(o_socketReceive);
		
		/* CLIENT */
		
		/* I don't know why exactly, but it is very important that for local test of TCP bidirectional on the same machine, truststore must be set right here with all trusting certificates */
		System.setProperty("javax.net.ssl.trustStore", s_certificatesDirectory + "all/TrustStore-all.p12");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		
		net.forestany.forestj.lib.net.https.Config o_clientConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_clientHost, net.forestany.forestj.lib.net.https.Mode.SOAP, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SOCKET);
		net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket>( o_clientConfig );
		
		/* it is wrong to check reachability in this test, because server side is not online when creating socket instance for sending */
		boolean b_checkReachability = false;
		
		net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket> o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket>(
			javax.net.ssl.SSLSocket.class,	/* class type */
			s_clientHost,					/* destination address */
			i_clientPort,					/* destination port */
			o_clientTask,					/* client task */
			30000,							/* timeout milliseconds */
			b_checkReachability,			/* check if destination is reachable */
			1,								/* max. number of executions */
			25,								/* interval for waiting for other communication side */
			8192,							/* buffer size */
			null,							/* sender address */
			0,								/* sender port */
			null							/* ssl context */
		);
		
		o_clientConfig.setSendingSocketInstanceForHttpClient(o_socketSend);
		
		net.forestany.forestj.lib.net.https.soap.WSDL o_clientWsdl = new net.forestany.forestj.lib.net.https.soap.WSDL(s_rootDirectory + "calculator" + net.forestany.forestj.lib.io.File.DIR + "calculator.wsdl");
		o_clientConfig.setWSDL(o_clientWsdl);
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		
		System.out.println("SOAP Server started with '" + s_host + ":" + i_port + "'");
		System.out.println("You can call the SOAP operations with the following menu...");
		
		net.forestany.forestj.lib.test.nettest.sock.https.Calculator o_calculator = new net.forestany.forestj.lib.test.nettest.sock.https.Calculator();
		int i_input = -1;
		
		do {
			System.out.println("Testing Tiny SOAP Server + Client");
			
			System.out.println("");
			
			System.out.println("[1] test SOAP Add method");
			System.out.println("[2] test SOAP Subtract method");
			System.out.println("[3] test SOAP Multiply method");
			System.out.println("[4] test SOAP Divide method");
			System.out.println("[0] quit");
			
			System.out.println("");
			
			i_input = net.forestany.forestj.lib.Console.consoleInputInteger("Enter menu number[1-9;0]: ", "Invalid input.", "Please enter a value.");
		
			System.out.println("");
			
			if (i_input == 1) {
				net.forestany.forestj.lib.test.nettest.sock.https.Calculator.Add o_add = o_calculator.new Add();
				o_add.param1 = net.forestany.forestj.lib.Console.consoleInputDouble("Value #1: ", "Entered value is not a double value.", "Please enter a value.");
				o_add.param2 = net.forestany.forestj.lib.Console.consoleInputDouble("Value #2: ", "Entered value is not a double value.", "Please enter a value.");
				
				o_clientTask.setSOAPRequest(o_clientWsdl.getService().getServicePorts().get(0).getAddressLocation(), o_add);
				o_clientTask.executeRequest();
				
				if (o_clientTask.getSOAPFault() != null) {
					throw new Exception( "SOAP fault occured: " +  o_clientTask.getSOAPFault() );
				} else {
					net.forestany.forestj.lib.test.nettest.sock.https.Calculator.AddResult o_addResult = (net.forestany.forestj.lib.test.nettest.sock.https.Calculator.AddResult) o_clientTask.getSOAPResponse();
					
					if (o_addResult != null) {
						System.out.println(o_add.param1 + " + " + o_add.param2 + " = " + o_addResult.result);
					} else {
						System.out.println("SOAP response object is null");
					}
				}
			} else if (i_input == 2) {
				net.forestany.forestj.lib.test.nettest.sock.https.Calculator.Subtract o_subtract = o_calculator.new Subtract();
				o_subtract.param1 = net.forestany.forestj.lib.Console.consoleInputDouble("Value #1: ", "Entered value is not a double value.", "Please enter a value.");
				o_subtract.param2 = net.forestany.forestj.lib.Console.consoleInputDouble("Value #2: ", "Entered value is not a double value.", "Please enter a value.");
				
				o_clientTask.setSOAPRequest(o_clientWsdl.getService().getServicePorts().get(0).getAddressLocation(), o_subtract);
				o_clientTask.executeRequest();
				
				if (o_clientTask.getSOAPFault() != null) {
					throw new Exception( "SOAP fault occured: " +  o_clientTask.getSOAPFault() );
				} else {
					net.forestany.forestj.lib.test.nettest.sock.https.Calculator.SubtractResult o_subtractResult = (net.forestany.forestj.lib.test.nettest.sock.https.Calculator.SubtractResult) o_clientTask.getSOAPResponse();
					
					if (o_subtractResult != null) {
						System.out.println(o_subtract.param1 + " - " + o_subtract.param2 + " = " + o_subtractResult.result);
					} else {
						System.out.println("SOAP response object is null");
					}
				}
			} else if (i_input == 3) {
				net.forestany.forestj.lib.test.nettest.sock.https.Calculator.Multiply o_multiply = o_calculator.new Multiply();
				o_multiply.param1 = net.forestany.forestj.lib.Console.consoleInputDouble("Value #1: ", "Entered value is not a double value.", "Please enter a value.");
				o_multiply.param2 = net.forestany.forestj.lib.Console.consoleInputDouble("Value #2: ", "Entered value is not a double value.", "Please enter a value.");
				
				o_clientTask.setSOAPRequest(o_clientWsdl.getService().getServicePorts().get(0).getAddressLocation(), o_multiply);
				o_clientTask.executeRequest();
				
				if (o_clientTask.getSOAPFault() != null) {
					throw new Exception( "SOAP fault occured: " +  o_clientTask.getSOAPFault() );
				} else {
					net.forestany.forestj.lib.test.nettest.sock.https.Calculator.MultiplyResult o_multiplyResult = (net.forestany.forestj.lib.test.nettest.sock.https.Calculator.MultiplyResult) o_clientTask.getSOAPResponse();
					
					if (o_multiplyResult != null) {
						System.out.println(o_multiply.param1 + " * " + o_multiply.param2 + " = " + o_multiplyResult.result);
					} else {
						System.out.println("SOAP response object is null");
					}
				}
			} else if (i_input == 4) {
				net.forestany.forestj.lib.test.nettest.sock.https.Calculator.Divide o_divide = o_calculator.new Divide();
				o_divide.param1 = net.forestany.forestj.lib.Console.consoleInputDouble("Value #1: ", "Entered value is not a double value.", "Please enter a value.");
				o_divide.param2 = net.forestany.forestj.lib.Console.consoleInputDouble("Value #2: ", "Entered value is not a double value.", "Please enter a value.");
				
				o_clientTask.setSOAPRequest(o_clientWsdl.getService().getServicePorts().get(0).getAddressLocation(), o_divide);
				o_clientTask.executeRequest();
				
				if (o_clientTask.getSOAPFault() != null) {
					throw new Exception( "SOAP fault occured: " +  o_clientTask.getSOAPFault() );
				} else {
					net.forestany.forestj.lib.test.nettest.sock.https.Calculator.DivideResult o_divideResult = (net.forestany.forestj.lib.test.nettest.sock.https.Calculator.DivideResult) o_clientTask.getSOAPResponse();
					
					if (o_divideResult != null) {
						System.out.println(o_divide.param1 + " / " + o_divide.param2 + " = " + o_divideResult.result);
					} else {
						System.out.println("SOAP response object is null");
					}
				}
			}
			
			System.out.println("");
			
		} while (i_input != 0);
		
		/* ------------------------------------------ */
		/* Stop SERVER                                */
		/* ------------------------------------------ */
		
		if (o_socketReceive != null) {
			o_socketReceive.stop();
		}
		
		if (o_socketSend != null) {
			o_socketSend.stop();
		}
		
		System.out.println("SOAP Server stopped");
	}

	public static void runTinySoapClientNumberConversion(HttpsConfig p_o_httpsConfig) throws Exception {
		String s_rootDirectory = p_o_httpsConfig.currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "soapclient" + net.forestany.forestj.lib.io.File.DIR;
		String s_host = p_o_httpsConfig.clientIp;
		int i_port = p_o_httpsConfig.clientPort;
		
		/* CLIENT */
		
		net.forestany.forestj.lib.net.https.Config o_clientConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.SOAP, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SOCKET);
		net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket>( o_clientConfig );
		
		/* it is wrong to check reachability in this test, because server side is not online when creating socket instance for sending */
		boolean b_checkReachability = false;
		
		/**
		 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		 * !!! NOT RECOMMENDED !!!
		 * SSL VULNERABILITY, but it will work in that case
		 * !!! NOT RECOMMENDED !!!
		 */
		javax.net.ssl.TrustManager[] trustAllCerts = {
			new javax.net.ssl.X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
					
				}
				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
					
				}
			}
		};

        javax.net.ssl.SSLContext o_sslContext = javax.net.ssl.SSLContext.getInstance("SSL");
        o_sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        /**
		 * !!! NOT RECOMMENDED !!!
		 * SSL VULNERABILITY, but it will work in that case
		 * !!! NOT RECOMMENDED !!!
		 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		 */
        
		net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket> o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket>(
			javax.net.ssl.SSLSocket.class,	/* class type */
			s_host,							/* destination address */
			i_port,							/* destination port */
			o_clientTask,					/* client task */
			30000,							/* timeout milliseconds */
			b_checkReachability,			/* check if destination is reachable */
			1,								/* max. number of executions */
			25,								/* interval for waiting for other communication side */
			8192,							/* buffer size */
			null,							/* sender address */
			0,								/* sender port */
			o_sslContext					/* ssl context */
		);
		o_clientConfig.setSendingSocketInstanceForHttpClient(o_socketSend);
		
		net.forestany.forestj.lib.net.https.soap.WSDL o_clientWsdl = new net.forestany.forestj.lib.net.https.soap.WSDL(s_rootDirectory + "numberconversion.wsdl");
		o_clientConfig.setWSDL(o_clientWsdl);
		
		int i_input = -1;
		
		do {
			System.out.println("Testing Tiny SOAP Client with external Number-Conversion service");
			
			System.out.println("");
			
			System.out.println("[1] test SOAP service 'Number to words'");
			System.out.println("[2] test SOAP service 'Number to dollars'");
			System.out.println("[0] quit");
			
			System.out.println("");
			
			i_input = net.forestany.forestj.lib.Console.consoleInputInteger("Enter menu number[1-2;0]: ", "Invalid input.", "Please enter a value.");
		
			System.out.println("");
			
			NumberConv o_numConv = new NumberConv();
			
			if (i_input == 1) {
				NumberConv.NumberToWords o_foo = o_numConv.new NumberToWords();
				o_foo.ubiNum = net.forestany.forestj.lib.Console.consoleInputLong("Number to words value: ", "Entered value is not a long value.", "Please enter a value.");
				
				o_clientTask.setSOAPRequest(o_clientWsdl.getService().getServicePorts().get(0).getAddressLocation(), o_foo);
				o_clientTask.executeRequest();
				
				NumberConv.NumberToWordsResponse o_response = (NumberConv.NumberToWordsResponse) o_clientTask.getSOAPResponse();
				System.out.println(o_foo.ubiNum + " = " + o_response.NumberToWordsResult);
			} else if (i_input == 2) {
				NumberConv.NumberToDollars o_foo = o_numConv.new NumberToDollars();
				o_foo.dNum = new java.math.BigDecimal(net.forestany.forestj.lib.Console.consoleInputDouble("Number to dollars value: ", "Entered value is not a double value.", "Please enter a value."));
				
				o_clientTask.setSOAPRequest(o_clientWsdl.getService().getServicePorts().get(0).getAddressLocation(), o_foo);
				o_clientTask.executeRequest();
				
				NumberConv.NumberToDollarsResponse o_response = (NumberConv.NumberToDollarsResponse) o_clientTask.getSOAPResponse();
				System.out.println(o_foo.dNum + " = " + o_response.NumberToDollarsResult);
			}
			
			System.out.println("");
			
		} while (i_input != 0);
		
		/* ------------------------------------------ */
		/* Stop CLIENT                                */
		/* ------------------------------------------ */
		
		if (o_socketSend != null) {
			o_socketSend.stop();
		}
	}

	public static void runTinyRestServerAndClient(HttpsConfig p_o_httpsConfig) throws Exception {
		String s_certificatesDirectory = p_o_httpsConfig.currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "com" + net.forestany.forestj.lib.io.File.DIR;
		String s_rootDirectory = p_o_httpsConfig.currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "restserver" + net.forestany.forestj.lib.io.File.DIR;
		String s_sessionDirectory = p_o_httpsConfig.currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "restsessions" + net.forestany.forestj.lib.io.File.DIR;
		String s_host = p_o_httpsConfig.serverIp;
		String s_hostPart = s_host.substring(0, s_host.lastIndexOf("."));
		int i_port = p_o_httpsConfig.serverPort;
		String s_clientHost = p_o_httpsConfig.clientIp;
		int i_clientPort = p_o_httpsConfig.clientPort;
		
		for (net.forestany.forestj.lib.io.ListingElement o_file : net.forestany.forestj.lib.io.File.listDirectory(s_sessionDirectory)) {
			if (!o_file.getFullName().endsWith("dummy.txt")) {
				net.forestany.forestj.lib.io.File.deleteFile(o_file.getFullName());
			}
		}
		
		/* SERVER */
		
		net.forestany.forestj.lib.net.https.Config o_serverConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.REST, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER);
		o_serverConfig.setAllowSourceList( java.util.Arrays.asList(s_hostPart + ".1/24") );
		o_serverConfig.setHost(s_host);
		o_serverConfig.setPort(i_port);
		o_serverConfig.setRootDirectory(s_rootDirectory);
		o_serverConfig.setSessionDirectory(s_sessionDirectory);
		o_serverConfig.setSessionMaxAge(new net.forestany.forestj.lib.DateInterval("PT30M"));
		o_serverConfig.setSessionRefresh(true);
		o_serverConfig.setForestREST(new net.forestany.forestj.lib.test.nettest.sock.https.TestREST());
		
		net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket>( o_serverConfig );
		net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket> o_socketReceive = new net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket>(
			javax.net.ssl.SSLServerSocket.class,				/* class type */
			net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER,	/* socket type */
			s_host,												/* receiving address */
			i_port,												/* receiving port */
			o_serverTask,										/* server task */
			30000,												/* timeout milliseconds */
			-1,													/* max. number of executions */
			8192,												/* receive buffer size */
			net.forestany.forestj.lib.Cryptography.createSSLContextWithOneCertificate(s_certificatesDirectory + "server/KeyStore-srv.p12", "123456", "test_server2")		/* ssl context */
		);
		Thread o_threadServer = new Thread(o_socketReceive);
		
		/* CLIENT */
		
		/* I don't know why exactly, but it is very important that for local test of TCP bidirectional on the same machine, truststore must be set right here with all trusting certificates */
		System.setProperty("javax.net.ssl.trustStore", s_certificatesDirectory + "all/TrustStore-all.p12");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		
		net.forestany.forestj.lib.net.https.Config o_clientConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.REST, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SOCKET);
		net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket>( o_clientConfig );
		
		/* it is wrong to check reachability in this junit test, because server side is not online when creating socket instance for sending */
		boolean b_checkReachability = false;
		
		net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket> o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket>(
			javax.net.ssl.SSLSocket.class,	/* class type */
			s_clientHost,					/* destination address */
			i_clientPort,					/* destination port */
			o_clientTask,					/* client task */
			30000,							/* timeout milliseconds */
			b_checkReachability,			/* check if destination is reachable */
			1,								/* max. number of executions */
			25,								/* interval for waiting for other communication side */
			8192,							/* buffer size */
			null,							/* sender address */
			0,								/* sender port */
			null							/* ssl context */
		);
		o_clientConfig.setSendingSocketInstanceForHttpClient(o_socketSend);
		o_clientConfig.setClientUseCookiesFromPreviousRequest(true);
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		
		System.out.println("REST Server started with '" + s_host + ":" + i_port + "'");
		System.out.println("You can call the REST methods with the following menu...");
		
		String s_url = "https://" + s_host + ":" + i_port;
		String s_requestPath = "/persons";
		net.forestany.forestj.lib.net.http.RequestType e_requestTypeMethod = net.forestany.forestj.lib.net.http.RequestType.GET;
		java.util.Map<String,Object> m_requestParamters = new java.util.LinkedHashMap<String,Object>();
		
		int i_input = -1;
		
		do {
			System.out.println("Testing Tiny Rest Server + Client");
			
			System.out.println("");
			
			System.out.println("Request path: " + s_requestPath);
			System.out.println("Request type method: " + e_requestTypeMethod);
			System.out.println("Request parmaters:");
			
			if (m_requestParamters.size() > 0) {
				for (java.util.Map.Entry<String, Object> o_pair : m_requestParamters.entrySet()) {
					System.out.println("\t" + o_pair.getKey() + " = " + o_pair.getValue());
				}
			}
			 
			System.out.println("");
			
			System.out.println("[1] change request path e.g. /persons/4/messages");
			System.out.println("[2] set request type method");
			System.out.println("[3] add request parameters");
			System.out.println("[4] execute request");
			System.out.println("[0] quit");
			
			System.out.println("");
			
			i_input = net.forestany.forestj.lib.Console.consoleInputInteger("Enter menu number[1-4;0]: ", "Invalid input.", "Please enter a value.");
		
			System.out.println("");
			
			if (i_input == 1) {
				s_requestPath = net.forestany.forestj.lib.Console.consoleInputString("Request path(e.g. '/persons/2'): ", false, "Please enter a value.");
			} else if (i_input == 2) {
				boolean b_valid = false;
				
				do {
					String s_foo = net.forestany.forestj.lib.Console.consoleInputString("Request type method: ", false, "Please enter a value.");
					
					switch (s_foo) {
					case "GET":
						e_requestTypeMethod = net.forestany.forestj.lib.net.http.RequestType.GET;
						b_valid = true;
						break;
					case "POST":
						e_requestTypeMethod = net.forestany.forestj.lib.net.http.RequestType.POST;
						b_valid = true;
						break;
					case "PUT":
						e_requestTypeMethod = net.forestany.forestj.lib.net.http.RequestType.PUT;
						b_valid = true;
						break;
					case "DELETE":
						e_requestTypeMethod = net.forestany.forestj.lib.net.http.RequestType.DELETE;
						b_valid = true;
						break;
					default:
						System.out.println("Please enter a valid request type method. [GET|POST|PUT|DELETE]");
						break;
					}
				} while (!b_valid);
			} else if (i_input == 3) {
				System.out.println("Request parameters string example #1: \tPIN=621897&Name=Max Mustermann&Age=35&City=Bochum&Country=DE");
				System.out.println("Request parameters string example #2: \tTo=Jennifer Garcia&Subject=Hey&Message=How are you doing?");
				String s_foo = net.forestany.forestj.lib.Console.consoleInputString("Request parameters(e.g. key1=value1&key2=value2): ", false, "Please enter a value.");
				
				if (s_foo.contains("&")) {
					String[] a_keyValuePairs = s_foo.split("&");
					
					for (String s_foo2 : a_keyValuePairs) {
						if (s_foo2.contains("=")) {
							String[] a_keyValuePair = s_foo2.split("=");
							
							m_requestParamters.put(a_keyValuePair[0], a_keyValuePair[1]);
						}
					}
				} else {
					if (s_foo.contains("=")) {
						String[] a_keyValuePair = s_foo.split("=");
						
						m_requestParamters.put(a_keyValuePair[0], a_keyValuePair[1]);
					}
				}
			} else if (i_input == 4) {
				o_clientTask.setRequest(s_url + s_requestPath, e_requestTypeMethod);
				
				if ( (e_requestTypeMethod != net.forestany.forestj.lib.net.http.RequestType.GET) && (m_requestParamters.size() > 0) ) {
					o_clientTask.setContentType(net.forestany.forestj.lib.net.http.PostType.HTML);
				}
				
				if (m_requestParamters.size() > 0) {
					for (java.util.Map.Entry<String, Object> o_pair : m_requestParamters.entrySet()) {
						o_clientTask.addRequestParameter(o_pair.getKey(), o_pair.getValue());
					}
				}
				
				o_clientTask.executeRequest();
				
				System.out.println(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage());
				System.out.println(o_clientTask.getResponse());
				
				e_requestTypeMethod = net.forestany.forestj.lib.net.http.RequestType.GET;
				m_requestParamters = new java.util.LinkedHashMap<String,Object>();
				o_clientTask.setRequest(s_url, e_requestTypeMethod);
			}
			
			System.out.println("");
			
		} while (i_input != 0);
		
		/* ------------------------------------------ */
		/* Stop SERVER + Close CLIENT                 */
		/* ------------------------------------------ */
		
		if (o_socketReceive != null) {
			o_socketReceive.stop();
		}
		
		if (o_socketSend != null) {
			o_socketSend.stop();
		}
	}
}
