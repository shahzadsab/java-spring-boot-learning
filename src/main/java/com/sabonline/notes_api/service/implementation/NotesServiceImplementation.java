package com.sabonline.notes_api.service.implementation;

import com.sabonline.notes_api.dto.NotesDTO;
import com.sabonline.notes_api.model.Notes;
import com.sabonline.notes_api.repository.NotesRepository;
import com.sabonline.notes_api.service.INotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotesServiceImplementation implements INotesService {
  @Autowired
  private NotesRepository notesRepo;

  @Override
  public List<NotesDTO> getAllNotes() {
    List<Notes> notes =  notesRepo.findAll();
    return notes.stream().map(n ->  NotesDTO.builder()
        .id(n.getId())
        .name(n.getName())
        .description(n.getDescription())
        .created_at(n.getCreated_at())
        .is_complete(n.getIs_complete())
        .build()).collect(Collectors.toList());
  }

  @Override
  public NotesDTO getNoteById(Long id) {
    Optional<Notes> noteOptional = notesRepo.findById(id);
    return noteOptional.map(n -> NotesDTO.builder()
            .id(n.getId())
            .description(n.getDescription())
            .created_at(n.getCreated_at())
            .is_complete(n.getIs_complete())
        .build()).orElse(null);
  }

  public NotesDTO createNote(NotesDTO n) {
    Notes note = Notes.builder()
        .id(n.getId())
        .name(n.getName())
        .description(n.getDescription())
        .created_at(n.getCreated_at())
        .is_complete(n.getIs_complete())
        .build();
    if (note.getCreated_at() == null) {
      note.setCreated_at(LocalDateTime.now());
    }
    Notes nn = notesRepo.save(note);
    return NotesDTO.builder()
        .id(nn.getId())
        .name(nn.getName())
        .description(nn.getDescription())
        .created_at(nn.getCreated_at())
        .is_complete(nn.getIs_complete())
        .build();
  }

  @Override
  public NotesDTO updateNote(Long id, NotesDTO note) {
    Optional<Notes> existingNote = notesRepo.findById(id);
    if (existingNote.isPresent()) {
      Notes updatedNote = existingNote.get();
      updatedNote.setName(note.getName());
      updatedNote.setDescription(note.getDescription());
      updatedNote.setIs_complete(note.getIs_complete());
      Notes n =  notesRepo.save(updatedNote);
      return NotesDTO.builder()
          .id(n.getId())
          .name(n.getName())
          .description(n.getDescription())
          .created_at(n.getCreated_at())
          .is_complete(n.getIs_complete())
          .build();
    } else  {
      throw new RuntimeException("Note not Found with id " + id);
    }
  }

  @Override
  public NotesDTO patchNote(Long id, NotesDTO note) {
    return null;
  }

  @Override
  public ResponseEntity<Void> deleteNote(Long id) {
    notesRepo.deleteById(id);
    return null;
  }

}
