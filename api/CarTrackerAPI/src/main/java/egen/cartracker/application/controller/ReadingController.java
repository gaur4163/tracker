package egen.cartracker.application.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.google.gson.JsonObject;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import egen.cartracker.application.events.ReminderProducer;

import egen.cartracker.application.model.Event;
import egen.cartracker.application.model.PlotAttributes;

import egen.cartracker.application.model.Reading;
import egen.cartracker.application.model.Vehicle;
import egen.cartracker.application.repository.EventRepository;
import egen.cartracker.application.repository.ReadingRepository;
import egen.cartracker.application.repository.VehicleRepository;
import java.text.ParseException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ReadingController {

	@Autowired
	ReadingRepository readingRepository;

	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	ReminderProducer reminderProducer;
	
	@Autowired
	EventRepository eventRepository;
	
	public static final Logger logger = LoggerFactory.getLogger(ReadingController.class);

	/**
	 * GET /create --> Create a new booking and save it in the database.
	 */
	@RequestMapping(value ="/reading", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody Reading reading) {
		System.out.println(reading);
		reading = readingRepository.save(reading);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Reading created successfully");
		dataMap.put("status", HttpStatus.CREATED);
		dataMap.put("reading", reading);
		return dataMap;
	}

	/**
	 * GET /read --> Get a Reading by Reading id from the database.
	 */
	@RequestMapping(value = "/reading/{id}", method = RequestMethod.GET)
	public Map<String, Object> read(@PathVariable("id") String id) {
		logger.info("Fetching reading with id {}", id);
		Reading reading = readingRepository.findOne(id);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (reading == null) {
			logger.error("reading with id {} not found.", id);
			dataMap.put("message", "Reading Not Found, Please Try Another ID");
			dataMap.put("status", HttpStatus.NOT_FOUND);
			return dataMap;
		}
		dataMap.put("message", "Reading found successfully");
		dataMap.put("status", HttpStatus.FOUND);
		dataMap.put("reading", reading);
		return dataMap;
	}
	/**
	 * GET  --> get all Readings of a vehicle record.
	 */
	@RequestMapping(value = "/readingsForVin/{id}", method = RequestMethod.GET)
	public Map<String, Object> getAllReadingForVin(@PathVariable("id") String id) {
		logger.info("Fetching reading with id {}", id);
		List<Reading> readinglist = readingRepository.findByVin(id);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		dataMap.put("message", "Readings found successfully");
		dataMap.put("status", HttpStatus.FOUND);
		dataMap.put("readings", readinglist);
		return dataMap;
	}
	
	/**
	 * GET  --> get all Readings of a vehicle record with specific format.
	 */
	@RequestMapping(value = "/readingsPlotEngRpm/{id}", method = RequestMethod.GET)
	public Map<String, Object> getAllReadingForVinFormatted(@PathVariable("id") String id) {
		logger.info("Fetching reading with id {}", id);
		List<Reading> readinglist = readingRepository.findByVin(id);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<PlotAttributes> plottingdata = new ArrayList<PlotAttributes>();
		if(readinglist.isEmpty()){
			System.out.println("list empty");
		}
		else{
			for(Reading r:readinglist){
				PlotAttributes p = new PlotAttributes();
				p.setValue(r.getEngineRpm());
				p.setLabel(r.getTimestamp());
				plottingdata.add(p);
			}
			
		}
		Map<String, Object> gh = new HashMap<String, Object>();
		gh.put("caption", "Plot of Engine Rpm");
		gh.put("subCaption", "Showing the trend of Engine Rpm with Timestamp");
		dataMap.put("data", plottingdata);
		dataMap.put("chart",gh);
		return dataMap;
	}
	
	@RequestMapping(value = "/readingsPlotFuelVol/{id}", method = RequestMethod.GET)
	public Map<String, Object> getAllReadingForFuelVol(@PathVariable("id") String id) {
		logger.info("Fetching reading with id {}", id);
		List<Reading> readinglist = readingRepository.findByVin(id);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<PlotAttributes> plottingdata = new ArrayList<PlotAttributes>();
		if(readinglist.isEmpty()){
			System.out.println("list empty");
		}
		else{
			for(Reading r:readinglist){
				PlotAttributes p = new PlotAttributes();
				p.setValue(r.getFuelVolume());
				p.setLabel(r.getTimestamp());
				plottingdata.add(p);
			}
			
		}
		Map<String, Object> gh = new HashMap<String, Object>();
		gh.put("caption", "Plot of Fuel Volume");
		gh.put("subCaption", "Showing the trend of Fuel Volume with Timestamp");
		dataMap.put("data", plottingdata);
		dataMap.put("chart",gh);
		
		return dataMap;
	}
	
	
	@RequestMapping(value = "/timeslotPlotFuelVol/{id}/{timeslot}", method = RequestMethod.GET)
	public Map<String, Object> getAllReadingForFuelVoltimed(@PathVariable("id") String id, @PathVariable("timeslot") String timeslot) {
		logger.info("Fetching reading with id {}", id);
	int timeInt = Integer.parseInt(timeslot);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date date = new Date(System.currentTimeMillis() - (timeInt  * 60 * 1000));
		System.out.println("Current last time is "+date.toString());
		List<Reading> readinglist = readingRepository.findByVin(id);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<PlotAttributes> plottingdata = new ArrayList<PlotAttributes>();
		if(readinglist.isEmpty()){
			System.out.println("list empty");
		}
		else{
			for(Reading r:readinglist){
				
				try{
					Date createddate;
						createddate = formatter.parse(r.getTimestamp().replaceAll("Z$", "+0000"));
						System.out.println("Reading time converted "+createddate.toString()+" acttual "+r.getTimestamp());
					if(createddate.after(date)){
					PlotAttributes p = new PlotAttributes();
					p.setValue(r.getFuelVolume());
					p.setLabel(r.getTimestamp());
					plottingdata.add(p);}}
				catch(ParseException e){
					e.printStackTrace();
				}
				
			}
			
		}
		Map<String, Object> gh = new HashMap<String, Object>();
		gh.put("caption", "Plot of Fuel Volume");
		gh.put("subCaption", "Showing the trend of Fuel Volume with Timestamp");
		dataMap.put("data", plottingdata);
		dataMap.put("chart",gh);
		
		return dataMap;
	}
	@RequestMapping(value = "/timeslotPlotEngRpm/{id}/{timeslot}", method = RequestMethod.GET)
	public Map<String, Object> getAllReadingForVintimed(@PathVariable("id") String id, @PathVariable("timeslot") String timeslot ) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
			int timeInt = Integer.parseInt(timeslot);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date date = new Date(System.currentTimeMillis() - (timeInt  * 60 * 1000));
		logger.info("Fetching reading with id {}", id);
		List<Reading> readinglist = readingRepository.findByVin(id);
		
		List<PlotAttributes> plottingdata = new ArrayList<PlotAttributes>();
		if(readinglist.isEmpty()){
			System.out.println("list empty");
		}
		else{
			for(Reading r:readinglist){
				try{
				Date createddate;
					createddate = formatter.parse(r.getTimestamp().replaceAll("Z$", "+0000"));
				
				if(createddate.after(date)){
				PlotAttributes p = new PlotAttributes();
				p.setValue(r.getEngineRpm());
				p.setLabel(r.getTimestamp());
				plottingdata.add(p);}}
			catch(ParseException e){
				
			}
			}
			
		}
		Map<String, Object> gh = new HashMap<String, Object>();
		gh.put("caption", "Plot of Engine Rpm");
		gh.put("subCaption", "Showing the trend of Engine Rpm with Timestamp");
		dataMap.put("data", plottingdata);
		dataMap.put("chart",gh);
		return dataMap;
	}
	/**
	 * GET /update --> Update a Reading record and save it in the database.
	 */
	@RequestMapping(value = "/reading/{id}", method = RequestMethod.PUT)
	public Map<String, Object> update(@PathVariable String id, @RequestBody Reading readingObj) {
		logger.info("Updating Reading with id {}", id);
		Reading reading = readingRepository.findOne(id);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (reading == null) {
			logger.error("reading with id {} not found.", id);
			dataMap.put("message", "Reading Not Found, Please Try Another ID");
			dataMap.put("status", HttpStatus.NOT_FOUND);
			return dataMap;
		}
		reading.setCheckEngineLightOn(readingObj.isCheckEngineLightOn());
		reading.setCruiseControlOn(readingObj.isCruiseControlOn());
		reading.setEngineCoolantLow(readingObj.isEngineCoolantLow());
		reading.setEngineHp(readingObj.getEngineHp());
		reading.setEngineRpm(readingObj.getEngineRpm());
		reading.setFuelVolume(readingObj.getFuelVolume());
		reading.setLatitude(readingObj.getLatitude());
		reading.setLongitude(readingObj.getLongitude());
		reading.setSpeed(readingObj.getSpeed());
		reading.setTimestamp(readingObj.getTimestamp());
		reading.setVin(readingObj.getVin());
		readingRepository.save(readingObj);

		dataMap.put("message", "Reading updated successfully");
		dataMap.put("status", HttpStatus.OK);
		dataMap.put("reading", readingObj);
		return dataMap;
	}

	@RequestMapping(value = "/readings", method = RequestMethod.POST)
	public Map<String, Object> addReadings(@RequestBody Reading v) {
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			
			 
				  Vehicle vehicle = vehicleRepository.findOne(v.getVin());
				  reminderProducer.checkSpeedRule(v.getEngineRpm(),vehicle.getRedlineRpm(),v.getVin());
				  reminderProducer.checkFuelVolume(v.getFuelVolume(),vehicle.getMaxFuelVolume(),v.getVin());
				  reminderProducer.checkEngineCoolentNLight(v.isCheckEngineLightOn(), v.isEngineCoolantLow(), v.getVin());
				  reminderProducer.checkTirePressure(v.getTires().getFrontLeft(), v.getTires().getFrontRight(), v.getTires().getRearLeft(), v.getTires().getRearRight(), v.getVin());
			
			readingRepository.save(v);
			dataMap.put("message", "Reading saved successfully");
			dataMap.put("status", HttpStatus.OK);
			dataMap.put("reading", v);
			return dataMap;
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("message", "Reading not Added");
			dataMap.put("status", HttpStatus.NOT_FOUND);
			return null;
		}
		
		

	}

	/**
	 * GET /delete --> Delete a Reading from the database.
	 */
	@RequestMapping(value="/reading/{id}" , method = RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable String id) {
		readingRepository.delete(id);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Reading deleted successfully");
		dataMap.put("status", HttpStatus.OK);
		return dataMap;
	}

	/**
	 * GET /read --> Read all readings from the database.
	 */
	@RequestMapping(value = "/readings", method = RequestMethod.GET)
	public Map<String, Object> readAll() {
		List<Reading> readings = readingRepository.findAll();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Readings found successfully");
		dataMap.put("totalReading", readings.size());
		dataMap.put("statusCode", HttpStatus.FOUND);
		dataMap.put("readings", readings);
		return dataMap;
	}
	
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public Map<String, Object> readAllEvents() {
		List<Event> events = eventRepository.findAll();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Events found successfully");
		dataMap.put("totalBooking", events.size());
		dataMap.put("statusCode", HttpStatus.FOUND);
		dataMap.put("events", events);
		return dataMap;
	}
	
	@RequestMapping(value = "/eventsAllHigh", method = RequestMethod.GET)
	public Map<String, Object> readAllHighPriorityEvents() {
		
		Example<Event> example = Example.of(new Event("HIGH"));
		List<Event> events = eventRepository.findAll(example);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Events found successfully");
		dataMap.put("totalBooking", events.size());
		dataMap.put("statusCode", HttpStatus.FOUND);
		dataMap.put("events", events);
		return dataMap;
	}
	
	
	@RequestMapping(value = "/alertsForVin/{id}", method = RequestMethod.GET)
	public Map<String, Object> readAlertsForVin(@PathVariable("id") String id) {
		
		List<Event> events = eventRepository.findByVin(id);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Events found successfully");
		dataMap.put("totalevents", events.size());
		dataMap.put("statusCode", HttpStatus.FOUND);
		dataMap.put("events", events);
		return dataMap;
	}
	
	@RequestMapping(value = "/alertsCount", method = RequestMethod.GET)
	public Map<String, Object> countAlertsForAllVehicle() {
		Date date = new Date(System.currentTimeMillis() - (2 * 60 * 60 * 1000));
		List<Vehicle> vehicles = vehicleRepository.findAll();
		
		for(Vehicle v:vehicles){
			int count =0;
		List<Event> events = eventRepository.findByVin(v.getVin());
			for(Event e :events){
				if(e.getPriority().equals("HIGH")&& e.getCreatedDate().after(date))count++;
			}
		v.setAlertsCount(count);
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Events Counts found successfully");
		dataMap.put("statusCode", HttpStatus.FOUND);
		dataMap.put("vehicles", vehicles);
		return dataMap;
	}
}
