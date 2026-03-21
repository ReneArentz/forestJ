package net.forestany.forestj.lib.test.io;

import net.forestany.forestj.lib.io.StandardTransposeMethods;
import net.forestany.forestj.lib.io.StandardTransposeMethods.*;

public class FixedLengthRecordSubtypeOptionalOne extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordSubtypeOptionalOne> {

	/* Fields */
	
	public int FieldTwoDigitId = 0;
	public String FieldShortText = null;
	
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordSubtypeOptionalOne() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordSubtypeOptionalOne.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("TwoDigitId", 2, Numbers::TransposeInteger, Numbers::TransposeInteger));
		this.Structure.put(i++, new StructureElement("ShortText", 10, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
	}
}
