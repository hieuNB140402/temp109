
package com.document.allreader.allofficefilereader.thirdpart.emf.data;

import java.io.IOException;

import com.document.allreader.allofficefilereader.java.awt.geom.AffineTransform;
import com.document.allreader.allofficefilereader.java.awt.geom.GeneralPath;
import com.document.allreader.allofficefilereader.thirdpart.emf.EMFInputStream;
import com.document.allreader.allofficefilereader.thirdpart.emf.EMFRenderer;
import com.document.allreader.allofficefilereader.thirdpart.emf.EMFTag;

/**
 * BeginPath TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: BeginPath.java 10367 2007-01-22 19:26:48Z duns $
 */
public class BeginPath extends EMFTag {

    public BeginPath() {
        super(59, 1);
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len)
            throws IOException {

        return this;
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer) {
        // The BeginPath function opens a path bracket in the specified
        // device context.
        renderer.setPath(new GeneralPath(
            renderer.getWindingRule()));
        renderer.setPathTransform(new AffineTransform());
    }
}
