package net.forestany.forestj.lib.test.io;

import net.forestany.forestj.lib.io.StandardTransposeMethods;
import net.forestany.forestj.lib.io.StandardTransposeMethods.*;

public class FixedLengthRecordNestedSubtypeOne extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordNestedSubtypeOne> {

	/* Fields */
	
	public String FieldString = null;
	public java.util.Date FieldTimestamp = null;
	
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordNestedSubtypeOne() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordNestedSubtypeOne.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("25"));
		this.Structure.put(i++, new StructureElement("String", 6, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
		this.Structure.put(i++, new StructureElement("Timestamp", 20, UtilDate::TransposeDate_ISO8601, UtilDate::TransposeDate_ISO8601));
	}
}
