package ip.histospot.android.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by alex on 10.04.2018.
 */

public class LeaderboardRow implements Serializable {

    private int id;
    private int rank;
    private Bitmap picture;
    private String name;
    private int points;
    private int level;

    public LeaderboardRow() {

    }

    public LeaderboardRow(int id, int rank, Bitmap picture, String name, int points, int level) {
        this.id = id;
        this.rank = rank;
        this.picture = picture;
        this.name = name;
        this.points = points;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
