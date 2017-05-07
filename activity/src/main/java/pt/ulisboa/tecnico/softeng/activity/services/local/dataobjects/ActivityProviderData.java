package pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;

import java.util.ArrayList;
import java.util.List;

public class ActivityProviderData {
	private String name;
	private String code;
	private List<ActivityData> activities = new ArrayList<>();

	public ActivityProviderData() {
	}

	public ActivityProviderData(ActivityProvider provider) {
		this.name = provider.getName();
		this.code = provider.getCode();

		for (Activity activity : provider.getActivitySet()) {
			activities.add(new ActivityData(activity));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<ActivityData> getActivities() {
		return activities;
	}

	public void setActivities(List<ActivityData> activities) {
		this.activities = activities;
	}
}