package net.forestany.forestj.lib.test.io;

import net.forestany.forestj.lib.io.StandardTransposeMethods.*;

public class FixedLengthRecordGroupFooterData extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordGroupFooterData> {

	/* Fields */
	
	public int FieldAmountRecords = 0;
	public int FieldSumInt = 0;
		
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordGroupFooterData() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordGroupFooterData.class;
		
		int i = 1;
		
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("+F+"));
		this.Structure.put(i++, new StructureElement(" ++++++ Amount Records: "));
		this.Structure.put(i++, new StructureElement("AmountRecords", 6, Numbers::TransposeInteger, Numbers::TransposeInteger));
		this.Structure.put(i++, new StructureElement(" ++++++ Sum Int Divide By 2: "));
		this.Structure.put(i++, new StructureElement("SumInt", 12, Numbers::TransposeInteger, Numbers::TransposeInteger));
		this.Structure.put(i++, new StructureElement(" ++++++"));
	}
}
