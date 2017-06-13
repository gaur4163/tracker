package egen.cartracker.application.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Event {

	@Id
	String id;
	String priority;
	String description;
	Date createdDate;
	String vin;
	
	
	
	public Event() {
		super();
	}
	public Event(String priority) {
		this.priority = priority;
	}
	public Event(String priority, String description, Date createdDate,  String vin) {
		
		this.priority = priority;
		this.description = description;
		this.createdDate = createdDate;
		
	
		this.vin = vin;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
