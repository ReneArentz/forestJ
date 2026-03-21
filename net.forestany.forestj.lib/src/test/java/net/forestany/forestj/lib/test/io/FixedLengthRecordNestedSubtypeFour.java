package net.forestany.forestj.lib.test.io;

import net.forestany.forestj.lib.io.StandardTransposeMethods.*;

public class FixedLengthRecordNestedSubtypeFour extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordNestedSubtypeFour> {

	/* Fields */
	
	public int FieldInt = 0;
	public java.util.Date FieldTime = null;
	
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordNestedSubtypeFour() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordNestedSubtypeFour.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("99"));
		this.Structure.put(i++, new StructureElement("Int", 4, Numbers::TransposeInteger, Numbers::TransposeInteger));
		this.Structure.put(i++, new StructureElement("Time", 8, UtilDate::TransposeDate_hhiiss_Colon, UtilDate::TransposeDate_hhiiss_Colon));
	}
}
