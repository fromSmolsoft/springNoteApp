package com.smolgeek.noteapp.models.services;

import com.smolgeek.noteapp.models.dto.NoteGTO;

import java.util.List;


public interface NotesService {

    /** Create new note  */
    void create(NoteGTO noteGTO);

    /** Edit existing note */
    void edit(NoteGTO noteGTO);

    /** @return note by id from data storage */
    NoteGTO getById(long noteId);

    /** @return all notes from data storage */
    List<NoteGTO> getAll();

    /** Delete note from data storage */
    void remove(long noteId);

}
