package com.sabonline.notes_api.service.implementation;

import com.sabonline.notes_api.dto.NotesDTO;
import com.sabonline.notes_api.mapper.NotesMapper;
import com.sabonline.notes_api.model.Notes;
import com.sabonline.notes_api.repository.NotesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotesServiceImplementationTest {

    @Mock
    private NotesRepository notesRepo;

    @Mock
    private NotesMapper notesMapper;

    @InjectMocks
    private NotesServiceImplementation notesService;

    private Notes testNote;
    private NotesDTO testNoteDTO;

    @BeforeEach
    void setUp() {
        testNote = new Notes();
        testNote.setId(1L);
        testNote.setName("Test Note");
        testNote.setDescription("This is a test note");
        testNote.setIs_complete(false);
        testNote.setCreated_at(LocalDateTime.now());

        testNoteDTO = new NotesDTO();
        testNoteDTO.setId(1L);
        testNoteDTO.setName("Test Note");
        testNoteDTO.setDescription("This is a test note");
        testNoteDTO.setIs_complete(false);
    }

    @Test
    void getAllNotes_ShouldReturnListOfNotes() {
        // Arrange
        List<Notes> notesList = Arrays.asList(testNote, testNote);
        when(notesRepo.findAll()).thenReturn(notesList);
        when(notesMapper.toListDTO(any())).thenReturn(Arrays.asList(testNoteDTO, testNoteDTO));

        // Act
        List<NotesDTO> result = notesService.getAllNotes();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(notesRepo, times(1)).findAll();
        verify(notesMapper, times(1)).toListDTO(any());
    }

    @Test
    void getNoteById_WithValidId_ShouldReturnNote() {
        // Arrange
        when(notesRepo.findById(1L)).thenReturn(Optional.of(testNote));
        when(notesMapper.toDTO(any(Notes.class))).thenReturn(testNoteDTO);

        // Act
        NotesDTO result = notesService.getNoteById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Note", result.getName());
        verify(notesRepo, times(1)).findById(1L);
    }

    @Test
    void getNoteById_WithInvalidId_ShouldReturnEmptyNote() {
        // Arrange
        when(notesRepo.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        NotesDTO result = notesService.getNoteById(999L);

        // Assert
        assertNotNull(result);
        assertNull(result.getId());
        verify(notesRepo, times(1)).findById(999L);
    }

    @Test
    void createNote_ShouldSaveAndReturnCreatedNote() {
        // Arrange
        when(notesMapper.toEntity(any(NotesDTO.class))).thenReturn(testNote);
        when(notesRepo.save(any(Notes.class))).thenReturn(testNote);
        when(notesMapper.toDTO(any(Notes.class))).thenReturn(testNoteDTO);

        // Act
        NotesDTO result = notesService.createNote(testNoteDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Test Note", result.getName());
        verify(notesRepo, times(1)).save(any(Notes.class));
    }

    @Test
    void createNote_ShouldSetCreatedAtIfNotProvided() {
        // Arrange
        testNote.setCreated_at(null);
        when(notesMapper.toEntity(any(NotesDTO.class))).thenReturn(testNote);
        when(notesRepo.save(any(Notes.class))).thenAnswer(invocation -> {
            Notes savedNote = invocation.getArgument(0);
            assertNotNull(savedNote.getCreated_at());
            return savedNote;
        });
        when(notesMapper.toDTO(any(Notes.class))).thenReturn(testNoteDTO);

        // Act
        notesService.createNote(testNoteDTO);

        // Assert is handled in the mock
        verify(notesRepo, times(1)).save(any(Notes.class));
    }

    @Test
    void updateNote_WithValidId_ShouldUpdateAndReturnNote() {
        // Arrange
        Notes updatedNote = new Notes();
        updatedNote.setId(1L);
        updatedNote.setName("Updated Note");
        updatedNote.setDescription("Updated description");
        updatedNote.setIs_complete(true);

        NotesDTO updatedNoteDTO = new NotesDTO();
        updatedNoteDTO.setName("Updated Note");
        updatedNoteDTO.setDescription("Updated description");
        updatedNoteDTO.setIs_complete(true);

        when(notesRepo.findById(1L)).thenReturn(Optional.of(testNote));
        when(notesRepo.save(any(Notes.class))).thenReturn(updatedNote);
        when(notesMapper.toDTO(any(Notes.class))).thenReturn(updatedNoteDTO);

        // Act
        NotesDTO result = notesService.updateNote(1L, updatedNoteDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Note", result.getName());
        assertEquals("Updated description", result.getDescription());
        assertTrue(result.getIs_complete());
        verify(notesRepo, times(1)).findById(1L);
        verify(notesRepo, times(1)).save(any(Notes.class));
    }

    @Test
    void updateNote_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(notesRepo.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            notesService.updateNote(999L, new NotesDTO()));
        verify(notesRepo, times(1)).findById(999L);
        verify(notesRepo, never()).save(any(Notes.class));
    }

    @Test
    void deleteNote_ShouldCallDeleteById() {
        // Arrange
        when(notesRepo.existsById(1L)).thenReturn(true);
        doNothing().when(notesRepo).deleteById(1L);
    
        // Act
        ResponseEntity<Void> response = notesService.deleteNote(1L);
    
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(notesRepo, times(1)).existsById(1L);
        verify(notesRepo, times(1)).deleteById(1L);
    }

    @Test
    void deleteNote_ShouldReturnNotFoundDeleteById() {
        // Arrange
        when(notesRepo.existsById(1L)).thenReturn(false);

        ResponseEntity<Void> response = notesService.deleteNote(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(notesRepo, times(1)).existsById(1L);
    }



    @Test
    void patchNote_ShouldReturnNull() {
        // Act
        NotesDTO result = notesService.patchNote(1L, new NotesDTO());

        // Assert
        assertNull(result);
    }
}
