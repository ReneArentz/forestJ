package net.forestany.forestj.lib.test.nettest;

/**
 * Collection of test data classes and generate method for testing fake real data
 */
public class Data implements java.io.Serializable {
	/**
	 * empty constructor
	 */
	public Data() {
		
	}
	
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 8627909750864964805L;

	/**
	 * generate data method
	 * 
	 * @return java.util.List&lt;ShipOrder&gt;
	 */
	public static java.util.List<ShipOrder> generateData() {
		Data o_data = new Data();
		java.util.List<ShipOrder> a_shipOrders = new java.util.ArrayList<ShipOrder>();
		
		try {
				ShipOrder o_shipOrder = o_data.new ShipOrder();
					o_shipOrder.setOrderId("ORD0001");
					o_shipOrder.setOrderPerson("Jon Doe");
					o_shipOrder.setOrderDate(java.time.LocalDate.of(2020, 1, 25));
					o_shipOrder.setOverallPrice(388.95f);
					o_shipOrder.setSomeBools( new boolean[] { false, true, false } );
					
					ShipTo o_shipTo = o_data.new ShipTo();
						o_shipTo.setName("John Smith");
						o_shipTo.setStreet("First Ave.");
						o_shipTo.setNumber(23);
						o_shipTo.setCity("New York");
						o_shipTo.setCountry("United States");
						o_shipTo.setDelivered(java.time.LocalDateTime.of(2020, 2, 3, 8, 27, 15));
						o_shipTo.setStored(new java.text.SimpleDateFormat("HH:mm:ss").parse("08:45:00"));
						o_shipTo.setHighPriority(false);
					
					o_shipOrder.setShipTo(o_shipTo);
					
					ShipMoreInfo o_shipMoreInfo = o_data.new ShipMoreInfo();
						
						ShipFrom o_shipFrom = o_data.new ShipFrom();
							o_shipFrom.setDeliveredBy("Francois Montpellier");
							o_shipFrom.setDeliveredCountry("France");
							o_shipFrom.setShipVia(10);
							o_shipFrom.setShipRegistered(java.time.LocalDateTime.of(2020, 2, 3, 9, 30, 0));
							
						o_shipMoreInfo.setShipFrom(o_shipFrom);
						
					o_shipOrder.setShipMoreInfo(o_shipMoreInfo);
					
					ShipItem o_shipItem1 = o_data.new ShipItem();
						o_shipItem1.setTitle("Item #1");
						o_shipItem1.setNote("----");
						o_shipItem1.setManufacturedTime(java.time.LocalTime.of(8, 42, 21));
						o_shipItem1.setQuantity(15);
						o_shipItem1.setPrice(new java.math.BigDecimal(5.25d));
						o_shipItem1.setCurrency("EUR");
						o_shipItem1.setSkonto(2.15d);
						o_shipItem1.setSomeDecimals(
							new java.math.BigDecimal[] {
								new java.math.BigDecimal("1.602176634"),
								new java.math.BigDecimal("8.8541878128"),
								new java.math.BigDecimal("6.62607015"),
								new java.math.BigDecimal("9.80665"),
								new java.math.BigDecimal("3.14159265359")
							}
						);
						o_shipItem1.setShipItemInfo(o_data.new ShipItemInfo());
						o_shipItem1.getShipItemInfo().setDevelopment("Development 1.1");
						
					ShipItem o_shipItem2 = o_data.new ShipItem();
						o_shipItem2.setTitle("Item #2");
						o_shipItem2.setNote("be careful");
						o_shipItem2.setManufacturedTime(java.time.LocalTime.of(13, 1, 0));
						o_shipItem2.setQuantity(35);
						o_shipItem2.setPrice(new java.math.BigDecimal(1.88d));
						o_shipItem2.setCurrency("EUR");
						o_shipItem2.setSkonto(5.00d);
						o_shipItem2.setSomeDecimals(
							new java.math.BigDecimal[] {
								new java.math.BigDecimal("1.602176634"),
								new java.math.BigDecimal("6.62607015"),
								new java.math.BigDecimal("3.14159265359")
							}
						);
						o_shipItem2.setShipItemInfo(o_data.new ShipItemInfo());
						o_shipItem2.getShipItemInfo().setDevelopment("Development 1.2");
						o_shipItem2.getShipItemInfo().setImplementation("Implementation 1.2");
						
					ShipItem o_shipItem3 = o_data.new ShipItem();
						o_shipItem3.setTitle("Item #3");
						o_shipItem3.setNote("store cold");
						o_shipItem3.setManufacturedTime(java.time.LocalTime.of(18, 59, 59));
						o_shipItem3.setQuantity(5);
						o_shipItem3.setPrice(new java.math.BigDecimal(12.23d));
						o_shipItem3.setCurrency("USD");
						o_shipItem3.setSkonto(7.86d);
						o_shipItem3.setSomeDecimals(
							new java.math.BigDecimal[] {
								new java.math.BigDecimal("8.8541878128"),
								new java.math.BigDecimal("9.80665")
							}
						);
						o_shipItem3.setShipItemInfo(o_data.new ShipItemInfo());
						o_shipItem3.getShipItemInfo().setConstruction("Construction 1.3");
						
				o_shipOrder.getShipItems().add(o_shipItem1);
				o_shipOrder.getShipItems().add(o_shipItem2);
				o_shipOrder.getShipItems().add(o_shipItem3);
							
			a_shipOrders.add(o_shipOrder);
			
				o_shipOrder = o_data.new ShipOrder();
				o_shipOrder.setOrderId("ORD0002");
				o_shipOrder.setOrderPerson("Linda Williams");
				o_shipOrder.setOrderDate(java.time.LocalDate.of(2020, 1, 13));
				o_shipOrder.setOverallPrice(1589.41f);
				o_shipOrder.setSomeBools( new boolean[] { true, false, true } );
				
					o_shipTo = o_data.new ShipTo();
						o_shipTo.setName("Elizabeth Miller");
						o_shipTo.setStreet("Old Street");
						o_shipTo.setNumber(2);
						o_shipTo.setCity("Hamburg");
						o_shipTo.setCountry("Germany");
						o_shipTo.setDelivered(java.time.LocalDateTime.of(2020, 1, 16, 14, 2, 55));
						o_shipTo.setStored(new java.text.SimpleDateFormat("HH:mm:ss").parse("14:15:00"));
						o_shipTo.setHighPriority(true);
					
					o_shipOrder.setShipTo(o_shipTo);
					
					o_shipMoreInfo = o_data.new ShipMoreInfo();
						o_shipMoreInfo.setMoreNote("Note #2");
					
						o_shipFrom = o_data.new ShipFrom();
							o_shipFrom.setDeliveredBy("Hans Kunz");
							o_shipFrom.setDeliveredCountry("Österreich");
							o_shipFrom.setShipVia(5);
							o_shipFrom.setShipRegistered(java.time.LocalDateTime.of(2020, 1, 16, 14, 2, 55));
							
						o_shipMoreInfo.setShipFrom(o_shipFrom);
						
						ShipSite o_shipSite = o_data.new ShipSite();
							o_shipSite.setSiteName("Site Q");
							o_shipSite.setBuilding("Building W3");
						
						o_shipMoreInfo.setShipSite(o_shipSite);
						
						ShipLocation o_shipLocation = o_data.new ShipLocation();
							o_shipLocation.setLocName("Location A");
							o_shipLocation.setLocFloor(5);
							o_shipLocation.setLocRack(99);
						
						o_shipMoreInfo.setShipLocation(o_shipLocation);
					
					o_shipOrder.setShipMoreInfo(o_shipMoreInfo);
					
					o_shipItem1 = o_data.new ShipItem();
						o_shipItem1.setTitle("Item #1");
						o_shipItem1.setNote("high value");
						o_shipItem1.setManufacturedTime(java.time.LocalTime.of(23, 0, 0));
						o_shipItem1.setQuantity(2);
						o_shipItem1.setPrice(new java.math.BigDecimal(500.00d));
						o_shipItem1.setCurrency("USD");
						o_shipItem1.setSkonto(0.00d);
						o_shipItem1.setSomeDecimals(
							new java.math.BigDecimal[] {
								new java.math.BigDecimal("1.602176634"),
								new java.math.BigDecimal("8.8541878128"),
								new java.math.BigDecimal("6.62607015"),
								new java.math.BigDecimal("9.80665"),
								new java.math.BigDecimal("3.14159265359")
							}
						);
						o_shipItem1.setShipItemInfo(o_data.new ShipItemInfo());
						o_shipItem1.getShipItemInfo().setDevelopment("Development 2.2");
						o_shipItem1.getShipItemInfo().setImplementation("Implementation 2.2");
						
					o_shipItem2 = o_data.new ShipItem();
						o_shipItem2.setTitle("Item #2");
						o_shipItem2.setNote("----");
						o_shipItem2.setManufacturedTime(java.time.LocalTime.of(2, 1, 2));
						o_shipItem2.setQuantity(64);
						o_shipItem2.setPrice(new java.math.BigDecimal(18.55d));
						o_shipItem2.setCurrency("USD");
						o_shipItem2.setSkonto(0.20d);
						o_shipItem2.setShipItemInfo(o_data.new ShipItemInfo());
						
					o_shipItem3 = o_data.new ShipItem();
						o_shipItem3.setTitle("Item #3");
						o_shipItem3.setNote("store dark");
						o_shipItem3.setManufacturedTime(java.time.LocalTime.of(6, 0, 30));
						o_shipItem3.setQuantity(19);
						o_shipItem3.setPrice(new java.math.BigDecimal(5.87d));
						o_shipItem3.setCurrency("EUR");
						o_shipItem3.setSkonto(9.55d);
						o_shipItem3.setSomeDecimals(
							new java.math.BigDecimal[] {
								new java.math.BigDecimal("1.602176634"),
								null
							}
						);
						o_shipItem3.setShipItemInfo(o_data.new ShipItemInfo());
						o_shipItem3.getShipItemInfo().setConstruction("Construction 2.3");
						
				o_shipOrder.getShipItems().add(o_shipItem1);
				o_shipOrder.getShipItems().add(o_shipItem2);
				o_shipOrder.getShipItems().add(o_shipItem3);
			
			a_shipOrders.add(o_shipOrder);
			
				o_shipOrder = o_data.new ShipOrder();
				o_shipOrder.setOrderId("ORD0003");
				o_shipOrder.setOrderPerson("David Davis");
				o_shipOrder.setOrderDate(java.time.LocalDate.of(2020, 1, 28));
				o_shipOrder.setOverallPrice(651.33f);
		
					o_shipTo = o_data.new ShipTo();
						o_shipTo.setName("Jennifer Garcia");
						o_shipTo.setStreet("New Street");
						o_shipTo.setNumber(89);
						o_shipTo.setCity("London");
						o_shipTo.setCountry("United Kingdom");
						o_shipTo.setDelivered(java.time.LocalDateTime.of(2020, 2, 6, 3, 55, 0));
						o_shipTo.setStored(new java.text.SimpleDateFormat("HH:mm:ss").parse("04:00:00"));
						o_shipTo.setHighPriority(false);
					
					o_shipOrder.setShipTo(o_shipTo);
					
					o_shipMoreInfo = o_data.new ShipMoreInfo();
						o_shipMoreInfo.setMoreNote("Note #3");
					
						o_shipFrom = o_data.new ShipFrom();
							o_shipFrom.setDeliveredBy("Jakub Kowalski");
							o_shipFrom.setDeliveredCountry("Polska");
							o_shipFrom.setShipVia(30);
							o_shipFrom.setShipRegistered(java.time.LocalDateTime.of(2020, 1, 06, 21, 35, 3));
						
						o_shipMoreInfo.setShipFrom(o_shipFrom);
						
						o_shipSite = o_data.new ShipSite();
							o_shipSite.setSiteName("Site A");
							o_shipSite.setBuilding("Building C.22");
							
						o_shipMoreInfo.setShipSite(o_shipSite);
						
					o_shipOrder.setShipMoreInfo(o_shipMoreInfo);
					
					o_shipItem1 = o_data.new ShipItem();
						o_shipItem1.setTitle("Item #1");
						o_shipItem1.setNote("be careful");
						o_shipItem1.setManufacturedTime(java.time.LocalTime.of(0, 0));
						o_shipItem1.setQuantity(20);
						o_shipItem1.setPrice(new java.math.BigDecimal(50.10d));
						o_shipItem1.setCurrency("EUR");
						o_shipItem1.setSkonto(0.10d);
						o_shipItem1.setSomeDecimals(
							new java.math.BigDecimal[] {
								new java.math.BigDecimal("9.80665"),
								new java.math.BigDecimal("3.14159265359")
							}
						);
						o_shipItem1.setShipItemInfo(o_data.new ShipItemInfo());
						o_shipItem1.getShipItemInfo().setConstruction("Construction 3.1");
						
					o_shipItem2 = o_data.new ShipItem();
						o_shipItem2.setTitle("Item #2");
						o_shipItem2.setNote("----");
						o_shipItem2.setManufacturedTime(java.time.LocalTime.of(8, 42, 21));
						o_shipItem2.setQuantity(32);
						o_shipItem2.setPrice(new java.math.BigDecimal(29.65d));
						o_shipItem2.setCurrency("EUR");
						o_shipItem2.setSkonto(0.00d);
						o_shipItem2.setSomeDecimals(
							new java.math.BigDecimal[] {
								new java.math.BigDecimal("1.602176634"),
								new java.math.BigDecimal("6.62607015"),
								new java.math.BigDecimal("3.14159265359")
							}
						);
						o_shipItem2.setShipItemInfo(o_data.new ShipItemInfo());
						o_shipItem2.getShipItemInfo().setConstruction("Construction 3.2");
						o_shipItem2.getShipItemInfo().setDevelopment("Development 3.2");
						
					o_shipItem3 = o_data.new ShipItem();
						o_shipItem3.setTitle("Item #3");
						o_shipItem3.setNote("----");
						o_shipItem3.setManufacturedTime(java.time.LocalTime.of(4, 21, 12));
						o_shipItem3.setQuantity(120);
						o_shipItem3.setPrice(new java.math.BigDecimal(2.50d));
						o_shipItem3.setCurrency("EUR");
						o_shipItem3.setSkonto(10.90d);
						o_shipItem3.setSomeDecimals(
							new java.math.BigDecimal[] {
								null
							}
						);
						o_shipItem3.setShipItemInfo(o_data.new ShipItemInfo());
						o_shipItem3.getShipItemInfo().setConstruction("Construction 3.3");
						
				o_shipOrder.getShipItems().add(o_shipItem1);
				o_shipOrder.getShipItems().add(o_shipItem2);
				o_shipOrder.getShipItems().add(o_shipItem3);
			
			a_shipOrders.add(o_shipOrder);
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
		}
		
		return a_shipOrders;
	}

