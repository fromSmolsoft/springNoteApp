package com.smolgeek.noteapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smolgeek.noteapp.data.entities.NoteEntity;
import com.smolgeek.noteapp.models.dto.NoteDTO;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/** Testing helper class.  Mostly for generating dummy objects in consistent manner. */
public class TestUtils {
    public static final String NOTE_TITLE_DUMMY = " Title of Lorem Ipsum";
    public static final String LOREM_IPSUM = " Content: Lorem ipsum dolor sit amet. Aut minima impedit ut sunt sint vel maiores tempore et odit sapiente et sint vero est dolorem quia.";
    public static final String DATE = "2024-01-01T01:01:01.111+01:00";

    /**
     * Maps object to Json String.
     */
    public static String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }


    //Single objects generations

    public static NoteDTO generateNoteDTO(Long id) {
        String title = id + NOTE_TITLE_DUMMY;
        String content = id + LOREM_IPSUM;
        OffsetDateTime time = OffsetDateTime.parse(DATE);

        return generateNoteDTO(id, title, content, time, time);
    }

    public static NoteDTO generateNoteDTO(Long id, String title, String content, OffsetDateTime created, OffsetDateTime modified) {
        NoteDTO noteDTO = new NoteDTO();

        if (id != null) noteDTO.setNoteId(id);

        noteDTO.setTitle(title);
        noteDTO.setContent(content);
        noteDTO.setCreationDateTime(created);
        noteDTO.setModifiedDateTime(modified);
        return noteDTO;
    }

    public static NoteEntity generateEntity(Long id) {
        String title = id + NOTE_TITLE_DUMMY;
        String content = id + LOREM_IPSUM;
        OffsetDateTime time = OffsetDateTime.parse(DATE);

        return generateEntity(id, title, content, time, time);
    }

    public static NoteEntity generateEntity(Long id, String title, String content, OffsetDateTime created, OffsetDateTime modified) {
        NoteEntity noteEntity = new NoteEntity();

        if (id != null) noteEntity.setNoteId(id);

        noteEntity.setTitle(title);
        noteEntity.setContent(content);
        noteEntity.setCreationDateTime(created);
        noteEntity.setModifiedDateTime(modified);
        return noteEntity;
    }


    //List generations

    /**
     * Generates a list of entities without ids.
     * @param n the number generated elements
     * @return the list of generated elements
     */
    public static List<NoteEntity> generateNoteEntitiesNoId(int n) {
        OffsetDateTime time = OffsetDateTime.parse(DATE);
        List<NoteEntity> list = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            NoteEntity noteEntity = generateEntity(null);
            list.add(noteEntity);
        }
        return list;
    }

    /**
     * Generates a list of entities with ids.
     * @param n the number generated elements
     * @return the list of generated elements
     */
    public static List<NoteEntity> generateNoteEntitiesId(int n) {
        List<NoteEntity> list = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            NoteEntity noteEntity = generateEntity((long) i);
            list.add(noteEntity);
        }
        return list;
    }


    /**
     * Generates a list of NoteDTOs with ids.
     * @param n the number of NoteDTOs to generate
     * @return the list of generated NoteDTOs
     */
    public static List<NoteDTO> generateNoteDTOsId(int n) {
        List<NoteDTO> list = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            NoteDTO noteDTO = generateNoteDTO((long) i);
            list.add(noteDTO);
        }
        return list;
    }


}