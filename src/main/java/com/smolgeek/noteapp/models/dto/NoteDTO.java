package com.smolgeek.noteapp.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;


/** Data Transfer Object */
@Getter
@Setter
@EqualsAndHashCode
public class NoteDTO {



    private long noteId;

    @NotBlank(message = "Fill in the title")
    @NotNull(message = "Fill in the title")
    @Size(max = 100, message = "The title is too long")
    private String title;

    @Size(max = 3000, message = "The title is too long")
    private String content;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @DateTimeFormat(pattern = "")
    private OffsetDateTime creationDateTime;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
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
