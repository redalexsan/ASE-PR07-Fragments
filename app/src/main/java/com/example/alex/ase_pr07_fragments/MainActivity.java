package com.example.alex.ase_pr07_fragments;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alex.ase_pr07_fragments.ui.list.ListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ListFragment.newInstance())
                    .commitNow();
        }
    }
}
