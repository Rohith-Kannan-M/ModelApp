package com.example.modelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>  {
    private ArrayList<PostT> mytransactions=new ArrayList<>();
    private Context context;

    public TransactionAdapter(Context context, ArrayList<PostT> mytransactions){
        this.mytransactions=mytransactions;
        this.context=context;

    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transactions_list,viewGroup,false);
        return new TransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv1.setText(mytransactions.get(i).getType());
        viewHolder.tv2.setText("₹"+mytransactions.get(i).getAmount());
        viewHolder.tv3.setText(mytransactions.get(i).getReason());
        viewHolder.tv4.setText("On "+mytransactions.get(i).getDatatime());

    }

    @Override
    public int getItemCount() {
        return mytransactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv1,tv2,tv3,tv4;
        CardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1=(TextView)itemView.findViewById(R.id.mtext1);
            tv2=(TextView)itemView.findViewById(R.id.mtext2);
            tv3=(TextView)itemView.findViewById(R.id.mtext3);
            tv4=(TextView)itemView.findViewById(R.id.mtext4);
            cv=(CardView)itemView.findViewById(R.id.card_vieww);

        }
    }

}
