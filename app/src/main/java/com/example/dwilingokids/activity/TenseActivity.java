package com.example.dwilingokids.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dwilingokids.R;
import com.example.dwilingokids.adapter.adapterTense;
import com.example.dwilingokids.api.HTTP;
import com.example.dwilingokids.model.modelTense;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;

public class TenseActivity extends AppCompatActivity {

    ListView lvTense;
    String id, title, created, content, parseContent;

    ArrayList<modelTense> tenseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tense);

        lvTense = findViewById(R.id.lvTense);

        getTense();
    }

    private void getTense() {
        String url = getString(R.string.api_server)+"/posts";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HTTP Http = new HTTP(TenseActivity.this, url);
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

                                    if (id.equals("1")){

                                        tenseList.add(new modelTense(id, title, parseContent, created));


                                    }

                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                            adapterTense adapter = new adapterTense(TenseActivity.this, 0, tenseList);
                            lvTense.setAdapter(adapter);

                            lvTense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    modelTense list = tenseList.get(i);
                                    Intent intent = new Intent(TenseActivity.this, DetailMateriActivity.class);
                                    intent.putExtra("category_id", list.getIdCat());
                                    intent.putExtra("title", list.getTitleTense());
                                    intent.putExtra("content", list.getContentTense());
                                    intent.putExtra("created_at", list.getCreated_AT());
                                    startActivity(intent);
                                }
                            });


                        }else{
                            Toast.makeText(TenseActivity.this, "Error "+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }
}