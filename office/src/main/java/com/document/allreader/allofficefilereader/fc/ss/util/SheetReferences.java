

package com.document.allreader.allofficefilereader.fc.ss.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds a collection of Sheet names and their associated
 * reference numbers.
 *
 * @author Andrew C. Oliver (acoliver at apache dot org)
 *
 */
public class SheetReferences
{
    Map map;
    public SheetReferences()
    {
      map = new HashMap(5);
    }

    public void addSheetReference(String sheetName, int number) {
       map.put(Integer.valueOf(number), sheetName);
    }

    public String getSheetName(int number) {
       return (String)map.get(Integer.valueOf(number));
    }
}
