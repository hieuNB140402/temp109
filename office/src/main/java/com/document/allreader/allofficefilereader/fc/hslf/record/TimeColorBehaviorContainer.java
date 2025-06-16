
package com.document.allreader.allofficefilereader.fc.hslf.record;


public class TimeColorBehaviorContainer extends PositionDependentRecordContainer
{
    private byte[] _header;
    public static long RECORD_ID = 0xF12C;
    
    private TimeColorBehaviorAtom colorBehaviorAtom;
    

    public long getRecordType()
    {
        return RECORD_ID;
    }
    

    protected TimeColorBehaviorContainer(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        colorBehaviorAtom = new TimeColorBehaviorAtom(source, start + 8, 60);

        _children = Record.findChildRecords(source, start + 8, len - 8);
    }

}
