package net.forestany.forestj.lib.test.nettest.msg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test message box marshalling
 */
public class MessageBoxMarshallingTest {
	/**
	 * method to test message box marshalling
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testMessageBoxMarshalling() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			net.forestany.forestj.lib.net.msg.MessageBox o_messageBox = new net.forestany.forestj.lib.net.msg.MessageBox(1, 1500);
			
			int i_dataLengthInBytes = 1;
			boolean b_usePropertyMethods = false;
			
			testMessageBoxMarshallingObjects(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingPrimitives(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingArrays(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			
			i_dataLengthInBytes = 2;
			
			testMessageBoxMarshallingObjects(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingPrimitives(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingArrays(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			
			i_dataLengthInBytes = 3;
			
			testMessageBoxMarshallingObjects(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingPrimitives(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingArrays(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			
			i_dataLengthInBytes = 4;
			
			testMessageBoxMarshallingObjects(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingPrimitives(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingArrays(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			
			i_dataLengthInBytes = 1;
			b_usePropertyMethods = true;
			
			testMessageBoxMarshallingObjects(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingPrimitives(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingArrays(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			
			i_dataLengthInBytes = 2;
			
			testMessageBoxMarshallingObjects(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingPrimitives(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingArrays(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			
			i_dataLengthInBytes = 3;
			
			testMessageBoxMarshallingObjects(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingPrimitives(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingArrays(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			
			i_dataLengthInBytes = 4;
			
			testMessageBoxMarshallingObjects(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingPrimitives(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
			testMessageBoxMarshallingArrays(o_messageBox, i_dataLengthInBytes, b_usePropertyMethods);
		} catch (Exception o_exc) {
			o_exc.printStackTrace();
			fail(o_exc.getMessage());
		}
	}

	private void testMessageBoxMarshallingObjects(net.forestany.forestj.lib.net.msg.MessageBox p_o_messageBox, int p_i_dataLengthInBytes, boolean p_b_usePropertyMethods) throws Exception {
		MessageObject o_messageObject = new MessageObject();
		o_messageObject.initAll();
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(o_messageObject, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		o_messageObject.emptyAll();
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(o_messageObject, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		SmallMessageObject o_smallMessageObject = new SmallMessageObject();
		o_smallMessageObject.initAll();
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(o_smallMessageObject, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		o_smallMessageObject.emptyAll();
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(o_smallMessageObject, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		MessageObject o_returnMessageObject = (MessageObject)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		MessageObject o_returnMessageObjectEmpty = (MessageObject)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		SmallMessageObject o_returnSmallMessageObject = (SmallMessageObject)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		SmallMessageObject o_returnSmallMessageObjectEmpty = (SmallMessageObject)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		
		/* correct the small deviation with string null and empty string - empty strings are always interpreted as null */
		o_returnMessageObject.getStringArray()[5] = "";
		o_returnMessageObject.getStringList().set(5, "");
		
		/* strip trailing zeroes for decimal, decimal array and decimal list */
		if ( (o_returnMessageObject.getDecimal() != null) && (o_returnMessageObject.getDecimal() != new java.math.BigDecimal(0d)) ) {
			o_returnMessageObject.setDecimal(o_returnMessageObject.getDecimal().stripTrailingZeros());
		}
		
		if ( (o_returnMessageObjectEmpty.getDecimal() != null) && (o_returnMessageObjectEmpty.getDecimal() != new java.math.BigDecimal(0d)) ) {
			o_returnMessageObjectEmpty.setDecimal(o_returnMessageObjectEmpty.getDecimal().stripTrailingZeros());
		}
		
		for (int i = 0; i < o_returnMessageObject.getDecimalArray().length; i++) {
			if ( (o_returnMessageObject.getDecimalArray()[i] != null) && (o_returnMessageObject.getDecimal() != new java.math.BigDecimal(0d)) ) {
				o_returnMessageObject.getDecimalArray()[i] = o_returnMessageObject.getDecimalArray()[i].stripTrailingZeros();
			}
		}
		
