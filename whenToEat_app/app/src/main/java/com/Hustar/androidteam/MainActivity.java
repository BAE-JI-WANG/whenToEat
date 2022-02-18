package com.Hustar.androidteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.Hustar.androidteam.Fragment.BarcodeFragment;
import com.Hustar.androidteam.Fragment.ListFragment;
import com.Hustar.androidteam.Fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListner);

        Bundle intent = getIntent().getExtras();
        if (intent != null){
            String publisher = intent.getString("publisherid");

            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ListFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("custid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectFragment = new ListFragment();
                            break;

                        case R.id.nav_add:
                            selectFragment = new BarcodeFragment();
                            editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("custid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
//                            startActivity(new Intent(MainActivity.this, UploadProductActivity.class));
                            break;

//                        case R.id.nav_list:
//                            selectFragment = new ListFragment();
//                            break;

                        case R.id.nav_profile:
                            editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectFragment = new ProfileFragment();
                            break;
                    }

                    if (selectFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectFragment).commit();
                    }

                    return true;
                }
            };
}