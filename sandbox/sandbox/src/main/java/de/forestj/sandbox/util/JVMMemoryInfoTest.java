package de.forestj.sandbox.util;

public class JVMMemoryInfoTest {
	public static void testJVMMemoryInfo() throws Exception {
    	de.forestj.lib.JVMMemoryInfo o_jvmMemoryInfo = new de.forestj.lib.JVMMemoryInfo(de.forestj.lib.Global.get().LOG, 1000, java.util.logging.Level.INFO);
    	Thread o_jvmMemoryInfoThread = new Thread(o_jvmMemoryInfo);
		
    	de.forestj.lib.Global.log("starting jvm memory thread . . .");
    	
		o_jvmMemoryInfoThread.start();
		
		de.forestj.lib.Global.log("calculate pi and occupy memory . . .");
		
		java.util.List<String> a_foo = new java.util.ArrayList<String>();
		double d_pi = 0.0d;
		
		for (int i = 500_000_000; i > 0; i--) {
			d_pi += Math.pow(-1, i + 1) / (2 * i - 1); // Calculate series in parenthesis
			
			if (i == 1) {
				a_foo.add(String.valueOf(d_pi));
				d_pi *= 4;    
			}  
		}
		
		System.out.println(d_pi); // Print pi
		
		if (o_jvmMemoryInfo != null) {
			o_jvmMemoryInfo.stop();
		}
		
		de.forestj.lib.Global.log("jvm memory thread stopped . . .");
    }
}