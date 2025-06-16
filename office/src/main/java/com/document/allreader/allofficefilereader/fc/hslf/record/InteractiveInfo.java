

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public class InteractiveInfo extends RecordContainer
{
    private byte[] _header;
    private static long _type = 4082;

    private InteractiveInfoAtom infoAtom;


    public InteractiveInfoAtom getInteractiveInfoAtom()
    {
        return infoAtom;
    }


    protected InteractiveInfo(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);


        _children = Record.findChildRecords(source, start + 8, len - 8);
        findInterestingChildren();
    }


    private void findInterestingChildren()
    {

        if (_children[0] instanceof InteractiveInfoAtom)
        {
            infoAtom = (InteractiveInfoAtom)_children[0];
        }
        else
        {
            throw new IllegalStateException(
                "First child record wasn't a InteractiveInfoAtom, was of type "
                    + _children[0].getRecordType());
        }
    }


    public InteractiveInfo()
    {
        _header = new byte[8];
        _children = new Record[1];


        _header[0] = 0x0f; // We are a container record
        LittleEndian.putShort(_header, 2, (short)_type);

        infoAtom = new InteractiveInfoAtom();
        _children[0] = infoAtom;
    }


    public long getRecordType()
    {
        return _type;
    }

    
    public void dispose()
    {
        _header = null;
        if (infoAtom != null)
        {
            infoAtom.dispose();
            infoAtom = null;
        }
    }
}
