package com.example.okno_wyszukiwanie_kat_nazwy.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.okno_wyszukiwanie_kat_nazwy.R;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.SD;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.StaticMethods;

import java.util.Objects;

public class RandomRecipeActivity extends AppCompatActivity {

    private SensorManager mSensorManager;

    private float mAcceleration;
    private float mAccelerationCurrent;
    private float mAccelerationLast;

    private TextView mRandomTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_recipe);

        mRandomTextView = findViewById(R.id.randomTextView);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).
                registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                        SensorManager.SENSOR_DELAY_NORMAL);

        mAcceleration = 10f;
        mAccelerationCurrent = SensorManager.GRAVITY_EARTH;
        mAccelerationLast = SensorManager.GRAVITY_EARTH;

        if(StaticMethods.getScreenWidth() <= 480 || StaticMethods.getScreenHeight() <= 800) {
            mRandomTextView.setTextSize(28f);
        }
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            mAccelerationLast = mAccelerationCurrent;
            mAccelerationCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelerationCurrent-mAccelerationLast;
            mAcceleration = mAcceleration * 0.9f + delta;

            if(mAcceleration > 12)
            {
                Intent intent = new Intent(RandomRecipeActivity.this, RecipeDetailsActivity.class);
                intent.putExtra("recipeKey", SD.getRandomRecipe());
                RandomRecipeActivity.this.startActivity(intent);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}
