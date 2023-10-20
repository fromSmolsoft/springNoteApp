package com.smolgeek.noteapp.controllers;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequestMapping("/notes")
public class NoteController {


    //todo
    //  create
    //  create form
    //  delete
    //  edit
    //  get all
    //  exception


    @PostMapping("create")
    private String createNote(RedirectAttributes redirectAttributes) {
        //todo validation
        redirectAttributes.addFlashAttribute("success", "New note created"); //message back to the user
        return "redirect:/notes";
    }

    @GetMapping("/{noteId}")
    public String renderDetail(@Valid @PathVariable long noteId, Model model) {
        return null;
    }

}
