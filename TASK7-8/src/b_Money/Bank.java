package b_Money;

import java.util.Hashtable;

public class Bank {
	private Hashtable<String, Account> accountlist = new Hashtable<String, Account>();
	private String name;
	private Currency currency;

	/**
	 * New Bank
	 * @param name Name of this bank
	 * @param currency Base currency of this bank (If this is a Swedish bank, this might be a currency class representing SEK)
	 */
	public Bank(String name, Currency currency) {
		this.name = name;
		this.currency = currency;
	}

	public int getSize(){return accountlist.size();}
	/**
	 * Get the name of this bank
	 * @return Name of this bank
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the Currency of this bank
	 * @return The Currency of this bank
	 */
	public Currency getCurrency() {
		return currency;
	}
	/**
	 * Get the Account in this bank
	 * @param id The id of the account
	 * @return The Currency of this bank
	 *  @throws AccountDoesNotExistException If the account does not exist
	 */
	public Account getAccount(String id)throws AccountDoesNotExistException{
		if(this.accountlist.get(id)==null) throw new AccountDoesNotExistException();
		return this.accountlist.get(id);
	}
	/**
	 * Open an account at this bank.
	 * @param accountid The ID of the account
	 * @throws AccountExistsException If the account already exists
	 */
	public void openAccount(String accountid) throws AccountExistsException {
		if (accountlist.containsKey(accountid)) {
			throw new AccountExistsException();
		}
		else {
			Account acc=new Account(accountid,this.getCurrency());
			accountlist.put(accountid,acc);
		}
	}

	/**
	 * Deposit money to an account
	 * @param accountid Account to deposit to
	 * @param money Money to deposit.
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	public void deposit(String accountid, Money money) throws AccountDoesNotExistException {
		if (!accountlist.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		}
		else {
			Account account = accountlist.get(accountid);
			account.deposit(money);
		}
	}

	/**
	 * Withdraw money from an account
	 * @param accountid Account to withdraw from
	 * @param money Money to withdraw
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	public void withdraw(String accountid, Money money) throws AccountDoesNotExistException {
		if (!accountlist.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		}
		else {
			Account account = accountlist.get(accountid);
			account.deposit(money);
		}
	}

	/**
	 * Get the balance of an account
	 * @param accountid Account to get balance from
	 * @return Balance of the account
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	public double getBalance(String accountid) throws AccountDoesNotExistException {
		if (!accountlist.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		}
		else {
			return accountlist.get(accountid).getBalance().getAmount();
		}
	}

	/**
	 * Transfer money between two accounts
	 * @param fromaccount Id of account to deduct from in this Bank
	 * @param tobank Bank where receiving account resides
	 * @param toaccount Id of receiving account
	 * @param amount Amount of Money to transfer
	 * @throws AccountDoesNotExistException If at least one of the accounts does not exist
	 */
	public void transfer(String fromaccount, Bank tobank, String toaccount, Money amount) throws AccountDoesNotExistException {
		if (!accountlist.containsKey(fromaccount) || !tobank.accountlist.containsKey(toaccount)) {
			throw new AccountDoesNotExistException();
		}
		else {
			accountlist.get(fromaccount).withdraw(amount);
			tobank.accountlist.get(toaccount).deposit(amount);
		}
	}

	/**
	 * Transfer money between two accounts on the same bank
	 * @param fromaccount Id of account to deduct from
	 * @param toaccount Id of receiving account
	 * @param amount Amount of Money to transfer
	 * @throws AccountDoesNotExistException If at least one of the accounts does not exist
	 */
	public void transfer(String fromaccount, String toaccount, Money amount) throws AccountDoesNotExistException {
		if(!this.accountlist.containsKey(fromaccount)||!this.accountlist.containsKey(toaccount)) throw new AccountDoesNotExistException();
		transfer(fromaccount, this, fromaccount, amount);
	}

	/**
	 * Add a timed payment
	 * @param accountid Id of account to deduct from in this Bank
	 * @param payid Id of timed payment
	 * @param interval Number of ticks between payments
	 * @param next Number of ticks till first payment
	 * @param amount Amount of Money to transfer each payment
	 * @param tobank Bank where receiving account resides
	 * @param toaccount Id of receiving account
	 * @throws AccountDoesNotExistException If at least one of the accounts does not exist
	 */
	public void addTimedPayment(String accountid, String payid, Integer interval, Integer next, Money amount, Bank tobank, String toaccount) throws AccountDoesNotExistException {
		if (!accountlist.containsKey(accountid) || !tobank.accountlist.containsKey(toaccount)) {
			throw new AccountDoesNotExistException();
		}
		Account account = accountlist.get(accountid);
		account.addTimedPayment(payid, interval, next, amount, tobank, toaccount);
	}

	/**
	 * Remove a timed payment
	 * @param accountid Id of account to remove timed payment from
	 * @param id Id of timed payment
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	public void removeTimedPayment(String accountid, String id) throws AccountDoesNotExistException{
		if (!accountlist.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		}
		Account account = accountlist.get(accountid);
		account.removeTimedPayment(id);
	}

	/**
	 * A time unit passes in the system
	 */
	public void tick(){
		for (Account account : accountlist.values()) {
			account.tick();
		}
	}
}
