package me.remil.notes.service;

import java.util.List;

import me.remil.notes.dto.NoteTitles;
import me.remil.notes.entity.Notes;

public interface NoteService {
	public List<NoteTitles> fetchTenNoteTitles(String username, int pageNumber);
	
	public Notes fetchNoteById(String id);
	
	public void saveNote(Notes note);
	
	public void updateNote(Notes note, String username);
	
	public void delete(String id);
}
