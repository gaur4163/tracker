package egen.cartracker.application.events;

import org.springframework.stereotype.Component;

@Component
public class RuleCoolentNLightEvent extends RuleCreatedEvent {

    	public boolean checkEngineLightOn;
    	public boolean engineCoolantLow;

     
        
        public RuleCoolentNLightEvent() {
			}
        public RuleCoolentNLightEvent(boolean checkEngineLightOn, boolean engineCoolantLow,String vin) {
			super(vin);
			this.checkEngineLightOn = checkEngineLightOn;
			this.engineCoolantLow = engineCoolantLow;
		}

		
    }

