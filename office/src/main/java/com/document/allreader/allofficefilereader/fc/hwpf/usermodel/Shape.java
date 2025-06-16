

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import com.document.allreader.allofficefilereader.fc.hwpf.model.GenericPropertyNode;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



@ Deprecated
public final class Shape
{
    int _id, _left, _right, _top, _bottom;

    boolean _inDoc;

    public Shape(GenericPropertyNode nodo)
    {
        byte[] contenuto = nodo.getBytes();
        _id = LittleEndian.getInt(contenuto);
        _left = LittleEndian.getInt(contenuto, 4);
        _top = LittleEndian.getInt(contenuto, 8);
        _right = LittleEndian.getInt(contenuto, 12);
        _bottom = LittleEndian.getInt(contenuto, 16);
        _inDoc = (_left >= 0 && _right >= 0 && _top >= 0 && _bottom >= 0);
    }

    public int getId()
    {
        return _id;
    }

    public int getLeft()
    {
        return _left;
    }

    public int getRight()
    {
        return _right;
    }

    public int getTop()
    {
        return _top;
    }

    public int getBottom()
    {
        return _bottom;
    }

    public int getWidth()
    {
        return _right - _left + 1;
    }

    public int getHeight()
    {
        return _bottom - _top + 1;
    }

    public boolean isWithinDocument()
    {
        return _inDoc;
    }
}
