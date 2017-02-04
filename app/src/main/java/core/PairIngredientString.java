package core;

import org.javatuples.Pair;

import java.io.Serializable;

/**
* PairIngredientString is used to bind a string to an ingredient.
* The given string binded with an ingredient is used in the UI.
*
* @author  Julien Aspirot
* @version 1.0
* @since   2017-02-04 
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

    /**
   * This method is used to generate a string like "1/4 cup of salt" 
   * @return String that represent the quantity need for an ingredient.
   */
    @Override
    public String toString() {
        return p.getValue1() + " of " + p.getValue0().getName();
    }
}
