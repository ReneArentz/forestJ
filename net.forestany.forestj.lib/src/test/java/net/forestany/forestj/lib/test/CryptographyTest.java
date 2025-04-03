package net.forestany.forestj.lib.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CryptographyTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testCryptography() {
		try {
			String s_commonSecretPassphrase = "123456789012345678901234567890123456";
			
			/* some test messages */
			java.util.List<String> a_messages = java.util.Arrays.asList(
				new String[] {
					/* 001 length */	"1",	
					/* 008 length */	"12345678",
					/* 016 length */	"1234567890123456",
					/* 032 length */	"12345678901234567890123456789012",
					/* 063 length */	"123456789012345678901234567890123456789012345678901234567890123",
					/* 064 length */	"1234567890123456789012345678901234567890123456789012345678901234",
					/* 108 length */	"123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678",
					/* 1500 length */	"123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890",
					/* 1468 length */	"1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678"
				}
			);
			
			for (String s_message : a_messages) {
				/* static encrypt and decrypt each message with 128-bit key */
				byte[] a_encrypted = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(s_message.getBytes(java.nio.charset.StandardCharsets.UTF_8), s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
				byte[] a_decrypted = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_encrypted, s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
				
				assertTrue(
						a_encrypted.length == (s_message.length() + 28),
						a_encrypted.length + " != " + (s_message.length() + 28)
				);
				assertTrue(
						a_decrypted.length == s_message.length(),
						a_decrypted.length + " != " + s_message.length()
				);
				assertTrue(
						java.util.Arrays.equals(s_message.getBytes(java.nio.charset.StandardCharsets.UTF_8), a_decrypted),
						"difference in byte arrays"
				);
				
				/* static encrypt and decrypt each message with 256-bit key */
				a_encrypted = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(s_message.getBytes(java.nio.charset.StandardCharsets.UTF_8), s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
				a_decrypted = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_encrypted, s_commonSecretPassphrase);
				
				assertTrue(
						a_encrypted.length == (s_message.length() + 28),
						a_encrypted.length + " != " + (s_message.length() + 28) 
				);
				assertTrue(
						a_decrypted.length == s_message.length(),
						a_decrypted.length + " != " + s_message.length()
				);
				assertTrue(
						java.util.Arrays.equals(s_message.getBytes(), a_decrypted),
						"difference in byte arrays"
				);
			}
			
			net.forestany.forestj.lib.Cryptography o_cryptography128 = new net.forestany.forestj.lib.Cryptography(s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
			
			for (String s_message : a_messages) {
				/* encrypt and decrypt each message with 128-bit key and cryptography instance */
				byte[] a_encrypted = o_cryptography128.encrypt(s_message.getBytes(java.nio.charset.StandardCharsets.UTF_8));
				byte[] a_decrypted = o_cryptography128.decrypt(a_encrypted);
				
				assertTrue(
						a_encrypted.length == (s_message.length() + 16),
						a_encrypted.length + " != " + (s_message.length() + 16)
				);
				assertTrue(
						a_decrypted.length == s_message.length(),
						a_decrypted.length + " != " + s_message.length()
				);
				assertTrue(
						java.util.Arrays.equals(s_message.getBytes(java.nio.charset.StandardCharsets.UTF_8), a_decrypted),
						"difference in byte arrays"
				);
			}
			
			net.forestany.forestj.lib.Cryptography o_cryptography256 = new net.forestany.forestj.lib.Cryptography(s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
			
			for (String s_message : a_messages) {
				/* encrypt and decrypt each message with 256-bit key and cryptography instance */
				byte[] a_encrypted = o_cryptography256.encrypt(s_message.getBytes(java.nio.charset.StandardCharsets.UTF_8));
				byte[] a_decrypted = o_cryptography256.decrypt(a_encrypted);
				
				assertTrue(
						a_encrypted.length == (s_message.length() + 16),
						a_encrypted.length + " != " + (s_message.length() + 16)
				);
				assertTrue(
						a_decrypted.length == s_message.length(),
						a_decrypted.length + " != " + s_message.length()
				);
				assertTrue(
						java.util.Arrays.equals(s_message.getBytes(java.nio.charset.StandardCharsets.UTF_8), a_decrypted),
						"difference in byte arrays"
				);
			}
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
