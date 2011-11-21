package com.scalaxia.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		//UI References
		WebView aboutWebView = (WebView) findViewById(R.id.about_web_view);
		
		//Load the webpage
		aboutWebView.loadUrl(getString(R.string.assets_about));
	}
}
