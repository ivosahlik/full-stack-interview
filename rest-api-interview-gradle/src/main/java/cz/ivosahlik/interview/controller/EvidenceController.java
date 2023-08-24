package cz.ivosahlik.interview.controller;

import cz.ivosahlik.interview.service.EvidenceService;
import cz.ivosahlik.interview.model.Evidence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("api/interview")
@RestController
public class EvidenceController {

	private final EvidenceService evidenceService;

	@GetMapping("/evidences")
	public List<Evidence> allEvidences() {
		return evidenceService.findAll();
	}

	@GetMapping("/{framework}/evidences")
	public List<Evidence> retrieveEvidences(@PathVariable String framework) {
		return evidenceService.findByName(framework);
	}

	@GetMapping("/evidences/{id}")
	public Evidence retrieveEvidence(@PathVariable int id) {
		Optional<Evidence> evidenceOptional = evidenceService.findById(id);
		return evidenceOptional.orElse(null);
	}

	@GetMapping("/hello-interview")
	public String helloInterview() {
		return "Interview";

	}

	@DeleteMapping("/evidences/{id}")
	public ResponseEntity<Void> deleteEvidence(@PathVariable int id) {
		evidenceService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("evidences/{id}")
	public Evidence updateEvidence(@PathVariable("id") int id,
								   @RequestBody Evidence evidence) {
		evidence.setId(id);
		evidenceService.updateEvidence(evidence);
		return evidence;
	}

	@PostMapping("/evidences")
	public Evidence createEvidence(@RequestBody Evidence evidence) {
		evidence.setId(null);
		return evidenceService.save(evidence);
	}
}