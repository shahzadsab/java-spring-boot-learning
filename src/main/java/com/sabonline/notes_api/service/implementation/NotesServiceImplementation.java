package com.sabonline.notes_api.service.implementation;

import com.sabonline.notes_api.dto.NotesDTO;
import com.sabonline.notes_api.mapper.NotesMapper;
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
  private NotesMapper notesMapper;

  @Autowired
  private NotesRepository notesRepo;

  @Override
  public List<NotesDTO> getAllNotes() {
      return notesMapper.toListDTO(notesRepo.findAll());
  }

  @Override
  public NotesDTO getNoteById(Long id) {
    return notesRepo.findById(id).map(note -> notesMapper.toDTO(note)).orElse(new NotesDTO());
  }

  public NotesDTO createNote(NotesDTO n) {
    Notes note = notesMapper.toEntity(n);
    if (note.getCreated_at() == null) {
      note.setCreated_at(LocalDateTime.now());
    }
    return notesMapper.toDTO(notesRepo.save(note));
  }

  @Override
  public NotesDTO updateNote(Long id, NotesDTO note) {
    Optional<Notes> existingNote = notesRepo.findById(id);
    if (existingNote.isPresent()) {
      Notes updatedNote = existingNote.get();
      updatedNote.setName(note.getName());
      updatedNote.setDescription(note.getDescription());
      updatedNote.setIs_complete(note.getIs_complete());
      return notesMapper.toDTO(notesRepo.save(updatedNote)) ;
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
    if (notesRepo.existsById(id)) {
      notesRepo.deleteById(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

}
