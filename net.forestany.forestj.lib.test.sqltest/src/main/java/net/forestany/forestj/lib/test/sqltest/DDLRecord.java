package net.forestany.forestj.lib.test.sqltest;

/**
 * record class to test data definition language queries
 */
public class DDLRecord extends net.forestany.forestj.lib.sql.Record<DDLRecord> {
	
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
	 * short text
	 */
	public String ColumnShortText = null;
	/**
	 * text
	 */
	public String ColumnText = null;
	/**
	 * small int
	 */
	public short ColumnSmallInt = 0;
	/**
	 * int
	 */
	public int ColumnInt = 0;
	/**
	 * big int
	 */
	public long ColumnBigInt = 0;
	/**
	 * timestamp
	 */
	public java.util.Date ColumnTimestamp = null;
	/**
	 * date
	 */
	public java.util.Date ColumnDate = null;
	/**
	 * time
	 */
	public java.util.Date ColumnTime = null;
	/**
	 * LocalDateTime
	 */
	public java.time.LocalDateTime ColumnLocalDateTime = null;
	/**
	 * LocalDate
	 */
	public java.time.LocalDate ColumnLocalDate = null;
	/**
	 * LocalTime
	 */
	public java.time.LocalTime ColumnLocalTime = null;
	/**
	 * double
	 */
	public Double ColumnDoubleCol = 0.d;
	/**
	 * decimal
	 */
	public java.math.BigDecimal ColumnDecimal = null;
	/**
	 * bool
	 */
	public boolean ColumnBool = false;
	/**
	 * text 2
	 */
	public String ColumnText2 = null;
	/**
	 * short text 2
	 */
	public String ColumnShortText2 = null;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * constructor
	 * 
	 * @throws NullPointerException			record image class, primary, unique or order by have no values
	 * @throws IllegalArgumentException		table name is invalid(empty)
	 * @throws NoSuchFieldException			given field values in primary, unique or order by do not exists is current class
	 */
	public DDLRecord() throws NullPointerException, IllegalArgumentException, NoSuchFieldException {
		super();
	}
	
	protected void init() {
		this.RecordImageClass = DDLRecord.class;
		this.Table = "sys_forestj_testddl2";
		this.Primary.add("Id");
		this.Unique.add("UUID");
		this.Unique.add("ShortText");
		this.OrderBy.put("SmallInt", true);
		this.Interval = 50;
	}
}
