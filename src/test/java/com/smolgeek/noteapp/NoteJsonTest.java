package com.smolgeek.noteapp;

import com.smolgeek.noteapp.models.dto.NoteDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class NoteJsonTest {

    @Autowired
    private JacksonTester<NoteDTO> json;


    @Test
    void NoteSerializationTest() throws IOException {
        NoteDTO note = new NoteDTO(999, "Title of the note",
                "Content body of the note. New line starts here.",
                OffsetDateTime.parse("2007-12-03T10:15:30+01:00"),
                OffsetDateTime.parse("2000-01-01T01:00:30+01:00"));

        assertThat(json.write(note)).extractingJsonPathNumberValue("@.noteId").isEqualTo((int) note.getNoteId());
        assertThat(json.write(note)).extractingJsonPathStringValue("@.title").asString().isEqualTo(note.getTitle());
        assertThat(json.write(note)).extractingJsonPathStringValue("@.content").asString().isEqualTo(note.getContent());

        assertThat(json.write(note)).extractingJsonPathStringValue("@.creationDateTime")
                .asString()
                .isEqualTo(note.getCreationDateTime().toString());

    }

    @Test
    void NoteDeSerializationTest() throws IOException {

        String expected = """
                {
                    "noteId": 999,
                    "title": "Title of the note",
                    "content": "Content body of the note. New line starts here.",
                    "creationDateTime": "2007-12-03T10:15:30+01:00",
                    "modifiedDateTime": "2000-01-01T01:00:30+01:00"
                }
                """;

        NoteDTO note = new NoteDTO(999, "Title of the note",
                "Content body of the note. New line starts here.",
                OffsetDateTime.parse("2007-12-03T10:15:30+01:00"),
                OffsetDateTime.parse("2000-01-01T01:00:30+01:00"));

        assertThat(json.parseObject(expected).getNoteId()).isEqualTo(note.getNoteId());
        assertThat(json.parseObject(expected).getTitle()).isEqualTo(note.getTitle());
        assertThat(json.parseObject(expected).getContent()).isEqualTo(note.getContent());
        assertThat(json.parseObject(expected).getCreationDateTime()).isEqualTo(note.getCreationDateTime());
        assertThat(json.parseObject(expected).getModifiedDateTime()).isEqualTo(note.getModifiedDateTime());
    }

}
