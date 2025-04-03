package net.forestany.forestj.lib;

/**
 * 
 * Cryptography class to de-/encrypt data with a secret key instance, create ssl context or trustmanager with a keystore and certificate alias parameter.
 *
 */
public class Cryptography {
	
	/* Constants */
	
	/**
	 * key 128-bit constant setting
	 */
	public static final int KEY128BIT = 0;
	/**
	 * key 256-bit constant setting
	 */
	public static final int KEY256BIT = 1;
	
	/* Fields */
	
	private byte[] a_ivLocal;
    private javax.crypto.SecretKey o_secretKeyLocal;
    private javax.crypto.spec.GCMParameterSpec o_gcmParameterSpecLocal;
    
	/* Properties */
	
	/* Methods */
	
    /**
     * Constructor to create cryptography instance for encoding and decoding byte arrays with AES/GCM, one secret key during cryptography instance
     * for higher security static encoding and decoding methods are available which are always generating a new secret key, thus need higher performance.
     * 
     * @param p_s_commonSecretPassphrase						common secret passphrase string which must be at least 36 characters long
     * @param p_i_keyLengthOption								Please use ['Cryptography.KEY128BIT'] or ['Cryptography.KEY256BIT']			
     * @throws IllegalArgumentException							invalid key length option [Cryptography.KEY128BIT | Cryptography.KEY256BIT]
	 * @throws java.security.NoSuchAlgorithmException 			invalid key factory algorithm
	 * @throws java.security.spec.InvalidKeySpecException 		invalid key specifications, length of common secret passphrase, salt, iteration value or key length option
	 */
	public Cryptography(String p_s_commonSecretPassphrase, int p_i_keyLengthOption) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException {
		/* check length of common secret passphrase */
		if (p_s_commonSecretPassphrase.length() < 36) {
			throw new IllegalArgumentException("Common secret passphrase must have at least '36' characters, but has '" + p_s_commonSecretPassphrase.length() + "' characters");
		}
		
		/* check key length option */
		if ( (p_i_keyLengthOption < 0) || (p_i_keyLengthOption > 1) ) {
			throw new IllegalArgumentException("Unknown key length option[" + p_i_keyLengthOption + "]. Please use ['Cryptography.KEY128BIT'] or ['Cryptography.KEY256BIT']");
		}
		
		/* get 12 bytes for salt from common secret passphrase */
		byte[] by_utf8 = p_s_commonSecretPassphrase.getBytes(java.nio.charset.StandardCharsets.UTF_8);
		String s_utf8 = new String(by_utf8, java.nio.charset.StandardCharsets.UTF_8);
		this.a_ivLocal = s_utf8.substring(0, 12).getBytes(java.nio.charset.StandardCharsets.UTF_8);
		
		/* generate secret key for cryptography instance */
		this.o_secretKeyLocal = Cryptography.generateSecretKey(this.a_ivLocal, p_s_commonSecretPassphrase, p_i_keyLengthOption);
        this.o_gcmParameterSpecLocal = new javax.crypto.spec.GCMParameterSpec(128, this.a_ivLocal);
	}
	
