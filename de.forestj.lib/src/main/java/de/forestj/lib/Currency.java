package de.forestj.lib;

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
		EUR(0), USD(1), GBP(2), YEN(3);
		
		private int CurrencyDescriptionIndex;
	    
		CurrencyDescription(int p_i_value) {
	        this.CurrencyDescriptionIndex = p_i_value;
	    }
	 
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
	
	public java.math.BigDecimal getValue() {
		return o_bigDecimal;
	}

	public void setValue(java.math.BigDecimal p_o_value) {
		this.updateCurrencySettings(p_o_value, this.getDescription(), this.getLocale(), this.getDecimalSeparator(), this.getGroupSeparator(), this.getMinimumIntegerDigits(), this.getMinimumFractionDigits());
	}

	public CurrencyDescription getDescription() {
		return this.e_description;
	}

	public void setDescription(CurrencyDescription p_e_description) throws IllegalArgumentException {
		if (p_e_description == null) {
			throw new IllegalArgumentException("Description parameter is null.");
		}
		
		this.updateCurrencySettings(this.getValue(), p_e_description, this.getLocale(), this.getDecimalSeparator(), this.getGroupSeparator(), this.getMinimumIntegerDigits(), this.getMinimumFractionDigits());
	}
	
	public java.util.Locale getLocale() {
		return this.o_locale;
	}

	public void setLocale(java.util.Locale p_o_value) {
		this.updateCurrencySettings(this.getValue(), this.getDescription(), p_o_value, '$', '$', this.getMinimumIntegerDigits(), this.getMinimumFractionDigits());
	}
	
	public char getDecimalSeparator() {
		return this.c_decimalSeparator;
	}

	public void setDecimalSeparator(char p_c_value) throws IllegalArgumentException {
		if (p_c_value == '$') {
			throw new IllegalArgumentException("Illegal decimal separator '" + p_c_value + "'");
		}
		
		this.updateCurrencySettings(this.getValue(), this.getDescription(), this.getLocale(), p_c_value, this.getGroupSeparator(), this.getMinimumIntegerDigits(), this.getMinimumFractionDigits());
	}
	
	public char getGroupSeparator() {
		return this.c_groupSeparator;
	}

	public void setGroupSeparator(char p_c_value) throws IllegalArgumentException {
		if (p_c_value == '$') {
			throw new IllegalArgumentException("Illegal group separator '" + p_c_value + "'");
		}
		
		this.updateCurrencySettings(this.getValue(), this.getDescription(), this.getLocale(), this.getDecimalSeparator(), p_c_value, this.getMinimumIntegerDigits(), this.getMinimumFractionDigits());
	}
	
	public int getMinimumIntegerDigits() {
		return this.i_minimumIntegerDigits;
	}
	
	public void setMinimumIntegerDigits(int p_i_value) throws IllegalArgumentException {
		if (p_i_value < 0) {
			throw new IllegalArgumentException("Minimunm intger digits '" + p_i_value + "' must be a positive integer number");
		}
		
		this.updateCurrencySettings(this.getValue(), this.getDescription(), this.getLocale(), this.getDecimalSeparator(), this.getGroupSeparator(), p_i_value, this.getMinimumFractionDigits());
	}
	
	public int getMinimumFractionDigits() {
		return this.i_minimumFractionDigits;
	}
	
	public void setMinimumFractionDigits(int p_i_value) throws IllegalArgumentException {
		if (p_i_value < 0) {
			throw new IllegalArgumentException("Minimunm fraction digits '" + p_i_value + "' must be a positive integer number");
		}
		
		this.updateCurrencySettings(this.getValue(), this.getDescription(), this.getLocale(), this.getDecimalSeparator(), this.getGroupSeparator(), this.getMinimumIntegerDigits(), p_i_value);
	}
	
	public boolean getUseCurrencySymbol() {
		return this.b_useCurrencySymbol;
	}
	
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
	 * @param	p_d_value					double value of currency object
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
	 * @param p_o_value						double value of currency object
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
	
	public String toString() {
		return this.toString(java.math.RoundingMode.HALF_EVEN);
	}
	
	public String toString(java.math.RoundingMode p_e_roundingMode) {
		return this.o_decimalFormat.format(this.getValue().setScale(this.getMinimumFractionDigits(), p_e_roundingMode)) + " " + ( (this.b_useCurrencySymbol) ? de.forestj.lib.Currency.CurrencySymbols[this.getDescription().getCurrencyDescriptionIndex()] : this.getDescription().toString() );
    }
}
