package egen.cartracker.application.events;

import org.springframework.stereotype.Component;

@Component
public class RuleCreatedEvent {

        public String vin;
        
        public RuleCreatedEvent() {
        	
        }
        public RuleCreatedEvent(String vin) {
        	this.vin = vin;
        }
      
    }

