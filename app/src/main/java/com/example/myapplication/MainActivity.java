package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    EditText firstNo, secondNo;
    Button btnMultiply;
    TextView result;
    MultiplyInterface multiplyInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNo = (EditText) findViewById(R.id.firstNo);
        secondNo = (EditText) findViewById(R.id.secondNo);
        result = (TextView) findViewById(R.id.product);
        btnMultiply = (Button) findViewById(R.id.multiply);

        Intent multiplyIntent = new Intent(this,MultiplicationService.class);

        //bind service to interface
        bindService(multiplyIntent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    //connecting service to interface
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            multiplyInterface = MultiplyInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //when multiply button clicks..
    public void click(View v) {
        int firstNumber = Integer.parseInt(firstNo.getText().toString());
        int secondNumber = Integer.parseInt(secondNo.getText().toString());
        try {
            int output = multiplyInterface.multiplyValues(firstNumber, secondNumber);
            result.setText(output+"");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}