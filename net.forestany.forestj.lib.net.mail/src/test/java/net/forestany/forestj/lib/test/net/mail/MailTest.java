package net.forestany.forestj.lib.test.net.mail;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test mail
 */
public class MailTest {
	/**
	 * method to test mail
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testMail() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			System.setProperty("https.protocols", "TLSv1.2");
			
			String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testMail" + net.forestany.forestj.lib.io.File.DIR;
			String s_resourcesDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "mail" + net.forestany.forestj.lib.io.File.DIR;
		
			MailLoginData o_mailLoginData = new MailLoginData(s_resourcesDirectory, "172.24.87.179", "172.24.87.179");
			
			runMail(s_testDirectory, s_resourcesDirectory, o_mailLoginData);
		} catch (Exception o_exc) {
			o_exc.printStackTrace();
			fail(o_exc.getMessage());
		}
	}
	
	private class MailLoginData {
		public String s_pop3SmtpServer = "127.0.0.1;STARTTLS";
		public int i_pop3SmtpServerPort = 587;
		public String s_pop3SmtpUser = "postmaster@mshome.net";
		public String s_pop3SmtpPass = "postmaster";
		
		public String s_pop3MailServer = "127.0.0.1;STARTTLS";
		public int i_pop3MailServerPort = 110;
		public String s_pop3MailUser = "test1@mshome.net";
		public String s_pop3MailPass = "Testtest1!";
		
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
		
		public MailLoginData(String p_s_resourcesDirectory, String p_s_pop3ServerIp, String p_s_imapServerIp) {
			this.s_trustStorePath = p_s_resourcesDirectory + "TrustStore-mail.p12";
			s_pop3SmtpServer = p_s_pop3ServerIp + ";STARTTLS";
			s_pop3MailServer = p_s_pop3ServerIp + ";STARTTLS";
			s_imapSmtpServer = p_s_imapServerIp + ";STARTTLS";
			s_imapMailServer = p_s_imapServerIp + ";STARTTLS";
		}
	}
	
	private static void runMail(String p_s_testDirectory, String p_s_resourcesDirectory, MailLoginData p_o_mailLoginData) throws Exception {
		if ( net.forestany.forestj.lib.io.File.folderExists(p_s_testDirectory) ) {
			net.forestany.forestj.lib.io.File.deleteDirectory(p_s_testDirectory);
		}
		
		net.forestany.forestj.lib.io.File.createDirectory(p_s_testDirectory);
		assertTrue(
			net.forestany.forestj.lib.io.File.folderExists(p_s_testDirectory),
			"directory[" + p_s_testDirectory + "] does not exist"
		);
		
		runPop3(p_s_testDirectory, p_s_resourcesDirectory, p_o_mailLoginData);
		
		if (net.forestany.forestj.lib.io.File.exists(p_s_testDirectory + "attachment1.txt")) {
			net.forestany.forestj.lib.io.File.deleteFile(p_s_testDirectory + "attachment1.txt");
		}
		
		if (net.forestany.forestj.lib.io.File.exists(p_s_testDirectory + "attachment2.pdf")) {
			net.forestany.forestj.lib.io.File.deleteFile(p_s_testDirectory + "attachment2.pdf");
		}
		
		runImap(p_s_testDirectory, p_s_resourcesDirectory, p_o_mailLoginData);
		
		/* ******** */
		/* clean up */
		/* ******** */
		
