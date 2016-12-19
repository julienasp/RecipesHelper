package dao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.example.juasp_g73.recipeshelper.RecipeActivity;
import core.Recipe;

/**
 * Created by JUASP-G73 on 18/12/2016.
 */
public class RecipeDaoTasker extends AsyncTask<Void, Void, Void> {
    private ProgressDialog progress;
    private Integer id;
    private Recipe currentRecipe;
    private Activity a;
    private Context c;

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

    public void onPreExecute() {
        progress.show();
    }

    public void onPostExecute(Void unused) {
        progress.dismiss();
        Intent i = new Intent(c, RecipeActivity.class);
        i.putExtra("recipe",currentRecipe);
        a.startActivity(i);
    }
}

