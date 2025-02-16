package de.forestj.lib.test.io;

import de.forestj.lib.io.StandardTransposeMethods.*;

public class FixedLengthRecordGroupHeaderData extends de.forestj.lib.io.FixedLengthRecord<FixedLengthRecordGroupHeaderData> {

	/* Fields */
	
	public int FieldCustomerNumber = 0;
	public java.time.LocalDate FieldDate = null;
	public Double FieldDoubleWithSeparator = 0.d;
		
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordGroupHeaderData() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordGroupHeaderData.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("+H+"));
		this.Structure.put(i++, new StructureElement(" ++++++ Customer Number: "));
		this.Structure.put(i++, new StructureElement("CustomerNumber", 5, Numbers::TransposeInteger, Numbers::TransposeInteger));
		this.Structure.put(i++, new StructureElement(" ++++++ Date: "));
		this.Structure.put(i++, new StructureElement("Date", 8, LocalDate::TransposeLocalDate_yyyymmdd, LocalDate::TransposeLocalDate_yyyymmdd));
		this.Structure.put(i++, new StructureElement(" ++++++ DoubleWithSeparator: "));
		this.Structure.put(i++, new StructureElement("DoubleWithSeparator", 15, FloatingPointNumbers::TransposeDouble, FloatingPointNumbers::TransposeDouble, 0, 8, 6, ".", null)); /* we must set position decimal separator to '0', because decimal separator is part of string */
		this.Structure.put(i++, new StructureElement(" ++++++"));
				
		this.Unique.add("CustomerNumber");
		
		this.OrderBy.put("CustomerNumber", true);
	}
}
