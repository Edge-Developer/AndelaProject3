package io.edgedev.andelaproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OPEYEMI OLORUNLEKE on 9/13/2017.
 */


public class Item {

    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("score")
    @Expose
    private Double score;

    public String getLogin() {
        return login;
    }

    public Integer get_Id() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public Double getScore() {
        return score;
    }
}