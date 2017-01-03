package core;

import org.javatuples.Pair;

import java.io.Serializable;

/**
 * Created by JUASP-G73 on 03/01/2017.
 */
public class PairIngredientString implements Serializable{
    private Pair<Ingredient,String> p;

    public PairIngredientString(Pair<Ingredient, String> p) {
        this.p = p;
    }

    public Pair<Ingredient, String> getP() {
        return p;
    }

    public void setP(Pair<Ingredient, String> p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return p.getValue1() + " of " + p.getValue0().getName();
    }
}
