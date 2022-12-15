package com.example.amazonclone.MenuFiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.amazonclone.HomeActivity;
import com.example.amazonclone.R;

public class BaseActivity extends AppCompatActivity {

    RadioGroup radioGroup1;
    RadioButton home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        radioGroup1=(RadioGroup)findViewById(R.id.radioGroup1);
        home = (RadioButton)findViewById(R.id.bottom_home);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                Intent in;
                Log.i("matching", "matching inside1 bro" + checkedId);

                switch (checkedId)
                {

                    case R.id.bottom_home:
                        Log.i("home", "inside home" +  checkedId);
                        in=new Intent(getBaseContext(), HomeActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.bottom_addprod:
                        Log.i("addprod", "inside addprod" + checkedId);

                        in = new Intent(getBaseContext(), AddProductActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);

                        break;
                    case R.id.bottom_search:
                        Log.i("search", "inside search" + checkedId);

                        in = new Intent(getBaseContext(),SearchActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.bottom_cart:
                        Log.i("cart", "inside cart" + checkedId);
                        in = new Intent(getBaseContext(), CartActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.bottom_profile:
                        Log.i("profile", "inside profile" + checkedId);
                        in = new Intent(getBaseContext(), ProfileActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        break;
                    default:
                        break;


                }

            }
        });



    }
}