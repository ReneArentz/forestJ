package net.forestany.forestj.lib;

/**
 * 
 * Global singleton class to store central values and objects.
 *
 */
public class Global {
	
	/* Fields */
	
	private static Global o_instance;
	
	/**
	 * java.security.SecureRandom instance
	 */
	public java.security.SecureRandom SecureRandom;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * private constructor of singleton class, can set standard values for objects or settings
	 */
	private Global () {
		try {
			/* System.setProperty("https.protocols", "TLSv1.3"); */
			this.SecureRandom = java.security.SecureRandom.getInstanceStrong();
		} catch (java.security.NoSuchAlgorithmException o_exc) {
			/* nothing to do */
		}
	}

	/**
	 * method to access singleton instance
	 * @return Global singleton
	 */
	public static synchronized Global get() {
		if (Global.o_instance == null) {
			Global.o_instance = new Global();
		}
		
		return Global.o_instance;
	}
}
