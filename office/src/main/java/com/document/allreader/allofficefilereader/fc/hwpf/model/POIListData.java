

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import java.util.Arrays;

import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.BitFieldFactory;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


@ Internal
public final class POIListData
{
    private int _lsid;
    private int _tplc;
    private short[] _rgistd;
    private byte _info;
    private static BitField _fSimpleList = BitFieldFactory.getInstance(0x1);
    private static BitField _fRestartHdn = BitFieldFactory.getInstance(0x2);
    private byte _reserved;
    POIListLevel[] _levels;

    public POIListData(int listID, boolean numbered)
    {
        _lsid = listID;
        _rgistd = new short[9];

        for (int x = 0; x < 9; x++)
        {
            _rgistd[x] = StyleSheet.NIL_STYLE;
        }

        _levels = new POIListLevel[9];

        for (int x = 0; x < _levels.length; x++)
        {
            _levels[x] = new POIListLevel(x, numbered);
        }
    }

    POIListData(byte[] buf, int offset)
    {
        _lsid = LittleEndian.getInt(buf, offset);
        offset += LittleEndian.INT_SIZE;
        _tplc = LittleEndian.getInt(buf, offset);
        offset += LittleEndian.INT_SIZE;
        _rgistd = new short[9];
        for (int x = 0; x < 9; x++)
        {
            _rgistd[x] = LittleEndian.getShort(buf, offset);
            offset += LittleEndian.SHORT_SIZE;
        }
        _info = buf[offset++];
        _reserved = buf[offset];
        if (_fSimpleList.getValue(_info) > 0)
        {
            _levels = new POIListLevel[1];
        }
        else
        {
            _levels = new POIListLevel[9];
        }

    }

    public int getLsid()
    {
        return _lsid;
    }

    public int numLevels()
    {
        return _levels.length;
    }

    public void setLevel(int index, POIListLevel level)
    {
        _levels[index] = level;
    }

    public POIListLevel[] getLevels()
    {
        return _levels;
    }


    public POIListLevel getLevel(int index)
    {
        return _levels[index - 1];
    }

    public int getLevelStyle(int index)
    {
        return _rgistd[index];
    }

    public void setLevelStyle(int index, int styleIndex)
    {
        _rgistd[index] = (short)styleIndex;
    }

    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }

        POIListData lst = (POIListData)obj;
        return lst._info == _info && Arrays.equals(lst._levels, _levels) && lst._lsid == _lsid
            && lst._reserved == _reserved && lst._tplc == _tplc
            && Arrays.equals(lst._rgistd, _rgistd);
    }

    int resetListID()
    {
        _lsid = (int)(Math.random() * System.currentTimeMillis());
        return _lsid;
    }

    public byte[] toByteArray()
    {
        byte[] buf = new byte[28];
        int offset = 0;
        LittleEndian.putInt(buf, _lsid);
        offset += LittleEndian.INT_SIZE;
        LittleEndian.putInt(buf, offset, _tplc);
        offset += LittleEndian.INT_SIZE;
        for (int x = 0; x < 9; x++)
        {
            LittleEndian.putShort(buf, offset, _rgistd[x]);
            offset += LittleEndian.SHORT_SIZE;
        }
        buf[offset++] = _info;
        buf[offset] = _reserved;
        return buf;
    }
}
