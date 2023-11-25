package test.b_Money;

import static org.junit.Assert.*;

import b_Money.Currency;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;

	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test //passed successfully
	public void testGetName() {
        assertEquals("SEK", Optional.of("SEK"),Optional.of(SEK.getName()));
        assertEquals("DKK", Optional.of("DKK"),Optional.of(DKK.getName()));
        assertEquals("EUR", Optional.of("EUR"),Optional.of(EUR.getName()));
	}

	@Test //passed successfully
	public void testGetRate() {
        assertEquals("0.15", Optional.of(0.15),Optional.of(SEK.getRate()));
        assertEquals("0.20", Optional.of(0.20),Optional.of(DKK.getRate()));
        assertEquals("1.5", Optional.of(1.5),Optional.of(EUR.getRate()));
	}

	@Test //passed successfully
	public void testSetRate() {
        SEK.setRate(0.20);
        assertEquals("0.20", Optional.of(0.20),Optional.of(SEK.getRate()));
        DKK.setRate(1.5);
        assertEquals("1.5", Optional.of(1.5),Optional.of(DKK.getRate()));
        EUR.setRate(0.15);
        assertEquals("0.15", Optional.of(0.15),Optional.of(EUR.getRate()));

    }

	@Test //passed successfully
	public void testGlobalValue() {
        assertEquals("1.5", Optional.of((int)1.5),Optional.of(SEK.universalValue(10)));
        assertEquals("2.0", Optional.of((int)2.0),Optional.of(DKK.universalValue(10)));
        assertEquals("15.0", Optional.of((int)15.0),Optional.of(EUR.universalValue(10)));
	}


	@Test //passed successfully
	public void testValueInThisCurrency() {
        assertEquals("OK", Optional.of((15/0.15)),Optional.of(SEK.valueInThisCurrency(10,EUR)));
        assertEquals("OK", Optional.of((15/0.20)),Optional.of(DKK.valueInThisCurrency(10,EUR)));
        assertEquals("OK", Optional.of((1.5/1.5)),Optional.of(EUR.valueInThisCurrency(10,SEK)));
	}


}
