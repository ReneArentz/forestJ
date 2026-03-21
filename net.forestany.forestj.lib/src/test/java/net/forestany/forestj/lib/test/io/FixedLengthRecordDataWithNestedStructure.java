package net.forestany.forestj.lib.test.io;

public class FixedLengthRecordDataWithNestedStructure extends net.forestany.forestj.lib.io.FixedLengthRecord<FixedLengthRecordDataWithNestedStructure> {

	/* Fields */
	
	public java.util.List<FixedLengthRecordNestedSubtypeOne> FieldNestedSubtypeOne = null;
    public java.util.List<FixedLengthRecordNestedSubtypeTwo> FieldNestedSubtypeTwo = null;
    public java.util.List<FixedLengthRecordNestedSubtypeThree> FieldNestedSubtypeThree = null;
    public java.util.List<FixedLengthRecordNestedSubtypeFour> FieldNestedSubtypeFour = null;
    
	/* Properties */
	
	/* Methods */
	
	public FixedLengthRecordDataWithNestedStructure() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		super();
	}
	
	protected void init() {
		this.FLRImageClass = FixedLengthRecordDataWithNestedStructure.class;
		
		int i = 1;
        
		this.Structure = new java.util.LinkedHashMap<>();
		this.Structure.put(i++, new StructureElement("NestedSubtypeOne", 1, FixedLengthRecordNestedSubtypeOne.class, "25(.{6})(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})Z"));
        this.Structure.put(i++, new StructureElement("NestedSubtypeTwo", 1, FixedLengthRecordNestedSubtypeTwo.class, "50(\\d{4})(\\d{2}):(\\d{2}):(\\d{2})"));
        this.Structure.put(i++, new StructureElement("NestedSubtypeThree", 1, FixedLengthRecordNestedSubtypeThree.class, "75(\\d{5})(.{13})"));
        this.Structure.put(i++, new StructureElement("NestedSubtypeFour", 1, FixedLengthRecordNestedSubtypeFour.class, "99(\\d{4})(\\d{2}):(\\d{2}):(\\d{2})"));
	}
}
