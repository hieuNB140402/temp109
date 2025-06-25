package com.document.allreader.allofficefilereader.fc.p013sl.usermodel;

/* renamed from: com.allreader.office.allofficefilereader.fc.sl.usermodel.Slide */

public interface Slide extends Sheet {
    boolean getFollowMasterBackground();

    boolean getFollowMasterColourScheme();

    boolean getFollowMasterObjects();

    Notes getNotes();

    void setFollowMasterBackground(boolean z);

    void setFollowMasterColourScheme(boolean z);

    void setFollowMasterObjects(boolean z);

    void setNotes(Notes notes);
}
