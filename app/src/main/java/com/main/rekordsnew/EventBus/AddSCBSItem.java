package com.main.rekordsnew.EventBus;


import com.main.rekordsnew.Others.OtherModel;

public class AddSCBSItem {
    boolean successful;
    OtherModel collectorNode;

    public AddSCBSItem(boolean successful, OtherModel collectorNode) {
        this.successful = successful;
        this.collectorNode = collectorNode;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public OtherModel getCollectorNode() {
        return collectorNode;
    }

    public void setCollectorNode(OtherModel collectorNode) {
        this.collectorNode = collectorNode;
    }
}
