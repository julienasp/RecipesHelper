package dao;

import core.Ingredient;
import core.Recipe;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
* IngredientDao is a child of AbstractDao<Direction>, this class help us find an ingredient in the database.
*
* @author  Julien Aspirot
* @version 1.0
* @since   2017-02-04 
*/
public class IngredientDao extends AbstractDao<Ingredient> {

    /**
   * This method is used to get the instance of a specific Ingredient given an id  
   * @return Ingredient it returns an Ingredient object with all his attributes.
   */
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
