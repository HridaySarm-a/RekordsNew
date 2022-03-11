package com.main.rekordsnew.LeafCollectors.Model;

public class LeafEntryModel {
    private String date;
    private String growerId;
    private float quantity;
    private String key;
    private String leaf_collector_id;

    public LeafEntryModel(String date, String growerId, float quantity, String leaf_collector_id) {
        this.date = date;
        this.growerId = growerId;
        this.quantity = quantity;
        this.leaf_collector_id = leaf_collector_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGrowerId() {
        return growerId;
    }

    public void setGrowerId(String growerId) {
        this.growerId = growerId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getLeaf_collector_id() {
        return leaf_collector_id;
    }

    public void setLeaf_collector_id(String leaf_collector_id) {
        this.leaf_collector_id = leaf_collector_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "LeafEntryModel{" +
                "date='" + date + '\'' +
                ", growerId='" + growerId + '\'' +
                ", quantity=" + quantity +
                ", leaf_collector_id='" + leaf_collector_id + '\'' +
                '}';
    }
}
