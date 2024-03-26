package fr.uga.warehouse.client.time.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import fr.liglab.adele.icasa.clockservice.Clock;
import fr.liglab.adele.icasa.service.scheduler.PeriodicRunnable;
import fr.uga.warehouse.client.time.MomentOfTheDay;
import fr.uga.warehouse.client.time.MomentOfTheDayListener;
import fr.uga.warehouse.client.time.TimeService;

public class TimeImpl implements TimeService, PeriodicRunnable {

	/** Default moment is the Morning */
	private MomentOfTheDay moment = MomentOfTheDay.MORNING;

	private List<MomentOfTheDayListener> listeners = new ArrayList<MomentOfTheDayListener>();

	private Clock clock;

	@Override
	public MomentOfTheDay getMomentOfTheDay() {
		return this.moment;
	}

	@Override
	public void run() {
		System.out.println("[TIME] Runtime");

		// Get the current time from currentTimeMillis and convert it in GetCorrespondingMoment
		int currentHour = (new DateTime(clock.currentTimeMillis())).getHourOfDay();
		MomentOfTheDay currentMoment = this.moment.getCorrespondingMoment(currentHour);
		
		// If the moment changed, transfer the information.
		if (currentMoment != this.moment) {
			for (MomentOfTheDayListener listener : listeners) {
				listener.momentOfTheDayHasChanged(currentMoment);
			}
			this.moment = currentMoment;
		}
	}

	@Override
	public long getPeriod() {
		return 1;
	}

	@Override
	public TimeUnit getUnit() {
		return TimeUnit.HOURS;
	}

	@Override
	public void register(MomentOfTheDayListener listener) {
		System.out.println("[TIME] - Add a MomentOfTheDayListener.");
		this.listeners.add(listener);
	}

	@Override
	public void unregister(MomentOfTheDayListener listener) {
		System.out.println("[TIME] - Remove a MomentOfTheDayListener.");
		this.listeners.remove(listener);

	}

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("[TIME] - Time Component is stopping.");
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("[TIME] - Time Component is starting.");
	}

}
