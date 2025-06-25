
package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import com.document.allreader.allofficefilereader.common.pictureefftect.PictureEffectInfo;
import com.document.allreader.allofficefilereader.system.IControl;


public interface OfficeDrawing {

    public enum HorizontalPositioning {


        ABSOLUTE,


        CENTER,


        INSIDE,


        LEFT,


        OUTSIDE,


        RIGHT;
    }

    public enum HorizontalRelativeElement {
        CHAR, MARGIN, PAGE, TEXT;
    }

    public enum VerticalPositioning {


        ABSOLUTE,

        BOTTOM,

        CENTER,

        INSIDE,

        OUTSIDE,

        TOP;
    }

    public enum VerticalRelativeElement {
        LINE, MARGIN, PAGE, TEXT;
    }

    public PictureEffectInfo getPictureEffectInfor();


    public byte getHorizontalPositioning();


    public byte getHorizontalRelative();


    byte[] getPictureData(IControl control);

    public byte[] getPictureData(IControl control, int index);

    public HWPFShape getAutoShape();


    int getRectangleBottom();


    int getRectangleLeft();

    int getRectangleRight();

    int getRectangleTop();


    int getShapeId();

    public int getWrap();


    public boolean isBelowText();


    public boolean isAnchorLock();

    public String getTempFilePath(IControl control);


    public byte getVerticalPositioning();


    public byte getVerticalRelativeElement();

}
