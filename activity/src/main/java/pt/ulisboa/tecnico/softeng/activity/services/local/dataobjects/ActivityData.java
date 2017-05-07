package pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;

public class ActivityData {
	private ActivityProvider provider; 
	private String name; 
	private int minAge; 
	private int maxAge; 
	private int capacity;

   
    public ActivityData() {
    }

    public ActivityData(Activity activity) {
    	this.setProvider(activity.getActivityProvider());
    	this.setName(activity.getName());
    	this.setMinAge(activity.getMinAge());
    	this.setMaxAge(activity.getMaxAge());
    	this.setCapacity(activity.getCapacity());
    }

	public ActivityProvider getProvider() {
		return provider;
	}

	public void setProvider(ActivityProvider provider) {
		this.provider = provider;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
