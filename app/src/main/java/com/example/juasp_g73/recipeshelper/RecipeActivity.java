package com.example.juasp_g73.recipeshelper;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import core.Direction;
import core.Recipe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Recipe interface elements
        TextView recipe_name = (TextView) findViewById(R.id.recipe_name);
        TextView recipe_description = (TextView) findViewById(R.id.recipe_description);
        ImageView recipe_image_url = (ImageView) findViewById(R.id.recipe_image_url);
        TextView recipe_nb_portions = (TextView) findViewById(R.id.recipe_nb_portions);
        TextView recipe_calories = (TextView) findViewById(R.id.recipe_calories);
        TextView recipe_time = (TextView) findViewById(R.id.recipe_time);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Recipe r = (Recipe) extras.getSerializable("recipe");
            Log.i("RecipeActivity","[Recipe#id:"+r.getId()+"] has " + r.getDirections().size() + " directions");
            for(Direction d : r.getDirections()){
                Log.i("RecipeActivity","[Direction#id:" + d.getId() + "] has " + d.getDirection_ingredients().size() + " ingredients and has " + d.getDirection_tools().size() + " tools");
            }

            //Hydrating the interface
            if(r.getImage_url() != null) {
                Picasso.with(this)
                        .load(r.getImage_url())
                        .into(recipe_image_url);
            }
            //Make sure we have the whole recipe
            if(     r.getName() != null &&
                    r.getDescription() != null &&
                    r.getNb_portions() !=null &&
                    r.getCalories() != null &&
                    r.getCooking_time() != null &&
                    r.getPreparation_time() != null  )
            {
                recipe_name.setText(r.getName());
                recipe_description.setText(r.getDescription());
                recipe_nb_portions.setText(r.getNb_portions().toString());
                recipe_calories.setText(r.getCalories().toString());

                //Time manipulation because java.sql.time is poorly written
                //To refactor into a function...
                Date datePrep = new Date(r.getPreparation_time().getTime());
                Calendar calendarPreparationTime = GregorianCalendar.getInstance();
                calendarPreparationTime.setTime(datePrep);

                Date dateCooking = new Date(r.getCooking_time().getTime());
                Calendar calendarTotalTime = GregorianCalendar.getInstance();
                calendarTotalTime.setTime(dateCooking);
                calendarTotalTime.set(Calendar.HOUR_OF_DAY,calendarTotalTime.get(Calendar.HOUR_OF_DAY) + calendarPreparationTime.get(Calendar.HOUR_OF_DAY));
                calendarTotalTime.set(Calendar.MINUTE,calendarTotalTime.get(Calendar.MINUTE) + calendarPreparationTime.get(Calendar.MINUTE));
                calendarTotalTime.set(Calendar.SECOND,calendarTotalTime.get(Calendar.SECOND) + calendarPreparationTime.get(Calendar.SECOND));

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                recipe_time.setText(timeFormat.format(calendarTotalTime.getTime()));
            }
            Log.i("recipeInvisible", recipe_nb_portions.getText().toString());
            Log.i("recipeInvisible", recipe_calories.getText().toString());
            Log.i("recipeInvisible", recipe_time.getText().toString());

        }
    }
}
