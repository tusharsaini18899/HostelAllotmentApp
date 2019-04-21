package com.tusharsaini.app.allotment.hostel.hostelallotment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ChangePassword extends AppCompatActivity {

    TextInputLayout coerid,password,confirm;

    Button buttonconfirm ;

    ProgressDialog dialog;

    final String URL = "https://hostelallotment12345.000webhostapp.com/changepassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        coerid=findViewById(R.id.coerid);
        password=findViewById(R.id.newpassword);
        confirm=findViewById(R.id.conform);

        dialog = new ProgressDialog(ChangePassword.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setTitle("Please wait.......");
        dialog.setMessage("Password Changing");

        buttonconfirm=findViewById(R.id.button1);

        buttonconfirm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        buttonconfirm.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                        break;
                    case MotionEvent.ACTION_DOWN:
                        buttonconfirm.setBackground(getResources().getDrawable(R.drawable.pushbutton));
                        break;
                }
                return false;
            }
        });

        buttonconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coerid1=coerid.getEditText().getText().toString();
                final String password1=password.getEditText().getText().toString();
                String password11=confirm.getEditText().getText().toString();

                if (coerid1.equals("") || password1.equals("") || password11.equals("") )
                {
                    Toast.makeText(ChangePassword.this, "Please! Fill up all the values", Toast.LENGTH_SHORT).show();
                }
                else if (!password1.equals(password11))
                {
                    Toast.makeText(ChangePassword.this, "Password Doen't Match Please Check Again", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("SQL", "Inserting data");
                    dialog.show();

                    RequestQueue requestQueue= Volley.newRequestQueue(ChangePassword.this);
                    StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String connectionStatus=jsonObject.get("conn").toString();
                                if(connectionStatus.equals("CONN_SUCCESS"))
                                {

                                    String signupstatus=jsonObject.get("queryExecute").toString();

                                    if (signupstatus.equals("SUCCESSFUL"))
                                    {
                                        Toast.makeText(ChangePassword.this, "Password Updated Successfully !", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        Intent change=new Intent(ChangePassword.this,MainActivity.class);
                                        startActivity(change);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(ChangePassword.this,"Password Updation Failed !",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }

                                }
                                else
                                {
                                    Toast.makeText(ChangePassword.this, "Connection Error", Toast.LENGTH_SHORT).show();
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
                            Log.e("Volly error","error");
                            dialog.dismiss();

                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {

                            HashMap<String,String> hash =new HashMap<>();
                            hash.put("coerid",coerid.getEditText().getText().toString());
                            hash.put("password",password.getEditText().getText().toString());
                            return hash;
                        }
                    };
                    requestQueue.add(request);
                }

            }
        });

    }
}
