

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import java.util.ArrayList;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class PlexOfCps
{
    private int _iMac;
    private int _offset;
    private int _cbStruct;
    private ArrayList<GenericPropertyNode> _props;

    public PlexOfCps(int sizeOfStruct)
    {
        _props = new ArrayList<GenericPropertyNode>();
        _cbStruct = sizeOfStruct;
    }


    public PlexOfCps(byte[] buf, int start, int cb, int cbStruct)
    {
        _iMac = (cb - 4) / (4 + cbStruct);

        _cbStruct = cbStruct;
        _props = new ArrayList<GenericPropertyNode>(_iMac);

        for (int x = 0; x < _iMac; x++)
        {
            _props.add(getProperty(x, buf, start));
        }
    }

    public GenericPropertyNode getProperty(int index)
    {
        return _props.get(index);
    }

    public void addProperty(GenericPropertyNode node)
    {
        _props.add(node);
    }

    public byte[] toByteArray()
    {
        int size = _props.size();
        int cpBufSize = ((size + 1) * LittleEndian.INT_SIZE);
        int structBufSize = +(_cbStruct * size);
        int bufSize = cpBufSize + structBufSize;

        byte[] buf = new byte[bufSize];

        GenericPropertyNode node = null;
        for (int x = 0; x < size; x++)
        {
            node = _props.get(x);

            LittleEndian.putInt(buf, (LittleEndian.INT_SIZE * x), node.getStart());

            System.arraycopy(node.getBytes(), 0, buf, cpBufSize + (x * _cbStruct), _cbStruct);
        }
        LittleEndian.putInt(buf, LittleEndian.INT_SIZE * size, node.getEnd());

        return buf;

    }

    private GenericPropertyNode getProperty(int index, byte[] buf, int offset)
    {
        int start = LittleEndian.getInt(buf, offset + index * 4);
        int end = LittleEndian.getInt(buf, offset + (index + 1) * 4);

        byte[] struct = new byte[_cbStruct];
        System.arraycopy(buf, offset + getStructOffset(index), struct, 0, _cbStruct);

        return new GenericPropertyNode(start, end, struct);
    }


    public int length()
    {
        return _iMac;
    }


    private int getStructOffset(int index)
    {
        return (4 * (_iMac + 1)) + (_cbStruct * index);
    }

    GenericPropertyNode[] toPropertiesArray()
    {
        if (_props == null || _props.isEmpty())
            return new GenericPropertyNode[0];

        return _props.toArray(new GenericPropertyNode[_props.size()]);
    }

    @ Override
    public String toString()
    {
        return "PLCF (cbStruct: " + _cbStruct + "; iMac: " + _iMac + ")";
    }
}
