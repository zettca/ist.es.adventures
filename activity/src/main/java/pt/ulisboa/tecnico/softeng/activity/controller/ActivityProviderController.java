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
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData;


@Controller
@RequestMapping(value = "/activityProviders")
public class ActivityProviderController {
	private static Logger logger = LoggerFactory.getLogger(ActivityProviderController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String activityProviderForm(Model model) {
		logger.info("activityProviderForm");
		model.addAttribute("activityProvider", new ActivityProviderData());
		model.addAttribute("activityProviders", ActivityInterface.getActivityProviders());
		return "activityProviders";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String activityProviderSubmit(Model model, @ModelAttribute ActivityProviderData activityProviderData) {
		logger.info("activityProviderSubmit name:{}, code:{}", activityProviderData.getName(), activityProviderData.getCode());

		try {
			ActivityInterface.createActivityProvider(activityProviderData);
		} catch (ActivityException be) {
			model.addAttribute("error", "Error: it was not possible to create the activityProvider");
			model.addAttribute("activityProvider", activityProviderData);
			model.addAttribute("activityProviders", ActivityInterface.getActivityProviders());
			return "activityProviders";
		}

		return "redirect:/activityProviders";
	}
}
