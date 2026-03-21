package net.forestany.forestj.lib.test.io;

import net.forestany.forestj.lib.io.StandardTransposeMethods;

public class FixedLengthRecordDataWithSubtypesOptional extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordDataWithSubtypesOptional> {

	/* Fields */
	
	public String FieldText = null;
    public java.util.List<FixedLengthRecordSubtypeOptionalOne> FieldListOptionalOne = null;
    public java.util.List<FixedLengthRecordSubtypeOptionalTwo> FieldListOptionalTwo = null;
    
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordDataWithSubtypesOptional() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordDataWithSubtypesOptional.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("400"));
		this.Structure.put(i++, new StructureElement("Text", 6, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
		this.Structure.put(i++, new StructureElement("ListOptionalOne", 10, FixedLengthRecordSubtypeOptionalOne.class, "(\\d{2})(.{10})"));
		this.Structure.put(i++, new StructureElement("ListOptionalTwo", 8, FixedLengthRecordSubtypeOptionalTwo.class, "Zusatz(\\d{3})"));
		this.Structure.put(i++, new StructureElement("Ende"));
	}
}
