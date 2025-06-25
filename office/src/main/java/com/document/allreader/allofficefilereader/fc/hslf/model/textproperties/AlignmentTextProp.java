

package com.document.allreader.allofficefilereader.fc.hslf.model.textproperties;


public class AlignmentTextProp extends TextProp
{
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    public static final int JUSTIFY = 3;
    public static final int THAIDISTRIBUTED = 5;
    public static final int JUSTIFYLOW = 6;

    public AlignmentTextProp()
    {
        super(2, 0x800, "alignment");
    }
}
