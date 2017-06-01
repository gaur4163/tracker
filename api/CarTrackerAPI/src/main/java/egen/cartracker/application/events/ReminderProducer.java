package egen.cartracker.application.events;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import egen.cartracker.application.events.VehicleEventListner.Priority;
import egen.cartracker.application.events.VehicleEventListner.ReminderActivatedEvent;
import egen.cartracker.application.events.VehicleEventListner.ReminderCreatedEvent;


@Component
public class ReminderProducer {

    final ApplicationEventPublisher publisher;
    
    static final Logger logger = LoggerFactory.getLogger(ReminderProducer.class);

    public ReminderProducer(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void create(String title, Priority priority) {
        ReminderCreatedEvent event = new ReminderCreatedEvent(title, priority);
        logger.info("producer created '{}' event'", event);
        publisher.publishEvent(event);
    }

    public void activate(String title) {
        ReminderActivatedEvent event = new ReminderActivatedEvent(title, new Date());
        logger.info("producer created '{}' event", event);
        publisher.publishEvent(event);
    }

  
    
    public void checkSpeedRule(int engineRpm, int redlineRpm, String vin) {
        publisher.publishEvent(new RuleSpeedEvent(engineRpm,redlineRpm, vin));
    }
    
    public void checkFuelVolume(double fuelVolume, double maxFuelVolume, String vin) {
        publisher.publishEvent(new RuleFuelVolumeEvent(fuelVolume,maxFuelVolume, vin));
    }
    public void checkTirePressure(int frontLeft, int frontRight, int rearLeft, int rearRight, String vin) {
        publisher.publishEvent(new RuleTiresEvent(frontLeft, frontRight, rearLeft,  rearRight, vin));
    }
    public void checkEngineCoolentNLight(boolean checkEngineLightOn, boolean engineCoolantLow,String vin) {
        publisher.publishEvent(new RuleCoolentNLightEvent(checkEngineLightOn,engineCoolantLow, vin));
    }
}