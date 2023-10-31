package com.smolgeek.noteapp.models.services;

import com.smolgeek.noteapp.data.entities.NoteEntity;
import com.smolgeek.noteapp.data.repositories.NoteRepository;
import com.smolgeek.noteapp.models.dto.NoteDTO;
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
     * @param noteDTO
     */
    @Override
    public void create(NoteDTO noteDTO) {
        NoteEntity newNote = new NoteEntity();
        newNote.setTitle(noteDTO.getTitle());
        newNote.setContent(noteDTO.getContent());

        OffsetDateTime dateTime = Instant.now().atOffset(ZoneOffset.UTC);
        newNote.setCreationDateTime(dateTime);
        newNote.setModifiedDateTime(dateTime);

        noteRepository.save(newNote);
    }

    /**
     * @param noteId
     * @return note by id from data storage
     */
    @Override
    public NoteDTO getById(long noteId) {
        NoteEntity noteEntitiy = getNoteOrTrow(noteId);
        if (noteEntitiy == null) return null;
        return mapEntityToDTO(noteEntitiy);
    }

    /** @return all notes from data storage */
    @Override
    public List<NoteDTO> getAll() {
        return StreamSupport.stream(noteRepository.findAll().spliterator(), false)
                .map(this::mapEntityToDTO)
                .toList();
    }

    /**
     * Edit existing note
     * @param noteDTO
     */
    @Override
    public void edit(NoteDTO noteDTO) {
        NoteEntity oldNote = getNoteOrTrow(noteDTO.getNoteId());
        oldNote.setTitle(noteDTO.getTitle());
        oldNote.setContent(noteDTO.getContent());

        OffsetDateTime dateTime = Instant.now().atOffset(ZoneOffset.UTC);
        oldNote.setModifiedDateTime(dateTime);

        noteRepository.save(oldNote);
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

    /**
     * Updates target DTO with data from source DTO
     * @param source DTO is read from (source)
     * @param target DTO is written into (target)
     */
    @Override
    public void updateNoteDTO(NoteDTO source, NoteDTO target) {
        if (source == null) return;

        target.setNoteId(source.getNoteId());
        target.setTitle(source.getTitle());
        target.setContent(source.getContent());
        target.setModifiedDateTime(source.getModifiedDateTime());
        target.setCreationDateTime(source.getCreationDateTime());
    }

    /**
     * Converts entity to GTO. (Transfers data)
     * @param noteEntity source of data
     */
    public NoteDTO mapEntityToDTO(NoteEntity noteEntity) {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setNoteId(noteEntity.getNoteId());
        noteDTO.setTitle(noteEntity.getTitle());
        noteDTO.setContent(noteEntity.getContent());
        noteDTO.setCreationDateTime(noteEntity.getCreationDateTime());
        noteDTO.setModifiedDateTime(noteEntity.getModifiedDateTime());
        return noteDTO;
    }
}