		net.forestany.forestj.lib.io.File.deleteDirectory(p_s_testDirectory);
		assertFalse(
			net.forestany.forestj.lib.io.File.folderExists(p_s_testDirectory),
			"directory[" + p_s_testDirectory + "] does exist"
		);
	}

	private static void runPop3(String p_s_testDirectory, String p_s_resourcesDirectory, MailLoginData p_o_mailLoginData) throws Exception {
		net.forestany.forestj.lib.net.mail.Client o_mailClient = new net.forestany.forestj.lib.net.mail.Client(p_o_mailLoginData.s_pop3SmtpServer, p_o_mailLoginData.i_pop3SmtpServerPort, p_o_mailLoginData.s_pop3SmtpUser, p_o_mailLoginData.s_pop3SmtpPass, p_o_mailLoginData.s_trustStorePath, p_o_mailLoginData.s_trustStorePass);
		
		assertTrue(o_mailClient != null, "mail client object is null");
		
		net.forestany.forestj.lib.net.mail.Message o_message = new net.forestany.forestj.lib.net.mail.Message("test1@mshome.net", "Test 1", "send from JUnit 5 Test");
		o_mailClient.sendMessage(o_message);
		
		o_message = new net.forestany.forestj.lib.net.mail.Message("test1@mshome.net", "Test 2", "send from JUnit 5 Test, again");
		o_mailClient.sendMessage(o_message);
		
		o_message = new net.forestany.forestj.lib.net.mail.Message("test1@mshome.net", "Test 3", "send from JUnit 5 Test, with attachments");
		o_message.addAttachment(p_s_resourcesDirectory + "attachment1.txt");
		o_message.addAttachment(p_s_resourcesDirectory + "attachment2.pdf");
		o_mailClient.sendMessage(o_message);
		
		Thread.sleep(1000);
		
		o_mailClient = new net.forestany.forestj.lib.net.mail.Client(p_o_mailLoginData.s_pop3MailServer, null, p_o_mailLoginData.i_pop3MailServerPort, 1, p_o_mailLoginData.s_pop3MailUser, p_o_mailLoginData.s_pop3MailPass, null, null, true, true, false, p_o_mailLoginData.s_trustStorePath, p_o_mailLoginData.s_trustStorePass);
		
		assertTrue(o_mailClient != null, "mail client object is null");
		
		assertTrue(o_mailClient.getMessagesAmount(true) == 3, "amount of unread messages in pop3(s) inbox is not '3'");
		assertTrue(o_mailClient.getMessagesAmount() == 3, "amount of total messages in pop3(s) inbox is not '3'");
		
		java.util.List<net.forestany.forestj.lib.net.mail.Message> a_messages = o_mailClient.getMessages(null, true, false);
		
		boolean b_attachmentsFound = false;
		
		for (net.forestany.forestj.lib.net.mail.Message o_messageObj : a_messages) {
			if (o_messageObj.getSubject().contentEquals("Test 1")) {
				assertEquals(o_messageObj.getText().trim(), "send from JUnit 5 Test", "mail text from message ist not 'send from JUnit 5 Test', but '" + o_messageObj.getText().trim() + "'");
			} else if (o_messageObj.getSubject().contentEquals("Test 2")) {
				assertEquals(o_messageObj.getText().trim(), "send from JUnit 5 Test, again", "mail text from message ist not 'send from JUnit 5 Test, again', but '" + o_messageObj.getText().trim() + "'");
			} else if (o_messageObj.getSubject().contentEquals("Test 3")) {
				assertEquals(o_messageObj.getText().trim(), "send from JUnit 5 Test, with attachments", "mail text from message ist not 'send from JUnit 5 Test, with attachments', but '" + o_messageObj.getText().trim() + "'");
			}
			
			if (o_messageObj.hasAttchments()) {
				for (int i = 0; i < o_messageObj.getAttachments().size(); i++) {
					o_messageObj.saveAllAttachments(p_s_testDirectory);
					b_attachmentsFound = true;
				}
			}
		}
		
		assertTrue(b_attachmentsFound, "no attachments found after reading all messages from pop3(s) inbox");
		
		assertEquals(
			net.forestany.forestj.lib.io.File.hashFile(p_s_resourcesDirectory +  "attachment1.txt", "SHA-256"),
			net.forestany.forestj.lib.io.File.hashFile(p_s_testDirectory +  "attachment1.txt", "SHA-256"),
			"hash values of resource and test attachment 'attachment1.txt' do not match to each other"
		);
		
		assertEquals(
			net.forestany.forestj.lib.io.File.hashFile(p_s_resourcesDirectory +  "attachment2.pdf", "SHA-256"),
			net.forestany.forestj.lib.io.File.hashFile(p_s_testDirectory +  "attachment2.pdf", "SHA-256"),
			"hash values of resource and test attachment 'attachment2.pdf' do not match to each other"
		);
	}

	private static void runImap(String p_s_testDirectory, String p_s_resourcesDirectory, MailLoginData p_o_mailLoginData) throws Exception {
		net.forestany.forestj.lib.net.mail.Client o_mailClient = new net.forestany.forestj.lib.net.mail.Client(p_o_mailLoginData.s_imapSmtpServer, p_o_mailLoginData.i_imapSmtpServerPort, p_o_mailLoginData.s_imapSmtpUser, p_o_mailLoginData.s_imapSmtpPass, p_o_mailLoginData.s_trustStorePath, p_o_mailLoginData.s_trustStorePass);
		
		assertTrue(o_mailClient != null, "mail client object is null");
		
		net.forestany.forestj.lib.net.mail.Message o_message = new net.forestany.forestj.lib.net.mail.Message("test1@mshome.net", "Test 1", "send from JUnit 5 Test");
		o_mailClient.sendMessage(o_message);
		
		o_message = new net.forestany.forestj.lib.net.mail.Message("test1@mshome.net", "Test 2", "send from JUnit 5 Test, again");
		o_mailClient.sendMessage(o_message);
		
		o_message = new net.forestany.forestj.lib.net.mail.Message("test1@mshome.net", "Test 3", "send from JUnit 5 Test, with attachments");
		o_message.addAttachment(p_s_resourcesDirectory + "attachment1.txt");
		o_message.addAttachment(p_s_resourcesDirectory + "attachment2.pdf");
		o_mailClient.sendMessage(o_message);
		
		Thread.sleep(1000);
		
		o_mailClient = new net.forestany.forestj.lib.net.mail.Client(p_o_mailLoginData.s_imapMailServer, p_o_mailLoginData.i_imapMailServerPort, p_o_mailLoginData.i_imapSmtpServerPort, p_o_mailLoginData.s_imapMailUserOne, p_o_mailLoginData.s_imapMailPassOne, p_o_mailLoginData.s_trustStorePath, p_o_mailLoginData.s_trustStorePass);
		
		assertTrue(o_mailClient != null, "mail client object is null");
		
		java.util.List<net.forestany.forestj.lib.net.mail.Message> a_messages = o_mailClient.getMessages(null, false, false, true);
		
		assertTrue(o_mailClient.getMessagesAmount(true) == 0, "amount of unread messages in imap(s) inbox is not '0'");
		assertTrue(o_mailClient.getMessagesAmount() == 3, "amount of total messages in imap(s) inbox is not '3'");
		
		boolean b_attachmentsFound = false;
		
		for (net.forestany.forestj.lib.net.mail.Message o_messageObj : a_messages) {
			if (o_messageObj.getSubject().contentEquals("Test 1")) {
				assertEquals(o_messageObj.getText().trim(), "send from JUnit 5 Test", "mail text from message ist not 'send from JUnit 5 Test', but '" + o_messageObj.getText().trim() + "'");
			} else if (o_messageObj.getSubject().contentEquals("Test 2")) {
				assertEquals(o_messageObj.getText().trim(), "send from JUnit 5 Test, again", "mail text from message ist not 'send from JUnit 5 Test, again', but '" + o_messageObj.getText().trim() + "'");
			} else if (o_messageObj.getSubject().contentEquals("Test 3")) {
				assertEquals(o_messageObj.getText().trim(), "send from JUnit 5 Test, with attachments", "mail text from message ist not 'send from JUnit 5 Test, with attachments', but '" + o_messageObj.getText().trim() + "'");
			}
			
			if (o_messageObj.hasAttchments()) {
				for (int i = 0; i < o_messageObj.getAttachments().size(); i++) {
					o_messageObj.saveAllAttachments(p_s_testDirectory);
					b_attachmentsFound = true;
				}
			}
		}
		
		assertTrue(b_attachmentsFound, "no attachments found after reading all messages from imap(s) inbox");
		
		assertEquals(
			net.forestany.forestj.lib.io.File.hashFile(p_s_resourcesDirectory +  "attachment1.txt", "SHA-256"),
			net.forestany.forestj.lib.io.File.hashFile(p_s_testDirectory +  "attachment1.txt", "SHA-256"),
			"hash values of resource and test attachment 'attachment1.txt' do not match to each other"
		);
		
		assertEquals(
			net.forestany.forestj.lib.io.File.hashFile(p_s_resourcesDirectory +  "attachment2.pdf", "SHA-256"),
			net.forestany.forestj.lib.io.File.hashFile(p_s_testDirectory +  "attachment2.pdf", "SHA-256"),
			"hash values of resource and test attachment 'attachment2.pdf' do not match to each other"
		);
		
		a_messages = o_mailClient.getMessages(null, true, true, false);
		
		assertTrue(o_mailClient.getMessagesAmount(true) == 0, "amount of unread messages in imap(s) inbox is not '0'");
		assertTrue(o_mailClient.getMessagesAmount() == 0, "amount of total messages in imap(s) inbox is not '0'");
		
		Thread.sleep(1000);
		
		o_mailClient = new net.forestany.forestj.lib.net.mail.Client(p_o_mailLoginData.s_imapSmtpServer, p_o_mailLoginData.i_imapSmtpServerPort, p_o_mailLoginData.s_imapSmtpUser, p_o_mailLoginData.s_imapSmtpPass, p_o_mailLoginData.s_trustStorePath, p_o_mailLoginData.s_trustStorePass);
		
		assertTrue(o_mailClient != null, "mail client object is null");
		
		o_message = new net.forestany.forestj.lib.net.mail.Message("test1@mshome.net", "test2@mshome.net", "#1 To test1 and test2 plain text", "send from postmaster");
		o_mailClient.sendMessage(o_message);
		
		o_message = new net.forestany.forestj.lib.net.mail.Message("test1@mshome.net", "#2 To test1 plain text", "send from postmaster");
		o_mailClient.sendMessage(o_message);
		
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
		o_message.addAttachment(p_s_resourcesDirectory + "attachment1.txt");
		o_message.addAttachment(p_s_resourcesDirectory + "attachment2.pdf");
		o_mailClient.sendMessage(o_message);
		
		Thread.sleep(1000);
		
		o_mailClient = new net.forestany.forestj.lib.net.mail.Client(p_o_mailLoginData.s_imapMailServer, p_o_mailLoginData.i_imapMailServerPort, p_o_mailLoginData.i_imapSmtpServerPort, p_o_mailLoginData.s_imapMailUserOne, p_o_mailLoginData.s_imapMailPassOne, p_o_mailLoginData.s_trustStorePath, p_o_mailLoginData.s_trustStorePass);
		
		assertTrue(o_mailClient != null, "mail client object is null");
		
		assertTrue(o_mailClient.getMessagesAmount() == 5, "amount of total messages in imap(s) inbox is not '5'");
		
		a_messages = o_mailClient.getMessages(null, false, false, true);
		
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
		}
		
		o_message = o_mailClient.getMessageById(s_messageId_1, true);
		assertEquals("#1 To test1 and test2 plain text", o_message.getSubject(), "subject of message #1 has unexpected value");
		
		o_message = o_mailClient.getMessageById(s_messageId_2, true);
		assertEquals("#2 To test1 plain text", o_message.getSubject(), "subject of message #2 has unexpected value");
		
		o_message = o_mailClient.getMessageById(s_messageId_5, true);
		assertEquals("#5 To test1 and test2 plain text and html text and attachments", o_message.getSubject(), "subject of message #5 has unexpected value");
		assertTrue( !o_message.hasAttchments() ,"message #5 has attachments, although we ignore attachments with 'GetMessageById'");
		
		o_message = o_mailClient.getMessageById(s_messageId_2, true);
		o_message.setFrom(null);
		o_message.setTo("test2@mshome.net");
		o_message.setSubject("FW: " + o_message.getSubject());
		o_message.setText("A message from postmaster" + net.forestany.forestj.lib.io.File.NEWLINE + o_message.getText());
		o_mailClient.sendMessage(o_message);
		
		o_mailClient.createSubFolder("subfolder");
		
		o_mailClient.changeToFolder("subfolder");
		
		o_mailClient.createSubFolder("another_subfolder");
		
		o_mailClient.changeToFolder("..");
		
		o_mailClient.moveMessages(java.util.Arrays.asList(s_messageId_1, s_messageId_5), "INBOX/subfolder/another_subfolder");
		
		o_mailClient.changeToFolder("subfolder");
		
		o_mailClient.renameSubFolder("another_subfolder", "sub");
		
		o_mailClient.changeToFolder("sub");
		
		o_mailClient.moveAllMessages("INBOX/subfolder", "FLAGGED", true);
		
		o_mailClient.deleteFolder();
		
		o_mailClient.setSeen(s_messageId_1);
		
		o_message = o_mailClient.getMessageById(s_messageId_1, true);
		assertTrue( o_message.hasFlag("FLAGGED") , "message #1 has not the FLAGGED flag");
		assertTrue( o_message.hasFlag("SEEN") , "message #1 has not the SEEN flag");
		
		o_mailClient.expungeFolder();
		
		o_mailClient.expungeFolder("INBOX");
		
		o_mailClient.expungeFolder("Sent");
		
		o_mailClient.deleteFolder();
		
		Thread.sleep(1000);
		
		o_mailClient = new net.forestany.forestj.lib.net.mail.Client(p_o_mailLoginData.s_imapMailServer, p_o_mailLoginData.i_imapMailServerPort, p_o_mailLoginData.i_imapSmtpServerPort, p_o_mailLoginData.s_imapMailUserTwo, p_o_mailLoginData.s_imapMailPassTwo, p_o_mailLoginData.s_trustStorePath, p_o_mailLoginData.s_trustStorePass);
		
		assertTrue(o_mailClient != null, "mail client object is null");
		
		assertTrue(o_mailClient.getMessagesAmount() == 4, "amount of total messages in imap(s) inbox is not '4'");
		
		o_mailClient.expungeFolder();
	}
}
