package pt.ulisboa.tecnico.softeng.activity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.activity.services.local.ActivityInterface;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityOfferData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData;

@Controller
@RequestMapping(value = "/activityProviders/{providerCode}/activities/{activityCode}")
public class ActivityOfferController {
	private static Logger logger = LoggerFactory.getLogger(ActivityOfferController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String activityOfferForm(Model model, @PathVariable String providerCode, @PathVariable String activityCode) {
		logger.info("activityOfferForm");
		ActivityProviderData providerData = ActivityInterface.getActivityProviderDataByCode(providerCode);
		ActivityData activityData = ActivityInterface.getActivityDataByCode(providerData, activityCode);
		model.addAttribute("activity", activityData);
		model.addAttribute("offer", new ActivityOfferData());
		model.addAttribute("offers", activityData.getOffers());
		return "activityOffers";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String activityOfferSubmit(Model model, @PathVariable String providerCode, @PathVariable String activityCode,
									  @ModelAttribute ActivityOfferData activityOfferData) {
		logger.info("activityProviderSubmit begin:{}, end:{}, capacity:{}", activityOfferData.getBegin(), activityOfferData.getEnd(), activityOfferData.getCapacity());

		try {
			ActivityInterface.createActivityOffer(providerCode, activityCode, activityOfferData);
		} catch (ActivityException be) {
			model.addAttribute("error", "Error: it was not possible to create the ActivityOffer");
			model.addAttribute("offer", new ActivityOfferData());
			return "activityOffers";
		}

		return "redirect:/activityProviders/" + providerCode + "/activities/" + activityCode;
	}
}
