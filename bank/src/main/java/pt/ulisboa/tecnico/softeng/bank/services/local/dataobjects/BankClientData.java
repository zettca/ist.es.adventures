package pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;

import java.util.ArrayList;
import java.util.List;

public class BankClientData {
    private String id;
    private String name;
    private List<BankAccountData> accounts = new ArrayList<>();

    public BankClientData() {
    }

    public BankClientData(Client client) {
        this.id = client.getID();
        this.name = client.getName();

        for (Account account : client.getAccountSet()) {
            accounts.add(new BankAccountData(account));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BankAccountData> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccountData> accounts) {
        this.accounts = accounts;
    }
}
