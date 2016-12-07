package core;

import ca.usherbrooke.domus.lights.LightRepository;

/**
 * Created by JUASP-G73 on 06/12/2016.
 */
public abstract class AbstractLightLover {
    protected LightRepository lightRepo = new LightRepository();
    public abstract void showHint();
}
