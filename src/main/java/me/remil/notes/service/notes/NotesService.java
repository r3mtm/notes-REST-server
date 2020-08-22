package me.remil.notes.service.notes;

import me.remil.notes.dto.send.NoteTitles;
import me.remil.notes.entity.Notes;

import java.util.List;

public interface NotesService {

    enum ACTIONS {SAVE, UPDATE};

    List<NoteTitles> fetchNoteTitles(String username, int titleNumber, int titleCount);

    Notes fetchNoteById(String noteId, String usernameFromToken);

    void saveNote(Notes note);

    void updateNote(Notes note, String usernameFromToken);

    void deleteNote(String noteId, String usernameFromToken);

    void validateBeforeSaveOrUpdate(Notes note, String username, ACTIONS type);

}
