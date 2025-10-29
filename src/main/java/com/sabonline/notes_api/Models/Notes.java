package com.sabonline.notes_api.Models;


//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import lombok.Data;
//import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
//@Entity
public class Notes {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String description;
  private LocalDateTime created_at;
  private Boolean is_complete;
}
