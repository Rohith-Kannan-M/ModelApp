package com.example.modelapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

import static com.example.modelapp.MainActivity.staticuserid;
import static com.example.modelapp.WelcomeActivity.tt;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    private ArrayList<Post> myposts=new ArrayList<>();
    private Context context;


    public CustomerAdapter(Context context, ArrayList<Post> myposts){
        this.myposts=myposts;
        this.context=context;

    }

    @NonNull
    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customers_list,viewGroup,false);
        return new CustomerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.ViewHolder viewHolder, int i) {
        //String id = myposts.get(i).getUserid();


        //Log.i("stst",""+t);
        //if(!id.equals(t)){


        //}else{
            viewHolder.tv1.setText(myposts.get(i).getCustomername());
            viewHolder.tv2.setText(myposts.get(i).getCustomernumber());
        //}
        //Log.i("id da",""+id);
            viewHolder.carda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,""+myposts.get(i).getCustomerid(),Toast.LENGTH_LONG).show();
                    Log.i("clickedd",""+myposts.get(i).getCustomerid());
                   String custid= myposts.get(i).getCustomerid();
                    Log.i("cuu",""+custid);
                    Intent i = new Intent(context,TransactionActivity.class);
                    i.putExtra("cust_id",custid);
                    context.startActivity(i);
                }
            });

    }

    @Override
    public int getItemCount() {
        return myposts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv1;
        private TextView tv2;
        LinearLayout parentLayout;
        CardView carda;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=(TextView)itemView.findViewById(R.id.customername);
            tv2=(TextView)itemView.findViewById(R.id.customernumber);
            parentLayout =(LinearLayout)itemView.findViewById(R.id.parent_layout);
            carda = (CardView)itemView.findViewById(R.id.carda);

        }
    }

}


/*

 */