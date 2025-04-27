package net.forestany.forestj.lib.test.nettest.msg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test message box
 */
public class MessageBoxTest {
	/**
	 * method to test message box
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testMessageBox() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			net.forestany.forestj.lib.test.nettest.Data o_data = new net.forestany.forestj.lib.test.nettest.Data();
			
			java.util.List<net.forestany.forestj.lib.test.nettest.Data.SimpleClass> a_data = new java.util.ArrayList<net.forestany.forestj.lib.test.nettest.Data.SimpleClass>();
			a_data.add(o_data.new SimpleClass("Record #1 Value A", "Record #1 Value B", "Record #1 Value C"));
			a_data.add(o_data.new SimpleClass("Record #2 Value A", "Record #2 Value B", "", java.util.Arrays.asList(1, 2, -3, -4)));
			a_data.add(o_data.new SimpleClass("Record #3 Value A", "Record #3 Value B", "Record #3 Value C", java.util.Arrays.asList(9, 8, -7, -6)));
			a_data.add(o_data.new SimpleClass("Record #4 Value A", "Record #4 Value B", null, java.util.Arrays.asList(16, 32, null, 128)));
			
			net.forestany.forestj.lib.test.nettest.Data.SimpleClassCollection o_foo = o_data.new SimpleClassCollection(a_data);
			
			/* insert into message box */
			net.forestany.forestj.lib.net.msg.MessageBox o_messageBox = new net.forestany.forestj.lib.net.msg.MessageBox(1, 1500);
			
			if (!o_messageBox.enqueueObject(o_foo)) {
				throw new Exception("Could not enqueue object");
			}
			
			/* clear variables */
			o_foo = null;
			a_data = null;
			
			/* remove from message box */
			o_foo = (net.forestany.forestj.lib.test.nettest.Data.SimpleClassCollection)o_messageBox.dequeueObject();
			
			if (o_foo == null) {
				throw new Exception("could not dequeue object, is null");
			}
			
			a_data = o_foo.SimpleClasses;
			int i = 0;
			
			for (net.forestany.forestj.lib.test.nettest.Data.SimpleClass o_simpleClassObject : a_data) {
				if (i == 0) {
					assertEquals(o_simpleClassObject.ValueA, "Record #1 Value A", "dequeued object #" + (i + 1) + " ValueA does not match with origin");
					assertEquals(o_simpleClassObject.ValueB, "Record #1 Value B", "dequeued object #" + (i + 1) + " ValueB does not match with origin");
					assertEquals(o_simpleClassObject.ValueC, "Record #1 Value C", "dequeued object #" + (i + 1) + " ValueC does not match with origin");
					assertEquals(o_simpleClassObject.ValueD, new java.util.ArrayList<Integer>(), "dequeued object #" + (i + 1) + " ValueD does not match with origin");
				} else if (i == 1) {
					assertEquals(o_simpleClassObject.ValueA, "Record #2 Value A", "dequeued object #" + (i + 1) + " ValueA does not match with origin");
					assertEquals(o_simpleClassObject.ValueB, "Record #2 Value B", "dequeued object #" + (i + 1) + " ValueB does not match with origin");
					assertEquals(o_simpleClassObject.ValueC, "", "dequeued object #" + (i + 1) + " ValueC does not match with origin");
					assertEquals(o_simpleClassObject.ValueD, java.util.Arrays.asList(1, 2, -3, -4), "dequeued object #" + (i + 1) + " ValueD does not match with origin");
				} else if (i == 2) {
					assertEquals(o_simpleClassObject.ValueA, "Record #3 Value A", "dequeued object #" + (i + 1) + " ValueA does not match with origin");
					assertEquals(o_simpleClassObject.ValueB, "Record #3 Value B", "dequeued object #" + (i + 1) + " ValueB does not match with origin");
					assertEquals(o_simpleClassObject.ValueC, "Record #3 Value C", "dequeued object #" + (i + 1) + " ValueC does not match with origin");
					assertEquals(o_simpleClassObject.ValueD, java.util.Arrays.asList(9, 8, -7, -6), "dequeued object #" + (i + 1) + " ValueD does not match with origin");
				} else if (i == 3) {
					assertEquals(o_simpleClassObject.ValueA, "Record #4 Value A", "dequeued object #" + (i + 1) + " ValueA does not match with origin");
					assertEquals(o_simpleClassObject.ValueB, "Record #4 Value B", "dequeued object #" + (i + 1) + " ValueB does not match with origin");
					assertEquals(o_simpleClassObject.ValueC, null, "dequeued object #" + (i + 1) + " ValueC does not match with origin");
					assertEquals(o_simpleClassObject.ValueD, java.util.Arrays.asList(16, 32, null, 128), "dequeued object #" + (i + 1) + " ValueD does not match with origin");
				}
				
	    		i++;
	    	}
			
