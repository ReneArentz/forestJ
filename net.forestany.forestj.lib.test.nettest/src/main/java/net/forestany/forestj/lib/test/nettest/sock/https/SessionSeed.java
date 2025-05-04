package net.forestany.forestj.lib.test.nettest.sock.https;

/**
 * session seed class for testing
 */
public class SessionSeed extends net.forestany.forestj.lib.net.https.dynm.ForestSeed {
	/**
	 * empty constructor
	 */
	public SessionSeed() {
		
	}
	
	/**
	 * prepare content method of ForestSeed
	 */
	@Override
	public void prepareContent() throws Exception {
		if (this.getSeed().getSessionData().containsKey("4")) {
			this.getSeed().getSessionData().put("5", "five");
		}
		
		if (this.getSeed().getSessionData().containsKey("3")) {
			this.getSeed().getSessionData().put("4", "four");
		}
		
		if (this.getSeed().getSessionData().containsKey("2")) {
			this.getSeed().getSessionData().put("3", "three");
		}
		
		if (this.getSeed().getSessionData().containsKey("1")) {
			this.getSeed().getSessionData().put("2", "two");
		}
		
		if (this.getSeed().getSessionData().size() == 0) {
			this.getSeed().getSessionData().put("1", "one");
		}
		
		this.getSeed().getTemp().put("SESSIONLIST", this.getSeed().getSessionData());
	}
}
