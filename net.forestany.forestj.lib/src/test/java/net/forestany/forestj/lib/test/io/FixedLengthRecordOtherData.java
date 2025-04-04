package net.forestany.forestj.lib.test.io;

import net.forestany.forestj.lib.io.StandardTransposeMethods;
import net.forestany.forestj.lib.io.StandardTransposeMethods.*;

public class FixedLengthRecordOtherData extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordOtherData> {

	/* Fields */
	
	public String FieldStringId = null;
	public int FieldInt = 0;
	public java.util.Date FieldTimestamp = null;
	public java.util.Date FieldDate = null;
	public java.util.Date FieldTime = null;
	public String FieldShortText = null;
	
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordOtherData() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordOtherData.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("100"));
		this.Structure.put(i++, new StructureElement("StringId", 6, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
		this.Structure.put(i++, new StructureElement("Int", 8, Numbers::TransposeInteger, Numbers::TransposeInteger));
		this.Structure.put(i++, new StructureElement("Timestamp", 14, UtilDate::TransposeDate_yyyymmddhhiiss, UtilDate::TransposeDate_yyyymmddhhiiss));
		this.Structure.put(i++, new StructureElement("Date", 8, UtilDate::TransposeDate_yyyymmdd, UtilDate::TransposeDate_yyyymmdd));
		this.Structure.put(i++, new StructureElement("Time", 6, UtilDate::TransposeDate_hhiiss, UtilDate::TransposeDate_hhiiss));
		this.Structure.put(i++, new StructureElement("ShortText", 13, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
		
		this.Unique.add("StringId");
		
		this.OrderBy.put("StringId", true);
	}
}
