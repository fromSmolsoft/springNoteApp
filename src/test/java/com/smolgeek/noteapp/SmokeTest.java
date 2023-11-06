package com.smolgeek.noteapp;

import com.smolgeek.noteapp.controllers.NoteController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SmokeTest {

    @Autowired
    private NoteController controller;

    /** Verifies whether context loads by creating controller. */
    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }


}