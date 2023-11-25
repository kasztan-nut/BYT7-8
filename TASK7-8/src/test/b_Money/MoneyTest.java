package test.b_Money;

import static org.junit.Assert.*;

import b_Money.Currency;
import b_Money.Money;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

    @Test //passed successfully
	public void testGetAmount() {
        assertEquals("100.00", Optional.of(10000),Optional.of(SEK100.getAmount()));
        assertEquals("10.00", Optional.of(1000),Optional.of(EUR10.getAmount()));
        assertEquals("200.00", Optional.of(20000),Optional.of(SEK200.getAmount()));
        assertEquals("20.00", Optional.of(2000),Optional.of(EUR20.getAmount()));
        assertEquals("0.00", Optional.of(0),Optional.of(SEK0.getAmount()));
        assertEquals("0.00", Optional.of(0),Optional.of(EUR0.getAmount()));
        assertEquals("-100.00", Optional.of(-10000),Optional.of(SEKn100.getAmount()));
	}

    @Test //passed successfully
	public void testGetCurrency() {
        assertEquals("SEK", Optional.of(SEK),Optional.of(SEK100.getCurrency()));
        assertEquals("EUR", Optional.of(EUR),Optional.of(EUR10.getCurrency()));
        assertEquals("SEK", Optional.of(SEK),Optional.of(SEK200.getCurrency()));
        assertEquals("EUR", Optional.of(EUR),Optional.of(EUR20.getCurrency()));
        assertEquals("SEK", Optional.of(SEK),Optional.of(SEK0.getCurrency()));
        assertEquals("EUR", Optional.of(EUR),Optional.of(EUR0.getCurrency()));
        assertEquals("SEK", Optional.of(SEK),Optional.of(SEKn100.getCurrency()));
	}

    @Test //passed successfully
	public void testToString() {
        assertEquals("100.00 SEK", Optional.of(10000+" "+SEK.getName()),Optional.of(SEK100.toString()));
        assertEquals("10.00 EUR", Optional.of(1000+" "+EUR.getName()),Optional.of(EUR10.toString()));
        assertEquals("200.00SEK", Optional.of(20000+" "+SEK.getName()),Optional.of(SEK200.toString()));
        assertEquals("20.00 EUR", Optional.of(2000+" "+EUR.getName()),Optional.of(EUR20.toString()));
        assertEquals("0.00 SEK", Optional.of(0+" "+SEK.getName()),Optional.of(SEK0.toString()));
        assertEquals("0.00 EUR", Optional.of(0+" "+EUR.getName()),Optional.of(EUR0.toString()));
        assertEquals("-100.00 SEK", Optional.of(-10000+" "+SEK.getName()),Optional.of(SEKn100.toString()));
	}

    @Test //passed successfully
	public void testGlobalValue() {
        assertEquals("OK", Optional.of(1500),Optional.of(SEK100.universalValue()));
        assertEquals("OK", Optional.of(1500),Optional.of(EUR10.universalValue()));
        assertEquals("OK", Optional.of(3000),Optional.of(SEK200.universalValue()));
        assertEquals("OK", Optional.of(3000),Optional.of(EUR20.universalValue()));
        assertEquals("OK", Optional.of(0),Optional.of(SEK0.universalValue()));
        assertEquals("OK", Optional.of(0),Optional.of(EUR0.universalValue()));
        assertEquals("OK", Optional.of(-1500),Optional.of(SEKn100.universalValue()));
    }

    @Test //passed successfully
	public void testEqualsMoney() {
        assertFalse("OK", (SEK100.equals(EUR0)));
        assertFalse("OK", (EUR10.equals(EUR0)));
        assertFalse("OK", (SEK200.equals(EUR0)));
        assertFalse("OK", (EUR20.equals(EUR0)));
        assertTrue("OK", (SEK0.equals(EUR0)));
        assertTrue("OK", (EUR0.equals(EUR0)));
        assertFalse("OK", (SEKn100.equals(EUR0)));
	}

    @Test //passed successfully
	public void testAdd() {
        assertEquals("OK", Optional.of(3000),Optional.of(SEK100.add(EUR10).universalValue()));
        assertEquals("OK", Optional.of(3000),Optional.of(EUR10.add(EUR10).universalValue()));
        assertEquals("OK", Optional.of(4500),Optional.of(SEK200.add(EUR10).universalValue()));
        assertEquals("OK", Optional.of(4500),Optional.of(EUR20.add(EUR10).universalValue()));
        assertEquals("OK", Optional.of(1500),Optional.of(SEK0.add(EUR10).universalValue()));
        assertEquals("OK", Optional.of(1500),Optional.of(EUR0.add(EUR10).universalValue()));
        assertEquals("OK", Optional.of(0),Optional.of(SEKn100.add(EUR10).universalValue()));
	}

    @Test //passed successfully
	public void testSub() {
        assertEquals("OK", Optional.of(0),Optional.of(SEK100.sub(EUR10).universalValue()));
        assertEquals("OK", Optional.of(0),Optional.of(EUR10.sub(EUR10).universalValue()));
        assertEquals("OK", Optional.of(1500),Optional.of(SEK200.sub(EUR10).universalValue()));
        assertEquals("OK", Optional.of(1500),Optional.of(EUR20.sub(EUR10).universalValue()));
        assertEquals("OK", Optional.of(-1500),Optional.of(SEK0.sub(EUR10).universalValue()));
        assertEquals("OK", Optional.of(-1500),Optional.of(EUR0.sub(EUR10).universalValue()));
        assertEquals("OK", Optional.of(-3000),Optional.of(SEKn100.sub(EUR10).universalValue()));
	}

    @Test //passed successfully
	public void testIsZero() {
        assertFalse("OK",(SEK100.isZero()));
        assertFalse("OK", (EUR10.isZero()));
        assertFalse("OK", SEK200.isZero());
        assertFalse("OK", (EUR20.isZero()));
        assertTrue("OK",(SEK0.isZero()));
        assertTrue("OK", (EUR0.isZero()));
        assertFalse("OK", (SEKn100.isZero()));
	}

    @Test //passed successfully
	public void testNegate() {
        assertEquals("OK", Optional.of(-1),Optional.of(SEK100.negate().getAmount()/SEK100.getAmount()));
        assertEquals("OK", Optional.of(-1),Optional.of(EUR10.negate().getAmount()/EUR10.getAmount()));
        assertEquals("OK", Optional.of(-1),Optional.of(SEK200.negate().getAmount()/SEK200.getAmount()));
        assertEquals("OK", Optional.of(-1),Optional.of(EUR20.negate().getAmount()/EUR20.getAmount()));
        assertEquals("OK", Optional.of(-0),Optional.of(SEK0.negate().getAmount()));
        assertEquals("OK", Optional.of(-0),Optional.of(EUR0.negate().getAmount()));
        assertEquals("OK", Optional.of(-1),Optional.of(SEKn100.negate().getAmount()/SEKn100.getAmount()));

    }

    @Test //passed successfully
	public void testCompareTo() {
        assertEquals("OK", Optional.of(0),Optional.of(SEK100.compareTo(EUR10)));
        assertEquals("OK", Optional.of(0),Optional.of(EUR10.compareTo(EUR10)));
        assertEquals("OK", Optional.of(1),Optional.of(SEK200.compareTo(EUR10)));
        assertEquals("OK", Optional.of(1),Optional.of(EUR20.compareTo(EUR10)));
        assertEquals("OK", Optional.of(-1),Optional.of(SEK0.compareTo(EUR10)));
        assertEquals("OK", Optional.of(-1),Optional.of(EUR0.compareTo(EUR10)));
        assertEquals("OK", Optional.of(-1),Optional.of(SEKn100.compareTo(EUR10)));

	}
}
