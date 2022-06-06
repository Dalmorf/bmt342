package com.example.mobilProje.Activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilProje.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.ViewHolder> {
    private List<User> userList;
    private Context context;
    private int btnId = 0;


    public HighScoreAdapter(Context context,List<User> userList) {
        this.userList = userList;
        this.context = context;

    }

    @NonNull
    @Override
    public HighScoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_high_score_list,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreAdapter.ViewHolder holder, int position) {
        String userNickText = context.getString(R.string.nickname)+ " " + userList.get(position).getUserName();
        String userLetterText = context.getString(R.string.correct_letter_count)+ " " + userList.get(position).getUserLetterCount();
        String userWPMText = context.getString(R.string.typing_speed)+ " " + userList.get(position).getUserScore() + " WPM";
        holder.textViewUserNick.setText(userNickText);
        holder.textViewUserScore.setText(userLetterText);
        holder.textViewUserLetter.setText(userWPMText);
        holder.btnSil.setId(btnId++);

        holder.btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.USER_SCORE);
                int btnId = view.getId();
                String id = HighScoreActivity.keyList.get(btnId);
                databaseReference.child(id).removeValue();
                context.startActivity(new Intent(context, HighScoreActivity.class));
            }
        });


        String currentUserText = context.getSharedPreferences(Constants.USER_PREFERENCE,Context.MODE_PRIVATE).
                        getString(Constants.USER_NICK,context.getString(R.string.OK));

        if(currentUserText.equals(userList.get(position).getUserName()))
            holder.btnSil.setVisibility(View.VISIBLE);

        switch (position){
            case 0:
                holder.imgUserScore.setImageResource(R.drawable.gold_medal);
                break;
            case 1:
                holder.imgUserScore.setImageResource(R.drawable.silver_medal);
                break;
            case 2:
                holder.imgUserScore.setImageResource(R.drawable.bronze_medal);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewUserNick,textViewUserScore,textViewUserLetter;
        private Button btnSil;
        private ImageView imgUserScore;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserLetter = itemView.findViewById(R.id.txtCorrectLetterCount);
            textViewUserNick = itemView.findViewById(R.id.txtHighScoreNick);
            textViewUserScore = itemView.findViewById(R.id.txtHighScoreScore);
            imgUserScore = itemView.findViewById(R.id.imgHighScoreImg);
            btnSil = itemView.findViewById(R.id.btn_sil);
        }
    }
}
