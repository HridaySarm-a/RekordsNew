package com.main.rekordsnew.Admin.AutoGenerate.Models;

import com.main.rekordsnew.LeafCollectors.Model.LeafEntryModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoChallanRoot {

    Map<String,LeafEntryModel> entries;

    public AutoChallanRoot() {
    }

    public AutoChallanRoot(Map<String, LeafEntryModel> entries) {
        this.entries = entries;
    }


    public Map<String, LeafEntryModel> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, LeafEntryModel> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "AutoChallanRoot{" +
                "entries=" + entries +
                '}';
    }
}
