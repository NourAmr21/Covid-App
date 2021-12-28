package com.example.covidapp;

import android.content.Context;
import android.media.AudioManager;
import android.net.rtp.AudioCodec;
import android.net.rtp.AudioGroup;
import android.net.rtp.AudioStream;
import android.net.rtp.RtpStream;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class Call extends AppCompatActivity {
    AudioGroup m_AudioGroup;
    AudioStream m_AudioStream;
    Button connect;
    Button disconnect;
    TextView edittext1;
    TextView edittext2;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audio.setMode(AudioManager.MODE_IN_COMMUNICATION);
            m_AudioGroup = new AudioGroup();
            m_AudioGroup.setMode(AudioGroup.MODE_NORMAL);
            m_AudioStream = new AudioStream(InetAddress.getByAddress(getLocalIPAddress()));
            int localPort = m_AudioStream.getLocalPort();
            m_AudioStream.setCodec(AudioCodec.PCMU);
            m_AudioStream.setMode(RtpStream.MODE_NORMAL);

            ((TextView) findViewById(R.id.lblLocalPort)).setText(String.valueOf(localPort));

            connect = findViewById(R.id.button1);
            connect.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String remoteAddress = ((EditText) findViewById(R.id.editText2)).getText().toString();
                    String remotePort = ((EditText) findViewById(R.id.editText1)).getText().toString();

                    try {
                        m_AudioStream.associate(InetAddress.getByName(remoteAddress), Integer.parseInt(remotePort));
                    } catch (NumberFormatException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (UnknownHostException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    m_AudioStream.join(m_AudioGroup);
                }
            });

            disconnect = findViewById(R.id.button2);
            disconnect.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    m_AudioStream.release();
                }
            });

        } catch (Exception e) {
            Log.e("----------------------", e.toString());
            e.printStackTrace();
        }
    }



    private byte[] getLocalIPAddress() {
        byte[] bytes = null;

        try {
//            // get the string ip
            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            String ip = Formatter.formatIpAddress(wifi.getConnectionInfo().getIpAddress());

//            // convert to bytes
            InetAddress inetAddress = null;
            try {
                inetAddress = InetAddress.getByName(ip);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            bytes = new byte[0];
            if (inetAddress != null) {
                bytes = inetAddress.getAddress();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "VOIP Failed", Toast.LENGTH_SHORT).show();
        }

        return bytes;
    }
}


