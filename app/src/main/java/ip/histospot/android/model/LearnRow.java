package ip.histospot.android.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Denisa on 04.05.2018.
 */

public class LearnRow implements Serializable {

    private int id;
    private String name;

    public LearnRow() {

    }

    public LearnRow(int id, int rank, Bitmap picture, String name, int points) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
