/**
 * 
 */
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import egen.cartracker.application.events.ReminderProducer;
import egen.cartracker.application.model.Vehicle;
import egen.cartracker.application.repository.VehicleRepository;

/**
 * @author Gaurav Saxena
 *
 */
@CrossOrigin(origins = "http://mocker.egen.io", maxAge = 3600)
@RestController
public class VehicleController {

	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	ReminderProducer reminderProducer;

	public static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

	/**
	 * GET /create --> Create a new booking and save it in the database.
	 */
	@RequestMapping(value="/vehicle", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody Vehicle vehicle) {
		System.out.println(vehicle);
		
		vehicle = vehicleRepository.save(vehicle);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Vehicle created successfully");
		dataMap.put("status", HttpStatus.CREATED);
		dataMap.put("vehicle", vehicle);
		return dataMap;
	}

	/**
	 * GET /read --> Get a Vehicle by Vehicle id from the database.
	 */
	@RequestMapping(value = "/vehicle/{id}", method = RequestMethod.GET)
	public Map<String, Object> read(@PathVariable("id") String id) {
		logger.info("Fetching vehicle with id {}", id);
		Vehicle vehicle = vehicleRepository.findOne(id);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (vehicle == null) {
			logger.error("vehicle with id {} not found.", id);
			dataMap.put("message", "Vehicle Not Found, Please Try Another ID");
			dataMap.put("status", HttpStatus.NOT_FOUND);
			return dataMap;
		}
		dataMap.put("message", "Vehicle found successfully");
		dataMap.put("status", HttpStatus.FOUND);
		dataMap.put("vehicle", vehicle);
		return dataMap;
	}

	/**
	 * GET /update --> Update a Vehicle record and save it in the database.
	 */
	@RequestMapping(value = "/vehicle/{id}", method = RequestMethod.PUT)
	public Map<String, Object> update(@PathVariable String id, @RequestBody Vehicle vehicleObj) {
		logger.info("Updating Vehicle with id {}", id);
		Vehicle vehicle = vehicleRepository.findOne(id);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (vehicle == null) {
			logger.error("vehicle with id {} not found.", id);
			dataMap.put("message", "Vehicle Not Found, Please Try Another ID");
			dataMap.put("status", HttpStatus.NOT_FOUND);
			return dataMap;
		}
		vehicle.setYear(vehicleObj.getYear());
		vehicle.setRedlineRpm(vehicleObj.getRedlineRpm());
		vehicle.setModel(vehicleObj.getModel());
		vehicle.setMaxFuelVolume(vehicleObj.getMaxFuelVolume());
		vehicle.setMake(vehicleObj.getMake());
		vehicle.setLastServiceDate(vehicleObj.getLastServiceDate());
		vehicleRepository.save(vehicleObj);

		dataMap.put("message", "Vehicle updated successfully");
		dataMap.put("status", HttpStatus.OK);
		dataMap.put("vehicle", vehicleObj);
		return dataMap;
	}

	@RequestMapping(value = "/vehicles", method = RequestMethod.PUT)
	public ResponseEntity<List<Vehicle>> retrievePartnersAvailability(@RequestBody List<Vehicle> requestBody) {

		try {
			List<Vehicle> partnerList = new ArrayList<Vehicle>();
			System.out.println(requestBody);
			/*
			 * for (Vehicle v : requestBody){ partnerList.add(v); }
			 */
			requestBody.stream().forEach(c -> partnerList.add(c));

			vehicleRepository.insert(partnerList);
			//checkForAlert(partnerList);
			return new ResponseEntity<List<Vehicle>>(partnerList, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "List not Added");
		dataMap.put("status", HttpStatus.NOT_FOUND);
		return null;

	}

	/*private void checkForAlert(List<Vehicle> partnerList) {
		// TODO Auto-generated method stub
		
		
	}*/

	/**
	 * GET /delete --> Delete a Vehicle from the database.
	 */
	@RequestMapping(value="/vehicle/{id}" , method = RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable String id) {
		vehicleRepository.delete(id);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Vehicle deleted successfully");
		dataMap.put("status", HttpStatus.OK);
		return dataMap;
	}

	/**
	 * GET /read --> Read all vehicles from the database.
	 */
	@RequestMapping(value = "/vehicles", method = RequestMethod.GET)
	public Map<String, Object> readAll() {
		List<Vehicle> vehicles = vehicleRepository.findAll();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Booking found successfully");
		dataMap.put("totalBooking", vehicles.size());
		dataMap.put("statusCode", HttpStatus.FOUND);
		dataMap.put("bookings", vehicles);
		return dataMap;
	}
}
