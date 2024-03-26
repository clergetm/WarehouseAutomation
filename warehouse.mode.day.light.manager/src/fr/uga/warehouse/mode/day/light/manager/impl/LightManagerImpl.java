package fr.uga.warehouse.mode.day.light.manager.impl;

import fr.uga.warehouse.mode.day.light.manager.LightAdministration;
import fr.uga.warehouse.mode.day.light.service.onoff.OnOffConfiguration;
import java.util.Map;

import fr.uga.warehouse.client.time.MomentOfTheDay;
import fr.uga.warehouse.client.time.MomentOfTheDayListener;
import fr.uga.warehouse.client.time.TimeService;

/**
 * <u>LightManagerImpl</u> implements the <u>LightAdministration</u> and bind to
 * <u>OnOffConfiguration</u>. In order to manipulate the lights of the whole
 * map.
 * 
 * @author mathys
 */
public class LightManagerImpl implements LightAdministration, MomentOfTheDayListener {

	/** Field for OnOffConfigurations dependency */
	private OnOffConfiguration[] OnOffConfigurations;
	/** Field for timeService dependency */
	private TimeService timeService;

	@Override
	public void turnOffAllTheLights() {
		System.out.println("[DAY][MANAGER] - turning all lights off.");
		for (OnOffConfiguration conf : this.OnOffConfigurations) {
			conf.turnOffAllTheLights();
		}
	}

	@Override
	public void turnOnAllTheLights() {
		System.out.println("[DAY][MANAGER] - turning all lights on.");
		for (OnOffConfiguration conf : this.OnOffConfigurations) {
			conf.turnOnAllTheLights();
		}
	}

	/** Bind Method for OnOffConfigurations dependency */
	public void bindOnOffConfiguration(OnOffConfiguration onOffConfiguration, Map<?, ?> properties) {
		System.out.println("[DAY][MANAGER] - bind new OnOffConfiguration : "
				+ properties.get(OnOffConfiguration.PROP_ON_OFF_NAME));
	}

	/** Unbind Method for OnOffConfigurations dependency */
	public void unbindOnOffConfiguration(OnOffConfiguration onOffConfiguration, Map<?, ?> properties) {
		System.out.println(
				"[DAY][MANAGER] - unbind OnOffConfiguration : " + properties.get(OnOffConfiguration.PROP_ON_OFF_NAME));
	}

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("[DAY][MANAGER] - Stopping LightManager.");
		this.timeService.unregister(this);
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("[DAY][MANAGER] - Starting LightManager.");
		this.timeService.register(this);
	}

	@Override
	public void momentOfTheDayHasChanged(MomentOfTheDay newMomentOfTheDay) {
		switch (newMomentOfTheDay) {
		case MORNING:
			this.turnOnAllTheLights();
			break;
		case AFTERNOON:
		case NIGHT:
			this.turnOffAllTheLights();
			break;
		case EVENING:
			// do nothing, like default.
		default:
			break;
		}

	}

}
