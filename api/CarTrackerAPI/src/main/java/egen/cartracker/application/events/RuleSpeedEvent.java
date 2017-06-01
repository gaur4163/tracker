package egen.cartracker.application.events;

import org.springframework.stereotype.Component;

@Component
public class RuleSpeedEvent extends RuleCreatedEvent{
		public int engineRpm;
        public int redlineRpm;
  
        public RuleSpeedEvent(){}
        public RuleSpeedEvent(int engineRpm, int redlineRpm, String vin) {
            super(vin);
        	this.redlineRpm = redlineRpm;
            this.engineRpm = engineRpm;
            
        }
		public int getEngineRpm() {
			return engineRpm;
		}
		public void setEngineRpm(int engineRpm) {
			this.engineRpm = engineRpm;
		}
		public int getRedlineRpm() {
			return redlineRpm;
		}
		public void setRedlineRpm(int redlineRpm) {
			this.redlineRpm = redlineRpm;
		}
       
      
    }

