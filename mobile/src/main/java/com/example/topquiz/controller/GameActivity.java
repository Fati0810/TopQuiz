package com.example.topquiz.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.topquiz.R;
import com.example.topquiz.model.Question;
import com.example.topquiz.model.QuestionBank;
import com.example.topquiz.model.User;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestionTextView;
    private Button mButtonUn;
    private Button mButtonDeux;
    private Button mButtonTrois;
    private Button mButtonQuatre;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private int mRemainingQuestionCount;
    private int mScore;
    private boolean mEnableTouchEvents;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTION = "BUNDLE_STATE_QUESTION";
    public static final String BUNDLE_STATE_QUESTIONBANK = "questionBank";

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mRemainingQuestionCount);
        outState.putParcelable(BUNDLE_STATE_QUESTIONBANK, mQuestionBank);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        User mUser = intent.getParcelableExtra("user");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mQuestionTextView = findViewById(R.id.game_activity_textview_question);
        mButtonUn = findViewById(R.id.game_activity_button_1);
        mButtonDeux = findViewById(R.id.game_activity_button_2);
        mButtonTrois = findViewById(R.id.game_activity_button_3);
        mButtonQuatre = findViewById(R.id.game_activity_button_4);

        mButtonUn.setOnClickListener(this);
        mButtonDeux.setOnClickListener(this);
        mButtonTrois.setOnClickListener(this);
        mButtonQuatre.setOnClickListener(this);

        mEnableTouchEvents = true;

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
            mQuestionBank = savedInstanceState.getParcelable(BUNDLE_STATE_QUESTIONBANK);
        } else {
            mScore = 0;
            mRemainingQuestionCount = 3;
            mQuestionBank = generateQuestions();
        }

        mCurrentQuestion = mQuestionBank.getCurrentQuestion();

        if (mUser != null) {
            String message = getString(R.string.welcome_user, mUser.getFirstName());
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

        displayQuestion(mCurrentQuestion);
    }


    private void displayQuestion(final Question question) {
        mQuestionTextView.setText(question.getQuestion());
        mButtonUn.setText(question.getChoiceList().get(0));
        mButtonDeux.setText(question.getChoiceList().get(1));
        mButtonTrois.setText(question.getChoiceList().get(2));
        mButtonQuatre.setText(question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Context context = getApplicationContext();

        Question question1 = new Question(
                context.getString(R.string.question_android),
                Arrays.asList(
                        context.getString(R.string.answer_android_1),
                        context.getString(R.string.answer_android_2),
                        context.getString(R.string.answer_android_3),
                        context.getString(R.string.answer_android_4)
                ),
                0
        );

        Question question2 = new Question(
                context.getString(R.string.question_moon),
                Arrays.asList(
                        context.getString(R.string.answer_moon_1),
                        context.getString(R.string.answer_moon_2),
                        context.getString(R.string.answer_moon_3),
                        context.getString(R.string.answer_moon_4)
                ),
                3
        );

        Question question3 = new Question(
                context.getString(R.string.question_simpsons),
                Arrays.asList(
                        context.getString(R.string.answer_simpsons_1),
                        context.getString(R.string.answer_simpsons_2),
                        context.getString(R.string.answer_simpsons_3),
                        context.getString(R.string.answer_simpsons_4)
                ),
                3
        );

        return new QuestionBank(Arrays.asList(question1, question2, question3));
    }

    @Override
    public void onClick(View view) {
        int index;

        if (view == mButtonUn) {
            index = 0;
        } else if (view == mButtonDeux) {
            index = 1;
        } else if (view == mButtonTrois) {
            index = 2;
        } else if (view == mButtonQuatre) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + view);
        }

        boolean isCorrect = index == mCurrentQuestion.getAnswerIndex();

        if (isCorrect) {
            view.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.quiz_button_correct));
            Toast.makeText(this, getString(R.string.toast_correct), Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            view.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.quiz_button_wrong));
            Toast.makeText(this, getString(R.string.toast_incorrect), Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;

        new Handler().postDelayed(() -> {
            view.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary));

            mRemainingQuestionCount--;

            if (mRemainingQuestionCount > 0) {
                mCurrentQuestion = mQuestionBank.getNextQuestion();
                displayQuestion(mCurrentQuestion);
            } else {
                endGame();
            }

            mEnableTouchEvents = true;
        }, 1000);
    }



    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_well_done))
                .setMessage(getString(R.string.dialog_score, mScore))
                .setPositiveButton(getString(R.string.dialog_ok), (dialog, which) -> {
                    Intent intent = new Intent();
                    intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                    setResult(RESULT_OK, intent);
                    finish();
                })
                .create()
                .show();
    }

}
