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
@RequestMapping(value = "/banks/{bankCode}")
public class ClientController {
    private static Logger logger = LoggerFactory.getLogger(BanksController.class);

   
    @RequestMapping(method = RequestMethod.POST)
    public String accountSubmit(Model model, @PathVariable String bankCode, BankOperationData type, BankAccountData accountData, BankOperationData value) {
        logger.info("accountSubmit operationType:{} accountName:{} value:{}", type.getType(), accountData.getId(), value.getValue());

        try {
            BankInterface.createAccount(bankCode, accountData);
        } catch (BankException be) {
            model.addAttribute("error", "Error: it was not possible to create the account");
            be.printStackTrace();
            model.addAttribute("account", accountData);
           // fix:  model.addAttribute("client", BankInterface.getClientByID(bankCode, accountData.getId()));
            return "client";
        }

        return "redirect:/banks/" + bankCode;
    }
}
