package com.scalaxia.app.model;

public class TeamPlayer {
	private String name;
	private String role;
	private String about;
	private int profileImage;
	
	public TeamPlayer(String name, String role, String about, int profileImage) {
		super();
		this.name = name;
		this.role = role;
		this.about = about;
		this.profileImage = profileImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public int getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(int profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		return "TeamPlayer [name=" + name + ", role=" + role + ", about="
				+ about + ", profileImage=" + profileImage + "]";
	}
}
