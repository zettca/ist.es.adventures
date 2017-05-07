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
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankClientData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankOperationData;

@Controller
@RequestMapping(value = "/banks/{bankCode}")
public class BankController {
    private static Logger logger = LoggerFactory.getLogger(BanksController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String bankForm(Model model, @PathVariable String bankCode) {
        logger.info("bankForm bank:{}", bankCode);

        BankData bankData = BankInterface.getBankDataByCode(bankCode);
        if (bankData == null) {
            model.addAttribute("error", "Error: there is no bank with code " + bankCode);
            model.addAttribute("bank", new BankData());
            model.addAttribute("client", new BankClientData());
            model.addAttribute("banks", BankInterface.getBanks());
            return "banks";
        }

        model.addAttribute("bank", bankData);
        model.addAttribute("client", new BankClientData());
        model.addAttribute("clients", bankData.getClients());
        model.addAttribute("operation", new BankOperationData());
        model.addAttribute("operations", bankData.getOperations());
        return "bank";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String bankSubmit(Model model, @PathVariable String bankCode, @ModelAttribute BankClientData clientData) {
        logger.info("bankSubmit bankCode:{} clientName:{}", bankCode, clientData.getName());

        try {
            BankInterface.createClient(bankCode, clientData);
        } catch (BankException be) {
            model.addAttribute("error", "Error: it was not possible to create the client");
            be.printStackTrace();
            BankData bankData = BankInterface.getBankDataByCode(bankCode);
            model.addAttribute("bank", bankData);
            model.addAttribute("client", clientData);
            model.addAttribute("clients", bankData.getClients());
            model.addAttribute("operations", bankData.getOperations());
            return "bank";
        }

        return "redirect:/banks/" + bankCode;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String bankDelete(Model model, @PathVariable String bankCode) {
        logger.info("bankDelete bankCode:{}", bankCode);

        try {
            BankInterface.deleteBank(bankCode);
        } catch (BankException be) {
            model.addAttribute("error", "Error: it was not possible to delete the client");
            be.printStackTrace();
            BankData bankData = BankInterface.getBankDataByCode(bankCode);
            model.addAttribute("bank", bankData);
            model.addAttribute("client", new BankClientData());
            model.addAttribute("clients", bankData.getClients());
            model.addAttribute("operations", bankData.getOperations());
            return "bank";
        }

        return "redirect:/banks";
    }
}
