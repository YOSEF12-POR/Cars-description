package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class CarActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQ_CODE = 1;
    public static final int ADD_CAR_RESUTL_CODE = 2;
    public static final int EDIT_CAR_REQ_CODE = 3;
    private Toolbar toolbar;
    private TextInputEditText et_model, et_color,
            et_descriptio,
            et_dpl;
    private ImageView iv;
    private int carId = -1;
    private MyDatabase db;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);
        toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        db=new MyDatabase(this);

        et_model = findViewById(R.id.et_details_model);
        et_color = findViewById(R.id.et_details_color);
        et_descriptio = findViewById(R.id.et_details_description);
        et_dpl = findViewById(R.id.et_details_dp1);
        iv = findViewById(R.id.details_iv);

        Intent intent=getIntent();


        carId=intent.getIntExtra(MainActivity.CAR_KEY,-1);

        if(carId==-1){//عملية اضافة
            enapleFileds();
            clearFileds();
        }else{//عملية عرض
            dissapleFelids();
            Car c=   db.getCar(carId);
            if(c!=null){
                fillCarTofildes(c);

            }

        }


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(n,PICK_IMAGE_REQ_CODE);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // نعرف قائمة المينو
        getMenuInflater().inflate(R.menu.details_menu, menu);
        MenuItem save=menu.findItem(R.id.details_menu_save);
        MenuItem edit=menu.findItem(R.id.details_menu_edit);
        MenuItem delete=menu.findItem(R.id.details_menu_delete);

        if(carId==-1){//عملية اضافة
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);

        }else{//عملية عرض
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }
        return true;
    }


    private void dissapleFelids() {
        iv.setEnabled(false);
        et_dpl.setEnabled(false);
        et_descriptio.setEnabled(false);
        et_color.setEnabled(false);
        et_model.setEnabled(false);

    }

    private void enapleFileds() {
        iv.setEnabled(true);
        et_dpl.setEnabled(true);
        et_descriptio.setEnabled(true);
        et_color.setEnabled(true);
        et_model.setEnabled(true);

    }


    private void clearFileds() {
        iv.setImageURI(null);
        et_dpl.setText("");
        et_descriptio.setText("");
        et_color.setText("");
        et_model.setText("");

    }


    private void fillCarTofildes(Car c) {
        if (c.getImage() != null && !c.getImage().equals(""))
            iv.setImageURI(Uri.parse(c.getImage()));
        et_model.setText(c.getModel());
        et_dpl.setText(String.valueOf(c.getDpl()));
        et_color.setText(c.getColor());
        et_descriptio.setText(c.getDescription());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String model,color,desc,image="";
        double dpl;
        switch (item.getItemId()) {//سوتش لاختيار عنصر من المنيو عن طريق ال id
            case R.id.details_menu_save: // الid  الخاص بأيقونة الحفظ
                model=et_model.getText().toString();
                color=et_color.getText().toString();
                dpl=Double.parseDouble(et_dpl.getText().toString());
                desc=et_descriptio.getText().toString();
                if(imageUri!=null)
                    image=imageUri.toString();
                boolean res;
                Car c =new Car(carId,model,color,dpl,image,desc);
                if(carId==-1) {
                    res = db.insarteCar(c);
                    setResult(ADD_CAR_RESUTL_CODE,null);
                    finish();
                    Toast.makeText(this, "car added successfully ", Toast.LENGTH_SHORT).show();
                }else{
                    res=db.editCar(c);
                    setResult(EDIT_CAR_REQ_CODE,null);
                    finish();
                    Toast.makeText(this, "car modifide successfully", Toast.LENGTH_SHORT).show();
                }



                return true;
            case R.id.details_menu_edit:
                enapleFileds();// الid  الخاص بأيقونة التعديل
                MenuItem save=toolbar.getMenu().findItem(R.id.details_menu_save);
                MenuItem edit=toolbar.getMenu().findItem(R.id.details_menu_edit);
                MenuItem delete=toolbar.getMenu().findItem(R.id.details_menu_delete);
                delete.setVisible(false);
                edit.setVisible(false);
                save.setVisible(true);
                return true;
            case R.id.details_menu_delete: // الid  الخاص بأيقونة الحذفـ
                c=new Car(carId,null,null,0,null,null);

                res=db.deleteCar(c);
                if(res){
                    Toast.makeText(this, "car deledted successfully", Toast.LENGTH_SHORT).show();
                    setResult(EDIT_CAR_REQ_CODE,null);
                    finish();
                }



                return true;


        }

        return false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==PICK_IMAGE_REQ_CODE&&resultCode==RESULT_OK){

            if(data!=null){
                imageUri=data.getData();
                iv.setImageURI(imageUri);



            }

        }
    }
}
