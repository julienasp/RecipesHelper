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
                recipe_time.setText(Long.toString(r.getCooking_time().getTime() + r.getPreparation_time().getTime()));
            }
            Log.i("recipeInvisible", recipe_nb_portions.getText().toString());
            Log.i("recipeInvisible", recipe_time.getText().toString());
            Log.i("recipeInvisible", recipe_calories.getText().toString());
            Log.i("recipeInvisible", Long.toString(r.getCooking_time().getTime()));
            Log.i("recipeInvisible", Long.toString(r.getPreparation_time().getTime()));
            java.sql.Time Try = new java.sql.Time(r.getPreparation_time().getTime() + r.getCooking_time().getTime() );

            Log.i("recipeInvisible", Try.toString());
        }
    }
}
