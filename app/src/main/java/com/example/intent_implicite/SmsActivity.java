package com.example.intent_implicite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SmsActivity extends AppCompatActivity {
    EditText txtnum,txtmsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        txtmsg=findViewById(R.id.txt_sms);
        txtnum=findViewById(R.id.num_tel);

    }
    public void btn_envoyer(View view) {
        testPermission();
    }
    public void envoyer_sms()
    {
        String no = txtnum.getText().toString();
        String msg = txtmsg.getText().toString();
//Intent la commonction entres les activites et aussi les composantes exterieurs
// Intent intent = new Intent(getApplicationContext(), SmsActivity.class);
// PendingIntent pi =PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
        if(checkSelfPermission(Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) return;
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(no, null, msg, null, null);
        Toast.makeText(getApplicationContext(), "Message bien envoyée!", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
// Checking whether user granted the permission ornot.
            if (grantResults.length > 0
                    && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
// Showing the toast message
                Toast.makeText(this,
                                "Permission Granted",
                                Toast.LENGTH_SHORT)
                        .show();
                envoyer_sms();
            } else {
                Toast.makeText(this,
                                " Permission Denied",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    public void testPermission()
    {
        if (checkSelfPermission(Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {
// Permission is not granted
// Should we show an explanation?
// No explanation needed; request thepermission
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.SEND_SMS},1);
// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
// app-defined int constant. The callback method gets the
// result of the request.
        } else {
// Permission has already been granted
            envoyer_sms();
        }
    }
}