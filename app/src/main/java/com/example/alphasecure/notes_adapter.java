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

public class notes_adapter extends RecyclerView.Adapter<notes_adapter.notes_viewholder> {

	private Context context;
	private ArrayList notes_id, notes_title, notes_body;

	//CONSTRUCTOR
	notes_adapter(Context context, ArrayList notes_id, ArrayList notes_title, ArrayList notes_body){
		//making all the variables global in our class so we can use them in our class wherever needed
		this.context = context;
		this.notes_id = notes_id;
		this.notes_title = notes_title;
		this.notes_body = notes_body;
	}

	@NonNull
	@Override
	public notes_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.notes_row, parent, false);
		return new notes_viewholder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull notes_viewholder holder, int position) {
		//USED HOLDER OBJECT TO SET A TEXT TO OUR VIEWS
		String primary = (String) notes_title.get(position);
		try {
			holder.primary_title_tv.setText(String.valueOf(AESUtils.decrypt(primary)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		holder.notes_row.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent notes_row_intent = new Intent(context, notes_ud.class);

				//TRANSFERING DATA(STRINGS) TO notes_UD
				notes_row_intent.putExtra("notes_id", String.valueOf(notes_id.get(position)));
				notes_row_intent.putExtra("notes_title", String.valueOf(notes_title.get(position)));
				notes_row_intent.putExtra("notes_body", String.valueOf(notes_body.get(position)));

				context.startActivity(notes_row_intent);
			}
		});
	}

	@Override
	public int getItemCount() {
		return notes_id.size();
	}

	public class notes_viewholder extends RecyclerView.ViewHolder {
		TextView primary_title_tv, secondary_title_tv;
		LinearLayout notes_row;

		public notes_viewholder(@NonNull View itemView) {
			super(itemView);
			primary_title_tv = itemView.findViewById(R.id.primary_title_tv);
			//secondary_title_tv = itemView.findViewById(R.id.secondary_title_tv);
			notes_row = itemView.findViewById(R.id.notes_row);
		}
	}
}
