package net.forestany.forestj.lib.test.io;

import net.forestany.forestj.lib.io.StandardTransposeMethods;
import net.forestany.forestj.lib.io.StandardTransposeMethods.Numbers;

public class FixedLengthRecordSubtypeOne extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordSubtypeOne> {

	/* Fields */
	
	public int FieldThreeDigitId = 0;
	public String FieldShortText = null;
	
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordSubtypeOne() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordSubtypeOne.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("ThreeDigitId", 3, Numbers::TransposeInteger, Numbers::TransposeInteger));
		this.Structure.put(i++, new StructureElement("ShortText", 10, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
		
		this.Unique.add("ThreeDigitId");
		this.AllowEmptyUniqueFields = true;
	}
}