	/**
	 * ship from class
	 */
	public class ShipFrom {

		/* Fields */
		
		private String DeliveredBy;
		private String DeliveredCountry;
		private int ShipVia;
		private java.time.LocalDateTime ShipRegistered;
		
		/* Properties */
		
		/**
		 * get delivered by
		 * 
		 * @return String
		 */
		public String getDeliveredBy() {
			return this.DeliveredBy;
		}
		
		/**
		 * set delivered by
		 * 
		 * @param p_s_value String
		 */
		public void setDeliveredBy(String p_s_value) {
			this.DeliveredBy = p_s_value;
		}
		
		/**
		 * get delivered country
		 * 
		 * @return String
		 */
		public String getDeliveredCountry() {
			return this.DeliveredCountry;
		}
		
		/**
		 * set delivered country
		 * 
		 * @param p_s_value String
		 */
		public void setDeliveredCountry(String p_s_value) {
			this.DeliveredCountry = p_s_value;
		}
		
		/**
		 * get ship via
		 * 
		 * @return int
		 */
		public int getShipVia() {
			return this.ShipVia;
		}
		
		/**
		 * set ship via
		 * 
		 * @param p_i_value int
		 */
		public void setShipVia(int p_i_value) {
			this.ShipVia = p_i_value;
		}
		
