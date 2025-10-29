package com.sabonline.notes_api.Controller;

import com.sabonline.notes_api.Models.Notes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class NotesController {

  @GetMapping("/")
  public List<Notes> getAllNotes() {
    List<Notes> notes = new ArrayList<>();
    Notes n1 = new Notes();
    n1.setId(1);
    n1.setName("test");
    n1.setDescription("Dummy Description");
    n1.setCreated_at(LocalDateTime.now());
    n1.setIs_complete(false);
    notes.add(n1);
    return notes;
  }
}
