

package com.document.allreader.allofficefilereader.fc.hslf.usermodel;

import java.util.ArrayList;

import com.document.allreader.allofficefilereader.fc.hslf.record.Document;
import com.document.allreader.allofficefilereader.fc.hslf.record.Record;
import com.document.allreader.allofficefilereader.fc.hslf.record.RecordContainer;
import com.document.allreader.allofficefilereader.fc.hslf.record.RecordTypes;
import com.document.allreader.allofficefilereader.fc.hslf.record.Sound;


public final class SoundData
{

    private Sound _container;


    public SoundData(Sound container)
    {
        this._container = container;
    }


    public String getSoundName()
    {
        return _container.getSoundName();
    }


    public String getSoundType()
    {
        return _container.getSoundType();
    }


    public byte[] getData()
    {
        return _container.getSoundData();
    }


    public static SoundData[] find(Document document)
    {
        ArrayList<SoundData> lst = new ArrayList<SoundData>();
        Record[] ch = document.getChildRecords();
        for (int i = 0; i < ch.length; i++)
        {
            if (ch[i].getRecordType() == RecordTypes.SoundCollection.typeID)
            {
                RecordContainer col = (RecordContainer)ch[i];
                Record[] sr = col.getChildRecords();
                for (int j = 0; j < sr.length; j++)
                {
                    if (sr[j] instanceof Sound)
                    {
                        lst.add(new SoundData((Sound)sr[j]));
                    }
                }
            }

        }
        return lst.toArray(new SoundData[lst.size()]);
    }
}
