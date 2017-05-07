package pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.bank.domain.Client;

public class BankClientData {
    private String id;
    private String name;
   
    public BankClientData() {
    }

    public BankClientData(Client client) {
        this.id = client.getID();
        this.name = client.getName();
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
}
