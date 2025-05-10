package net.forestany.forestj.lib.test.nettest.sock.com;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test socket communication
 */
public class CommunicationTest {
	/* sleep multiplier for test cycle executions */
	private static int i_sleepMultiplier = 8;
	
	/* counter for total test fails, but we will tolerate some */
	private static int i_fails = 0;
	
	/**
	 * method to test socket communication
	 */
	/*@org.junit.jupiter.api.Disabled*/
	@Test
	public void testCommunicationMultipleTimes() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			int i_iterations = 1;
			
			for (int i = 0; i < i_iterations; i++) {
				if (i_iterations > 1) {
					System.out.println("#" + (i + 1) + " - " + java.time.LocalDateTime.now());
				}
				
				testCommunication();
				
				try {
					/* 10 milliseconds to close any connections */
					Thread.sleep(10);
				} catch (InterruptedException e) {
					net.forestany.forestj.lib.Global.logException(e);
				}
			}
			
			if (i_fails >= 8) {
				throw new Exception("overall fails '" + i_fails + "' are greater than '8'");
			}
			
			if (i_iterations > 1)
			{
			    net.forestany.forestj.lib.Global.ilogWarning("INFO: Iteration #" + i_iterations + " completed");
			}

			Thread.sleep(2500);

			net.forestany.forestj.lib.Global.ilogWarning(">>>>>> End TestCommunication <<<<<<");
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
	}
	
	private void testCommunication() {
		try {
			boolean b_skipUDP = false;
			boolean b_skipTCP = false;
			boolean b_skipMarshalling = false;
			boolean b_skipUDPMarshalling = false;
			boolean b_skipTCPMarshalling = false;
			
			boolean b_enhancedUDP = false;
			boolean b_enhancedTCP = false;
				
			if (!b_skipUDP) {
				testUDP(b_enhancedUDP, false, 2);
			}
			
			if (!b_skipTCP) {
				testTCP(b_enhancedTCP, false, 2);
			}
			
			if (!b_skipMarshalling) {
				testMarshalling(b_skipUDPMarshalling, b_skipTCPMarshalling, b_enhancedUDP, b_enhancedTCP);
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
	}
	
	private static void testUDP(boolean p_b_enhancedUDP, boolean p_b_useMarshalling, int p_i_marshallingDataLengthInBytes) throws Exception {
		boolean b_symmetricSecurity128 = false;
		boolean b_symmetricSecurity256 = false;
		boolean b_asymmetricSecurity = false;
		boolean b_highSecurity = false;
		
		boolean b_useMarshalling = p_b_useMarshalling;
		boolean b_useMarshallingWholeObject = false;
		int i_marshallingDataLengthInBytes = p_i_marshallingDataLengthInBytes;
		boolean b_marshallingUsePropertyMethods = false;
		boolean b_marshallingSystemUsesLittleEndian = false;
		
		int i_iterationsUDP = 1;
		
		if (p_b_enhancedUDP) {
			i_iterationsUDP = 5;
		}
		
		for (int i = 0; i < i_iterationsUDP; i++) {
			if (i == 0) {
				net.forestany.forestj.lib.Global.ilogWarning("INFO: no security");
			} else if (i == 1) {
				b_symmetricSecurity128 = true;
				b_symmetricSecurity256 = false;
				b_asymmetricSecurity = false;
				b_highSecurity = false;
				net.forestany.forestj.lib.Global.ilogWarning("INFO: 128-bit security");
			} else if (i == 2) {
				b_symmetricSecurity128 = true;
				b_symmetricSecurity256 = false;
				b_asymmetricSecurity = false;
				b_highSecurity = true;
				net.forestany.forestj.lib.Global.ilogWarning("INFO: 128-bit 'high' security");
			} else if (i == 3) {
				b_symmetricSecurity128 = false;
				b_symmetricSecurity256 = true;
				b_asymmetricSecurity = false;
				b_highSecurity = false;
				net.forestany.forestj.lib.Global.ilogWarning("INFO: 256-bit security");
			} else if (i == 4) {
				b_symmetricSecurity128 = false;
				b_symmetricSecurity256 = true;
				b_asymmetricSecurity = false;
				b_highSecurity = true;
				net.forestany.forestj.lib.Global.ilogWarning("INFO: 256-bit 'high' security");
			}
			
			net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunication UDP");
			testCommunication(false, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
			net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunication UDP with ACK");
			testCommunication(false, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 42333, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
			net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunication UDP with Multicast");
			testCommunicationUDPMulticast(false, "unknown", "239.255.1.2", 12080, "239.255.1.2", 12080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_highSecurity, b_useMarshalling, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
			net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunication UDP with IPv6 Multicast");
			testCommunicationUDPMulticast(true, "unknown", "FF05:0:0:0:0:0:0:342", 12080, "FF05:0:0:0:0:0:0:342", 12080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_highSecurity, b_useMarshalling, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
						
			if (p_b_enhancedUDP) {
				net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationSharedMemoryUniDirectional UDP");
				testCommunicationSharedMemoryUniDirectional(false, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationSharedMemoryUniDirectional UDP with ACK");
				testCommunicationSharedMemoryUniDirectional(false, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 42333, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationSharedMemoryBiDirectional UDP");
				testCommunicationSharedMemoryBiDirectional(false, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationSharedMemoryBiDirectional UDP with ACK");
				testCommunicationSharedMemoryBiDirectional(false, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, java.net.InetAddress.getLocalHost().getHostAddress(), 42333, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
			}
		}
	}
	
	private static void testTCP(boolean p_b_enhancedTCP, boolean p_b_useMarshalling, int p_i_marshallingDataLengthInBytes) throws Exception {
		boolean b_symmetricSecurity128 = false;
		boolean b_symmetricSecurity256 = false;
		boolean b_asymmetricSecurity = false;
		boolean b_highSecurity = false;
		
		boolean b_useMarshalling = p_b_useMarshalling;
		boolean b_useMarshallingWholeObject = false;
		int i_marshallingDataLengthInBytes = p_i_marshallingDataLengthInBytes;
		boolean b_marshallingUsePropertyMethods = false;
		boolean b_marshallingSystemUsesLittleEndian = false;
		
		for (int i = 0; i < 6; i++) {
			if (i == 0) {
				net.forestany.forestj.lib.Global.ilogWarning("INFO: no security");
				
				if (!p_b_enhancedTCP) {
					i = 4; /* jump directly to asymmetric security on next iteration */
				}
			} else if (i == 1) {
				b_symmetricSecurity128 = true;
				b_symmetricSecurity256 = false;
				b_asymmetricSecurity = false;
				b_highSecurity = false;
				net.forestany.forestj.lib.Global.ilogWarning("INFO: 128-bit security");
			} else if (i == 2) {
				b_symmetricSecurity128 = true;
				b_symmetricSecurity256 = false;
				b_asymmetricSecurity = false;
				b_highSecurity = true;
				net.forestany.forestj.lib.Global.ilogWarning("INFO: 128-bit 'high' security");
			} else if (i == 3) {
				b_symmetricSecurity128 = false;
				b_symmetricSecurity256 = true;
				b_asymmetricSecurity = false;
				b_highSecurity = false;
				net.forestany.forestj.lib.Global.ilogWarning("INFO: 256-bit security");
			} else if (i == 4) {
				b_symmetricSecurity128 = false;
				b_symmetricSecurity256 = true;
				b_asymmetricSecurity = false;
				b_highSecurity = true;
				net.forestany.forestj.lib.Global.ilogWarning("INFO: 256-bit 'high' security");
			} else if (i == 5) {
				b_symmetricSecurity128 = false;
				b_symmetricSecurity256 = false;
				b_asymmetricSecurity = true;
				b_highSecurity = false;
				net.forestany.forestj.lib.Global.ilogWarning("INFO: asymmetric security");
			}
			
			net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunication TCP");
			testCommunication(true, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
			net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationTCPBidirectional TCP");
			testCommunicationTCPBidirectional(java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
			net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationTCPHandshakeTask TCP");
			testCommunicationTCPHandshakeTask(java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
			net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationObjectTransmission TCP");
			testCommunicationObjectTransmissionTCP(java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
			net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationWithAnswerOne TCP");
			testCommunicationWithAnswerOneTCP(java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
			net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationWithAnswerTwo TCP");
			testCommunicationWithAnswerTwoTCP(java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
			net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationSharedMemoryUniDirectional TCP");
			testCommunicationSharedMemoryUniDirectional(true, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
			net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationSharedMemoryBiDirectional TCP");
			testCommunicationSharedMemoryBiDirectional(true, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, b_useMarshalling, b_useMarshallingWholeObject, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
		}
	}
	
	private static void testMarshalling(boolean p_b_skipUDPMarshalling, boolean p_b_skipTCPMarshalling, boolean p_b_enhancedUDP, boolean p_b_enhancedTCP) throws Exception {
		boolean b_enhancedMarshalling = false;
		boolean b_marshallingOnlyDataLengthTwo = true;
		boolean b_skipUniAndBiDirectional = true;
        int i_marshallingDataLengthInBytes = 1;

        int i_marshallingStart = 0;
        int i_marshallingEnd = 4;

        if (b_marshallingOnlyDataLengthTwo)
        {
            i_marshallingStart = 1;
            i_marshallingEnd = 2;
        }
		
        for (int i = i_marshallingStart; i < i_marshallingEnd; i++) {
			boolean b_symmetricSecurity128 = false;
			boolean b_symmetricSecurity256 = false;
			boolean b_asymmetricSecurity = false;
			boolean b_highSecurity = false;
			
			if (i == 0) {
				i_marshallingDataLengthInBytes = 1;
				net.forestany.forestj.lib.Global.ilogWarning("INFO: Marshalling - data length in bytes = 1");
			} else if (i == 1) {
				i_marshallingDataLengthInBytes = 2;
				net.forestany.forestj.lib.Global.ilogWarning("INFO: Marshalling - data length in bytes = 2");
			} else if (i == 2) {
				i_marshallingDataLengthInBytes = 3;
				net.forestany.forestj.lib.Global.ilogWarning("INFO: Marshalling - data length in bytes = 3");
			} else if (i == 3) {
				i_marshallingDataLengthInBytes = 4;
				net.forestany.forestj.lib.Global.ilogWarning("INFO: Marshalling - data length in bytes = 4");
			}
			
			if (!p_b_skipUDPMarshalling) {
				testUDP(p_b_enhancedUDP, true, i_marshallingDataLengthInBytes);
			}
			
			if (!p_b_skipTCPMarshalling) {
				testTCP(p_b_enhancedTCP, true, i_marshallingDataLengthInBytes);
			}
			
			for (int j = 0; j < 6; j++) {
				if (j == 0) {
					net.forestany.forestj.lib.Global.ilogWarning("INFO: no security");
					
					if (!b_enhancedMarshalling) {
                        j = 4; /* jump directly to asymmetric security on next iteration */
                    }
				} else if (j == 1) {
					b_symmetricSecurity128 = true;
					b_symmetricSecurity256 = false;
					b_asymmetricSecurity = false;
					b_highSecurity = false;
					net.forestany.forestj.lib.Global.ilogWarning("INFO: 128-bit security");
				} else if (j == 2) {
					b_symmetricSecurity128 = true;
					b_symmetricSecurity256 = false;
					b_asymmetricSecurity = false;
					b_highSecurity = true;
					net.forestany.forestj.lib.Global.ilogWarning("INFO: 128-bit 'high' security");
				} else if (j == 3) {
					b_symmetricSecurity128 = false;
					b_symmetricSecurity256 = true;
					b_asymmetricSecurity = false;
					b_highSecurity = false;
					net.forestany.forestj.lib.Global.ilogWarning("INFO: 256-bit security");
				} else if (j == 4) {
					b_symmetricSecurity128 = false;
					b_symmetricSecurity256 = true;
					b_asymmetricSecurity = false;
					b_highSecurity = true;
					net.forestany.forestj.lib.Global.ilogWarning("INFO: 256-bit 'high' security");
				} else if (j == 5) {
					b_symmetricSecurity128 = false;
					b_symmetricSecurity256 = false;
					b_asymmetricSecurity = true;
					b_highSecurity = false;
					net.forestany.forestj.lib.Global.ilogWarning("INFO: asymmetric security");
				}
				
				boolean b_marshallingUsePropertyMethods = false;
				boolean b_marshallingSystemUsesLittleEndian = false;
				
				/* ****************** */
				/* Marshalling Object */
				/* ****************** */
				
				/* skip UDP with asymmetric security */
				if (!b_asymmetricSecurity) {
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingObject UDP - small object");
					testCommunicationMarshallingObject(false, false, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingObject UDP - normal object");
					testCommunicationMarshallingObject(false, false, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
					
					b_marshallingUsePropertyMethods = true;
					
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingObject UDP - small object - use property methods");
					testCommunicationMarshallingObject(false, false, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingObject UDP - normal object - use property methods");
					testCommunicationMarshallingObject(false, false, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				
					b_marshallingUsePropertyMethods = false;
					
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingObject UDP with ACK - small object");
					testCommunicationMarshallingObject(false, true, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 42333, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingObject UDP with ACK - normal object");
					testCommunicationMarshallingObject(false, true, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 42333, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
					
					b_marshallingUsePropertyMethods = true;
					
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingObject UDP with ACK - small object - use property methods");
					testCommunicationMarshallingObject(false, true, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 42333, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingObject UDP with ACK - normal object - use property methods");
					testCommunicationMarshallingObject(false, true, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 42333, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				
					b_marshallingUsePropertyMethods = false;
				}
				
				net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingObject TCP - small object");
				testCommunicationMarshallingObject(true, false, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingObject TCP - normal object");
				testCommunicationMarshallingObject(true, false, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				
				b_marshallingUsePropertyMethods = true;
				
				net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingObject TCP - small object - use property methods");
				testCommunicationMarshallingObject(true, false, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingObject TCP - normal object - use property methods");
				testCommunicationMarshallingObject(true, false, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				
				if (!b_skipUniAndBiDirectional) {
					/* **************************************** */
					/* Marshalling Whole Object Uni Directional */
					/* **************************************** */
					
					/* shared memory objects only have private fields, so we must use property methods to access these fields */
					b_marshallingUsePropertyMethods = true;
					
					boolean b_smallObject = true;
					boolean b_massChangeAtEnd = false;
					
					/* skip UDP with asymmetric security */
					if (!b_asymmetricSecurity) {
						net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryUniDirectional UDP - small object");
						testCommunicationMarshallingSharedMemoryUniDirectional(false, b_smallObject, b_massChangeAtEnd, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
						
						b_massChangeAtEnd = true;
						
						net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryUniDirectional UDP - small object - mass change at end");
						testCommunicationMarshallingSharedMemoryUniDirectional(false, b_smallObject, b_massChangeAtEnd, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
						
						b_smallObject = false;
						b_massChangeAtEnd = false;
						
						net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryUniDirectional UDP");
						testCommunicationMarshallingSharedMemoryUniDirectional(false, b_smallObject, b_massChangeAtEnd, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
						
						b_massChangeAtEnd = true;
						
						net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryUniDirectional UDP - mass change at end");
						testCommunicationMarshallingSharedMemoryUniDirectional(false, b_smallObject, b_massChangeAtEnd, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
						
						b_smallObject = true;
						b_massChangeAtEnd = false;
						
						net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryUniDirectional UDP with ACK - small object");
						testCommunicationMarshallingSharedMemoryUniDirectional(false, b_smallObject, b_massChangeAtEnd, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 42333, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
						
						b_massChangeAtEnd = true;
						
						net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryUniDirectional UDP with ACK - small object - mass change at end");
						testCommunicationMarshallingSharedMemoryUniDirectional(false, b_smallObject, b_massChangeAtEnd, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 42333, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
						
						b_smallObject = false;
						b_massChangeAtEnd = false;
						
						net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryUniDirectional UDP with ACK");
						testCommunicationMarshallingSharedMemoryUniDirectional(false, b_smallObject, b_massChangeAtEnd, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 42333, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
						
						b_massChangeAtEnd = true;
						
						net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryUniDirectional UDP with ACK - mass change at end");
						testCommunicationMarshallingSharedMemoryUniDirectional(false, b_smallObject, b_massChangeAtEnd, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 42333, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
						
						b_smallObject = true;
						b_massChangeAtEnd = false;
					}
					
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryUniDirectional TCP - small object");
					testCommunicationMarshallingSharedMemoryUniDirectional(true, b_smallObject, b_massChangeAtEnd, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
					
					b_massChangeAtEnd = true;
					
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryUniDirectional TCP - small object - mass change at end");
					testCommunicationMarshallingSharedMemoryUniDirectional(true, b_smallObject, b_massChangeAtEnd, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
					
					b_smallObject = false;
					b_massChangeAtEnd = false;
					
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryUniDirectional TCP");
					testCommunicationMarshallingSharedMemoryUniDirectional(true, b_smallObject, b_massChangeAtEnd, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
					
					b_massChangeAtEnd = true;
					
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryUniDirectional TCP - mass change at end");
					testCommunicationMarshallingSharedMemoryUniDirectional(true, b_smallObject, b_massChangeAtEnd, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
					
					/* *************************************** */
					/* Marshalling Whole Object Bi Directional */
					/* *************************************** */
					
					b_smallObject = true;
					
					/* skip UDP with asymmetric security */
					if (!b_asymmetricSecurity) {
						net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryBiDirectional UDP - small object");
						testCommunicationMarshallingSharedMemoryBiDirectional(false, b_smallObject, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
						
						b_smallObject = false;
						
						net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryBiDirectional UDP");
						testCommunicationMarshallingSharedMemoryBiDirectional(false, b_smallObject, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
						
						b_smallObject = true;
						
						net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryBiDirectional UDP with ACK - small object");
						testCommunicationMarshallingSharedMemoryBiDirectional(false, b_smallObject, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, java.net.InetAddress.getLocalHost().getHostAddress(), 42333, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
						
						b_smallObject = false;
						b_massChangeAtEnd = false;
						
						net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryBiDirectional UDP with ACK");
						testCommunicationMarshallingSharedMemoryBiDirectional(false, b_smallObject, true, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, java.net.InetAddress.getLocalHost().getHostAddress(), 42333, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
						
						b_smallObject = true;
					}
					
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryBiDirectional TCP - small object");
					testCommunicationMarshallingSharedMemoryBiDirectional(true, b_smallObject, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
					
					b_smallObject = false;
					
					net.forestany.forestj.lib.Global.ilogWarning("INFO: testCommunicationMarshallingSharedMemoryBiDirectional TCP");
					testCommunicationMarshallingSharedMemoryBiDirectional(true, b_smallObject, false, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8080, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, java.net.InetAddress.getLocalHost().getHostAddress(), 8081, null, 0, b_symmetricSecurity128, b_symmetricSecurity256, b_asymmetricSecurity, b_highSecurity, true, true, i_marshallingDataLengthInBytes, b_marshallingUsePropertyMethods, b_marshallingSystemUsesLittleEndian);
				}
			}
		}
	}

	private static net.forestany.forestj.lib.net.sock.com.Config getCommunicationConfig(net.forestany.forestj.lib.net.sock.com.Type p_e_comType, net.forestany.forestj.lib.net.sock.com.Cardinality p_e_comCardinality, String p_s_host, int p_i_port, String p_s_localHost, int p_i_localPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_asymmetricSecurity, boolean p_b_highSecurity, boolean p_b_securityTrustAll, boolean p_b_useMarshalling, boolean p_b_useMarshallingWholeObject, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		String s_resourcesDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "com" + net.forestany.forestj.lib.io.File.DIR;
			
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
		
		return o_communicationConfig;
	}
	
	private static void testCommunication(boolean p_b_falseUDPtrueTCP, boolean p_b_udpWithAck, String p_s_serverHost, int p_i_serverPort, String p_s_clientHost, int p_i_clientPort, String p_s_clientLocalHost, int p_i_clientLocalPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_asymmetricSecurity, boolean p_b_highSecurity, boolean p_b_useMarshalling, boolean p_b_useMarshallingWholeObject, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		java.util.List<String> a_serverLog = new java.util.ArrayList<String>();
		java.util.List<String> a_clientLog = new java.util.ArrayList<String>();
		
		int i_comDequeueWaitLoopTimeout = 5000;
		int i_iterations = 10;
		
		/* SERVER */
		
		Thread o_threadServer = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE;
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK;
					}
					
					if (p_b_falseUDPtrueTCP) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE;
					}
					
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_serverHost, p_i_serverPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					for (int i = 0; i < i_iterations; i++) {
						if (p_b_falseUDPtrueTCP) {
							String s_message = (String)o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
							
							if (s_message != null) {
								net.forestany.forestj.lib.Global.ilog("#" + (i + 1) + " message(" + s_message.length() + ") received: '" + s_message + "'");
								a_serverLog.add("#" + (i + 1) + " message(" + s_message.length() + ") received");
							} else {
								net.forestany.forestj.lib.Global.ilogWarning("could not receive any data");
							}
						} else {
							java.util.Date o_date = (java.util.Date)o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
							
							if (o_date != null) {
								java.text.SimpleDateFormat o_sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss");    
								net.forestany.forestj.lib.Global.ilog("#" + (i + 1) + " message received: '" + o_sdf.format(o_date) + "'");
								a_serverLog.add("#" + (i + 1) + " message received");
							} else {
								net.forestany.forestj.lib.Global.ilogWarning("could not receive any data");
							}
						}
					}
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* CLIENT */
		
		Thread o_threadClient = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND;
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK;
					}
					
					if (p_b_falseUDPtrueTCP) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND;
					}
					
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_clientHost, p_i_clientPort, p_s_clientLocalHost, p_i_clientLocalPort, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					for (int i = 0; i < i_iterations; i++) {
						if (p_b_falseUDPtrueTCP) {
							String s_foo = (i + 1) + ": Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.";
							
							if (p_i_marshallingDataLengthInBytes == 1) {
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
						
						net.forestany.forestj.lib.Global.ilog("message enqueued");
						a_clientLog.add("message enqueued");
						
						if (i == 4) { /* additional delay for 25 milliseconds times sleep multiplier constant, after the 5th time enqueue has been executed */
							Thread.sleep(25 * i_sleepMultiplier);
						}
						
						Thread.sleep(25 * i_sleepMultiplier + ( ((!p_b_falseUDPtrueTCP) && (p_b_highSecurity)) ? 150 : 0 ) + ( ((p_b_falseUDPtrueTCP) && ((p_b_highSecurity) || (p_b_asymmetricSecurity))) ? 25 : 0 ));
					}
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		Thread.sleep(50);
		o_threadClient.start();
		
		o_threadServer.join();
		o_threadClient.join();
		
		/* CHECK LOG ENTRIES */
		
		assertTrue(a_serverLog.size() == 10, "server log has not '10' entries, but '" + a_serverLog.size() + "'");
		assertTrue(a_clientLog.size() == 10, "client log has not '10' entries, but '" + a_clientLog.size() + "'");
		
		if (p_b_falseUDPtrueTCP) {
			int i_expectedLength = 1438;
			
			if (p_i_marshallingDataLengthInBytes == 1) {
				i_expectedLength = 215;
			}
			
			for (int i = 0; i < 10; i++) {
				assertTrue(a_serverLog.get(i).startsWith("#" + (i + 1) + " message(" + ((i == 9) ? (i_expectedLength + 1) : i_expectedLength) + ") received"), "server log entry does not start with '#" + (i + 1) + " message(" + ((i == 9) ? (i_expectedLength + 1) : i_expectedLength) + ") received:', but with '" + a_serverLog.get(i) + "'");
				assertTrue(a_clientLog.get(i).contentEquals("message enqueued"), "client log entry does not match with 'message enqueued', but is '" + a_clientLog.get(i) + "'");
			}
		} else {
			for (int i = 0; i < 10; i++) {
				assertTrue(a_serverLog.get(i).startsWith("#" + (i + 1) + " message received"), "server log entry does not start with '#" + (i + 1) + " message received:', but with '" + a_serverLog.get(i) + "'");
				assertTrue(a_clientLog.get(i).contentEquals("message enqueued"), "client log entry does not match with 'message enqueued', but is '" + a_clientLog.get(i) + "'");
			}
		}
	}
	
	private static void testCommunicationUDPMulticast(boolean p_b_falseIPv4trueIPv6, String p_s_networkInterfaceName, String p_s_serverHost, int p_i_serverPort, String p_s_clientHost, int p_i_clientPort, String p_s_clientLocalHost, int p_i_clientLocalPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_highSecurity, boolean p_b_useMarshalling, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		java.util.List<String> a_serverLog = new java.util.ArrayList<String>();
		java.util.List<String> a_clientLog = new java.util.ArrayList<String>();
		
		int i_comDequeueWaitLoopTimeout = 5000;
		int i_iterations = 10;
		
		/* SERVER */
		
		Thread o_threadServer = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_RECEIVER;
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_serverHost, p_i_serverPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, false, p_b_highSecurity, false, p_b_useMarshalling, false, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					o_communicationConfig.setUDPMulticastReceiverNetworkInterfaceName(p_s_networkInterfaceName);
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					for (int i = 0; i < i_iterations; i++) {
						String s_ip = (String)o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
						
						if (s_ip != null) {
							net.forestany.forestj.lib.Global.ilog("#" + (i + 1) + " message received: '" + s_ip + "'");
							a_serverLog.add("#" + (i + 1) + " message received");
						} else {
							net.forestany.forestj.lib.Global.ilogWarning("could not receive any data");
						}
					}
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* CLIENT */
		
		Thread o_threadClient = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_SENDER;
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_clientHost, p_i_clientPort, p_s_clientLocalHost, p_i_clientLocalPort, p_b_symmetricSecurity128, p_b_symmetricSecurity256, false, p_b_highSecurity, false, p_b_useMarshalling, false, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					o_communicationConfig.setUDPMulticastSenderTTL(3);
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					String s_ip = "COULD_NOT_GET_DEFAULT_IP";
                    java.util.List<java.util.AbstractMap.SimpleEntry<String, String>> a_ips = new java.util.ArrayList<java.util.AbstractMap.SimpleEntry<String, String>>();

                    if (p_b_falseIPv4trueIPv6) {
                    	a_ips = net.forestany.forestj.lib.Helper.GetNetworkInterfacesIpv6();
                    } else {
                    	a_ips = net.forestany.forestj.lib.Helper.GetNetworkInterfacesIpv4();
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
						
						net.forestany.forestj.lib.Global.ilog("message enqueued");
						a_clientLog.add("message enqueued");
						
						if (i == 4) { /* additional delay for 25 milliseconds times sleep multiplier constant, after the 5th time enqueue has been executed */
							Thread.sleep(25 * i_sleepMultiplier);
						}
						
						Thread.sleep(25 * i_sleepMultiplier + ( (p_b_highSecurity) ? 150 : 0 ));
					}
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		Thread.sleep(50);
		o_threadClient.start();
		
		o_threadServer.join();
		o_threadClient.join();
		
		/* CHECK LOG ENTRIES */
		
		assertTrue(a_serverLog.size() == 10, "server log has not '10' entries, but '" + a_serverLog.size() + "'");
		assertTrue(a_clientLog.size() == 10, "client log has not '10' entries, but '" + a_clientLog.size() + "'");
		
		for (int i = 0; i < 10; i++) {
			assertTrue(a_serverLog.get(i).startsWith("#" + (i + 1) + " message received"), "server log entry does not start with '#" + (i + 1) + " message received:', but with '" + a_serverLog.get(i) + "'");
			assertTrue(a_clientLog.get(i).contentEquals("message enqueued"), "client log entry does not match with 'message enqueued', but is '" + a_clientLog.get(i) + "'");
		}
	}
	
	private static void testCommunicationTCPBidirectional(String p_s_serverHost, int p_i_serverPort, String p_s_clientHost, int p_i_clientPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_asymmetricSecurity, boolean p_b_highSecurity, boolean p_b_useMarshalling, boolean p_b_useMarshallingWholeObject, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		java.util.List<String> a_serverLog = new java.util.ArrayList<String>();
		java.util.List<String> a_clientLog = new java.util.ArrayList<String>();
		
		int i_comDequeueWaitLoopTimeout = 2500;
		int i_iterations = 10;
		
		/* SERVER */
		
		Thread o_threadServer = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE, net.forestany.forestj.lib.net.sock.com.Cardinality.EqualBidirectional, p_s_serverHost, p_i_serverPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					o_communicationConfig.setReceiverTimeoutMilliseconds(100);
					o_communicationConfig.setSenderTimeoutMilliseconds(100);
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					for (int i = 0; i < i_iterations; i++) {
						String s_message = (String)o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
						
						if (s_message != null) {
							net.forestany.forestj.lib.Global.ilog("server: #" + (i + 1) + " message(" + s_message.length() + ") received: '" + s_message + "'");
							a_serverLog.add("server: #" + (i + 1) + " message(" + s_message.length() + ") received");
						} else {
							net.forestany.forestj.lib.Global.ilogWarning("server: could not receive any data");
						}
						
						String s_foo = "server: " + (i + 1) + ": Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";
						
						while ( !o_communication.enqueue(
							s_foo
						) ) {
							net.forestany.forestj.lib.Global.ilogWarning("server: could not enqueue message");
						}
						
						net.forestany.forestj.lib.Global.ilog("server: message enqueued");
						a_serverLog.add("server: message enqueued");
						
						if (i == 4) { /* additional delay for 25 milliseconds times sleep multiplier constant, after the 5th time enqueue has been executed */
							Thread.sleep(25 * i_sleepMultiplier);
							
							s_foo = "server: " + (i + 1) + ": Additional Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";
							
							while ( !o_communication.enqueue(
								s_foo
							) ) {
								net.forestany.forestj.lib.Global.ilogWarning("server: could not enqueue message");
							}
							
							net.forestany.forestj.lib.Global.ilog("server: additional message enqueued");
							a_serverLog.add("server: additional message enqueued");
						}
						
						Thread.sleep(25 * i_sleepMultiplier + ((p_b_highSecurity) ? 25 : 0 ));
					}
					
					Thread.sleep(250);
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* CLIENT */
		
		Thread o_threadClient = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND, net.forestany.forestj.lib.net.sock.com.Cardinality.EqualBidirectional, p_s_clientHost, p_i_clientPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					o_communicationConfig.setReceiverTimeoutMilliseconds(100);
					o_communicationConfig.setSenderTimeoutMilliseconds(100);
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					for (int i = 0; i < i_iterations; i++) {
						String s_foo = "client: " + (i + 1) + ": Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";
						
						while ( !o_communication.enqueue(
							s_foo
						) ) {
							net.forestany.forestj.lib.Global.ilogWarning("client: could not enqueue message");
						}
						
						net.forestany.forestj.lib.Global.ilog("client: message enqueued");
						a_clientLog.add("client: message enqueued");
						
						String s_message = (String)o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
						
						if (s_message != null) {
							net.forestany.forestj.lib.Global.ilog("client: #" + (i + 1) + " message(" + s_message.length() + ") received: '" + s_message + "'");
							a_clientLog.add("client: #" + (i + 1) + " message(" + s_message.length() + ") received");
						} else {
							net.forestany.forestj.lib.Global.ilogWarning("client: could not receive any data");
						}
						
						if (i == 4) { /* additional delay for 25 milliseconds times sleep multiplier constant, after the 5th time enqueue has been executed */
							Thread.sleep(25 * i_sleepMultiplier);
						}
						
						if (i == 9) { /* receive additional message */
							s_message = (String)o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
							
							if (s_message != null) {
								net.forestany.forestj.lib.Global.ilog("client: #" + (i + 2) + " message(" + s_message.length() + ") received: '" + s_message + "'");
								a_clientLog.add("client: #" + (i + 2) + " message(" + s_message.length() + ") received");
							} else {
								net.forestany.forestj.lib.Global.ilogWarning("client: could not receive any data");
							}
						}
						
						Thread.sleep(25 * i_sleepMultiplier + ((p_b_highSecurity) ? 25 : 0 ));
					}
					
					Thread.sleep(250);
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		Thread.sleep(50);
		o_threadClient.start();
		
		o_threadServer.join();
		o_threadClient.join();
				
		/* CHECK LOG ENTRIES */
		
		assertTrue(a_serverLog.size() == 21, "server log has not '21' entries, but '" + a_serverLog.size() + "'");
		assertTrue(a_clientLog.size() == 21, "client log has not '21' entries, but '" + a_clientLog.size() + "'");
		
		int i_foo = 166;
		int j = 0;
		
		for (int i = 0; i < 21; i++) {
			if (i == 2) {
				j--;
			}
			
			if ((i - j) == 10) {
				i_foo++;
			}
			
			assertTrue(a_serverLog.get(i).startsWith("server: #" + ((i != 0) ? (i - j) : (i + 1)) + " message(" + i_foo + ") received"), "server log entry does not start with 'server: #" + ((i != 0) ? (i - j) : (i + 1)) + " message(" + i_foo + ") received:', but with '" + a_serverLog.get(i) + "'");
			assertTrue(a_serverLog.get(i + 1).contentEquals("server: message enqueued"), "server log entry does not match with 'server: message enqueued', but is '" + a_serverLog.get(i + 1) + "'");
			i++;
			
			if (i == 9) {
				assertTrue(a_serverLog.get(i + 1).contentEquals("server: additional message enqueued"), "server log entry does not match with 'server: additional message enqueued', but is '" + a_serverLog.get(i + 1) + "'");
				i++;
				j++;
			}
			
			j++;
		}
		
		i_foo = 166;
		j = 0;
		
		for (int i = 0; i < 20; i++) {
			if (i == 2) {
				j--;
			}
			
			assertTrue(a_clientLog.get(i).contentEquals("client: message enqueued"), "client log entry does not match with 'client: message enqueued', but is '" + a_clientLog.get(i) + "'");
			assertTrue(a_clientLog.get(i + 1).startsWith("client: #" + ((i != 0) ? (i - j) : (i + 1)) + " message(" + (((i + 1) == 11) ? (i_foo + 11) : i_foo) + ") received"), "client log entry does not start with 'client: #" + ((i != 0) ? (i - j) : (i + 1)) + " message(" + (((i + 1) == 11) ? (i_foo + 11) : i_foo) + ") received:', but with '" + a_clientLog.get(i + 1) + "'");
			i++;
			
			if (i == 19) {
				assertTrue(a_clientLog.get(i + 1).startsWith("client: #" + ((i != 0) ? (i - j) : (i + 1)) + " message(167) received"), "client log entry does not start with 'client: #" + ((i != 0) ? (i - j) : (i + 1)) + " message(167) received:', but with '" + a_clientLog.get(i + 1) + "'");
				i++;
			}
			
			j++;
		}
	}
	
	private static void testCommunicationTCPHandshakeTask(String p_s_serverHost, int p_i_serverPort, String p_s_clientHost, int p_i_clientPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_asymmetricSecurity, boolean p_b_highSecurity, boolean p_b_useMarshalling, boolean p_b_useMarshallingWholeObject, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		java.util.List<String> a_serverLog = new java.util.ArrayList<String>();
		java.util.List<String> a_clientLog = new java.util.ArrayList<String>();
		
		/* SERVER */
		
		Thread o_threadServer = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_serverHost, p_i_serverPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					
					net.forestany.forestj.lib.net.sock.task.Task<?> o_socketTask = null;
					
					if (p_b_asymmetricSecurity) {
						o_socketTask = new net.forestany.forestj.lib.net.sock.task.recv.HandshakeReceive<javax.net.ssl.SSLServerSocket>(net.forestany.forestj.lib.net.sock.Type.TCP);
					} else {
						o_socketTask = new net.forestany.forestj.lib.net.sock.task.recv.HandshakeReceive<java.net.ServerSocket>(net.forestany.forestj.lib.net.sock.Type.TCP);
					}
					
					((net.forestany.forestj.lib.net.sock.task.recv.HandshakeReceive<?>)o_socketTask).setEndless(true);
					((net.forestany.forestj.lib.net.sock.task.recv.HandshakeReceive<?>)o_socketTask).setTaskIntervalMilliseconds(1000);
					((net.forestany.forestj.lib.net.sock.task.recv.HandshakeReceive<?>)o_socketTask).setDelegateAdditionalExecution(
						new net.forestany.forestj.lib.net.sock.task.recv.HandshakeReceive.IDelegateAdditionalExecution() {
							@Override public void AdditionalExecution() {
								a_serverLog.add("additonal execution from receiver");
							}
						}
					);
					o_communicationConfig.addSocketTask(o_socketTask);
					
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					Thread.sleep(5000);
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* CLIENT */
		
		Thread o_threadClient = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_clientHost, p_i_clientPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);

					net.forestany.forestj.lib.net.sock.task.Task<?> o_socketTask = null;
					
					if (p_b_asymmetricSecurity) {
						o_socketTask = new net.forestany.forestj.lib.net.sock.task.send.HandshakeSend<javax.net.ssl.SSLSocket>(net.forestany.forestj.lib.net.sock.Type.TCP);
					} else {
						o_socketTask = new net.forestany.forestj.lib.net.sock.task.send.HandshakeSend<java.net.Socket>(net.forestany.forestj.lib.net.sock.Type.TCP);
					}
					
					((net.forestany.forestj.lib.net.sock.task.send.HandshakeSend<?>)o_socketTask).setEndless(true);
					((net.forestany.forestj.lib.net.sock.task.send.HandshakeSend<?>)o_socketTask).setTaskIntervalMilliseconds(1000);
					((net.forestany.forestj.lib.net.sock.task.send.HandshakeSend<?>)o_socketTask).setDelegateAdditionalExecution(
						new net.forestany.forestj.lib.net.sock.task.send.HandshakeSend.IDelegateAdditionalExecution() {
							@Override public void AdditionalExecution() {
								a_clientLog.add("additonal execution from sender");
							}
						}
					);
					o_communicationConfig.addSocketTask(o_socketTask);
					
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					Thread.sleep(5000);
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		Thread.sleep(50);
		o_threadClient.start();
		
		o_threadServer.join();
		o_threadClient.join();
		
		Thread.sleep(25 * i_sleepMultiplier);
		
		/* CHECK LOG ENTRIES */
		
		assertTrue(a_serverLog.size() == 5, "server log has not '5' entries, but '" + a_serverLog.size() + "'");
		assertTrue(a_clientLog.size() == 5, "client log has not '5' entries, but '" + a_clientLog.size() + "'");
		
		for (int i = 0; i < 5; i++) {
			assertTrue(a_serverLog.get(i).contentEquals("additonal execution from receiver"), "server log entry does not match with 'additonal execution from receiver', but is '" + a_serverLog.get(i) + "'");
			assertTrue(a_clientLog.get(i).contentEquals("additonal execution from sender"), "client log entry does not match with 'additonal execution from sender', but is '" + a_clientLog.get(i) + "'");
		}
	}
	
	private static void testCommunicationObjectTransmissionTCP(String p_s_serverHost, int p_i_serverPort, String p_s_clientHost, int p_i_clientPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_asymmetricSecurity, boolean p_b_highSecurity, boolean p_b_useMarshalling, boolean p_b_useMarshallingWholeObject, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		java.util.List<String> a_serverLog = new java.util.ArrayList<String>();
		java.util.List<String> a_clientLog = new java.util.ArrayList<String>();
		
		int i_comDequeueWaitLoopTimeout = 5000;
		int i_iterations = 10;
		
		/* SERVER */
		
		Thread o_threadServer = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_serverHost, p_i_serverPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					o_communicationConfig.setObjectTransmission(true);
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					for (int i = 0; i < i_iterations; i++) {
						String s_message = (String)o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
						
						if (s_message != null) {
							net.forestany.forestj.lib.Global.ilog("#" + (i + 1) + " message(" + s_message.length() + ") received: '" + s_message + "'");
							a_serverLog.add("#" + (i + 1) + " message(" + s_message.length() + ") received");
						} else {
							net.forestany.forestj.lib.Global.ilogWarning("could not receive any data");
						}
					}
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* CLIENT */
		
		Thread o_threadClient = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_clientHost, p_i_clientPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					o_communicationConfig.setObjectTransmission(true);
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					for (int i = 0; i < i_iterations; i++) {
						String s_foo = (i + 1) + ": Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.";
						
						if (p_i_marshallingDataLengthInBytes == 1) {
							s_foo = (i + 1) + ": Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum, sed diam nonumy eirmod tempor invidun.";
						}
						
						while ( !o_communication.enqueue(
							s_foo
						) ) {
							net.forestany.forestj.lib.Global.ilogWarning("could not enqueue message");
						}
						
						net.forestany.forestj.lib.Global.ilog("message enqueued");
						a_clientLog.add("message enqueued");
						
						if (i == 4) { /* additional delay for 25 milliseconds times sleep multiplier constant, after the 5th time enqueue has been executed */
							Thread.sleep(25 * i_sleepMultiplier);
						}
						
						Thread.sleep(25 * i_sleepMultiplier + ( (p_b_highSecurity || p_b_asymmetricSecurity) ? 25 : 0 ));
					}
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		Thread.sleep(50);
		o_threadClient.start();
		
		o_threadServer.join();
		o_threadClient.join();
				
		/* CHECK LOG ENTRIES */
		
		assertTrue(a_serverLog.size() == 10, "server log has not '10' entries, but '" + a_serverLog.size() + "'");
		assertTrue(a_clientLog.size() == 10, "client log has not '10' entries, but '" + a_clientLog.size() + "'");
		
		int i_expectedLength = 4308;
		
		if (p_i_marshallingDataLengthInBytes == 1) {
			i_expectedLength = 254;
		}
		
		for (int i = 0; i < 10; i++) {
			assertTrue(a_serverLog.get(i).startsWith("#" + (i + 1) + " message(" + ((i == 9) ? (i_expectedLength + 1) : i_expectedLength) + ") received"), "server log entry does not start with '#" + (i + 1) + " message(" + ((i == 9) ? (i_expectedLength + 1) : i_expectedLength) + ") received:', but with '" + a_serverLog.get(i) + "'");
			assertTrue(a_clientLog.get(i).contentEquals("message enqueued"), "client log entry does not match with 'message enqueued', but is '" + a_clientLog.get(i) + "'");
		}
	}

	private static void testCommunicationWithAnswerOneTCP(String p_s_serverHost, int p_i_serverPort, String p_s_clientHost, int p_i_clientPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_asymmetricSecurity, boolean p_b_highSecurity, boolean p_b_useMarshalling, boolean p_b_useMarshallingWholeObject, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		java.util.List<String> a_serverLog = new java.util.ArrayList<String>();
		java.util.List<String> a_clientLog = new java.util.ArrayList<String>();
		
		int i_comDequeueWaitLoopTimeout = 5000;
		int i_iterations = 10;
		
		/* SERVER */
		
		Thread o_threadServer = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE_WITH_ANSWER, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_serverHost, p_i_serverPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					
					/* add receive socket task(s) */
					net.forestany.forestj.lib.net.sock.task.Task<?> o_receiveSocketTask = null;
					
					if (p_b_asymmetricSecurity) {
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
									fail(o_exc.getMessage());
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
									fail(o_exc.getMessage());
								}
							}
						};
					}
					
					o_receiveSocketTask.addObject("<answer>");
					o_receiveSocketTask.addObject("</answer>");
					
					o_communicationConfig.addReceiveSocketTask(o_receiveSocketTask);
					
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					Thread.sleep(25 * i_sleepMultiplier * (i_iterations / 2) + ( (p_b_highSecurity) ? 3000 : 0 ));
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* CLIENT */
		
		Thread o_threadClient = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND_WITH_ANSWER, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_clientHost, p_i_clientPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					for (int i = 0; i < i_iterations; i++) {
						while ( !o_communication.enqueue(
							Integer.valueOf(i + 1)
						) ) {
							net.forestany.forestj.lib.Global.ilogWarning("could not enqueue message");
						}
						
						net.forestany.forestj.lib.Global.ilog("message enqueued");
						a_clientLog.add("message enqueued");
						
						Object o_answer = o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
						
						if (o_answer != null) {
							net.forestany.forestj.lib.Global.ilog("#" + (i + 1) + " message(" + o_answer.toString().length() + ") received: '" + o_answer.toString() + "'");
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
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		Thread.sleep(50);
		o_threadClient.start();
		
		o_threadServer.join();
		o_threadClient.join();
		
		/* CHECK LOG ENTRIES */
		
		assertTrue(a_serverLog.size() == 10, "server log has not '10' entries, but '" + a_serverLog.size() + "'");
		assertTrue(a_clientLog.size() == 10, "client log has not '10' entries, but '" + a_clientLog.size() + "'");
		
		java.util.List<Integer> a_expectedLength = java.util.Arrays.asList(20, 20, 22, 21, 21, 20, 22, 22, 21, 20);
		
		for (int i = 0; i < 10; i++) {
			assertTrue(a_serverLog.get(i).startsWith("#" + (i + 1) + " message(" + a_expectedLength.get(i) + ") received"), "server log entry does not start with '#" + (i + 1) + " message(" + a_expectedLength.get(i) + ") received:', but with '" + a_serverLog.get(i) + "'");
			assertTrue(a_clientLog.get(i).contentEquals("message enqueued"), "client log entry does not match with 'message enqueued', but is '" + a_clientLog.get(i) + "'");
		}
	}

	private static void testCommunicationWithAnswerTwoTCP(String p_s_serverHost, int p_i_serverPort, String p_s_clientHost, int p_i_clientPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_asymmetricSecurity, boolean p_b_highSecurity, boolean p_b_useMarshalling, boolean p_b_useMarshallingWholeObject, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		java.util.List<String> a_serverLog = new java.util.ArrayList<String>();
		java.util.List<String> a_clientLog = new java.util.ArrayList<String>();
		
		int i_comDequeueWaitLoopTimeout = 5000;
		int i_iterations = 14;
		
		/* SERVER */
		
		Thread o_threadServer = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE_WITH_ANSWER, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_serverHost, p_i_serverPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					
					/* add receive socket task(s) */
					net.forestany.forestj.lib.net.sock.task.Task<?> o_receiveSocketTask = null;
					
					if (p_b_asymmetricSecurity) {
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
									fail(o_exc.getMessage());
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
									fail(o_exc.getMessage());
								}
							}
						};
					}
					
					o_receiveSocketTask.addObject("<answer>");
					o_receiveSocketTask.addObject("</answer>");
					
					o_communicationConfig.addReceiveSocketTask(o_receiveSocketTask);
					
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					Thread.sleep(25 * i_sleepMultiplier * (i_iterations / 2) + ( (p_b_highSecurity) ? 4000 : 0 ));
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* CLIENT */
		
		Thread o_threadClient = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND_WITH_ANSWER, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_clientHost, p_i_clientPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					for (int i = 0; i < i_iterations; i++) {
						while ( !o_communication.enqueue(
							java.time.LocalDate.now().plusDays(i)
						) ) {
							net.forestany.forestj.lib.Global.ilogWarning("could not enqueue message");
						}
						
						net.forestany.forestj.lib.Global.ilog("message enqueued");
						a_clientLog.add("message enqueued");
						
						Object o_answer = o_communication.dequeueWithWaitLoop(i_comDequeueWaitLoopTimeout);
						
						if (o_answer != null) {
							net.forestany.forestj.lib.Global.ilog("#" + (i + 1) + " message(" + o_answer.toString().length() + ") received: '" + o_answer.toString() + "'");
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
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		Thread.sleep(50);
		o_threadClient.start();
		
		o_threadServer.join();
		o_threadClient.join();
		
		/* CHECK LOG ENTRIES */
		
		assertTrue(a_serverLog.size() == 14, "server log has not '14' entries, but '" + a_serverLog.size() + "'");
		assertTrue(a_clientLog.size() == 14, "client log has not '14' entries, but '" + a_clientLog.size() + "'");
		
		int i_expectedLength = 27;
		
		for (int i = 0; i < 14; i++) {
			assertTrue(a_serverLog.get(i).startsWith("#" + (i + 1) + " message(" + i_expectedLength + ") received"), "server log entry does not start with '#" + (i + 1) + " message(" + i_expectedLength + ") received:', but with '" + a_serverLog.get(i) + "'");
			String s_foo = java.time.LocalDate.now().plusDays(i).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			assertTrue(a_serverLog.get(i).contains(s_foo), "server log entry does not contain '" + s_foo + "', entry value: '" + a_serverLog.get(i) + "'");
			assertTrue(a_clientLog.get(i).contentEquals("message enqueued"), "client log entry does not match with 'message enqueued', but is '" + a_clientLog.get(i) + "'");
		}
	}

	private static void testCommunicationSharedMemoryUniDirectional(boolean p_b_falseUDPtrueTCP, boolean p_b_udpWithAck, String p_s_serverHost, int p_i_serverPort, String p_s_clientHost, int p_i_clientPort, String p_s_clientLocalHost, int p_i_clientLocalPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_asymmetricSecurity, boolean p_b_highSecurity, boolean p_b_useMarshalling, boolean p_b_useMarshallingWholeObject, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		java.util.List<String> a_expectedResults = new java.util.ArrayList<String>();
		java.util.List<String> a_serverResults = new java.util.ArrayList<String>();
		java.util.List<String> a_clientResults = new java.util.ArrayList<String>();
		
		/* expected result */
		String s_expectedResult = "Id = 42|UUID = a8dfc91d-ec7e-4a5f-9a9c-243edd91e271|ShortText = NULL|Text = Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.|SmallInt = 0|Int = 21|BigInt = 546789546|Timestamp = NULL|Date = NULL|Time = NULL|LocalDateTime = NULL|LocalDate = 2003-03-03|LocalTime = NULL|DoubleCol = 1.2345|Decimal = NULL|Bool = true|Text2 = NULL|ShortText2 = NULL|FloatValue = 0.0|";
		
		for (String s_foo : s_expectedResult.split("\\|")) {
			a_expectedResults.add(s_foo);
		}
		
		/* SERVER */
		
		Thread o_threadServer = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE;
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK;
					}
					
					if (p_b_falseUDPtrueTCP) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE;
					}
					
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_s_serverHost, p_i_serverPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					
					SharedMemoryExample o_sharedMemoryExample = new SharedMemoryExample();
					o_sharedMemoryExample.initiateMirrors();
					o_communicationConfig.setSharedMemory(o_sharedMemoryExample);
					o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
					
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					int i_additionalTime = 25;
					
					if (p_b_highSecurity) {
						if (p_b_falseUDPtrueTCP) {
							i_additionalTime = 60;
						} else {
							i_additionalTime = 90;	
						}
					}
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						i_additionalTime += 225;
					}
					
					if ( (p_b_falseUDPtrueTCP) && (p_b_asymmetricSecurity) ) {
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
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* CLIENT */
		
		Thread o_threadClient = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND;
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK;
					}
					
					if (p_b_falseUDPtrueTCP) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND;
					}
					
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_s_clientHost, p_i_clientPort, p_s_clientLocalHost, p_i_clientLocalPort, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					
					SharedMemoryExample o_sharedMemoryExample = new SharedMemoryExample();
					o_sharedMemoryExample.initiateMirrors();
					o_communicationConfig.setSharedMemory(o_sharedMemoryExample);
					o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
					
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
			
					int i_additionalTime = 25;
					
					if (p_b_highSecurity) {
						if (p_b_falseUDPtrueTCP) {
							i_additionalTime = 60;
						} else {
							i_additionalTime = 90;	
						}
					}
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						i_additionalTime += 225;
					}
					
					if ( (p_b_falseUDPtrueTCP) && (p_b_asymmetricSecurity) ) {
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
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		Thread.sleep(50);
		o_threadClient.start();
		
		o_threadServer.join();
		o_threadClient.join();
		
		/* CHECK RESULT */
		
		assertTrue(a_expectedResults.size() == a_serverResults.size(), "server result has not the expected amount of fields '" + a_expectedResults.size() + "!='" + a_serverResults.size());
		assertTrue(a_expectedResults.size() == a_clientResults.size(), "client result has not the expected amount of fields '" + a_expectedResults.size() + "!='" + a_clientResults.size());
		
		int i_missingFieldsServer = 0;
		int i_missingFieldsClient = 0;
		
		for (int i = 0; i < a_expectedResults.size(); i++) {
			if (!a_expectedResults.get(i).contentEquals(a_serverResults.get(i))) {
				i_missingFieldsServer++;
				//System.out.println("server field result not equal expected result:\t" + a_serverResults.get(i) + "\t != \t" + a_expectedResults.get(i));
			}
			
			if (!a_expectedResults.get(i).contentEquals(a_clientResults.get(i))) {
				i_missingFieldsClient++;
				//System.out.println("client field result not equal expected result:\t" + a_clientResults.get(i) + "\t != \t" + a_expectedResults.get(i));
			}
		}
		
		assertFalse(i_missingFieldsServer > 5, i_missingFieldsServer + " server fields not matching expected values");
		assertFalse(i_missingFieldsClient > 5, i_missingFieldsClient + " client fields not matching expected values");
		
//		if (i_missingFieldsServer > 0) {
//			System.out.println("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " server: " + i_missingFieldsServer);
//		} else {
//			System.out.println("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " server: fine");
//		}
//		
//		if (i_missingFieldsClient > 0) {
//			System.out.println("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " client: " + i_missingFieldsClient);
//		} else {
//			System.out.println("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " client: fine");
//		}
	}
	
	private static void testCommunicationSharedMemoryBiDirectional(boolean p_b_falseUDPtrueTCP, boolean p_b_udpWithAck, String p_s_serverHost, int p_i_serverPort, String p_s_clientHost, int p_i_clientPort, String p_s_biServerHost, int p_i_biServerPort, String p_s_biClientHost, int p_i_biClientPort, String p_s_clientLocalHost, int p_i_clientLocalPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_asymmetricSecurity, boolean p_b_highSecurity, boolean p_b_useMarshalling, boolean p_b_useMarshallingWholeObject, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		java.util.List<String> a_expectedResults = new java.util.ArrayList<String>();
		java.util.List<String> a_serverResults = new java.util.ArrayList<String>();
		java.util.List<String> a_clientResults = new java.util.ArrayList<String>();
		
		/* expected result */
		String s_expectedResult = "Id = 42|UUID = 26cf332e-3f23-4523-9911-60207c8db7fd|ShortText = NULL|Text = Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum.|SmallInt = 0|Int = 50791|BigInt = 0|Timestamp = NULL|Date = NULL|Time = NULL|LocalDateTime = NULL|LocalDate = 2004-04-04|LocalTime = NULL|DoubleCol = 5.4321|Decimal = NULL|Bool = false|Text2 = At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.|ShortText2 = Mission accomplished.|FloatValue = 2.114014|";
		
		for (String s_foo : s_expectedResult.split("\\|")) {
			a_expectedResults.add(s_foo);
		}
						
		/* SERVER */
		
		Thread o_threadServer = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE;
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK;
					}
					
					if (p_b_falseUDPtrueTCP) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE;
					}
					
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_s_serverHost, p_i_serverPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, p_b_falseUDPtrueTCP, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					
					SharedMemoryExample o_sharedMemoryExample = new SharedMemoryExample();
					o_sharedMemoryExample.initiateMirrors();
					o_communicationConfig.setSharedMemory(o_sharedMemoryExample);
					o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
					
					o_communicationConfig.setSharedMemoryBidirectional(
						java.util.Arrays.asList(
							new java.util.AbstractMap.SimpleEntry<String,Integer>(p_s_biServerHost, p_i_biServerPort)
						),
						(p_b_falseUDPtrueTCP) ? o_communicationConfig.getSSLContextList() : null
					);
					
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					int i_additionalTime = 25;
					
					if (p_b_highSecurity) {
						if (p_b_falseUDPtrueTCP) {
							i_additionalTime = 60;
						} else {
							i_additionalTime = 90;
						}
					}
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						i_additionalTime += 225;
					}
					
					if ( (p_b_falseUDPtrueTCP) && (p_b_asymmetricSecurity) ) {
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
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* CLIENT */
		
		Thread o_threadClient = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND;
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK;
					}
					
					if (p_b_falseUDPtrueTCP) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND;
					}
					
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_s_clientHost, p_i_clientPort, p_s_clientLocalHost, p_i_clientLocalPort, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, p_b_falseUDPtrueTCP, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					
					SharedMemoryExample o_sharedMemoryExample = new SharedMemoryExample();
					o_sharedMemoryExample.initiateMirrors();
					o_communicationConfig.setSharedMemory(o_sharedMemoryExample);
					o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
					
					o_communicationConfig.setSharedMemoryBidirectional(
						java.util.Arrays.asList(
							new java.util.AbstractMap.SimpleEntry<String,Integer>(p_s_biClientHost, p_i_biClientPort)
						),
						(p_b_falseUDPtrueTCP) ? o_communicationConfig.getSSLContextList() : null
					);
					
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
			
					int i_additionalTime = 25;
					
					if (p_b_highSecurity) {
						if (p_b_falseUDPtrueTCP) {
							i_additionalTime = 60;
						} else {
							i_additionalTime = 90;
						}
					}
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						i_additionalTime += 225;
					}
					
					if ( (p_b_falseUDPtrueTCP) && (p_b_asymmetricSecurity) ) {
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
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		Thread.sleep(50);
		o_threadClient.start();
		
		o_threadServer.join();
		o_threadClient.join();
		
		/* CHECK RESULT */
		
		assertTrue(a_expectedResults.size() == a_serverResults.size(), "server result has not the expected amount of fields '" + a_expectedResults.size() + "!='" + a_serverResults.size());
		assertTrue(a_expectedResults.size() == a_clientResults.size(), "client result has not the expected amount of fields '" + a_expectedResults.size() + "!='" + a_clientResults.size());
		
		int i_missingFieldsServer = 0;
		int i_missingFieldsClient = 0;
		
		for (int i = 0; i < a_expectedResults.size(); i++) {
			if (!a_expectedResults.get(i).contentEquals(a_serverResults.get(i))) {
				i_missingFieldsServer++;
				//System.out.println("server field result not equal expected result:\t" + a_serverResults.get(i) + "\t != \t" + a_expectedResults.get(i));
			}
			
			if (!a_expectedResults.get(i).contentEquals(a_clientResults.get(i))) {
				i_missingFieldsClient++;
				//System.out.println("client field result not equal expected result:\t" + a_clientResults.get(i) + "\t != \t" + a_expectedResults.get(i));
			}
		}
		
		assertFalse(i_missingFieldsServer > 4, i_missingFieldsServer + " server fields not matching expected values");
		assertFalse(i_missingFieldsClient > 4, i_missingFieldsClient + " client fields not matching expected values");
		
//		if (i_missingFieldsServer > 0) {
//			System.out.println("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " server: " + i_missingFieldsServer);
//		} else {
//			System.out.println("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " server: fine");
//		}
//		
//		if (i_missingFieldsClient > 0) {
//			System.out.println("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " client: " + i_missingFieldsClient);
//		} else {
//			System.out.println("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + " client: fine");
//		}
	}

	private static void testCommunicationMarshallingObject(boolean p_b_falseUDPtrueTCP, boolean p_b_udpWithAck, boolean p_b_smallObject, String p_s_serverHost, int p_i_serverPort, String p_s_clientHost, int p_i_clientPort, String p_s_clientLocalHost, int p_i_clientLocalPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_asymmetricSecurity, boolean p_b_highSecurity, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		java.util.List<Object> a_serverObjects = new java.util.ArrayList<Object>();
		java.util.List<Object> a_clientObjects = new java.util.ArrayList<Object>();
		
		int i_comDequeueWaitLoopTimeout = 5000;
		int i_iterations = 10;
		
		/* SERVER */
		
		Thread o_threadServer = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_sock = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE;
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						e_sock = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK;
					}
					
					if (p_b_falseUDPtrueTCP) {
						e_sock = net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE;
					}
					
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_sock, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_serverHost, p_i_serverPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, true, false, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					
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
							net.forestany.forestj.lib.Global.ilog("#" + (i + 1) + " object received");
						} else {
							net.forestany.forestj.lib.Global.ilogWarning("could not receive any data");
						}
					}
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* CLIENT */
		
		Thread o_threadClient = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_sock = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND;
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						e_sock = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK;
					}
					
					if (p_b_falseUDPtrueTCP) {
						e_sock = net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND;
					}
					
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_sock, net.forestany.forestj.lib.net.sock.com.Cardinality.Equal, p_s_clientHost, p_i_clientPort, p_s_clientLocalHost, p_i_clientLocalPort, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, true, false, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);

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
						
						net.forestany.forestj.lib.Global.ilog("object enqueued");
						a_clientObjects.add(o_foo);
						
						if (i == 4) { /* additional delay for 25 milliseconds times sleep multiplier constant, after the 5th time enqueue has been executed */
							Thread.sleep(25 * i_sleepMultiplier);
						}
						
						Thread.sleep(25 * i_sleepMultiplier + ( (p_b_highSecurity) ? 50 : 0 ) + ( (p_b_udpWithAck) ? 225 : 0 ));
					}
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		Thread.sleep(50);
		o_threadClient.start();
		
		o_threadServer.join();
		o_threadClient.join();
				
		/* CHECK RESULTS */
		
		/* strip trailing zeroes for decimal, decimal array and decimal list */
		for (int i = 0; i < a_serverObjects.size(); i++) {
			if (!p_b_smallObject) {
				net.forestany.forestj.lib.test.nettest.msg.MessageObject o_foo = (net.forestany.forestj.lib.test.nettest.msg.MessageObject) a_serverObjects.get(i);
				
				if ( (o_foo.getDecimal() != null) && (o_foo.getDecimal() != new java.math.BigDecimal(0d)) ) {
					o_foo.setDecimal(o_foo.getDecimal().stripTrailingZeros());
				}
				
				for (int j = 0; j < o_foo.getDecimalArray().length; j++) {
					if ( (o_foo.getDecimalArray()[j] != null) && (o_foo.getDecimalArray()[j] != new java.math.BigDecimal(0d)) ) {
						o_foo.getDecimalArray()[j] = o_foo.getDecimalArray()[j].stripTrailingZeros();
					}
				}
				
				for (int j = 0; j < o_foo.getDecimalList().size(); j++) {
					if ( (o_foo.getDecimalList().get(j) != null) && (o_foo.getDecimal() != new java.math.BigDecimal(0d)) ) {
						o_foo.getDecimalList().set(j, o_foo.getDecimalList().get(j).stripTrailingZeros());
					}
				}
		    } else {
		    	net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject o_foo = (net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject) a_serverObjects.get(i);
		    	
		    	for (int j = 0; j < o_foo.getDecimalArray().length; j++) {
					if ( (o_foo.getDecimalArray()[j] != null) && (o_foo.getDecimalArray()[j] != new java.math.BigDecimal(0d)) ) {
						o_foo.getDecimalArray()[j] = o_foo.getDecimalArray()[j].stripTrailingZeros();
					}
				}
		    }
		}
		
		/* strip trailing zeroes for decimal, decimal array and decimal list */
		for (int i = 0; i < a_clientObjects.size(); i++) {
			if (!p_b_smallObject) {
				net.forestany.forestj.lib.test.nettest.msg.MessageObject o_foo = (net.forestany.forestj.lib.test.nettest.msg.MessageObject) a_clientObjects.get(i);
				
				if ( (o_foo.getDecimal() != null) && (o_foo.getDecimal() != new java.math.BigDecimal(0d)) ) {
					o_foo.setDecimal(o_foo.getDecimal().stripTrailingZeros());
				}
				
				for (int j = 0; j < o_foo.getDecimalArray().length; j++) {
					if ( (o_foo.getDecimalArray()[j] != null) && (o_foo.getDecimalArray()[j] != new java.math.BigDecimal(0d)) ) {
						o_foo.getDecimalArray()[j] = o_foo.getDecimalArray()[j].stripTrailingZeros();
					}
				}
				
				for (int j = 0; j < o_foo.getDecimalList().size(); j++) {
					if ( (o_foo.getDecimalList().get(j) != null) && (o_foo.getDecimal() != new java.math.BigDecimal(0d)) ) {
						o_foo.getDecimalList().set(j, o_foo.getDecimalList().get(j).stripTrailingZeros());
					}
				}
		    } else {
		    	net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject o_foo = (net.forestany.forestj.lib.test.nettest.msg.SmallMessageObject) a_clientObjects.get(i);
		    	
		    	for (int j = 0; j < o_foo.getDecimalArray().length; j++) {
					if ( (o_foo.getDecimalArray()[j] != null) && (o_foo.getDecimalArray()[j] != new java.math.BigDecimal(0d)) ) {
						o_foo.getDecimalArray()[j] = o_foo.getDecimalArray()[j].stripTrailingZeros();
					}
				}
		    }
		}
		
		if (p_b_udpWithAck)
		{
		    int i_limit = 7;

		    if (!p_b_smallObject)
		    {
		        i_limit = 4;
		    }

		    assertTrue(a_serverObjects.size() > i_limit, "server object list must have at least '" + (i_limit + 1) + "' entries, but '" + a_serverObjects.size() + "'");
			assertTrue(a_clientObjects.size() > i_limit, "client object list must have at least '" + (i_limit + 1) + "' entries, but '" + a_clientObjects.size() + "'");
		    
		    for (int i = 0; i < a_serverObjects.size(); i++)
		    {
		        assertTrue( net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_serverObjects.get(i), a_clientObjects.get(i), true, true) , "server object and client object are not equal" );
		    }
		}
		else
		{
		    int i_limit = 9;

		    if (!p_b_smallObject)
		    {
		        i_limit = 7;
		    }

		    assertTrue(a_serverObjects.size() > i_limit, "server object list must have at least '" + (i_limit + 1) + "' entries, but '" + a_serverObjects.size() + "'");
			assertTrue(a_clientObjects.size() > i_limit, "client object list must have at least '" + (i_limit + 1) + "' entries, but '" + a_clientObjects.size() + "'");
		    
		    for (int i = 0; i < a_serverObjects.size(); i++)
		    {
		        assertTrue( net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_serverObjects.get(i), a_clientObjects.get(i), true, true) , "server object and client object are not equal" );
		    }
		}
	}

	private static void testCommunicationMarshallingSharedMemoryUniDirectional(boolean p_b_falseUDPtrueTCP, boolean p_b_smallObject, boolean p_b_massChangeAtEnd, boolean p_b_udpWithAck, String p_s_serverHost, int p_i_serverPort, String p_s_clientHost, int p_i_clientPort, String p_s_clientLocalHost, int p_i_clientLocalPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_asymmetricSecurity, boolean p_b_highSecurity, boolean p_b_useMarshalling, boolean p_b_useMarshallingWholeObject, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		java.util.List<String> a_expectedResults = new java.util.ArrayList<String>();
		java.util.List<String> a_serverResults = new java.util.ArrayList<String>();
		java.util.List<String> a_clientResults = new java.util.ArrayList<String>();
		
		/* expected result */
		String s_expectedResult = "true | 0 | [ 1, 3, 5, -123, 42, 0, null, -102 ] | 42 | o | [ A, F, K, , U, *, null, ó ] | 0.0 | [ 1.25, 3.5, 5.75, 10.101, 41.998, 0.0, 4984654.5 ] | 0.0 | [ 1.25, 3.5, 5.75, 10.101, 41.998, 0.0, null, 8798546.2154656 ] | 16426 | 16426 | 0 | [ 1, 3, 5, 536870954, 42, 0 ] | [ 1, 3, 5, 536870954, 42, 0, null ] | 536870954 | 1170936177994235946 | [ 1, 3, 5, 1170936177994235946, 42, 0, null ] | 0 | [ 1, 3, 5, 1170936177994235946, 42, 0 ] | Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. | [ Hello World 1!, Hello World 2!, Hello World 3!, Hello World 4!, Hello World 5!, null, null ] | 06:02:03 | 2020-03-04 | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ] | 22:16:06 | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | 2020-03-04T06:02:03 | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ]";
		
		if (p_b_smallObject) {
			s_expectedResult = "true | o | [ 1, 3, 5, 536870954, 42, 0 ] | [ 1, 3, 5, 1170936177994235946, 42, 0, null ] | Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. | 22:16:06 | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ]";
		}
		
		if (p_b_massChangeAtEnd) {
			if (p_b_smallObject) {
				s_expectedResult = "true | ò | [ 1, 3, 5, 536870954, 42, 0 ] | [ 1, 3, 5, 1170936177994235946, 42, 0, null ] | Hello World! | 06:02:03 | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ]";
			} else {
				s_expectedResult = "true | [ true, false, true, false, true ] | [ true, false, true, false, true, null ] | 42 | [ 1, 3, 5, -123, 42, 0, -102 ] | [ 1, 3, 5, -123, 42, 0, null, -102 ] | 42 | [ 1, 3, 5, -123, 42, 0, -102 ] | [ 1, 3, 5, 10, 42, 0, null, -102 ] | ò | [ A, F, K, , U, *, ó ] | [ A, F, K, , U, *, null, ó ] | 42.25 | [ 1.25, 3.5, 5.75, 10.101, 41.998, 0.0, 4984654.5 ] | [ 1.25, 3.5, 5.75, 10.101, 41.998, 0.0, null, 4984654.5 ] | 42.75 | [ 1.25, 3.5, 5.75, 10.101, 41.998, 0.0, 8798546.2154656 ] | [ 1.25, 3.5, 5.75, 10.101, 41.998, 0.0, null, 8798546.2154656 ] | 16426 | [ 1, 3, 5, 16426, 42, 0 ] | [ 1, 3, 5, 10, 42, 0, null ] | 16426 | [ 1, 3, 5, 16426, 42, 0 ] | [ 1, 3, 5, 16426, 42, 0, null ] | 536870954 | [ 1, 3, 5, 536870954, 42, 0 ] | [ 1, 3, 5, 536870954, 42, 0, null ] | 536870954 | [ 1, 3, 5, 536870954, 42, 0 ] | [ 1, 3, 5, 536870954, 42, 0, null ] | 1170936177994235946 | [ 1, 3, 5, 1170936177994235946, 42, 0 ] | [ 1, 3, 5, 1170936177994235946, 42, 0, null ] | 1170936177994235946 | [ 1, 3, 5, 1170936177994235946, 42, 0 ] | [ 1, 3, 5, 1170936177994235946, 42, 0, null ] | Hello World! | [ Hello World 1!, Hello World 2!, Hello World 3!, Hello World 4!, Hello World 5!, null, null ] | [ Hello World 1!, Hello World 2!, Hello World 3!, Hello World 4!, Hello World 5!, null, null ] | 06:02:03 | [ 06:02:03, 09:24:16, 12:48:53, null ] | [ 06:02:03, 09:24:16, 12:48:53, null ] | 2020-03-04 | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | 2020-03-04T06:02:03 | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ] | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ] | 06:02:03 | [ 06:02:03, 09:24:16, 12:48:53, null ] | [ 06:02:03, 09:24:16, 12:48:53, null ] | 2020-03-04 | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | 2020-03-04T06:02:03 | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ] | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ]";
			}
		}
		
		for (String s_foo : s_expectedResult.split("\\|")) {
			a_expectedResults.add(s_foo);
		}
		
		/* SERVER */
		
		Thread o_threadServer = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE;
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK;
					}
					
					if (p_b_falseUDPtrueTCP) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE;
					}
					
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_s_serverHost, p_i_serverPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					net.forestany.forestj.lib.net.sock.com.SharedMemory<?> o_sharedMemory = null;
					
					if (!p_b_smallObject) {
						o_sharedMemory = new SharedMemoryMessageObject();
						((SharedMemoryMessageObject)o_sharedMemory).emptyAll();
						o_sharedMemory.initiateMirrors();
						o_communicationConfig.setSharedMemory(o_sharedMemory);
					} else {
						o_sharedMemory = new SharedMemorySmallMessageObject();
						((SharedMemorySmallMessageObject)o_sharedMemory).emptyAll();
						o_sharedMemory.initiateMirrors();
						o_communicationConfig.setSharedMemory(o_sharedMemory);
					}
					
					o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
					
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					int i_additionalTime = 25;
					
					if (p_b_falseUDPtrueTCP) { /* TCP */
						if (p_b_highSecurity) {
							i_additionalTime *= 3;
						}
						
						if (p_b_asymmetricSecurity) {
							i_additionalTime *= 3;
						}
						
						if (p_b_massChangeAtEnd) {
							/* nothing */
						}
						
						if (p_i_marshallingDataLengthInBytes > 3) {
							i_additionalTime *= (p_i_marshallingDataLengthInBytes / 2);
						}
					} else { /* UDP */
						if (p_b_highSecurity) {
							i_additionalTime *= 5;
						}
						
						if (p_b_udpWithAck) {
							i_additionalTime += 225;
						}
						
						if (p_b_massChangeAtEnd) {
							i_additionalTime *= 1.5;
						}
						
						i_additionalTime *= p_i_marshallingDataLengthInBytes;
					}
					
					Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
					
					Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);

					Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
					
					Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
					
					if (p_b_massChangeAtEnd) {
						Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
					}
					
					/* server result */
					for (String s_foo : o_sharedMemory.toString().split("\\|")) {
						a_serverResults.add(s_foo);
					}
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* CLIENT */
		
		Thread o_threadClient = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND;
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK;
					}
					
					if (p_b_falseUDPtrueTCP) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND;
					}
					
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_s_clientHost, p_i_clientPort, p_s_clientLocalHost, p_i_clientLocalPort, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, false, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					net.forestany.forestj.lib.net.sock.com.SharedMemory<?> o_sharedMemory = null;
					
					if (!p_b_smallObject) {
						o_sharedMemory = new SharedMemoryMessageObject();
						((SharedMemoryMessageObject)o_sharedMemory).emptyAll();
						o_sharedMemory.initiateMirrors();
						o_communicationConfig.setSharedMemory(o_sharedMemory);
					} else {
						o_sharedMemory = new SharedMemorySmallMessageObject();
						((SharedMemorySmallMessageObject)o_sharedMemory).emptyAll();
						o_sharedMemory.initiateMirrors();
						o_communicationConfig.setSharedMemory(o_sharedMemory);
					}
					
					o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
					
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
			
					int i_additionalTime = 25;
					
					if (p_b_falseUDPtrueTCP) { /* TCP */
						if (p_b_highSecurity) {
							i_additionalTime *= 3;
						}
						
						if (p_b_asymmetricSecurity) {
							i_additionalTime *= 3;
						}
						
						if (p_b_massChangeAtEnd) {
							/* nothing */
						}
						
						if (p_i_marshallingDataLengthInBytes > 3) {
							i_additionalTime *= (p_i_marshallingDataLengthInBytes / 2);
						}
					} else { /* UDP */
						if (p_b_highSecurity) {
							i_additionalTime *= 5;
						}
						
						if (p_b_udpWithAck) {
							i_additionalTime += 225;
						}
						
						if (p_b_massChangeAtEnd) {
							i_additionalTime *= 1.5;
						}
						
						i_additionalTime *= p_i_marshallingDataLengthInBytes;
					}
					
					Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
					
					if (!p_b_smallObject) {
						o_sharedMemory.setField("String", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
						o_sharedMemory.setField("LocalTime", java.time.LocalTime.of(15, 32, 03));
						o_sharedMemory.setField("IntegerArray", new int[] { 1, 3, 5, 536870954, 42, 0 });
						o_sharedMemory.setField("Short", (short)16426);
						o_sharedMemory.setField("UnsignedShort", (short)16426);
						o_sharedMemory.setField("IntegerList", java.util.Arrays.asList( 1, 3, 5, 536870954, -42, 0, null ));
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
						o_sharedMemory.setField("LongList", java.util.Arrays.asList( 1l, 3l, 5l, 1170936177994235946l, -42l, 0l, null ));
						o_sharedMemory.setField("Char", 'o');
						o_sharedMemory.setField("String", "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
						o_sharedMemory.setField("LocalDateArray", new java.time.LocalDate[] { java.time.LocalDate.of(2020, 3, 4), java.time.LocalDate.of(2020, 6, 8), java.time.LocalDate.of(2020, 12, 16), null });
						o_sharedMemory.setField("Bool", true);
						o_sharedMemory.setField("ByteList", java.util.Arrays.asList( (byte)1, (byte)3, (byte)5, (byte)133, (byte)42, (byte)0, null, (byte)102 ));
						o_sharedMemory.setField("UnsignedByte", (byte)42);
						o_sharedMemory.setField("CharList", java.util.Arrays.asList( (char)65, (char)70, (char)75, (char)133, (char)85, (char)0, null, (char)243 ));
						o_sharedMemory.setField("FloatArray", new float[] { 1.25f, 3.5f, 5.75f, 10.1010f, 41.998f, 0.f, 4984654.5498795465f });
						o_sharedMemory.setField("DoubleList", java.util.Arrays.asList( 1.25d, 3.5d, 5.75d, 10.1010d, -41.998d, 0.d, null, 8798546.2154656d ));
					} else {
						o_sharedMemory.setField("LongList", java.util.Arrays.asList( 1l, 3l, 5l, 1170936177994235946l, -42l, 0l, null ));
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
					
					if (p_b_massChangeAtEnd) {
						if (!p_b_smallObject) {
							((SharedMemoryMessageObject)o_sharedMemory).initAll();
						} else {
							((SharedMemorySmallMessageObject)o_sharedMemory).initAll();
						}
						
						Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
					}
					
					/* client result */
					for (String s_foo : o_sharedMemory.toString().split("\\|")) {
						a_clientResults.add(s_foo);
					}
					
					if (o_communication != null) {
						o_communication.stop();
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		Thread.sleep(50);
		o_threadClient.start();
		
		o_threadServer.join();
		o_threadClient.join();
		
		/* CHECK RESULT */
		
		int i_missingFieldsServer = 0;
		int i_missingFieldsClient = 0;
		
		for (int i = 0; i < a_serverResults.size(); i++) {
			if (!a_expectedResults.get(i).contentEquals(a_serverResults.get(i))) {
				i_missingFieldsServer++;
				//System.out.println("server field result not equal expected result:\t" + a_serverResults.get(i) + "\t != \t" + a_expectedResults.get(i));
			}
		}
		
		for (int i = 0; i < a_clientResults.size(); i++) {	
			if (!a_expectedResults.get(i).contentEquals(a_clientResults.get(i))) {
				i_missingFieldsClient++;
				//System.out.println("client field result not equal expected result:\t" + a_clientResults.get(i) + "\t != \t" + a_expectedResults.get(i));
			}
		}
		
		if (i_missingFieldsServer > 10) {
			i_fails++;
		} else if (i_missingFieldsClient > 10) {
			i_fails++;
		} else {
			assertFalse(i_missingFieldsServer > 4, i_missingFieldsServer + " server fields not matching expected values");
			assertFalse(i_missingFieldsClient > 4, i_missingFieldsClient + " client fields not matching expected values");
			
//			if (i_missingFieldsServer > 0) {
//				System.out.println("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + ((p_b_massChangeAtEnd) ? " - mass" : "") + " server: " + i_missingFieldsServer);
//			} else {
//				System.out.println("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + ((p_b_massChangeAtEnd) ? " - mass" : "") + " server: fine");
//			}
//			
//			if (i_missingFieldsClient > 0) {
//				System.out.println("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + ((p_b_massChangeAtEnd) ? " - mass" : "") + " client: " + i_missingFieldsClient);
//			} else {
//				System.out.println("uni " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + ((p_b_massChangeAtEnd) ? " - mass" : "") + " client: fine");
//			}
		}
	}

	private static void testCommunicationMarshallingSharedMemoryBiDirectional(boolean p_b_falseUDPtrueTCP, boolean p_b_smallObject, boolean p_b_udpWithAck, String p_s_serverHost, int p_i_serverPort, String p_s_clientHost, int p_i_clientPort, String p_s_biServerHost, int p_i_biServerPort, String p_s_biClientHost, int p_i_biClientPort, String p_s_clientLocalHost, int p_i_clientLocalPort, boolean p_b_symmetricSecurity128, boolean p_b_symmetricSecurity256, boolean p_b_asymmetricSecurity, boolean p_b_highSecurity, boolean p_b_useMarshalling, boolean p_b_useMarshallingWholeObject, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, boolean p_b_marshallingSystemUsesLittleEndian) throws Exception {
		java.util.List<String> a_expectedResults = new java.util.ArrayList<String>();
		java.util.List<String> a_serverResults = new java.util.ArrayList<String>();
		java.util.List<String> a_clientResults = new java.util.ArrayList<String>();
		
		/* expected result */
		String s_expectedResult = "true | 0 | [ 1, 3, 5, -123, 42, 0, null, -102 ] | 42 | o | [ A, F, K, , U, *, null, ó ] | 0.0 | [ 1.25, 3.5, 5.75, 10.101, 41.998, 0.0, 4984654.5 ] | 0.0 | [ 1.25, 3.5, 5.75, 10.101, 41.998, 0.0, null, 8798546.2154656 ] | 16426 | 16426 | 0 | [ 1, 3, 5, 536870954, 42, 0 ] | [ 1, 3, 5, 536870954, 42, 0, null ] | 536870954 | 1170936177994235946 | [ 1, 3, 5, 1170936177994235946, 42, 0, null ] | 0 | [ 1, 3, 5, 1170936177994235946, 42, 0 ] | Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. | [ Hello World 1!, Hello World 2!, Hello World 3!, Hello World 4!, Hello World 5!, null, null ] | 06:02:03 | 2020-03-04 | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ] | 22:16:06 | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | 2020-03-04T06:02:03 | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ]";
		
		if (p_b_smallObject) {
			s_expectedResult = "true | o | [ 1, 3, 5, 536870954, 42, 0 ] | [ 1, 3, 5, 1170936177994235946, 42, 0, null ] | Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. | 22:16:06 | [ 2020-03-04, 2020-06-08, 2020-12-16, null ] | [ 2020-03-04T06:02:03, 2020-06-08T09:24:16, 2020-12-16T12:48:53, null ]";
		}
		
		for (String s_foo : s_expectedResult.split("\\|")) {
			a_expectedResults.add(s_foo);
		}
		
		/* SERVER */
		
		Thread o_threadServer = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE;
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK;
					}
					
					if (p_b_falseUDPtrueTCP) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE;
					}
					
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_s_serverHost, p_i_serverPort, null, 0, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, p_b_falseUDPtrueTCP, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					net.forestany.forestj.lib.net.sock.com.SharedMemory<?> o_sharedMemory = null;
					
					if (!p_b_smallObject) {
						o_sharedMemory = new SharedMemoryMessageObject();
						((SharedMemoryMessageObject)o_sharedMemory).emptyAll();
						o_sharedMemory.initiateMirrors();
						o_communicationConfig.setSharedMemory(o_sharedMemory);
					} else {
						o_sharedMemory = new SharedMemorySmallMessageObject();
						((SharedMemorySmallMessageObject)o_sharedMemory).emptyAll();
						o_sharedMemory.initiateMirrors();
						o_communicationConfig.setSharedMemory(o_sharedMemory);
					}
					
					o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
					
					o_communicationConfig.setSharedMemoryBidirectional(
						java.util.Arrays.asList(
							new java.util.AbstractMap.SimpleEntry<String,Integer>(p_s_biServerHost, p_i_biServerPort)
						),
						(p_b_falseUDPtrueTCP) ? o_communicationConfig.getSSLContextList() : null
					);
					
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
					
					int i_additionalTime = 25;
					
					if (p_b_falseUDPtrueTCP) { /* TCP */
						if (p_b_highSecurity) {
							i_additionalTime *= 3;
						}
						
						if (p_b_asymmetricSecurity) {
							i_additionalTime *= 3;
						}
						
						if (p_i_marshallingDataLengthInBytes > 3) {
							i_additionalTime *= (p_i_marshallingDataLengthInBytes / 2);
						}
					} else { /* UDP */
						if (p_b_highSecurity) {
							i_additionalTime *= 5;
						}
						
						if (p_b_udpWithAck) {
							i_additionalTime += 225;
						}
						
						i_additionalTime *= p_i_marshallingDataLengthInBytes;
					}
					
					Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
					
					Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);

					if (!p_b_smallObject) {
						o_sharedMemory.setField("LongList", java.util.Arrays.asList( 1l, 3l, 5l, 1170936177994235946l, -42l, 0l, null ));
						o_sharedMemory.setField("Char", 'o');
						o_sharedMemory.setField("String", "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
						o_sharedMemory.setField("LocalDateArray", new java.time.LocalDate[] { java.time.LocalDate.of(2020, 3, 4), java.time.LocalDate.of(2020, 6, 8), java.time.LocalDate.of(2020, 12, 16), null });
						o_sharedMemory.setField("Bool", true);
						o_sharedMemory.setField("ByteList", java.util.Arrays.asList( (byte)1, (byte)3, (byte)5, (byte)133, (byte)42, (byte)0, null, (byte)102 ));
						o_sharedMemory.setField("UnsignedByte", (byte)42);
						o_sharedMemory.setField("CharList", java.util.Arrays.asList( (char)65, (char)70, (char)75, (char)133, (char)85, (char)0, null, (char)243 ));
						o_sharedMemory.setField("FloatArray", new float[] { 1.25f, 3.5f, 5.75f, 10.1010f, 41.998f, 0.f, 4984654.5498795465f });
						o_sharedMemory.setField("DoubleList", java.util.Arrays.asList( 1.25d, 3.5d, 5.75d, 10.1010d, -41.998d, 0.d, null, 8798546.2154656d ));
					} else {
						o_sharedMemory.setField("LongList", java.util.Arrays.asList( 1l, 3l, 5l, 1170936177994235946l, -42l, 0l, null ));
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
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* CLIENT */
		
		Thread o_threadClient = new Thread() {
			public void run() {
				try {
					net.forestany.forestj.lib.net.sock.com.Type e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND;
					
					if ( (!p_b_falseUDPtrueTCP) && (p_b_udpWithAck) ) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK;
					}
					
					if (p_b_falseUDPtrueTCP) {
						e_type = net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND;
					}
					
					net.forestany.forestj.lib.net.sock.com.Config o_communicationConfig = getCommunicationConfig(e_type, net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket, p_s_clientHost, p_i_clientPort, p_s_clientLocalHost, p_i_clientLocalPort, p_b_symmetricSecurity128, p_b_symmetricSecurity256, p_b_asymmetricSecurity, p_b_highSecurity, p_b_falseUDPtrueTCP, p_b_useMarshalling, p_b_useMarshallingWholeObject, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_b_marshallingSystemUsesLittleEndian);
					net.forestany.forestj.lib.net.sock.com.SharedMemory<?> o_sharedMemory = null;
					
					if (!p_b_smallObject) {
						o_sharedMemory = new SharedMemoryMessageObject();
						((SharedMemoryMessageObject)o_sharedMemory).emptyAll();
						o_sharedMemory.initiateMirrors();
						o_communicationConfig.setSharedMemory(o_sharedMemory);
					} else {
						o_sharedMemory = new SharedMemorySmallMessageObject();
						((SharedMemorySmallMessageObject)o_sharedMemory).emptyAll();
						o_sharedMemory.initiateMirrors();
						o_communicationConfig.setSharedMemory(o_sharedMemory);
					}
					
					o_communicationConfig.setSharedMemoryTimeoutMilliseconds(10);
					
					o_communicationConfig.setSharedMemoryBidirectional(
						java.util.Arrays.asList(
							new java.util.AbstractMap.SimpleEntry<String,Integer>(p_s_biClientHost, p_i_biClientPort)
						),
						(p_b_falseUDPtrueTCP) ? o_communicationConfig.getSSLContextList() : null
					);
					
					net.forestany.forestj.lib.net.sock.com.Communication o_communication = new net.forestany.forestj.lib.net.sock.com.Communication(o_communicationConfig);
					o_communication.start();
			
					int i_additionalTime = 25;
					
					if (p_b_falseUDPtrueTCP) { /* TCP */
						if (p_b_highSecurity) {
							i_additionalTime *= 3;
						}
						
						if (p_b_asymmetricSecurity) {
							i_additionalTime *= 3;
						}
						
						if (p_i_marshallingDataLengthInBytes > 3) {
							i_additionalTime *= (p_i_marshallingDataLengthInBytes / 2);
						}
					} else { /* UDP */
						if (p_b_highSecurity) {
							i_additionalTime *= 5;
						}
						
						if (p_b_udpWithAck) {
							i_additionalTime += 225;
						}
						
						i_additionalTime *= p_i_marshallingDataLengthInBytes;
					}
					
					Thread.sleep((25 * i_sleepMultiplier) + i_additionalTime);
					
					if (!p_b_smallObject) {
						o_sharedMemory.setField("String", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
						o_sharedMemory.setField("LocalTime", java.time.LocalTime.of(15, 32, 03));
						o_sharedMemory.setField("IntegerArray", new int[] { 1, 3, 5, 536870954, 42, 0 });
						o_sharedMemory.setField("Short", (short)16426);
						o_sharedMemory.setField("UnsignedShort", (short)16426);
						o_sharedMemory.setField("IntegerList", java.util.Arrays.asList( 1, 3, 5, 536870954, -42, 0, null ));
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
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
					fail(o_exc.getMessage());
				}
			}
		};
		
		/* START SERVER + CLIENT */
		
		o_threadServer.start();
		Thread.sleep(50);
		o_threadClient.start();
		
		o_threadServer.join();
		o_threadClient.join();
		
		/* CHECK RESULT */
		
		int i_missingFieldsServer = 0;
		int i_missingFieldsClient = 0;
		
		for (int i = 0; i < a_serverResults.size(); i++) {
			if (!a_expectedResults.get(i).contentEquals(a_serverResults.get(i))) {
				i_missingFieldsServer++;
				//System.out.println("server field result not equal expected result:\t" + a_serverResults.get(i) + "\t != \t" + a_expectedResults.get(i));
			}
		}
		
		for (int i = 0; i < a_clientResults.size(); i++) {	
			if (!a_expectedResults.get(i).contentEquals(a_clientResults.get(i))) {
				i_missingFieldsClient++;
				//System.out.println("client field result not equal expected result:\t" + a_clientResults.get(i) + "\t != \t" + a_expectedResults.get(i));
			}
		}
		
		if (i_missingFieldsServer > 10) {
			i_fails++;
		} else if (i_missingFieldsClient > 10) {
			i_fails++;
		} else {
			assertFalse(i_missingFieldsServer > 6, i_missingFieldsServer + " server fields not matching expected values");
			assertFalse(i_missingFieldsClient > 6, i_missingFieldsClient + " client fields not matching expected values");
			
//			if (i_missingFieldsServer > 0) {
//				System.out.println("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + " server: " + i_missingFieldsServer);
//			} else {
//				System.out.println("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + ((p_b_massChangeAtEnd) ? " - mass" : "") + " server: fine");
//			}
//			
//			if (i_missingFieldsClient > 0) {
//				System.out.println("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + " client: " + i_missingFieldsClient);
//			} else {
//				System.out.println("bi " + ((p_b_falseUDPtrueTCP) ? "tcp" : "udp") + ((p_b_udpWithAck) ? " - with ACK" : "") + ((p_b_smallObject) ? " - small" : "") + ((p_b_massChangeAtEnd) ? " - mass" : "") + " client: fine");
//			}
		}
	}
}
