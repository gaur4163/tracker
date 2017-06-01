package egen.cartracker.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import egen.cartracker.application.model.Event;

@Transactional
public interface EventRepository extends MongoRepository<Event, String> {
	
	
}
