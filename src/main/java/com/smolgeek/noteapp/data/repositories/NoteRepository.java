package com.smolgeek.noteapp.data.repositories;

import com.smolgeek.noteapp.data.entities.NoteEntity;
import org.springframework.data.repository.CrudRepository;

/** Repository for note.  */
public interface NoteRepository extends CrudRepository<NoteEntity, Long> {}
