package core;



import org.javatuples.Pair;

import java.io.Serializable;
import java.util.Vector;

/**
* Direction is POJO Plain old java object.
* It's basically a container of data for a direction.
* It has no behaviour, no parent and no implementation.
* It's only used as a JavaBean for the UI.
*
* @author  Julien Aspirot
* @version 1.0
* @since   2017-02-04 
*/
public class Direction implements Serializable {
    private Integer id;
    private Integer recipe_id;
    private Integer order;
    private String description;
    private String video_url;
    private String image_url;
    private Vector<Pair<Ingredient,String>> direction_ingredients;
    private Vector<Tool> direction_tools;

    public Direction(Integer id) {
        this.id = id;
    }

    public Direction(Integer id, Integer recipe_id, Integer order, String description) {
        this.id = id;
        this.recipe_id = recipe_id;
        this.order = order;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Vector<Pair<Ingredient,String>> getDirection_ingredients() {
        return direction_ingredients;
    }

    public void setDirection_ingredients(Vector<Pair<Ingredient,String>> direction_ingredients) {
        this.direction_ingredients = direction_ingredients;
    }

    public Vector<Tool> getDirection_tools() {
        return direction_tools;
    }

    public void setDirection_tools(Vector<Tool> direction_tools) {
        this.direction_tools = direction_tools;
    }

    @Override
    public String toString() {
        return super.toString() + " id: "+ getId() + ", recipeId: " + getRecipe_id() + ", order: " + getOrder() + ", description: " + getDescription() + ", direction_tools: " + getDirection_tools().toString() + ", direction_ingredients: " + getDirection_ingredients().toString() ;
    }
}