		/**
		 * get ship registered
		 * 
		 * @return java.time.LocalDateTime
		 */
		public java.time.LocalDateTime getShipRegistered() {
			return this.ShipRegistered;
		}
		
		/**
		 * set ship registered
		 * 
		 * @param p_o_value java.time.LocalDateTime
		 */
		public void setShipRegistered(java.time.LocalDateTime p_o_value) {
			this.ShipRegistered = p_o_value;
		}
		
		/* Methods */
		
		/**
		 * ship from constructor
		 */
		public ShipFrom() {
			
		}
		
		@Override public String toString() {
			String s_foo = "";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				String s_value = "";
				
				try {
					s_value = o_field.get(this).toString();
				} catch (Exception o_exc) {
					s_value = "null";
				}
				
				s_foo += o_field.getName() + " = " + s_value + "|";
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
			
			return s_foo;
		}
	}
	
	/**
	 * ship item class
	 */
	public class ShipItem {

		/* Fields */
		
		private String Title;
		private String Note;
		private java.time.LocalTime ManufacturedTime;
		private int Quantity;
		private java.math.BigDecimal Price = new java.math.BigDecimal(0);
		private String Currency;
		private double Skonto;
		private java.math.BigDecimal[] SomeDecimals;
		private ShipItemInfo ShipItemInfo;
		
