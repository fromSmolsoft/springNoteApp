package com.smolgeek.noteapp.data.repositories;

import com.smolgeek.noteapp.data.entities.NoteEntity;
import com.smolgeek.noteapp.utils.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class NoteRepositoryTest {


    @Autowired
    private NoteRepository noteRepository;

    @Test
    public void saveNoteReturnSavedNote() {
        NoteEntity noteEntity01 = TestUtils.generateNoteEntities(1).get(0);

        NoteEntity savedNote = noteRepository.save(noteEntity01);

        assertThat(savedNote).isNotNull();
        assertThat(savedNote.getNoteId()).isGreaterThan(0);
    }

    @Test
    public void getNoteById() {
        NoteEntity noteEntity01 = TestUtils.generateNoteEntities(2).get(1);

        NoteEntity savedNote = noteRepository.save(noteEntity01);
        long noteId = savedNote.getNoteId();
        NoteEntity fetchedNote = noteRepository.findById(noteId).orElse(null);

        assertThat(fetchedNote).isNotNull();
        assertThat(fetchedNote.getTitle()).isEqualTo(savedNote.getTitle());
        assertThat(fetchedNote.getContent()).isEqualTo(savedNote.getContent());
    }

    @Test
    public void getAllNotes() {
        NoteEntity noteEntity01 = TestUtils.generateNoteEntities(2).get(0);
        NoteEntity noteEntity02 = TestUtils.generateNoteEntities(2).get(1);

        noteRepository.save(noteEntity01);
        noteRepository.save(noteEntity02);

        List<NoteEntity> noteEntities = (List<NoteEntity>) noteRepository.findAll();

        Assertions.assertThat(noteEntities).isNotNull();
        Assertions.assertThat(noteEntities).hasSize(2);
    }

    @Test
    public void updateNote() {
        //todo updateNote
        NoteEntity noteEntity1 = TestUtils.generateNoteEntities(2).get(0);
        NoteEntity noteEntity2 = TestUtils.generateNoteEntities(2).get(1);
        noteEntity1.setNoteId(0L);
        noteEntity2.setNoteId(1L);

        System.out.println(noteEntity2.getNoteId());
        noteRepository.save(noteEntity1);
        NoteEntity saved = noteRepository.save(noteEntity2);

        NoteEntity fetchedNote = noteRepository.findById(saved.getNoteId())
                .orElse(noteEntity1);

        String expectedTitle = "Updated Title";
        fetchedNote.setTitle(expectedTitle);
        NoteEntity updatedNote = noteRepository.save(fetchedNote);

        Assertions.assertThat(updatedNote.getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    public void deleteNote() {
        //todo deleteNote

    }


}