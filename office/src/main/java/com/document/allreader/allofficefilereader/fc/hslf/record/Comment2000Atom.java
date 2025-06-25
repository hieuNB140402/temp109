

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import com.document.allreader.allofficefilereader.fc.hslf.util.SystemTimeUtils;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;




public final class Comment2000Atom extends RecordAtom
{

    private byte[] _header;


    private byte[] _data;

    protected Comment2000Atom()
    {
        _header = new byte[8];
        _data = new byte[28];

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


    public Date getDate()
    {
        return SystemTimeUtils.getDate(_data, 4);
    }


    public void setDate(Date date)
    {
        SystemTimeUtils.storeDate(date, _data, 4);
    }


    public int getXOffset()
    {
        return LittleEndian.getInt(_data, 20);
    }


    public void setXOffset(int xOffset)
    {
        LittleEndian.putInt(_data, 20, xOffset);
    }




    public long getRecordType()
    {
        return RecordTypes.Comment2000Atom.typeID;
    }


    public void writeOut(OutputStream out) throws IOException
    {
        out.write(_header);
        out.write(_data);
    }
    
    
    public void dispose()
    {
        _header = null;
        _data = null;
    }
}