		/* Properties */
		
		/**
		 * get title
		 * 
		 * @return String
		 */
		public String getTitle() {
			return this.Title;
		}
		
		/**
		 * set title
		 * 
		 * @param p_s_value String
		 */
		public void setTitle(String p_s_value) {
			this.Title = p_s_value;
		}
		
		/**
		 * get note
		 * 
		 * @return String
		 */
		public String getNote() {
			return this.Note;
		}
		
		/**
		 * set note
		 * 
		 * @param p_s_value String
		 */
		public void setNote(String p_s_value) {
			this.Note = p_s_value;
		}
		
		/**
		 * get manufactured time
		 * 
		 * @return java.time.LocalTime
		 */
		public java.time.LocalTime getManufacturedTime() {
			return this.ManufacturedTime;
		}
		
		/**
		 * set manufactured time
		 * 
		 * @param p_o_value java.time.LocalTime
		 */
		public void setManufacturedTime(java.time.LocalTime p_o_value) {
			this.ManufacturedTime = p_o_value;
		}
		
		/**
		 * get quantity
		 * 
		 * @return int
		 */
		public int getQuantity() {
			return this.Quantity;
		}
		
		/**
		 * set quantity
		 * 
		 * @param p_i_value int
		 */
		public void setQuantity(int p_i_value) {
			this.Quantity = p_i_value;
		}
		
		/**
		 * get price
		 * 
		 * @return java.math.BigDecimal
		 */
		public java.math.BigDecimal getPrice() {
			return this.Price;
		}
		
		/**
		 * set price
		 * 
		 * @param p_o_value java.math.BigDecimal
		 */
		public void setPrice(java.math.BigDecimal p_o_value) {
			this.Price = p_o_value;
		}
		
		/**
		 * get currency
		 * 
		 * @return String
		 */
		public String getCurrency() {
			return this.Currency;
		}
		
		/**
		 * set currency
		 * 
		 * @param p_s_value String
		 */
		public void setCurrency(String p_s_value) {
			this.Currency = p_s_value;
		}
		
		/**
		 * get skonto
		 * 
		 * @return Double
		 */
		public Double getSkonto() {
			return this.Skonto;
		}
		
		/**
		 * set skonto
		 * 
		 * @param p_d_value Double
		 */
		public void setSkonto(Double p_d_value) {
			this.Skonto = p_d_value;
		}
		
		/**
		 * get some decimals
		 * 
		 * @return java.math.BigDecimal[]
		 */
		public java.math.BigDecimal[] getSomeDecimals() {
			return this.SomeDecimals;
		}
		
		/**
		 * set some decimals
		 * 
		 * @param p_a_value java.math.BigDecimal[]
		 */
		public void setSomeDecimals(java.math.BigDecimal[] p_a_value) {
			this.SomeDecimals = p_a_value;
		}
		
		/**
		 * get ship item info
		 * 
		 * @return ShipItemInfo
		 */
		public ShipItemInfo getShipItemInfo() {
			return this.ShipItemInfo;
		}
		
		/**
		 * set ship item info
		 * 
		 * @param p_o_value ShipItemInfo
		 */
		public void setShipItemInfo(ShipItemInfo p_o_value) {
			this.ShipItemInfo = p_o_value;
		}
		
		/* Methods */
		
		/**
		 * ship item constructor
		 */
		public ShipItem() {
			
		}
		
		@Override public String toString() {
			String s_foo = "";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				try {
					if ( (o_field.get(this) instanceof ShipTo) || (o_field.get(this) instanceof ShipItemInfo) ) {
						s_foo += o_field.getName() + " = [" + o_field.get(this).toString() + "]|";
					} else if (o_field.getType().getTypeName().endsWith("[]")) {
						s_foo += o_field.getName() + " = " + net.forestany.forestj.lib.Helper.printArrayList( java.util.Arrays.asList( (java.math.BigDecimal[])o_field.get(this) ) ) + "|";
					} else {
						s_foo += o_field.getName() + " = " + o_field.get(this).toString() + "|";
					}
				} catch (Exception o_exc) {
					s_foo += o_field.getName() + " = null|";
				}
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
			
			return s_foo;
		}
	}
	
