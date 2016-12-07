package ca.usherbrooke.domus;

import ca.usherbrooke.domus.lights.Light;
import ca.usherbrooke.domus.lights.LightRepository;

public class Main {

    public static void main(String[] args) {

        /*SpeakerRepository repository = new SpeakerRepository();
        Speaker speakerChambre = repository.getSpeaker(SpeakerRepository.SpeakerId.CHAMBRE);
        Speaker speakerCuisine = repository.getSpeaker(SpeakerRepository.SpeakerId.CUISINE);
        Speaker speakerSalon = repository.getSpeaker(SpeakerRepository.SpeakerId.SALON);
        Speaker speakerSalleDeBain = repository.getSpeaker(SpeakerRepository.SpeakerId.SALLE_DE_BAIN);

        Speaker all = repository.getSpeaker(SpeakerRepository.SpeakerId.ALL);

        String soundToPlay = "D:\\Son\\silentnight.mp3";*/


/*

        speakerChambre.play(soundToPlay);
        pause();
        speakerChambre.stop();

        speakerSalleDeBain.play(soundToPlay);
        pause();
        speakerSalleDeBain.stop();

        speakerCuisine.play(soundToPlay);
        pause();
        speakerCuisine.stop();

        speakerSalon.play(soundToPlay);
        pause();
        speakerSalon.stop();
*/
/*
        all.play(soundToPlay);
        pause();
        all.stop();*/


        LightRepository lightRepo = new LightRepository();
        Light chambre = lightRepo.getLight(LightRepository.LightID.MICRO_ONDES_TIROIR_BAS);

        System.out.println(chambre.getName());
        chambre.turnOff();
        //Light chambre1 = lightRepo.getLight(LightRepository.LightID.MICRO_ONDES_PANNEAU_BAS_GAUCHE);
        //Light chambre2 = lightRepo.getLight(LightRepository.LightID.MICRO_ONDES_PANNEAU_HAUT_UN);
        //Light chambre3 = lightRepo.getLight(LightRepository.LightID.MICRO_ONDES_PANNEAU_HAUT_DEUX);
        //Light chambre4 = lightRepo.getLight(LightRepository.LightID.MICRO_ONDES_PANNEAU_HAUT_TROIS);
        //Light chambre5 = lightRepo.getLight(LightRepository.LightID.MICRO_ONDES_PANNEAU_HAUT_QUATRE);
        //Light chambre6 = lightRepo.getLight(LightRepository.LightID.LAVE_VAISELLE_ARMOIR_BAS_DROITE);
        //Light chambre7 = lightRepo.getLight(LightRepository.LightID.LAVE_VAISELLE_ARMOIR_BAS_GAUCHE);
        //Light chambre8 = lightRepo.getLight(LightRepository.LightID.CUISINIERE_ARMOIR_BAS_DROITE);
        //Light chambre9 = lightRepo.getLight(LightRepository.LightID.CUISINIERE_ARMOIR_BAS_GAUCHE);
        //Light chambre10 = lightRepo.getLight(LightRepository.LightID.CUISINIERE_PANNEAU_BAS_DROITE);
        //Light chambre11 = lightRepo.getLight(LightRepository.LightID.EVIER_PANNEAU_BAS_DROITE);
        //Light chambre12 = lightRepo.getLight(LightRepository.LightID.EVIER_TIROIR_DROITE_DEUX);
        //Light chambre13 = lightRepo.getLight(LightRepository.LightID.EVIER_TIROIR_HAUT_DROITE);
        //Light chambre14 = lightRepo.getLight(LightRepository.LightID.CUISINE_SOUS_ARMOIR_MICRO_ONDES);

        //for (LightRepository.LightID v: LightRepository.LightID.values()) {
        //    Light c = lightRepo.getLight(v);
        //    System.out.println(c.getName());
        //    c.turnOff();
        //    pause(4000);
        //}

        System.out.println(" Démarage du test");

        //SpeakerRepository repository = new SpeakerRepository();
        //Speaker all = repository.getSpeaker(SpeakerRepository.SpeakerId.ALL);
        //
        //String soundToPlay = "D:\\Son\\silentnight.mp3";
        //all.play(soundToPlay);
        //pause(30000);
        //all.stop();


        //System.out.println("Clignotement de la chambre");
        //chambre.turnBlink();
        //pause(5000);
        //System.out.println("Fermeture de la lumière de la chambre");
        //chambre.turnOff();
    }

    private static void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }
    }
}
