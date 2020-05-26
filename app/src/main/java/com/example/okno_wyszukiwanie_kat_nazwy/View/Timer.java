package com.example.okno_wyszukiwanie_kat_nazwy.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.okno_wyszukiwanie_kat_nazwy.Model.Category;
import com.example.okno_wyszukiwanie_kat_nazwy.Model.Country;
import com.example.okno_wyszukiwanie_kat_nazwy.Model.TimerModel;
import com.example.okno_wyszukiwanie_kat_nazwy.R;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.ListViewAdapter;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.ListViewTimerAdapter;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.SD;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.StaticMethods;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Timer extends AppCompatActivity {

    private ArrayList<TimerModel> timer = new ArrayList<>();
    private String[] timerNamesArray = new String[timer.size()];
    private String[] timerTimesArray = new String[timer.size()];
    private ListView mListView;
    private ListViewTimerAdapter listViewAdapterTimer;
    private static Context context;
    private long timeLeftInMiliseconds = 600000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Timer.context = getApplicationContext();
        mListView = findViewById(R.id.listViewtimer);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                timer.add(new TimerModel(
                        "1",
                        "test",
                        "11:11")
                );
                timerNamesArray = StaticMethods.getTimerNames(timer).toArray(timerNamesArray);
                timerTimesArray = StaticMethods.getTimerTimes(timer).toArray(timerTimesArray);

                listViewAdapterTimer = new ListViewTimerAdapter(Timer.this, timerNamesArray, timerTimesArray);
                mListView.setAdapter(listViewAdapterTimer);
            }
        });
    }
    public static Context getTimerContext()
    {
        return Timer.context;
    }



}
