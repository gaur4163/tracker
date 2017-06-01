package egen.cartracker.application.events;

import org.springframework.stereotype.Component;

@Component
public class RuleTiresEvent extends RuleCreatedEvent {

	
    	public int frontLeft;
    	public int frontRight;
    	public int rearLeft;
    	public int rearRight;
    	public boolean pressureIncorrect;
    	public String faultyTires;
    	public RuleTiresEvent(){}
		public RuleTiresEvent(int frontLeft, int frontRight, int rearLeft, int rearRight, String vin) {
			super(vin);
			this.frontLeft = frontLeft;
			this.frontRight = frontRight;
			this.rearLeft = rearLeft;
			this.rearRight = rearRight;
			checkPressure();
		}
        
		public void checkPressure(){
			this.faultyTires = "";
			this.pressureIncorrect=false;
			if(frontLeft<32||frontLeft>36){
				this.pressureIncorrect=true;
				this.faultyTires += "frontLeft ";
			}
			if(frontRight<32||frontRight>36){
				this.pressureIncorrect=true;
				this.faultyTires+="frontRight ";
			}
			if(rearLeft<32||rearLeft>36){
				this.pressureIncorrect=true;
				this.faultyTires+="rearLeft ";
			}
			if(rearRight<32||rearRight>36){
				this.pressureIncorrect=true;
				this.faultyTires+="rearRight ";
			}
		}
		public int getFrontLeft() {
			return frontLeft;
		}
		public void setFrontLeft(int frontLeft) {
			this.frontLeft = frontLeft;
		}
		public int getFrontRight() {
			return frontRight;
		}
		public void setFrontRight(int frontRight) {
			this.frontRight = frontRight;
		}
		public int getRearLeft() {
			return rearLeft;
		}
		public void setRearLeft(int rearLeft) {
			this.rearLeft = rearLeft;
		}
		public int getRearRight() {
			return rearRight;
		}
		public void setRearRight(int rearRight) {
			this.rearRight = rearRight;
		}
		public boolean isPressureIncorrect() {
			return pressureIncorrect;
		}
		public void setPressureIncorrect(boolean pressureIncorrect) {
			this.pressureIncorrect = pressureIncorrect;
		}
		public String getFaultyTires() {
			return faultyTires;
		}
		public void setFaultyTires(String faultyTires) {
			this.faultyTires = faultyTires;
		}
	   
       
    }

