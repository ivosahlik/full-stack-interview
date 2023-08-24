package cz.ivosahlik.interview.service;

import cz.ivosahlik.interview.model.Evidence;
import cz.ivosahlik.interview.model.Version;
import cz.ivosahlik.interview.repository.EvidenceRepository;
import cz.ivosahlik.interview.service.impl.EvidenceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class EvidenceServiceTest {

    @Mock
    private EvidenceRepository evidenceRepository;

    @InjectMocks
    private EvidenceServiceImpl evidenceService;

    private Evidence evidence;

    @BeforeEach
    void setup(){
        Version version = new Version();
        version.setId(1);
        version.setVersion("0.0.1-SNAPSHOT");

        evidence = new Evidence();
        evidence.setId(1);
        evidence.setName("javascript");
        evidence.setDescription("learn javascript");
        evidence.setVersion(Set.of(version));
        evidence.setHypeLevel("10");
        evidence.setDeprecationDate(LocalDate.now());
    }

    @DisplayName("allEvidences: JUnit test for allEvidences method")
    @Test
    void givenEvidenceList_whenGetAllEvidence_thenReturnEvidenceList(){
        given(evidenceRepository.findAll()).willReturn(List.of(evidence));

        List<Evidence> evidenceList = evidenceService.findAll();

        assertThat(evidenceList).hasSize(1);
    }

    @DisplayName("allEvidences: JUnit test for allEvidences method (negative scenario)")
    @Test
    void givenEmptyEvidenceList_whenGetAllEvidence_thenReturnEmptyEvidenceList(){
        given(evidenceRepository.findAll()).willReturn(Collections.emptyList());

        List<Evidence> evidenceList = evidenceService.findAll();

        assertThat(evidenceList).isEmpty();
        assertThat(evidenceList.size()).isZero();
    }

    @DisplayName("retrieveEvidence: JUnit test for retrieveEvidence method")
    @Test
    void givenEvidenceId_whenGetEvidenceById_thenReturnEvidenceObject(){
        // given
        given(evidenceRepository.findById(1)).willReturn(Optional.of(evidence));

        // when
        Evidence savedEvidence = evidenceService.findById(evidence.getId()).get();

        // then
        assertThat(savedEvidence).isNotNull();

    }

    @DisplayName("updateEvidence: JUnit test for updateEvidence method")
    @Test
    void givenEvidenceObject_whenUpdateEvidence_thenReturnUpdatedEvidence(){
        given(evidenceRepository.save(evidence)).willReturn(evidence);
        evidence.setName("test123");
        evidence.setDescription("test123");

        Evidence updatedEvidence = evidenceService.updateEvidence(evidence);

        assertThat(updatedEvidence.getName()).isEqualTo("test123");
        assertThat(updatedEvidence.getDescription()).isEqualTo("test123");
    }

    @DisplayName("deleteEvidence: JUnit test for deleteEvidence method")
    @Test
    void givenEvidenceId_whenDeleteEvidence_thenNothing(){
        // given
        int evidenceId = 1;

        willDoNothing().given(evidenceRepository).deleteById(evidenceId);

        // when
        evidenceService.deleteById(evidenceId);

        // then
        verify(evidenceRepository, times(1)).deleteById(evidenceId);
    }

}