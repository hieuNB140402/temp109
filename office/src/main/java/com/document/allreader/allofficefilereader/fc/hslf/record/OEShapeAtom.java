

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class OEShapeAtom extends RecordAtom
{


    private byte[] _header;


    private byte[] _recdata;


    public OEShapeAtom()
    {
        _recdata = new byte[4];

        _header = new byte[8];
        LittleEndian.putShort(_header, 2, (short)getRecordType());
        LittleEndian.putInt(_header, 4, _recdata.length);
    }



    public long getRecordType()
    {
        return RecordTypes.OEShapeAtom.typeID;
    }

    public int getOptions()
    {
        return LittleEndian.getInt(_recdata, 0);
    }


    public void setOptions(int id)
    {
        LittleEndian.putInt(_recdata, 0, id);
    }
    

    public void dispose()
    {
        _header = null;
        _recdata = null;
    }
}
