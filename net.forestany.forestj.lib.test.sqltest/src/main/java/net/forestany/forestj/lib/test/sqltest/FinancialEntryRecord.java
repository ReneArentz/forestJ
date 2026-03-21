package net.forestany.forestj.lib.test.sqltest;

/**
 * record class to test many inserts and auto-increment
 */
public class FinancialEntryRecord extends net.forestany.forestj.lib.sql.Record<FinancialEntryRecord> {
	
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
	 * from
	 */
	public int ColumnFrom = 0;
	/**
	 * to
	 */
	public int ColumnTo = 0;
    /**
     * amount
     */
    public java.math.BigDecimal ColumnAmount = null;
    /**
     * created
     */
    public java.time.LocalDateTime ColumnCreated = null;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * constructor
	 * 
	 * @throws NullPointerException			record image class, primary, unique or order by have no values
	 * @throws IllegalArgumentException		table name is invalid(empty)
	 * @throws NoSuchFieldException			given field values in primary, unique or order by do not exists is current class
	 */
	public FinancialEntryRecord() throws NullPointerException, IllegalArgumentException, NoSuchFieldException {
		super();
	}
	
	protected void init() {
		this.RecordImageClass = FinancialEntryRecord.class;
		this.Table = "sys_forestj_financial_entry";
		this.Primary.add("Id");
		this.Unique.add("UUID");
		this.OrderBy.put("Id", true);
		this.Interval = 50;
	}
}
