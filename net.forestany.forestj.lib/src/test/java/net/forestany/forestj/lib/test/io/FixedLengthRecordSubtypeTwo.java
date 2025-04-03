package net.forestany.forestj.lib.test.io;

import net.forestany.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers;

public class FixedLengthRecordSubtypeTwo extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordSubtypeTwo> {

	/* Fields */
	
	public Double FieldDoubleValue = 0.d;
	
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordSubtypeTwo() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordSubtypeTwo.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("DoubleValue", 12, FloatingPointNumbers::TransposeDouble, FloatingPointNumbers::TransposeDoubleWithSign, 9, 8, 3, null, null)); /* we must set position decimal separator, because of sign */
	}
}
