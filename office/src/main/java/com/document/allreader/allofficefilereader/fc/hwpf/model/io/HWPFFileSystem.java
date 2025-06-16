

package com.document.allreader.allofficefilereader.fc.hwpf.model.io;

import java.util.HashMap;
import java.util.Map;

import com.document.allreader.allofficefilereader.fc.util.Internal;


@ Internal
public final class HWPFFileSystem
{
    Map<String, HWPFOutputStream> _streams = new HashMap<String, HWPFOutputStream>();

    public HWPFFileSystem()
    {
        _streams.put("WordDocument", new HWPFOutputStream());
        _streams.put("1Table", new HWPFOutputStream());
        _streams.put("Data", new HWPFOutputStream());
    }

    public HWPFOutputStream getStream(String name)
    {
        return _streams.get(name);
    }

}
