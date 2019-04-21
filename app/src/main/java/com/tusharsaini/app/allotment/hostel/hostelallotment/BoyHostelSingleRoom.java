package com.tusharsaini.app.allotment.hostel.hostelallotment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
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

import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class BoyHostelSingleRoom extends AppCompatActivity {

    TextView currentdate, currenttime;
    private int yy, mm, dd, hh, mm1, ss;

    EditText name;
    EditText coerid;
    EditText branch, year;

    EditText roomtype;
    EditText roomnumber;
    RadioGroup hostelname;
    RadioButton bcj, gsb, akb, kkb, ashok, arihant;

    Button next, back;

    ProgressDialog dialog;

    final String URL = "https://hostelallotment12345.000webhostapp.com/singleroomboyhostel.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boyhostelsingleroom);

        name = findViewById(R.id.namee);
        coerid = findViewById(R.id.coeride);
        branch = findViewById(R.id.branche);
        year = findViewById(R.id.yeare);
        roomtype = findViewById(R.id.roomtypee);
        roomnumber = findViewById(R.id.roomnumbere);
        hostelname = findViewById(R.id.hostelnamee);
        bcj = findViewById(R.id.bcj);
        gsb = findViewById(R.id.gsb);
        kkb = findViewById(R.id.kkb);
        akb = findViewById(R.id.akb);
        ashok = findViewById(R.id.ashok);
        arihant = findViewById(R.id.arihant);

        currentdate = findViewById(R.id.todaydate);
        final Calendar calendar = Calendar.getInstance();
        yy = calendar.get(Calendar.YEAR);
        mm = calendar.get(Calendar.MONTH);
        dd = calendar.get(Calendar.DATE);
        currentdate.setText(new StringBuilder().append(yy).append("/").append(mm + 1).append("/").append(dd));

        currenttime = findViewById(R.id.currenttime);
        final Calendar calendar1 = Calendar.getInstance();
        hh = calendar1.get(Calendar.HOUR_OF_DAY);
        mm1 = calendar1.get(Calendar.MINUTE);
        ss = calendar1.get(Calendar.SECOND);
        currenttime.setText(new StringBuilder().append(hh).append(":").append(mm1).append(":").append(ss));

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(BoyHostelSingleRoom.this, HostelType.class);
                startActivity(back);
            }
        });

        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
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

        dialog = new ProgressDialog(BoyHostelSingleRoom.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setTitle("Information Submission");
        dialog.setMessage("Informaton Submission");

        next = findViewById(R.id.next);

        next.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
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
                final String date1 = currentdate.getText().toString();
                final String time1 = currenttime.getText().toString();
                final String name1 = name.getText().toString();
                final String coerid1 = coerid.getText().toString();
                final String branch1 = branch.getText().toString();
                final String year1 = year.getText().toString();
                final String roomtype1 = roomtype.getText().toString();
                final String roomNumber1 = roomnumber.getText().toString();
                String hostelname1 = "";
                if (bcj.isChecked()) {
                    hostelname1 = "BCJ";
                } else if (kkb.isChecked()) {
                    hostelname1 = "Kund Kund Bhawan";
                } else if (akb.isChecked()) {
                    hostelname1 = "Aklank Bahwan";
                } else if (gsb.isChecked()) {
                    hostelname1 = "Gupti Sagar Bhawan";
                } else if (arihant.isChecked()) {
                    hostelname1 = "Arihant Bhawan";
                } else if (ashok.isChecked()) {
                    hostelname1 = "Ashok Bhawan";
                }

                if (name1.equals("") || coerid1.equals("") || branch1.equals("") || year1.equals("") || roomNumber1.equals("") || roomtype1.equals("")) {
                    Toast.makeText(BoyHostelSingleRoom.this, "Please! Enter All the values", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("SQL", "Inserting data");
                    dialog.show();

                    RequestQueue requestQueue = Volley.newRequestQueue(BoyHostelSingleRoom.this);
                    final String finalHostelname = hostelname1;
                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String connectionStatus = jsonObject.get("conn").toString();
                                if (connectionStatus.equals("CONN_SUCCESS")) {

                                    String signupstatus = jsonObject.get("queryInsert").toString();

                                    if (signupstatus.equals("INSERTION_SUCCESS")) {
                                        Toast.makeText(BoyHostelSingleRoom.this, "Dear "+name1+" ! Roomnumber "+roomNumber1+" in "+finalHostelname+" is Alloted Successfully", Toast.LENGTH_LONG).show();
                                        Intent info=new Intent(BoyHostelSingleRoom.this,SliderMainPage.class);
                                        startActivity(info);
                                        finish();
                                        dialog.dismiss();


                                    } else {
                                        Toast.makeText(BoyHostelSingleRoom.this, "Dear " + name1 + "! Submission Failed !", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                } else {
                                    Toast.makeText(BoyHostelSingleRoom.this, "Connection Error", Toast.LENGTH_SHORT).show();
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
                            Log.e("Volly error", "error");
                            Toast.makeText(BoyHostelSingleRoom.this, "Volley Error", Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {

                            HashMap<String, String> hash = new HashMap<>();
                            hash.put("name", name.getText().toString());
                            hash.put("coerid", coerid.getText().toString());
                            hash.put("branch", branch.getText().toString());
                            hash.put("year", year.getText().toString());
                            hash.put("roomtype", roomtype.getText().toString());
                            hash.put("roomnumber", roomnumber.getText().toString());
                            hash.put("hostelname", finalHostelname);
                            hash.put("currentdate", currentdate.getText().toString());
                            hash.put("currenttime", currenttime.getText().toString());
                            return hash;
                        }
                    };
                    requestQueue.add(request);
                }
            }

        });
    }
}
