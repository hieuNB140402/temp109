

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.document.allreader.allofficefilereader.fc.poifs.filesystem.DirectoryEntry;
import com.document.allreader.allofficefilereader.fc.poifs.filesystem.Entry;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.POIUtils;


@ Internal
public class ObjectPoolImpl implements ObjectsPool
{
    private DirectoryEntry _objectPool;

    public ObjectPoolImpl(DirectoryEntry _objectPool)
    {
        super();
        this._objectPool = _objectPool;
    }

    public Entry getObjectById(String objId)
    {
        if (_objectPool == null)
            return null;

        try
        {
            return _objectPool.getEntry(objId);
        }
        catch(FileNotFoundException exc)
        {
            return null;
        }
    }

    @ Internal
    public void writeTo(DirectoryEntry directoryEntry) throws IOException
    {
        if (_objectPool != null)
            POIUtils.copyNodeRecursively(_objectPool, directoryEntry);
    }
}
