package core;

import ca.usherbrooke.domus.lights.Light;
import ca.usherbrooke.domus.lights.LightRepository;

import java.io.Serializable;

/**
* Tool is POJO Plain old java object with only one behaviour.
* It's basically a container of data for a tool.
* It's only used as a JavaBean for the UI.
* Tool also extends a method from AbstractLightLover, so he can be directily bind with a light from the DOMUS API.
*
* @author  Julien Aspirot
* @version 1.0
* @since   2017-02-04 
*/
public class Tool extends AbstractLightLover implements Serializable {
    private Integer id;
    private String name;
    private String linked_light;
    private String image_url;

    public Tool(Integer id)  {
        this.id = id;
    }

    public Tool(Integer id, String name, String linked_light, String image_url) {
        this.id = id;
        this.name = name;
        this.linked_light = linked_light;
        this.image_url = image_url;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

   /**
   * This method is used to activate the Light who's binded with the curent tool 
   *
   */
    @Override
    public void showHint() {
        Light toolLight = this.lightRepo.getLight(getLinked_light());
        toolLight.turnOn();
    }

    /**
   * This method is used to desactivate the Light who's binded with the curent tool 
   *
   */
    @Override
    public void dismissHint() {
        Light toolLight = this.lightRepo.getLight(getLinked_light());
        toolLight.turnOff();
    }

    @Override
    public String toString() {
        return getName();
    }
}
