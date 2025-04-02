package net.forestany.forestj.lib;

/**
 * 
 * Class to handle currency values consistent.
 *
 */
public class Currency {
	
	/* Enumerations */
	
	/**
	 * currency description enumeration
	 */
	public enum CurrencyDescription {
		/**
		 * Euro currency
		 */
		EUR(0),
		/**
		 * US-Dollar currency
		 */
		USD(1),
		/**
		 * Pound sterling currency 
		 */
		GBP(2),
		/**
		 * Japanese yen currency
		 */
		YEN(3);
		
		private int CurrencyDescriptionIndex;
	    
		CurrencyDescription(int p_i_value) {
	        this.CurrencyDescriptionIndex = p_i_value;
	    }
	 
		/**
		 * get currency description index
		 * 
		 * @return int
		 */
	    public int getCurrencyDescriptionIndex() {
	        return this.CurrencyDescriptionIndex;
	    }
	}
	
	/* Constants */
	
	/**
     * currency symbols matching to currency description enumeration
     */ 
    public static final String[] CurrencySymbols = { "€", "$", "£", "¥" };
	
	/* Fields */
	
	private java.math.BigDecimal o_bigDecimal;
	private CurrencyDescription e_description;
	private java.util.Locale o_locale;
	private char c_decimalSeparator;
	private char c_groupSeparator;
	private java.text.DecimalFormatSymbols o_decimalFormatSymbols;
	private java.text.DecimalFormat o_decimalFormat;
	private int i_minimumIntegerDigits;
	private int i_minimumFractionDigits;
	private boolean b_useCurrencySymbol;
	
	/* Properties */
	
	/**
	 * get value
	 * 
	 * @return java.math.BigDecimal
	 */
	public java.math.BigDecimal getValue() {
		return o_bigDecimal;
	}

	/**
	 * set value
	 * 
	 * @param p_o_value		java.math.BigDecimal value	
	 */
	public void setValue(java.math.BigDecimal p_o_value) {
		this.updateCurrencySettings(p_o_value, this.getDescription(), this.getLocale(), this.getDecimalSeparator(), this.getGroupSeparator(), this.getMinimumIntegerDigits(), this.getMinimumFractionDigits());
	}

	/**
	 * get description
	 * 
	 * @return net.forestany.forestj.lib.Currency.CurrencyDescription
	 */
	public CurrencyDescription getDescription() {
		return this.e_description;
	}

	/**
	 * set description
	 * 
	 * @param p_e_description				net.forestany.forestj.lib.Currency.CurrencyDescription
	 * @throws IllegalArgumentException		parameter is null
	 */
	public void setDescription(CurrencyDescription p_e_description) throws IllegalArgumentException {
		if (p_e_description == null) {
			throw new IllegalArgumentException("Description parameter is null.");
		}
		
		this.updateCurrencySettings(this.getValue(), p_e_description, this.getLocale(), this.getDecimalSeparator(), this.getGroupSeparator(), this.getMinimumIntegerDigits(), this.getMinimumFractionDigits());
	}
	
	/**
	 * get locale setting
	 * 
	 * @return java.util.Locale
	 */
	public java.util.Locale getLocale() {
		return this.o_locale;
	}

	/**
	 * set locale setting
	 * 
	 * @param p_o_value					locale instance
	 * @throws NullPointerException		parameter is null
	 */
	public void setLocale(java.util.Locale p_o_value) {
		this.updateCurrencySettings(this.getValue(), this.getDescription(), p_o_value, '$', '$', this.getMinimumIntegerDigits(), this.getMinimumFractionDigits());
	}
	
	/**
	 * get decimal separator
	 * 
	 * @return char
	 */
	public char getDecimalSeparator() {
		return this.c_decimalSeparator;
	}

	/**
	 * set decimal separator
	 * 
	 * @param p_c_value						character for decimal separator
	 * @throws IllegalArgumentException		'$' is not allowed
	 */
	public void setDecimalSeparator(char p_c_value) throws IllegalArgumentException {
		if (p_c_value == '$') {
			throw new IllegalArgumentException("Illegal decimal separator '" + p_c_value + "'");
		}
		
		this.updateCurrencySettings(this.getValue(), this.getDescription(), this.getLocale(), p_c_value, this.getGroupSeparator(), this.getMinimumIntegerDigits(), this.getMinimumFractionDigits());
	}
	
	/**
	 * get group separator
	 * 
	 * @return char
	 */
	public char getGroupSeparator() {
		return this.c_groupSeparator;
	}

	/**
	 * set group separator
	 * 
	 * @param p_c_value						character for group separator
	 * @throws IllegalArgumentException		'$' is not allowed
	 */
	public void setGroupSeparator(char p_c_value) throws IllegalArgumentException {
		if (p_c_value == '$') {
			throw new IllegalArgumentException("Illegal group separator '" + p_c_value + "'");
		}
		
		this.updateCurrencySettings(this.getValue(), this.getDescription(), this.getLocale(), this.getDecimalSeparator(), p_c_value, this.getMinimumIntegerDigits(), this.getMinimumFractionDigits());
	}
	
	/**
	 * get minimum integer digits
	 * 
	 * @return int
	 */
	public int getMinimumIntegerDigits() {
		return this.i_minimumIntegerDigits;
	}
	
	/**
	 * set minimum integer digits
	 * 
	 * @param p_i_value						minimum integer digits value
	 * @throws IllegalArgumentException		must be a positive integer value
	 */
	public void setMinimumIntegerDigits(int p_i_value) throws IllegalArgumentException {
		if (p_i_value < 0) {
			throw new IllegalArgumentException("Minimunm intger digits '" + p_i_value + "' must be a positive integer number");
		}
		
		this.updateCurrencySettings(this.getValue(), this.getDescription(), this.getLocale(), this.getDecimalSeparator(), this.getGroupSeparator(), p_i_value, this.getMinimumFractionDigits());
	}
	
