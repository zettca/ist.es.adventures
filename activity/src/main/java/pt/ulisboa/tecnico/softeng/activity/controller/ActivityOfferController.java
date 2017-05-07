package pt.ulisboa.tecnico.softeng.activity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.activity.services.local.ActivityInterface;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityOfferData;

@Controller
@RequestMapping(value = "/activity/{code}")
public class ActivityOfferController {
	private static Logger logger = LoggerFactory.getLogger(ActivityOfferController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String activityOfferForm(Model model, String code) {
		logger.info("activityOfferForm");
		model.addAttribute("activity", ActivityInterface.getActivityByCode(code));
		model.addAttribute("activityoffers", ActivityInterface.getActivityOffers(code));
		model.addAttribute("activityoffer", new ActivityOfferData());
		return "activityoffer";
	}

	@RequestMapping(value="/activity/{code}/newoffer", method = RequestMethod.POST)
	public String activityOfferSubmit(Model model, String code, @ModelAttribute ActivityOfferData activityOfferData) {
		logger.info("activityProviderSubmit begin:{}, end:{}, capacity:{}", activityOfferData.getBegin(), activityOfferData.getEnd(), activityOfferData.getCapacity());

		try {
			ActivityInterface.createActivityOffer(activityOfferData);
		} catch (ActivityException be) {
			model.addAttribute("error", "Error: it was not possible to create the ActivityOffer");
			model.addAttribute("activity", activityOfferData);
			model.addAttribute("activityoffer", ActivityInterface.getActivityProviders());
			return "activityoffer";
		}

		return "redirect:/activity/" + code;
	}
}
