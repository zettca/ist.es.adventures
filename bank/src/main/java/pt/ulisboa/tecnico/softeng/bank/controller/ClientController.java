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
@Controller
@RequestMapping(value = "/banks/{bankCode}/clients/{clid}")
public class ClientController {
    private static Logger logger = LoggerFactory.getLogger(BanksController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String clientForm(Model model, @PathVariable String bankCode, @PathVariable String clid) {
        logger.info("clientForm bank:{} clid:{}", bankCode, clid);

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

        model.addAttribute("bank", bankData);
        model.addAttribute("client", clientData);
        model.addAttribute("account", new BankAccountData());
        model.addAttribute("accounts", clientData.getAccounts());
        return "client";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String clientSubmit(Model model, @PathVariable String bankCode, @PathVariable String clid,
                               @ModelAttribute BankAccountData accountData) {
        logger.info("clientSubmit bank:{} clid:{} | IBAN:{}", bankCode, clid, accountData.getIban());

        try {
            BankInterface.createAccount(bankCode, clid);
        } catch (BankException be) {
            model.addAttribute("error", "Error: it was not possible to create the account");
            be.printStackTrace();
            BankData bankData = BankInterface.getBankDataByCode(bankCode);
            model.addAttribute("bank", bankData);
            model.addAttribute("client", BankInterface.getClientDataById(bankData, clid));
            model.addAttribute("account", accountData);
            return "client";
        }

        return "redirect:/banks/" + bankCode + "/clients/" + clid;
    }
}
