package com.main.rekordsnew.EventBus;


import com.main.rekordsnew.Others.OtherModel;

import java.util.List;

public class CollectorsSelected {
    boolean successful;
    List<OtherModel> selectedList;

    public CollectorsSelected(boolean successful, List<OtherModel> selectedList) {
        this.successful = successful;
        this.selectedList = selectedList;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public List<OtherModel> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<OtherModel> selectedList) {
        this.selectedList = selectedList;
    }
}
