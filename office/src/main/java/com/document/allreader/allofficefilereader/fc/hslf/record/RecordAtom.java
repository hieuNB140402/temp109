

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.LinkedList;

import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.AutoNumberTextProp;


public abstract class RecordAtom extends Record
{

    public boolean isAnAtom()
    {
        return true;
    }


    public Record[] getChildRecords()
    {
        return null;
    }
    

    public LinkedList<AutoNumberTextProp> getExtendedParagraphPropList()
    {
        return null;
    }
    
    
}
