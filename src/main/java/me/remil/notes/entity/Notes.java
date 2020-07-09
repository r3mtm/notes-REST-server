package me.remil.notes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notes")
public class Notes {
	
	@Id
	@Column(name = "note_id")
	private String noteId;

	@Column(name = "note_heading")
	private String noteHeading;
	
	@Column(name = "note_body")
	private String noteBody;
	
	@Column(name = "user_id")
	private String userId; // username
	
	@Column(name = "last_updated")
	private long lastUpdated;
	
	public Notes() {}
	
	public Notes(String noteId, String noteHeading, String noteBody, String userId, long lastUpdated) {
		this.noteId = noteId;
		this.noteHeading = noteHeading;
		this.noteBody = noteBody;
		this.userId = userId;
		this.lastUpdated = lastUpdated;
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

	public String getNoteBody() {
		return noteBody;
	}

	public void setNoteBody(String noteBody) {
		this.noteBody = noteBody;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
