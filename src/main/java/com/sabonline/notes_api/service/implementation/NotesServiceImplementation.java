package com.sabonline.notes_api.service.implementation;

import com.sabonline.notes_api.model.Notes;
import com.sabonline.notes_api.repository.NotesRepository;
import com.sabonline.notes_api.service.INotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotesServiceImplementation implements INotesService {
  @Autowired
  private NotesRepository notesRepo;


  @Override
  public List<Notes> getAllNotes() {
    return notesRepo.findAll();
  }

  @Override
  public Optional<Notes> getNoteById(Long id) {
    return notesRepo.findById(id);
  }

  @Override
  public Notes createNote(Notes note) {
    if (note.getCreated_at() == null) {
      note.setCreated_at(LocalDateTime.now());
    }
    return notesRepo.save(note);
  }

  @Override
  public Notes updateNote(Long id, Notes note) {
    Optional<Notes> existingNote = notesRepo.findById(id);
    if (existingNote.isPresent()) {
      Notes updatedNote = existingNote.get();
      updatedNote.setName(note.getName());
      updatedNote.setDescription(note.getDescription());
      updatedNote.setIs_complete(note.getIs_complete());
      return notesRepo.save(updatedNote);
    } else  {
      throw new RuntimeException("Note not Found with id" + id);
    }
  }

  @Override
  public Notes patchNote(Long id, Notes note) {
    return null;
  }

  @Override
  public ResponseEntity<Void> deleteNote(Long id) {
    notesRepo.deleteById(id);
    return null;
  }

}
