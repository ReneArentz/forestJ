package net.forestany.forestj.sandbox.util;

public class MailTest {
	private static class MailLoginData {
		public String s_imapSmtpServer = "127.0.0.1;STARTTLS";
		public int i_imapSmtpServerPort = 587;
		public String s_imapSmtpUser = "postmaster@mshome.net";
		public String s_imapSmtpPass = "postmaster";
		
		public String s_imapMailServer = "127.0.0.1;STARTTLS";
		public int i_imapMailServerPort = 143;
		public String s_imapMailUserOne = "test1@mshome.net";
		public String s_imapMailPassOne = "Testtest1!";
		public String s_imapMailUserTwo = "test2@mshome.net";
		public String s_imapMailPassTwo = "Testtest1!";
		
		public String s_trustStorePath = null;
		public String s_trustStorePass = "123456";
		
		public MailLoginData(String s_resourcesDirectory, String p_s_imapServerIp) {
			this.s_trustStorePath = s_resourcesDirectory + "TrustStore-mail.p12";
			s_imapSmtpServer = p_s_imapServerIp + ";STARTTLS";
			s_imapMailServer = p_s_imapServerIp + ";STARTTLS";
		}
	}
	
	public static void testMail(String p_s_currentDirectory, String p_s_mailServerIp) throws Exception {
		System.setProperty("https.protocols", "TLSv1.2");
		
		String s_currentDirectory = p_s_currentDirectory;
//		String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testMail" + net.forestany.forestj.lib.io.File.DIR;
		String s_resourcesDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "mail" + net.forestany.forestj.lib.io.File.DIR;
	
		MailLoginData o_mailLoginData = new MailLoginData(s_resourcesDirectory, p_s_mailServerIp);
		
//		if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
//			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
//		}
//		
//		net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
//		
//		if (!net.forestany.forestj.lib.io.File.folderExists(s_testDirectory)) {
//			throw new Exception("directory[" + s_testDirectory + "] does not exist");
//		}
		
		net.forestany.forestj.lib.net.mail.Client o_mailClient = new net.forestany.forestj.lib.net.mail.Client(o_mailLoginData.s_imapSmtpServer, o_mailLoginData.i_imapSmtpServerPort, o_mailLoginData.s_imapSmtpUser, o_mailLoginData.s_imapSmtpPass, o_mailLoginData.s_trustStorePath, o_mailLoginData.s_trustStorePass);
		
		net.forestany.forestj.lib.net.mail.Message o_message = new net.forestany.forestj.lib.net.mail.Message("test1@mshome.net", "test2@mshome.net", "#1 To test1 and test2 plain text", "send from postmaster");
		o_mailClient.sendMessage(o_message);
		
		System.out.println("Send message with plain text to 'test1@mshome.net' and 'test2@mshome.net'");
		
		o_message = new net.forestany.forestj.lib.net.mail.Message("test1@mshome.net", "#2 To test1 plain text", "send from postmaster");
		o_mailClient.sendMessage(o_message);
		
		System.out.println("Send message with plain text to 'test1@mshome.net'");
		
		o_message = new net.forestany.forestj.lib.net.mail.Message("test1@mshome.net", "test2@mshome.net", "#3 To test1 and test2 plain text and html text", "*bold* /italic/" + net.forestany.forestj.lib.io.File.NEWLINE + "_underline_" + net.forestany.forestj.lib.io.File.NEWLINE + "send from postmaster");
		o_message.setHtml("<html>\r\n"
				+ "  <head>\r\n"
				+ "    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\r\n"
				+ "  </head>\r\n"
				+ "  <body>\r\n"
				+ "    <p><b>bold</b> <i>italic</i></p>\r\n"
				+ "    <p><u>underline</u><br>\r\n"
				+ "    </p>\r\n"
				+ "    <p>send from postmaster</p>\r\n"
				+ "  </body>\r\n"
				+ "</html>");
		o_mailClient.sendMessage(o_message);
		
		System.out.println("Send message with plain and html text to 'test1@mshome.net' and 'test2@mshome.net'");
		
		o_message = new net.forestany.forestj.lib.net.mail.Message("test1@mshome.net", "#4 To test1 plain text and html text", "*bold*" + net.forestany.forestj.lib.io.File.NEWLINE + "_underline_" + net.forestany.forestj.lib.io.File.NEWLINE + "send from postmaster");
		o_message.setHtml("<html>\r\n"
				+ "  <head>\r\n"
				+ "    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\r\n"
				+ "  </head>\r\n"
				+ "  <body>\r\n"
				+ "    <p><b>bold</b></p>\r\n"
				+ "    <p><u>underline</u><br>\r\n"
				+ "    </p>\r\n"
				+ "    <p>send from postmaster</p>\r\n"
				+ "  </body>\r\n"
				+ "</html>");
		o_mailClient.sendMessage(o_message);
		
		System.out.println("Send message with plain and html text to 'test1@mshome.net'");
		
		o_message = new net.forestany.forestj.lib.net.mail.Message("test1@mshome.net;test2@mshome.net", "#5 To test1 and test2 plain text and html text and attachments", "*bold_attachments* /italic_attachments/" + net.forestany.forestj.lib.io.File.NEWLINE + "_underline_attachments_" + net.forestany.forestj.lib.io.File.NEWLINE + "send from postmaster");
		o_message.setHtml("<html>\r\n"
				+ "  <head>\r\n"
				+ "    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\r\n"
				+ "  </head>\r\n"
				+ "  <body>\r\n"
				+ "    <p><b>bold_attachments</b> <i>italic_attachments</i></p>\r\n"
				+ "    <p><u>underline_attachments</u><br>\r\n"
				+ "    </p>\r\n"
				+ "    <p>send from postmaster</p>\r\n"
				+ "  </body>\r\n"
				+ "</html>");
		o_message.addAttachment(s_resourcesDirectory + "attachment1.txt");
		o_message.addAttachment(s_resourcesDirectory + "attachment2.pdf");
		o_mailClient.sendMessage(o_message);
		
		System.out.println("Send message with plain and html text and two attachments to 'test1@mshome.net' and 'test2@mshome.net'");
		
		Thread.sleep(1000);
		
		o_mailClient = new net.forestany.forestj.lib.net.mail.Client(o_mailLoginData.s_imapMailServer, o_mailLoginData.i_imapMailServerPort, o_mailLoginData.i_imapSmtpServerPort, o_mailLoginData.s_imapMailUserOne, o_mailLoginData.s_imapMailPassOne, o_mailLoginData.s_trustStorePath, o_mailLoginData.s_trustStorePass);
		
		int i_messageAmountInbox = o_mailClient.getMessagesAmount();
		
		System.out.println("Amount of total messages in imap(s) inbox: " + i_messageAmountInbox);
		
		if (i_messageAmountInbox != 5) {
			throw new Exception("amount of total messages in imap(s) inbox is not '5'");
		}
		
		java.util.List<net.forestany.forestj.lib.net.mail.Message> a_messages = o_mailClient.getMessages(null, false, false, true);
		
		String s_messageId_1 = null;
		String s_messageId_2 = null;
		String s_messageId_5 = null;
		
		for (net.forestany.forestj.lib.net.mail.Message o_messageObj : a_messages) {
			if (o_messageObj.getSubject().contentEquals("#1 To test1 and test2 plain text")) {
				s_messageId_1 = o_messageObj.getMessageId();
			} else if (o_messageObj.getSubject().contentEquals("#2 To test1 plain text")) {
				s_messageId_2 = o_messageObj.getMessageId();
			} else if (o_messageObj.getSubject().contentEquals("#5 To test1 and test2 plain text and html text and attachments")) {
				s_messageId_5 = o_messageObj.getMessageId();
			}
			
			System.out.println("-------------------------------------------------------");
			System.out.println("-------------------------------------------------------");
			System.out.println("-------------------------------------------------------");
			System.out.println(o_messageObj.toString(true));
			System.out.println("-------------------------------------------------------");
			System.out.println("-------------------------------------------------------");
			System.out.println("-------------------------------------------------------");
		}
		
		o_message = o_mailClient.getMessageById(s_messageId_1, true);
		
		if (!o_message.getSubject().contentEquals("#1 To test1 and test2 plain text")) {
			throw new Exception("subject of message #1 has unexpected value");
		}
		
		o_message = o_mailClient.getMessageById(s_messageId_2, true);
		
		if (!o_message.getSubject().contentEquals("#2 To test1 plain text")) {
			throw new Exception("subject of message #2 has unexpected value");
		}
		
		o_message = o_mailClient.getMessageById(s_messageId_5, true);
		
		if (!o_message.getSubject().contentEquals("#5 To test1 and test2 plain text and html text and attachments")) {
			throw new Exception("subject of message #5 has unexpected value");
		}
		
		if (o_message.hasAttchments()) {
			throw new Exception("message #5 has attachments, although we ignore attachments with 'GetMessageById'");
		}
		
		o_message = o_mailClient.getMessageById(s_messageId_2, true);
		o_message.setFrom(null);
		o_message.setTo("test2@mshome.net");
		o_message.setSubject("FW: " + o_message.getSubject());
		o_message.setText("A message from postmaster" + net.forestany.forestj.lib.io.File.NEWLINE + o_message.getText());
		o_mailClient.sendMessage(o_message);
		
		System.out.println("Forwarded message with plain and html text to 'test2@mshome.net'");
		
		o_mailClient.expungeFolder();
		
		System.out.println("Deleted all messages in 'INBOX' of 'test1@mshome.net'");
		
		o_mailClient.expungeFolder("Sent");
		
		System.out.println("Deleted all messages in 'Sent' of 'test1@mshome.net'");
		
		Thread.sleep(1000);
		
		o_mailClient = new net.forestany.forestj.lib.net.mail.Client(o_mailLoginData.s_imapMailServer, o_mailLoginData.i_imapMailServerPort, o_mailLoginData.i_imapSmtpServerPort, o_mailLoginData.s_imapMailUserTwo, o_mailLoginData.s_imapMailPassTwo, o_mailLoginData.s_trustStorePath, o_mailLoginData.s_trustStorePass);
		
		i_messageAmountInbox = o_mailClient.getMessagesAmount();
		
		System.out.println("Amount of total messages in imap(s) inbox: " + i_messageAmountInbox);
		
		if (i_messageAmountInbox != 4) {
			throw new Exception("amount of total messages in imap(s) inbox is not '4'");
		}
		
		o_mailClient.expungeFolder();
		
		System.out.println("Deleted all messages in 'INBOX' of 'test2@mshome.net'");
		
//		net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
//		
//		if (net.forestany.forestj.lib.io.File.folderExists(s_testDirectory)) {
//			throw new Exception("directory[" + s_testDirectory + "] does exist");
//		}
	}
}
