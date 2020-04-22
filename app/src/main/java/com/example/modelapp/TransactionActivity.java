package com.example.modelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.modelapp.WelcomeActivity.tt;

public class TransactionActivity extends AppCompatActivity implements TransactionDialog.stringListener {

    FloatingActionButton addbutton;
    String custid;
    ArrayList<PostT> mytransactions = new ArrayList<>();
    private TransactionAdapter transactionAdapter;
    private RecyclerView transaction_view;
    SwipeRefreshLayout nSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        if(getIntent().hasExtra("cust_id")){
            custid = getIntent().getStringExtra("cust_id");
        }
        transaction_view = (RecyclerView) findViewById(R.id.transaction_view);
        transaction_view.setLayoutManager(new LinearLayoutManager(this));
        nSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_transaction);

        displayTransactions();


        nSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayTransactions();
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        nSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        addbutton=(FloatingActionButton)findViewById(R.id.add_transaction);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransactionDialog td = new TransactionDialog();
                td.show(getSupportFragmentManager(),"transaction dialog");
            }
        });


    }

    private void displayTransactions() {

        ApiInterface apiInterface = ApiClient.getApiClinet().create(ApiInterface.class);

        Log.i("t-", "" + tt+""+custid);
        Call<List<PostT>> call = apiInterface.getPostT(tt,custid);


        call.enqueue(new Callback<List<PostT>>() {
            @Override
            public void onResponse(Call<List<PostT>> call, Response<List<PostT>> response) {

                Log.i("t-on", "" + response);
                if (!response.isSuccessful()) {
                    Toast.makeText(TransactionActivity.this, "" + response.code(), Toast.LENGTH_LONG);
                    return;
                }
                mytransactions = new ArrayList<>(response.body());
                if (!mytransactions.isEmpty()) {
                    Log.i("mytransactions", "" + mytransactions);
                    transactionAdapter = new TransactionAdapter(TransactionActivity.this, mytransactions);
                    transactionAdapter.notifyDataSetChanged();
                    transaction_view.setAdapter(transactionAdapter);
//

                } else {
                    Log.i("mypap", "" + mytransactions);
                    Toast.makeText(TransactionActivity.this, "No Customers", Toast.LENGTH_LONG).show();
                }

            }
//s

            @Override
            public void onFailure(Call<List<PostT>> call, Throwable t) {
                Log.i("t-failure", "" + t.getMessage());
            }

        });

    }

    public void applyfun(String itype, String iamount,String ireason){
        String stype = itype;
        String samount = iamount;
        String sreason = ireason;

        ApiInterface api = ApiClient.getApiClinet().create(ApiInterface.class);
       Call<String> call = api.getTransdet(tt, custid, stype,samount,sreason);

       call.enqueue(new Callback<String>() {
           @Override
           public void onResponse(Call<String> call, Response<String> response) {
               if (response.isSuccessful()) {
                   if (response.body() != null) {
                       Log.i("r-onSuccess", response.body().toString());
                       String jsonresponse = response.body().toString();
                       try {
                           parseCusRegData(jsonresponse);
                       } catch (JSONException e) {
                           Log.i("error", "" + e);
                           e.printStackTrace();
                       }

                   } else {
                       Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                   }
               }
           }

           @Override
           public void onFailure(Call<String> call, Throwable t) {

           }
       });
   }
    private void parseCusRegData(String jsonresponse) throws JSONException {
        Log.i("response", "" + jsonresponse);
        JSONObject jsonObject = new JSONObject(jsonresponse);
        if (jsonObject.optString("status").equals("true")) {

            Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }
    }
}
