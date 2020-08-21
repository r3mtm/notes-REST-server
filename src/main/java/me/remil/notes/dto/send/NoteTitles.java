package me.remil.notes.dto.send;

import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;

@SqlResultSetMapping(
		name = "NoteTitleListMapping",
		entities = @EntityResult(
				entityClass = NoteTitles.class,
				fields = {
						@FieldResult(name = "noteId", column = "note_id"),
						@FieldResult(name = "noteHeading", column = "note_heading")
				})
		)

public class NoteTitles {
	
	private String noteId;
	private String noteHeading;
	
	public NoteTitles(String noteId, String noteHeading) {
		this.noteId = noteId;
		this.noteHeading = noteHeading;
	}
	public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	public String getNoteHeading() {
		return noteHeading;
	}
	public void setNoteHeading(String noteHeading) {
		this.noteHeading = noteHeading;
	}
	
}
