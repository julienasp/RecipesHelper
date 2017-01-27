package core;

import ca.usherbrooke.domus.lights.LightRepository;

/**
 * Created by JUASP-G73 on 06/12/2016.
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
