

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public class ExEmbedAtom extends RecordAtom
{


    private byte[] _header;


    private byte[] _data;


    protected ExEmbedAtom()
    {
        _header = new byte[8];
        _data = new byte[7];

        LittleEndian.putShort(_header, 2, (short)getRecordType());
        LittleEndian.putInt(_header, 4, _data.length);

    }


    protected ExEmbedAtom(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        _data = new byte[len - 8];
        System.arraycopy(source, start + 8, _data, 0, len - 8);

        if (_data.length < 7)
        {
            throw new IllegalArgumentException(
                "The length of the data for a ExEmbedAtom must be at least 4 bytes, but was only "
                    + _data.length);
        }
    }

    public int getFollowColorScheme()
    {
        return LittleEndian.getInt(_data, 0);
    }


    public boolean getCantLockServerB()
    {
        return _data[4] != 0;
    }


    public boolean getNoSizeToServerB()
    {
        return _data[5] != 0;
    }


    public boolean getIsTable()
    {
        return _data[6] != 0;
    }

    public long getRecordType()
    {
        return RecordTypes.ExEmbedAtom.typeID;
    }

    
    public void dispose()
    {
        _header = null;
        _data = null;
    }

}
