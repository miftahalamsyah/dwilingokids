package com.example.dwilingokids.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dwilingokids.api.HTTP;
import com.example.dwilingokids.activity.ListeningActivity;
import com.example.dwilingokids.R;
import com.example.dwilingokids.activity.ReadingActivity;
import com.example.dwilingokids.activity.TenseActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    TextView tvReading, tvTense, tvListening;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        CardView cardReading, cardTense, cardListening;

        cardReading = view.findViewById(R.id.cardReading);
        cardTense = view.findViewById(R.id.cardTense);
        cardListening = view.findViewById(R.id.cardListening);

        cardReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReadingActivity.class);
                startActivity(intent);
            }
        });

        cardTense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TenseActivity.class);
                startActivity(intent);
            }
        });

        cardListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListeningActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvReading = view.findViewById(R.id.reading);
        tvListening = view.findViewById(R.id.listening);
        tvTense = view.findViewById(R.id.tense);

        getReading();
        getListening();
        getTense();

    }

    private void getTense() {
        String url = getString(R.string.api_server)+"/categories/1";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HTTP Http = new HTTP(getActivity(), url);
                Http.setToken(true);
                Http.send();

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = Http.getStatusCode();
                        if (code == 200){
                            try {
                                JSONObject response = new JSONObject(Http.getResponse());
                                String reading = response.getJSONObject("data").getString("name");
                                tvTense.setText(reading);

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Error "+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    private void getListening() {
        String url = getString(R.string.api_server)+"/categories/2";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HTTP Http = new HTTP(getActivity(), url);
                Http.setToken(true);
                Http.send();

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = Http.getStatusCode();
                        if (code == 200){
                            try {
                                JSONObject response = new JSONObject(Http.getResponse());
                                String reading = response.getJSONObject("data").getString("name");
                                tvListening.setText(reading);

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Error "+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    private void getReading() {
        String url = getString(R.string.api_server)+"/categories/3";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HTTP Http = new HTTP(getActivity(), url);
                Http.setToken(true);
                Http.send();

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = Http.getStatusCode();
                        if (code == 200){
                            try {
                                JSONObject response = new JSONObject(Http.getResponse());
                                String reading = response.getJSONObject("data").getString("name");
                                tvReading.setText(reading);

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Error "+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }
}