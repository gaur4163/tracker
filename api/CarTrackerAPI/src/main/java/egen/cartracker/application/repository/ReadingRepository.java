package egen.cartracker.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import egen.cartracker.application.model.Reading;

@Transactional
public interface ReadingRepository extends MongoRepository<Reading, String> {
	
	
}
