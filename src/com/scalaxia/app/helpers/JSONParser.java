package com.scalaxia.app.helpers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.scalaxia.app.model.Tweet;

public class JSONParser {
	//Debug
	private static final String TAG = "Scalaxia - JSON Parser";
	
	public static List<Tweet> getTweets(String jsonString) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		
		if(Common.DEBUG) {
			Log.d(TAG, jsonString);
		}
		
		try {
			JSONArray tweetResults = new JSONArray(jsonString);
			int n = tweetResults.length();
			
			for(int i=0; i<n; i++) {
				JSONObject tweetObject = tweetResults.getJSONObject(i);
				
				String profileImageUrl = tweetObject.getString(Tweet.KEY_PROFILE_IMAGE_URL);
				String screenName = tweetObject.getString(Tweet.KEY_SCREEN_NAME);
				String text = tweetObject.getString(Tweet.KEY_TEXT);
				String plainText = tweetObject.getString(Tweet.KEY_PLAIN_TEXT);
				String createdAt = tweetObject.getString(Tweet.KEY_CREATED_AT);
				boolean needsTranslation = tweetObject.getBoolean(Tweet.KEY_NEEDS_TRANSLATION);
				
				tweets.add(new Tweet(profileImageUrl, screenName, 
						text, plainText, createdAt, needsTranslation));
				
				if(Common.DEBUG) {
					Log.d(TAG, tweets.get(i).toString());
				}
			}
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}
		
		return tweets.size() == 0 ? null : tweets;
	}
}
