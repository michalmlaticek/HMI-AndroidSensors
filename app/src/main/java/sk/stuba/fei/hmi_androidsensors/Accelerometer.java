package sk.stuba.fei.hmi_androidsensors;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mlaticek on 2/22/2016.
 */
public class Accelerometer extends Fragment implements SensorEventListener {

    private Context context;

    private SensorManager sensorManager;
    private Sensor sensor;

    private float valueX = 0;
    private float valueY;
    private float valueZ;

    private TextView valueXui;
    private TextView valueYui;
    private TextView valueZui;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        View fragView = inflater.inflate(R.layout.accelerometer_fragment, container, false);
        initiView(fragView);

        return fragView;
    }

    private void initiView(View view) {
        valueXui = (TextView) view.findViewById(R.id.accValueX);
    }

    private void render() {

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        valueXui.setText(Float.toString(event.values[0]));
//        valueXui.setText(Float.toString(valueX++));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
