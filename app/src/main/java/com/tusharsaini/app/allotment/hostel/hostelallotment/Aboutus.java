package com.tusharsaini.app.allotment.hostel.hostelallotment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class Aboutus extends AppCompatActivity {

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setContentView(R.layout.aboutus);


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
            Intent intent=new Intent(Aboutus.this,SliderMainPage.class);
                startActivity(intent);
                break;

            case R.id.exit:
                AlertDialog.Builder builder=new AlertDialog.Builder(Aboutus.this);
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
