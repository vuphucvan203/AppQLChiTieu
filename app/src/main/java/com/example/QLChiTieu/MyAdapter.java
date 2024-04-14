package com.example.QLChiTieu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<String> rvId, rvDate , rvNote,rvAmount,rvIncome;
    private Object Text;

    public MyAdapter(ArrayList<String> inputId,ArrayList<String> inputDate,ArrayList<String> inputNote,ArrayList<String> inputAmount,ArrayList<String> inputIncome) {
        rvId = inputId;
        rvDate = inputDate;
        rvNote = inputNote;
        rvAmount=inputAmount;
        rvIncome=inputIncome;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvSubtitle;
        public  TextView tvJum;
        public  TextView tvJns;
        public ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.txttgl);
            tvSubtitle = (TextView) v.findViewById(R.id.txtnot);
            tvJum = (TextView) v.findViewById(R.id.txtamount);
            tvJns = (TextView) v.findViewById(R.id.txtjns);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, parent,
                false);
        //set view size, margins, padding, and other layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
// - retrieves an element from the dataset (ArrayList) at a specific position
// - sets the contents of the view with elements from the dataset
        final String name = rvDate.get(position);
        holder.tvTitle.setText(rvDate.get(position));
        holder.tvSubtitle.setText(rvNote.get(position));
        holder.tvJum.setText(rvAmount.get(position));
        String Expenses = rvIncome.get(position);
        holder.tvJns.setText(Expenses);
        String Expense = "Expenses";
        if (Expense.equals(Expenses)){
            holder.tvTitle.setBackgroundResource(R.drawable.tanggalan);
        }else{
            holder.tvTitle.setBackgroundResource(R.drawable.tanggalan2);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CheckNote.class);
                i.putExtra("id",rvId.get(position));
                view.getContext().startActivity(i);
            }
        });
        //int[] i=new int[10];
    }
    @Override
    public int getItemCount() {
//calculate the size of the dataset / amount of data displayed in the Recycler
        return rvDate.size();
    }
}