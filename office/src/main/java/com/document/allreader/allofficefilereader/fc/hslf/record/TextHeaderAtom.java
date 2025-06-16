

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class TextHeaderAtom extends RecordAtom implements ParentAwareRecord
{
    private byte[] _header;
    private static long _type = 3999l;
    private RecordContainer parentRecord;

    public static final int TITLE_TYPE = 0;
    public static final int BODY_TYPE = 1;
    public static final int NOTES_TYPE = 2;
    public static final int OTHER_TYPE = 4;
    public static final int CENTRE_BODY_TYPE = 5;
    public static final int CENTER_TITLE_TYPE = 6;
    public static final int HALF_BODY_TYPE = 7;
    public static final int QUARTER_BODY_TYPE = 8;

    private int textType;

    public int getTextType()
    {
        return textType;
    }

    public void setTextType(int type)
    {
        textType = type;
    }

    public RecordContainer getParentRecord()
    {
        return parentRecord;
    }

    public void setParentRecord(RecordContainer record)
    {
        this.parentRecord = record;
    }


    protected TextHeaderAtom(byte[] source, int start, int len)
    {

        if (len < 12)
        {
            len = 12;
            if (source.length - start < 12)
            {
                throw new RuntimeException(
                    "Not enough data to form a TextHeaderAtom (always 12 bytes long) - found "
                        + (source.length - start));
            }
        }

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        textType = LittleEndian.getInt(source, start + 8);
    }

    public TextHeaderAtom()
    {
        _header = new byte[8];
        LittleEndian.putUShort(_header, 0, 0);
        LittleEndian.putUShort(_header, 2, (int)_type);
        LittleEndian.putInt(_header, 4, 4);

        textType = OTHER_TYPE;
    }


    public long getRecordType()
    {
        return _type;
    }
    

    public void dispose()
    {
        _header = null;
        parentRecord = null;
    }
}
