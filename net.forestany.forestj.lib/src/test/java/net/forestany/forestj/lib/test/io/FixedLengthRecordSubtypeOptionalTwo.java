package net.forestany.forestj.lib.test.io;

import net.forestany.forestj.lib.io.StandardTransposeMethods.*;

public class FixedLengthRecordSubtypeOptionalTwo extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordSubtypeOptionalTwo> {

	/* Fields */
	
	public int FieldThreeDigitId = 0;
	
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordSubtypeOptionalTwo() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordSubtypeOptionalTwo.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();

        this.Structure.put(i++, new StructureElement("Zusatz"));
		this.Structure.put(i++, new StructureElement("ThreeDigitId", 3, Numbers::TransposeInteger, Numbers::TransposeInteger));

        this.Unique.add("ThreeDigitId");
	}
}
