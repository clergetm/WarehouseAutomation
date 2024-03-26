package fr.ugawarehouse.client.time.command.impl;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;

import fr.liglab.adele.icasa.command.handler.Command;
import fr.liglab.adele.icasa.command.handler.CommandProvider;
import fr.uga.warehouse.client.time.TimeService;

/**
 * <u>TimeCommandImpl</u> allows you to turn get informations
 * about time.
 * 
 * @author mathys
 */
@Component
@Instantiate(name = "fr.uga.warehouse.client.time.command")
@CommandProvider(namespace = "timecmd")
public class TimeCommandImpl {

	@Requires
	private TimeService timeService;
	
	@Command
	public void getTime() {
		System.out.println("[TIME]: current time is : " + this.timeService.getMomentOfTheDay());
	}
	
	
	/** Component Lifecycle Method */
	@Invalidate
	public void stop() {
		System.out.println("[CMD] - Time commands are now unusable.");
	}

	/** Component Lifecycle Method */
	@Validate
	public void start() {
		System.out.println("[CMD] - Time commands are now usable.");
	}
}
