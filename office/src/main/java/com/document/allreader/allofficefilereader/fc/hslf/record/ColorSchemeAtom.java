

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class ColorSchemeAtom extends RecordAtom
{
    private byte[] _header;
    private static long _type = 2032l;

    private int backgroundColourRGB;
    private int textAndLinesColourRGB;
    private int shadowsColourRGB;
    private int titleTextColourRGB;
    private int fillsColourRGB;
    private int accentColourRGB;
    private int accentAndHyperlinkColourRGB;
    private int accentAndFollowingHyperlinkColourRGB;


    public int getAccentAndHyperlinkColourRGB()
    {
        return accentAndHyperlinkColourRGB;
    }


    protected ColorSchemeAtom(byte[] source, int start, int len)
    {

        if (len < 40)
        {
            len = 40;
            if (source.length - start < 40)
            {
                throw new RuntimeException(
                    "Not enough data to form a ColorSchemeAtom (always 40 bytes long) - found "
                        + (source.length - start));
            }
        }


        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        backgroundColourRGB = LittleEndian.getInt(source, start + 8 + 0);
        textAndLinesColourRGB = LittleEndian.getInt(source, start + 8 + 4);
        shadowsColourRGB = LittleEndian.getInt(source, start + 8 + 8);
        titleTextColourRGB = LittleEndian.getInt(source, start + 8 + 12);
        fillsColourRGB = LittleEndian.getInt(source, start + 8 + 16);
        accentColourRGB = LittleEndian.getInt(source, start + 8 + 20);
        accentAndHyperlinkColourRGB = LittleEndian.getInt(source, start + 8 + 24);
        accentAndFollowingHyperlinkColourRGB = LittleEndian.getInt(source, start + 8 + 28);
    }


    public ColorSchemeAtom()
    {
        _header = new byte[8];
        LittleEndian.putUShort(_header, 0, 16);
        LittleEndian.putUShort(_header, 2, (int)_type);
        LittleEndian.putInt(_header, 4, 32);


        backgroundColourRGB = 16777215;
        textAndLinesColourRGB = 0;
        shadowsColourRGB = 8421504;
        titleTextColourRGB = 0;
        fillsColourRGB = 10079232;
        accentColourRGB = 13382451;
        accentAndHyperlinkColourRGB = 16764108;
        accentAndFollowingHyperlinkColourRGB = 11711154;
    }

    public long getRecordType()
    {
        return _type;
    }


    public static byte[] splitRGB(int rgb)
    {
        byte[] ret = new byte[3];


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            writeLittleEndian(rgb, baos);
        }
        catch(IOException ie)
        {

            throw new RuntimeException(ie);
        }
        byte[] b = baos.toByteArray();
        System.arraycopy(b, 0, ret, 0, 3);

        return ret;
    }


    public static int joinRGB(byte r, byte g, byte b)
    {
        return joinRGB(new byte[]{r, g, b});
    }

    public static int joinRGB(byte[] rgb)
    {
        if (rgb.length != 3)
        {
            throw new RuntimeException("joinRGB accepts a byte array of 3 values, but got one of "
                + rgb.length + " values!");
        }
        byte[] with_zero = new byte[4];
        System.arraycopy(rgb, 0, with_zero, 0, 3);
        with_zero[3] = 0;
        int ret = LittleEndian.getInt(with_zero, 0);
        return ret;
    }

    public void writeOut(OutputStream out) throws IOException
    {

        out.write(_header);

        writeLittleEndian(backgroundColourRGB, out);
        writeLittleEndian(textAndLinesColourRGB, out);
        writeLittleEndian(shadowsColourRGB, out);
        writeLittleEndian(titleTextColourRGB, out);
        writeLittleEndian(fillsColourRGB, out);
        writeLittleEndian(accentColourRGB, out);
        writeLittleEndian(accentAndHyperlinkColourRGB, out);
        writeLittleEndian(accentAndFollowingHyperlinkColourRGB, out);
    }

    public int getColor(int idx)
    {
        int[] clr = {backgroundColourRGB, textAndLinesColourRGB, shadowsColourRGB,
            titleTextColourRGB, fillsColourRGB, accentColourRGB, accentAndHyperlinkColourRGB,
            accentAndFollowingHyperlinkColourRGB};
        return clr[idx];
    }
    
    
    public void dispose()
    {
        _header = null;
    }
}