	/**
	 * Encrypt data with cryptography instance configuration.
	 * 
	 * @param p_a_data												data in byte array which will be encrypted
	 * @return														encrypted byte array
	 * @throws IllegalArgumentException								byte array for encryption is empty
	 * @throws java.security.NoSuchAlgorithmException				if transformation is null, empty, in an invalid format,or if no Provider supports a CipherSpiimplementation for the specified algorithm
	 * @throws javax.crypto.NoSuchPaddingException					if transformation contains a padding scheme that is not available
	 * @throws java.security.InvalidKeyException					if the given key is inappropriate for initializing this cipher, or its key size exceeds the maximum allowable key size (as determined from the configured jurisdiction policy files).
	 * @throws java.security.InvalidAlgorithmParameterException		if the given algorithm parameters are inappropriate for this cipher,or this cipher requires algorithm parameters and parameters is null, or the given algorithm parameters imply a cryptographic strength that would exceed the legal limits (as determined from the configured jurisdiction policy files)
	 * @throws javax.crypto.IllegalBlockSizeException				if this cipher is a block cipher,no padding has been requested (only in encryption mode), and the total input length of the data processed by this cipher is not a multiple of block size; or if this encryption algorithm is unable to process the input data provided
	 * @throws javax.crypto.BadPaddingException						if this cipher is in decryption mode,and (un)padding has been requested, but the decrypted data is not bounded by the appropriate padding bytes
	 */
	public byte[] encrypt(byte[] p_a_data) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
		return this.encrypt(p_a_data, true);
	}
	
	/**
	 * Encrypt data with cryptography instance configuration.
	 * 
	 * @param p_a_data												data in byte array which will be encrypted
	 * @param p_b_skipIV											true - do not add iv part to encrypted data, false - add iv part as first part to encrypted data
	 * @return														encrypted byte array
	 * @throws IllegalArgumentException								byte array for encryption is empty
	 * @throws java.security.NoSuchAlgorithmException				if transformation is null, empty, in an invalid format,or if no Provider supports a CipherSpiimplementation for the specified algorithm
	 * @throws javax.crypto.NoSuchPaddingException					if transformation contains a padding scheme that is not available
	 * @throws java.security.InvalidKeyException					if the given key is inappropriate for initializing this cipher, or its key size exceeds the maximum allowable key size (as determined from the configured jurisdiction policy files).
	 * @throws java.security.InvalidAlgorithmParameterException		if the given algorithm parameters are inappropriate for this cipher,or this cipher requires algorithm parameters and parameters is null, or the given algorithm parameters imply a cryptographic strength that would exceed the legal limits (as determined from the configured jurisdiction policy files)
	 * @throws javax.crypto.IllegalBlockSizeException				if this cipher is a block cipher,no padding has been requested (only in encryption mode), and the total input length of the data processed by this cipher is not a multiple of block size; or if this encryption algorithm is unable to process the input data provided
	 * @throws javax.crypto.BadPaddingException						if this cipher is in decryption mode,and (un)padding has been requested, but the decrypted data is not bounded by the appropriate padding bytes
	 */
	public byte[] encrypt(byte[] p_a_data, boolean p_b_skipIV) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
												long l_foo = System.currentTimeMillis();
		
		/* check input data parameter */
		if ( (p_a_data == null) || (p_a_data.length == 0) ) {
			throw new IllegalArgumentException("Byte array for encryption is empty");
		}
		
		/* create cipher instance */
		javax.crypto.Cipher o_cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
		/* init cipher with encrypt mode */
		o_cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, this.o_secretKeyLocal, this.o_gcmParameterSpecLocal);
		/* encrypt data with cipher */
		byte[] a_encryptedData = o_cipher.doFinal(p_a_data);
        
												net.forestany.forestj.lib.Global.ilogFiner("encrypt: " + (System.currentTimeMillis() - l_foo) + " ms");
        
		/* add iv part as first part to encrypted data */
		if (!p_b_skipIV) {
			/* define new return array */
			byte[] a_encryptedDataWithIV = new byte[12 + a_encryptedData.length];
			
			/* copy iv part to return array */
			for (int i = 0; i < 12; i++) {
				a_encryptedDataWithIV[i] = this.a_ivLocal[i];
			}
			
			/* copy encrypted data to return array */
			for (int i = 0; i < a_encryptedData.length; i++) {
				a_encryptedDataWithIV[i + 12] = a_encryptedData[i];
			}
			
			/* return encrypted data with iv part */
			return a_encryptedDataWithIV;
		}
												
        return a_encryptedData;
	}
	
	/**
	 * Decrypt data with cryptography instance configuration.
	 * 
	 * @param p_a_data												data in byte array which will be decrypted
	 * @return														decrypted byte array
	 * @throws IllegalArgumentException								byte array for decryption is empty
	 * @throws java.security.NoSuchAlgorithmException				if transformation is null, empty, in an invalid format,or if no Provider supports a CipherSpiimplementation for the specified algorithm
	 * @throws javax.crypto.NoSuchPaddingException					if transformation contains a padding scheme that is not available
	 * @throws java.security.InvalidKeyException					if the given key is inappropriate for initializing this cipher, or its key size exceeds the maximum allowable key size (as determined from the configured jurisdiction policy files).
	 * @throws java.security.InvalidAlgorithmParameterException		if the given algorithm parameters are inappropriate for this cipher,or this cipher requires algorithm parameters and parameters is null, or the given algorithm parameters imply a cryptographic strength that would exceed the legal limits (as determined from the configured jurisdiction policy files)
	 * @throws javax.crypto.IllegalBlockSizeException				if this cipher is a block cipher,no padding has been requested (only in encryption mode), and the total input length of the data processed by this cipher is not a multiple of block size; or if this encryption algorithm is unable to process the input data provided
	 * @throws javax.crypto.BadPaddingException						if this cipher is in decryption mode,and (un)padding has been requested, but the decrypted data is not bounded by the appropriate padding bytes
	 */
	public byte[] decrypt(byte[] p_a_data) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
		return this.decrypt(p_a_data, true);
	}
	
	/**
	 * Decrypt data with cryptography instance configuration.
	 * 
	 * @param p_a_data												data in byte array which will be decrypted
	 * @param p_b_skipIV											true - do not read first 12 bytes as iv part, false - read first 12 bytes as iv part
	 * @return														decrypted byte array
	 * @throws IllegalArgumentException								byte array for decryption is empty
	 * @throws java.security.NoSuchAlgorithmException				if transformation is null, empty, in an invalid format,or if no Provider supports a CipherSpiimplementation for the specified algorithm
	 * @throws javax.crypto.NoSuchPaddingException					if transformation contains a padding scheme that is not available
	 * @throws java.security.InvalidKeyException					if the given key is inappropriate for initializing this cipher, or its key size exceeds the maximum allowable key size (as determined from the configured jurisdiction policy files).
	 * @throws java.security.InvalidAlgorithmParameterException		if the given algorithm parameters are inappropriate for this cipher,or this cipher requires algorithm parameters and parameters is null, or the given algorithm parameters imply a cryptographic strength that would exceed the legal limits (as determined from the configured jurisdiction policy files)
	 * @throws javax.crypto.IllegalBlockSizeException				if this cipher is a block cipher,no padding has been requested (only in encryption mode), and the total input length of the data processed by this cipher is not a multiple of block size; or if this encryption algorithm is unable to process the input data provided
	 * @throws javax.crypto.BadPaddingException						if this cipher is in decryption mode,and (un)padding has been requested, but the decrypted data is not bounded by the appropriate padding bytes
	 */
	public byte[] decrypt(byte[] p_a_data, boolean p_b_skipIV) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
		/* read first 12 bytes as iv part */
		if (!p_b_skipIV) {
			/* we must get rid of the first 12 bytes */
			byte[] o_foo = new byte[p_a_data.length - 12];
			
			/* get encrypted data without iv part */
			for (int i = 12; i < p_a_data.length; i++) {
				o_foo[i - 12] = p_a_data[i];
			}
			
			/* overwrite parameter */
			p_a_data = o_foo;
		}
		
												long l_foo = System.currentTimeMillis();
		
		/* check input data parameter */
		if ( (p_a_data == null) || (p_a_data.length == 0) ) {
			throw new IllegalArgumentException("Byte array for decryption is empty");
		}
		
		/* create cipher instance */
		javax.crypto.Cipher o_cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
		/* init cipher with decrypt mode */
		o_cipher.init(javax.crypto.Cipher.DECRYPT_MODE, this.o_secretKeyLocal, this.o_gcmParameterSpecLocal);
		/* decrypt data with cipher */
		byte[] a_decryptedData = o_cipher.doFinal(p_a_data);
		
												net.forestany.forestj.lib.Global.ilogFiner("decrypt: " + (System.currentTimeMillis() - l_foo) + " ms");
        
        return a_decryptedData;
	}
	
	/**
	 * Encrypt data with new salt and secret key instance with key length 256 bit.
	 * 
	 * @param p_a_data												data in byte array which will be encrypted
	 * @param p_s_commonSecretPassphrase							common secret passphrase string which must be at least 36 characters long
     * @return														encrypted byte array
	 * @throws IllegalArgumentException								byte array for encryption is empty
	 * @throws java.security.NoSuchAlgorithmException				if transformation is null, empty, in an invalid format,or if no Provider supports a CipherSpiimplementation for the specified algorithm
	 * @throws javax.crypto.NoSuchPaddingException					if transformation contains a padding scheme that is not available
	 * @throws java.security.InvalidKeyException					if the given key is inappropriate for initializing this cipher, or its key size exceeds the maximum allowable key size (as determined from the configured jurisdiction policy files).
	 * @throws java.security.InvalidAlgorithmParameterException		if the given algorithm parameters are inappropriate for this cipher,or this cipher requires algorithm parameters and parameters is null, or the given algorithm parameters imply a cryptographic strength that would exceed the legal limits (as determined from the configured jurisdiction policy files)
	 * @throws javax.crypto.IllegalBlockSizeException				if this cipher is a block cipher,no padding has been requested (only in encryption mode), and the total input length of the data processed by this cipher is not a multiple of block size; or if this encryption algorithm is unable to process the input data provided
	 * @throws javax.crypto.BadPaddingException						if this cipher is in decryption mode,and (un)padding has been requested, but the decrypted data is not bounded by the appropriate padding bytes
	 * @throws java.security.spec.InvalidKeySpecException			invalid key specifications, length of common secret passphrase, salt, iteration value or key length option
	 */
	public static byte[] encrypt_AES_GCM(byte[] p_a_data, String p_s_commonSecretPassphrase) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, java.security.spec.InvalidKeySpecException {
		return Cryptography.encrypt_AES_GCM(p_a_data, p_s_commonSecretPassphrase, Cryptography.KEY256BIT);
	}
	
	/**
	 * Encrypt data with new salt and secret key instance.
	 * 
	 * @param p_a_data												data in byte array which will be encrypted
	 * @param p_s_commonSecretPassphrase							common secret passphrase string which must be at least 36 characters long
     * @param p_i_keyLengthOption									Please use ['Cryptography.KEY128BIT'] or ['Cryptography.KEY256BIT']			
     * @return														encrypted byte array
	 * @throws IllegalArgumentException								byte array for encryption is empty
	 * @throws java.security.NoSuchAlgorithmException				if transformation is null, empty, in an invalid format,or if no Provider supports a CipherSpiimplementation for the specified algorithm
	 * @throws javax.crypto.NoSuchPaddingException					if transformation contains a padding scheme that is not available
	 * @throws java.security.InvalidKeyException					if the given key is inappropriate for initializing this cipher, or its key size exceeds the maximum allowable key size (as determined from the configured jurisdiction policy files).
	 * @throws java.security.InvalidAlgorithmParameterException		if the given algorithm parameters are inappropriate for this cipher,or this cipher requires algorithm parameters and parameters is null, or the given algorithm parameters imply a cryptographic strength that would exceed the legal limits (as determined from the configured jurisdiction policy files)
	 * @throws javax.crypto.IllegalBlockSizeException				if this cipher is a block cipher,no padding has been requested (only in encryption mode), and the total input length of the data processed by this cipher is not a multiple of block size; or if this encryption algorithm is unable to process the input data provided
	 * @throws javax.crypto.BadPaddingException						if this cipher is in decryption mode,and (un)padding has been requested, but the decrypted data is not bounded by the appropriate padding bytes
	 * @throws java.security.spec.InvalidKeySpecException			invalid key specifications, length of common secret passphrase, salt, iteration value or key length option
	 */
	public static byte[] encrypt_AES_GCM(byte[] p_a_data, String p_s_commonSecretPassphrase, int p_i_keyLengthOption) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, java.security.spec.InvalidKeySpecException {
												long l_foo = System.currentTimeMillis();
		
		/* check input data parameter */
		if ( (p_a_data == null) || (p_a_data.length == 0) ) {
			throw new IllegalArgumentException("Byte array for decryption is empty");
		}
		
		/* check length of common secret passphrase */
		if (p_s_commonSecretPassphrase.length() < 36) {
			throw new IllegalArgumentException("Common secret passphrase must have at least '36' characters, but has '" + p_s_commonSecretPassphrase.length() + "' characters");
		}
		
		/* check key length option */
		if ( (p_i_keyLengthOption < 0) || (p_i_keyLengthOption > 1) ) {
			throw new IllegalArgumentException("Unknown key length option[" + p_i_keyLengthOption + "]. Please use ['Cryptography.KEY128BIT'] or ['Cryptography.KEY256BIT']");
		}
		
		/* define salt byte array with 12 bytes */
		byte[] a_iv = new byte[12];
		/* fill salt byte array randomly */
		net.forestany.forestj.lib.Global.get().SecureRandom.nextBytes(a_iv);
        
        /* generate secret key for cryptography instance */
        javax.crypto.SecretKey o_secretKey = Cryptography.generateSecretKey(a_iv, p_s_commonSecretPassphrase, p_i_keyLengthOption);
        
        /* create cipher instance and gcm specifications */
        javax.crypto.Cipher o_cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
        javax.crypto.spec.GCMParameterSpec o_gcmParameterSpec = new javax.crypto.spec.GCMParameterSpec(128, a_iv);
        
        /* init cipher with encrypt mode */
        o_cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, o_secretKey, o_gcmParameterSpec);

        /* encrypt data with cipher */
        byte[] a_encryptedData = o_cipher.doFinal(p_a_data);
        
        /* set salt before encrypted data */
        java.nio.ByteBuffer o_byteBuffer = java.nio.ByteBuffer.allocate(a_iv.length + a_encryptedData.length);
        o_byteBuffer.put(a_iv);
        o_byteBuffer.put(a_encryptedData);
        
        										net.forestany.forestj.lib.Global.ilogFiner("encrypt: " + (System.currentTimeMillis() - l_foo) + " ms");
        
        return o_byteBuffer.array();
	}
	
	/**
	 * Decrypt data with new secret key instance by reading salt at the beginning of encrypted data byte array, with key length 256 bit.
	 * 
	 * @param p_a_data												data in byte array which will be decrypted
	 * @param p_s_commonSecretPassphrase							common secret passphrase string which must be at least 36 characters long
     * @return														decrypted byte array
	 * @throws IllegalArgumentException								byte array for encryption is empty
	 * @throws java.security.NoSuchAlgorithmException				if transformation is null, empty, in an invalid format,or if no Provider supports a CipherSpiimplementation for the specified algorithm
	 * @throws javax.crypto.NoSuchPaddingException					if transformation contains a padding scheme that is not available
	 * @throws java.security.InvalidKeyException					if the given key is inappropriate for initializing this cipher, or its key size exceeds the maximum allowable key size (as determined from the configured jurisdiction policy files).
	 * @throws java.security.InvalidAlgorithmParameterException		if the given algorithm parameters are inappropriate for this cipher,or this cipher requires algorithm parameters and parameters is null, or the given algorithm parameters imply a cryptographic strength that would exceed the legal limits (as determined from the configured jurisdiction policy files)
	 * @throws javax.crypto.IllegalBlockSizeException				if this cipher is a block cipher,no padding has been requested (only in encryption mode), and the total input length of the data processed by this cipher is not a multiple of block size; or if this encryption algorithm is unable to process the input data provided
	 * @throws javax.crypto.BadPaddingException						if this cipher is in decryption mode,and (un)padding has been requested, but the decrypted data is not bounded by the appropriate padding bytes
	 * @throws java.security.spec.InvalidKeySpecException			invalid key specifications, length of common secret passphrase, salt, iteration value or key length option
	 */
	public static byte[] decrypt_AES_GCM(byte[] p_a_data, String p_s_commonSecretPassphrase) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, java.security.spec.InvalidKeySpecException {
		return Cryptography.decrypt_AES_GCM(p_a_data, p_s_commonSecretPassphrase, Cryptography.KEY256BIT);
	}
	
	/**
	 * Decrypt data with new secret key instance by reading salt at the beginning of encrypted data byte array.
	 * 
	 * @param p_a_data												data in byte array which will be decrypted
	 * @param p_s_commonSecretPassphrase							common secret passphrase string which must be at least 36 characters long
     * @param p_i_keyLengthOption									Please use ['Cryptography.KEY128BIT'] or ['Cryptography.KEY256BIT']			
     * @return														decrypted byte array
	 * @throws IllegalArgumentException								byte array for encryption is empty
	 * @throws java.security.NoSuchAlgorithmException				if transformation is null, empty, in an invalid format,or if no Provider supports a CipherSpiimplementation for the specified algorithm
	 * @throws javax.crypto.NoSuchPaddingException					if transformation contains a padding scheme that is not available
	 * @throws java.security.InvalidKeyException					if the given key is inappropriate for initializing this cipher, or its key size exceeds the maximum allowable key size (as determined from the configured jurisdiction policy files).
	 * @throws java.security.InvalidAlgorithmParameterException		if the given algorithm parameters are inappropriate for this cipher,or this cipher requires algorithm parameters and parameters is null, or the given algorithm parameters imply a cryptographic strength that would exceed the legal limits (as determined from the configured jurisdiction policy files)
	 * @throws javax.crypto.IllegalBlockSizeException				if this cipher is a block cipher,no padding has been requested (only in encryption mode), and the total input length of the data processed by this cipher is not a multiple of block size; or if this encryption algorithm is unable to process the input data provided
	 * @throws javax.crypto.BadPaddingException						if this cipher is in decryption mode,and (un)padding has been requested, but the decrypted data is not bounded by the appropriate padding bytes
	 * @throws java.security.spec.InvalidKeySpecException			invalid key specifications, length of common secret passphrase, salt, iteration value or key length option
	 */
	public static byte[] decrypt_AES_GCM(byte[] p_a_data, String p_s_commonSecretPassphrase, int p_i_keyLengthOption) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, java.security.spec.InvalidKeySpecException {
												long l_foo = System.currentTimeMillis();
		
		/* check input data parameter */
		if ( (p_a_data == null) || (p_a_data.length == 0) ) {
			throw new IllegalArgumentException("Byte array for decryption is empty");
		}
		
		/* check length of common secret passphrase */
		if (p_s_commonSecretPassphrase.length() < 36) {
			throw new IllegalArgumentException("Common secret passphrase must have at least '36' characters, but has '" + p_s_commonSecretPassphrase.length() + "' characters");
		}
		
		/* check key length option */
		if ( (p_i_keyLengthOption < 0) || (p_i_keyLengthOption > 1) ) {
			throw new IllegalArgumentException("Unknown key length option[" + p_i_keyLengthOption + "]. Please use ['Cryptography.KEY128BIT'] or ['Cryptography.KEY256BIT']");
		}
		
		/* convert encrypted data to byte buffer */
		java.nio.ByteBuffer o_byteBuffer = java.nio.ByteBuffer.wrap(p_a_data);
        
        /* define salt byte array with 12 bytes */
        byte[] a_iv = new byte[12];
        /* read salt */
        o_byteBuffer.get(a_iv);
        
        /* generate secret key for cryptography instance */
        javax.crypto.SecretKey o_secretKey = Cryptography.generateSecretKey(a_iv, p_s_commonSecretPassphrase, p_i_keyLengthOption);
        
        /* create byte array for remaining encrypted bytes */
        byte[] a_cipherBytes = new byte[o_byteBuffer.remaining()];
        /* read remaining encrypted bytes */
        o_byteBuffer.get(a_cipherBytes);
        
        /* create cipher instance and gcm specifications */
        javax.crypto.Cipher o_cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
        javax.crypto.spec.GCMParameterSpec o_gcmParameterSpec = new javax.crypto.spec.GCMParameterSpec(128, a_iv);
        
        /* init cipher with decrypt mode */
        o_cipher.init(javax.crypto.Cipher.DECRYPT_MODE, o_secretKey, o_gcmParameterSpec);

        										net.forestany.forestj.lib.Global.ilogFiner("decrypt: " + (System.currentTimeMillis() - l_foo) + " ms ");
        
        /* decrypt data with cipher */
        return o_cipher.doFinal(a_cipherBytes);
	}
	
	/**
	 * generate secret key instance which can be used with a cipher to de-/encrypt data.
	 * 
	 * @param p_a_iv						salt byte array, default 16 bytes
	 * @param p_s_commonSecretPassphrase	common secret passphrase string which must be at least 36 characters long
     * @param p_i_keyLengthOption			Please use ['Cryptography.KEY128BIT'] or ['Cryptography.KEY256BIT']	
	 * @return								javax.crypto.SecretKey instance
	 * @throws IllegalArgumentException		invalid key length option [Cryptography.KEY128BIT | Cryptography.KEY256BIT]
	 * @throws NoSuchAlgorithmException 	invalid key factory algorithm
	 * @throws InvalidKeySpecException 		invalid key specifications, length of common secret passphrase, salt, iteration value or key length option
	 */
	private static javax.crypto.SecretKey generateSecretKey(byte[] p_a_iv, String p_s_commonSecretPassphrase, int p_i_keyLengthOption) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException {
		int i_keyLength = 0;
		String s_keyFactory = "";
		
		/* check key length option and set key factory algorithm */
		if (p_i_keyLengthOption == Cryptography.KEY128BIT) {
			i_keyLength = 128;
			s_keyFactory = "PBKDF2WithHmacSHA1";
		} else if (p_i_keyLengthOption == Cryptography.KEY256BIT) {
			i_keyLength = 256;
			s_keyFactory = "PBKDF2WithHmacSHA512";
		} else {
			throw new IllegalArgumentException("Unknown key length option[" + p_i_keyLengthOption + "]. Please use ['Cryptography.KEY128BIT'] or ['Cryptography.KEY256BIT']");
		}
		
		/* retrieve iteration count from salt byte array, max. 16 bits, otherwise key generation takes to long  */
		byte[] a_iterationCount = new byte[] { 0x00, 0x00, 0x00, 0x00 };
		
		a_iterationCount[2] |= (byte)(p_a_iv[0] & 0x80); /* 8th bit of 1st salt byte */
		a_iterationCount[2] |= (byte)(p_a_iv[1] & 0x40); /* 7th bit of 2nd salt byte */
		a_iterationCount[2] |= (byte)(p_a_iv[2] & 0x20); /* 6th bit of 3rd salt byte */
		a_iterationCount[2] |= (byte)(p_a_iv[3] & 0x10); /* 5th bit of 4th salt byte */
		a_iterationCount[2] |= (byte)(p_a_iv[4] & 0x08); /* 4th bit of 5th salt byte */
		a_iterationCount[2] |= (byte)(p_a_iv[5] & 0x04); /* 3rd bit of 6th salt byte */
		a_iterationCount[2] |= (byte)(p_a_iv[6] & 0x02); /* 2nd bit of 7th salt byte */
		a_iterationCount[2] |= (byte)(p_a_iv[7] & 0x01); /* 1st bit of 8th salt byte */
        
		a_iterationCount[3] |= (byte)(p_a_iv[8] & 0x05); /* 1st + 3rd bit of 9th salt byte */
		a_iterationCount[3] |= (byte)(p_a_iv[9] & 0x0A); /* 2nd + 4th bit of 10th salt byte */
		a_iterationCount[3] |= (byte)(p_a_iv[10] & 0x50); /* 5th + 7th bit of 11th salt byte */
		a_iterationCount[3] |= (byte)(p_a_iv[11] & 0xA0); /* 6th + 8th bit of 12th salt byte */
		
		/* convert iteration count bytes to integer value */
		int i_iterationCount = net.forestany.forestj.lib.Helper.byteArrayToInt(a_iterationCount);
		
		/* create secret key specifications */
		byte[] by_utf8 = p_s_commonSecretPassphrase.getBytes(java.nio.charset.StandardCharsets.UTF_8);
		String s_utf8 = new String(by_utf8, java.nio.charset.StandardCharsets.UTF_8);
		java.security.spec.KeySpec o_spec = new javax.crypto.spec.PBEKeySpec(s_utf8.toCharArray(), p_a_iv, i_iterationCount, i_keyLength);
        /* create secret key factory */
		javax.crypto.SecretKeyFactory o_secretKeyFactory = javax.crypto.SecretKeyFactory.getInstance(s_keyFactory);
        /* generate secret key byte array */
		byte[] a_key = o_secretKeyFactory.generateSecret(o_spec).getEncoded();
		/* return javax.crypto.SecretKey with secret key byte array and AES algorithm */
        return new javax.crypto.spec.SecretKeySpec(a_key, "AES");
	}

	/**
	 * Creates ssl context instance with keystore copy and just one certificate.
	 * 
	 * @param p_s_keystoreLocation							keystore location full path
	 * @param p_s_keystorePassword							keystore passphrase
	 * @param p_s_certificateAlias							certificate alias which should be the last one left in keystore copy
	 * @return												javax.net.ssl SSL context instance
	 * @throws java.io.FileNotFoundException				keystore location is invalid
	 * @throws IllegalArgumentException						certificate alias parameter is null
	 * @throws IllegalStateException						more than one certificate is left
	 * @throws java.io.IOException							cannot close opened keystore streams
	 * @throws java.security.KeyStoreException				cannot create keystore default type instances
	 * @throws java.security.cert.CertificateException		keystore has invalid certificate entries
	 * @throws java.security.NoSuchAlgorithmException		if the specified algorithm is not available from the specified provider
	 * @throws java.security.UnrecoverableKeyException 		if the key cannot be recovered(e.g. the given password is wrong)	
	 * @throws java.security.KeyManagementException 		cannot init SSL context instance
	 * 			
	 */
	public static javax.net.ssl.SSLContext createSSLContextWithOneCertificate(String p_s_keystoreLocation, String p_s_keystorePassword, String p_s_certificateAlias) throws java.io.FileNotFoundException, IllegalArgumentException, IllegalStateException, java.io.IOException, java.security.KeyStoreException, java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.UnrecoverableKeyException, java.security.KeyManagementException {
		javax.net.ssl.SSLContext o_sslContext = null;
		
		/* check keystore location */
		if (!net.forestany.forestj.lib.io.File.exists(p_s_keystoreLocation)) {
			throw new java.io.FileNotFoundException("Keystore[" + p_s_keystoreLocation + "] does not exist");
		}
		
		/* check certificate alias parameter */
		if (Helper.isStringEmpty(p_s_certificateAlias)) {
			throw new IllegalArgumentException("Please specify a certificate alias");
		}
		
		/* open two streams of the same keystore */
		try (
			java.io.FileInputStream o_fileInputStream = new java.io.FileInputStream(p_s_keystoreLocation);
			java.io.FileInputStream o_fileInputStreamCopy = new java.io.FileInputStream(p_s_keystoreLocation);
		) {
			/* open and load both keystore objects */
			java.security.KeyStore o_keystore = java.security.KeyStore.getInstance(java.security.KeyStore.getDefaultType());
			java.security.KeyStore o_keystoreCopy = java.security.KeyStore.getInstance(java.security.KeyStore.getDefaultType());
			o_keystore.load(o_fileInputStream, p_s_keystorePassword.toCharArray());
			o_keystoreCopy.load(o_fileInputStreamCopy, p_s_keystorePassword.toCharArray());
	        
													/* log amount of certificates and aliases */
													net.forestany.forestj.lib.Global.ilogFinest("Amount certificates in keystore: " + o_keystore.size());
													net.forestany.forestj.lib.Global.ilogFinest("Certificates in keystore ... ");
	        
											        for (String s_certificateAlias : java.util.Collections.list(o_keystore.aliases())) {
											        	net.forestany.forestj.lib.Global.ilogFinest(s_certificateAlias);
											        }
	        
	        /* delete all certificate entries which do not match with alias parameter */
	        for (String s_certificateAlias : java.util.Collections.list(o_keystoreCopy.aliases())) {
	        	if (!s_certificateAlias.contentEquals(p_s_certificateAlias)) {
	        		o_keystoreCopy.deleteEntry(s_certificateAlias);
	        	}
	        }
	
	        /* just one certificate entry must be left */
	        if (o_keystoreCopy.size() != 1) {
	        	throw new IllegalStateException("Certificate[" + p_s_certificateAlias + "] could not be found in keystore[" + p_s_keystoreLocation + "]");
	        }
	        
											        /* log amount of certificates and alias in keystore copy */
											        net.forestany.forestj.lib.Global.ilogFinest("Amount certificates in keystoreCopy: " + o_keystoreCopy.size());
											        net.forestany.forestj.lib.Global.ilogFinest("Certificates in keystoreCopy ... ");
											        
											        for (String s_certificateAlias : java.util.Collections.list(o_keystoreCopy.aliases())) {
											        	net.forestany.forestj.lib.Global.ilogFinest(s_certificateAlias);
											        }
	        
	        /* init key manager factory with default algorithm */
	        javax.net.ssl.KeyManagerFactory o_keyManagerFactory = javax.net.ssl.KeyManagerFactory.getInstance(javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm());
	        o_keyManagerFactory.init(o_keystoreCopy, p_s_keystorePassword.toCharArray());
	        /* init key manager */
	        javax.net.ssl.KeyManager a_keyManagers[] = o_keyManagerFactory.getKeyManagers();
	        /* init SSL context instance */
	        o_sslContext = javax.net.ssl.SSLContext.getInstance("TLSv1.3");
	        o_sslContext.init(a_keyManagers, null, new java.security.SecureRandom());
		}
		
		return o_sslContext;
	}
	
