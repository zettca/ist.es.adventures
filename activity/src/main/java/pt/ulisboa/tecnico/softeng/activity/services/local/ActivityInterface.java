package pt.ulisboa.tecnico.softeng.activity.services.local;

import org.joda.time.LocalDate;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;
import pt.ulisboa.tecnico.softeng.activity.domain.Booking;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityOfferData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityReservationData;

import java.util.ArrayList;
import java.util.List;

public class ActivityInterface {
	
	@Atomic(mode = TxMode.READ)
	public static List<ActivityProviderData> getActivityProviders() {
		List<ActivityProviderData> activityProviders = new ArrayList<>();
		for (ActivityProvider activityProvider : FenixFramework.getDomainRoot().getActivityProviderSet()) {
			activityProviders.add(new ActivityProviderData(activityProvider));
		}
		return activityProviders;
	}
	
	@Atomic(mode = TxMode.WRITE)
	public static void createActivityProvider(ActivityProviderData activityProviderData) {
		new ActivityProvider(activityProviderData.getCode(), activityProviderData.getName());
	}
	
	@Atomic(mode = TxMode.READ)
	public static ActivityProviderData getActivityProviderDataByCode(String activityProviderCode) {
		ActivityProvider activityProvider = getActivityProviderByCode(activityProviderCode);

		if (activityProvider != null) {
			return new ActivityProviderData(activityProvider);
		} else {
			return null;
		}
	}
	
	@Atomic(mode = TxMode.WRITE)
	public static void createActivityOffer(String providerCode, String activityCode, ActivityOfferData activityOfferData) {
		new ActivityOffer(getActivityByCode(getActivityProviderByCode(providerCode), activityCode),
				activityOfferData.getBegin(), activityOfferData.getEnd());
	}

	@Atomic(mode = TxMode.READ)
	public static List<ActivityOfferData> getActivityOffers(String providerCode, String activityCode) {
		List<ActivityOfferData> activityOffers = new ArrayList<>();
		Activity activity = ActivityInterface.getActivityByCode(getActivityProviderByCode(providerCode), activityCode);
		if (activity != null) {
			for (ActivityOffer activityOffer : activity.getActivityOfferSet()) {
				activityOffers.add(new ActivityOfferData(activityOffer));
			}
		}
		return activityOffers;
	}

	@Atomic(mode = TxMode.WRITE)
	public static String reserveActivity(LocalDate begin, LocalDate end, int age) {
		List<ActivityOffer> offers;
		for (ActivityProvider provider : FenixFramework.getDomainRoot().getActivityProviderSet()) {
			offers = provider.findOffer(begin, end, age);
			if (!offers.isEmpty()) {
				return new Booking(offers.get(0)).getReference();
			}
		}
		throw new ActivityException();
	}

	@Atomic(mode = TxMode.WRITE)
	public static String cancelReservation(String reference) {
		Booking booking = getBookingByReference(reference);
		if (booking != null) {
			return booking.cancel();
		}
		throw new ActivityException();
	}

	@Atomic(mode = TxMode.READ)
	public static ActivityReservationData getActivityReservationData(String reference) {
		for (ActivityProvider provider : FenixFramework.getDomainRoot().getActivityProviderSet()) {
			for (Activity activity : provider.getActivitySet()) {
				for (ActivityOffer offer : activity.getActivityOfferSet()) {
					Booking booking = offer.getBooking(reference);
					if (booking != null) {
						return new ActivityReservationData(provider, offer, booking);
					}
				}
			}
		}
		throw new ActivityException();
	}
	
	private static ActivityProvider getActivityProviderByCode(String code) {
		for (ActivityProvider activityProvider : FenixFramework.getDomainRoot().getActivityProviderSet()) {
			if (activityProvider.getCode().equals(code)) {
				return activityProvider;
			}
		}
		return null;
	}

	@Atomic(mode = TxMode.READ)
	public static Activity getActivityByCode(ActivityProvider provider, String activityCode) {
		for (Activity activity : provider.getActivitySet()) {
			if (activity.getCode().equals(activityCode)) {
				return activity;
			}
		}
		return null;
	}

	public static ActivityData getActivityDataByCode(ActivityProviderData providerData, String activityCode) {
		for (ActivityData activityData : providerData.getActivities()) {
			if (activityData.getCode().equals(activityCode)) {
				return activityData;
			}
		}
		return null;
	}

	private static Booking getBookingByReference(String reference) {
		for (ActivityProvider provider : FenixFramework.getDomainRoot().getActivityProviderSet()) {
			Booking booking = provider.getBooking(reference);
			if (booking != null) {
				return booking;
			}
		}
		return null;
	
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createActivity(String providerCode, ActivityData activityData) {
		new Activity(getActivityProviderByCode(providerCode), activityData.getName(),
				activityData.getMinAge(), activityData.getMaxAge(), activityData.getCapacity());
	}

}
