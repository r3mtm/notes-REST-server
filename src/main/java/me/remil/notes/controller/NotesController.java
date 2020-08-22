package me.remil.notes.controller;

import java.util.List;

import me.remil.notes.service.notes.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.remil.notes.dto.send.NoteTitles;
import me.remil.notes.entity.Notes;
import me.remil.notes.exception.BadParameterException;
import me.remil.notes.jwt.util.JwtTokenUtil;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.0.199:3000"})
@RequestMapping("/api")
public class NotesController {

	final private NotesService notesService;

	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	public NotesController(NotesService notesService) {
		this.notesService = notesService;
	}
	
	@GetMapping("/notes/page/{record}/{count}")
	public List<NoteTitles> fetchNoteTitles(@PathVariable String record, @PathVariable String count, @RequestHeader("Authorization") String token) {

		token = token.substring(7);
		int recordNumber, recordCount;
		try {
			recordNumber = Integer.parseInt(record);
			recordCount = Integer.parseInt(count);
		} catch(NumberFormatException e) {
			throw new BadParameterException("Page parameter should be a number. Given string!");
		}
		
		String username = jwtTokenUtil.getUsernameFromToken(token);
		
		return notesService.fetchNoteTitles(username, recordNumber, recordCount);
	}
	
	@GetMapping("/notes/{id}")
	public Notes fetchById(@PathVariable String id) {
		return notesService.fetchNoteById(id);
	}

	@PostMapping("/notes")
	public void saveNote(@RequestBody Notes note) {
		notesService.saveNote(note);
	}

	@PutMapping("/notes")
	public void updateNote(@RequestHeader("Authorization") String token, @RequestBody Notes note) {
		token = token.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		notesService.updateNote(note, username);
	}

	@DeleteMapping("/notes/{id}")
	public void deleteNote(@PathVariable String id) {
		notesService.deleteNote(id);
	}

	@Autowired
	public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}
}
