package com.tusharsaini.app.allotment.hostel.hostelallotment;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import static com.tusharsaini.app.allotment.hostel.hostelallotment.R.menu.option_menu;

public class SliderMainPage extends AppCompatActivity {

    ViewFlipper v_flipper;
    Button bookhostelroom,hostelfacilities;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidermainpage);

        bookhostelroom=(Button)findViewById(R.id.bookhostelroom);
        bookhostelroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hosteltype=new Intent(SliderMainPage.this,HostelType.class);
                startActivity(hosteltype);
            }
        });
        bookhostelroom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        bookhostelroom.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                        break;
                    case MotionEvent.ACTION_DOWN:
                        bookhostelroom.setBackground(getResources().getDrawable(R.drawable.pushbutton));
                        break;
                }
                return false;
            }
        });

        hostelfacilities=(Button)findViewById(R.id.hostelfacilities);
        hostelfacilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facilities=new Intent(SliderMainPage.this,HostelFacilities.class);
                startActivity(facilities);
            }
        });

        hostelfacilities.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        hostelfacilities.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                        break;
                    case MotionEvent.ACTION_DOWN:
                        hostelfacilities.setBackground(getResources().getDrawable(R.drawable.pushbutton));
                        break;
                }
                return false;
            }
        });

        int images[]={R.drawable.slider1,R.drawable.slider2,R.drawable.slider3};
        v_flipper=(ViewFlipper)findViewById(R.id.v_fliper);


        for (int image: images)
        {
            fliperImage(image);
        }

    }
    public void fliperImage(int image)
    {
        ImageView imageView=new ImageView(SliderMainPage.this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(3000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(SliderMainPage.this,android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(SliderMainPage.this,android.R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.lg:
                SharedPreferences.Editor sp=getSharedPreferences("hosteller",MODE_PRIVATE).edit();
                sp.clear();
                sp.commit();
                Intent i1=new Intent(SliderMainPage.this,MainActivity.class);
                startActivity(i1);
                finish();
                break;
            case R.id.aboutus:
                Intent b=new Intent(SliderMainPage.this,Aboutus.class);
                startActivity(b);
                break;
            case R.id.exit:
                AlertDialog.Builder builder=new AlertDialog.Builder(SliderMainPage.this);
                builder.setCancelable(false);
                builder.setTitle("Alert !");
                builder.setMessage("Do You want to Exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
