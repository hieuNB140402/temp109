package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

import com.document.allreader.allofficefilereader.fc.hssf.usermodel.IClientAnchor;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.Drawing */

public interface Drawing {
    IClientAnchor createAnchor(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    Comment createCellComment(IClientAnchor iClientAnchor);

    Chart createChart(IClientAnchor iClientAnchor);

    Picture createPicture(IClientAnchor iClientAnchor, int i);
}
