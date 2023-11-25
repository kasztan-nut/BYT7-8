package test.b_Money;

import b_Money.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test //passed successfully
	public void testAddRemoveTimedPayment() throws AccountDoesNotExistException{
		testAccount.addTimedPayment("as",10000,1000000,new Money(100,SEK),SweBank,"Alice");
		assertEquals("OK",1,testAccount.getSize());
		testAccount.removeTimedPayment("as");
		assertEquals("OK",0,testAccount.getSize());

	}

	@Test //passed successfully
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("as", 1, 1, new Money(10000000, SEK), SweBank, "Alice");
		assertTrue("OK", testAccount.timedPaymentExists("as"));
		testAccount.tick();
		assertEquals("OK", Optional.of(0),Optional.of(testAccount.getBalance().getAmount()));
	}

	@Test //passed successfully
	public void testAddWithdraw() {
		testAccount.withdraw(new Money(10000000,SEK));
		assertTrue("OK",testAccount.getBalance().getAmount()<10000000);

	}

	@Test //passed successfully
	public void testGetBalance() {
		System.out.println(testAccount.getBalance());
		assertTrue("OK", testAccount.getBalance().getAmount()==10000000&&testAccount.getBalance().getCurrency()==SEK);
	}
}
