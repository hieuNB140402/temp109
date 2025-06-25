/*
 * 文件名称:          TimeBehaviorContainer.java
 *  
 * 编译器:            android2.2
 * 时间:              上午10:58:32
 */
package com.document.allreader.allofficefilereader.fc.hslf.record;


public class TimeBehaviorContainer extends PositionDependentRecordContainer
{
    private byte[] _header;
    public static long RECORD_ID = 0xF12A;
    

    public long getRecordType()
    {
        return RECORD_ID;
    }


    protected TimeBehaviorContainer(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);


        _children = Record.findChildRecords(source, start + 8, len - 8);
    }
}
