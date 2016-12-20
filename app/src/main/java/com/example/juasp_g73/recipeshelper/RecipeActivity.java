package com.example.juasp_g73.recipeshelper;

import android.os.AsyncTask;
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
import tasker.DownloadImageTask;

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
            //new DownloadImageTask(recipe_image_url).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,r.getImage_url());
            recipe_name.setText(r.getName());
            recipe_description.setText(r.getDescription());


            //tv_test.setText(r.toString());
        }
    }
}
