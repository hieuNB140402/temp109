

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.ddf.EscherTextboxRecord;



public final class EscherTextboxWrapper extends RecordContainer
{
    private EscherTextboxRecord _escherRecord;
    private long _type;
    private int shapeId;

    public EscherTextboxRecord getEscherRecord()
    {
        return _escherRecord;
    }


    public EscherTextboxWrapper(EscherTextboxRecord textbox)
    {
        _escherRecord = textbox;
        _type = _escherRecord.getRecordId();


        byte[] data = _escherRecord.getData();
        _children = Record.findChildRecords(data, 0, data.length);
    }

    public EscherTextboxWrapper()
    {
        _escherRecord = new EscherTextboxRecord();
        _escherRecord.setRecordId(EscherTextboxRecord.RECORD_ID);
        _escherRecord.setOptions((short)15);

        _children = new Record[0];
    }


    public long getRecordType()
    {
        return _type;
    }


    public int getShapeId()
    {
        return shapeId;
    }

    public void setShapeId(int id)
    {
        shapeId = id;
    }
    
    public void dispose()
    {
        super.dispose();
        if (_escherRecord != null)
        {
            _escherRecord.dispose();
            _escherRecord = null;
        }
    }
}
