package net.forestany.forestj.lib.test.io;

import net.forestany.forestj.lib.io.StandardTransposeMethods;
import net.forestany.forestj.lib.io.StandardTransposeMethods.*;

public class FixedLengthRecordNestedSubtypeThree extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordNestedSubtypeThree> {

	/* Fields */
	
	public int FieldInt = 0;
	public String FieldShortText = null;
	
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordNestedSubtypeThree() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordNestedSubtypeThree.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("75"));
		this.Structure.put(i++, new StructureElement("Int", 5, Numbers::TransposeInteger, Numbers::TransposeInteger));
		this.Structure.put(i++, new StructureElement("ShortText", 13, StandardTransposeMethods::TransposeString, StandardTransposeMethods::TransposeString));
	}
}
