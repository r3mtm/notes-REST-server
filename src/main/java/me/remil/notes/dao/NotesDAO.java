package me.remil.notes.dao;

import java.util.List;

import me.remil.notes.dto.NoteTitles;
import me.remil.notes.entity.Notes;

public interface NotesDAO {
	public List<NoteTitles> fetchTenNoteTitles(String username, int pageNumber);
	
	public Notes fetchById(String id);
	
	public void save(Notes notes);
	
	public void update(Notes notes, String username);
	
	public void delete(String id);
}
