package net.forestany.forestj.lib.test.io;

import net.forestany.forestj.lib.io.StandardTransposeMethods;
import net.forestany.forestj.lib.io.StandardTransposeMethods.*;

public class FixedLengthRecordDataWithSubtypesOptionalNestedStructure extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordDataWithSubtypesOptionalNestedStructure> {

	/* Fields */
	
	public String FieldText = null;
    public java.util.List<FixedLengthRecordDataWithNestedStructure> FieldListRecordNestedStructure = null;
    public java.time.LocalDate FieldDate = null;
    
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordDataWithSubtypesOptionalNestedStructure() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordDataWithSubtypesOptionalNestedStructure.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("500"));
		this.Structure.put(i++, new StructureElement("Text", 6, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
		this.Structure.put(i++, new StructureElement("ListRecordNestedStructure", 10, FixedLengthRecordDataWithNestedStructure.class, "28|14|20___^25(.{6})(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})Z$|^50(\\d{4})(\\d{2}):(\\d{2}):(\\d{2})$|^75(\\d{5})(.{13})|^99(\\d{4})(\\d{2}):(\\d{2}):(\\d{2})$"));
        this.Structure.put(i++, new StructureElement("Date", 10, LocalDate::TransposeLocalDate_yyyymmdd_ISO, LocalDate::TransposeLocalDate_yyyymmdd_ISO));
		this.Structure.put(i++, new StructureElement("     "));
        this.Structure.put(i++, new StructureElement("EOL"));
	}
}
