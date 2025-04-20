package net.forestany.forestj.lib.test.sqltest;

/**
 * record class for a simple test
 */
public class LanguageRecord extends net.forestany.forestj.lib.sql.Record<LanguageRecord> {
	
	/* Fields */
	
	/**
	 * id
	 */
	public int ColumnId = 0;
	/**
	 * uuid
	 */
	public String ColumnUUID = null;
	/**
	 * code
	 */
	public String ColumnCode = null;
	/**
	 * language
	 */
	public String ColumnLanguage = null;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * constructor
	 * 
	 * @throws NullPointerException			record image class, primary, unique or order by have no values
	 * @throws IllegalArgumentException		table name is invalid(empty)
	 * @throws NoSuchFieldException			given field values in primary, unique or order by do not exists is current class
	 */
	public LanguageRecord() throws NullPointerException, IllegalArgumentException, NoSuchFieldException {
		super();
	}
	
	protected void init() {
		this.RecordImageClass = LanguageRecord.class;
		this.Table = "sys_forestj_language";
		this.Primary.add("Id");
		this.Unique.add("UUID");
		this.Unique.add("Code");
		this.OrderBy.put("Id", true);
		this.Interval = 50;
	}
}
