

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class ExHyperlinkAtom extends RecordAtom
{

    private byte[] _header;


    private byte[] _data;


    protected ExHyperlinkAtom()
    {
        _header = new byte[8];
        _data = new byte[4];

        LittleEndian.putShort(_header, 2, (short)getRecordType());
        LittleEndian.putInt(_header, 4, _data.length);


    }





    public int getNumber()
    {
        return LittleEndian.getInt(_data, 0);
    }


    public void setNumber(int number)
    {
        LittleEndian.putInt(_data, 0, number);
    }

    public long getRecordType()
    {
        return RecordTypes.ExHyperlinkAtom.typeID;
    }

    
    
    public void dispose()
    {
        _header = null;
        _data = null;
    }

}
