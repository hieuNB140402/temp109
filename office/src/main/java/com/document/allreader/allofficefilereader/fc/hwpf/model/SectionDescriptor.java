

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


@ Internal
public final class SectionDescriptor
{

    private short fn;



    private int fcSepx;


    private short fnMpr;


    private int fcMpr;

    public SectionDescriptor()
    {
    }

    public SectionDescriptor(byte[] buf, int offset)
    {
        fn = LittleEndian.getShort(buf, offset);
        offset += LittleEndian.SHORT_SIZE;
        fcSepx = LittleEndian.getInt(buf, offset);
        offset += LittleEndian.INT_SIZE;
        fnMpr = LittleEndian.getShort(buf, offset);
        offset += LittleEndian.SHORT_SIZE;
        fcMpr = LittleEndian.getInt(buf, offset);
    }

    public int getFc()
    {
        return fcSepx;
    }

    public void setFc(int fc)
    {
        this.fcSepx = fc;
    }

    public boolean equals(Object o)
    {
        SectionDescriptor sed = (SectionDescriptor)o;
        return sed.fn == fn && sed.fnMpr == fnMpr;
    }

    public byte[] toByteArray()
    {
        int offset = 0;
        byte[] buf = new byte[12];

        LittleEndian.putShort(buf, offset, fn);
        offset += LittleEndian.SHORT_SIZE;
        LittleEndian.putInt(buf, offset, fcSepx);
        offset += LittleEndian.INT_SIZE;
        LittleEndian.putShort(buf, offset, fnMpr);
        offset += LittleEndian.SHORT_SIZE;
        LittleEndian.putInt(buf, offset, fcMpr);

        return buf;
    }

    @ Override
    public String toString()
    {
        return "[SED] (fn: " + fn + "; fcSepx: " + fcSepx + "; fnMpr: " + fnMpr + "; fcMpr: "
            + fcMpr + ")";
    }
}
