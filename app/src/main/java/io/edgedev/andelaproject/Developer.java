package io.edgedev.andelaproject;
/**
 * Created by OPEYEMI OLORUNLEKE on 9/12/2017.
 */

public class Developer {
    final private int id;
    final private String username;
    final private String profile;
    final private String image;
    final private Double score;

    public Developer(int id, String username, String profile, String image, Double score) {
        this.id = id;
        this.username = username;
        this.profile = profile;
        this.image = image;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getProfile() {
        return profile;
    }

    public String getImage() {
        return image;
    }

    public String getScore() {
        return ""+score;
    }
}
