package com.example.juasp_g73.recipeshelper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import core.Direction;
import core.Ingredient;
import core.Recipe;
import core.Tool;
import org.javatuples.Pair;

import java.util.Vector;

public class DirectionActivity extends AppCompatActivity {
    Recipe r;
    int stepIndex = 0;
    Direction currentDirection;
    TextView direction_step;
    TextView direction_description;
    ImageView direction_image_url;
    GridView direction_ingredients;
    GridView direction_tools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        direction_step = (TextView) findViewById(R.id.direction_step);
        direction_description = (TextView) findViewById(R.id.direction_description);
        direction_image_url = (ImageView) findViewById(R.id.direction_image_url);
        direction_ingredients = (GridView) findViewById(R.id.direction_ingredients);
        direction_tools = (GridView) findViewById(R.id.direction_tools);
        direction_description.setMovementMethod(new ScrollingMovementMethod());


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             r = (Recipe) extras.getSerializable("recipe");

            Log.i("DirectionActivity","[Recipe#id:"+r.getId()+"] has " + r.getDirections().size() + " directions");


            //Hydrating the interface
            currentDirection = r.getDirections().get(stepIndex);
            if(currentDirection.getImage_url() != null) {
                Picasso.with(this)
                        .load(currentDirection.getImage_url())
                        .into(direction_image_url);
            }

            //attributes are not null?
            if (  currentDirection.getOrder() != null && currentDirection.getDescription() != null )
            {
                direction_step.setText("STEP #" + currentDirection.getOrder().toString());
                direction_description.setText(currentDirection.getDescription());
            }

            //ingredient vector empty?
            if(generateIngredientsFromDirection(currentDirection).size() > 0){
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.test_list_item, generateIngredientsFromDirection(currentDirection));

                direction_ingredients.setAdapter(adapter);
            }else direction_ingredients.setVisibility(View.INVISIBLE);

            //tool vector empty?
            if(currentDirection.getDirection_tools().size() > 0){
                ArrayAdapter<Tool> adapter = new ArrayAdapter<Tool>(this,
                        android.R.layout.test_list_item, currentDirection.getDirection_tools());

                direction_tools.setAdapter(adapter);
            }else direction_tools.setVisibility(View.INVISIBLE);

        }

    }
    private Vector<String> generateIngredientsFromDirection(Direction d){
        Vector<String> ingredients = new Vector<>();

        for(Pair<Ingredient,String> p : d.getDirection_ingredients()){
            ingredients.add(p.getValue1() + " of " + p.getValue0().getName() );
        }

        return ingredients;
    }
}
