package com.document.allreader.allofficefilereader.fc.p014ss.util;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.SheetReferences */

public class SheetReferences {
    Map map = new HashMap(5);

    public void addSheetReference(String str, int i) {
        this.map.put(Integer.valueOf(i), str);
    }

    public String getSheetName(int i) {
        return (String) this.map.get(Integer.valueOf(i));
    }
}
