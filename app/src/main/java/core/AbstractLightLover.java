package core;

import ca.usherbrooke.domus.lights.LightRepository;

/**
* AbstractLightLover is a class that uses DOMUS LightRepository API
* It's also share the ImageLover contracts to his childrens
*
* @author  Julien Aspirot
* @version 1.0
* @since   2017-02-04 
*/
public abstract class AbstractLightLover implements ImageLover {
    protected LightRepository lightRepo = new LightRepository();
    public abstract void showHint();
    public abstract void dismissHint();

    @Override
    public abstract String getImage_url();

    @Override
    public abstract String getName();

    @Override
    public abstract void setImage_url(String url);
}
