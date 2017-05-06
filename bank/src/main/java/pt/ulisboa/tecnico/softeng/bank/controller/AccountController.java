package pt.ulisboa.tecnico.softeng.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Operation;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.bank.services.local.BankInterface;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankAccountData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankClientData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankOperationData;

@Controller
@RequestMapping(value = "/banks/{bankCode}/clients/account")
public class AccountController {
	
	
	
    private static Logger logger = LoggerFactory.getLogger(BanksController.class);

    @RequestMapping(method = RequestMethod.POST)
    public String accountSubmit(Model model, @PathVariable String bankCode, 
    		@ModelAttribute BankAccountData accountData, BankOperationData bankOperationData) {
    	
        logger.info("operationSubmit accountID:{} operationType:{} value:{}", accountData.getId(), 
        		bankOperationData.getType() , bankOperationData.getValue());

        	switch (bankOperationData.getType()) {
    		case Operation.DEPOSIT:
    			int deposit = accountData.getBalance() + bankOperationData.getValue();
    			accountData.setBalance(deposit);
    			model.addAttribute("You have successfully deposited %d€ to your account.\n", bankOperationData.getValue());
    			return "deposit";
    		case WITHDRAW:
    			if(bankOperationData.getValue() - bankOperationData.getValue() >= 0){
    				model.addAttribute("Error: It is not possible to withdraw %d€ when you only have %d€ available.\n", bankOperationData.getValue()); //how?
    				return null;
    			}else{
    				int withdraw = bankOperationData.getValue() - bankOperationData.getValue();
    				accountData.setBalance(withdraw);
    				model.addAttribute("You have successfully withdrawed %d€ from your account.\n", bankOperationData.getValue());
        			return "withdraw";
    			}
    		default:
    			throw new BankException();           
      
        	}

        return "redirect:/banks/" + bankCode;
    }
    
}
