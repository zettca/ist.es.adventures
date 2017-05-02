package pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.bank.domain.Operation;

import java.util.ArrayList;
import java.util.List;

public class BankData {
    public enum CopyDepth {
        SHALLOW, ACCOUNTS, CLIENTS, OPERATIONS
    }

    private String name;
    private String code;
    private List<BankClientData> clients = new ArrayList<>();
    private List<BankAccountData> accounts = new ArrayList<>();
    private List<BankOperationData> operations = new ArrayList<>();

    public BankData() {
    }

    public BankData(Bank bank, CopyDepth depth) {
        this.name = bank.getName();
        this.code = bank.getCode();

        switch (depth) {
            case CLIENTS:
                for (Client client : bank.getClientSet()) {
                    this.clients.add(new BankClientData(client));
                }
                break;
            case ACCOUNTS:
                for (Account account : bank.getAccountSet()) {
                    this.accounts.add(new BankAccountData(account));
                }
                break;
            case OPERATIONS:
                for (Operation operation : bank.getOperationSet()) {
                    this.operations.add(new BankOperationData(operation));
                }
                break;
            case SHALLOW:
                break;
            default:
                break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<BankClientData> getClients() {
        return clients;
    }

    public void setClients(List<BankClientData> clients) {
        this.clients = clients;
    }

    public List<BankAccountData> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccountData> accounts) {
        this.accounts = accounts;
    }

    public List<BankOperationData> getOperations() {
        return operations;
    }

    public void setOperations(List<BankOperationData> operations) {
        this.operations = operations;
    }
}
