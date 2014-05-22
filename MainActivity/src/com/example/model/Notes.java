/*
 * Editor: Christian Joseph Dalisay
 * Date: 05/21/14
 * Description
 * 		added personnel_id
 */
package com.example.model;

public class Notes {
	private String notes_id;
	private int encounter_id;
	private int personnel_id;
	private String title;
	private String body;
	private String type;
	private String date_created;
	private boolean sync;
	
	public Notes() {
		notes_id = "";
		encounter_id = -1;
		this.personnel_id = -1;
		title = null;
		body = null;
		type = null;
		date_created = DateTime.getDateTime();
		sync = false;
	}
	
	public Notes(String nid, int eid, String title, String body, String type, String date_created, boolean sync) {
		this.notes_id = nid;
		this.encounter_id = eid;
		this.title = title;
		this.body = body;
		this.type = type;
		this.date_created = DateTime.getDateTime();
		this.sync = sync;
	}
	
	public String getNotesId() {
		return notes_id;
	}

	public int getEncounterId() {
		return encounter_id;
	}

	public int getPersonnelId() {
		return this.personnel_id;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public String getType() {
		return type;
	}

	public String getDateCreated() {
		return date_created;
	}

	public boolean isSync() {
		return sync;
	}

	public void setNotesId(String notes_id) {
		this.notes_id = notes_id;
	}

	public void setEncounterId(int encounter_id) {
		this.encounter_id = encounter_id;
	}

	public void setPersonnelId(int personnel_id) {
		this.personnel_id = personnel_id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDateCreated(String date_created) {
		this.date_created = DateTime.getDateTime();
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}
	
	public String toString() {
		return this.title + "\n" + this.type + "\n" + this.date_created;
	}
}
