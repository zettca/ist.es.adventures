package pt.ulisboa.tecnico.softeng.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.bank.services.local.BankInterface;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankAccountData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankClientData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankOperationData;

@Controller
@RequestMapping(value = "/banks/{bankCode}/clients/{clid}/accounts/{iban}")
public class AccountController {
    private static Logger logger = LoggerFactory.getLogger(BanksController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String accountForm(Model model,
                              @PathVariable String bankCode, @PathVariable String clid, @PathVariable String iban) {
        logger.info("accountForm bank:{} clid:{} iban:{}", bankCode, clid, iban);

        BankData bankData = BankInterface.getBankDataByCode(bankCode);
        if (bankData == null) {
            model.addAttribute("error", "Error: there is no bank with code " + bankCode);
            model.addAttribute("bank", new BankData());
            return "banks";
        }

        BankClientData clientData = BankInterface.getClientDataById(bankData, clid);
        if (clientData == null) {
            model.addAttribute("error", "Error: there is no client with ID " + bankCode);
            model.addAttribute("bank", bankData);
            model.addAttribute("client", new BankClientData());
            return "bank";
        }

        BankAccountData accountData = BankInterface.getAccountDataByIban(clientData, iban);
        if (accountData == null) {
            model.addAttribute("error", "Error: there is no account with IBAN " + iban);
            model.addAttribute("bank", bankData);
            model.addAttribute("client", clientData);
            model.addAttribute("account", new BankAccountData());
            return "client";
        }

        model.addAttribute("bank", bankData);
        model.addAttribute("client", clientData);
        model.addAttribute("account", accountData);
        model.addAttribute("operation", new BankOperationData());
        model.addAttribute("operations", BankInterface.getAccountOperations(bankData, iban));
        return "account";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String accountSubmit(Model model, @PathVariable String bankCode, @PathVariable String clid,
                                @PathVariable String iban, @ModelAttribute BankOperationData operationData) {
        logger.info("accountSubmit bank:{} clid:{} IBAN:{} | type:{} value:{}", bankCode, clid, iban,
                operationData.getType(), operationData.getValue());

        try {
            BankInterface.createOperation(bankCode, operationData);
        } catch (BankException be) {
            model.addAttribute("error", "Error: it was not possible to process the operation");
            be.printStackTrace();
            BankData bankData = BankInterface.getBankDataByCode(bankCode);
            BankClientData clientData = BankInterface.getClientDataById(bankData, clid);
            model.addAttribute("bank", bankData);
            model.addAttribute("client", clientData);
            model.addAttribute("account", BankInterface.getAccountDataByIban(clientData, iban));
            model.addAttribute("operation", operationData);
            model.addAttribute("operations", BankInterface.getAccountOperations(bankData, iban));
            return "account";
        }

        return "redirect:/banks/" + bankCode + "/clients/" + clid + "/accounts/" + iban;
    }
}
