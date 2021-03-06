package sk.stuba.fei.hmi_androidsensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Integer> selectedSensors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.sensorList);
        if (deviceSensors.size() > 0) {
            for (final Sensor sensor : deviceSensors) {
                CheckBox cb = new CheckBox(this);
                cb.setText(sensor.getName());
                SensorConfiguration sensorConfig = new SensorConfiguration(sensor.getType());
                if(sensorConfig.isActive()) {
                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                selectedSensors.add(sensor.getType());
                            } else {
                                selectedSensors.remove((Integer)sensor.getType());
                            }
                        }
                    });
                } else {
                    cb.setEnabled(false);
                }
                linearLayout.addView(cb);
            }
        } else {
            TextView tv = new TextView(this);
            tv.setText("No sensors found");
            linearLayout.addView(tv);
        }
    }

    public void startHMIActivity(View view) {
        if(selectedSensors.size()==0) {
            Context context = getApplicationContext();
            CharSequence text = "No Sensor Selected!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            Intent intent = new Intent(this, HMIActivity.class);
            intent.putExtra(AppConstants.SELECTED_SENSOR_TYPE_LIST, selectedSensors);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
