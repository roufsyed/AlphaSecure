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

public class bank_adapter extends RecyclerView.Adapter<bank_adapter.bank_viewholder> {

	private Context context;
	private ArrayList bank_id, bank_title, bank_name_of_the_bank, bank_name_on_account,
			bank_account_number, bank_pin, bank_phone, bank_address, bank_notes;
	int position;

	//CONSTRUCTOR
	bank_adapter(Context context, ArrayList bank_id, ArrayList bank_title, ArrayList bank_name_of_the_bank,
	             ArrayList bank_name_on_account, ArrayList bank_account_number,
	             ArrayList bank_pin, ArrayList bank_phone, ArrayList bank_address, ArrayList bank_notes){
		//making all the variables global in our class so we can use them in our class wherever needed
		this.context = context;
		this.bank_id = bank_id;
		this.bank_title = bank_title;
		this.bank_name_of_the_bank = bank_name_of_the_bank;
		this.bank_name_on_account = bank_name_on_account;
		this.bank_account_number = bank_account_number;
		this.bank_pin = bank_pin;
		this.bank_phone = bank_phone;
		this.bank_address = bank_address;
		this.bank_notes = bank_notes;
	}

	@NonNull
	@Override
	public bank_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.bank_row, parent, false);
		return new bank_viewholder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull bank_viewholder holder, int position) {
		//USED HOLDER OBJECT TO SET A TEXT TO OUR VIEWS
		String primary = (String) bank_title.get(position);
		String secondary = (String) bank_name_on_account.get(position);
		try {
			holder.primary_title_tv.setText(String.valueOf(AESUtils.decrypt(primary)));
			holder.secondary_title_tv.setText(String.valueOf(AESUtils.decrypt(secondary)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		holder.bank_row.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent bank_row_intent = new Intent(context, bank_ud.class);

				//TRANSFERING DATA(STRINGS) TO bank_UD
				bank_row_intent.putExtra("bank_id", String.valueOf(bank_id.get(position)));
				bank_row_intent.putExtra("bank_title", String.valueOf(bank_title.get(position)));
				bank_row_intent.putExtra("bank_name_of_the_bank", String.valueOf(bank_name_of_the_bank.get(position)));
				bank_row_intent.putExtra("bank_name_on_account", String.valueOf(bank_name_on_account.get(position)));
				bank_row_intent.putExtra("bank_account_number", String.valueOf(bank_account_number.get(position)));
				bank_row_intent.putExtra("bank_pin", String.valueOf(bank_pin.get(position)));
				bank_row_intent.putExtra("bank_phone", String.valueOf(bank_phone.get(position)));
				bank_row_intent.putExtra("bank_address", String.valueOf(bank_address.get(position)));
				bank_row_intent.putExtra("bank_notes", String.valueOf(bank_notes.get(position)));

				context.startActivity(bank_row_intent);
			}
		});
	}

	@Override
	public int getItemCount() {
		return bank_id.size();
	}

	public class bank_viewholder extends RecyclerView.ViewHolder {
		TextView primary_title_tv, secondary_title_tv;
		LinearLayout bank_row;

		public bank_viewholder(@NonNull View itemView) {
			super(itemView);
			primary_title_tv = itemView.findViewById(R.id.primary_title_tv);
			secondary_title_tv = itemView.findViewById(R.id.secondary_title_tv);
			bank_row = itemView.findViewById(R.id.bank_row);
		}
	}
}
