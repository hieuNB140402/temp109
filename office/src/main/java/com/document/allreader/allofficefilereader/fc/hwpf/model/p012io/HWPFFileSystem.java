package com.document.allreader.allofficefilereader.fc.hwpf.model.p012io;

import com.document.allreader.allofficefilereader.fc.util.Internal;
import java.util.HashMap;
import java.util.Map;

@Internal
/* renamed from: com.allreader.office.allofficefilereader.fc.hwpf.model.io.HWPFFileSystem */

public final class HWPFFileSystem {
    Map<String, HWPFOutputStream> _streams;

    public HWPFFileSystem() {
        HashMap hashMap = new HashMap();
        this._streams = hashMap;
        hashMap.put("WordDocument", new HWPFOutputStream());
        this._streams.put("1Table", new HWPFOutputStream());
        this._streams.put("Data", new HWPFOutputStream());
    }

    public HWPFOutputStream getStream(String str) {
        return this._streams.get(str);
    }
}
