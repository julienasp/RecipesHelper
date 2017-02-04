package com.example.juasp_g73.recipeshelper;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.*;
import org.javatuples.Pair;
import android.view.View;
import com.squareup.picasso.Picasso;

import core.AbstractLightLover;
import core.Direction;
import core.Ingredient;
import core.PairIngredientString;
import core.Recipe;
import core.Tool;

import java.text.SimpleDateFormat;
import java.util.*;

/**
* RecipeActivity is the activity that shows the compress informations about a recipe
*
* @author  Julien Aspirot
* @version 1.0
* @since   2017-02-04 
*/
public class RecipeActivity extends AppCompatActivity {

    private Boolean popUpOpen = false;
    private Context ctx;
    private PopupWindow mPopupWindow;
    private TextView recipe_description;
    private GridView recipe_ingredients;
    private GridView recipe_tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        //Recipe interface elements
        TextView recipe_name = (TextView) findViewById(R.id.recipe_name);
        recipe_description = (TextView) findViewById(R.id.recipe_description);
        ImageView recipe_image_url = (ImageView) findViewById(R.id.recipe_image_url);
        TextView recipe_nb_portions = (TextView) findViewById(R.id.recipe_nb_portions);
        TextView recipe_calories = (TextView) findViewById(R.id.recipe_calories);
        TextView recipe_time = (TextView) findViewById(R.id.recipe_time);
        recipe_ingredients = (GridView) findViewById(R.id.recipe_ingredients);
        recipe_tools = (GridView) findViewById(R.id.recipe_tools);
        Button button_start = (Button) findViewById(R.id.button_start);
        ctx = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final Recipe r = (Recipe) extras.getSerializable("recipe");
            Log.i("RecipeActivity","[Recipe#id:"+r.getId()+"] has " + r.getDirections().size() + " directions");
            for(Direction d : r.getDirections()){
                Log.i("RecipeActivity","[Direction#id:" + d.getId() + "] has " + d.getDirection_ingredients().size() + " ingredients and has " + d.getDirection_tools().size() + " tools");
            }

