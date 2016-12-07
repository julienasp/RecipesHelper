package dao;

import core.Direction;
import core.Ingredient;
import core.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by JUASP-G73 on 06/12/2016.
 */
public class DirectionDao extends AbstractDao<Direction> {

    @Override
    public Direction find(Integer id) {
        Direction direction = new Direction(id);
        try {
            ResultSet result = this.connect
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM recipe_directions WHERE id = " + id
                    );
            if(result.first()) {
                direction.setRecipe_id(result.getInt("recipe_id"));
                direction.setOrder(result.getInt("order"));
                direction.setDescription(result.getString("description"));
                direction.setVideo_url(result.getString("video_url"));
                direction.setImage_url(result.getString("image_url"));
            }

            //tools vector generation
            Vector<Tool> v_tools = new Vector<>();
            ResultSet resultTools = this.connect
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM direction_tools WHERE direction_id = " + id
                    );
            while (resultTools.next()) {
                v_tools.add(new ToolDao().find(resultTools.getInt("tool_id")));
            }
            //We add all the tools to the direction_tools variable
            direction.setDirection_tools(v_tools);

            //ingredients vector generation
            Vector<Ingredient> v_ingredients = new Vector<>();
            ResultSet resultIngredients = this.connect
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM direction_ingredients WHERE direction_id = " + id
                    );
            while (resultIngredients.next()) {
                v_ingredients.add(new IngredientDao().find(resultIngredients.getInt("ingredient_id")));
            }
            //We add all the ingredients to the direction_ingredients variable
            direction.setDirection_ingredients(v_ingredients);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return direction;
    }
}
