package egen.cartracker.application.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import egen.cartracker.application.model.Event;

@Transactional
public interface EventRepository extends MongoRepository<Event, String> {
	
	public List<Event> findByVin(String vin);

	
}
