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
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData;

@Controller
@RequestMapping(value = "/activityProviders/{providerCode}")
public class ActivityController {
    private static Logger logger = LoggerFactory.getLogger(ActivityProviderController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String activityForm(Model model, @PathVariable String providerCode) {
        logger.info("activityForm provider:{}", providerCode);

        ActivityProviderData providerData = ActivityInterface.getActivityProviderDataByCode(providerCode);

        if (providerData != null) {
            model.addAttribute("provider", providerData);
            model.addAttribute("activity", new ActivityData());
            model.addAttribute("activities", providerData.getActivities());
            return "activity";
        } else {
            model.addAttribute("error", "Error: there is no provider with code " + providerCode);
            model.addAttribute("provider", new ActivityProviderData());
            model.addAttribute("activityProviders", ActivityInterface.getActivityProviders());
            return "activityProviders";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String activitySubmit(Model model, @PathVariable String providerCode, @ModelAttribute ActivityData activityData) {
        logger.info("activitySubmit providerCode:{} activityName:{}", providerCode, activityData.getName());

        try {
            ActivityInterface.createActivity(providerCode, activityData);
        } catch (ActivityException ae) {
            model.addAttribute("error", "Error: it was not possible to create the activity");
            ae.printStackTrace();
            model.addAttribute("activity", activityData);
            model.addAttribute("provider", ActivityInterface.getActivityProviderDataByCode(providerCode));
            return "activity";
        }

        return "redirect:/activityProviders/" + providerCode;
    }
}
