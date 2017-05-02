package pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.bank.domain.Account;

public class BankAccountData {
    private String id;
    private String iban;
    private int balance;

    public BankAccountData() {
    }

    public BankAccountData(Account account) {
        this.id = account.getClient().getID();
        this.iban = account.getIBAN();
        this.balance = account.getBalance();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