			/* insert into message box */
			if (!o_messageBox.enqueueObject(a_data)) {
				throw new Exception("Could not enqueue object");
			}
			
			a_data = null;
			
			/* remove from message box */
			@SuppressWarnings("unchecked")
			java.util.List<net.forestany.forestj.lib.test.nettest.Data.SimpleClass> a_foo = (java.util.List<net.forestany.forestj.lib.test.nettest.Data.SimpleClass>)o_messageBox.dequeueObject();
			a_data = a_foo;
			
			if (a_data == null) {
				throw new Exception("could not dequeue object, is null");
			}
			
			i = 0;
			
			for (net.forestany.forestj.lib.test.nettest.Data.SimpleClass o_simpleClassObject : a_data) {
				if (i == 0) {
					assertEquals(o_simpleClassObject.ValueA, "Record #1 Value A", "dequeued object #" + (i + 1) + " ValueA does not match with origin");
					assertEquals(o_simpleClassObject.ValueB, "Record #1 Value B", "dequeued object #" + (i + 1) + " ValueB does not match with origin");
					assertEquals(o_simpleClassObject.ValueC, "Record #1 Value C", "dequeued object #" + (i + 1) + " ValueC does not match with origin");
					assertEquals(o_simpleClassObject.ValueD, new java.util.ArrayList<Integer>(), "dequeued object #" + (i + 1) + " ValueD does not match with origin");
				} else if (i == 1) {
					assertEquals(o_simpleClassObject.ValueA, "Record #2 Value A", "dequeued object #" + (i + 1) + " ValueA does not match with origin");
					assertEquals(o_simpleClassObject.ValueB, "Record #2 Value B", "dequeued object #" + (i + 1) + " ValueB does not match with origin");
					assertEquals(o_simpleClassObject.ValueC, "", "dequeued object #" + (i + 1) + " ValueC does not match with origin");
					assertEquals(o_simpleClassObject.ValueD, java.util.Arrays.asList(1, 2, -3, -4), "dequeued object #" + (i + 1) + " ValueD does not match with origin");
				} else if (i == 2) {
					assertEquals(o_simpleClassObject.ValueA, "Record #3 Value A", "dequeued object #" + (i + 1) + " ValueA does not match with origin");
					assertEquals(o_simpleClassObject.ValueB, "Record #3 Value B", "dequeued object #" + (i + 1) + " ValueB does not match with origin");
					assertEquals(o_simpleClassObject.ValueC, "Record #3 Value C", "dequeued object #" + (i + 1) + " ValueC does not match with origin");
					assertEquals(o_simpleClassObject.ValueD, java.util.Arrays.asList(9, 8, -7, -6), "dequeued object #" + (i + 1) + " ValueD does not match with origin");
				} else if (i == 3) {
					assertEquals(o_simpleClassObject.ValueA, "Record #4 Value A", "dequeued object #" + (i + 1) + " ValueA does not match with origin");
					assertEquals(o_simpleClassObject.ValueB, "Record #4 Value B", "dequeued object #" + (i + 1) + " ValueB does not match with origin");
					assertEquals(o_simpleClassObject.ValueC, null, "dequeued object #" + (i + 1) + " ValueC does not match with origin");
					assertEquals(o_simpleClassObject.ValueD, java.util.Arrays.asList(16, 32, null, 128), "dequeued object #" + (i + 1) + " ValueD does not match with origin");
				}
				
	    		i++;
	    	}
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
