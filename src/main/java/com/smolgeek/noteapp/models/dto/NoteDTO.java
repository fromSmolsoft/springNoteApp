package com.smolgeek.noteapp.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;


/** Data Transfer Object */
@Getter
@Setter
public class NoteDTO {



    private long noteId;

    @NotBlank(message = "Fill in the title")
    @NotNull(message = "Fill in the title")
    @Size(max = 100, message = "The title is too long")
    private String title;

    @Size(max = 3000, message = "The title is too long")
    private String content;

    @DateTimeFormat(pattern = "")
    private OffsetDateTime creationDateTime;

    @DateTimeFormat(pattern = "")
    private OffsetDateTime modifiedDateTime;

    public NoteDTO() {
    }

    public NoteDTO(long noteId, String title, String content, OffsetDateTime creationDateTime, OffsetDateTime modifiedDateTime) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.creationDateTime = creationDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }
}
