package egen.cartracker.application.events;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import egen.cartracker.application.events.VehicleEventListner.ReminderActivatedEvent;
import egen.cartracker.application.events.VehicleEventListner.ReminderCreatedEvent;
import egen.cartracker.application.model.Event;
import egen.cartracker.application.repository.EventRepository;
import egen.cartracker.application.service.MailService;

@Component
public class ReminderListener {

	static final Logger logger = LoggerFactory.getLogger(ReminderListener.class);

	@Autowired
	MailService mailservice;

	@Autowired
	EventRepository eventRepository;

	enum Priority {
		NONE, LOW, MEDIUM, HIGH
	}

	@EventListener(condition = "#event.priority.name() == 'HIGH'")
	public void handleHighPriorityReminders(ReminderCreatedEvent event) {
		logger.info("handle high priority reminder created events '{}'", event);
	}

	@EventListener
	public void handleReminderCreatedEvents(ReminderCreatedEvent event) {
		logger.info("handle all reminder created events '{}'", event);
	}

	@EventListener({ ReminderCreatedEvent.class, ReminderActivatedEvent.class })
	public void handleAllEvents() {
		logger.info("handle all reminder events");
	}

	@EventListener(condition = "#ruleSpeedEvent.engineRpm >#ruleSpeedEvent.redlineRpm")
	public void handleSpeedRule(RuleSpeedEvent ruleSpeedEvent) {

		Date date = new Date();
		logger.info("handle speed event of '{}'", ruleSpeedEvent.engineRpm);
		String subject = "Alert! Priority: " + Priority.HIGH;
		String body = "Alert! EngineRpm > readlineRpm for VehicleID: " + ruleSpeedEvent.vin;
		try {
			Event eventObj = new Event(Priority.HIGH.toString(), body, date, ruleSpeedEvent.vin);
			eventRepository.save(eventObj);
			mailservice.sendEmail(subject, body);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Exception Occured while sending Mail");
		}
	}

	@EventListener(condition = "#ruleFuelVolumeEvent.fuelVol < ( #ruleFuelVolumeEvent.maxVol * 0.1)")
	public void handleFuelVolumeRule(RuleFuelVolumeEvent ruleFuelVolumeEvent) {

		Date date = new Date();
		logger.info("handle fuel volume check event of '{}'", ruleFuelVolumeEvent.fuelVol);
		String subject = "Alert! Priority: " + Priority.MEDIUM;
		String body = "Alert! Fuel Volume of reading < 10 % of max fuel volume for VehicleID: "
				+ ruleFuelVolumeEvent.vin;
		
			Event eventObj = new Event(Priority.MEDIUM.toString(), body, date, ruleFuelVolumeEvent.vin);
			eventRepository.save(eventObj);
		
	}

	@EventListener(condition = "#ruleCoolentNLightEvent.checkEngineLightOn || #ruleCoolentNLightEvent.engineCoolantLow")
	public void handleEngineCoolentNLightRule(RuleCoolentNLightEvent ruleCoolentNLightEvent) {

		Date date = new Date();
		logger.info("handle engine coolent and light check event of '{}' vehicle", ruleCoolentNLightEvent.vin);
		String subject = "Alert! Priority: " + Priority.LOW;
		String body = "Alert! either engine light is on or coolent is low for VehicleID: " + ruleCoolentNLightEvent.vin;
		
			Event eventObj = new Event(Priority.LOW.toString(), body, date, ruleCoolentNLightEvent.vin);
			eventRepository.save(eventObj);
			
	}

	@EventListener(condition = "#ruleTiresEvent.pressureIncorrect")
	public void handleTiresRule(RuleTiresEvent ruleTiresEvent) {

		Date date = new Date();
		logger.info("handle all tires pressure check event of '{}' vehicle", ruleTiresEvent.vin);
		String subject = "Alert! Priority: " + Priority.LOW;
		String body = "Alert! tires pressure is not acurate for VehicleID: " + ruleTiresEvent.vin + " for tires "
				+ ruleTiresEvent.getFaultyTires();
		
			Event eventObj = new Event(Priority.LOW.toString(), body, date, ruleTiresEvent.vin);
			eventRepository.save(eventObj);
			//mailservice.sendEmail(subject, body);
		
	}
}
