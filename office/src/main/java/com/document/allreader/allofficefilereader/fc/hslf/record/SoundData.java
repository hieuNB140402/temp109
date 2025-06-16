

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class SoundData extends RecordAtom
{


    private byte[] _header;


    private byte[] _data;


    protected SoundData()
    {
        _header = new byte[8];
        _data = new byte[0];

        LittleEndian.putShort(_header, 2, (short)getRecordType());
        LittleEndian.putInt(_header, 4, _data.length);
    }

    protected SoundData(byte[] source, int start, int len)
    {
        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        _data = new byte[len - 8];
        System.arraycopy(source, start + 8, _data, 0, len - 8);
    }


    public byte[] getData()
    {
        return _data;
    }


    public long getRecordType()
    {
        return RecordTypes.SoundData.typeID;
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
