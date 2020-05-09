package com.example.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "cars_db";
    public static final int DB_VERSION = 1;
    public static final String CAR_TB_NAME = "car";
    public static final String CAR_CLN_ID = "id";
    public static final String CAR_CLN_MODEL = "model";
    public static final String CAR_CLN_COLOR = "color";
    public static final String CAR_CLN_DPL = "distancePerLetter";
    public static final String CAR_CLN_IMAGE = "image";
    public static final String CAR_CLN_DESC = "descriptio";

    public MyDatabase(@Nullable Context context    ) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + CAR_TB_NAME + " (" + CAR_CLN_ID + " " +
                "INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "" + CAR_CLN_MODEL + " TEXT ,"
                + CAR_CLN_COLOR + " TEXT ," + CAR_CLN_DPL + " TEXT," + CAR_CLN_IMAGE +
                " TEXT," + CAR_CLN_DESC + " TEXT )");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CAR_TB_NAME);
        onCreate(sqLiteDatabase);

    }


    public boolean insarteCar(Car car) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CAR_CLN_MODEL, car.getModel());
        values.put(CAR_CLN_COLOR, car.getColor());
        values.put(CAR_CLN_DPL, car.getDpl());
        values.put(CAR_CLN_IMAGE, car.getImage());
        values.put(CAR_CLN_DESC, car.getDescription());
        long res = db.insert(CAR_TB_NAME, null, values);
        return res > -1;
    }

    public boolean editCar(Car car) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CAR_CLN_MODEL, car.getModel());
        values.put(CAR_CLN_COLOR, car.getColor());
        values.put(CAR_CLN_DPL, car.getDpl());
        values.put(CAR_CLN_IMAGE, car.getImage());
        values.put(CAR_CLN_DESC, car.getDescription());
        String args[] = {car.getId() + ""};
        int res = db.update(CAR_TB_NAME, values, "id=?", args);
        return res > 0;
    }

    public boolean deleteCar(Car car) {
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {car.getId() + ""};

        int res = db.delete(CAR_TB_NAME, "id=?", args);

        return res > 0;
    }

    public Car getCar(int carId){
        ArrayList <Car> cars=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+CAR_TB_NAME+ " WHERE "+CAR_CLN_ID+"=?"
                ,new String[]{String.valueOf(carId)});
        if(cursor.moveToFirst()&&cursor!=null){


            int id=cursor.getInt(cursor.getColumnIndex(CAR_CLN_ID));
            String model=cursor.getString(cursor.getColumnIndex(CAR_CLN_MODEL));
            String color=cursor.getString(cursor.getColumnIndex(CAR_CLN_COLOR));
            double dpl=cursor.getDouble(cursor.getColumnIndex(CAR_CLN_DPL));
            String image=cursor.getString(cursor.getColumnIndex(CAR_CLN_IMAGE));
            String descriptio=cursor.getString(cursor.getColumnIndex(CAR_CLN_DESC));
            Car c=new Car(id,model,color,dpl,image,descriptio);
            cars.add(c);
            cursor.close();
            return c;

        }
        return null;

    }





    public ArrayList<Car> getAllCars(){
        ArrayList <Car> cars=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+CAR_TB_NAME,null);
        if(cursor.moveToFirst()&&cursor!=null){
            do {//id,model,color,dpb,iamge,desc
                int id=cursor.getInt(cursor.getColumnIndex(CAR_CLN_ID));
                String model=cursor.getString(cursor.getColumnIndex(CAR_CLN_MODEL));
                String color=cursor.getString(cursor.getColumnIndex(CAR_CLN_COLOR));
                double dpl=cursor.getDouble(cursor.getColumnIndex(CAR_CLN_DPL));
                String image=cursor.getString(cursor.getColumnIndex(CAR_CLN_IMAGE));
                String descriptio=cursor.getString(cursor.getColumnIndex(CAR_CLN_DESC));
                Car c=new Car(id,model,color,dpl,image,descriptio);
                cars.add(c);

            }while (cursor.moveToNext());
            cursor.close();

        }

        return cars;

    }

    public ArrayList<Car> getCars(String modelSearch){
        ArrayList <Car> cars=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+
                        CAR_TB_NAME+ " WHERE "+CAR_CLN_MODEL+" LIKE ?"
                ,new String[]{"%"+modelSearch+"%"});
        if(cursor.moveToFirst()&&cursor!=null){

            do {
                int id=cursor.getInt(cursor.getColumnIndex(CAR_CLN_ID));
                String model=cursor.getString(cursor.getColumnIndex(CAR_CLN_MODEL));
                String color=cursor.getString(cursor.getColumnIndex(CAR_CLN_COLOR));
                double dpl=cursor.getDouble(cursor.getColumnIndex(CAR_CLN_DPL));
                String image=cursor.getString(cursor.getColumnIndex(CAR_CLN_IMAGE));
                String descriptio=cursor.getString(cursor.getColumnIndex(CAR_CLN_DESC));

                Car c=new Car(id,model,color,dpl,image,descriptio);
                cars.add(c);

            }while (cursor.moveToNext());
            cursor.close();

        }

        return cars;

    }

}