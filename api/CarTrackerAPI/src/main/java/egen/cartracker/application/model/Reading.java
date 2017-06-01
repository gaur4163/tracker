/**
 * 
 */
package egen.cartracker.application.model;

import org.mongodb.morphia.annotations.Embedded;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Gaurav Saxena
 *
 */
@Document
public class Reading{

	@Id
	String Id;
	String vin;
	double latitude;
	double longitude;
	
	String timestamp;
	double fuelVolume;
	double speed;
	double engineHp;
	boolean checkEngineLightOn;
	boolean engineCoolantLow;
	boolean cruiseControlOn;
	int engineRpm;
	
	@Embedded
	Tires tires;

	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public double getFuelVolume() {
		return fuelVolume;
	}

	public void setFuelVolume(double fuelVolume) {
		this.fuelVolume = fuelVolume;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getEngineHp() {
		return engineHp;
	}

	public void setEngineHp(double engineHp) {
		this.engineHp = engineHp;
	}

	public boolean isCheckEngineLightOn() {
		return checkEngineLightOn;
	}

	public void setCheckEngineLightOn(boolean checkEngineLightOn) {
		this.checkEngineLightOn = checkEngineLightOn;
	}

	public boolean isEngineCoolantLow() {
		return engineCoolantLow;
	}

	public void setEngineCoolantLow(boolean engineCoolantLow) {
		this.engineCoolantLow = engineCoolantLow;
	}

	public boolean isCruiseControlOn() {
		return cruiseControlOn;
	}

	public void setCruiseControlOn(boolean cruiseControlOn) {
		this.cruiseControlOn = cruiseControlOn;
	}

	public int getEngineRpm() {
		return engineRpm;
	}

	public void setEngineRpm(int engineRpm) {
		this.engineRpm = engineRpm;
	}

	public Tires getTires() {
		return tires;
	}

	public void setTires(Tires tires) {
		this.tires = tires;
	}
	

	
}