package com.document.allreader.allofficefilereader.models;


public class ModelFilesHolder {
     String fileName;
     String fileUri;
     boolean isSelected;

    public ModelFilesHolder(String str, String str2, boolean z) {
        this.fileName = str;
        this.fileUri = str2;
        this.isSelected = z;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileUri() {
        return this.fileUri;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }
}
