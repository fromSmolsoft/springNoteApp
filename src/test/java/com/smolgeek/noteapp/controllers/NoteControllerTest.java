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

import java.time.OffsetDateTime;
import java.util.ArrayList;
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
        List<NoteDTO> dummyList = generateNoteDTOs(1);
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
        NoteDTO mockNoteDTO = generateNoteDTOs(1).get(0);
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
        List<NoteDTO> mockNoteDTOs = generateNoteDTOs(2);
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

    /**
     * Generates a list of NoteDTOs with the given number of elements.
     * @param n the number of NoteDTOs to generate
     * @return the list of generated NoteDTOs
     */
    private List<NoteDTO> generateNoteDTOs(int n) {
        OffsetDateTime time = OffsetDateTime.parse("2024-01-01T01:01:01.111+01:00");
        List<NoteDTO> list = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            NoteDTO noteDTO = new NoteDTO();
            noteDTO.setNoteId(i);
            noteDTO.setTitle(i + " Title");
            noteDTO.setContent(i + " content");
            noteDTO.setCreationDateTime(time);
            noteDTO.setModifiedDateTime(time);
            list.add(noteDTO);
        }
        return list;
    }

    /**
     * Generates a list of JSON strings representing notes.
     * @param n the number of notes to generate
     * @return a list of JSON strings representing notes
     */
    private List<String> generateNoteJSons(int n) {
        OffsetDateTime time = OffsetDateTime.parse("2024-01-01T01:01:01.111+01:00");
        List<String> list = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            String json =
                    "{\n" +
                    "    \"noteId\" : " + i + ",\n" +
                    "    \"title\" : \"" + i + " Title\",\n" +
                    "    \"content\" : \"" + i + " Content\",\n" +
                    "    \"creationDateTime\" : \"" + time + "\",\n" +
                    "    \"modifiedDateTime\" : \"" + time + "\"" +
                    "\n}";
            list.add(json);
        }
        return list;
    }
}