	/**
	 * ship item info class
	 */
	public class ShipItemInfo {

		/* Fields */
		
		private String Development;
		private String Construction;
		private String Implementation;
		
		/* Properties */
		
		/**
		 * get development
		 * 
		 * @return String
		 */
		public String getDevelopment() {
			return this.Development;
		}
		
		/**
		 * set development
		 * 
		 * @param p_s_value String
		 */
		public void setDevelopment(String p_s_value) {
			this.Development = p_s_value;
		}
		
		/**
		 * get construction
		 * 
		 * @return String
		 */
		public String getConstruction() {
			return this.Construction;
		}
		
		/**
		 * set construction
		 * 
		 * @param p_s_value String
		 */
		public void setConstruction(String p_s_value) {
			this.Construction = p_s_value;
		}
		
		/**
		 * get implementation
		 * 
		 * @return String
		 */
		public String getImplementation() {
			return this.Implementation;
		}
		
		/**
		 * set implementation
		 * 
		 * @param p_s_value String
		 */
		public void setImplementation(String p_s_value) {
			this.Implementation = p_s_value;
		}
		
		/* Methods */
		
		/**
		 * ship item info constructor
		 */
		public ShipItemInfo() {
			
		}
		
		@Override public String toString() {
			String s_foo = "";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				String s_value = "";
				
				try {
					s_value = o_field.get(this).toString();
				} catch (Exception o_exc) {
					s_value = "null";
				}
				
				s_foo += o_field.getName() + " = " + s_value + "|";
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
			
			return s_foo;
		}
	}
	
	/**
	 * ship location class
	 */
	public class ShipLocation {

		/* Fields */
		
		private String LocName;
		private int LocFloor;
		private int LocRack;
		
		/* Properties */
		
		/**
		 * get location name
		 * 
		 * @return String
		 */
		public String getLocName() {
			return this.LocName;
		}
		
		/**
		 * set location name
		 * 
		 * @param p_s_value String
		 */
		public void setLocName(String p_s_value) {
			this.LocName = p_s_value;
		}
		
		/**
		 * get location floor
		 * 
		 * @return int
		 */
		public int getLocFloor() {
			return this.LocFloor;
		}
		
		/**
		 * set location floor
		 * 
		 * @param p_i_value int
		 */
		public void setLocFloor(int p_i_value) {
			this.LocFloor = p_i_value;
		}
		
		/**
		 * get location rack
		 * 
		 * @return int
		 */
		public int getLocRack() {
			return this.LocRack;
		}
		
		/**
		 * set location rack
		 * @param p_i_value int
		 */
		public void setLocRack(int p_i_value) {
			this.LocRack = p_i_value;
		}
		
		/* Methods */
		
		/**
		 * ship location constructor
		 */
		public ShipLocation() {
			
		}
		
		@Override public String toString() {
			String s_foo = "";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				String s_value = "";
				
				try {
					s_value = o_field.get(this).toString();
				} catch (Exception o_exc) {
					s_value = "null";
				}
				
				s_foo += o_field.getName() + " = " + s_value + "|";
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
			
			return s_foo;
		}
	}
	
	/**
	 * ship more info class
	 */

	public class ShipMoreInfo {

		/* Fields */
		
		private String MoreNote;
		private ShipFrom ShipFrom;
		private ShipSite ShipSite;
		private ShipLocation ShipLocation;
		
		/* Properties */
		
		/**
		 * get more note
		 * 
		 * @return String
		 */
		public String getMoreNote() {
			return this.MoreNote;
		}
		
		/**
		 * set more note
		 * 
		 * @param p_s_value String
		 */
		public void setMoreNote(String p_s_value) {
			this.MoreNote = p_s_value;
		}
		
		/**
		 * get ship from
		 * 
		 * @return ShipFrom
		 */
		public ShipFrom getShipFrom() {
			return this.ShipFrom;
		}
		
		/**
		 * set ship from
		 * 
		 * @param p_o_value ShipFrom
		 */
		public void setShipFrom(ShipFrom p_o_value) {
			this.ShipFrom = p_o_value;
		}
		
		/**
		 * get ship site
		 * 
		 * @return ShipSite
		 */
		public ShipSite getShipSite() {
			return this.ShipSite;
		}
		
		/**
		 * set ship site
		 * 
		 * @param p_o_value ShipSite
		 */
		public void setShipSite(ShipSite p_o_value) {
			this.ShipSite = p_o_value;
		}
		
		/**
		 * get ship location
		 * 
		 * @return ShipLocation
		 */
		public ShipLocation getShipLocation() {
			return this.ShipLocation;
		}
		
		/**
		 * set ship location
		 * 
		 * @param p_o_value ShipLocation
		 */
		public void setShipLocation(ShipLocation p_o_value) {
			this.ShipLocation = p_o_value;
		}
			
		/* Methods */
		
		/**
		 * ship more info constructor
		 */
		public ShipMoreInfo() {
			
		}
		
		@Override public String toString() {
			String s_foo = "";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				try {
					if ( (o_field.get(this) instanceof ShipFrom) || (o_field.get(this) instanceof ShipSite) || (o_field.get(this) instanceof ShipLocation) ) {
						s_foo += o_field.getName() + " = [" + o_field.get(this).toString() + "]|";
					} else {
						s_foo += o_field.getName() + " = " + o_field.get(this).toString() + "|";
					}
				} catch (Exception o_exc) {
					s_foo += o_field.getName() + " = null|";
				}
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
			
			return s_foo;
		}
	}
	
