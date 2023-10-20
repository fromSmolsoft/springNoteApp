package com.smolgeek.noteapp.models.services;

import com.smolgeek.noteapp.data.entities.NoteEntity;
import com.smolgeek.noteapp.data.repositories.NoteRepository;
import com.smolgeek.noteapp.models.dto.NoteGTO;
import com.smolgeek.noteapp.models.exceptions.NoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class NoteServiceImp implements NotesService {

    @Autowired
    private NoteRepository noteRepository;


    /**
     * Create new note
     * @param noteGTO
     */
    @Override
    public void create(NoteGTO noteGTO) {
        NoteEntity newNote = new NoteEntity();
        newNote.setTitle(noteGTO.getTitle());
        newNote.setContent(noteGTO.getContent());

        OffsetDateTime dateTime = Instant.now().atOffset(ZoneOffset.UTC);
        newNote.setCreationDateTime(dateTime);
        newNote.setModifiedDateTime(dateTime);

        noteRepository.save(newNote);
    }

    /**
     * Edit existing note
     * @param noteGTO
     */
    @Override
    public void edit(NoteGTO noteGTO) {
        NoteEntity oldNote = getNoteOrTrow(noteGTO.getNoteId());
        oldNote.setTitle(noteGTO.getTitle());
        oldNote.setContent(noteGTO.getContent());

        OffsetDateTime dateTime = Instant.now().atOffset(ZoneOffset.UTC);
        oldNote.setModifiedDateTime(dateTime);

        noteRepository.save(oldNote);
    }

    /**
     * @param noteId
     * @return note by id from data storage
     */
    @Override
    public NoteGTO getById(long noteId) {
        NoteEntity noteEntitiy = getNoteOrTrow(noteId);
        if (noteEntitiy == null) return null;
        return entityToGTO(noteEntitiy);
    }

    /** @return all notes from data storage */
    @Override
    public List<NoteGTO> getAll() {
        return StreamSupport.stream(noteRepository.findAll().spliterator(), false)
                .map(this::entityToGTO)
                .toList();
    }

    /**
     * Delete note from data storage
     * @param noteId
     */
    @Override
    public void remove(long noteId) {
        NoteEntity noteEntity = getNoteOrTrow(noteId);
        noteRepository.delete(noteEntity);
    }

    private NoteEntity getNoteOrTrow(long noteId) {
        return noteRepository.findById(noteId).orElseThrow(NoteNotFoundException::new);
    }

    /** Converts entity to GTO. (Transfers data) */
    private NoteGTO entityToGTO(NoteEntity noteEntitiy) {
        NoteGTO noteGTO = new NoteGTO();
        noteGTO.setTitle(noteEntitiy.getTitle());
        noteGTO.setContent(noteEntitiy.getContent());
        noteGTO.setCreationDateTime(noteEntitiy.getCreationDateTime());
        noteGTO.setModifiedDateTime(noteEntitiy.getModifiedDateTime());
        return noteGTO;
    }
}
