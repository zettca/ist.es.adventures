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

@Controller
@RequestMapping(value = "/banks/{bankCode}")
public class BankController {
    private static Logger logger = LoggerFactory.getLogger(BanksController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String clientForm(Model model, @PathVariable String bankCode) {
        logger.info("clientForm bank:{}", bankCode);

        BankData bankData = BankInterface.getBankDataByCode(bankCode, BankData.CopyDepth.CLIENTS);

        if (bankData != null) {
            model.addAttribute("bank", bankData);
            model.addAttribute("client", new BankClientData());
            model.addAttribute("clients", bankData.getClients());
            return "bank";
        } else {
            model.addAttribute("error", "Error: there is no bank with code " + bankCode);
            model.addAttribute("bank", new BankData());
            model.addAttribute("banks", BankInterface.getBanks());
            return "banks";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String clientSubmit(Model model, @PathVariable String bankCode, @ModelAttribute BankClientData clientData) {
        logger.info("clientSubmit bankCode:{} clientName:{}", bankCode, clientData.getName());

        try {
            BankInterface.createClient(bankCode, clientData);
        } catch (BankException be) {
            model.addAttribute("error", "Error: it was not possible to create the client");
            be.printStackTrace();
            model.addAttribute("client", clientData);
            model.addAttribute("bank", BankInterface.getBankDataByCode(bankCode, BankData.CopyDepth.CLIENTS));
            return "bank";
        }

        return "redirect:/banks/" + bankCode;
    }
}
