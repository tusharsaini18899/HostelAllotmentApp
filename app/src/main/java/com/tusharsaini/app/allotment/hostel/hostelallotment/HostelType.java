package com.tusharsaini.app.allotment.hostel.hostelallotment;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class HostelType extends AppCompatActivity {

    RadioButton girlhostel,boyhostel,single,double1;
    RadioGroup hostelType;
    Button back,next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hosteltype);

        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(HostelType.this,SliderMainPage.class);
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

        hostelType= findViewById(R.id.hosteltype);
        girlhostel=findViewById(R.id.girlhostel);
        boyhostel=findViewById(R.id.boyhostel);
        single=findViewById(R.id.single);
        double1=findViewById(R.id.double1);

        next= findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (girlhostel.isChecked() && single.isChecked())
                {
                    Intent girlhostelsingleroom=new Intent(HostelType.this,GirlHostelSingleRoom.class);
                    startActivity(girlhostelsingleroom);
                }
                else if (girlhostel.isChecked() && double1.isChecked() )
                {
                    Intent girlhosteldoubleroom=new Intent(HostelType.this,GirlHostelDoubleRoom.class);
                    startActivity(girlhosteldoubleroom);
                }
                else if (boyhostel.isChecked() && single.isChecked())
                {
                    Intent boyhostelsingleroom=new Intent(HostelType.this,BoyHostelSingleRoom.class);
                    startActivity(boyhostelsingleroom);
                }
                else if (boyhostel.isChecked() && double1.isChecked())
                {
                    Intent boyhosteldouble=new Intent(HostelType.this,BoyHostelDoubleRoom.class);
                    startActivity(boyhosteldouble);
                }
            }
        });
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.other_optionmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                Intent intent=new Intent(HostelType.this,SliderMainPage.class);
                startActivity(intent);
                break;

            case R.id.exit:
                AlertDialog.Builder builder=new AlertDialog.Builder(HostelType.this);
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
