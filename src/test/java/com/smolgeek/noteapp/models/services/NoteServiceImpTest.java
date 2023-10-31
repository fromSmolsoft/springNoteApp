package com.smolgeek.noteapp.models.services;

import com.smolgeek.noteapp.data.entities.NoteEntity;
import com.smolgeek.noteapp.data.repositories.NoteRepository;
import com.smolgeek.noteapp.models.dto.NoteDTO;
import com.smolgeek.noteapp.models.exceptions.NoteNotFoundException;
import com.smolgeek.noteapp.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class NoteServiceImpTest {

    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    NoteServiceImp noteServiceImp;

    private NoteEntity noteEntity;
    private NoteDTO noteDTO;

    @BeforeEach
    void setUp() {
        noteDTO = TestUtils.generateNoteDTOsId(1).get(0);
        noteEntity = TestUtils.generateNoteEntitiesId(1).get(0);
    }

    @DisplayName("map Entity to DTO in NoteServiceImp.java")
    @Test
    void mapEntityToDTO() {
        assertThat(noteServiceImp.mapEntityToDTO(noteEntity)).isNotNull();
        assertThat(noteServiceImp.mapEntityToDTO(noteEntity)).isEqualTo(noteDTO);
    }


    @DisplayName("Get noteDTO by id.")
    @Test
    void getById() {
        long id = noteDTO.getNoteId();

        when(noteRepository.findById(noteDTO.getNoteId())).thenReturn(Optional.of(noteEntity));

        NoteDTO fetchedNoteDTO = noteServiceImp.getById(id);

        assertThat(fetchedNoteDTO).isNotNull();
        assertThat(fetchedNoteDTO).isEqualTo(noteDTO);
    }

    @Test
    void getAll() {
        int n = 3;
        List<NoteDTO> noteDTOs = TestUtils.generateNoteDTOsId(n);
        List<NoteEntity> noteEntities = TestUtils.generateNoteEntitiesId(n);

        when(noteRepository.findAll()).thenReturn(noteEntities);

        List<NoteDTO> fetchedNoteDTOs = noteServiceImp.getAll();

        assertThat(fetchedNoteDTOs).hasSize(n);
        assertThat(fetchedNoteDTOs.get(0).getTitle()).isEqualTo(noteDTOs.get(0).getTitle());
        assertThat(fetchedNoteDTOs.get(0).getContent()).isEqualTo(noteDTOs.get(0).getContent());

    }

    @Test
    void getNoteOrTrow() {
        long id = noteDTO.getNoteId();

        when(noteRepository.findById(noteDTO.getNoteId())).thenReturn(Optional.of(noteEntity));

        NoteDTO fetchedExistinNoteDTO = noteServiceImp.getById(id);
        assertThatExceptionOfType(NoteNotFoundException.class).isThrownBy(() -> noteServiceImp.getById(id + 1));

        assertThat(fetchedExistinNoteDTO).isNotNull();
        assertThat(fetchedExistinNoteDTO).isEqualTo(noteDTO);
    }

    @Test
    void updateNoteDTO() {
        NoteDTO newNoteDTO = new NoteDTO();
        noteServiceImp.updateNoteDTO(noteDTO, newNoteDTO);

        assertThat(newNoteDTO).isNotNull();
        assertThat(newNoteDTO).isEqualTo(noteDTO);
    }


}