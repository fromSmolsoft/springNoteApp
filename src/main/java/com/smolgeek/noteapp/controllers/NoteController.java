package com.smolgeek.noteapp.controllers;

import com.smolgeek.noteapp.models.dto.NoteDTO;
import com.smolgeek.noteapp.models.exceptions.NoteNotFoundException;
import com.smolgeek.noteapp.models.services.NotesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * All notes are accessible under the @RequestMapping("/notes")
 */

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    NotesService notesService;


    //--- Get notes ---

    /** Get all notes */
    @GetMapping
    public String renderNotes(Model model) {
        List<NoteDTO> notes = notesService.getAll();
        model.addAttribute("notes", notes);
        return "pages/notes/index";
    }

    /**
     * @Returns note's full details
     */
    @GetMapping("/{noteId}")
    public String renderDetail(@PathVariable long noteId, Model model) {

        //Find note by id
        NoteDTO note = notesService.getById(noteId);
        model.addAttribute("note", note);
        return "pages/articles/detail";
    }


    //--- create note ---

    /** Create form at "pages/notes/create" */
    @GetMapping("create")
    private String renderCreateForm(@ModelAttribute NoteDTO note) {
        return "pages/notes/create";
    }

    /**
     * Saves newly created note. <p>
     * Implements `@Valid` jakarta validation to validate inserted data, e.g. number of characters in title field.
     */
    @PostMapping("create")
    private String createNote(@Valid @ModelAttribute NoteDTO note, BindingResult result, RedirectAttributes redirectAttributes) {

        //validation
        if (result.hasErrors()) return renderCreateForm(note);

        //adds new note into data store
        notesService.create(note);

        //message for the user
        redirectAttributes.addFlashAttribute("success", "New note was created");

        return "redirect:/notes";
    }


    //--- edit note ---

    @GetMapping("/edit/{noteId}")
    private String renderEditFrom(@PathVariable Long noteId, NoteDTO note) {
        NoteDTO noteDTO = notesService.getById(noteId);
        notesService.updateNoteDTO(noteDTO, note);
        return "pages/notes/edit";
    }


    @PostMapping("/edit/{noteId}")
    public String editNote(@PathVariable Long noteId, @Valid NoteDTO note, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return renderEditFrom(noteId, note);
        note.setNoteId(noteId);
        notesService.edit(note);

        redirectAttributes.addFlashAttribute("success", "Note was edited.");
        return "redirect:/notes";
    }


    //--- delete note ---


    @GetMapping("delete/{noteId}")
    public String deleteNote(@PathVariable long noteId, RedirectAttributes redirectAttributes) {
        notesService.remove(noteId);
        redirectAttributes.addFlashAttribute("success", "Note was deleted.");
        return "redirect:/notes";
    }


    //---expetions ---

    @ExceptionHandler({NoteNotFoundException.class})
    private String handleNoteNotFoundException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Note wasn't found.");
        return "redirect:/articles";
    }

}
