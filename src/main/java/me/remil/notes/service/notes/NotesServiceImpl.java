package me.remil.notes.service.notes;

import me.remil.notes.dao.NotesRepository;
import me.remil.notes.dto.send.NoteTitles;
import me.remil.notes.entity.Notes;
import me.remil.notes.exception.IdAlreadyExistsException;
import me.remil.notes.exception.MissingItemException;
import me.remil.notes.exception.NotFoundException;
import me.remil.notes.exception.UnauthorizedRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    public Notes fetchNoteById(String noteId, String usernameFromToken) {
        String username = notesRepository.fetchUserIdByNoteId(noteId);
        if (!username.equals(usernameFromToken)) {
            throw new UnauthorizedRequestException("UNAUTHORIZED: Request cannot be fulfilled");
        }
        return notesRepository.findByNoteId(noteId);
    }

    @Override
    public void saveNote(Notes note) {
        notesRepository.save(note);
    }

    @Override
    public void updateNote(Notes note, String usernameFromToken) {
        String userIdFromDb = notesRepository.fetchUserIdByNoteId(note.getNoteId());

        if (userIdFromDb == null) {
            throw new NotFoundException("No note associated with that id");
        }

        if (!usernameFromToken.equals(userIdFromDb)) {
            throw new UnauthorizedRequestException("Requesting access to unauthorized resources.");
        }
        notesRepository.save(note);
    }

    @Override
    public void deleteNote(String noteId, String usernameFromToken) {
        String username = notesRepository.fetchUserIdByNoteId(noteId);
        if (!username.equals(usernameFromToken)) {
            throw new UnauthorizedRequestException("UNAUTHORIZED: Request cannot be fulfilled");
        }
        notesRepository.deleteById(noteId);
    }

    @Override
    public void validateBeforeSaveOrUpdate(Notes note, String usernameFromToken, ACTIONS type) {
        String noteId = note.getNoteId();
        String noteTitle = note.getNoteHeading();
        String username = note.getUserId();
        String noteBody = note.getNoteBody();
        Timestamp lastUpdated = note.getLastUpdated();

        if (username == null) {
            throw new MissingItemException("Missing user credentials in request");
        } else if (!username.equals(usernameFromToken)) {
            throw new UnauthorizedRequestException("UNAUTHORIZED: Request cannot be fulfilled");
        }

        if (noteId == null || noteId.trim().length() == 0) {
            throw new MissingItemException("Note id is missing");
        }

        if (noteTitle == null || noteTitle.trim().length() == 0) {
            throw new MissingItemException("Missing title for note");
        }

        if (noteBody == null) {
            throw new MissingItemException("No note given");
        }

        if (lastUpdated == null) {
            throw new MissingItemException("Missing last updated time");
        }

        boolean noteExists = notesRepository.existsByNoteId(noteId);

        if (type.equals(ACTIONS.SAVE)) {
            if (noteExists) {
                throw new IdAlreadyExistsException("Note with id "+noteId+" already exists");
            }
            saveNote(note);
        } else if (!noteExists) {
            throw new NotFoundException("No note found with id "+noteId);
        }
        updateNote(note, usernameFromToken);
    }
}
