package me.remil.notes.service;

import me.remil.notes.dto.NoteTitles;
import me.remil.notes.entity.Notes;

import java.util.List;

public interface NotesService {
    List<NoteTitles> fetchNoteTitles(String username, int titleNumber, int titleCount);

    Notes fetchNoteById(String noteId);

    void saveNote(Notes note);

    void updateNote(Notes note, String userId);

    void deleteNote(String noteId);

}
