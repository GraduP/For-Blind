package com.example.myapplication.tts;

import static android.speech.tts.TextToSpeech.ERROR;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class SingleTonTTS {
    public static TextToSpeech tts;
    public static final int QUEUE_FLUSH = 0;
    private static SingleTonTTS ttsInstance = new SingleTonTTS();
    private Context appContext;
    private SingleTonTTS() { }
    public static Context get() {
        return getInstance().getContext();
    }
    public static synchronized SingleTonTTS getInstance() {
        return ttsInstance;
    }
    public void init(Context context) {
        if (appContext == null) {
            this.appContext = context;
        }
    }
    private Context getContext() {
        return appContext;
    }

    public void stop(){
        tts.stop();
    }
    public boolean speaking() {return tts.isSpeaking();}
    public void speak(String text) {
        tts = new TextToSpeech(get(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status != ERROR){
                    int result = tts.setLanguage(Locale.KOREA); // 언어 선택
                    if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                        //Log.e("TTS", "This Language is not supported");
                    }else{
                        tts.speak(text, QUEUE_FLUSH, null, null);
                    }
                }else{
                    //Log.e("TTS", "Initialization Failed!");
                }
            }
        });
    }
}




