package com.natallia.radaman.epamlab2module2;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * It is the main activity of the Application which is protected via custom dangerous permission.
 * It is used for writing email and deliver it via standard Android Email Application.
 *
 * @author Natallia Radaman
 * @version 0.1
 */
public class MainActivity extends AppCompatActivity {
    private Button btnBackFirstApp;
    private Button btnSendEmail;
    private EditText emailAddress;
    private EditText letterTopic;
    private EditText letterBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBackFirstApp = (Button) findViewById(R.id.buttonBackFirstApp);
        btnSendEmail = (Button) findViewById(R.id.buttonSendEmail);
        emailAddress = (EditText) findViewById(R.id.addressee);
        letterTopic = (EditText) findViewById(R.id.letterTopic);
        letterBody = (EditText) findViewById(R.id.letterBody);
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLetter();
            }
        });

        btnBackFirstApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName("com.natallia.radaman.epamlaboratory2",
                        "com.natallia.radaman.epamlaboratory2.MainActivity"));
                startActivity(intent);
            }
        });
    }

    /**
     * Take String values from EditText fields and then send the letter via the method
     * transmitLetter(String[] addresses, String letterTopic, String letterBody).
     * <p>
     * This method don't throw exception and need any parameters.
     * <p>
     * {@link #transmitLetter(String[], String, String)}
     */
    @Nullable
    private void sendLetter() {
        String letterAddress = emailAddress.getText().toString();
        String[] addresses = letterAddress.split(",");
        String topic = letterTopic.getText().toString();
        String message = letterBody.getText().toString();
        transmitLetter(addresses, topic, message);
    }

    /**
     * Send the string data to the Android Default Email Program.
     * <p>
     * If PackageManager doesn't have any program for the delivery email, toast message will be shown.
     * Otherwise standard program activity for the delivering letters will be opened.
     *
     * @param addresses   String[] may contain one or more email addresses.
     * @param letterTopic String contains the topic of the letter.
     * @param letterBody  String contains text message.
     */
    private void transmitLetter(String[] addresses, String letterTopic, String letterBody) {
        Intent intentSendLetter = new Intent(Intent.ACTION_SENDTO);
        intentSendLetter.setData(Uri.parse("mailto:"))
                .putExtra(Intent.EXTRA_EMAIL, addresses)
                .putExtra(Intent.EXTRA_SUBJECT, letterTopic)
                .putExtra(Intent.EXTRA_TEXT, letterBody);
        if (intentSendLetter.resolveActivity(getPackageManager()) != null) {
            startActivity(intentSendLetter);
        } else {
            Toast toastEmailClient = Toast.makeText(getApplicationContext(), R.string.sendEmailProgram, Toast
                    .LENGTH_SHORT);
            toastEmailClient.show();
        }
    }
}
