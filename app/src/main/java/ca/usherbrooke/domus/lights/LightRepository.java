package ca.usherbrooke.domus.lights;

/**
 * Created by grop2401 on 2015-11-10.
 */
public class LightRepository {

    public enum LightID {
        CUISINE, SALON, CHAMBRE, SALLE_DE_BAIN, ENTREE, SALLE_A_MANGER,
        MICRO_ONDES_PANNEAU_BAS_GAUCHE, MICRO_ONDES_PANNEAU_BAS_DROITE, MICRO_ONDES_PANNEAU_HAUT_UN, MICRO_ONDES_PANNEAU_HAUT_DEUX, MICRO_ONDES_PANNEAU_HAUT_TROIS, MICRO_ONDES_PANNEAU_HAUT_QUATRE, MICRO_ONDES_TIROIR_BAS,
        CUISINIERE_ARMOIR_BAS_DROITE, CUISINIERE_ARMOIR_BAS_GAUCHE, CUISINIERE_PANNEAU_BAS_DROITE,
        EVIER_TIROIR_HAUT_DROITE, EVIER_TIROIR_DROITE_DEUX, EVIER_PANNEAU_BAS_DROITE,
        LAVE_VAISELLE_ARMOIR_BAS_DROITE, LAVE_VAISELLE_ARMOIR_BAS_GAUCHE,
        CUISINE_SOUS_ARMOIR_MICRO_ONDES,
        CUISINE_HOTTE

    }

    private String url = "http://localhost:3000/lights";

    public LightRepository(String url){
        this.url = url;
    }

    public LightRepository(){
    }

    public Light getLight(String lightName){
        return new Light(lightName,url);
    }

    public Light getLight(LightID lightID) {
        switch (lightID) {
            case CUISINE:
                return new Light("Relais lampe cuisine",url);
            case SALON:
                return new Light("Relais lampe salon",url);
            case CHAMBRE:
                return new Light("Relais lampe chambre",url);
            case SALLE_DE_BAIN:
                return new Light("Relais lampe salle de bain",url);
            case ENTREE:
                return new Light("Relais lampe entree",url);
            case SALLE_A_MANGER:
                return new Light("Relais lampe salle a manger",url);
            case MICRO_ONDES_PANNEAU_BAS_GAUCHE:
                return new Light("Lumiere panneau du bas micro-ondes gauche",url);
            case MICRO_ONDES_PANNEAU_BAS_DROITE:
                return new Light("Lumiere panneau du bas micro-ondes droite",url);
            case MICRO_ONDES_PANNEAU_HAUT_UN:
                return new Light("Lumiere panneau du haut micro-ondes",url);
            case MICRO_ONDES_PANNEAU_HAUT_DEUX:
                return new Light("Lumiere panneau du haut micro-ondes deux",url);
            case MICRO_ONDES_PANNEAU_HAUT_TROIS:
                return new Light("Lumiere panneau du haut micro-ondes trois",url);
            case MICRO_ONDES_PANNEAU_HAUT_QUATRE:
                return new Light("Lumiere panneau du haut micro-ondes quatre",url);
            case MICRO_ONDES_TIROIR_BAS:
                return new Light("Lumiere tiroir sous micro-onde",url);
            case CUISINIERE_ARMOIR_BAS_DROITE:
                return new Light("Lumiere sous armoir droite cuisiniere",url);
            case CUISINIERE_ARMOIR_BAS_GAUCHE:
                return new Light("Lumiere sous armoir gauche cuisiniere",url);
            case CUISINIERE_PANNEAU_BAS_DROITE:
                return new Light("Lumiere panneau du bas droite cuinisiere",url);
            case EVIER_TIROIR_HAUT_DROITE:
                return new Light("Lumiere tiroir haut droite evier",url);
            case EVIER_TIROIR_DROITE_DEUX:
                return new Light("Lumiere tiroir deux droite evier",url);
            case EVIER_PANNEAU_BAS_DROITE:
                return new Light("Lumiere panneau du bas droite evier",url);
            case LAVE_VAISELLE_ARMOIR_BAS_DROITE:
                return new Light("Lumiere sous armoire droite lave-vaisselle",url);
            case LAVE_VAISELLE_ARMOIR_BAS_GAUCHE:
                return new Light("Lumiere sous armoir gauche lave-vaisselle",url);
            case CUISINE_SOUS_ARMOIR_MICRO_ONDES:
                return new Light("Lumiere sous armoire cuisine cote micro-ondes",url);
            case CUISINE_HOTTE:
                return new Light("Hotte lumiere en",url);
            default:
                return null;
        }
    }

}
