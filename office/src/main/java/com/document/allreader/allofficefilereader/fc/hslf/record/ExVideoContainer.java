

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public final class ExVideoContainer extends RecordContainer
{
    private byte[] _header;


    private ExMediaAtom mediaAtom;

    private CString pathAtom;



    public ExVideoContainer()
    {

        _header = new byte[8];
        _header[0] = 0x0f;
        LittleEndian.putShort(_header, 2, (short)getRecordType());

        _children = new Record[2];
        _children[0] = mediaAtom = new ExMediaAtom();
        _children[1] = pathAtom = new CString();
    }

    public long getRecordType()
    {
        return RecordTypes.ExVideoContainer.typeID;
    }


    public ExMediaAtom getExMediaAtom()
    {
        return mediaAtom;
    }


    public CString getPathAtom()
    {
        return pathAtom;
    }
    

    public void dispose()
    {
        super.dispose();
        _header = null;
        if (pathAtom != null)
        {
            pathAtom.dispose();
            pathAtom = null;
        }
        if (mediaAtom != null)
        {
            mediaAtom.dispose();
            mediaAtom = null;
        }
    }

}
