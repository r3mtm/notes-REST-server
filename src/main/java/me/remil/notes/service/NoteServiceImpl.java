package me.remil.notes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.remil.notes.dao.NotesDAO;
import me.remil.notes.dto.NoteTitles;
import me.remil.notes.entity.Notes;

@Service
public class NoteServiceImpl implements NoteService {

	private NotesDAO notesDAO;
	
	@Autowired
	public NoteServiceImpl(NotesDAO notesDAO) {
		this.notesDAO = notesDAO;
	}

	@Override
	@Transactional
	public List<NoteTitles> fetchAllNotesTitle(String username) {
		return notesDAO.fetchAllNotesTitle(username);
	}

	@Override
	@Transactional
	public Notes fetchNoteById(String id) {
		return notesDAO.fetchById(id);
	}

	@Override
	@Transactional
	public void saveNote(Notes note) {
		notesDAO.save(note);
	}

	@Override
	@Transactional
	public void updateNote(Notes note, String username) {
		notesDAO.update(note, username);
	}

	@Override
	@Transactional
	public void delete(String id) {
		notesDAO.delete(id);
	}

}
