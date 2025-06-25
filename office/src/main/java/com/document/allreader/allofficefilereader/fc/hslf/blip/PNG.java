

package com.document.allreader.allofficefilereader.fc.hslf.blip;

import com.document.allreader.allofficefilereader.fc.hslf.model.Picture;

/**
 * Represents a PNG picture data in a PPT file
 *
 * @author Yegor Kozlov
 */
public final class PNG extends Bitmap
{

    /**
     * @return PNG data
     */
    public byte[] getData()
    {
        byte[] data = super.getData();
        /*try
        {
            //PNG created on MAC may have a 16-byte prefix which prevents successful reading.
            //Just cut it off!.
            BufferedImage bi = ImageIO.read(new ByteArrayInputStream(data));
            if (bi == null)
            {
                byte[] png = new byte[data.length - 16];
                System.arraycopy(data, 16, png, 0, png.length);
                data = png;
            }
        }
        catch(IOException e)
        {
            throw new HSLFException(e);
        }*/
        return data;
    }

    /**
     * @return type of  this picture
     * @see  com.document.allreader.allofficefilereader.fc.hslf.model.Picture#PNG
     */
    public int getType()
    {
        return Picture.PNG;
    }

    /**
     * PNG signature is <code>0x6E00</code>
     *
     * @return PNG signature (<code>0x6E00</code>)
     */
    public int getSignature()
    {
        return 0x6E00;
    }
}