            //Hydrating the interface
            if(r.getImage_url() != null) {
                Picasso.with(this)
                        .load(r.getImage_url())
                        .resize(250, 250)
                        .centerInside()
                        .into(recipe_image_url);
            }
            //Make sure we have the whole recipe
            if(     r.getName() != null &&
                    r.getDescription() != null &&
                    r.getNb_portions() !=null &&
                    r.getCalories() != null &&
                    r.getCooking_time() != null &&
                    r.getPreparation_time() != null )
            {
                recipe_name.setText(r.getName());
                recipe_description.setText(r.getDescription());
                recipe_description.setMovementMethod(new ScrollingMovementMethod()); //allow scrolling
                recipe_nb_portions.setText(String.valueOf(r.getNb_portions()));
                recipe_calories.setText(String.valueOf(r.getCalories()));

                //ingredient vector empty?

                ArrayAdapter<PairIngredientString> adapter = new ArrayAdapter<>(this,
                            android.R.layout.test_list_item, generateIngredientsFromRecipe(r));
                recipe_ingredients.setAdapter(adapter);

                //tool vector empty?
                ArrayAdapter<Tool> adapterTool = new ArrayAdapter<>(this,
                            android.R.layout.test_list_item, generateToolsFromRecipe(r));
                recipe_tools.setAdapter(adapterTool);



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

            //Listener to start the recipe directions
            button_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DirectionActivity.class);
                    i.putExtra("recipe",r);
                    startActivity(i);
                }
            });

            recipe_image_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!popUpOpen){
                        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
                        View customView = inflater.inflate(R.layout.popup, null);
                        ImageView iv = (ImageView) customView.findViewById(R.id.iv_image);
                        TextView tv = (TextView) customView.findViewById(R.id.txt_item);

                        tv.setText(R.string.detailled_view);

                        popUpOpen = true;

                        if(r.getImage_url() != null) {
                            Picasso.with(ctx)
                                    .load(r.getImage_url())
                                    .resize(700,700)
                                    .centerInside()
                                    .placeholder( R.drawable.animation_progress)
                                    .into(iv);
                        }

                        mPopupWindow = new PopupWindow(
                                customView,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        mPopupWindow.setElevation(5.0f);
                        mPopupWindow.setOutsideTouchable(false);

                        Button closeButton = (Button) customView.findViewById(R.id.close);
                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popUpOpen = false;
                                mPopupWindow.dismiss();
                            }
                        });

                        mPopupWindow.showAtLocation(recipe_description, Gravity.CENTER,0,0);
                    }
                }
            });

            recipe_tools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(!popUpOpen) {
                        Tool tool = (Tool) recipe_tools.getItemAtPosition(position);
                        showHint(tool);
                    }

                }
            });

            recipe_ingredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(!popUpOpen) {
                        PairIngredientString p = (PairIngredientString) recipe_ingredients.getItemAtPosition(position);
                        showHint(p.getP().getValue0());
                    }
                }
            });
        }
    }
    
    
       /**
   * Helper method to generate ingredients from a recipe
   *
   * @param r Recipe object which contains all the ingredients that are returned from this method
   * @return Vector<PairIngredientString> it returns a ingredients vector from a recipe
   */
    private Vector<PairIngredientString> generateIngredientsFromRecipe(Recipe r){
        Vector<PairIngredientString> ingredients = new Vector<>();
        for(Direction d : r.getDirections() ){
            for(Pair<Ingredient,String> p : d.getDirection_ingredients()){
                ingredients.add(new PairIngredientString(p));

            }
        }
        return ingredients;
    }

   
   /**
   * Helper method to generate tools from a recipe
   *
   * @param r Recipe object which contains all the tool that are returned from this method
   * @return Vector<Tool> it returns a tool vector from a recipe
   */
    private Vector<Tool> generateToolsFromRecipe(Recipe r){
        Vector<Tool> tools = new Vector<>();
        for(Direction d : r.getDirections() ){
            for(Tool tool : d.getDirection_tools()){
                tools.add(tool);

            }
        }
        return tools;
    }


    
   /**
   * This method is used to activate the Light who's binded with the curent AbstractLightLover object 
   *
   * @param ll is an AbstractLightLover like an ingredient or a tool
   */
    private void showHint(final AbstractLightLover ll){
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popup, null);
        ImageView iv = (ImageView) customView.findViewById(R.id.iv_image);
        TextView tv = (TextView) customView.findViewById(R.id.txt_item);

        popUpOpen = true;

        if(ll.getImage_url() != null) {
            Picasso.with(ctx)
                    .load(ll.getImage_url())
                    .resize(500,500)
                    .centerInside()
                    .placeholder( R.drawable.animation_progress)
                    .into(iv);
        }
        tv.setText(ll.getName());
        tv.setTextSize(15);

        new AsyncTask<AbstractLightLover, Void, Void>() {
            protected Void doInBackground(AbstractLightLover... params) {
                // Background Code
                AbstractLightLover t = params[0];
                t.showHint();
                return null;
            }
        }.execute(ll);

        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        mPopupWindow.setElevation(5.0f);
        mPopupWindow.setOutsideTouchable(false);

        Button closeButton = (Button) customView.findViewById(R.id.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<AbstractLightLover, Void, Void>() {
                    protected Void doInBackground(AbstractLightLover... params) {
                        AbstractLightLover t = params[0];
                        t.dismissHint();
                        return null;
                    }
                }.execute(ll);

                popUpOpen = false;
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow.showAtLocation(recipe_description, Gravity.CENTER,0,0);

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_exit:
//                // User chose the "Settings" item, show the app settings UI...
//                return true;
//
//            case R.id.action_favorite:
//                // User chose the "Favorite" action, mark the current item
//                // as a favorite...
//                return true;
//
//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);
//
//        }
//    }
}
