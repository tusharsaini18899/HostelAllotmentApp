package com.tusharsaini.app.allotment.hostel.hostelallotment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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

public class SignUp extends AppCompatActivity {

    TextInputLayout name,coerid,branch,password,email,phone;

    Button signup;

    ProgressDialog progressDialog;

    final String URL="https://hostelallotment12345.000webhostapp.com/signup.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        name= findViewById(R.id.name);
        coerid=findViewById(R.id.coerid);
        branch=findViewById(R.id.branch);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);

        progressDialog=new ProgressDialog(SignUp.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait !");
        progressDialog.setMessage("Signup ");

        signup=(Button)findViewById(R.id.signup);

       signup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        signup.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                        break;
                    case MotionEvent.ACTION_DOWN:
                        signup.setBackground(getResources().getDrawable(R.drawable.pushbutton));
                        break;
                }
                return false;
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name2=name.getEditText().getText().toString();
                String coerid2=coerid.getEditText().getText().toString();
                String branch2=branch.getEditText().getText().toString();
                String password2=password.getEditText().getText().toString();
                String email2=email.getEditText().getText().toString();
                String phone2=phone.getEditText().getText().toString();

                if (name2.equals("") || coerid2.equals("") || branch2.equals("") || password2.equals("") || email2.equals("") || phone2.equals(""))
                {
                    Toast.makeText(SignUp.this,"Please Enter all the values",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                else
                {
                    Log.d("SQL", "Inserting data");
                    progressDialog.show();

                    RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String connectionStatus = jsonObject.get("conn").toString();
                                if (connectionStatus.equals("CONN_SUCCESS")) {

                                    String signupstatus = jsonObject.get("queryInsert").toString();
                                    if (signupstatus.equals("IN_FAIL")) {
                                        Toast.makeText(SignUp.this, "Dear"+name2+"! Your Account is creation  Failed !", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    } else {
                                        Toast.makeText(SignUp.this, "Dear " + name2 + "! Your Account is Created Successfully!", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        Intent intent=new Intent(SignUp.this,MainActivity.class);
                                        startActivity(intent);
                                    }

                                } else {
                                    Toast.makeText(SignUp.this, "Connection Error", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volly error", "error");
                            Toast.makeText(SignUp.this,"Volley Error",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {

                            HashMap<String, String> hash = new HashMap<>();
                            hash.put("name",name.getEditText().getText().toString());
                            hash.put("coerid",coerid.getEditText().getText().toString());
                            hash.put("branch",branch.getEditText().getText().toString());
                            hash.put("password",password.getEditText().getText().toString());
                            hash.put("email",email.getEditText().getText().toString());
                            hash.put("phone",phone.getEditText().getText().toString());
                            return hash;
                        }
                    };
                    requestQueue.add(request);
                }

            }
        });
    }
}
