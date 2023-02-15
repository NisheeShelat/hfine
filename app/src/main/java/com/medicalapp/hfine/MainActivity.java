package com.medicalapp.hfine;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

	private TextView txvResult;
	private TextView text1;

//	String inp="12345";
	PyObject obj1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txvResult =findViewById(R.id.txvResult);

	}


	public void getSpeechInput(View view) {
		String languagePref = "hi";
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languagePref);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, languagePref);
		intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, languagePref);
//		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(intent, 10);
		} else {
			Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 10) {
			if (resultCode == RESULT_OK && data != null) {
				ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				txvResult.setText(result.get(0));
				Log.i("hindiii",result.get(0));
				String inp= result.get(0);
				text1=findViewById(R.id.textView);
				if (! Python.isStarted()) {
					Python.start(new AndroidPlatform(this));
				}
				//this will start python and and now we will create  python instance

				Python py=Python.getInstance();
				//nw create python object
				PyObject pyobj=py.getModule("myscript");
				PyObject obj=pyobj.callAttr("translate",inp);
				//now set returnd  text to textview
				text1.setText(obj.toString());
			}
		}
	}
}
