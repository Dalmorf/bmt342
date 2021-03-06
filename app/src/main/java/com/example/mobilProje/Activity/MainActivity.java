package com.example.mobilProje.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobilProje.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnStartGame,btnSingleWord,btnParagraph,btnHighScore,btnHParagraph,btnHSingleWord;
    final String prefName = Constants.IS_FIRST_LOGIN;
    static int test = 0;
    static boolean kelimedenMiGeldi = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.typing_test));
        initializeComponents();
        initializeSharedPreferences();

        if(kelimedenMiGeldi){
            Intent intent = getIntent();
            String s = intent.getStringExtra("Kelime");
            TextView textCurrentUser = findViewById(R.id.textCurrentUser);
            textCurrentUser.setText(s);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnParagraph:
                startParagraph();
                break;
            case R.id.btnSingleWord:
                startSingleWord();
                break;
            case R.id.btnStartGame:
                startGameSelection();
                initializeToolbar();
                break;
            case R.id.btnHighScore:
                startHighScoreSelection();
                initializeToolbar();
                break;
            case R.id.btnHighScoreParagraph:
                startHighScoreParagraph();
                break;
            case R.id.btnHighScoreSingleWord:
                startHighScoreSingleWord();
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        btnHSingleWord.setVisibility(View.GONE);
        btnHParagraph.setVisibility(View.GONE);
        btnSingleWord.setVisibility(View.GONE);
        btnParagraph.setVisibility(View.GONE);
        btnStartGame.setVisibility(View.VISIBLE);
        btnHighScore.setVisibility(View.VISIBLE);
        removeToolbar();
        return super.onOptionsItemSelected(item);
    }

    private void initializeComponents(){
        btnStartGame = findViewById(R.id.btnStartGame);
        btnParagraph = findViewById(R.id.btnParagraph);
        btnSingleWord = findViewById(R.id.btnSingleWord);
        btnHighScore = findViewById(R.id.btnHighScore);
        btnHParagraph = findViewById(R.id.btnHighScoreParagraph);
        btnHSingleWord = findViewById(R.id.btnHighScoreSingleWord);
        btnParagraph.setVisibility(View.GONE);
        btnSingleWord.setVisibility(View.GONE);
        btnHParagraph.setVisibility(View.GONE);
        btnHSingleWord.setVisibility(View.GONE);
        btnHighScore.setOnClickListener(this);
        btnStartGame.setOnClickListener(this);
        btnSingleWord.setOnClickListener(this);
        btnParagraph.setOnClickListener(this);
        btnHParagraph.setOnClickListener(this);
        btnHSingleWord.setOnClickListener(this);
    }
    private void initializeSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(prefName, Context.MODE_PRIVATE);
        if (test++ == 0){
            initializeDialog();
        }
    }
    private void initializeDialog() {
        final EditText input = new EditText(this);
        AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.nickname))
                .setView(input)
                .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = getSharedPreferences(Constants.USER_PREFERENCE,Context.MODE_PRIVATE).edit();
                        editor.putString(Constants.USER_NICK,input.getText().toString());
                        editor.apply();
                        //recreate();
                        String currentUserText = getString(R.string.current_user) + " " +
                                getSharedPreferences(Constants.USER_PREFERENCE,Context.MODE_PRIVATE).
                                        getString(Constants.USER_NICK,getString(R.string.OK));
                        TextView textCurrentUser = findViewById(R.id.textCurrentUser);
                        textCurrentUser.setText(currentUserText);
                    }
                }).create();
        builder.show();
    }
    private void initializeToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    private void removeToolbar(){
        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
    private void startGameSelection(){
        btnStartGame.setVisibility(View.GONE);
        btnHighScore.setVisibility(View.GONE);
        btnSingleWord.setVisibility(View.VISIBLE);
        btnParagraph.setVisibility(View.VISIBLE);

    }
    private void startHighScoreSelection() {
        btnStartGame.setVisibility(View.GONE);
        btnHighScore.setVisibility(View.GONE);
        btnHParagraph.setVisibility(View.VISIBLE);
        btnHSingleWord.setVisibility(View.VISIBLE);

    }
    private void startSingleWord(){
        startActivity(new Intent(this,SingleWordActivity.class));
    }
    private void startParagraph(){

    }
    private void startHighScoreSingleWord() {
        Intent intent = new Intent(this,HighScoreActivity.class);
        intent.putExtra(Constants.GAME_TYPE,Constants.SINGLE_WORD);
        startActivity(intent);
    }
    private void startHighScoreParagraph() {
        Intent intent = new Intent(this,HighScoreActivity.class);
        intent.putExtra(Constants.GAME_TYPE,Constants.PARAGRAPH);
        startActivity(intent);
    }
}
