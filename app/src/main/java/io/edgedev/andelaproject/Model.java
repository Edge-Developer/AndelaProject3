package io.edgedev.andelaproject;

/**
 * Created by OPEYEMI OLORUNLEKE on 9/13/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {

    @SerializedName("total_count")
    @Expose
    private int totalCount;

    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;

    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public List<Item> getItems() {
        return items;
    }

    public int getTotalCount() {
        return totalCount;
    }
}