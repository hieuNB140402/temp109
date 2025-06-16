

package com.document.allreader.allofficefilereader.fc.hslf.blip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.hslf.exceptions.HSLFException;
import com.document.allreader.allofficefilereader.fc.hslf.model.Picture;
import com.document.allreader.allofficefilereader.java.awt.Dimension;
import com.document.allreader.allofficefilereader.java.awt.Rectangle;


/**
 * Represents Macintosh PICT picture data.
 *
 * @author Yegor Kozlov
 */
public final class PICT extends Metafile
{

    public PICT()
    {
        super();
    }

    /**
     * Extract compressed PICT data from a ppt
     */
    public byte[] getData()
    {
        byte[] rawdata = getRawData();
        try
        {
            byte[] macheader = new byte[512];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            out.write(macheader);
            int pos = CHECKSUM_SIZE;
            byte[] pict;
            try
            {
                pict = read(rawdata, pos);
            }
            catch(IOException e)
            {
                //weird MAC behaviour.
                //if failed to read right after the checksum - skip 16 bytes and try again
                pict = read(rawdata, pos + 16);
            }
            out.write(pict);
            return out.toByteArray();
        }
        catch(IOException e)
        {
            throw new HSLFException(e);
        }
    }

    private byte[] read(byte[] data, int pos) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        Header header = new Header();
        header.read(data, pos);
        bis.skip(pos + header.getSize());
        InflaterInputStream inflater = new InflaterInputStream(bis);
        byte[] chunk = new byte[4096];
        int count;
        while ((count = inflater.read(chunk)) >= 0)
        {
            out.write(chunk, 0, count);
        }
        inflater.close();
        return out.toByteArray();
    }

    public void setData(byte[] data) throws IOException
    {
        int pos = 512; //skip the first 512 bytes - they are MAC specific crap
        byte[] compressed = compress(data, pos, data.length - pos);

        Header header = new Header();
        header.wmfsize = data.length - 512;
        //we don't have a PICT reader in java, have to set default image size  200x200
        header.bounds = new Rectangle(0, 0, 200, 200);
        header.size = new Dimension(header.bounds.width * ShapeKit.EMU_PER_POINT, header.bounds.height
            * ShapeKit.EMU_PER_POINT);
        header.zipsize = compressed.length;

        byte[] checksum = getChecksum(data);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(checksum);

        out.write(new byte[16]); //16-byte prefix which is safe to ignore
        header.write(out);
        out.write(compressed);

        setRawData(out.toByteArray());
    }

    /**
     * @see com.document.allreader.allofficefilereader.fc.hslf.model.Picture#PICT
     */
    public int getType()
    {
        return Picture.PICT;
    }

    /**
     * PICT signature is <code>0x5430</code>
     *
     * @return PICT signature (<code>0x5430</code>)
     */
    public int getSignature()
    {
        return 0x5430;
    }

}
