package cz.ivosahlik.interview.repository;

import cz.ivosahlik.interview.model.Evidence;
import cz.ivosahlik.interview.model.Version;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EvidenceRepositoryTests {

    @Autowired
    private EvidenceRepository evidenceRepository;

    private Evidence evidence;

    @BeforeEach
    void setup() {
        Version version = new Version();
        version.setVersion("0.0.1-SNAPSHOT");

        evidence = new Evidence();
        evidence.setName("javascript");
        evidence.setDescription("learn javascript");
        evidence.setVersion(Set.of(version));
        evidence.setHypeLevel("10");
        evidence.setDeprecationDate(LocalDate.now());
    }

    @Test
    void givenEvidenceObject_whenSave_thenReturnSavedEvidence() {

        //given

        // when
        Evidence evidenceSaved = evidenceRepository.save(evidence);

        // then
        assertThat(evidenceSaved).isNotNull();
        assertThat(evidenceSaved.getId()).isPositive();
    }

    @Test
    void givenEvidencesList_whenFindAll_thenEvidencesList() {
        // given

        Evidence evidence1 = new Evidence();
        evidence1.setName("reactjs");
        evidence1.setDescription("learn reactjs");
        evidence1.setHypeLevel("10");
        evidence1.setDeprecationDate(LocalDate.now());

        evidenceRepository.saveAll(List.of(evidence, evidence1));

        // when
        List<Evidence> evidenceList = evidenceRepository.findAll();

        // then
        assertThat(evidenceList).isNotNull();
        assertThat(evidenceList.size()).isEqualTo(2);

    }

    @DisplayName("JUnit test for get evidence by id operation")
    @Test
    void givenEvidenceObject_whenFindById_thenReturnEvidenceObject() {
        // given
        evidenceRepository.save(evidence);

        // when
        Evidence evidenceDB = evidenceRepository.findById(evidence.getId()).get();

        // then
        assertThat(evidenceDB).isNotNull();
    }

    @Test
    void givenEvidenceObject_whenDelete_thenRemoveEvidence() {
        // given

        evidenceRepository.save(evidence);

        // when
        evidenceRepository.deleteById(evidence.getId());
        Optional<Evidence> evidenceOptional = evidenceRepository.findById(evidence.getId());

        // then
        assertThat(evidenceOptional).isEmpty();
    }

}
