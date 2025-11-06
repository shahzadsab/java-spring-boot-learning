package com.sabonline.notes_api.mapper;

import com.sabonline.notes_api.dto.NotesDTO;
import com.sabonline.notes_api.model.Notes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotesMapper {
  Notes toEntity(NotesDTO note);
  NotesDTO toDTO(Notes note);
  List<NotesDTO> toListDTO (List<Notes> notes);
}
