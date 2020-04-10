package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import data.CityDbHelper;
import data.CityModel;

public class SearchCity extends AppCompatActivity {
    ListView citiesList;
    ArrayAdapter<CityModel> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        VirifingViewItems();
    }


    private void VirifingViewItems() {
        citiesList=findViewById(R.id.search_city);
    }

    private void gettingcities(String searchWord,int length) {
        List<CityModel> SearchList=new ArrayList<>();
        final CityDbHelper dbHelper=new CityDbHelper(this);
        if (length <4)
        SearchList= dbHelper.searchCityByName(searchWord,"20");
        else {
            SearchList= dbHelper.searchCityByName(searchWord,"10");

        }
        adapter =new ArrayAdapter<CityModel>(SearchCity.this,android.R.layout.simple_list_item_1,SearchList);
        citiesList.setAdapter(adapter);
        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city= citiesList.getItemAtPosition(position).toString();
                dbHelper.Add_DeleteCityFromData(city,true);
                Intent intent=new Intent(SearchCity.this,Activity_cities.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.search_city);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                gettingcities(newText,newText.length());
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
