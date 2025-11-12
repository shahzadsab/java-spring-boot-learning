package com.sabonline.notes_api.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabonline.notes_api.dto.NotesDTO;
import com.sabonline.notes_api.model.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotesMapper {
  private final ObjectMapper objectMapper;

  @Autowired
  public NotesMapper (ObjectMapper objectMapper)  {
    this.objectMapper  =  objectMapper;
  }

  public <T, R> R  map (T source, Class<R> targetClass) {
    return objectMapper.convertValue(source,  targetClass);
  }


  public Notes toEntity(NotesDTO note) {
    return map(note, Notes.class);
  }
  public NotesDTO toDTO(Notes note)  {
    return map(note, NotesDTO.class);
  }
  public List<NotesDTO> toListDTO (List<Notes> notes) {
    return notes.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
  }
}