	/**
	 * ship site class
	 */
	
	public class ShipSite {

		/* Fields */
		
		private String SiteName;
		private String Building;
		
		/* Properties */
		
		/**
		 * get site name
		 * 
		 * @return String
		 */
		public String getSiteName() {
			return this.SiteName;
		}
		
		/**
		 * set site name
		 * 
		 * @param p_s_value String
		 */
		public void setSiteName(String p_s_value) {
			this.SiteName = p_s_value;
		}
		
		/**
		 * get building
		 * 
		 * @return String
		 */
		public String getBuilding() {
			return this.Building;
		}
		
		/**
		 * set building
		 * 
		 * @param p_s_value String
		 */
		public void setBuilding(String p_s_value) {
			this.Building = p_s_value;
		}
		
		/* Methods */
		
		/**
		 * ship site constructor
		 */
		public ShipSite() {
			
		}
		
		@Override public String toString() {
			String s_foo = "";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				String s_value = "";
				
				try {
					s_value = o_field.get(this).toString();
				} catch (Exception o_exc) {
					s_value = "null";
				}
				
				s_foo += o_field.getName() + " = " + s_value + "|";
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
			
			return s_foo;
		}
	}
	
	/**
	 * ship to class
	 */
	
	public class ShipTo {

		/* Fields */
		
		private String Name;
		private String Street;
		private int Number;
		private String City;
		private String Country;
		private java.time.LocalDateTime Delivered;
		private java.util.Date Stored;
		private boolean HighPriority;
		
		/* Properties */
		
		/**
		 * get name
		 * 
		 * @return String
		 */
		public String getName() {
			return this.Name;
		}
		
		/**
		 * set name
		 * 
		 * @param p_s_value String
		 */
		public void setName(String p_s_value) {
			this.Name = p_s_value;
		}
		
		/**
		 * get street
		 * 
		 * @return String
		 */
		public String getStreet() {
			return this.Street;
		}
		
		/**
		 * set street
		 * 
		 * @param p_s_value String
		 */
		public void setStreet(String p_s_value) {
			this.Street = p_s_value;
		}
		
		/**
		 * get number
		 * 
		 * @return int
		 */
		public int getNumber() {
			return this.Number;
		}
		
		/**
		 * set number
		 * 
		 * @param p_i_value String
		 */
		public void setNumber(int p_i_value) {
			this.Number = p_i_value;
		}
		
		/**
		 * get city
		 * 
		 * @return String
		 */
		public String getCity() {
			return this.City;
		}
		
		/**
		 * set city
		 * 
		 * @param p_s_value String
		 */
		public void setCity(String p_s_value) {
			this.City = p_s_value;
		}
		
		/**
		 * get country
		 * 
		 * @return String
		 */
		public String getCountry() {
			return this.Country;
		}
		
		/**
		 * set country
		 * 
		 * @param p_s_value String
		 */
		public void setCountry(String p_s_value) {
			this.Country = p_s_value;
		}
		
		/**
		 * get delivered
		 * 
		 * @return java.time.LocalDateTime
		 */
		public java.time.LocalDateTime getDelivered() {
			return this.Delivered;
		}
		
		/**
		 * set delivered
		 * 
		 * @param p_o_value java.time.LocalDateTime
		 */
		public void setDelivered(java.time.LocalDateTime p_o_value) {
			this.Delivered = p_o_value;
		}
		
		/**
		 * get stored
		 * 
		 * @return java.util.Date
		 */
		public java.util.Date getStored() {
			return this.Stored;
		}
		
		/**
		 * set stored
		 * 
		 * @param p_o_value java.util.Date
		 */
		public void setStored(java.util.Date p_o_value) {
			this.Stored = p_o_value;
		}
		
		/**
		 * get high priority
		 * 
		 * @return boolean
		 */
		public boolean getHighPriority() {
			return this.HighPriority;
		}
		
		/**
		 * set high priority
		 * 
		 * @param p_b_value boolean
		 */
		public void setHighPriority(boolean p_b_value) {
			this.HighPriority = p_b_value;
		}
		
		/* Methods */
		
		/**
		 * ship to constructor
		 */
		public ShipTo() {
			
		}
		
		@Override public String toString() {
			String s_foo = "";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				String s_value = "";
				
				try {
					s_value = o_field.get(this).toString();
				} catch (Exception o_exc) {
					s_value = "null";
				}
				
				s_foo += o_field.getName() + " = " + s_value + "|";
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
			
			return s_foo;
		}
	}
	
	/**
	 * ship order class
	 */

	public class ShipOrder {

		/* Fields */
		
		private String OrderId;
		private java.time.LocalDate OrderDate;
		private float OverallPrice;
		private String OrderPerson;
		private ShipTo ShipTo;
		private ShipMoreInfo ShipMoreInfo;
		private boolean[] SomeBools;
		private java.util.List<ShipItem> ShipItems = new java.util.ArrayList<ShipItem>();
		
		/* Properties */
		
		/**
		 * get order id
		 * 
		 * @return String
		 */
		public String getOrderId() {
			return this.OrderId;
		}
		
		/**
		 * set order id
		 * 
		 * @param p_s_value String
		 */
		public void setOrderId(String p_s_value) {
			this.OrderId = p_s_value;
		}
		
		/**
		 * get order date
		 * 
		 * @return java.time.LocalDate
		 */
		public java.time.LocalDate getOrderDate() {
			return this.OrderDate;
		}
		
