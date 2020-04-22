package com.example.modelapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.modelapp.MainActivity.staticuserid;
//192.168.106.8
public class WelcomeActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {

    PreferenceHelper preferenceHelper;
    public static String tt = "t";
    //ListView listView;
    ArrayList<Post> myposts = new ArrayList<>();
    private CustomerAdapter customerAdapter;
    private RecyclerView customer_view;
    FloatingActionButton add_customers;
    ApiInterface apiInterface;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        preferenceHelper = new PreferenceHelper(this);
        //Log.i("preference   ",""+preferenceHelper.getName());
        add_customers = (FloatingActionButton) findViewById(R.id.add);
        tt = preferenceHelper.getName();
        Log.i("preference   and t", "" + preferenceHelper.getName() + " and t is " + tt);
        //listView=(ListView)findViewById(R.id.listView);
        customer_view = (RecyclerView) findViewById(R.id.customer_view);
        customer_view.setHasFixedSize(true);
        customer_view.setLayoutManager(new LinearLayoutManager(this));

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_customers);

        displayCustomers();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayCustomers();
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        add_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExampleDialog exampleDialog = new ExampleDialog();
                exampleDialog.show(getSupportFragmentManager(), "example dialog");
            }
        });

    }

    private void displayCustomers() {
        apiInterface = ApiClient.getApiClinet().create(ApiInterface.class);

        Log.i("t-", "" + tt);
        Call<List<Post>> call = apiInterface.getPosts(tt);


        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                Log.i("on", "" + response);
                if (!response.isSuccessful()) {
                    Toast.makeText(WelcomeActivity.this, "" + response.code(), Toast.LENGTH_LONG);
                    return;
                }
                myposts = new ArrayList<>(response.body());
                if (!myposts.isEmpty()) {
                    Log.i("myposts", "" + myposts);
                    customerAdapter = new CustomerAdapter(WelcomeActivity.this, myposts);
                    customerAdapter.notifyDataSetChanged();
                    customer_view.setAdapter(customerAdapter);
//

                } else {
                    Log.i("mypa", "" + myposts);
                    Toast.makeText(WelcomeActivity.this, "No Customers", Toast.LENGTH_LONG).show();
                }

            }
//s

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.i("failure", "" + t.getMessage());
            }

        });

    }


    public void applyTexts(String username, String password) {

        String customername = username;
        String customernumber = password;

        ApiInterface api = ApiClient.getApiClinet().create(ApiInterface.class);

        Call<String> call = api.getCustomerRegi(tt, customername, customernumber);
        Log.i("r-Responsestring", call.toString() + "/" + tt + "/" + customername + "/" + customernumber);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Log.i("r-Responsestring", response.body().toString());
                //Toast.makeText()
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

                Log.i("failure", "" + call + "\n" + t);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                openAlertBox();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openAlertBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Log out?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //perform any action
                preferenceHelper.putIsLogin(false);
                Intent intent = new Intent(WelcomeActivity.this, LoginAcivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //perform any action
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder.create();
        alert11.show();

    }

}

//String id = myposts.get(position).getCustomerid();
//        Toast.makeText(this,""+id,Toast.LENGTH_LONG).show();