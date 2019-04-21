package com.tusharsaini.app.allotment.hostel.hostelallotment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BoyHostelDoubleRoom extends AppCompatActivity {

    TextView currentdate,currenttime;
    private int yy,mm,dd,hh,mm1,ss;

    EditText name,coerid,branch,year,roomtype,roomnumber;
    RadioGroup hostelname,bednumber;
    RadioButton bcj,gsb,akb,kkb,arihant,one,two;

    Button next,back;

    ProgressDialog dialog;

    final String URL = "https://hostelallotment12345.000webhostapp.com/doubleroomboyhostel.php";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boyhosteldoubleroom);

        name=findViewById(R.id.namee);
        coerid=findViewById(R.id.coeride);
        branch=findViewById(R.id.branche);
        year=findViewById(R.id.yeare);
        roomtype=findViewById(R.id.roomtypee);
        roomnumber=findViewById(R.id.roomnumbere);
        hostelname=findViewById(R.id.hostelnamee);
        bcj=findViewById(R.id.bcj);
        gsb=findViewById(R.id.gsb);
        kkb=findViewById(R.id.kkb);
        akb=findViewById(R.id.akb);
        arihant=findViewById(R.id.arihant);

        bednumber=findViewById(R.id.bednumbere);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);


        currentdate=findViewById(R.id.todaydate);
        final Calendar calendar=Calendar.getInstance();
        yy=calendar.get(Calendar.YEAR);
        mm=calendar.get(Calendar.MONTH);
        dd=calendar.get(Calendar.DATE);
        currentdate.setText(new StringBuilder().append(yy).append("/").append(mm+1).append("/").append(dd));

        currenttime=findViewById(R.id.currenttime);
        final Calendar calendar1=Calendar.getInstance();
        hh=calendar1.get(Calendar.HOUR_OF_DAY);
        mm1=calendar1.get(Calendar.MINUTE);
        ss=calendar1.get(Calendar.SECOND);
        currenttime.setText(new StringBuilder().append(hh).append(":").append(mm1).append(":").append(ss));

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(BoyHostelDoubleRoom.this,HostelType.class);
                startActivity(back);
            }
        });

        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        back.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                        break;
                    case MotionEvent.ACTION_DOWN:
                        back.setBackground(getResources().getDrawable(R.drawable.pushbutton));
                        break;
                }
                return false;
            }
        });

        dialog = new ProgressDialog(BoyHostelDoubleRoom.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setTitle("Information Submission");
        dialog.setMessage("Informaton Submission");

        next=findViewById(R.id.next);

        next.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        next.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                        break;
                    case MotionEvent.ACTION_DOWN:
                        next.setBackground(getResources().getDrawable(R.drawable.pushbutton));
                        break;
                }
                return false;
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date1=currentdate.getText().toString();
                String time1=currenttime.getText().toString();
                final String name1=name.getText().toString();
                String coerid1=coerid.getText().toString();
                String branch1=branch.getText().toString();
                String year1=year.getText().toString();
                String roomtype1=roomtype.getText().toString();
                final String roomNumber1=roomnumber.getText().toString();
                String bednumber=null;
                String hostelname1=null;

                if (one.isChecked())
                {
                    bednumber="1";
                }
                else if (two.isChecked())
                {
                    bednumber="2";
                }


                if (bcj.isChecked())
                {
                     hostelname1="BCJ";
                }
                else if (kkb.isChecked())
                {
                    hostelname1="Kund Kund Bhawan";
                }
                else if (akb.isChecked())
                {
                     hostelname1="Aklank Bahwan";
                }
                else if (gsb.isChecked())
                {
                     hostelname1="Gupti Sagar Bhawan";
                }
                else if (arihant.isChecked())
                {
                     hostelname1="Arihant Bhawan";
                }

                if (name1.equals("") || coerid1.equals("") || branch1.equals("") || year1.equals("") || roomNumber1.equals(""))
                {
                    Toast.makeText(BoyHostelDoubleRoom.this, "Please! Enter all the values", Toast.LENGTH_SHORT).show();
                }
                else
               {
                    Log.d("SQL", "Inserting data");
                    dialog.show();

                    RequestQueue requestQueue= Volley.newRequestQueue(BoyHostelDoubleRoom.this);
                    final String finalHostelname = hostelname1;
                    final String finalbednumber=bednumber;
                    StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String connectionStatus=jsonObject.get("conn").toString();
                                if(connectionStatus.equals("CONN_SUCCESS"))
                                {

                                    String signupstatus=jsonObject.get("queryInsert").toString();

                                    if (signupstatus.equals("INSERTION_SUCCESS"))
                                    {
                                        Toast.makeText(BoyHostelDoubleRoom.this, "Dear "+name1+" ! Roomnumber "+roomNumber1+" in "+finalHostelname+" is Alloted Successfully", Toast.LENGTH_LONG).show();
                                        Intent a=new Intent(BoyHostelDoubleRoom.this,SliderMainPage.class);
                                        startActivity(a);
                                        finish();
                                        dialog.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(BoyHostelDoubleRoom.this, "Dear "+name1+"!Submission Failed !", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }

                                }
                                else
                                {
                                    Toast.makeText(BoyHostelDoubleRoom.this, "Connection Error", Toast.LENGTH_SHORT).show();
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
                            hash.put("name",name.getText().toString());
                            hash.put("coerid",coerid.getText().toString());
                            hash.put("branch",branch.getText().toString());
                            hash.put("year",year.getText().toString());
                            hash.put("roomtype",roomtype.getText().toString());
                            hash.put("roomnumber",roomnumber.getText().toString());
                            hash.put("hostelname", finalHostelname);
                            hash.put("currentdate",currentdate.getText().toString());
                            hash.put("currenttime",currenttime.getText().toString());
                            hash.put("bednumber",finalbednumber);
                            return hash;
                        }
                    };
                    requestQueue.add(request);
                }
            }
        });
    }
}
