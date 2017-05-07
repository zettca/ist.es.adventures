package pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;

import java.util.ArrayList;
import java.util.List;

public class ActivityData {
	private String name;
	private String code;
	private int minAge; 
	private int maxAge; 
	private int capacity;

	private List<ActivityOfferData> offers = new ArrayList<>();

	public ActivityData() {
	}

	public ActivityData(Activity activity) {
		this.name = activity.getName();
		this.code = activity.getCode();
		this.minAge = activity.getMinAge();
		this.maxAge = activity.getMaxAge();
		this.capacity = activity.getCapacity();

		for (ActivityOffer offer : activity.getActivityOfferSet()) {
			offers.add(new ActivityOfferData(offer));
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

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public List<ActivityOfferData> getOffers() {
		return offers;
	}

	public void setOffers(List<ActivityOfferData> offers) {
		this.offers = offers;
	}
}
