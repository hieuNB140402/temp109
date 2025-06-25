

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class DocumentAtom extends RecordAtom
{
    private byte[] _header;
    private static long _type = 1001l;

    private long slideSizeX;
    private long slideSizeY;
    private long notesSizeX;
    private long notesSizeY;
    private long serverZoomFrom;
    private long serverZoomTo;

    private long notesMasterPersist;
    private long handoutMasterPersist;

    private int firstSlideNum;
    private int slideSizeType;

    private byte saveWithFonts;
    private byte omitTitlePlace;
    private byte rightToLeft;
    private byte showComments;

    private byte[] reserved;

    public long getSlideSizeX()
    {
        return slideSizeX;
    }

    public long getSlideSizeY()
    {
        return slideSizeY;
    }

    public long getNotesSizeX()
    {
        return notesSizeX;
    }

    public long getNotesSizeY()
    {
        return notesSizeY;
    }

    public void setSlideSizeX(long x)
    {
        slideSizeX = x;
    }

    public void setSlideSizeY(long y)
    {
        slideSizeY = y;
    }

    public void setNotesSizeX(long x)
    {
        notesSizeX = x;
    }

    public void setNotesSizeY(long y)
    {
        notesSizeY = y;
    }

    public long getServerZoomFrom()
    {
        return serverZoomFrom;
    }

    public long getServerZoomTo()
    {
        return serverZoomTo;
    }

    public void setServerZoomFrom(long zoom)
    {
        serverZoomFrom = zoom;
    }

    public void setServerZoomTo(long zoom)
    {
        serverZoomTo = zoom;
    }

    public long getNotesMasterPersist()
    {
        return notesMasterPersist;
    }

    public long getHandoutMasterPersist()
    {
        return handoutMasterPersist;
    }

    public int getFirstSlideNum()
    {
        return firstSlideNum;
    }

    public int getSlideSizeType()
    {
        return slideSizeType;
    }

    public boolean getSaveWithFonts()
    {
        return saveWithFonts != 0;
    }

    public boolean getOmitTitlePlace()
    {
        return omitTitlePlace != 0;
    }
    public boolean getRightToLeft()
    {
        return rightToLeft != 0;
    }

    public boolean getShowComments()
    {
        return showComments != 0;
    }


    protected DocumentAtom(byte[] source, int start, int len)
    {

        if (len < 48)
        {
            len = 48;
        }

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        slideSizeX = LittleEndian.getInt(source, start + 0 + 8);
        slideSizeY = LittleEndian.getInt(source, start + 4 + 8);
        notesSizeX = LittleEndian.getInt(source, start + 8 + 8);
        notesSizeY = LittleEndian.getInt(source, start + 12 + 8);
        serverZoomFrom = LittleEndian.getInt(source, start + 16 + 8);
        serverZoomTo = LittleEndian.getInt(source, start + 20 + 8);

        notesMasterPersist = LittleEndian.getInt(source, start + 24 + 8);
        handoutMasterPersist = LittleEndian.getInt(source, start + 28 + 8);

        firstSlideNum = LittleEndian.getShort(source, start + 32 + 8);

        slideSizeType = LittleEndian.getShort(source, start + 34 + 8);

        saveWithFonts = source[start + 36 + 8];
        omitTitlePlace = source[start + 37 + 8];
        rightToLeft = source[start + 38 + 8];
        showComments = source[start + 39 + 8];
        reserved = new byte[len - 40 - 8];
        System.arraycopy(source, start + 48, reserved, 0, reserved.length);
    }

    public long getRecordType()
    {
        return _type;
    }

    public void writeOut(OutputStream out) throws IOException
    {
        out.write(_header);

        writeLittleEndian((int)slideSizeX, out);
        writeLittleEndian((int)slideSizeY, out);
        writeLittleEndian((int)notesSizeX, out);
        writeLittleEndian((int)notesSizeY, out);
        writeLittleEndian((int)serverZoomFrom, out);
        writeLittleEndian((int)serverZoomTo, out);

        writeLittleEndian((int)notesMasterPersist, out);
        writeLittleEndian((int)handoutMasterPersist, out);

        writeLittleEndian((short)firstSlideNum, out);

        writeLittleEndian((short)slideSizeType, out);

        out.write(saveWithFonts);
        out.write(omitTitlePlace);
        out.write(rightToLeft);
        out.write(showComments);

        out.write(reserved);
    }


    public static final class SlideSize
    {
        public static final int ON_SCREEN = 0;
        public static final int LETTER_SIZED_PAPER = 1;
        public static final int A4_SIZED_PAPER = 2;
        public static final int ON_35MM = 3;
        public static final int OVERHEAD = 4;
        public static final int BANNER = 5;
        public static final int CUSTOM = 6;
    }
    
    
    public void dispose()
    {
        _header = null;
        reserved = null;
    }
}
