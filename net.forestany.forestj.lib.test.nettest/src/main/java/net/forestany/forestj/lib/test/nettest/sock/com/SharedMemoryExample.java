package net.forestany.forestj.lib.test.nettest.sock.com;

/**
 * shared memory example class
 */
@SuppressWarnings("unused")
public class SharedMemoryExample extends net.forestany.forestj.lib.net.sock.com.SharedMemory<SharedMemoryExample> {
	
	/* Fields */
	
	private int Id = 0;
	private String UUID = null;
	private String ShortText = null;
	private String Text = null;
	private short SmallInt = 0;
	private int Int = 0;
	private long BigInt = 0;
	private java.util.Date Timestamp = null;
	private java.util.Date Date = null;
	private java.util.Date Time = null;
	private java.time.LocalDateTime LocalDateTime = null;
	private java.time.LocalDate LocalDate = null;
	private java.time.LocalTime LocalTime = null;
	private Double DoubleCol = 0.d;
	private java.math.BigDecimal Decimal = null;
	private boolean Bool = false;
	private String Text2 = null;
	private String ShortText2 = null;
	private float FloatValue = 0.f;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * constructor
	 * 
	 * @throws NullPointerException mirror class not set by Init method
	 */
	public SharedMemoryExample() throws NullPointerException {
		super();
	}
	
	/**
	 * shared memory init method
	 */
	protected void init() {
		this.o_mirrorClass = SharedMemoryExample.class;
	}
}
