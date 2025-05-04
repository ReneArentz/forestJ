package net.forestany.forestj.lib.test.nettest.sock.https;

/**
 * calculator implementation class
 */
public class CalculatorImpl {
	/** * empty constructor */ public CalculatorImpl() { }
	
	/**
	 * SOAP add operation
	 * @return net.forestany.forestj.lib.net.https.soap.WSDL.SOAPOperationInterface
	 */
	public static net.forestany.forestj.lib.net.https.soap.WSDL.SOAPOperationInterface Add() {
		return (Object p_o_inputMessage, net.forestany.forestj.lib.net.https.Seed p_o_seed) -> {
			Calculator.Add o_add = (Calculator.Add)p_o_inputMessage;
			
			Calculator.AddResult o_addResult = new Calculator().new AddResult();
			o_addResult.result = o_add.param1 + o_add.param2;
			
			return o_addResult;
		};
	}
	
	/**
	 * SOAP subtract operation
	 * @return net.forestany.forestj.lib.net.https.soap.WSDL.SOAPOperationInterface
	 */
	public static net.forestany.forestj.lib.net.https.soap.WSDL.SOAPOperationInterface Subtract() {
		return (Object p_o_inputMessage, net.forestany.forestj.lib.net.https.Seed p_o_seed) -> {
			Calculator.Subtract o_sub = (Calculator.Subtract)p_o_inputMessage;
			
			Calculator.SubtractResult o_subResult = new Calculator().new SubtractResult();
			o_subResult.result = o_sub.param1 - o_sub.param2;
			
			return o_subResult;
		};
	}
	
	/**
	 * SOAP multiply operation
	 * @return net.forestany.forestj.lib.net.https.soap.WSDL.SOAPOperationInterface
	 */
	public static net.forestany.forestj.lib.net.https.soap.WSDL.SOAPOperationInterface Multiply() {
		return (Object p_o_inputMessage, net.forestany.forestj.lib.net.https.Seed p_o_seed) -> {
			Calculator.Multiply o_mul = (Calculator.Multiply)p_o_inputMessage;
			
			Calculator.MultiplyResult o_mulResult = new Calculator().new MultiplyResult();
			o_mulResult.result = o_mul.param1 * o_mul.param2;
			
			return o_mulResult;
		};
	}
	
	/**
	 * SOAP divide operation
	 * @return net.forestany.forestj.lib.net.https.soap.WSDL.SOAPOperationInterface
	 */
	public static net.forestany.forestj.lib.net.https.soap.WSDL.SOAPOperationInterface Divide() {
		return (Object p_o_inputMessage, net.forestany.forestj.lib.net.https.Seed p_o_seed) -> {
			Calculator.Divide o_div = (Calculator.Divide)p_o_inputMessage;
			
			Calculator.DivideResult o_divResult = new Calculator().new DivideResult();
			o_divResult.result = o_div.param1 / o_div.param2;
			
			return o_divResult;
		};
	}
}