		/**
		 * set order date
		 * 
		 * @param p_o_value java.time.LocalDate
		 */
		public void setOrderDate(java.time.LocalDate p_o_value) {
			this.OrderDate = p_o_value;
		}
		
		/**
		 * get overall price
		 * 
		 * @return Float
		 */
		public Float getOverallPrice() {
			return this.OverallPrice;
		}
		
		/**
		 * set overall price
		 * 
		 * @param p_f_value Float
		 */
		public void setOverallPrice(Float p_f_value) {
			this.OverallPrice = p_f_value;
		}
		
		/**
		 * get order person
		 * 
		 * @return String
		 */
		public String getOrderPerson() {
			return this.OrderPerson;
		}
		
		/**
		 * set order person
		 * 
		 * @param p_s_value String
		 */
		public void setOrderPerson(String p_s_value) {
			this.OrderPerson = p_s_value;
		}
		
		/**
		 * get ship to
		 * @return ShipTo
		 */
		public ShipTo getShipTo() {
			return this.ShipTo;
		}
		
		/**
		 * set ship to
		 * 
		 * @param p_o_value ShipTo
		 */
		public void setShipTo(ShipTo p_o_value) {
			this.ShipTo = p_o_value;
		}
		
		/**
		 * get ship more info
		 * @return ShipMoreInfo
		 */
		public ShipMoreInfo getShipMoreInfo() {
			return this.ShipMoreInfo;
		}
		
		/**
		 * set ship more info
		 * 
		 * @param p_o_value ShipMoreInfo
		 */
		public void setShipMoreInfo(ShipMoreInfo p_o_value) {
			this.ShipMoreInfo = p_o_value;
		}
		
		/**
		 * get some booleans
		 * 
		 * @return boolean[]
		 */
		public boolean[] getSomeBools() {
			return this.SomeBools;
		}
		
		/**
		 * set some booleans
		 * @param p_a_value boolean[]
		 */
		public void setSomeBools(boolean[] p_a_value) {
			this.SomeBools = p_a_value;
		}
		
		/**
		 * get ship items
		 * 
		 * @return java.util.List&lt;ShipItem&gt;
		 */
		public java.util.List<ShipItem> getShipItems() {
			return this.ShipItems;
		}
		
		/**
		 * set ship items
		 * 
		 * @param p_a_value java.util.List&lt;ShipItem&gt;
		 */
		public void setShipItems(java.util.List<ShipItem> p_a_value) {
			this.ShipItems = p_a_value;
		}
			
		/* Methods */
		
		/**
		 * ship order constructor
		 */
		public ShipOrder() {
			
		}
		
		@Override public String toString() {
			String s_foo = "";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				try {
					if (o_field.get(this) instanceof java.util.List<?>) {
						@SuppressWarnings("unchecked")
						java.util.List<ShipItem> a_items = (java.util.List<ShipItem>)o_field.get(this);
						
						s_foo += o_field.getName() + " = [";
						
						if (a_items.size() > 0) {
							for (ShipItem o_shipItem : a_items) {
								s_foo += o_shipItem.toString() + ",";
							}
							
							s_foo = s_foo.substring(0, s_foo.length() - 1);
						}
						
						s_foo += "]|";
					} else if ( (o_field.get(this) instanceof ShipTo) || (o_field.get(this) instanceof ShipMoreInfo) ) {
						s_foo += o_field.getName() + " = [" + o_field.get(this).toString() + "]|";
					} else if (o_field.getType().getTypeName().endsWith("[]")) {
						Boolean[] a_foo = new Boolean[((boolean[])o_field.get(this)).length];
						int i = 0;
						
						for (boolean b_foo : (boolean[])o_field.get(this)) {
							a_foo[i++] = b_foo;
						}
						s_foo += o_field.getName() + " = " + net.forestany.forestj.lib.Helper.printArrayList( java.util.Arrays.asList( (Boolean[])a_foo ) ) + "|";
					} else {
						s_foo += o_field.getName() + " = " + o_field.get(this).toString() + "|";
					}
				} catch (Exception o_exc) {
					s_foo += o_field.getName() + " = null|";
				}
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
			
			return s_foo;
		}
	}
	
	/**
	 * ship order collection class
	 */
	
	public class ShipOrderCollection {

		/* Fields */
		
		private int OrderAmount;
		private java.util.List<ShipOrder> ShipOrders = new java.util.ArrayList<ShipOrder>();
		
		/* Properties */
		
		/**
		 * get order amount
		 * 
		 * @return int
		 */
		public int getOrderAmount() {
			return this.OrderAmount;
		}
		
		/**
		 * set order amount
		 * 
		 * @param p_i_value int
		 */
		public void setOrderAmount(int p_i_value) {
			this.OrderAmount = p_i_value;
		}
		
		/**
		 * get ship orders
		 * 
		 * @return java.util.List&lt;ShipOrder&gt;
		 */
		public java.util.List<ShipOrder> getShipOrders() {
			return this.ShipOrders;
		}
		
		/**
		 * set ship orders
		 * 
		 * @param p_a_value java.util.List&lt;ShipOrder&gt;
		 */
		public void setShipOrders(java.util.List<ShipOrder> p_a_value) {
			this.ShipOrders = p_a_value;
		}
			
		/* Methods */
		
		/**
		 * ship order collection constructor
		 */
		public ShipOrderCollection() {
			
		}
		
