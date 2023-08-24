package cz.ivosahlik.interview.service.impl;

import cz.ivosahlik.interview.repository.EvidenceRepository;
import cz.ivosahlik.interview.service.EvidenceService;
import cz.ivosahlik.interview.model.Evidence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EvidenceServiceImpl implements EvidenceService {

	private final EvidenceRepository evidenceRepository;


	@Override
	public List<Evidence> findAll() {
		return evidenceRepository.findAll();
	}

	@Override
	public List<Evidence> findByName(String name) {
		return evidenceRepository.findByName(name);
	}

	@Override
	public void deleteById(int id) {
		evidenceRepository.deleteById(id);
	}

	@Override
	public Optional<Evidence> findById(int id) {
		return evidenceRepository.findById(id);
	}

	@Override
	public Evidence updateEvidence(Evidence evidence) {
		deleteById(evidence.getId());
		return evidenceRepository.save(evidence);
	}

	@Override
	public Evidence save(Evidence evidence) {
		return evidenceRepository.save(evidence);
	}
}