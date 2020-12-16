package com.bba.datacollection;

import android.app.Activity;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
//    private TextView accelerometerView;
//    private TextView orientationView;
    private TextView net_status_check;
    private TextView accelerometerView;
    private TextView gyroscopeView;
    private TextView resultView;
    private SensorManager sensorManager;
    private MySensorEventListener sensorEventListener;
    private NetworkStateReceiver netWorkStateReceiver;
    private AndroidApplication app;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ""https://www.cnblogs.com/zhujiabin/p/4227785.html
        sensorEventListener = new MySensorEventListener();
        net_status_check = (TextView) this.findViewById(R.id.net_status);
        accelerometerView = (TextView) this.findViewById(R.id.accelerometer);
        gyroscopeView = (TextView) this.findViewById(R.id.gyroscope);
        resultView = (TextView) this.findViewById(R.id.result);
//        orientationView = (TextView) this.findViewById(R.id.orientationView);
        //获取感应器管理器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        app = (AndroidApplication)getApplication();

    }

    @Override
    protected void onResume()
    {
        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetworkStateReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);
        netWorkStateReceiver.set_view(net_status_check);
        System.out.println("注册");
        //获取加速度传感器
//        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    protected void start(View v){
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
    }
    protected void stop(View v){
        Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
        sensorManager.unregisterListener(sensorEventListener);
    }

    private final class MySensorEventListener implements SensorEventListener
    {
        //可以得到传感器实时测量出来的变化值
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            //得到加速度的值
            if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
            {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                x = Float.valueOf(String.format("%.3f",x));
                y = Float.valueOf(String.format("%.3f",y));
                z = Float.valueOf(String.format("%.3f",z));

                accelerometerView.setText("Accelerometer: " + x + "," + y + "," + z);
            }
            //得到陀螺仪的值
            if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE)
            {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                x = Float.valueOf(String.format("%.3f",x));
                y = Float.valueOf(String.format("%.3f",y));
                z = Float.valueOf(String.format("%.3f",z));
                gyroscopeView.setText("Gyroscope:    " + x + "," + y + "," + z);
            }

        }
        //重写变化
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {
        }
    }

    //暂停传感器的捕获
    @Override
    protected void onPause()
    {
        unregisterReceiver(netWorkStateReceiver);
        System.out.println("注销");
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }

}
