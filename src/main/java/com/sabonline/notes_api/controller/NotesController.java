package com.sabonline.notes_api.controller;

import com.sabonline.notes_api.dto.NotesDTO;
import com.sabonline.notes_api.model.Notes;
import com.sabonline.notes_api.service.INotesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

  private final INotesService notesService;

  public NotesController(INotesService notesService) {
    this.notesService = notesService;
  }

  @GetMapping("/")
  public ResponseEntity<List<NotesDTO>> getAllNotes() {
    return ResponseEntity.ok(notesService.getAllNotes());
  }

  @GetMapping("/{id}")
  public NotesDTO getNoteById(@PathVariable Long id) {
    return notesService.getNoteById(id);
  }

  @PostMapping()
  public NotesDTO createNote(@RequestBody NotesDTO newNote) {
    return notesService.createNote(newNote);
  }

  @PutMapping("/{id}")
  public NotesDTO updateNote(@PathVariable Long id, @RequestBody NotesDTO note) {
    return notesService.updateNote(id, note);
  }

  @PatchMapping("/{id}")
  public NotesDTO updateNoteFieldsById(@PathVariable Long id, @RequestBody NotesDTO partialNote) {
    return notesService.patchNote(id, partialNote);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
    return notesService.deleteNote(id);
  }
}
