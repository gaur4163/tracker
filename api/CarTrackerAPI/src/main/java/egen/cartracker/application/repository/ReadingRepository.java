package egen.cartracker.application.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import egen.cartracker.application.model.Event;
import egen.cartracker.application.model.Reading;

@Transactional
public interface ReadingRepository extends MongoRepository<Reading, String> {
	
	public List<Reading> findByVin(String vin);

}
