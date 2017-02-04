package dao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.example.juasp_g73.recipeshelper.RecipeActivity;
import core.Recipe;

import java.text.DecimalFormat;

/**
* RecipeDaoTasker is a child of AsyncTask<Void, Void, Void>, this class help us find a direction in the database.
* This class is used to make sure that when we are hydrating a Recipe from the database it doesn't block the UI
* This is an AsyncTask for the RecipeDao.
*
* @author  Julien Aspirot
* @version 1.0
* @since   2017-02-04 
*/
public class RecipeDaoTasker extends AsyncTask<Void, Void, Void> {
    private ProgressDialog progress;
    private Integer id;
    private Recipe currentRecipe;
    private Activity a;
    private Context c;
    private long startTime;

    /**
   * The constructor recipe a ProgressDialog, RecipeId and the android context  
   * 
   */
    public RecipeDaoTasker(ProgressDialog progress, Integer recipeId, Activity a, Context c) {
        this.progress = progress;
        this.id = recipeId;
        this.a = a;
        this.c = c;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        currentRecipe = new RecipeDao().find(this.id);
        return null;
    }

    /**
   * This method is executed before the doInBackground work, we show the progressDialog to the user
   * 
   */
    public void onPreExecute() {
        //Chrono start
        this.startTime = System.nanoTime();
        progress.show();
    }

     /**
   * This method is executed after the doInBackground work, we dismiss the progressDialog and we redirect the user to another Activity
   * 
   */
    public void onPostExecute(Void unused) {
        progress.dismiss();
        Intent i = new Intent(c, RecipeActivity.class);
        i.putExtra("recipe",currentRecipe);
        a.startActivity(i);
        long totalTime = System.nanoTime() - startTime;
        Log.i("timer", "RecipeDaoTasker took: " + totalTime/1e6 + " ms.");
    }
}

