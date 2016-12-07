package ca.usherbrooke.domus.lights;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by grop2401 on 2015-11-10.
 */
public class Light {

    private String name;
    private String httpURL;

    public enum Actions {
        turnOn, turnOff, blink
    }

    protected Light(String name, String url) {
        this.name = name;
        this.httpURL = url;
    }

    private void executeAction(Actions action){
        try {
            String query = "lightName=" + URLEncoder.encode(name, "UTF-8");
            post(httpURL+"/"+action.toString()+"/", query);
        } catch (Exception e) {
            System.out.println("An exception occur : " + e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void turnOn() {
        executeAction(Actions.turnOn);
    }

    public void turnOff() {
        executeAction(Actions.turnOff);
    }

    public void turnBlink() {
        executeAction(Actions.blink);
    }

    private static void post(String httpURL, String query) throws IOException {
        URL myurl = new URL(httpURL);
        HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
        con.setRequestMethod("POST");

        con.setRequestProperty("Content-length", String.valueOf(query.length()));
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
        con.setDoOutput(true);
        con.setDoInput(true);

        DataOutputStream output = new DataOutputStream(con.getOutputStream());

        output.writeBytes(query);

        output.close();

        DataInputStream input = new DataInputStream(con.getInputStream());


        for (int c = input.read(); c != -1; c = input.read())
            System.out.print((char) c);
        input.close();

        System.out.println("Resp Code:" + con.getResponseCode());
        System.out.println("Resp Message:" + con.getResponseMessage());
    }
}
