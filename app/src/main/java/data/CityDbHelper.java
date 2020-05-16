package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.weather.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CityDbHelper extends SQLiteOpenHelper {

    Context context;
    private static final String [] ALL_COLUMNS_CITY ={"ID","NAME","LAT","LON","COUNTRY"};
    private static final String [] ALL_COLUMNS_MY_CITIES ={"ID","NAME","SELECTED"};
    private static final String [] ALL_COLUMNS_CURRENT_CITY ={"ID","LATITUDE","LONGITUDE"};
    public static final int DB_VERSION =1;
    public static final String DB_NAME="DB_CITY";
    public static final String TABLE_NAME ="TABLE_CITY";
    public static final String MY_CITIES_TABLE ="MY_CITIES";
    public static final String MY_CITY ="MY_CITY";
    private final String CMD_CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "("+
            "'ID' INTEGER PRIMARY KEY NOT NULL, " +
            "'NAME' TEXT , " +
            "'LAT' DOUBLE, " +
            "'LON' DOUBLE, " +
            "'COUNTRY' TEXT " +
            " ) ";
    private final String CMD_CREATE_MY_CITIES="CREATE TABLE IF NOT EXISTS "+ MY_CITIES_TABLE + "("+
            "'ID' INTEGER PRIMARY KEY NOT NULL, "+
            "'NAME' TEXT , "+
            "'SELECTED' INTEGER "+
            " )";

    private final String CMD_CREATE_CURRENT_CITY="CREATE TABLE IF NOT EXISTS "+ MY_CITY + "("+
            "'ID' INTEGER, "+
            "'LATITUDE' DOUBLE, "+
            "'LONGITUDE' DOUBLE "+
            " )";

    public CityDbHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CMD_CREATE_TABLE);
        db.execSQL(CMD_CREATE_MY_CITIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+MY_CITIES_TABLE);
        onCreate(db);
    }
    public void CreateCurrentCityTable(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ID",1);
        contentValues.put("LATITUDE",0.0);
        contentValues.put("LONGITUDE",0.0);
        Long salam =sqLiteDatabase.insert(MY_CITY,null,contentValues);
        Log.i("fsajflsk", String.valueOf(salam));
        sqLiteDatabase.close();
    }
    public void SetCurrentCity(double lat,double lon){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ID",1);
        contentValues.put("LATITUDE",lat);
        contentValues.put("LONGITUDE",lon);
        db.update(MY_CITY,contentValues,"ID = 1",null);
        db.close();
    }
    public boolean cangetcurrentcity() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor;
        try {
             cursor = sqLiteDatabase.query(true, MY_CITY, ALL_COLUMNS_CURRENT_CITY,
                    "ID = 1", null, null, null, null, null);
        }
        catch (SQLException ex){
            return false;
        }
        if (cursor.getCount() == 0){
            return false;
        }
        else {
            return true;
        }
    }
    public List<Double> GetCurrentCity(){
        List<Double> Coordinates=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(true,MY_CITY,ALL_COLUMNS_CURRENT_CITY,
                "ID = 1",null,null,null,null,null);
            if(cursor.moveToFirst()){
                Coordinates.add(cursor.getDouble(cursor.getColumnIndex("LATITUDE")));
                Coordinates.add(cursor.getDouble(cursor.getColumnIndex("LONGITUDE")));
            }
            for (Double list:Coordinates){
                Log.i("narinim", String.valueOf(list));
            }

        return Coordinates;

    }
    public void InitContents(){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SQLiteDatabase db=getWritableDatabase();
                    InputStream inputStream=context.getResources().openRawResource(R.raw.city_list);
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    String line="";
                    while ((line =reader.readLine()) != null){
                        String [] token=line.split(",");
                        if(token.length != 5){
                            continue;
                        }
                        Long id =db.insert(TABLE_NAME,null,CityModel.CreateContentValues(Integer.valueOf(token[0]),
                                token[1],Double.valueOf(token[2]),Double.valueOf(token[3]),token[4]));
                    }
                    db.close();
                }
                catch (IOException IE){
                    Log.i("ERROR",IE.getMessage());
                }
                catch (SQLiteConstraintException se){
                    Log.i("dbhelper",se.getMessage());
                }
            }
        });
        thread.start();
    }
    public void InsertCityData(CityModel cityModel){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert(TABLE_NAME,null,cityModel.GetcontentValues());
        db.close();
    }
    public List<CityModel> getcities(String selection,String[] selectionarges){
        List<CityModel> citylist= new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME, ALL_COLUMNS_CITY,selection,selectionarges,null,null,"NAME");
        Log.i("dbhelper","cursor returned "+cursor.getCount()+"records");
        if (cursor.moveToFirst()){
            do {
                citylist.add(CityModel.CursorToCityModel(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return citylist;
    }
    public void UpdateCitySelected(Long id,Boolean selected){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("SELECTED",selected ? 1:0);
        sqLiteDatabase.update(MY_CITIES_TABLE,cv,"ID ="+id,null);
        sqLiteDatabase.close();
    }
    public List<CityModel> searchCityByName(String cityname, String limit){
        List<CityModel> citylist = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_NAME, ALL_COLUMNS_CITY,
                "NAME LIKE '" + cityname + "%'" ,
                null, null, null, null, limit);
        if(cursor.moveToFirst()){
            do{
                citylist.add(CityModel.CursorToCityModel(cursor));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return citylist;
    }
    public String getId(String name,String country,String lat){
        String id="";
        List<CityModel> s=new ArrayList<>();
        SQLiteDatabase database=getReadableDatabase();
        Cursor cursor = database.query(true, TABLE_NAME, ALL_COLUMNS_CITY,
                "NAME LIKE '" + name.toLowerCase()+"' AND COUNTRY LIKE '"
                        +country.toLowerCase()+"'",
                null, null, null, null, "20");
        if (cursor.moveToFirst()){
            id= String.valueOf(cursor.getLong(cursor.getColumnIndex("ID")));
            return id;
        }
        return "0";
    }
    public void Add_DeleteCityFromData(String parameter,boolean add_remove){
        String [] token;
        String [] nameandcountry;
        String country;
        String name1 ="";
        String lat ="";
        token  =parameter.split("\n");
        nameandcountry=token[0].trim().split(",");
        name1=nameandcountry[0];
        country=nameandcountry[1].trim();
        lat=token[1].substring(token[1].lastIndexOf(": ")+2);
        Log.i("rrrr",name1+"\n"+country+"\n"+lat);
        String id = getId(name1,country,lat);
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("ID",id);
        cv.put("SELECTED",1);
        sqLiteDatabase.insert(MY_CITIES_TABLE,null,cv);
        sqLiteDatabase.update(MY_CITIES_TABLE,cv,"ID = "+id,null);
        sqLiteDatabase.close();
    }
    public List<String> GetMyCitiesId(){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        List<String> MyCities=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.query(true,MY_CITIES_TABLE,ALL_COLUMNS_MY_CITIES,
                "SELECTED = 1",null,null,null,"NAME",null);
        if (cursor.moveToFirst()){
            do {
                Log.i("getcity", String.valueOf(cursor.getLong(cursor.getColumnIndex("ID"))));
                MyCities.add(String.valueOf(cursor.getLong(cursor.getColumnIndex("ID"))));
            }while (cursor.moveToNext());
        }
        return MyCities;
    }
    public String IdToName(long id){
        String name="";
        String country="";
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query(true,TABLE_NAME,ALL_COLUMNS_CITY,
                "ID ="+id,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                name=cursor.getString(cursor.getColumnIndex("NAME"));
                country=cursor.getString(cursor.getColumnIndex("COUNTRY"));
            }while (cursor.moveToNext());
        }
        return name+", "+country;
    }

    public void DeleteUnUsedInfo() {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(MY_CITIES_TABLE,"ID = 0",null);
    }
}
