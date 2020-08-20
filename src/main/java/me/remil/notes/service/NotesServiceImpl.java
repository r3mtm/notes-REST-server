package me.remil.notes.service;

import me.remil.notes.dao.NotesRepository;
import me.remil.notes.dto.NoteTitles;
import me.remil.notes.entity.Notes;
import me.remil.notes.exception.IdAlreadyExistsException;
import me.remil.notes.exception.NotFoundException;
import me.remil.notes.exception.UnauthorizedRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {

    final private NotesRepository notesRepository;

    @Autowired
    public NotesServiceImpl(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public List<NoteTitles> fetchNoteTitles(String username, int titleNumber, int titleCount) {
        Pageable pageable = PageRequest.of(titleNumber, titleCount, Sort.by(Sort.Direction.DESC, "lastUpdated"));
        List<Object[]> titles = notesRepository.fetchAllTitleAndIdByUserId(username, pageable);
        List<NoteTitles> noteTitles = new ArrayList<>();

        titles.forEach(object -> {
            noteTitles.add(new NoteTitles((String)object[0], (String)object[1]));
        });

        return noteTitles;
    }

    @Override
    public Notes fetchNoteById(String noteId) {
        return notesRepository.findByNoteId(noteId);
    }

    @Override
    public void saveNote(Notes note) {
        if (notesRepository.countByNoteId(note.getNoteId()) != 0) {
            throw new IdAlreadyExistsException("Note id already exists");
        }
        notesRepository.save(note);
    }

    @Override
    public void updateNote(Notes note, String userId) {
        String userIdFromDb = notesRepository.fetchUserIdByNoteId(note.getNoteId());

        if (userIdFromDb == null) {
            throw new NotFoundException("No note found with that Id");
        }

        if (!userId.equals(userIdFromDb)) {
            throw new UnauthorizedRequestException("Requesting access to unauthorized resources.");
        }
        notesRepository.save(note);
    }

    @Override
    public void deleteNote(String noteId) {
        notesRepository.deleteById(noteId);
    }
}
