package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.Footer */

public interface Footer extends HeaderFooter {
    @Override // fc4ss.usermodel.HeaderFooter
    String getCenter();

    @Override // fc4ss.usermodel.HeaderFooter
    String getLeft();

    @Override // fc4ss.usermodel.HeaderFooter
    String getRight();

    @Override // fc4ss.usermodel.HeaderFooter
    void setCenter(String str);

    @Override // fc4ss.usermodel.HeaderFooter
    void setLeft(String str);

    @Override // fc4ss.usermodel.HeaderFooter
    void setRight(String str);
}
