package data;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.fonts.Font;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

public class CityModel {
    private Long id;
    private Double lat;
    private Double lon;
    private String name ="";
    private String country="";
    private boolean selected =false;

    public static ContentValues CreateContentValues(int id,double lat,double lon,String name,String country,Boolean selected){
        ContentValues contentValues =new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("lat",lat);
        contentValues.put("lon",lon);
        contentValues.put("country",country);
        contentValues.put("SELECTED",selected ? 1 : 0);
        return contentValues;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString(){
        SpannableString ss1=  new SpannableString(name+", "+country);
        ss1.setSpan(new RelativeSizeSpan(3f), 0, ss1.length(), 0);
        return ss1+"\nLatitude: "+lat+"\tLongitude: "+lon;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ContentValues GetcontentValues(){
        ContentValues contentValues =new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("lat",lat);
        contentValues.put("lon",lon);
        contentValues.put("country",country);
        contentValues.put("SELECTED",selected ? 1 : 0);
        return contentValues;
    }
    public static CityModel CursorToCityModel(Cursor cursor){
        CityModel city=new CityModel();
        city.setId(cursor.getLong(cursor.getColumnIndex("id")));
        city.setName(cursor.getString(cursor.getColumnIndex("name")));
        city.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
        city.setLon(cursor.getDouble(cursor.getColumnIndex("lon")));
        city.setCountry(cursor.getString(cursor.getColumnIndex("countryCode")));
        return city;
    }
}
