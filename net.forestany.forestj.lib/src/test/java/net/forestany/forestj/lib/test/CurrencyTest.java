package net.forestany.forestj.lib.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CurrencyTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testCurrency() {
		net.forestany.forestj.lib.Currency o_currency = new net.forestany.forestj.lib.Currency(net.forestany.forestj.lib.Currency.CurrencyDescription.EUR);
		assertTrue(
				o_currency.toString().contentEquals("0,00 EUR"),				
				o_currency.toString() + " != '0,00 EUR'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(12.34), net.forestany.forestj.lib.Currency.CurrencyDescription.EUR);
		assertTrue(
				o_currency.toString().contentEquals("12,34 EUR"),
				o_currency.toString() + " != '12,34 EUR'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(12.3), net.forestany.forestj.lib.Currency.CurrencyDescription.EUR);
		assertTrue(
				o_currency.toString().contentEquals("12,30 EUR"),
				o_currency.toString() + " != '12,30 EUR'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(.3), net.forestany.forestj.lib.Currency.CurrencyDescription.EUR);
		assertTrue(
				o_currency.toString().contentEquals("0,30 EUR"),
				o_currency.toString() + " != '0,30 EUR'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(0.3), net.forestany.forestj.lib.Currency.CurrencyDescription.EUR);
		assertTrue(
				o_currency.toString().contentEquals("0,30 EUR"),
				o_currency.toString() + " != '0,30 EUR'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(.34), net.forestany.forestj.lib.Currency.CurrencyDescription.EUR);
		assertTrue(
				o_currency.toString().contentEquals("0,34 EUR"),
				o_currency.toString() + " != '0,34 EUR'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(0.34), net.forestany.forestj.lib.Currency.CurrencyDescription.EUR);
		assertTrue(
				o_currency.toString().contentEquals("0,34 EUR"),
				o_currency.toString() + " != '0,34 EUR'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(1234.98765), net.forestany.forestj.lib.Currency.CurrencyDescription.EUR);
		assertTrue(
				o_currency.toString().contentEquals("1.234,99 EUR"),
				o_currency.toString() + " != '1.234,99 EUR'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(302020101234.56789), net.forestany.forestj.lib.Currency.CurrencyDescription.EUR);
		assertTrue(
				o_currency.toString().contentEquals("302.020.101.234,57 EUR"),
				o_currency.toString() + " != '302.020.101.234,57 EUR'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(12.34), net.forestany.forestj.lib.Currency.CurrencyDescription.GBP);
		assertTrue(
				o_currency.toString().contentEquals("12,34 GBP"),
				o_currency.toString() + " != '12,34 GBP'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(9834.12), net.forestany.forestj.lib.Currency.CurrencyDescription.GBP);
		o_currency.setLocale(java.util.Locale.UK);
		assertTrue(
				o_currency.toString().contentEquals("9,834.12 GBP"),
				o_currency.toString() + " != '9,834.12 GBP'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(1112.34), net.forestany.forestj.lib.Currency.CurrencyDescription.USD);
		o_currency.setLocale(java.util.Locale.US);
		assertTrue(
				o_currency.toString().contentEquals("1,112.34 USD"),
				o_currency.toString() + " != '1,112.34 USD'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(12.34), net.forestany.forestj.lib.Currency.CurrencyDescription.USD);
		o_currency.setDecimalSeparator('.');
		o_currency.setGroupSeparator(',');
		assertTrue(
				o_currency.toString().contentEquals("12.34 USD"),
				o_currency.toString() + " != '12.34 USD'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(1234.98765), net.forestany.forestj.lib.Currency.CurrencyDescription.USD);
		o_currency.setDecimalSeparator('.');
		o_currency.setGroupSeparator(',');
		assertTrue(
				o_currency.toString().contentEquals("1,234.99 USD"),
				o_currency.toString() + " != '1,234.99 USD'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(302020101234.56789), net.forestany.forestj.lib.Currency.CurrencyDescription.USD);
		o_currency.setDecimalSeparator('.');
		o_currency.setGroupSeparator('_');
		assertTrue(
				o_currency.toString().contentEquals("302_020_101_234.57 USD"),
				o_currency.toString() + " != '302_020_101_234.57 USD'"
		);
		
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(12.34), net.forestany.forestj.lib.Currency.CurrencyDescription.YEN);
		assertTrue(
				o_currency.toString().contentEquals("12,34 YEN"),
				o_currency.toString() + " != '12,34 YEN'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(12.34), net.forestany.forestj.lib.Currency.CurrencyDescription.YEN);
		o_currency.setMinimumIntegerDigits(6);
		assertTrue(
				o_currency.toString().contentEquals("000.012,34 YEN"),
				o_currency.toString() + " != '000.012,34 YEN'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(12.12345678909), net.forestany.forestj.lib.Currency.CurrencyDescription.YEN);
		o_currency.setMinimumIntegerDigits(6);
		o_currency.setMinimumFractionDigits(10);
		assertTrue(
				o_currency.toString().contentEquals("000.012,1234567891 YEN"),
				o_currency.toString() + " != '000.012,1234567891 YEN'"
		);
		
		o_currency = new net.forestany.forestj.lib.Currency(new java.math.BigDecimal(12.34), net.forestany.forestj.lib.Currency.CurrencyDescription.USD);
		o_currency.setDecimalSeparator('.');
		o_currency.setGroupSeparator(',');
		o_currency.setUseCurrencySymbol(true);
		assertTrue(
				o_currency.toString().contentEquals("12.34 $"),
				o_currency.toString() + " != '12.34 $'"
		);
    }
}
