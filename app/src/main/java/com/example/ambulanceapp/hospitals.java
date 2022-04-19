package com.example.ambulanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class hospitals extends AppCompatActivity implements Adapter.UserClickListener {
    ImageView back;
    Toolbar toolbar;
    RecyclerView recyclerView;
    SearchView searchView;
    private FirebaseAuth mAuth;
    private Button confirm;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference reference = db.getReference().child("hospitals");
    Adapter adapter;
    ArrayList<Model> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);
        confirm = (Button) findViewById(R.id.confirm_hospital);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new Adapter(list,this,this::selectedUser);
        recyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        searchView = findViewById(R.id.hospital);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search your nearby hospital");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchStr = newText;
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        back = findViewById(R.id.backview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(hospitals.this,homepage.class);
                startActivity(i);
            }
        });
    }

    private void Logout() {
        mAuth.signOut();
        finish();
        Intent intent = new Intent(hospitals.this, login.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.profile:
                Intent intent = new Intent(hospitals.this, profile.class);
                startActivity(intent);
                finish();
                break;

            case R.id.trips:
                break;

            case R.id.help:
                break;

            case R.id.settings:
                break;

            case R.id.logout:
                Logout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void selectedUser(Model model) {
        confirm.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Selected hospital "+model.getName(), Toast.LENGTH_SHORT).show();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(hospitals.this,selectRide.class);
                i.putExtra("KEY_FACTOR",model.getName().toString());
                startActivity(i);
            }
        });
    }
}