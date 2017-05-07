package pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;

public class ActivityOfferData {

	private Activity activity;
	private LocalDate begin;
	private LocalDate end;

	private int capacity;

	public ActivityOfferData() {
	}

	public void ActivitOfferData(ActivityOffer offer) {
		this.activity = offer.getActivity();
		this.begin = offer.getBegin();
		this.end = offer.getEnd();
		this.capacity = offer.getCapacity();
	}


	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public LocalDate getBegin() {
		return begin;
	}

	public void setBegin(LocalDate begin) {
		this.begin = begin;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacidade(int capacity) {
		this.capacity = capacity;
	}
}