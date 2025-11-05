package com.sabonline.notes_api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class NotesDTO {
  private Long id;
  private String name;
  private String description;
  private LocalDateTime created_at;
  private Boolean is_complete;
}
