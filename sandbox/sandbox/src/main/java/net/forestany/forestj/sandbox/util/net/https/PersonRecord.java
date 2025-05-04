package net.forestany.forestj.sandbox.util.net.https;

public class PersonRecord extends net.forestany.forestj.lib.sql.Record<PersonRecord> {
	
	/* Fields */
	
	public int ColumnId = 0;
	public int ColumnPersonalIdentificationNumber;
	public String ColumnName;
	public int ColumnAge;
	public String ColumnCity;
	public String ColumnCountry;
	
	/* Properties */
	
	/* Methods */
	
	public PersonRecord() throws NullPointerException, IllegalArgumentException, NoSuchFieldException {
		super();
	}
	
	protected void init() {
		this.RecordImageClass = PersonRecord.class;
		this.Table = "sys_forestj_person";
		this.Primary.add("Id");
		this.Unique.add("PersonalIdentificationNumber");
		this.OrderBy.put("PersonalIdentificationNumber", true);
		this.Interval = 50;
	}
}
