package net.forestany.forestj.lib.test.io;

import net.forestany.forestj.lib.io.StandardTransposeMethods.*;

public class FixedLengthRecordNestedSubtypeTwo extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordNestedSubtypeTwo> {

	/* Fields */
	
	public int FieldInt = 0;
	public java.util.Date FieldTime = null;
	
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordNestedSubtypeTwo() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordNestedSubtypeTwo.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("50"));
		this.Structure.put(i++, new StructureElement("Int", 4, Numbers::TransposeInteger, Numbers::TransposeInteger));
		this.Structure.put(i++, new StructureElement("Time", 8, UtilDate::TransposeDate_hhiiss_Colon, UtilDate::TransposeDate_hhiiss_Colon));
	}
}