	/**
	 * get minimum fraction digits
	 * 
	 * @return int
	 */
	public int getMinimumFractionDigits() {
		return this.i_minimumFractionDigits;
	}
	
	/**
	 * set minimum fraction digits
	 * 
	 * @param p_i_value						minimum fraction digits value
	 * @throws IllegalArgumentException		must be a positive integer value
	 */
	public void setMinimumFractionDigits(int p_i_value) throws IllegalArgumentException {
		if (p_i_value < 0) {
			throw new IllegalArgumentException("Minimunm fraction digits '" + p_i_value + "' must be a positive integer number");
		}
		
		this.updateCurrencySettings(this.getValue(), this.getDescription(), this.getLocale(), this.getDecimalSeparator(), this.getGroupSeparator(), this.getMinimumIntegerDigits(), p_i_value);
	}
	
	/**
	 * currency symbol flag used for printing
	 * 
	 * @return boolean
	 */
	public boolean getUseCurrencySymbol() {
		return this.b_useCurrencySymbol;
	}
	
	/**
	 * set currency symbol flag used for printing
	 * 
	 * @param p_b_value			true, use currency symbol from Currency class constant, false - use standard enum caption
	 */
	public void setUseCurrencySymbol(boolean p_b_value) {
		this.b_useCurrencySymbol = p_b_value;
	}
	
	/* Methods */
	
	/**
	 * creates currency object with default value 0.0.
	 * 
	 * @param	p_e_description				currency description enumeration parameter
	 * @throws  IllegalArgumentException	MinimumIntegerDigits or MinimumFractionDigits are not a positive integer number
	 * @see		CurrencyDescription
	 */
	public Currency(CurrencyDescription p_e_description) throws IllegalArgumentException {
		this(new java.math.BigDecimal(0.0), p_e_description);
	}
	
	/**
	 * creates currency object.
	 * 
	 * @param	p_o_value					big decimal value of currency object
	 * @param	p_e_description				currency description enumeration parameter
	 * @throws  IllegalArgumentException	MinimumIntegerDigits or MinimumFractionDigits are not a positive integer number
	 * @see		CurrencyDescription
	 * @see		java.math.BigDecimal
	 */
	public Currency(java.math.BigDecimal p_o_value, CurrencyDescription p_e_description) throws IllegalArgumentException {
		this.b_useCurrencySymbol = false;
		this.updateCurrencySettings(p_o_value, p_e_description, java.util.Locale.getDefault(), ',', '.', 1, 2);
	}
	
	/**
	 * private update method to change all currency settings
	 * 
	 * @param p_o_value						big decimal value of currency object
	 * @param p_e_description				currency description enumeration parameter
	 * @param p_o_locale					currency locale setting
	 * @param p_c_decimalSeparator			character for decimal separation
	 * @param p_c_groupSeparator			character for group separation
	 * @param p_i_minimumIntegerDigits		integer value for minimum digits
	 * @param p_i_minimumFractionDigits		integer value for minimum fraction digits
	 * @throws IllegalArgumentException		MinimumIntegerDigits or MinimumFractionDigits are not a positive integer number
	 */
	private void updateCurrencySettings(
		java.math.BigDecimal p_o_value,
		CurrencyDescription p_e_description,
		java.util.Locale p_o_locale,
		char p_c_decimalSeparator,
		char p_c_groupSeparator,
		int p_i_minimumIntegerDigits,
		int p_i_minimumFractionDigits
	) throws IllegalArgumentException {
		this.o_bigDecimal = p_o_value;
		this.e_description = p_e_description;
		this.o_locale = p_o_locale;
		
		if (p_c_decimalSeparator != '$') {
			this.c_decimalSeparator = p_c_decimalSeparator;
		}
		
		if (p_c_groupSeparator != '$') {
			this.c_groupSeparator = p_c_groupSeparator;
		}
		
		this.o_decimalFormatSymbols = new java.text.DecimalFormatSymbols(this.getLocale());
		
		if (p_c_decimalSeparator != '$') {
			this.o_decimalFormatSymbols.setDecimalSeparator(this.c_decimalSeparator);
		}
		
		if (p_c_groupSeparator != '$') {
			this.o_decimalFormatSymbols.setGroupingSeparator(this.c_groupSeparator);
		}
		
		this.o_decimalFormat = new java.text.DecimalFormat("###,###,###,###,###.##", this.o_decimalFormatSymbols);
		this.i_minimumIntegerDigits = p_i_minimumIntegerDigits;
		this.i_minimumFractionDigits = p_i_minimumFractionDigits;
		this.o_decimalFormat.setMinimumIntegerDigits(this.i_minimumIntegerDigits);
		this.o_decimalFormat.setMinimumFractionDigits(this.i_minimumFractionDigits);
	}
	
	/**
	 * print currency instance as string with rounding mode HALF_EVEN
	 * 
	 * @return String
	 */
	public String toString() {
		return this.toString(java.math.RoundingMode.HALF_EVEN);
	}
	
	/**
	 * print currency instance as string
	 * 
	 * @param p_e_roundingMode			rounding mode that is used for printing
	 * @return String
	 * @see java.math.RoundingMode
	 */
	public String toString(java.math.RoundingMode p_e_roundingMode) {
		return this.o_decimalFormat.format(this.getValue().setScale(this.getMinimumFractionDigits(), p_e_roundingMode)) + " " + ( (this.b_useCurrencySymbol) ? net.forestany.forestj.lib.Currency.CurrencySymbols[this.getDescription().getCurrencyDescriptionIndex()] : this.getDescription().toString() );
    }
}
