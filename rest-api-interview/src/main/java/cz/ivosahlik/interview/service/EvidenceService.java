package cz.ivosahlik.interview.service;

import cz.ivosahlik.interview.model.Evidence;

import java.util.List;
import java.util.Optional;

public interface EvidenceService {

	List<Evidence> findAll();

	List<Evidence> findByName(String name);

	void deleteById(int id);

	Optional<Evidence> findById(int id);

	Evidence updateEvidence(Evidence evidence) ;

	Evidence save(Evidence evidence);
}