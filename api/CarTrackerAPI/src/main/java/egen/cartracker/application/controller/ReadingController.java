package egen.cartracker.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import egen.cartracker.application.events.ReminderProducer;
import egen.cartracker.application.model.Event;
import egen.cartracker.application.model.Reading;
import egen.cartracker.application.model.Vehicle;
import egen.cartracker.application.repository.EventRepository;
import egen.cartracker.application.repository.ReadingRepository;
import egen.cartracker.application.repository.VehicleRepository;

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
	public ResponseEntity<List<Reading>> retrievePartnersAvailability(@RequestBody List<Reading> requestBody) {

		try {
			List<Reading> partnerList = new ArrayList<Reading>();
			System.out.println(requestBody);
			
			 
			for (Reading v : requestBody){ 
				  partnerList.add(v);
				  Vehicle vehicle = vehicleRepository.findOne(v.getVin());
				  reminderProducer.checkSpeedRule(v.getEngineRpm(),vehicle.getRedlineRpm(),v.getVin());
				  reminderProducer.checkFuelVolume(v.getFuelVolume(),vehicle.getMaxFuelVolume(),v.getVin());
				  reminderProducer.checkEngineCoolentNLight(v.isCheckEngineLightOn(), v.isEngineCoolantLow(), v.getVin());
				  reminderProducer.checkTirePressure(v.getTires().getFrontLeft(), v.getTires().getFrontRight(), v.getTires().getRearLeft(), v.getTires().getRearRight(), v.getVin());
			}
			readingRepository.insert(partnerList);
			return new ResponseEntity<List<Reading>>(partnerList, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "List not Added");
		dataMap.put("status", HttpStatus.NOT_FOUND);
		return null;

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
}
