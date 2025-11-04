package com.sabonline.notes_api.service;

import com.sabonline.notes_api.model.Notes;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface INotesService {
  List<Notes> getAllNotes();

  Optional<Notes> getNoteById(Long id);

  Notes createNote(Notes note);

  Notes updateNote(Long id, Notes note);
  Notes patchNote (Long id, Notes note);

  ResponseEntity<Void> deleteNote(Long id);
}
