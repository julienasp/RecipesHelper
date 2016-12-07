package dao;

import core.Direction;
import core.Recipe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by JUASP-G73 on 06/12/2016.
 */
public class RecipeDao extends AbstractDao<Recipe> {

    @Override
    public Recipe find(Integer id) {

        Recipe recipe = new Recipe(id);
        try {
            ResultSet result = this.connect
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM recipes WHERE id = " + id
                    );
            if(result.first()) {
                recipe.setName(result.getString("name"));
                recipe.setDescription(result.getString("description"));
                recipe.setNb_portions(result.getInt("nb_portions"));
                recipe.setCalories(result.getInt("calories"));
                recipe.setImage_url(result.getString("image_url"));
                recipe.setPreparation_time(result.getTime("preparation_time"));
                recipe.setCooking_time(result.getTime("cooking_time"));
            }

            //direction vector generation
            Vector<Direction> v_directions = new Vector<>();
            ResultSet resultDirections = this.connect
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM recipe_directions WHERE recipe_id = " + id
                    );
            while (resultDirections.next()) {
                v_directions.add(resultDirections.getInt("order")-1,new DirectionDao().find(resultDirections.getInt("id")));
            }
            //We add all the directions to the directions variable
            recipe.setDirections(v_directions);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipe;
    }
}
