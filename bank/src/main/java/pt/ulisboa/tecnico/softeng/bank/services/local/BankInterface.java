package pt.ulisboa.tecnico.softeng.bank.services.local;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.bank.domain.Operation;
import pt.ulisboa.tecnico.softeng.bank.domain.Operation.Type;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankAccountData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankClientData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankOperationData;

import java.util.ArrayList;
import java.util.List;

public class BankInterface {

	@Atomic(mode = TxMode.READ)
	public static List<BankData> getBanks() {
		List<BankData> banks = new ArrayList<>();
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			banks.add(new BankData(bank));
		}
		return banks;
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createBank(BankData bankData) {
		new Bank(bankData.getName(), bankData.getCode());
	}

	@Atomic(mode = TxMode.WRITE)
	public static void deleteBank(String bankCode) {
		getBankByCode(bankCode).delete();
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createClient(String bankCode, BankClientData clientData) {
		new Client(getBankByCode(bankCode), clientData.getName());
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createAccount(String bankCode, String clid) {
		Bank bank = getBankByCode(bankCode);
		new Account(bank, getClientByID(bank, clid));
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createOperation(String bankCode, BankOperationData operationData) {
		Account account = getAccountByIBAN(getBankByCode(bankCode), operationData.getIban());
		if (Type.valueOf(operationData.getType()).equals(Type.DEPOSIT)) {
			account.deposit(operationData.getValue());
		} else if (Type.valueOf(operationData.getType()).equals(Type.WITHDRAW)) {
			account.withdraw(operationData.getValue());
		}
	}

	@Atomic(mode = TxMode.READ)
	public static BankData getBankDataByCode(String bankCode) {
		Bank bank = getBankByCode(bankCode);
		return (bank != null) ? new BankData(bank) : null;
	}

	public static BankClientData getClientDataById(BankData bankData, String clid) {
		for (BankClientData clientData : bankData.getClients()) {
			if (clientData.getId().equals(clid)) {
				return clientData;
			}
		}
		return null;
	}

	public static BankAccountData getAccountDataByIban(BankClientData clientData, String iban) {
		for (BankAccountData accountData : clientData.getAccounts()) {
			if (accountData.getIban().equals(iban)) {
				return accountData;
			}
		}
		return null;
	}

	public static List<BankOperationData> getAccountOperations(BankData bankData, String iban) {
		List<BankOperationData> operations = new ArrayList<>();
		for (BankOperationData op : bankData.getOperations()) {
			if (op.getIban().equals(iban)) {
				operations.add(op);
			}
		}
		return operations;
	}

	@Atomic(mode = TxMode.WRITE)
	public static String processPayment(String IBAN, int amount) {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			if (bank.getAccount(IBAN) != null) {
				return bank.getAccount(IBAN).withdraw(amount);
			}
		}
		throw new BankException();
	}

	@Atomic(mode = TxMode.WRITE)
	public static String cancelPayment(String paymentConfirmation) {
		Operation operation = getOperationByReference(paymentConfirmation);
		if (operation != null) {
			return operation.revert();
		}
		throw new BankException();
	}

	@Atomic(mode = TxMode.READ)
	public static BankOperationData getOperationData(String reference) {
		Operation operation = getOperationByReference(reference);
		if (operation != null) {
			return new BankOperationData(operation);
		}
		throw new BankException();
	}

	private static Operation getOperationByReference(String reference) {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			Operation operation = bank.getOperation(reference);
			if (operation != null) {
				return operation;
			}
		}
		return null;
	}

	private static Bank getBankByCode(String code) {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			if (bank.getCode().equals(code)) {
				return bank;
			}
		}
		return null;
	}

	private static Client getClientByID(Bank bank, String id) {
		for (Client client : bank.getClientSet()) {
			if (client.getID().equals(id)) {
				return client;
			}
		}
		return null;
	}

	@Atomic(mode = TxMode.READ)
	private static Account getAccountByIBAN(Bank bank, String iban) {
		for (Account account : bank.getAccountSet()) {
			if (account.getIBAN().equals(iban)) {
				return account;
			}
		}
		return null;
	}
}
