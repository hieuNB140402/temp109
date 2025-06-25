
package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;


public final class List extends PositionDependentRecordContainer
{   

    protected List(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);
        

        _children = Record.findChildRecords(source, start + 8, len - 8);
        findExtendedPreRuleRecord(_children);
    }
    

    private void findExtendedPreRuleRecord(Record[] toSearch)
    {
        for (int i = 0; i < toSearch.length; i++)
        {
            if (toSearch[i] instanceof ExtendedPresRuleContainer)
            {
                _extendedPresRuleContainer = (ExtendedPresRuleContainer)toSearch[i];
            }
            else
            {
                if (!toSearch[i].isAnAtom())
                {
                    findExtendedPreRuleRecord(toSearch[i].getChildRecords());
                }
            }
        }
    }
    

    public ExtendedPresRuleContainer getExtendedPresRuleContainer()
    {
        return _extendedPresRuleContainer;
    }

    
    public long getRecordType()
    {
        return RecordTypes.List.typeID;
    }

    
    public void writeOut(OutputStream o) throws IOException
    {
        
    }
    

    public void dispose()
    {
        _header = null;
        if (_extendedPresRuleContainer != null)
        {
            _extendedPresRuleContainer.dispose();
            _extendedPresRuleContainer = null;
        }
    }


    private byte[] _header;

    private ExtendedPresRuleContainer _extendedPresRuleContainer;;
}
