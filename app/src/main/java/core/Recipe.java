package core;

import java.sql.Time;
import java.util.Vector;

/**
 * Created by JUASP-G73 on 06/12/2016.
 */
public class Recipe {
    private Integer id;
    private String name;
    private String description;
    private Integer nb_portions;
    private Integer calories;
    private String image_url;
    private Time preparation_time;
    private Time cooking_time;
    private Vector<Direction> directions;

    public Recipe(Integer id) {
        this.id = id;
    }

    public Recipe(Integer id, String name, String description, Integer nb_portions, Integer calories, String image_url, Time preparation_time, Time cooking_time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.nb_portions = nb_portions;
        this.calories = calories;
        this.image_url = image_url;
        this.preparation_time = preparation_time;
        this.cooking_time = cooking_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNb_portions() {
        return nb_portions;
    }

    public void setNb_portions(Integer nb_portions) {
        this.nb_portions = nb_portions;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Time getPreparation_time() {
        return preparation_time;
    }

    public void setPreparation_time(Time preparation_time) {
        this.preparation_time = preparation_time;
    }

    public Time getCooking_time() {
        return cooking_time;
    }

    public void setCooking_time(Time cooking_time) {
        this.cooking_time = cooking_time;
    }

    public Vector<Direction> getDirections() {
        return directions;
    }

    public void setDirections(Vector<Direction> directions) {
        this.directions = directions;
    }
}
