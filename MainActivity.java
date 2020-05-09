package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String CAR_KEY = "car_key";
    private static final int PERMISSON_REQ_CODE = 5;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private CarRVAdapter adapter;
    private MyDatabase db;
    private static final int ADD_CAR_REQ_CODE = 1;
    private static final int EDIT_CAR_REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSON_REQ_CODE);

        }

//id,model,color,dpb,iamge,desc

        toolbar=findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        rv=findViewById(R.id.main_rv);
        fab=findViewById(R.id.main_fab);
        db=new MyDatabase(this);
        ArrayList <Car> cars=db.getAllCars();

        adapter=new CarRVAdapter(cars, new OnRecyclerViewItemClickListener() {
            @Override
            public void onitemclick(int carId) {
                Intent intent= new Intent(getBaseContext(), CarActivity.class);
                intent.putExtra(CAR_KEY,carId);
                startActivityForResult(intent,EDIT_CAR_REQ_CODE);
            }
        });
        rv.setAdapter(adapter);
        RecyclerView.LayoutManager lm=new GridLayoutManager(this,2);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(), CarActivity.class);
                startActivityForResult(intent,ADD_CAR_REQ_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchView searchView= (SearchView) menu.findItem(R.id.main_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList <Car> cars=db.getCars(query);
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList <Car> cars=db.getCars(newText);
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();


                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                ArrayList <Car> cars=db.getAllCars();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();

                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_CAR_REQ_CODE){
            db=new MyDatabase(this);
            ArrayList <Car> cars=db.getAllCars();
            adapter.setCars(cars);
            adapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSON_REQ_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {


                }
        }

    }
}

