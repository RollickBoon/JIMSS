package com.example.jimssgym;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class QuickScanCardViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    QuickScanCardViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickscan_cardview);
        String qr_result = getIntent().getStringExtra("QR_RESULT");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            adapter = new QuickScanCardViewAdapter(this, getMyList(qr_result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter);



    }

    private ArrayList<QuickScanCardViewModel> getMyList(String qr_result) throws IOException {

        ArrayList<QuickScanCardViewModel> models = new ArrayList<>();

        InputStream machines_json = getResources().openRawResource(R.raw.machines_json);
        BufferedReader machines_reader = new BufferedReader(new InputStreamReader(machines_json, Charset.forName("UTF-8")));
        String machines_lines = "";
        try {
            while ((machines_lines = machines_reader.readLine()) != null) {
                JSONArray machinesArray = new JSONArray(machines_lines);
                for (int i = 0; i < machinesArray.length(); i++) {
                    try {
                        JSONObject machinesObject = machinesArray.getJSONObject(i);
                        String equipment_name = machinesObject.getString("name");
                        String equipment_id = machinesObject.getString("id");
                        if (equipment_name.toLowerCase().equals(qr_result.toLowerCase())) {
                            InputStream exercises_json = getResources().openRawResource(R.raw.exercises_json);
                            BufferedReader exercises_reader = new BufferedReader(new InputStreamReader(exercises_json, Charset.forName("UTF-8")));
                            String exercises_lines = "";
                            try {
                                while ((exercises_lines = exercises_reader.readLine()) != null) {
                                    JSONArray exercisesArray = new JSONArray(exercises_lines);
                                    for (int j = 0; j < exercisesArray.length(); j++) {
                                        try {
                                            QuickScanCardViewModel m = new QuickScanCardViewModel();
                                            JSONObject exerciseObject = exercisesArray.getJSONObject(j);
                                            String equipment_string = exerciseObject.getString("equipment");
                                            String exercise_name = exerciseObject.getString("name");
                                            String muscle_string = exerciseObject.getString("muscles");
                                            muscle_string = muscle_string.substring(1, muscle_string.length() - 1);
                                            String muscles[] = muscle_string.split(",");
                                            muscle_string = "";
                                            for (int k=0; k < muscles.length; k++) {
                                                if (!muscle_string.equals("")) {
                                                    muscle_string += ", ";
                                                }
                                                switch (muscles[k])
                                                {
                                                    case "1":
                                                        muscle_string += "Biceps brachii";
                                                        break;
                                                    case "2":
                                                        muscle_string += "Anterior deltoid";
                                                        break;
                                                    case "3":
                                                        muscle_string += "Serratus anterior";
                                                        break;
                                                    case "4":
                                                        muscle_string += "Pectoralis major";
                                                        break;
                                                    case "5":
                                                        muscle_string += "Triceps brachii";
                                                        break;
                                                    case "6":
                                                        muscle_string += "Rectus abdominis";
                                                        break;
                                                    case "7":
                                                        muscle_string += "Gastrocnemius";
                                                        break;
                                                    case "8":
                                                        muscle_string += "Gluteus maximus";
                                                        break;
                                                    case "9":
                                                        muscle_string += "Trapezius";
                                                        break;
                                                    case "10":
                                                        muscle_string += "Quadriceps femoris";
                                                        break;
                                                    case "11":
                                                        muscle_string += "Biceps femoris";
                                                        break;
                                                    case "12":
                                                        muscle_string += "Latissimus dorsi";
                                                        break;
                                                    case "13":
                                                        muscle_string += "Brachialis";
                                                        break;
                                                    case "14":
                                                        muscle_string += "Obliquus externus abdominis";
                                                        break;
                                                    case "15":
                                                        muscle_string += "Soleus";
                                                        break;
                                                }
                                            }
                                            equipment_string = equipment_string.substring(1, equipment_string.length() - 1);
                                            String equipment_array[] = equipment_string.split(",");
                                            List<String> equipment = Arrays.asList(equipment_array);
                                            if (equipment.contains(equipment_id)) {
                                                m.setTitle(exercise_name);
                                                m.setDescription(muscle_string);
                                                models.add(m);
                                            }
                                        } catch (JSONException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return models;

    }
}
