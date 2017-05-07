package pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects;

import java.util.List;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;

public class ActivityProviderData {
	public enum CopyDepth {
		SHALLOW, ACTIVITY, ACTIVITYOFFER, BOOKING
	}

	private String name;
	private String code;
	private List<ActivityData> activities;

	public ActivityProviderData() {
	}

	public ActivityProviderData(ActivityProvider activity, CopyDepth depth) {
		this.name = activity.getName();
		this.code = activity.getCode();

		switch(depth) {
		case ACTIVITY:
			//TODO
			break;
		case ACTIVITYOFFER:
			//TODO
			break;
		case BOOKING:
			//TODO
			break;
		case SHALLOW:
			break;
		default:
			break;
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
}