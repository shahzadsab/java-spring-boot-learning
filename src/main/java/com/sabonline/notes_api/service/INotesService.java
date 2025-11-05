package com.sabonline.notes_api.service;

import com.sabonline.notes_api.dto.NotesDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface INotesService {
  NotesDTO createNote(NotesDTO note);
  NotesDTO updateNote(Long id, NotesDTO note);
  NotesDTO patchNote (Long id, NotesDTO note);
  NotesDTO getNoteById(Long id);
  List<NotesDTO> getAllNotes();
  ResponseEntity<Void> deleteNote(Long id);
}
