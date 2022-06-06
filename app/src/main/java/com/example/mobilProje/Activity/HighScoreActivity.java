package com.example.mobilProje.Activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilProje.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HighScoreActivity extends AppCompatActivity {
    private List<User> userData;
    public static List<String> keyList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HighScoreAdapter highScoreAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        setTitle(getString(R.string.high_score_title));
        recyclerView = findViewById(R.id.recyclerViewHighScore);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userData = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.USER_SCORE);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userData.clear();
                keyList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        User user = snapshot.getValue(User.class);
                        if (user != null) {

                                userData.add(user);

                        }
                    }
                    Collections.sort(userData,User.sortByScore);

                    for(User user : userData){
                        String debug = user.getUserId();
                        keyList.add(user.getUserId());
                    }

                    highScoreAdapter = new HighScoreAdapter(getApplicationContext(),userData);
                    recyclerView.setAdapter(highScoreAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //empty method
            }
        });
    }

}