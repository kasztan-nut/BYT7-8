package test.b_Money;

import static org.junit.Assert.*;

import b_Money.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test //passed successfully
	public void testGetName() {
        assertEquals("OK", Optional.of("SweBank"),Optional.of(SweBank.getName()));
        assertEquals("OK", Optional.of("Nordea"),Optional.of(Nordea.getName()));
        assertEquals("OK", Optional.of("DanskeBank"),Optional.of(DanskeBank.getName()));

    }

	@Test //passed successfully
	public void testGetCurrency() {
        assertEquals("OK", Optional.of(SEK),Optional.of(SweBank.getCurrency()));
        assertEquals("OK", Optional.of(SEK),Optional.of(Nordea.getCurrency()));
        assertEquals("OK", Optional.of(DKK),Optional.of(DanskeBank.getCurrency()));

    }

	@Test //passed successfully
	public void testOpenAccount() throws AccountExistsException{
        SweBank.openAccount("asd");
        assertThrows(AccountExistsException.class,()->{
            SweBank.openAccount("asd");
        });
        Nordea.openAccount("asd");
        assertEquals("OK",2,Nordea.getSize());
        DanskeBank.openAccount("asd");
        assertEquals("OK",2,DanskeBank.getSize());
    }

	@Test //passed successfully
	public void testDeposit() throws AccountDoesNotExistException {
		assertThrows(AccountDoesNotExistException.class,()->{
			SweBank.deposit("asd", new Money(1000,SEK));
		});
		Nordea.deposit("Bob", new Money(1000,SEK));
		assertTrue("OK",Nordea.getBalance("Bob")==1000);

	}

	@Test //passed successfully
	public void testWithdraw() throws AccountDoesNotExistException {

		assertThrows(AccountDoesNotExistException.class,()->{
			Nordea.withdraw("asd", new Money(1000,SEK));
		});
		SweBank.deposit("Bob", new Money(1000,SEK));
		SweBank.withdraw("Bob", new Money(1000,SEK));
		assertTrue("OK",Nordea.getBalance("Bob")==0);

	}

	@Test //passed successfully
	public void testGetBalance() throws AccountDoesNotExistException {

		assertThrows(AccountDoesNotExistException.class,()->{
			Nordea.getBalance("asd");
		});
		SweBank.deposit("Bob", new Money(1000,SEK));
		SweBank.withdraw("Bob", new Money(1000,SEK));
		assertTrue("OK",Nordea.getBalance("Bob")==0);
	}

	@Test //passed successfully
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(1000,SEK));
		SweBank.transfer("Bob",Nordea,"Bob",new Money(1000,SEK));
		assertTrue("OK", SweBank.getBalance("Bob")==0&Nordea.getBalance("Bob")==1000);
		assertThrows(AccountDoesNotExistException.class,()->{
			SweBank.deposit("Bob", new Money(1000,SEK));
			SweBank.transfer("Bob",Nordea,"asd",new Money(1000,SEK));
		});
		assertThrows(AccountDoesNotExistException.class,()->{
			SweBank.transfer("asd","Bob",new Money(1000,SEK));
		});
		SweBank.deposit("Bob", new Money(1000,SEK));
		SweBank.transfer("Bob","Ulrika",new Money(1000,SEK));
		assertTrue("OK", SweBank.getBalance("Bob")==0&SweBank.getBalance("Ulrika")==1000);
	}

	@Test //passed successfully
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment("Bob","1",10000,20000,new Money(1000,SEK),Nordea,"Bob");
		assertTrue("OK", SweBank.getAccount("Bob").timedPaymentExists("1"));
		SweBank.removeTimedPayment("Bob","1");
		assertFalse("OK", SweBank.getAccount("Bob").timedPaymentExists("1"));
		assertThrows(AccountDoesNotExistException.class,()->{
			SweBank.addTimedPayment("Bob","1",1,1,new Money(1000,SEK),SweBank,"aaaaaaa");
			SweBank.tick();
		});
		assertThrows(AccountDoesNotExistException.class,()->{
			SweBank.addTimedPayment("aaaa","11",1,1,new Money(1000,SEK),Nordea,"Bob");
		});
		SweBank.deposit("Bob", new Money(1000000, SEK));
		SweBank.addTimedPayment("Bob", "1", 1, 1, new Money(1000000, SEK), SweBank, "Ulrika");
		SweBank.tick();

		assertTrue("OK", SweBank.getAccount("Bob").getBalance().getAmount()==0.0);

	}
}
