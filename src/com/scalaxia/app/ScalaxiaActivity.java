package com.scalaxia.app;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.commonsware.cwac.endless.EndlessAdapter;
import com.fedorvlasov.lazylist.ImageLoader;
import com.scalaxia.app.helpers.Common;
import com.scalaxia.app.helpers.JSONParser;
import com.scalaxia.app.helpers.Network;
import com.scalaxia.app.model.Tweet;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ScalaxiaActivity extends ListActivity implements OnClickListener {
	//Debug
	private static final String TAG = "Scalaxia Activity";
	
	//Constants for endless adapter
	private static final int TWEETS_PER_FETCH = 10;
	private static final int MIN_DELAY = 1500;
	private static final int MAX_DELAY = 5000;
	
	//Attributes
	private List<Tweet> allTweets;
	private List<Tweet> tweetsInView;
	private ImageLoader imageLoader;
	private FetchTweetsTasks task;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scalaxia);
        
        //Init objects
        tweetsInView = new ArrayList<Tweet>();
        imageLoader = new ImageLoader(this);
        
        //UI References
    	ImageButton teamButton = (ImageButton) findViewById(R.id.team);
    	ImageButton aboutButton = (ImageButton) findViewById(R.id.about);
    	ImageButton refreshButton = (ImageButton) findViewById(R.id.refresh);
    	
    	//Event listeners
    	teamButton.setOnClickListener(this);
    	aboutButton.setOnClickListener(this);
    	refreshButton.setOnClickListener(this);
        
    	//Fetch tweets
        fetchTweets();
    }
    
    class FetchTweetsTasks extends AsyncTask<Object, Object, List<Tweet>> {

		@Override
		protected List<Tweet> doInBackground(Object... params) {
			List<Tweet> tweets = null;
			String jsonResponse = Network.httpGet(getString(R.string.url_scalaxia_json));
			
			if(jsonResponse != null) {
				tweets = JSONParser.getTweets(jsonResponse);
			} 
			
			return tweets;
		}

		@Override
		protected void onPostExecute(List<Tweet> result) {
			if(result != null) {
				allTweets = result;
				for(int i=0; i<TWEETS_PER_FETCH; i++) {
					tweetsInView.add(allTweets.get(i));
				}
				
				ScalaxiaAdapter adapter = new ScalaxiaAdapter(
						ScalaxiaActivity.this, R.layout.tweet, tweetsInView);
				getListView().setAdapter(new ScalaxiaEndlessAdapter(adapter));
			}
		}
    }
    
    class ScalaxiaEndlessAdapter extends EndlessAdapter {

		public ScalaxiaEndlessAdapter(ScalaxiaAdapter adapter) {
			super(adapter);
		}

		@Override
		protected boolean cacheInBackground() throws Exception {
			int totalTweets = allTweets.size();
			int displayedTweets = ((ScalaxiaAdapter) getWrappedAdapter()).getCount();
			
			if(Common.DEBUG) {
				Log.d(TAG, String.format("Total tweets: %d, Displayed tweets: %d", 
						totalTweets, displayedTweets));
			}
			
			if(displayedTweets < totalTweets) {
				int delay = MIN_DELAY + (int) (Math.random() * (MAX_DELAY - MIN_DELAY) + 1);				
				if(Common.DEBUG) {
					Log.d(TAG, "Sleeping for " + delay + " millis.");
				}
				
				Thread.sleep(delay);
				
				return true;
			} else {
				return false;
			}
		}

		@Override
		protected void appendCachedData() {
			int totalTweets = allTweets.size();
			int displayedTweets = ((ScalaxiaAdapter) getWrappedAdapter()).getCount();
			
			if(displayedTweets < totalTweets) {
				int start = displayedTweets;
				int end = (displayedTweets + TWEETS_PER_FETCH) <= (totalTweets - 1) ? 
						displayedTweets + TWEETS_PER_FETCH : totalTweets;
				
				ScalaxiaAdapter wrappedAdapter = (ScalaxiaAdapter) getWrappedAdapter();
				
				if(Common.DEBUG) {
					Log.d(TAG, String.format("Tweets from %d to %d", start, end));
				}
				
				for(int i=start; i<end; i++) {
					wrappedAdapter.add(allTweets.get(i));
				}
			}
		}

		@Override
		protected View getPendingView(ViewGroup parent) {
			View view = getLayoutInflater().inflate(R.layout.loading_list_placeholder, null);
			view.findViewById(R.id.throbber).startAnimation(Common.getThrobberAnimation());
			return view;
		}
    	
    }
    
    class ScalaxiaAdapter extends ArrayAdapter<Tweet> {
    	private List<Tweet> tweets;

		public ScalaxiaAdapter(Context context, int textViewResourceId,
				List<Tweet> tweets) {
			super(context, textViewResourceId, tweets);
			this.tweets = tweets;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			Holder holder = null;
			
			if(view == null) {
				view = getLayoutInflater().inflate(R.layout.tweet, null);
				holder = new Holder();
				
				holder.profileImageView = (ImageView) view.findViewById(R.id.profile_image);
				holder.tweetTextView = (TextView) view.findViewById(R.id.tweet);
				holder.createdAtTextView = (TextView) view.findViewById(R.id.created_at);
				
				view.setTag(holder);
			} else {
				holder = (Holder) view.getTag();
			}
			
			//Get the current tweet
			Tweet tweet = tweets.get(position);
			
			//Set properties
			imageLoader.DisplayImage(tweet.getProfileImageUrl(), 
					ScalaxiaActivity.this, holder.profileImageView);
			holder.tweetTextView.setText(Html.fromHtml(getFormattedTweet(tweet)));
			holder.createdAtTextView.setText(tweet.getCreatedAt());
			
			return view;
		}
		
		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}

		@Override
		public boolean isEnabled(int position) {
			return false;
		}
    }
    
    static class Holder {
    	ImageView profileImageView;
    	TextView tweetTextView;
    	TextView createdAtTextView;
    }
    
    private String getFormattedTweet(Tweet tweet) {
    	return String.format(getString(R.string.format_tweet), 
    			tweet.getScreenName(), URLDecoder.decode(tweet.getPlainText()));
    }

	@Override
	public void onClick(View view) {
		Class<?> clazz = null;
		
		switch(view.getId()) {
		case R.id.team:
			clazz = TeamActivity.class;
			break;
		case R.id.about:
			clazz = AboutActivity.class;
			break;
		case R.id.refresh:
			fetchTweets();
			break;
		}
		
		if(clazz != null) {
			startActivity(new Intent(this, clazz));
		}
	}
	
	private void fetchTweets() {
		getListView().setAdapter(null);
		
		//Clear the tweets in view
		tweetsInView.clear();
		
		//Fetch new tweets
		if(task != null) {
			task.cancel(true);
		}
		
		task = new FetchTweetsTasks();
        task.execute((Object[]) null);
	}
}
