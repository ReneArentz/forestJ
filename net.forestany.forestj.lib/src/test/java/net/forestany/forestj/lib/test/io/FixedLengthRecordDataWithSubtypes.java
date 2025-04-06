package net.forestany.forestj.lib.test.io;

import net.forestany.forestj.lib.io.StandardTransposeMethods;
import net.forestany.forestj.lib.io.StandardTransposeMethods.UtilDate;

public class FixedLengthRecordDataWithSubtypes extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordDataWithSubtypes> {

	/* Fields */
	
	public java.util.Date FieldDate = null;
    public java.util.List<FixedLengthRecordSubtypeOne> FieldListOnes = null;
    public java.util.List<FixedLengthRecordSubtypeTwo> FieldListTwos = null;
    public String FieldLastNotice = null;
	
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordDataWithSubtypes() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordDataWithSubtypes.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("300"));
		this.Structure.put(i++, new StructureElement("Date", 8, UtilDate::TransposeDate_yyyymmdd, UtilDate::TransposeDate_yyyymmdd));
		this.Structure.put(i++, new StructureElement("ListOnes", 11, FixedLengthRecordSubtypeOne.class));
		this.Structure.put(i++, new StructureElement("ListTwos", 4, FixedLengthRecordSubtypeTwo.class));
		this.Structure.put(i++, new StructureElement("LastNotice", 15, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
		
		this.Unique.add("Date");
	}
}
