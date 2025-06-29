package com.artifex.mupdfdemo;

import android.graphics.RectF;

public class Annotation extends RectF {
    public final Type type;

    public enum Type {
        TEXT, LINK, FREETEXT, LINE, SQUARE, CIRCLE, POLYGON, POLYLINE, HIGHLIGHT, UNDERLINE, SQUIGGLY, STRIKEOUT, STAMP, CARET, INK, POPUP, FILEATTACHMENT, SOUND, MOVIE, WIDGET, SCREEN, PRINTERMARK, TRAPNET, WATERMARK, A3D, UNKNOWN
    }

    public Annotation(float x0, float y0, float x1, float y1, int _type) {
        super(x0, y0, x1, y1);
        type = _type == -1 ? Type.UNKNOWN : Type.values()[_type];
    }
}
