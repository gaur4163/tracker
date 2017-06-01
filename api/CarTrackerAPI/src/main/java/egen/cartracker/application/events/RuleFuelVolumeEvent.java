package egen.cartracker.application.events;

import org.springframework.stereotype.Component;

@Component
public class RuleFuelVolumeEvent extends RuleCreatedEvent {

        public double fuelVol;
        public double maxVol;
    	
        public RuleFuelVolumeEvent(){}
        public RuleFuelVolumeEvent(double fuelVol, double maxVol, String vin) {
            super(vin);
        	this.fuelVol = fuelVol;
            this.maxVol = maxVol;
            
        }
		public double getFuelVol() {
			return fuelVol;
		}
		public void setFuelVol(double fuelVol) {
			this.fuelVol = fuelVol;
		}
		public double getMaxVol() {
			return maxVol;
		}
		public void setMaxVol(double maxVol) {
			this.maxVol = maxVol;
		}
      
    }

