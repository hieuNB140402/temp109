

package com.document.allreader.allofficefilereader.fc.hslf.usermodel;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.document.allreader.allofficefilereader.fc.hslf.blip.DIB;
import com.document.allreader.allofficefilereader.fc.hslf.blip.EMF;
import com.document.allreader.allofficefilereader.fc.hslf.blip.ImagePainter;
import com.document.allreader.allofficefilereader.fc.hslf.blip.JPEG;
import com.document.allreader.allofficefilereader.fc.hslf.blip.PICT;
import com.document.allreader.allofficefilereader.fc.hslf.blip.PNG;
import com.document.allreader.allofficefilereader.fc.hslf.blip.WMF;
import com.document.allreader.allofficefilereader.fc.hslf.exceptions.HSLFException;
import com.document.allreader.allofficefilereader.fc.hslf.model.Picture;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public abstract class PictureData
{


    protected static final int CHECKSUM_SIZE = 16;

    protected byte[] rawdata;

    protected int offset;
    
    private String tempFilePath;


    public abstract int getType();


    public abstract byte[] getData();

    public abstract void setData(byte[] data) throws IOException;


    protected abstract int getSignature();

    protected static ImagePainter[] painters = new ImagePainter[8];



    public byte[] getRawData()
    {
        return rawdata;
    }
    
    public void setRawData(byte[] data)
    {
        rawdata = data;
    }


    public int getOffset()
    {
        return offset;
    }


    public void setOffset(int offset)
    {
        this.offset = offset;
    }


    public byte[] getUID()
    {
        byte[] uid = new byte[16];
        //System.arraycopy(rawdata, 0, uid, 0, uid.length);
        return uid;
    }


    public static byte[] getChecksum(byte[] data)
    {
        MessageDigest sha;
        try
        {
            sha = MessageDigest.getInstance("MD5");
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new HSLFException(e.getMessage());
        }
        sha.update(data);
        return sha.digest();
    }


    public static PictureData create(int type)
    {
        PictureData pict;
        switch (type)
        {
            case Picture.EMF:
                pict = new EMF();
                break;
            case Picture.WMF:
                pict = new WMF();
                break;
            case Picture.PICT:
                pict = new PICT();
                break;
            case Picture.JPEG:
                pict = new JPEG();
                break;
            case Picture.PNG:
                pict = new PNG();
                break;
            case Picture.DIB:
                pict = new DIB();
                break;
            default:
                throw new IllegalArgumentException("Unsupported picture type: " + type);
        }
        return pict;
    }

    public byte[] getHeader()
    {
        byte[] header = new byte[16 + 8];
        LittleEndian.putInt(header, 0, getSignature());
        return header;
    }


    public int getSize()
    {
        return getData().length;
    }


    public String getTempFilePath()
    {
        return tempFilePath;
    }


    public void setTempFilePath(String tempFilePath)
    {
        this.tempFilePath = tempFilePath;
    }
    
    public void dispose()
    {
        tempFilePath = null;

    }

}
