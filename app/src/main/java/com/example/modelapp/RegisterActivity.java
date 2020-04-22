package com.example.modelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.modelapp.MainActivity.staticuserid;

public class RegisterActivity extends AppCompatActivity {
    private EditText etname, etusername, etpassword;
    private Button btnregister;
    private TextView tvlogin;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        preferenceHelper = new PreferenceHelper(this);
        if(preferenceHelper.getIsLogin()){
            Intent intent = new Intent(RegisterActivity.this,WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }

        etname = (EditText) findViewById(R.id.etname);
        etusername = (EditText) findViewById(R.id.etusername);
        etpassword = (EditText) findViewById(R.id.etpassword);

        btnregister = (Button) findViewById(R.id.btn);
        tvlogin = (TextView) findViewById(R.id.tvlogin);

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginAcivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerMe();
            }
        });
    }

    private void registerMe() {

        final String name = etname.getText().toString();
        final String username = etusername.getText().toString();
        final String password = etpassword.getText().toString();

        ApiInterface api = ApiClient.getApiClinet().create(ApiInterface.class);

        Call<String> call = api.getUserRegi(name,username,password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("r-Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("r-onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        try {
                            parseRegData(jsonresponse);
                        } catch (JSONException e) {
                            Log.i("error",""+e);
                            e.printStackTrace();
                        }
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.i("failure",""+call+"\n"+t);

            }
        });
    }

    private void parseRegData(String response) throws JSONException {

        Log.i("response",""+response);
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.optString("status").equals("true")){

            staticuserid=jsonObject.optString("userid");
            Log.i("userr",""+staticuserid);
            saveInfo(response);

            Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this,WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }else {

            Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveInfo(String response){

        preferenceHelper.putIsLogin(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {

                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper.putName(dataobj.getString("userid"));
                    staticuserid=dataobj.getString("userid");
                    preferenceHelper.putID(staticuserid);
                    Log.i("preference   userid",""+preferenceHelper.getName()+" "+staticuserid);
                }
            }
        } catch (JSONException e) {
            Log.i("save_info_error",""+e);
            e.printStackTrace();
        }
    }

}
