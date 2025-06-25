

package com.document.allreader.allofficefilereader.fc.hslf.record;


import java.io.OutputStream;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;

import java.io.IOException;


public final class OutlineTextRefAtom extends RecordAtom
{

    private byte[] _header;


    private int _index;




    public long getRecordType()
    {
        return RecordTypes.OutlineTextRefAtom.typeID;
    }


    public void writeOut(OutputStream out) throws IOException
    {
        out.write(_header);

        byte[] recdata = new byte[4];
        LittleEndian.putInt(recdata, 0, _index);
        out.write(recdata);
    }

    public void setTextIndex(int idx)
    {
        _index = idx;
    }

    public int getTextIndex()
    {
        return _index;
    }
    

    public void dispose()
    {
        _header = null;
    }

}
