package com.example.cookbuddy;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StepActivity extends VoiceRecognitionActivity {

	private RecipeReader rr;
	private String id;
	private int currentStep;
	private ImageView currentPicture;
	private TextView instructions;
	private Button backBtn;
	private Button nextBtn;
	private int nrSteps;
	
	boolean speaking = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step);

		Intent activator = getIntent();
		id = activator.getStringExtra("recipeId");

		rr = new RecipeReader(id);
		currentStep = 1;
		nrSteps = rr.getNrSteps();

		currentPicture = (ImageView) findViewById(R.id.image_recipe);
		instructions = (TextView) findViewById(R.id.instructions);
		backBtn = (Button) findViewById(R.id.back_button);
		nextBtn = (Button) findViewById(R.id.next_button);

		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPreviousStep();
			}
		});

		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showNextStep();
			}
		});

	}

	@Override
	public void onInit(int status) {
		super.onInit(status);
		updateData();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE) {
			// If Voice recognition is successful then it returns RESULT_OK
			if (resultCode == RESULT_OK) {

				ArrayList<String> textMatchList = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				if (!textMatchList.isEmpty()) {
					String command = textMatchList.get(0);

					// listening is stopped
					if (command.contains("next")) {
						showNextStep();
					} else if (command.contains("back")) {
						showPreviousStep();
					} else if (command.contains("repeat")) {
						updateData();
					} else if (command.contains("f***")) {
						ioana.speak("fuck you back", TextToSpeech.QUEUE_FLUSH,
								speechParams);
					} else {
						// do nothing, just restart the microphone
						sr.startListening(recognizerIntent);
					}
				}
				// Result code for various error.
			} else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR) {
				showToastMessage("Audio Error");
			} else if (resultCode == RecognizerIntent.RESULT_CLIENT_ERROR) {
				showToastMessage("Client Error");
			} else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
				showToastMessage("Network Error");
			} else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
				showToastMessage("No Match");
			} else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
				showToastMessage("Server Error");
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step, menu);
		return true;
	}

	private void updateData() {
		// set audio back to normal (hi-quality)
//		audioManager.setMode(AudioManager.MODE_NORMAL);
//		audioManager.stopBluetoothSco();
//		audioManager.setBluetoothScoOn(false);
		
		Typeface externalFont = Typeface.createFromAsset(this.getAssets(), "fonts/lane_narrow.ttf");
		instructions.setTypeface(externalFont);
		instructions.setTextSize(TypedValue.COMPLEX_UNIT_PT, 18);
		instructions.setTextColor(Color.parseColor("#12418C"));
		
		instructions.setText(rr.getStepText(currentStep));
		currentPicture.setImageBitmap(rr.getStepPicture(currentStep));

		Log.d("PictureListener", "ioana speaking");
		ioana.speak(rr.getStepText(currentStep), TextToSpeech.QUEUE_FLUSH,
				speechParams);

		ioana.setOnUtteranceProgressListener(new UtteranceProgressListener() {

			@Override
			public void onDone(String utteranceId) {
				Log.d("Ioana", "dau drumul la microfon");
				audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);

				// screw'd up shit!
				instructions.post(new Runnable() {
					@Override
					public void run() {
						// set audio to comm mode for mic rerouting
						audioManager.setMode(AudioManager.MODE_IN_CALL);
						audioManager.startBluetoothSco();
						audioManager.setBluetoothScoOn(true);
						
						sr.startListening(recognizerIntent);
					}
				});
			}

			@Override
			public void onError(String utteranceId) {
				Log.d("Ioana", "mor");
			}

			@Override
			public void onStart(String utteranceId) {
				Log.d("Ioana", "vorbesc");
			}
		});
	}

	private void showNextStep() {
		if (currentStep == nrSteps) {
			sr.stopListening();
			Toast.makeText(this, "DONE!", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		sr.stopListening();
		currentStep++;
		updateData();
	}

	private void showPreviousStep() {
		if (currentStep <= 1) {
			sr.stopListening();
			finish();
			return;
		}

		sr.stopListening();
		currentStep--;
		updateData();
	}
}
