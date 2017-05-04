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
@RequestMapping(value = "/banks/{bankCode}/clients/{id}")
public class ClientController {
    private static Logger logger = LoggerFactory.getLogger(BanksController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String clientForm(Model model, @PathVariable String bankCode) {
        logger.info("accountForm account:{}", bankCode);

        BankData bankData = BankInterface.getBankDataByCode(bankCode, BankData.CopyDepth.CLIENTS);

        if (bankData != null) {
            model.addAttribute("bank", bankData);
            model.addAttribute("account", new BankAccountData());
            model.addAttribute("accounts", bankData.getAccounts());
            return "bank";
        } else {
            model.addAttribute("error", "Error: there is no bank with code " + bankCode);
            model.addAttribute("bank", new BankData());
            model.addAttribute("client", new BankClientData());
            model.addAttribute("account", new BankAccountData());
            return "banks";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String accountSubmit(Model model, @PathVariable String bankCode, @ModelAttribute BankClientData clientData) {
        logger.info("accountSubmit bankCode:{} client:{}", bankCode, clientData.getName());

        try {
            BankInterface.createAccount(bankCode, BankAccountData());
        } catch (BankException be) {
            model.addAttribute("error", "Error: it was not possible to create the account");
            be.printStackTrace();
            model.addAttribute("id", BankAccountData().getId());
            model.addAttribute("iban", BankAccountData().getIban());
            model.addAttribute("balance", BankAccountData().getBalance());
            return "bank";
        }

        return "redirect:/banks/" + bankCode;
    }

	private BankAccountData BankAccountData() {
		// TODO Auto-generated method stub
		return null;
	}
}
