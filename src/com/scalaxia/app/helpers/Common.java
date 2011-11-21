package com.scalaxia.app.helpers;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class Common {
	public static final boolean DEBUG = true;
	
	public static Animation getThrobberAnimation() {
		Animation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(600);
		animation.setRepeatMode(Animation.RESTART);
		animation.setRepeatCount(Animation.INFINITE);
		
		return animation;
	}
}
