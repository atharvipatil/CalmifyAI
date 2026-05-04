package com.example.ai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("CalmifyPrefs", MODE_PRIVATE);

        toolbar        = findViewById(R.id.toolbar);
        drawerLayout   = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);

        // Drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // 🔹 Set user info in nav header
        View headerView = navigationView.getHeaderView(0);
        TextView tvName = headerView.findViewById(R.id.tvNavUserName);
        TextView tvInfo = headerView.findViewById(R.id.tvNavUserInfo);

        String name       = prefs.getString("userName", "User");
        int age           = prefs.getInt("userAge", 0);
        String profession = prefs.getString("userProfession", "");

        tvName.setText(name);
        tvInfo.setText(age + " yrs | " + profession);

        // 🔹 Default screen
        if (savedInstanceState == null) {
            loadFragment(new Calendarfragment());
            navigationView.setCheckedItem(R.id.nav_calendar);
            setTitle("Stress Calendar");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_calendar) {
            loadFragment(new Calendarfragment());
            setTitle("Stress Calendar");

        } else if (id == R.id.nav_chatbot) {
            loadFragment(new Chatbotfragment());
            setTitle("AI Chatbot");

        } else if (id == R.id.nav_music) {
            loadFragment(new MusicFragment());
            setTitle("Calm Music");

        } else if (id == R.id.nav_profile) {
            loadFragment(new Profilefragment());
            setTitle("My Profile");

        } else if (id == R.id.nav_logout) {
            logout();
            return true;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void logout() {
        prefs.edit()
                .putBoolean("isLoggedIn", false)
                .apply();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}