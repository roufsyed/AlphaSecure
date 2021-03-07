package com.example.alphasecure;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class login_adapter extends RecyclerView.Adapter<login_adapter.login_viewholder> {

    private Context context;
    private ArrayList login_id, login_title, login_username, login_passsword, login_website, login_notes;
    int position;

    //CONSTRUCTOR
    login_adapter(Context context, ArrayList login_id, ArrayList login_title, ArrayList login_username,
                  ArrayList login_passsword, ArrayList login_website, ArrayList login_notes){
        //making all the variables global in our class so we can use them in our class wherever needed
        this.context = context;
        this.login_id = login_id;
        this.login_title = login_title;
        this.login_username = login_username;
        this.login_passsword = login_passsword;
        this.login_website = login_website;
        this.login_notes = login_notes;
    }

    @NonNull
    @Override
    public login_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.login_row, parent, false);
        return new login_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull login_viewholder holder, int position) {
        //USED HOLDER OBJECT TO SET A TEXT TO OUR VIEWS
        holder.primary_title_tv.setText(String.valueOf(login_title.get(position)));
        holder.secondary_title_tv.setText(String.valueOf(login_username.get(position)));
        holder.login_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_row_intent = new Intent(context, login_ud.class);

                //TRANSFERING DATA(STRINGS) TO LOGIN_UD
                login_row_intent.putExtra("login_id", String.valueOf(login_id.get(position)));
                login_row_intent.putExtra("login_title", String.valueOf(login_title.get(position)));
                login_row_intent.putExtra("login_username", String.valueOf(login_username.get(position)));
                login_row_intent.putExtra("login_password", String.valueOf(login_passsword.get(position)));
                login_row_intent.putExtra("login_website", String.valueOf(login_website.get(position)));
                login_row_intent.putExtra("login_notes", String.valueOf(login_notes.get(position)));

                context.startActivity(login_row_intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return login_id.size();
    }

    public class login_viewholder extends RecyclerView.ViewHolder {
        TextView primary_title_tv, secondary_title_tv;
        LinearLayout login_row;

        public login_viewholder(@NonNull View itemView) {
            super(itemView);
            primary_title_tv = itemView.findViewById(R.id.primary_title_tv);
            secondary_title_tv = itemView.findViewById(R.id.secondary_title_tv);
            login_row = itemView.findViewById(R.id.login_row);
        }
    }
}
