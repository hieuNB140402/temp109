

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.hwpf.model.types.LFOAbstractType;
import com.document.allreader.allofficefilereader.fc.util.Internal;


@ Internal
public final class ListFormatOverride extends LFOAbstractType
{
    private ListFormatOverrideLevel[] _levelOverrides;

    public ListFormatOverride(byte[] buf, int offset)
    {
        fillFields(buf, offset);

        _levelOverrides = new ListFormatOverrideLevel[getClfolvl()];
    }

    public ListFormatOverride(int lsid)
    {
        setLsid(lsid);
        _levelOverrides = new ListFormatOverrideLevel[0];
    }

    public ListFormatOverrideLevel[] getLevelOverrides()
    {
        return _levelOverrides;
    }

    public ListFormatOverrideLevel getOverrideLevel(int level)
    {

        ListFormatOverrideLevel retLevel = null;

        for (int x = 0; x < _levelOverrides.length; x++)
        {
            if (_levelOverrides[x] != null && _levelOverrides[x].getLevelNum() == level)
            {
                retLevel = _levelOverrides[x];
            }
        }
        return retLevel;
    }

    public int numOverrides()
    {
        return getClfolvl();
    }

    public void setOverride(int index, ListFormatOverrideLevel lfolvl)
    {
        _levelOverrides[index] = lfolvl;
    }

    public byte[] toByteArray()
    {
        byte[] bs = new byte[getSize()];
        serialize(bs, 0);
        return bs;
    }
}
