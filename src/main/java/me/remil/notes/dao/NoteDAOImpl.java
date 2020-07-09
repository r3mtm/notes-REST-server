package me.remil.notes.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import me.remil.notes.dto.NoteTitles;
import me.remil.notes.entity.Notes;
import me.remil.notes.exception.NotFoundException;
import me.remil.notes.exception.UnauthorizedRequestException;

@Repository
public class NoteDAOImpl implements NotesDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public NoteDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<NoteTitles> fetchAllNotesTitle(String username) {
		
		Query query = entityManager.createQuery
				("select n.noteId, n.noteHeading from Notes n where userId=:userId order by lastUpdated desc");
		query.setParameter("userId", username);
		
		List<Object[]> results = query.getResultList();
		List<NoteTitles> notes = new ArrayList<>();
		
		results.stream().forEach((record) -> {
			notes.add(new NoteTitles((String)record[0], (String)record[1]));
		});
		
		
		return notes;
	}

	@Override
	public Notes fetchById(String id) {
		Notes note = 
				entityManager.find(Notes.class, id);
		
		if (note == null) {
			throw new NotFoundException("No note found with id '" + id + "' ");
		}
		
		return note;
	}

	@Override
	public void save(Notes notes) {
		entityManager.persist(notes);
	}

	@Override
	public void delete(String id) {
		Query query = 
				entityManager.createQuery("delete from Notes where id=:noteid");
		
		query.setParameter("noteid", id);
		query.executeUpdate();
	}

	@Override
	public void update(Notes notes, String username) {
		Query query = entityManager.createQuery("select userId from Notes where noteId=:noteid");
		query.setParameter("noteid", notes.getNoteId());
		String unameDB = (String) query.getSingleResult();
		if (!unameDB.equals(username)) {
			throw new UnauthorizedRequestException("Requesting access to unauthorized resources.");
		}
		entityManager.merge(notes);
	}

}
