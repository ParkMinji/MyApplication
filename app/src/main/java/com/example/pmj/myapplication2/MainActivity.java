package com.example.pmj.myapplication2;

import android.content.Intent;
import android.location.Address;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.ExtractedText;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    protected Button btSpeeh;
    protected TextView txSpeech;
    protected static final int SPEECH_CODE = 1234;
    protected TextToSpeech tts;

    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            tts.setLanguage(Locale.KOREAN);
            tts.setPitch(0.7f);
            tts.setSpeechRate(1.0f);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SPEECH_CODE){
            if(resultCode == RESULT_OK && data!=null){
                ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String strSpeech = arrayList.get(0);
                txSpeech.setText(strSpeech);
                Toast.makeText(getApplicationContext(), strSpeech, Toast.LENGTH_SHORT).show();
                tts.speak(strSpeech, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txSpeech = (TextView)findViewById(R.id.txSp);
        btSpeeh = (Button)findViewById(R.id.btSp);

        btSpeeh.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);  //음성인식기가 인식하는대로
                //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH); 의미 분석하고 표현
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Recognizeing...");
                startActivityForResult(intent, SPEECH_CODE);
            }
        });

        tts = new TextToSpeech(this, this);
    }
}