		@Override public String toString() {
			String s_foo = "";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				try {
					if (o_field.get(this) instanceof java.util.List<?>) {
						@SuppressWarnings("unchecked")
						java.util.List<ShipOrder> a_items = (java.util.List<ShipOrder>)o_field.get(this);
						
						s_foo += o_field.getName() + " = [";
						
						if (a_items.size() > 0) {
							for (ShipOrder o_shipOrder : a_items) {
								s_foo += o_shipOrder.toString() + ",";
							}
							
							s_foo = s_foo.substring(0, s_foo.length() - 1);
						}
						
						s_foo += "]|";
					} else {
						s_foo += o_field.getName() + " = " + o_field.get(this).toString() + "|";
					}
				} catch (Exception o_exc) {
					s_foo += o_field.getName() + " = null|";
				}
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
			
			return s_foo;
		}
	}
	
	/**
	 * simple class
	 */
	
	public class SimpleClass implements java.io.Serializable {
		
		private static final long serialVersionUID = 5409016192193754196L;
		
		/**
		 * value A
		 */
		public String ValueA;
		/**
		 * value B
		 */
		public String ValueB;
		/**
		 * value C
		 */
		public String ValueC;
		/**
		 * value D
		 */
		public java.util.List<Integer> ValueD = new java.util.ArrayList<Integer>();
		/**
		 * value E
		 */
		public float[] ValueE;  
		
		/**
		 * simple class constructor
		 */
		public SimpleClass() {
			
		}
		
		/**
		 * simple class constructor
		 * 
		 * @param p_s_valueA value A
		 * @param p_s_valueB value B
		 * @param p_s_valueC value C
		 */
		public SimpleClass(String p_s_valueA, String p_s_valueB, String p_s_valueC) {
			this(p_s_valueA, p_s_valueB, p_s_valueC, null);
		}
		
		/**
		 * simple class constructor
		 * 
		 * @param p_s_valueA value A
		 * @param p_s_valueB value B
		 * @param p_s_valueC value C
		 * @param p_a_valueD value D
		 */
		public SimpleClass(String p_s_valueA, String p_s_valueB, String p_s_valueC, java.util.List<Integer> p_a_valueD) {
			this(p_s_valueA, p_s_valueB, p_s_valueC, p_a_valueD, null);
		}
		
		/**
		 * simple class constructor
		 * 
		 * @param p_s_valueA value A
		 * @param p_s_valueB value B
		 * @param p_s_valueC value C
		 * @param p_a_valueD value D
		 * @param p_a_valueE value E
		 */
		public SimpleClass(String p_s_valueA, String p_s_valueB, String p_s_valueC, java.util.List<Integer> p_a_valueD, float[] p_a_valueE) {
			this.ValueA = p_s_valueA;
			this.ValueB = p_s_valueB;
			this.ValueC = p_s_valueC;
			
			if (p_a_valueD != null) {
				this.ValueD = p_a_valueD;
			}
			
			if (p_a_valueE != null) {
				this.ValueE = p_a_valueE;
			}
		}
		
		@Override public String toString() {
			String s_foo = "";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				String s_value = "";
				
				try {
					if (o_field.getType().getTypeName().endsWith("[]")) {
						Float[] a_foo = new Float[((float[])o_field.get(this)).length];
						int i = 0;
						
						for (float f_foo : (float[])o_field.get(this)) {
							a_foo[i++] = f_foo;
						}
						
						s_value = net.forestany.forestj.lib.Helper.printArrayList( java.util.Arrays.asList( (Float[])a_foo ) );
					} else {
						s_value = o_field.get(this).toString();
					}
				} catch (Exception o_exc) {
					s_value = "null";
				}
				
				s_foo += o_field.getName() + " = " + s_value + "|";
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
			
			return s_foo;
		}
	}
	
	/**
	 * simple class collection
	 */

	public class SimpleClassCollection implements java.io.Serializable {
		private static final long serialVersionUID = -7111371115714974222L;
		
		/**
		 * simple classes
		 */
		public java.util.List<SimpleClass> SimpleClasses = new java.util.ArrayList<SimpleClass>();
		
		/**
		 * simple class collection constructor
		 */
		public SimpleClassCollection() {
			
		}
		
		/**
		 * simple class collection constructor
		 * 
		 * @param p_a_data simple classes
		 */
		public SimpleClassCollection(java.util.List<SimpleClass> p_a_data) {
			this.SimpleClasses = p_a_data;
		}
		
		@Override public String toString() {
			String s_foo = "";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				try {
					if (o_field.get(this) instanceof java.util.List<?>) {
						@SuppressWarnings("unchecked")
						java.util.List<SimpleClass> a_items = (java.util.List<SimpleClass>)o_field.get(this);
						
						s_foo += o_field.getName() + " = [";
						
						if (a_items.size() > 0) {
							for (SimpleClass o_simpleClass : a_items) {
								s_foo += o_simpleClass.toString() + ",";
							}
							
							s_foo = s_foo.substring(0, s_foo.length() - 1);
						}
						
						s_foo += "]|";
					} else if ( (o_field.get(this) instanceof ShipTo) || (o_field.get(this) instanceof ShipMoreInfo) ) {
						s_foo += o_field.getName() + " = [" + o_field.get(this).toString() + "]|";
					} else {
						s_foo += o_field.getName() + " = " + o_field.get(this).toString() + "|";
					}
				} catch (Exception o_exc) {
					s_foo += o_field.getName() + " = null|";
				}
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
			
			return s_foo;
		}
	}
}
