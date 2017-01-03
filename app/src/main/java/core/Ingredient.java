package core;


import ca.usherbrooke.domus.lights.Light;
import ca.usherbrooke.domus.lights.LightRepository;

import java.io.Serializable;

/**
 * Created by JUASP-G73 on 06/12/2016.
 */
public class Ingredient extends AbstractLightLover implements Serializable {
    private Integer id;
    private String name;
    private String linked_light;
    private String picture_url;

    public Ingredient(Integer id) {
        this.id = id;
    }

    public Ingredient(Integer id, String name, String linked_light, String picture_url) {
        this.id = id;
        this.name = name;
        this.linked_light = linked_light;
        this.picture_url = picture_url;
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

    public String getLinked_light() {
        return linked_light;
    }

    public void setLinked_light(String linked_light) {
        this.linked_light = linked_light;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    @Override
    public void showHint() {
        Light ingredientLight = this.lightRepo.getLight(getLinked_light());
        try {
            ingredientLight.turnOn();

            //We keep the light open for 15 secondes
            Thread.sleep(15000);

            ingredientLight.turnOff();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getImage_url() {
        return getPicture_url();
    }

    @Override
    public void setImage_url(String url) {
        setPicture_url(url);
    }

    @Override
    public String toString() {
        return super.toString() + " id: "+ getId() + ", name: " + getName();
    }
}
