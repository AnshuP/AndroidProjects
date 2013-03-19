package com.example.classproject_anshup;

public class BabyBook {

	private int id;
	private String date;
	private String time;
	private String note;
	private String photoLocation;
	private String audioLocation;
	
	// Empty constructor
    public BabyBook(){
 
    }
    // constructor
    public BabyBook(int id, String date, String time, String note, String photoLocation, String audioLocation){
        this.id = id;
    	this.date = date;
        this.time = time;
        this.note = note;
        this.photoLocation = photoLocation;
        this.audioLocation = audioLocation;
    }
 
    // constructor
    public BabyBook(String date, String time, String note, String photoLocation, String audioLocation){
    	this.date = date;
        this.time = time;
        this.note = note;
        this.photoLocation = photoLocation;
        this.audioLocation = audioLocation;
    }
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPhotoLocation() {
		return photoLocation;
	}
	public void setPhotoLocation(String photoLocation) {
		this.photoLocation = photoLocation;
	}
	public String getAudioLocation() {
		return audioLocation;
	}
	public void setAudioLocation(String audioLocation) {
		this.audioLocation = audioLocation;
	}
	
	
}
