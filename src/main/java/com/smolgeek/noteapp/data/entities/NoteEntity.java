package com.smolgeek.noteapp.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;

/** Notes table in database */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NoteEntity {

//    @Setter(value = AccessLevel.PRIVATE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noteId;

    private String title;

    private String content;

    @DateTimeFormat(pattern = "")
    private OffsetDateTime creationDateTime;

    @DateTimeFormat(pattern = "")
    private OffsetDateTime modifiedDateTime;


}
