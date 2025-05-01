package com.example.topquiz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;

import com.example.topquiz.R;
import com.example.topquiz.model.User;

public class MainActivity extends AppCompatActivity {
    private TextView mGreetingTextView;
    private EditText mNameEditText;
    private Button mPlayButton;
    private User mUser;
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    private static final String SHARED_PREF_USER_INFO_NAME = "SHARED_PREF_USER_INFO_NAME";
    private static final String SHARED_PREF_USER_INFO_SCORE = "SHARED_PREF_USER_INFO_SCORE";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GAME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            // Sauvegarde du score dans les SharedPreferences
            SharedPreferences preferences = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE);
            preferences.edit()
                    .putInt(SHARED_PREF_USER_INFO_SCORE, score)
                    .apply();

            // Récupération du nom de l'utilisateur et mise à jour du message d'accueil avec le score
            String userName = preferences.getString(SHARED_PREF_USER_INFO_NAME, "Player");
            mGreetingTextView.setText("Well done, " + userName + "! Your final score is: " + score);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mGreetingTextView = findViewById(R.id.textview);
        mNameEditText = findViewById(R.id.edit_text);
        mPlayButton = findViewById(R.id.button);
        mPlayButton.setEnabled(false);

        mUser = new User();

        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE);
        String firstName = preferences.getString(SHARED_PREF_USER_INFO_NAME, null);
        int lastScore = preferences.getInt(SHARED_PREF_USER_INFO_SCORE, -1);

        // Affichage du message de bienvenue avec le score précédent (si disponible)
        if (firstName != null) {
            mUser.setFirstName(firstName);
            mNameEditText.setText(firstName);
            mNameEditText.setSelection(firstName.length());
            if (lastScore != -1) {
                mGreetingTextView.setText("Welcome back, " + firstName + "! Your last score was: " + lastScore);
            } else {
                mGreetingTextView.setText("Welcome back, " + firstName + "!");
            }
        } else {
            // Message de bienvenue générique si aucun nom enregistré
            mGreetingTextView.setText("Welcome, player! Ready to play?");
        }

        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPlayButton.setEnabled(!editable.toString().isEmpty());
                mUser.setFirstName(editable.toString());
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
                mUser.setFirstName(mNameEditText.getText().toString());

                preferences.edit()
                        .putString(SHARED_PREF_USER_INFO_NAME, mUser.getFirstName())
                        .apply();
            }
        });

    }
}

//  Probème : - le focus lorsque j'entre dans l'app a partir du telephone
//  - Le texte ne change pas
//  - startActivityForResult depreciated