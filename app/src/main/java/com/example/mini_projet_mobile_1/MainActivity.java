package com.example.mini_projet_mobile_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView matiereListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> matieres;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        matiereListView = findViewById(R.id.matiereListView);
        matieres = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_matiere, R.id.titreTextView, matieres);
        matiereListView.setAdapter(adapter);

        Button addButton = findViewById(R.id.ajouterButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AjoutActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshMatiereList();
    }

    private void refreshMatiereList() {
        matieres.clear();
        matieres.addAll(dbHelper.getAllMatieres());
        adapter.notifyDataSetChanged();
    }
}

