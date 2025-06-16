

package com.document.allreader.allofficefilereader.fc.hslf.model;


import java.util.HashMap;

import com.document.allreader.allofficefilereader.fc.hslf.exceptions.HSLFException;

import java.lang.reflect.Field;


public final class ShapeTypes implements com.document.allreader.allofficefilereader.common.shape.ShapeTypes
{

    public static String typeName(int type)
    {
        String name = (String)types.get(Integer.valueOf(type));
        return name;
    }

    public static HashMap types;
    static
    {
        types = new HashMap();
        try
        {
            Field[] f = com.document.allreader.allofficefilereader.common.shape.ShapeTypes.class.getFields();
            for (int i = 0; i < f.length; i++)
            {
                Object val = f[i].get(null);
                if (val instanceof Integer)
                {
                    types.put(val, f[i].getName());
                }
            }
        }
        catch(IllegalAccessException e)
        {
            throw new HSLFException("Failed to initialize shape types");
        }
    }

}
