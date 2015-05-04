package com.example.videodemo;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.VideoView;

public class MainActivity extends ActionBarActivity {

	private VideoView video;
	private VerticalSeekBar volume;
	private AudioManager audioManager;
	private int currentVolume;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// play video
		video = (VideoView) findViewById(R.id.video);
		video.setVideoPath("/storage/external_SD/Video/test.mp4");
		video.start();

		volume = (VerticalSeekBar) findViewById(R.id.volume);

		MediaController controller = new MediaController(this);
		controller.setAnchorView(video.getRootView());
		video.setMediaController(controller);

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		// set max progress
		volume.setMax(audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

		// set current volume
		update_volume();
		volume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// Log.d("aaa",
				// progress+".."+audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
						progress, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
			}
		});
	}

	// event change volume
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// get current volume
		currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			if(currentVolume > 0)
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume - 1, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
			
			update_volume();
			break;
		case KeyEvent.KEYCODE_VOLUME_UP:
			if(currentVolume < audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)) 
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume + 1, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
			
			update_volume();
			break;
			
		case KeyEvent.KEYCODE_VOLUME_MUTE:
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
			update_volume();
			break;
		default:
			break;
		}
		return true;
	}

	private void update_volume() {
		volume.update(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
