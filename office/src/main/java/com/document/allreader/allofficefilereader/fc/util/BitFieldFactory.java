

package com.document.allreader.allofficefilereader.fc.util;

import java.util.*;

/**
 * Returns immutable Btfield instances.
 *
 * @author Jason Height (jheight at apache dot org)
 */

public class BitFieldFactory
{
    private static Map instances = new HashMap();

    public static BitField getInstance(int mask)
    {
        BitField f = (BitField)instances.get(Integer.valueOf(mask));
        if (f == null)
        {
            f = new BitField(mask);
            instances.put(Integer.valueOf(mask), f);
        }
        return f;
    }
}
