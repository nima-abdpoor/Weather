package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private static final String [] ALL_COLUMNS ={"id","name","lat","lon","countryCode"};
    public static final int DB_VERSION = 2;
    public static final String TABLE_NAME ="tb_city";
    public static final String DB_NAME="db_city";
    private final String CMD_CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "("+
            "'id' INTEGER PRIMARY KEY NOT NULL, " +
            "'name' TEXT , " +
            "'lat' DOUBLE, " +
            "'lon' DOUBLE, " +
            "'countryCode' TEXT, " +
            "'selected' INTEGER" +
            " ) ";

    public CityDbHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CMD_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
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
                        String [] token=line.split("\t");
                        if(token.length == 5){
                            continue;
                        }
                        Long id =db.insert(TABLE_NAME,null,CityModel.CreateContentValues(Integer.valueOf(token[0]),
                                Double.valueOf(token[1]),Double.valueOf(token[2]),token[3],token[4],false));

                        Log.i("dbhelper","data inserted : "+id);
                    }
                    db.close();
                }
                catch (IOException IE){
                    Log.i("EROR",IE.getMessage());
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
        Cursor cursor=db.query(TABLE_NAME,ALL_COLUMNS,selection,selectionarges,null,null,"NAME");
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
        cv.put("selected",selected ? 1:0);
        sqLiteDatabase.update(TABLE_NAME,cv,"id ="+id,null);
        sqLiteDatabase.close();
    }
    public List<CityModel> searchCityByName(String cityname, String limit){
        List<CityModel> citylist = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_NAME, ALL_COLUMNS,
                "name LIKE '" + cityname + "%'" ,
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
}
