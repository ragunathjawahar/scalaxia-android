package com.scalaxia.app.model;

public class Tweet {
	//JSON Keys
	public static final String KEY_PROFILE_IMAGE_URL = "profImgUrl";
	public static final String KEY_SCREEN_NAME = "screenName";
	public static final String KEY_TEXT = "text";
	public static final String KEY_PLAIN_TEXT = "plainText";
	public static final String KEY_CREATED_AT = "createdAt";
	public static final String KEY_NEEDS_TRANSLATION = "needsTranslation";
	
	//Attributes
	private String profileImageUrl;
	private String screenName;
	private String text;
	private String plainText;
	private String createdAt;
	private boolean needsTranslation;
	
	//Constructor
	public Tweet(String profileImageUrl, String screenName, String text,
			String plainText, String createdAt, boolean needsTranslation) {
		super();
		this.profileImageUrl = profileImageUrl;
		this.screenName = screenName;
		this.text = text;
		this.plainText = plainText;
		this.createdAt = createdAt;
		this.needsTranslation = needsTranslation;
	}
	
	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPlainText() {
		return plainText;
	}

	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isNeedsTranslation() {
		return needsTranslation;
	}

	public void setNeedsTranslation(boolean needsTranslation) {
		this.needsTranslation = needsTranslation;
	}

	@Override
	public String toString() {
		return "Tweet [profileImageUrl=" + profileImageUrl + ", screenName="
				+ screenName + ", text=" + text + ", plainText=" + plainText
				+ ", createdAt=" + createdAt + ", needsTranslation="
				+ needsTranslation + "]";
	}
}
