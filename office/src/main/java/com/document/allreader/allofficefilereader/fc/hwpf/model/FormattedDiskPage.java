

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


@ Internal
public abstract class FormattedDiskPage
{
    protected byte[] _fkp;
    protected int _crun;
    protected int _offset;

    public FormattedDiskPage()
    {

    }

    public FormattedDiskPage(byte[] documentStream, int offset)
    {
        _crun = LittleEndian.getUnsignedByte(documentStream, offset + 511);
        _fkp = documentStream;
        _offset = offset;
    }


    protected int getStart(int index)
    {
        return LittleEndian.getInt(_fkp, _offset + (index * 4));
    }


    protected int getEnd(int index)
    {
        return LittleEndian.getInt(_fkp, _offset + ((index + 1) * 4));
    }


    public int size()
    {
        return _crun;
    }

    protected abstract byte[] getGrpprl(int index);
}
