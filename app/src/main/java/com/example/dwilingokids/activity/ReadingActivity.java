package com.example.dwilingokids.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.dwilingokids.R;
import com.example.dwilingokids.adapter.adapterReading;
import com.example.dwilingokids.api.HTTP;
import com.example.dwilingokids.model.modelReading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;

public class ReadingActivity extends AppCompatActivity {
    ListView lvReading;
    String id, title, created, content, parseContent;

    ArrayList<modelReading> readingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        //readingList = new ArrayList<>();
        lvReading = findViewById(R.id.lvReading);

        getReading();

    }

    private void getReading() {
        String url = getString(R.string.api_server)+"/posts";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HTTP Http = new HTTP(ReadingActivity.this, url);
                Http.setToken(true);
                Http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = Http.getStatusCode();
                        if (code == 200){
                            try {
                                JSONObject response = new JSONObject(Http.getResponse());
                                JSONArray jsonArray = response.getJSONArray("data");

                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    id = jsonObject1.getString("category_id");
                                    title = jsonObject1.getString("title");
                                    content = jsonObject1.getString("content");
                                    created = jsonObject1.getString("created_at");


                                    //remove html tag
                                    parseContent = Jsoup.parse(content).text();

                                    //kondisi ambil data sesuai kategori
                                    if (id.equals("3")){
                                        //HashMap<String, String> reading = new HashMap<>();



                                        //reading.put("category_id", id);
                                        //reading.put("title", title);

                                        readingList.add(new modelReading(id, title, parseContent, created));


                                    }

                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                               /*
                            ListAdapter adapter = new SimpleAdapter(
                                    ReadingActivity.this,
                                    readingList,
                                    R.layout.item_reading,
                                    new String[]{"category_id", "title"},
                                    new int[]{R.id.tvID, R.id.tvTitle});
                            */

                            adapterReading adapter = new adapterReading(ReadingActivity.this, 0, readingList);
                            lvReading.setAdapter(adapter);

                            lvReading.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    modelReading list = readingList.get(i);
                                    Intent intent = new Intent(ReadingActivity.this, DetailMateriActivity.class);
                                    intent.putExtra("category_id", list.getIdCat());
                                    intent.putExtra("title", list.getTitleReading());
                                    intent.putExtra("content", list.getContentReading());
                                    intent.putExtra("created_at", list.getCreated_AT());
                                    startActivity(intent);

                                }
                            });
                        }else{
                            Toast.makeText(ReadingActivity.this, "Error "+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();



    }

}
