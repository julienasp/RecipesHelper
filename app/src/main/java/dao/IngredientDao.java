package dao;

import core.Ingredient;
import core.Recipe;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by JUASP-G73 on 06/12/2016.
 */
public class IngredientDao extends AbstractDao<Ingredient> {

    @Override
    public Ingredient find(Integer id) {
        Ingredient ingredient = new Ingredient(id);
        try {
            ResultSet result = this.connect
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM ingredients WHERE id = " + id
                    );
            if(result.first()) {
                ingredient.setName(result.getString("name"));
                ingredient.setLinked_light(result.getString("linked_light"));
                ingredient.setPicture_url(result.getString("picture_url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredient;
    }
}
