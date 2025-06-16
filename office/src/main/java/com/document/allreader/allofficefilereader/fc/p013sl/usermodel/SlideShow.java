package com.document.allreader.allofficefilereader.fc.p013sl.usermodel;

import java.io.IOException;

/* renamed from: com.allreader.office.allofficefilereader.fc.sl.usermodel.SlideShow */

public interface SlideShow {
    MasterSheet createMasterSheet() throws IOException;

    Slide createSlide() throws IOException;

    MasterSheet[] getMasterSheet();

    Resources getResources();

    Slide[] getSlides();
}
