package net.forestany.forestj.lib.test.nettest.sock.com;

/**
 * test class for a shared memory small message object
 */
public class SharedMemorySmallMessageObject extends net.forestany.forestj.lib.net.sock.com.SharedMemory<SharedMemorySmallMessageObject> {

	/* Fields */
	
	/**
	 * bool
	 */
	public boolean Bool;
	/**
	 * char
	 */
	public char Char;
	/**
	 * integer array
	 */
	public int[] IntegerArray;
	/**
	 * long list
	 */
	public java.util.List<Long> LongList;
	/**
	 * string
	 */
	public String String;
	/**
	 * local time
	 */
	public java.time.LocalTime LocalTime;
	/**
	 * local date array
	 */
	public java.time.LocalDate[] LocalDateArray;
	/**
	 * local date time list
	 */
	public java.util.List<java.time.LocalDateTime> LocalDateTimeList;
	/**
	 * decimal array
	 */
	public java.math.BigDecimal[] DecimalArray; 
	
	/* Properties */
	
	/**
	 * get bool
	 * 
	 * @return boolean
	 */
	public boolean getBool() {
		return this.Bool;
	}
	
	/**
	 * set bool
	 * 
	 * @param p_b_value boolean
	 */
	public void setBool(boolean p_b_value) {
		this.Bool = p_b_value;
	}
	
	
	/**
	 * get char
	 *  
	 * @return char
	 */
	public char getChar() {
		return this.Char;
	}
	
	/**
	 * set char
	 * 
	 * @param p_c_value char
	 */
	public void setChar(char p_c_value) {
		this.Char = p_c_value;
	}
	
	
	/**
	 * get integer array
	 * 
	 * @return int[]
	 */
	public int[] getIntegerArray() {
		return this.IntegerArray;
	}
	
	/**
	 * set integer array
	 * 
	 * @param p_a_value int[]
	 */
	public void setIntegerArray(int[] p_a_value) {
		this.IntegerArray = p_a_value;
	}
	
	
	/**
	 * get long list
	 * 
	 * @return java.util.List&lt;Long&gt;
	 */
	public java.util.List<Long> getLongList() {
		return this.LongList;
	}
	
	/**
	 * set long list
	 * 
	 * @param p_a_value java.util.List&lt;Long&gt;
	 */
	public void setLongList(java.util.List<Long> p_a_value) {
		this.LongList = p_a_value;
	}
	
	
	/**
	 * get string
	 * 
	 * @return String
	 */
	public String getString() {
		return this.String;
	}
	
	/**
	 * set string
	 * 
	 * @param p_s_value String
	 */
	public void setString(String p_s_value) {
		this.String = p_s_value;
	}
	
	
	/**
	 * get local time
	 * 
	 * @return java.time.LocalTime
	 */
	public java.time.LocalTime getLocalTime() {
		return this.LocalTime;
	}
	
	/**
	 * set local time
	 * 
	 * @param p_o_value java.time.LocalTime
	 */
	public void setLocalTime(java.time.LocalTime p_o_value) {
		this.LocalTime = p_o_value;
	}
	
	
	/**
	 * get local date array
	 * 
	 * @return java.time.LocalDate[]
	 */
	public java.time.LocalDate[] getLocalDateArray() {
		return this.LocalDateArray;
	}
	
	/**
	 * set local date array
	 * 
	 * @param p_a_value java.time.LocalDate[]
	 */
	public void setLocalDateArray(java.time.LocalDate[] p_a_value) {
		this.LocalDateArray = p_a_value;
	}
	
	
	/**
	 * get local date time list
	 * 
	 * @return java.util.List&lt;java.time.LocalDateTime&gt;
	 */
	public java.util.List<java.time.LocalDateTime> getLocalDateTimeList() {
		return this.LocalDateTimeList;
	}
	
	/**
	 * set local date time list
	 * 
	 * @param p_a_value java.util.List&lt;java.time.LocalDateTime&gt;
	 */
	public void setLocalDateTimeList(java.util.List<java.time.LocalDateTime> p_a_value) {
		this.LocalDateTimeList = p_a_value;
	}
	
	
	/**
	 * get decimal array
	 * 
	 * @return java.math.BigDecimal[]
	 */
	public java.math.BigDecimal[] getDecimalArray() {
		return this.DecimalArray;
	}
	
