package pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.bank.domain.Client;

public class BankClientData {

    private String code;
    private String name;

    public BankClientData() {
    }

    public BankClientData(Client client) {
        this.code = client.getBank().getCode();
        this.name = client.getName();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
