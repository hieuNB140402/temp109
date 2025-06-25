package com.document.allreader.allofficefilereader.common.autoshape.pathbuilder;

import android.graphics.Path;
import android.graphics.PointF;


public class ArrowPathAndTail {
    private Path path;
    private PointF tail;

    public void reset() {
        this.path = null;
        this.tail = null;
    }

    public Path getArrowPath() {
        return this.path;
    }

    public void setArrowPath(Path path) {
        this.path = path;
    }

    public PointF getArrowTailCenter() {
        return this.tail;
    }

    public void setArrowTailCenter(float f, float f2) {
        this.tail = new PointF(f, f2);
    }
}