		for (int i = 0; i < o_returnMessageObject.getDecimalList().size(); i++) {
			if ( (o_returnMessageObject.getDecimalList().get(i) != null) && (o_returnMessageObject.getDecimal() != new java.math.BigDecimal(0d)) ) {
				o_returnMessageObject.getDecimalList().set(i, o_returnMessageObject.getDecimalList().get(i).stripTrailingZeros());
			}
		}
		
		for (int i = 0; i < o_returnSmallMessageObject.getDecimalArray().length; i++) {
			if ( (o_returnSmallMessageObject.getDecimalArray()[i] != null) && (o_returnSmallMessageObject.getDecimalArray()[i] != new java.math.BigDecimal(0d)) ) {
				o_returnSmallMessageObject.getDecimalArray()[i] = o_returnSmallMessageObject.getDecimalArray()[i].stripTrailingZeros();
			}
		}
		
		o_messageObject.initAll();
		
		assertTrue(
			net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_messageObject, o_returnMessageObject, p_b_usePropertyMethods, true),
			"message object could not be retrieved identically"
		);
		
		o_messageObject.emptyAll();
		
		assertTrue(
			net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_messageObject, o_returnMessageObjectEmpty, p_b_usePropertyMethods, true),
			"empty message object could not be retrieved identically"
		);
		
		o_smallMessageObject.initAll();
		
		assertTrue(
			net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_smallMessageObject, o_returnSmallMessageObject, p_b_usePropertyMethods, true),
			"small message object could not be retrieved identically"
		);
		
		o_smallMessageObject.emptyAll();
		
		assertTrue(
			net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_smallMessageObject, o_returnSmallMessageObjectEmpty, p_b_usePropertyMethods, true),
			"empty small message object could not be retrieved identically"
		);
	}
	
	private void testMessageBoxMarshallingPrimitives(net.forestany.forestj.lib.net.msg.MessageBox p_o_messageBox, int p_i_dataLengthInBytes, boolean p_b_usePropertyMethods) throws Exception {
		boolean b_varBool = true;
		byte by_varByte = (byte)42;
		byte by_varSignedByte = (byte)-42;
		char c_varChar = (char)242;
		float f_varFloat = 42.25f;
		double d_varDouble = 42.75d;
		short sh_varShort = (short)-16426;
		short sh_varUnsignedShort = (short)16426;
		int i_varInteger = -536870954;
		int i_varUnsignedInteger = 536870954;
		long l_varLong = -1170936177994235946l;
		long l_varUnsignedLong = 1170936177994235946l;
		String s_varString = "Hello World!";
		java.util.Date o_varTime = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("01.01.1970 06:02:03");
		java.util.Date o_varDate = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("04.03.2020 00:00:00");
		java.util.Date o_varDateTime = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("04.03.2020 06:02:03");
		java.time.LocalTime o_varLocalTime = java.time.LocalTime.of(6, 2, 3);
		java.time.LocalDate o_varLocalDate = java.time.LocalDate.of(2020, 3, 4);
		java.time.LocalDateTime o_varLocalDateTime = java.time.LocalDateTime.of(2020, 3, 4, 6, 2, 3);
		java.math.BigDecimal o_varBigDecimal = new java.math.BigDecimal("-268435477.6710886925");
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(b_varBool, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(by_varByte, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(by_varSignedByte, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(c_varChar, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(f_varFloat, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(d_varDouble, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(sh_varShort, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(sh_varUnsignedShort, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(i_varInteger, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(i_varUnsignedInteger, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(l_varLong, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(l_varUnsignedLong, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(s_varString, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(o_varTime, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(o_varDate, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(o_varDateTime, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(o_varLocalTime, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(o_varLocalDate, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(o_varLocalDateTime, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(o_varBigDecimal, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		boolean b_varBoolDequeued = (boolean)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		byte by_varByteDequeued = (byte)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		byte by_varSignedByteDequeued = (byte)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		char c_varCharDequeued = (char)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		float f_varFloatDequeued = (float)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		double d_varDoubleDequeued = (double)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		short sh_varShortDequeued = (short)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		short sh_varUnsignedShortDequeued = (short)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		int i_varIntegerDequeued = (int)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		int i_varUnsignedIntegerDequeued = (int)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		long l_varLongDequeued = (long)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		long l_varUnsignedLongDequeued = (long)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		String s_varStringDequeued = (String)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.util.Date o_varTimeDequeued = (java.util.Date)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.util.Date o_varDateDequeued = (java.util.Date)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.util.Date o_varDateTimeDequeued = (java.util.Date)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.time.LocalTime o_varLocalTimeDequeued = (java.time.LocalTime)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.time.LocalDate o_varLocalDateDequeued = (java.time.LocalDate)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.time.LocalDateTime o_varLocalDateTimeDequeued = (java.time.LocalDateTime)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.math.BigDecimal o_varBigDecimalDequeued = (java.math.BigDecimal)p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		
		assertEquals(b_varBool, b_varBoolDequeued, "message box transfer for 'boolean' primitive variable failed");
		assertEquals(by_varByte, by_varByteDequeued, "message box transfer for 'byte' primitive variable failed");
		assertEquals(by_varSignedByte, by_varSignedByteDequeued, "message box transfer for 'signed byte' primitive variable failed");
		assertEquals(c_varChar, c_varCharDequeued, "message box transfer for 'char' primitive variable failed");
		assertEquals(f_varFloat, f_varFloatDequeued, "message box transfer for 'float' primitive variable failed");
		assertEquals(d_varDouble, d_varDoubleDequeued, "message box transfer for 'double' primitive variable failed");
		assertEquals(sh_varShort, sh_varShortDequeued, "message box transfer for 'short' primitive variable failed");
		assertEquals(sh_varUnsignedShort, sh_varUnsignedShortDequeued, "message box transfer for 'unsigned short' primitive variable failed");
		assertEquals(i_varInteger, i_varIntegerDequeued, "message box transfer for 'int' primitive variable failed");
		assertEquals(i_varUnsignedInteger, i_varUnsignedIntegerDequeued, "message box transfer for 'unsigned int' primitive variable failed");
		assertEquals(l_varLong, l_varLongDequeued, "message box transfer for 'long' primitive variable failed");
		assertEquals(l_varUnsignedLong, l_varUnsignedLongDequeued, "message box transfer for 'unsigned long' primitive variable failed");
		assertEquals(s_varString, s_varStringDequeued, "message box transfer for 'string' primitive variable failed");
		assertEquals(o_varTime, o_varTimeDequeued, "message box transfer for 'time' primitive variable failed");
		assertEquals(o_varDate, o_varDateDequeued, "message box transfer for 'date' primitive variable failed");
		assertEquals(o_varDateTime, o_varDateTimeDequeued, "message box transfer for 'date time' primitive variable failed");
		assertEquals(o_varLocalTime, o_varLocalTimeDequeued, "message box transfer for 'local time' primitive variable failed");
		assertEquals(o_varLocalDate, o_varLocalDateDequeued, "message box transfer for 'local date' primitive variable failed");
		assertEquals(o_varLocalDateTime, o_varLocalDateTimeDequeued, "message box transfer for 'local date time' primitive variable failed");
		assertEquals(o_varBigDecimal, o_varBigDecimalDequeued.stripTrailingZeros(), "message box transfer for 'big decimal' primitive variable failed");
	}
	
	private void testMessageBoxMarshallingArrays(net.forestany.forestj.lib.net.msg.MessageBox p_o_messageBox, int p_i_dataLengthInBytes, boolean p_b_usePropertyMethods) throws Exception {
		boolean[] a_varBoolArray = new boolean[] { true, false, true, false, true };
		byte[] a_varByteArray = new byte[] { 1, 3, 5, (byte)133, 42, 0, (byte)102 };
		byte[] a_varSignedByteArray = new byte[] { 1, 3, 5, (byte)127, 42, 0, (byte)-102 };
		char[] a_varCharArray = new char[] { 65, 70, 75, 133, 85, 0, 243 };
		float[] a_varFloatArray = new float[] { 1.25f, 3.5f, 5.75f, 10.1010f, -41.998f, 0.f, 4984654.5498795465f };
		double[] a_varDoubleArray = new double[] { 1.25d, 3.5d, 5.75d, 10.1010d, -41.998d, 0.d, 8798546.2154656d };
		short[] a_varShortArray = new short[] { 1, 3, 5, -16426, 42, 0 };
		short[] a_varUnsignedShortArray = new short[] { 1, 3, 5, 16426, 42, 0 };
		int[] a_varIntegerArray = new int[] { 1, 3, 5, -536870954, 42, 0 };
		int[] a_varUnsignedIntegerArray = new int[] { 1, 3, 5, 536870954, 42, 0 };
		long[] a_varLongArray = new long[] { 1l, 3l, 5l, -1170936177994235946l, 42l, 0l };
		long[] a_varUnsignedLongArray = new long[] { 1l, 3l, 5l, 1170936177994235946l, 42l, 0l };
		String[] a_varStringArray = new String[] { "Hello World 1!", "Hello World 2!", "Hello World 3!", "Hello World 4!", "Hello World 5!", "", null };
		java.util.Date[] a_varTimeArray = new java.util.Date[] {
			new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("01.01.1970 06:02:03"),
			new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("01.01.1970 09:24:16"),
			new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("01.01.1970 12:48:53"),
			null
		};
		java.util.Date[] a_varDateArray = new java.util.Date[] {
			new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("04.03.2020 00:00:00"),
			new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("08.06.2020 00:00:00"),
			new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("16.12.2020 00:00:00"),
			null
		};
		java.util.Date[] a_varDateTimeArray = new java.util.Date[] {
			new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("04.03.2020 06:02:03"),
			new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("08.06.2020 09:24:16"),
			new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("16.12.2020 12:48:53"),
			null
		};
		java.time.LocalTime[] a_varLocalTimeArray = new java.time.LocalTime[] { java.time.LocalTime.of(6, 2, 3), java.time.LocalTime.of(9, 24, 16), java.time.LocalTime.of(12, 48, 53), null };
		java.time.LocalDate[] a_varLocalDateArray = new java.time.LocalDate[] { java.time.LocalDate.of(2020, 3, 4), java.time.LocalDate.of(2020, 6, 8), java.time.LocalDate.of(2020, 12, 16), null };
		java.time.LocalDateTime[] a_varLocalDateTimeArray = new java.time.LocalDateTime[] { java.time.LocalDateTime.of(2020, 3, 4, 6, 2, 3), java.time.LocalDateTime.of(2020, 6, 8, 9, 24, 16), java.time.LocalDateTime.of(2020, 12, 16, 12, 48, 53), null };
		java.math.BigDecimal[] a_varBigDecimalArray = new java.math.BigDecimal[] {
			new java.math.BigDecimal("+578875020153.73804901109397"),
			new java.math.BigDecimal("-36.151686185423327"),
			new java.math.BigDecimal("+71740124.12171120119"),
			new java.math.BigDecimal("-2043204985254.1196"),
			new java.math.BigDecimal(0d),
			new java.math.BigDecimal("+601.9924")
		};
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varBoolArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varByteArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varSignedByteArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varCharArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varFloatArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varDoubleArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varShortArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varUnsignedShortArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varIntegerArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varUnsignedIntegerArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varLongArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varUnsignedLongArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varStringArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varTimeArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varDateArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varDateTimeArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varLocalTimeArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varLocalDateArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varLocalDateTimeArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		if (!p_o_messageBox.enqueueObjectWithMarshalling(a_varBigDecimalArray, p_i_dataLengthInBytes, p_b_usePropertyMethods)) {
			throw new Exception("Could not enqueue object");
		}
		
		boolean[] a_varBoolArrayDequeued = (boolean[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		byte[] a_varByteArrayDequeued = (byte[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		byte[] a_varSignedByteArrayDequeued = (byte[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		char[] a_varCharArrayDequeued = (char[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		float[] a_varFloatArrayDequeued = (float[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		double[] a_varDoubleArrayDequeued = (double[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		short[] a_varShortArrayDequeued = (short[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		short[] a_varUnsignedShortArrayDequeued = (short[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		int[] a_varIntegerArrayDequeued = (int[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		int[] a_varUnsignedIntegerArrayDequeued = (int[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		long[] a_varLongArrayDequeued = (long[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		long[] a_varUnsignedLongArrayDequeued = (long[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		String[] a_varStringArrayDequeued = (String[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.util.Date[] a_varTimeArrayDequeued = (java.util.Date[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.util.Date[] a_varDateArrayDequeued = (java.util.Date[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.util.Date[] a_varDateTimeArrayDequeued = (java.util.Date[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.time.LocalTime[] a_varLocalTimeArrayDequeued = (java.time.LocalTime[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.time.LocalDate[] a_varLocalDateArrayDequeued = (java.time.LocalDate[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.time.LocalDateTime[] a_varLocalDateTimeArrayDequeued = (java.time.LocalDateTime[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		java.math.BigDecimal[] a_varBigDecimalArrayDequeued = (java.math.BigDecimal[])p_o_messageBox.dequeueObjectWithMarshalling(p_b_usePropertyMethods);
		
		/* correct the small deviation with string null and empty string - empty strings are always interpreted as null */
		a_varStringArrayDequeued[5] = "";
		
		/* strip trailing zeroes for decimal array */
		for (int i = 0; i < a_varBigDecimalArrayDequeued.length; i++) {
			if (a_varBigDecimalArrayDequeued[i] != null) {
				a_varBigDecimalArrayDequeued[i] = a_varBigDecimalArrayDequeued[i].stripTrailingZeros();
			}
		}
		
		assertArrayEquals(a_varBoolArray, a_varBoolArrayDequeued, "message box transfer for 'boolean' array variable failed");
		assertArrayEquals(a_varByteArray, a_varByteArrayDequeued, "message box transfer for 'byte' array variable failed");
		assertArrayEquals(a_varSignedByteArray, a_varSignedByteArrayDequeued, "message box transfer for 'signed byte' array variable failed");
		assertArrayEquals(a_varCharArray, a_varCharArrayDequeued, "message box transfer for 'char' array variable failed");
		assertArrayEquals(a_varFloatArray, a_varFloatArrayDequeued, "message box transfer for 'float' array variable failed");
		assertArrayEquals(a_varDoubleArray, a_varDoubleArrayDequeued, "message box transfer for 'double' array variable failed");
		assertArrayEquals(a_varShortArray, a_varShortArrayDequeued, "message box transfer for 'short' v variable failed");
		assertArrayEquals(a_varUnsignedShortArray, a_varUnsignedShortArrayDequeued, "message box transfer for 'unsigned short' array variable failed");
		assertArrayEquals(a_varIntegerArray, a_varIntegerArrayDequeued, "message box transfer for 'int' array variable failed");
		assertArrayEquals(a_varUnsignedIntegerArray, a_varUnsignedIntegerArrayDequeued, "message box transfer for 'unsigned int' array variable failed");
		assertArrayEquals(a_varLongArray, a_varLongArrayDequeued, "message box transfer for 'long' array variable failed");
		assertArrayEquals(a_varUnsignedLongArray, a_varUnsignedLongArrayDequeued, "message box transfer for 'unsigned long' array variable failed");
		assertArrayEquals(a_varStringArray, a_varStringArrayDequeued, "message box transfer for 'string' array variable failed");
		assertArrayEquals(a_varTimeArray, a_varTimeArrayDequeued, "message box transfer for 'time' array variable failed");
		assertArrayEquals(a_varDateArray, a_varDateArrayDequeued, "message box transfer for 'date' array variable failed");
		assertArrayEquals(a_varDateTimeArray, a_varDateTimeArrayDequeued, "message box transfer for 'date time' array variable failed");
		assertArrayEquals(a_varLocalTimeArray, a_varLocalTimeArrayDequeued, "message box transfer for 'local time' array variable failed");
		assertArrayEquals(a_varLocalDateArray, a_varLocalDateArrayDequeued, "message box transfer for 'local date' array variable failed");
		assertArrayEquals(a_varLocalDateTimeArray, a_varLocalDateTimeArrayDequeued, "message box transfer for 'local date time' array variable failed");
		assertArrayEquals(a_varBigDecimalArray, a_varBigDecimalArrayDequeued, "message box transfer for 'big decimal' array variable failed");
	}
}
