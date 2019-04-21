package com.tusharsaini.app.allotment.hostel.hostelallotment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    TextView signuppage,forgetpassword;
    Button login;
    CheckBox remember;

    TextInputLayout coerid,password;

    ProgressDialog dialog;

    final String URL = "https://hostelallotment12345.000webhostapp.com/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coerid = findViewById(R.id.coerid);
        password = findViewById(R.id.password);

        remember = findViewById(R.id.rememberme);

        signuppage = (TextView) findViewById(R.id.donthaveaccount);
        signuppage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, SignUp.class);
                startActivity(signup);
            }
        });

        forgetpassword = (TextView) findViewById(R.id.forgetpassword);
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otppage = new Intent(MainActivity.this, PhoneAuthActivity.class);
                startActivity(otppage);
                finish();
            }
        });

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setTitle("Please Wait!");
        dialog.setMessage("Login Your Account......");

        login = (Button) findViewById(R.id.login);

        login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        login.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                        break;
                    case MotionEvent.ACTION_DOWN:
                        login.setBackground(getResources().getDrawable(R.drawable.pushbutton));
                        break;
                }
                return false;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String coerid1 = coerid.getEditText().getText().toString();
                final String password1 = password.getEditText().getText().toString();

                if (coerid1.equals("") || password1.equals("")) {
                    Toast.makeText(MainActivity.this, "Please! Fill up all the values", Toast.LENGTH_SHORT).show();
                } else {

                    if (remember.isChecked()) {
                        SharedPreferences.Editor sp = getSharedPreferences("hosteller", MODE_PRIVATE).edit();
                        sp.putString("remember", "2");
                        sp.commit();
                    }

                    dialog.show();
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject responseObject = new JSONObject(response);
                                String connectionStatus = responseObject.get("conn").toString();
                                if (connectionStatus.equals("CONN_SUCCESS")) {
                                    String loginStatus = responseObject.get("loginStatus").toString();
                                    if (loginStatus.equals("LOGIN_SUCCESS")) {
                                        Intent intent = new Intent(MainActivity.this, SliderMainPage.class);
                                        finish();
                                        dialog.dismiss();
                                        startActivity(intent);
                                    } else if (loginStatus.equals("WRONG_PASS")) {
                                        Toast.makeText(MainActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Login failed due to some exception", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Connection with database failed", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                dialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley Error", error.toString());
                            dialog.dismiss();
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("coerid", coerid.getEditText().getText().toString());
                            params.put("password", password.getEditText().getText().toString());
                            return params;
                        }
                    };
                    requestQueue.add(request);
                }


            }
        });
    }
        @Override
        protected void onStart() {
            super.onStart();
            SharedPreferences sp1=getSharedPreferences("hosteller",MODE_PRIVATE);
            String check=sp1.getString("remember",null);
            if(check !=null)
            {
                Intent i1=new Intent(MainActivity.this,SliderMainPage.class);
                startActivity(i1);
                finish();
            }
    }
}
