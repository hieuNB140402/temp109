

package com.document.allreader.allofficefilereader.fc.hslf.usermodel;

import java.io.InputStream;

import com.document.allreader.allofficefilereader.fc.hslf.record.ExOleObjStg;

import java.io.IOException;



public class ObjectData
{

    private ExOleObjStg storage;

    public ObjectData(ExOleObjStg storage)
    {
        this.storage = storage;
    }


    public InputStream getData()
    {
        return storage.getData();
    }

    public void setData(byte[] data) throws IOException
    {
        storage.setData(data);
    }


    public ExOleObjStg getExOleObjStg()
    {
        return storage;
    }
    

    public void dispose()
    {
        if (storage != null)
        {
            storage.dispose();
            storage = null;
        }
    }
}
