package cz.ivosahlik.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.ivosahlik.interview.model.Evidence;
import cz.ivosahlik.interview.model.Version;
import cz.ivosahlik.interview.service.EvidenceService;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(EvidenceController.class)
class EvidenceControllerTests {

    public static final String PATH = "/api/interview/evidences";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvidenceService evidenceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenEvidenceObject_whenCreateEvidence_thenReturnSavedEvidence() throws Exception {

        // given - precondition or setup
        Evidence evidence = getEvidence();

        given(evidenceService.save(any(Evidence.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post(PATH)
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(evidence)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name",
                        is(evidence.getName())))
                .andExpect(jsonPath("$.description",
                        is(evidence.getDescription())));

    }

    private static Evidence getEvidence() {
        Version version = new Version();
        version.setVersion("0.0.1-SNAPSHOT");

        Evidence evidence = new Evidence();
        evidence.setName("javascript");
        evidence.setDescription("learn javascript");
        evidence.setVersion(Set.of(version));
        evidence.setHypeLevel("10");
        evidence.setDeprecationDate(LocalDate.now());
        return evidence;
    }

    @Test
    void givenListOfEvidence_whenGetAllEvidence_thenReturnEvidenceList() throws Exception {
        // given
        List<Evidence> listOfEvidence = new ArrayList<>();

        Evidence evidence = getEvidence();

        listOfEvidence.add(evidence);
        listOfEvidence.add(evidence);
        given(evidenceService.findAll()).willReturn(listOfEvidence);

        // when
        ResultActions response = mockMvc.perform(get(PATH)
                .with(SecurityMockMvcRequestPostProcessors.jwt()));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEvidence.size())));
    }

    @Test
    void givenEvidenceId_whenGetEvidenceById_thenReturnEvidenceObject() throws Exception {
        // given
        int evidenceId = 1;

        Evidence evidence = getEvidence();

        given(evidenceService.findById(evidenceId)).willReturn(Optional.of(evidence));

        // when
        ResultActions response = mockMvc.perform(get(PATH + "/{id}", evidenceId)
                .with(SecurityMockMvcRequestPostProcessors.jwt()));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(evidence.getName())))
                .andExpect(jsonPath("$.description", is(evidence.getDescription())));
    }


    @Test
    void givenInvalidEvidenceId_whenGetEvidenceById_thenReturnEmpty() throws Exception {
        // given
        int evidenceId = 1;

        getEvidence();

        given(evidenceService.findById(evidenceId)).willReturn(Optional.empty());

        // when
        ResultActions response = mockMvc.perform(get(PATH + "/{id}", evidenceId)
                .with(SecurityMockMvcRequestPostProcessors.jwt()));

        // then
        response.andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void givenUpdatedEvidence_whenUpdateEvidence_thenReturnUpdateEvidenceObject() throws Exception {
        // given
        int evidenceId = 1;

        Evidence savedEvidence = getEvidence();

        Evidence updatedEvidence = new Evidence();
        savedEvidence.setName("javascript updated");
        savedEvidence.setDescription("learn javascript updated");

        given(evidenceService.findById(evidenceId)).willReturn(Optional.of(savedEvidence));
        given(evidenceService.updateEvidence(any(Evidence.class)))
                .willAnswer((val) -> val.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(put(PATH + "/{id}", evidenceId)
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEvidence)));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(updatedEvidence.getName())))
                .andExpect(jsonPath("$.description", is(updatedEvidence.getDescription())));
    }

    @Test
    void givenUpdatedEvidence_whenUpdateEvidence_thenReturn404() throws Exception {
        // given
        int evidenceId = 1;

        Evidence savedEvidence = getEvidence();

        Evidence updatedEvidence = new Evidence();
        savedEvidence.setName("javascript updated");
        savedEvidence.setDescription("learn javascript updated");

        given(evidenceService.findById(evidenceId)).willReturn(Optional.empty());
        given(evidenceService.updateEvidence(any(Evidence.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(put(PATH + "/{id}", evidenceId)
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEvidence)));

        // then
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void givenEvidenceId_whenDeleteEvidence_thenReturn200() throws Exception {
        // given
        int evidenceId = 1;
        willDoNothing().given(evidenceService).deleteById(evidenceId);

        // when
        ResultActions response = mockMvc.perform(delete(PATH + "/{id}", evidenceId)
                .with(SecurityMockMvcRequestPostProcessors.jwt()));

        // then
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
