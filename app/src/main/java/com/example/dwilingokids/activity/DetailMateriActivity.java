package com.example.dwilingokids.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.dwilingokids.R;

public class DetailMateriActivity extends AppCompatActivity {

    TextView tvJudulMateri, tvIsiMateri, tvIdCat, tvTanggal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_materi);

        tvIdCat = findViewById(R.id.tvIdCat);
        tvJudulMateri = findViewById(R.id.tvJudulMateri);
        tvIsiMateri = findViewById(R.id.tvIsiMateri);
        tvTanggal = findViewById(R.id.tvTanggal);

        Bundle bundle = getIntent().getExtras();
        tvIdCat.setText(bundle.getString("category_id"));
        tvJudulMateri.setText(bundle.getString("title"));
        tvIsiMateri.setText(bundle.getString("content"));
        tvTanggal.setText(bundle.getString("created_at"));

    }
}