package com.smolgeek.noteapp.controllers;

import com.smolgeek.noteapp.models.dto.NoteDTO;
import com.smolgeek.noteapp.models.services.NotesService;
import com.smolgeek.noteapp.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotesService service;

    /**
     * Tests whether the HTTP GET request to "/notes" returns a status code of 200 (OK).
     * @throws Exception if an error occurs
     */
    @Test
    void shouldReturnStatusOK() throws Exception {
        List<NoteDTO> dummyList = TestUtils.generateNoteDTOsId(1);
        String expectedJson = TestUtils.mapToJson(dummyList);
        when(service.getAll()).thenReturn(dummyList);

        this.mockMvc.perform(get("/notes"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Tests fetching  note details.
     * @throws Exception if an error occurs
     */
    @Test
    void renderNoteDetail() throws Exception {

        // mocking service response
        NoteDTO mockNoteDTO = TestUtils.generateNoteDTOsId(1).get(0);
        long id = mockNoteDTO.getNoteId();
        when(service.getById(id)).thenReturn(mockNoteDTO);

        // request
        String inputNoteDTOJson = TestUtils.mapToJson(mockNoteDTO);
        String URI = "/notes/" + id;

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputNoteDTOJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andReturn();

        String outputNoteGTOJson = TestUtils.mapToJson(Objects.requireNonNull(result.getModelAndView())
                .getModel()
                .get("note"));
        assertThat(outputNoteGTOJson).isEqualTo(inputNoteDTOJson);
    }

    /**
     * Tests the fetching of all notes.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void shouldRenderAllNote() throws Exception {
        List<NoteDTO> mockNoteDTOs = TestUtils.generateNoteDTOsId(2);
        when(service.getAll()).thenReturn(mockNoteDTOs);
        String URI = "/notes";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
        String expectedJson = TestUtils.mapToJson(mockNoteDTOs);
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        //Accesses  MvcResult -> Model -> value -> converts back into NoteDTOs
        List<NoteDTO> outputNoteDTOs = (List<NoteDTO>) Objects.requireNonNull(result.getModelAndView())
                .getModel()
                .get("notes");
        String outputJson = TestUtils.mapToJson(outputNoteDTOs);
        assertThat(outputJson).isEqualTo(expectedJson);
    }

}