package de.forestj.lib.test.io;

import de.forestj.lib.io.StandardTransposeMethods;
import de.forestj.lib.io.StandardTransposeMethods.*;

public class FixedLengthRecordData extends de.forestj.lib.io.FixedLengthRecord<FixedLengthRecordData> {

	/* Fields */
	
	public int FieldId = 0;
	public String FieldUUID = null;
	public String FieldShortText = null;
	public String FieldText = null;
	public short FieldSmallInt = 0;
	public int FieldInt = 0;
	public long FieldBigInt = 0;
	public java.util.Date FieldTimestamp = null;
	public java.util.Date FieldDate = null;
	public java.util.Date FieldTime = null;
	public java.time.LocalTime FieldLocalTime = null;
	public java.time.LocalDate FieldLocalDate = null;
	public java.time.LocalDateTime FieldLocalDateTime = null;
	public Byte FieldByteCol = 0x00;
	public Float FieldFloatCol = 0.f;
	public Double FieldDoubleCol = 0.d;
	public java.math.BigDecimal FieldDecimal = null;
	public boolean FieldBool = false;
	public String FieldText2 = null;
	public String FieldShortText2 = null;
	
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordData() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordData.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("000"));
		this.Structure.put(i++, new StructureElement("Id", 3, Numbers::TransposeInteger, Numbers::TransposeInteger));
		this.Structure.put(i++, new StructureElement("UUID", 36, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
		this.Structure.put(i++, new StructureElement("ShortText", 16, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
		this.Structure.put(i++, new StructureElement("Text", 64, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
		this.Structure.put(i++, new StructureElement("SmallInt", 6, Numbers::TransposeShort, Numbers::TransposeShort));
		this.Structure.put(i++, new StructureElement("Int", 10, Numbers::TransposeInteger, Numbers::TransposeInteger));
		this.Structure.put(i++, new StructureElement("BigInt", 19, Numbers::TransposeLong, Numbers::TransposeLong));
		this.Structure.put(i++, new StructureElement("Timestamp", 14, UtilDate::TransposeDate_yyyymmddhhiiss, UtilDate::TransposeDate_yyyymmddhhiiss));
		this.Structure.put(i++, new StructureElement("Date", 8, UtilDate::TransposeDate_yyyymmdd, UtilDate::TransposeDate_yyyymmdd));
		this.Structure.put(i++, new StructureElement("Time", 6, UtilDate::TransposeDate_hhiiss, UtilDate::TransposeDate_hhiiss));
		this.Structure.put(i++, new StructureElement("LocalDateTime", 20, LocalDateTime::TransposeLocalDateTime_ISO8601, LocalDateTime::TransposeLocalDateTime_ISO8601));
		this.Structure.put(i++, new StructureElement("LocalDate", 10, LocalDate::TransposeLocalDate_yyyymmdd_ISO, LocalDate::TransposeLocalDate_yyyymmdd_ISO));
		this.Structure.put(i++, new StructureElement("LocalTime", 8, LocalTime::TransposeLocalTime_hhiiss_Colon, LocalTime::TransposeLocalTime_hhiiss_Colon));
		this.Structure.put(i++, new StructureElement("ByteCol", 3, Numbers::TransposeByte, Numbers::TransposeByte));
		this.Structure.put(i++, new StructureElement("FloatCol", 11, FloatingPointNumbers::TransposeFloat, FloatingPointNumbers::TransposeFloat, 9, 2, null, null));
		this.Structure.put(i++, new StructureElement("DoubleCol", 19, FloatingPointNumbers::TransposeDouble, FloatingPointNumbers::TransposeDoubleWithSign, 13, 12, 6, null, null)); /* we must set position decimal separator, because of sign */
		this.Structure.put(i++, new StructureElement("Decimal", 23, FloatingPointNumbers::TransposeBigDecimal, FloatingPointNumbers::TransposeBigDecimal, 9, 14, null, null));
		this.Structure.put(i++, new StructureElement("Bool", 1, StandardTransposeMethods::TransposeBoolean, StandardTransposeMethods::TransposeBoolean));
		this.Structure.put(i++, new StructureElement("Text2", 32, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
		this.Structure.put(i++, new StructureElement("ShortText2", 8, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
		
		this.Unique.add("Id");
		this.Unique.add("UUID");
		this.Unique.add("ShortText");
		
		this.OrderBy.put("SmallInt", true);
	}
}
