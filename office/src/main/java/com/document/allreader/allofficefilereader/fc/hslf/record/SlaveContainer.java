
package com.document.allreader.allofficefilereader.fc.hslf.record;


public class SlaveContainer extends PositionDependentRecordContainer
{
    private byte[] _header;
    private static long _type = 0xF145;
    

    public long getRecordType()
    {
        return _type;
    }
    

    protected SlaveContainer(byte[] source, int start, int len)
    {
        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        _children = Record.findChildRecords(source, start + 8, len - 8);
    }
}

