package com.smolgeek.noteapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** To be used to render home page elements. */
@Controller
public class HomeController {

    /** render index (homepage) */
    @GetMapping("/")
    public String renderIndex() {
        return "pages/notes/index";
    }
}
