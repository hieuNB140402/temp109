
package com.document.allreader.allofficefilereader.thirdpart.emf.data;

import java.io.IOException;

import com.document.allreader.allofficefilereader.thirdpart.emf.EMFInputStream;
import com.document.allreader.allofficefilereader.thirdpart.emf.EMFRenderer;
import com.document.allreader.allofficefilereader.thirdpart.emf.EMFTag;

/**
 * CreatePen TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: CreatePen.java 10367 2007-01-22 19:26:48Z duns $
 */
public class CreatePen extends EMFTag
{

    private int index;

    private LogPen pen;

    public CreatePen()
    {
        super(38, 1);
    }

    public CreatePen(int index, LogPen pen)
    {
        this();
        this.index = index;
        this.pen = pen;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new CreatePen(emf.readDWORD(), new LogPen(emf));
    }

    public String toString()
    {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(index) + "\n"
            + pen.toString();
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer)
    {

        renderer.storeGDIObject(index, pen);
    }
}
