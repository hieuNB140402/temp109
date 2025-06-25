/*
 * 文件名称:          TimeAnimateBehaviorContainer.java
 *  
 * 编译器:            android2.2
 * 时间:              上午10:56:19
 */
package com.document.allreader.allofficefilereader.fc.hslf.record;


public class TimeAnimateBehaviorContainer extends PositionDependentRecordContainer
{
    private byte[] _header;
    public static long RECORD_ID = 0xF12B;
    

    public long getRecordType()
    {
        return RECORD_ID;
    }
    

    protected TimeAnimateBehaviorContainer(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);


        _children = Record.findChildRecords(source, start + 8, len - 8);
    }

}
