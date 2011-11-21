package com.scalaxia.app;

import java.util.ArrayList;
import java.util.List;

import com.scalaxia.app.model.TeamPlayer;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TeamActivity extends ListActivity {
	//Team Data
	private int[] PROFILE_IMAGES = {
		R.drawable.pi_rajmahendra, R.drawable.pi_prasanna,
		R.drawable.pi_gautham, R.drawable.pi_ragunathjawahar
	};
	
	private String[] NAMES = {
		"Rajmahendra ( Raj )", "Prasanna", 
		"Gautham", "Ragunath Jawahar"
	};
	
	private String[] ROLES = {
		"Founder & Co-Creator", "Creator & Co-Founder",
		"Interface Designer", "Android Programmer"
	};
	
	private String[] ABOUT = {
		"Java geek, Java developer since 2000, voracious reader, Taichi student, Java User Group - Chennai Lead.<br />Find me at @rajmahendra",
		"A Java programmer, now attracted by Scala. I am a regular speaker at JUG Chennai and a committer of Kandash project.<br />Follow me @prassee<br />I blog at tumblr",
		"Eternal Linux Fan , jQuery Coder, Regular JUG Chennai Member.<br />I enjoy coding especially in Java , jQuery/JavaScript and Python.<br /><br />FollowMe @gautamNitish<br />I blog at : http://j.mp/ab2pdox<br />GitHub: https://github.com/gautamk",
		"Android enthusiast, entrepreneur and avid programmer.<br /><br />Follow me @ragunathjawahar"
	};
	
	//Attributes
	private List<TeamPlayer> teamPlayers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.team);
		
		//Init objects
		teamPlayers = new ArrayList<TeamPlayer>();
		
		//Get team players' info
		int n = NAMES.length;
		for(int i=0; i<n; i++) {
			teamPlayers.add(new TeamPlayer(NAMES[i],
					ROLES[i], ABOUT[i], PROFILE_IMAGES[i]));
		}
		
		//Set ListView adapter
		getListView().setAdapter(new TeamPlayerAdapter(this, 
				R.id.about_player, teamPlayers));
	}
	
	class TeamPlayerAdapter extends ArrayAdapter<TeamPlayer> {

		public TeamPlayerAdapter(Context context, int textViewResourceId,
				List<TeamPlayer> players) {
			super(context, textViewResourceId, players);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			
			if(view == null) {
				view = getLayoutInflater().inflate(R.layout.team_player, null);
			} 
			
			//Get references
			ImageView profileImageView = (ImageView) view.findViewById(R.id.profile_image);
			TextView aboutPlayerTextView = (TextView) view.findViewById(R.id.about_player);
			
			TeamPlayer player = teamPlayers.get(position);
			String aboutPlayer = String.format(getString(R.string.player_about), 
					player.getRole(), player.getName(), player.getAbout());
			
			//Set properties
			profileImageView.setImageResource(player.getProfileImage());
			aboutPlayerTextView.setText(Html.fromHtml(aboutPlayer));
			
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
}
