package com.example.su;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EnterPersonalDetailsActivity extends AppCompatActivity {

    final String[] SIDES = new String[]{"L", "R"};
    int sidePosition;
    String[] HOSTELS;
    int hostelPostion;
    FirebaseFirestore db;
    String name;
    String email;

    AutoCompleteTextView hostelPicker;
    AutoCompleteTextView sidePicker;


    TextInputEditText roomNoEditText;
    TextInputEditText phoneEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_personal_details);

        db = FirebaseFirestore.getInstance();

        phoneEditText = findViewById(R.id.phoneEditText);
        roomNoEditText = findViewById(R.id.roomNoEditText);

        name = GoogleSignIn.getLastSignedInAccount(getApplicationContext()).getDisplayName();
        email = GoogleSignIn.getLastSignedInAccount(getApplicationContext()).getEmail();

        HOSTELS = new String[]{getString(R.string.hostel_vm), getString(R.string.hostel_vk), getString(R.string.hostel_budh), getString(R.string.hostel_ram), getString(R.string.hostel_krishna), getString(R.string.hostel_gandhi), getString(R.string.hostel_gautam), getString(R.string.hostel_shankar), getString(R.string.hostel_meera), getString(R.string.hostel_malviya)};

        ArrayAdapter<String> hostelAdapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu_popup_item,
                        HOSTELS);
        hostelPicker = findViewById(R.id.filled_exposed_dropdown);
        hostelPicker.setAdapter(hostelAdapter);


        ArrayAdapter<String> sideAdapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu_popup_item,
                        SIDES);
        sidePicker = findViewById(R.id.filled_exposed_dropdown_2);
        sidePicker.setAdapter(sideAdapter);

        final TextInputLayout sideTextLayout = findViewById(R.id.sideInputTextLayout);

        hostelPicker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hostelPostion = position;
                if (position == 0 || position == 1 || position == 6) {
                    sideTextLayout.setVisibility(View.VISIBLE);
                } else {
                    sideTextLayout.setVisibility(View.GONE);
                }
            }
        });

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> data = new HashMap<>();
                data.put(getString(R.string.firebase_database_student_email_field), email);
                data.put(getString(R.string.firebase_database_student_name_field), name);
                data.put(getString(R.string.firebase_database_student_room_no_field), getRoomNo());
                data.put(getString(R.string.firebase_database_student_phone_no_field), phoneEditText.getText().toString());

                db.collection(getString(R.string.firebase_database_students_collection))
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                startActivity(new Intent(EnterPersonalDetailsActivity.this, MainActivity.class));
                                finish();
                            }
                        });
            }
        });
    }

    private String getRoomNo() {
        if (hostelPostion == 0 || hostelPostion == 1 || hostelPostion == 6) {
            return HOSTELS[hostelPostion] + roomNoEditText.getText().toString();
        } else {
            return HOSTELS[hostelPostion] + roomNoEditText.getText().toString() + SIDES[sidePosition];
        }
    }

}
