package de.forestj.lib.test.io;

import de.forestj.lib.io.StandardTransposeMethods;
import de.forestj.lib.io.StandardTransposeMethods.*;

public class FixedLengthRecordAnotherData extends de.forestj.lib.io.FixedLengthRecord<FixedLengthRecordAnotherData> {

	/* Fields */
	
	public String FieldStringId = null;
	public Float FieldFloatCol = 0.f;
	public Double FieldDoubleCol = 0.d;
	public java.math.BigDecimal FieldDecimal = null;
	public int FieldInt = 0;
	
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordAnotherData() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordAnotherData.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("200"));
		this.Structure.put(i++, new StructureElement("StringId", 6, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
		this.Structure.put(i++, new StructureElement("FloatCol", 11, FloatingPointNumbers::TransposeFloat, FloatingPointNumbers::TransposeFloat, 9, 2, null, null));
		this.Structure.put(i++, new StructureElement("DoubleCol", 19, FloatingPointNumbers::TransposeDouble, FloatingPointNumbers::TransposeDoubleWithSign, 13, 12, 6, null, null)); /* we must set position decimal separator, because of sign */
		this.Structure.put(i++, new StructureElement("Decimal", 23, FloatingPointNumbers::TransposeBigDecimal, FloatingPointNumbers::TransposeBigDecimal, 9, 14, null, null));
		this.Structure.put(i++, new StructureElement("Int", 10, Numbers::TransposeInteger, Numbers::TransposeInteger));
		
		this.Unique.add("StringId");
		
		this.OrderBy.put("StringId", true);
	}
}
