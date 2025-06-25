package com.document.allreader.allofficefilereader.fc.p013sl.usermodel;

import com.document.allreader.allofficefilereader.java.awt.geom.Rectangle2D;

/* renamed from: com.allreader.office.allofficefilereader.fc.sl.usermodel.Shape */

public interface Shape {
    Rectangle2D getAnchor();

    Shape getParent();

    int getShapeType();

    void moveTo(float f, float f2);

    void setAnchor(Rectangle2D rectangle2D);
}
