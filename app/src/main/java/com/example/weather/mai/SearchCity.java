package com.example.weather.mai;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.R;

import java.util.ArrayList;
import java.util.List;

import data.CityDbHelper;
import data.CityModel;


public class SearchCity extends AppCompatActivity {
    ListView citiesList;
    Button search_btn;
    ArrayAdapter<CityModel> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        VirifingViewItems();
        SearchOnClick();
    }

    private void SearchOnClick() {
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionModeCallback = (ActionMode.Callback) startActionMode(actionModeCallback);
                v.setSelected(true);
            }
        });
    }
    private ActionMode.Callback actionModeCallback=new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.search_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionModeCallback=null;
        }
    };


    private void VirifingViewItems() {
        citiesList=findViewById(R.id.search_city);
        search_btn =findViewById(R.id.search_button);
    }


    private void gettingcities(String searchWord,int length) {
        List<CityModel> SearchList = new ArrayList<>();
        final CityDbHelper dbHelper = new CityDbHelper(this);
        if (length < 4)
            SearchList = dbHelper.searchCityByName(searchWord, "20");
        else {
            SearchList = dbHelper.searchCityByName(searchWord, "10");

        }
        adapter = new ArrayAdapter<CityModel>(SearchCity.this, android.R.layout.simple_list_item_1, SearchList);
        citiesList.setAdapter(adapter);
        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = citiesList.getItemAtPosition(position).toString();
                dbHelper.Add_DeleteCityFromData(city, true);
                Intent intent = new Intent(SearchCity.this, Activity_cities.class);
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
