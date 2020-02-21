package com.rizky92.madedicodingsubmission2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rizky92.madedicodingsubmission2.notification.NotificationItems;

import java.util.ArrayList;
import java.util.List;

public class TestNotificationActivity extends AppCompatActivity {

    private static final int NOTIFICATION_REQUEST_CODE = 200;

    private static final int MAX_NOTIFICATION = 5;

    public static String CHANNEL_ID = "channel_1";
    public static CharSequence CHANNEL_NAME = "test channel";

    public static final String GROUP_KEY = "group_key";

    private EditText edtSender;
    private EditText edtMessage;
    private Button btnSend;

    private List<NotificationItems> stackNotificationItems = new ArrayList<>();

    private int idNotification = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_notification);

        edtSender = findViewById(R.id.edt_sender);
        edtMessage = findViewById(R.id.edt_message);
        btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationItems notificationItems = new NotificationItems();
                notificationItems.setId(idNotification);
                notificationItems.setTitle(edtSender.getText().toString());
                notificationItems.setDesc(edtMessage.getText().toString());

                if (TextUtils.isEmpty(edtSender.getText()) || TextUtils.isEmpty(edtMessage.getText())) {
                    Toast.makeText(TestNotificationActivity.this, "Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    idNotification++;
                    edtSender.setText("");
                    edtMessage.setText("");

                    InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(edtMessage.getWindowToken(), 0);
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        stackNotificationItems.clear();
        idNotification = 0;
    }
}
