package com.example.cs360_nii_tagoe_option3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SMSNotificationActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_REQUEST_CODE = 100;

    private Button btnRequestPermission, btnSendTestSMS;
    private TextView tvPermissionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_notifications);

        btnRequestPermission = findViewById(R.id.btnRequestPermission);
        btnSendTestSMS = findViewById(R.id.btnSendTestSMS);
        tvPermissionStatus = findViewById(R.id.PermissionStatus);

        btnRequestPermission.setOnClickListener(v -> requestSmsPermission());
        btnSendTestSMS.setOnClickListener(v -> sendTestSMS());

        // Check current permission status on start
        checkSmsPermission();
    }

    // Request SMS permission if not already granted
    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    SMS_PERMISSION_REQUEST_CODE);
        } else {
            Toast.makeText(this, "SMS Permission already granted", Toast.LENGTH_SHORT).show();
            updateUI(true);
        }
    }

    // Check and update the UI based on SMS permission status
    private void checkSmsPermission() {
        boolean granted = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED;
        updateUI(granted);
    }

    // Update UI elements depending on whether permission is granted
    private void updateUI(boolean permissionGranted) {
        if (permissionGranted) {
            tvPermissionStatus.setText("Permission status: Granted");
            btnSendTestSMS.setVisibility(Button.VISIBLE);
        } else {
            tvPermissionStatus.setText("Permission status: Denied");
            btnSendTestSMS.setVisibility(Button.GONE);
        }
    }

    // Method to send a test SMS message if permission is granted
    private void sendTestSMS() {
        try {
            // Replace with a valid phone number for testing purposes
            String phoneNumber = "1234567890";
            String message = "This is a test SMS from MyWeightTracker app.";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "Test SMS sent.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "SMS failed, please try again.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Handle the result from the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS Permission Granted", Toast.LENGTH_SHORT).show();
                updateUI(true);
            } else {
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
                updateUI(false);
            }
        }
    }
}
