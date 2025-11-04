package com.sabonline.notes_api.controller;

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
  public ResponseEntity<List<Notes>> getAllNotes() {
    return ResponseEntity.ok(notesService.getAllNotes());
  }

  @GetMapping("/{id}")
  public Optional<Notes> getNoteById(@PathVariable Long id) {
    return notesService.getNoteById(id);
  }

  @PostMapping()
  public Notes createNote(@RequestBody Notes newNote) {
    return notesService.createNote(newNote);
  }

  @PutMapping("/{id}")
  public Notes updateNote(@PathVariable Long id, @RequestBody Notes note) {
    return notesService.updateNote(id, note);
  }

  @PatchMapping("/{id}")
  public Notes updateNoteFieldsById(@PathVariable Long id, @RequestBody Notes partialNote) {
    return notesService.patchNote(id, partialNote);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
    return notesService.deleteNote(id);
  }
}
