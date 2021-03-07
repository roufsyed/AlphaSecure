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

public class email_adapter extends RecyclerView.Adapter<email_adapter.email_viewholder> {

    private Context context;
    private ArrayList email_id, email_title, email_username, email_passsword, email_website, email_phone, email_notes;
    int position;

    //CONSTRUCTOR
    email_adapter(Context context, ArrayList email_id, ArrayList email_title, ArrayList email_username,
                  ArrayList email_passsword, ArrayList email_website, ArrayList email_phone, ArrayList email_notes){
        //making all the variables global in our class so we can use them in our class wherever needed
        this.context = context;
        this.email_id = email_id;
        this.email_title = email_title;
        this.email_username = email_username;
        this.email_passsword = email_passsword;
        this.email_website = email_website;
        this.email_phone = email_phone;
        this.email_notes = email_notes;

    }

    @NonNull
    @Override
    public email_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.email_row, parent, false);
        return new email_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull email_viewholder holder, int position) {
        //USED HOLDER OBJECT TO SET A TEXT TO OUR VIEWS
        String primary = (String) email_title.get(position);
        String secondary = (String) email_username.get(position);
        try {
            holder.primary_title_tv.setText(String.valueOf(AESUtils.decrypt(primary)));
            holder.secondary_title_tv.setText(String.valueOf(AESUtils.decrypt(secondary)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.email_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email_row_intent = new Intent(context, email_ud.class);

                //TRANSFERING DATA(STRINGS) TO email_UD
                email_row_intent.putExtra("email_id", String.valueOf(email_id.get(position)));
                email_row_intent.putExtra("email_title", String.valueOf(email_title.get(position)));
                email_row_intent.putExtra("email_username", String.valueOf(email_username.get(position)));
                email_row_intent.putExtra("email_password", String.valueOf(email_passsword.get(position)));
                email_row_intent.putExtra("email_website", String.valueOf(email_website.get(position)));
                email_row_intent.putExtra("email_phone", String.valueOf(email_phone.get(position)));
                email_row_intent.putExtra("email_notes", String.valueOf(email_notes.get(position)));

                context.startActivity(email_row_intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return email_id.size();
    }

    public class email_viewholder extends RecyclerView.ViewHolder {
        TextView primary_title_tv, secondary_title_tv;
        LinearLayout email_row;

        public email_viewholder(@NonNull View itemView) {
            super(itemView);
            primary_title_tv = itemView.findViewById(R.id.primary_title_tv);
            secondary_title_tv = itemView.findViewById(R.id.secondary_title_tv);
            email_row = itemView.findViewById(R.id.email_row);
        }
    }
}