//	/**
//	 * Create trustmanager instance with keystore copy and just one certificate - dependency on org.apache.commons.net.
//	 * 
//	 * @param p_s_keystoreLocation							keystore location full path
//	 * @param p_s_keystorePassword							keystore passphrase
//	 * @param p_s_certificateAlias							certificate alias which should be the last one left in keystore copy
//	 * @return												javax.net.ssl X509TrustManager instance
//	 * @throws java.io.FileNotFoundException				keystore location is invalid
//	 * @throws IllegalArgumentException						certificate alias parameter is null
//	 * @throws IllegalStateException						more than one certificate is left
//	 * @throws java.io.IOException							cannot close opened keystore streams
//	 * @throws java.security.GeneralSecurityException 		if an error occurs retrieving default trust manager
//	 * @throws java.security.NoSuchAlgorithmException		if the specified algorithm is not available from the specified provider
//	 */
//	public static javax.net.ssl.X509TrustManager createTrustManagerWithOneCertificate(String p_s_keystoreLocation, String p_s_keystorePassword, String p_s_certificateAlias) throws java.io.FileNotFoundException, IllegalArgumentException, IllegalStateException, java.io.IOException, java.security.GeneralSecurityException {
//		javax.net.ssl.X509TrustManager o_trustManager = null;
//		
//		/* check keystore location */
//		if (!net.forestany.forestj.lib.io.File.exists(p_s_keystoreLocation)) {
//			throw new java.io.FileNotFoundException("Keystore[" + p_s_keystoreLocation + "] does not exist");
//		}
//		
//		/* check certificate alias parameter */
//		if (Helper.isStringEmpty(p_s_certificateAlias)) {
//			throw new IllegalArgumentException("Please specify a certificate alias");
//		}
//		
//		/* open two streams of the same keystore */
//		try (
//			java.io.FileInputStream o_fileInputStream = new java.io.FileInputStream(p_s_keystoreLocation);
//			java.io.FileInputStream o_fileInputStreamCopy = new java.io.FileInputStream(p_s_keystoreLocation);
//		) {
//			/* open and load both keystore objects */
//			java.security.KeyStore o_keystore = java.security.KeyStore.getInstance(java.security.KeyStore.getDefaultType());
//			java.security.KeyStore o_keystoreCopy = java.security.KeyStore.getInstance(java.security.KeyStore.getDefaultType());
//			o_keystore.load(o_fileInputStream, p_s_keystorePassword.toCharArray());
//			o_keystoreCopy.load(o_fileInputStreamCopy, p_s_keystorePassword.toCharArray());
//	        
//													/* log amount of certificates and aliases */
//													net.forestany.forestj.lib.Global.ilogFinest("Amount certificates in keystore: " + o_keystore.size());
//													net.forestany.forestj.lib.Global.ilogFinest("Certificates in keystore ... ");
//											        
//											        for (String s_certificateAlias : java.util.Collections.list(o_keystore.aliases())) {
//											        	net.forestany.forestj.lib.Global.ilogFinest(s_certificateAlias);
//											        }
//	        
//	        /* delete all certificate entries which do not match with alias parameter */
//	        for (String s_certificateAlias : java.util.Collections.list(o_keystoreCopy.aliases())) {
//	        	if (!s_certificateAlias.contentEquals(p_s_certificateAlias)) {
//	        		o_keystoreCopy.deleteEntry(s_certificateAlias);
//	        	}
//	        }
//	
//	        /* just one certificate entry must be left */
//	        if (o_keystoreCopy.size() != 1) {
//	        	throw new IllegalStateException("Certificate[" + p_s_certificateAlias + "] could not be found in keystore[" + p_s_keystoreLocation + "]");
//	        }
//	        
//											        /* log amount of certificates and alias in keystore copy */
//											        net.forestany.forestj.lib.Global.ilogFinest("Amount certificates in keystoreCopy: " + o_keystoreCopy.size());
//											        net.forestany.forestj.lib.Global.ilogFinest("Certificates in keystoreCopy ... ");
//											        
//											        for (String s_certificateAlias : java.util.Collections.list(o_keystoreCopy.aliases())) {
//											        	net.forestany.forestj.lib.Global.ilogFinest(s_certificateAlias);
//											        }
//	        
//	        /* create trustmanager instance with keystore copy and just one certificate - need org.apache.commons.net dependency */
//	        o_trustManager = org.apache.commons.net.util.TrustManagerUtils.getDefaultTrustManager(o_keystoreCopy);
//		}
//		
//		return o_trustManager;
//	}
}
