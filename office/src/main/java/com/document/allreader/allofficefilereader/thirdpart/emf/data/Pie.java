
package com.document.allreader.allofficefilereader.thirdpart.emf.data;

import android.graphics.Point;

import java.io.IOException;

import com.document.allreader.allofficefilereader.java.awt.Rectangle;
import com.document.allreader.allofficefilereader.java.awt.geom.Arc2D;
import com.document.allreader.allofficefilereader.thirdpart.emf.EMFInputStream;
import com.document.allreader.allofficefilereader.thirdpart.emf.EMFRenderer;
import com.document.allreader.allofficefilereader.thirdpart.emf.EMFTag;

/**
 * Pie TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: Pie.java 10377 2007-01-23 15:44:34Z duns $
 */
public class Pie extends AbstractArc {

    public Pie() {
        super(47, 1, null, null, null);
    }

    public Pie(Rectangle bounds, Point start, Point end) {
        super(47, 1, bounds, start, end);
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len)
            throws IOException {

        return new Pie(
            emf.readRECTL(),
            emf.readPOINTL(),
            emf.readPOINTL());
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer) {
        renderer.fillAndDrawOrAppend(
            getShape(renderer, Arc2D.PIE));
    }
}
