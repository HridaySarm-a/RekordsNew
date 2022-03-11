package com.main.rekordsnew.Interface;

public interface UpdateFileCallback {
    void onFileUpdated(String fileName, boolean isSuccess);

    void onFileUpdated(String fileName);
}
