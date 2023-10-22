package com.smolgeek.noteapp.models.services;

import com.smolgeek.noteapp.models.dto.NoteDTO;

import java.util.List;


public interface NotesService {

    /** Create new note  */
    void create(NoteDTO noteDTO);

    /** Edit existing note */
    void edit(NoteDTO noteDTO);

    /** @return note by id from data storage */
    NoteDTO getById(long noteId);

    /** @return all notes from data storage */
    List<NoteDTO> getAll();

    /** Delete note from data storage */
    void remove(long noteId);

}
