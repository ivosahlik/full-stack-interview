package cz.ivosahlik.interview.repository;

import cz.ivosahlik.interview.model.Evidence;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EvidenceRepository extends CrudRepository<Evidence, Integer> {
	
	List<Evidence> findByName(String name);

	@Query("SELECT e FROM Evidence e ORDER BY e.deprecationDate ASC")
	List<Evidence> findAll();


}
