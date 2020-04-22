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

public class LoginAcivity extends AppCompatActivity {

    private EditText etUname, etPass;
    private Button btnlogin;
    private TextView tvreg;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acivity);

        preferenceHelper = new PreferenceHelper(this);

        etUname = (EditText) findViewById(R.id.etusername);
        etPass = (EditText) findViewById(R.id.etpassword);

        btnlogin = (Button) findViewById(R.id.btn);
        tvreg = (TextView) findViewById(R.id.tvreg);

        tvreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAcivity.this, RegisterActivity.class);
                startActivity(intent);
                LoginAcivity.this.finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser() {

        final String username = etUname.getText().toString().trim();
        final String password = etPass.getText().toString().trim();

        Log.i("us and pas",username+"/"+password);
        ApiInterface api = ApiClient.getApiClinet().create(ApiInterface.class);
        Call<String> call = api.getUserLogin(username,password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("l-Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("l-onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        parseLoginData(jsonresponse);

                    } else {
                        Log.i("l-onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void parseLoginData(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            Log.i("logres and jsonobject",""+response+"****"+jsonObject);
            if (jsonObject.getString("status").equals("true")) {


                staticuserid=jsonObject.optString("userid");
                Log.i("usingg",""+staticuserid);
                saveInfo(response);

                Toast.makeText(this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
                    staticuserid=dataobj.getString("userid");
                    Log.i("using",""+staticuserid);
                    preferenceHelper.putName(dataobj.getString("userid"));
                    preferenceHelper.putID(staticuserid);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}


