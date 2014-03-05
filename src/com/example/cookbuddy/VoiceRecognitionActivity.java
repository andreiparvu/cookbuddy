package com.example.cookbuddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public 	class VoiceRecognitionActivity 
		extends Activity 
		implements TextToSpeech.OnInitListener {

	protected Button speakButton;
	protected SpeechRecognizer sr;
	protected AudioManager audioManager;
	protected TextToSpeech ioana;
	protected HashMap<String, String> speechParams;
    protected Intent recognizerIntent;
    
	protected final String speakPromptString = "Waiting for command";

	protected static final int NO_OF_MATCHES = 5;
	protected static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
	protected static final String PROMPT_STARTER = "buddy";
	protected static final String RECOGNITION_ID = "ioana";
	protected static final String TAG = "RecognitionActivity";

	// ---------------------------------------------------------------
	// From Activity
	// ---------------------------------------------------------------
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		audioManager.setMode(AudioManager.MODE_IN_CALL);
		audioManager.startBluetoothSco();
		audioManager.setBluetoothScoOn(true);
		
		sr = SpeechRecognizer.createSpeechRecognizer(this);
		sr.setRecognitionListener(new CustomRecListener());
		
		speechParams = new HashMap<String, String>();
		ioana = new TextToSpeech(this, this);
		ioana.setSpeechRate(0.8f);
		
		// preparing active recognition
		recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, NO_OF_MATCHES);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
		
		Log.i("111111", "11111111");
	}

	@Override
	public void onDestroy() {
		if (ioana != null) {
			ioana.stop();
			ioana.shutdown();
		}

		audioManager.setMode(AudioManager.MODE_NORMAL);
		audioManager.stopBluetoothSco();
		audioManager.setBluetoothScoOn(false);
		
		super.onDestroy();
	}
	
	// ---------------------------------------------------------------
	// From TTS.OnInitListener
	// ---------------------------------------------------------------
	
	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = ioana.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This language is not supported");
				return;
			}
			
			speechParams.put(TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS, "true");
			speechParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, RECOGNITION_ID);
		} else {
			Log.e("TTS", "Initialization Failed");
		}
	}

	// ---------------------------------------------------------------
	// Own stuff
	// ---------------------------------------------------------------
	
	public void checkVoiceRecognition() {
		// Check if voice recognition is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

		if (activities.size() == 0) {
			speakButton.setEnabled(false);
			speakButton.setText("Voice recognizer not present");
			Toast.makeText(this, "Voice recognizer not present", Toast.LENGTH_SHORT).show();
		}
	}

	public void promptSpeak() {
		startActivityForResult(recognizerIntent, VOICE_RECOGNITION_REQUEST_CODE);
	}


	/**
	 * Helper method to show the toast message
	 **/
	void showToastMessage(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	// ---------------------------------------------------------------
	// private classes
	// ---------------------------------------------------------------
	/**
	 * Super RecognitionListener that's always active (supposedly).
	 * @author ciocirla
	 *
	 */
	private class CustomRecListener implements RecognitionListener {
		public void onReadyForSpeech(Bundle params) {
			Log.d(TAG, "onReadyForSpeech");
		}

		public void onBeginningOfSpeech() {
			Log.d(TAG, "onBeginningOfSpeech");
		}

		public void onRmsChanged(float rmsdB) {
			Log.d(TAG, "onRmsChanged");
		}

		public void onBufferReceived(byte[] buffer) {
			Log.d(TAG, "onBufferReceived");
		}

		public void onEndOfSpeech() {
			Log.d(TAG, "onEndofSpeech");
		}

		public void onError(int error) {
			Log.d(TAG, "error " + error);
		}

		public void onResults(Bundle results) {
			String str = "\n";
			Log.d(TAG, "onResults " + results);
			ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			for (int i = 0; i < data.size(); i++) {
				Log.d(TAG, "result " + data.get(i));
				str += data.get(i) + "\n";
			}
			
			audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
			
			if (str.toLowerCase(Locale.getDefault()).contains(PROMPT_STARTER)) {
				sr.stopListening();
				promptSpeak();
			}
		}

		public void onPartialResults(Bundle partialResults) {
			Log.d(TAG, "partialResults");
		}

		public void onEvent(int eventType, Bundle params) {
			Log.d(TAG, "onEvent " + eventType);
		}
	}
}
