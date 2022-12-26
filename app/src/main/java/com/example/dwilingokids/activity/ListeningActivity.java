package com.example.dwilingokids.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dwilingokids.R;
import com.example.dwilingokids.adapter.adapterListening;
import com.example.dwilingokids.api.HTTP;
import com.example.dwilingokids.model.modelListening;
import com.example.dwilingokids.model.modelReading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;

public class ListeningActivity extends AppCompatActivity {

    ListView lvListening;
    String id, title, created, content, parseContent;

    ArrayList<modelListening> listeningList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);

        lvListening = findViewById(R.id.lvListening);

        getListening();


    }

    private void getListening() {
        String url = getString(R.string.api_server)+"/posts";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HTTP Http = new HTTP(ListeningActivity.this, url);
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

                                    if (id.equals("2")){

                                        listeningList.add(new modelListening(id, title, parseContent, created));


                                    }

                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                            adapterListening adapter = new adapterListening(ListeningActivity.this, 0, listeningList);
                            lvListening.setAdapter(adapter);

                            lvListening.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    modelListening list = listeningList.get(i);
                                    Intent intent = new Intent(ListeningActivity.this, DetailMateriActivity.class);
                                    intent.putExtra("category_id", list.getIdCat());
                                    intent.putExtra("title", list.getTitleListening());
                                    intent.putExtra("content", list.getContentListening());
                                    intent.putExtra("created_at", list.getCreated_AT());
                                    startActivity(intent);
                                }
                            });


                        }else{
                            Toast.makeText(ListeningActivity.this, "Error "+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }
}