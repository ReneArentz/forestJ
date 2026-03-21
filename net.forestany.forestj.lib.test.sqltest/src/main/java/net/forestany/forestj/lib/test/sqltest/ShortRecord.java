package net.forestany.forestj.lib.test.sqltest;

/**
 * record class to test many inserts with a very short record
 */
public class ShortRecord extends net.forestany.forestj.lib.sql.Record<ShortRecord> {
	
	/* Fields */
	
	/**
	 * id
	 */
	public int ColumnKey = 0;
	/**
	 * text
	 */
	public String ColumnText = null;
	/**
	 * number
	 */
	public int ColumnNumber = 0;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * constructor
	 * 
	 * @throws NullPointerException			record image class, primary, unique or order by have no values
	 * @throws IllegalArgumentException		table name is invalid(empty)
	 * @throws NoSuchFieldException			given field values in primary, unique or order by do not exists is current class
	 */
	public ShortRecord() throws NullPointerException, IllegalArgumentException, NoSuchFieldException {
		super();
	}
	
	protected void init() {
		this.RecordImageClass = ShortRecord.class;
		this.Table = "sys_forestj_short_table";
		this.Primary.add("Key");
		this.OrderBy.put("Key", true);
		this.Interval = 50;
	}
}
