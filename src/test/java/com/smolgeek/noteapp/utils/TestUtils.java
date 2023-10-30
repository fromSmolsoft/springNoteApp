package com.smolgeek.noteapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smolgeek.noteapp.data.entities.NoteEntity;
import com.smolgeek.noteapp.models.dto.NoteDTO;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    /**
     * Maps object to Json String.
     */
    public static String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }

    /**
     * Generates a list of NoteDTOs with the given number of elements.
     * @param n the number of NoteDTOs to generate
     * @return the list of generated NoteDTOs
     */
    public static List<NoteEntity> generateNoteEntities(int n) {
        OffsetDateTime time = OffsetDateTime.parse("2024-01-01T01:01:01.111+01:00");
        List<NoteEntity> list = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            NoteEntity noteDTO = new NoteEntity();
            noteDTO.setTitle(i + "# Title text");
            noteDTO.setContent(i + "# Content text");
            noteDTO.setCreationDateTime(time);
            noteDTO.setModifiedDateTime(time);
            list.add(noteDTO);
        }
        return list;
    }


    /**
     * Generates a list of NoteDTOs with the given number of elements.
     * @param n the number of NoteDTOs to generate
     * @return the list of generated NoteDTOs
     */
    public static List<NoteDTO> generateNoteDTOs(int n) {
        OffsetDateTime time = OffsetDateTime.parse("2024-01-01T01:01:01.111+01:00");
        List<NoteDTO> list = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            NoteDTO noteDTO = new NoteDTO();
            noteDTO.setNoteId(i);
            noteDTO.setTitle(i + "# Title text");
            noteDTO.setContent(i + "# Content text");
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
                    "    \"title\" : \"" + i + "# Title text\",\n" +
                    "    \"content\" : \"" + i + "# Content text\",\n" +
                    "    \"creationDateTime\" : \"" + time + "\",\n" +
                    "    \"modifiedDateTime\" : \"" + time + "\"" +
                    "\n}";
            list.add(json);
        }
        return list;
    }
}
