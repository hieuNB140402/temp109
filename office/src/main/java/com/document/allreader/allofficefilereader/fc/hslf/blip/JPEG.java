

package com.document.allreader.allofficefilereader.fc.hslf.blip;

import com.document.allreader.allofficefilereader.fc.hslf.model.Picture;

/**
 * Represents a JPEG picture data in a PPT file
 *
 * @author Yegor Kozlov
 */
public final class JPEG extends Bitmap
{

    /**
     * @return type of  this picture
     * @see  com.document.allreader.allofficefilereader.fc.hslf.model.Picture#JPEG
     */
    public int getType()
    {
        return Picture.JPEG;
    }

    /**
     * JPEG signature is <code>0x46A0</code>
     *
     * @return JPEG signature (<code>0x46A0</code>)
     */
    public int getSignature()
    {
        return 0x46A0;
    }
}