	/**
	 * set decimal array
	 * 
	 * @param p_a_value java.math.BigDecimal[]
	 */
	public void setDecimalArray(java.math.BigDecimal[] p_a_value) {
		this.DecimalArray = p_a_value;
	}
		
	/* Methods */
	
	/**
	 * constructor
	 * 
	 * @throws NullPointerException mirror class not set by Init method
	 */
	public SharedMemorySmallMessageObject() throws NullPointerException {
		super();
	}
	
	/**
	 * shared memory init method
	 */
	protected void init() {
		this.o_mirrorClass = SharedMemorySmallMessageObject.class;
	}
	
	/**
	 * initialize all values
	 */
	public void initAll() {
		this.setBool(true);
		this.setChar((char)242);
		this.setIntegerArray(new int[] { 1, 3, 5, 536870954, 42, 0 });
		this.setLongList(java.util.Arrays.asList( 1l, 3l, 5l, 1170936177994235946l, 42l, 0l, null ));
		this.setString("Hello World!");
		this.setLocalTime(java.time.LocalTime.of(6, 2, 3));
		this.setLocalDateArray(new java.time.LocalDate[] { java.time.LocalDate.of(2020, 3, 4), java.time.LocalDate.of(2020, 6, 8), java.time.LocalDate.of(2020, 12, 16), null });
		this.setLocalDateTimeList(java.util.Arrays.asList( java.time.LocalDateTime.of(2020, 3, 4, 6, 2, 3), java.time.LocalDateTime.of(2020, 6, 8, 9, 24, 16), java.time.LocalDateTime.of(2020, 12, 16, 12, 48, 53), null ));
		this.setDecimalArray(new java.math.BigDecimal[] {
			new java.math.BigDecimal("+578875020153.73804901109397"),
			new java.math.BigDecimal("-36.151686185423327"),
			new java.math.BigDecimal("+71740124.12171120119"),
			new java.math.BigDecimal("-2043204985254.1196"),
			new java.math.BigDecimal(0d),
			new java.math.BigDecimal("+601.9924")
		});
	}

	/**
	 * empty all values
	 */
	public void emptyAll() {
		this.setBool(false);
		this.setChar((char)0);
		this.setIntegerArray(null);
		this.setLongList(new java.util.ArrayList<Long>());
		this.setString(null);
		this.setLocalTime(null);
		this.setLocalDateArray(null);
		this.setLocalDateTimeList(new java.util.ArrayList<java.time.LocalDateTime>());
		this.setDecimalArray(null);
	}

	@Override public String toString() {
		String s_foo = "";
		
		s_foo += this.Bool;
		
		s_foo += " | " + this.Char;
		
		if ( (this.IntegerArray != null) && (this.IntegerArray.length > 0) ) {
			s_foo += " | [ ";
			
			for (int i_foo : this.IntegerArray) {
				s_foo += i_foo + ", ";
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 2);
			
			s_foo += " ]";
		}
		
		if ( (this.LongList != null) && (this.LongList.size() > 0) ) {
			s_foo += " | [ ";
			
			for (int i = 0; i < this.LongList.size(); i++) {
				s_foo += this.LongList.get(i) + ", ";
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 2);
			
			s_foo += " ]";
		}
		
		if ( (this.String != null) && (this.String.length() == 0) ) {
			s_foo += " | null";
		} else {
			s_foo += " | " + this.String;
		}
		
		if (this.LocalTime != null) {
			s_foo += " | " + this.LocalTime;
		}
		
		if ( (this.LocalDateArray != null) && (this.LocalDateArray.length > 0) ) {
			s_foo += " | [ ";
			
			for (java.time.LocalDate o_foo : this.LocalDateArray) {
				s_foo += o_foo + ", ";
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 2);
			
			s_foo += " ]";
		}
		
		if ( (this.LocalDateTimeList != null) && (this.LocalDateTimeList.size() > 0) ) {
			s_foo += " | [ ";
			
			for (int i = 0; i < this.LocalDateTimeList.size(); i++) {
				s_foo += this.LocalDateTimeList.get(i) + ", ";
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 2);
			
			s_foo += " ]";
		}
		
		if ( (this.DecimalArray != null) && (this.DecimalArray.length > 0) ) {
			s_foo += " | [ ";
			
			for (java.math.BigDecimal o_foo : this.DecimalArray) {
				s_foo += o_foo + ", ";
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 2);
			
			s_foo += " ]";
		}
		
		return s_foo;
	}
}
