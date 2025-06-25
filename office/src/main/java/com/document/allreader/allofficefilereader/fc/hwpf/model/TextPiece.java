

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import java.io.UnsupportedEncodingException;

import com.document.allreader.allofficefilereader.fc.util.Internal;



@ Internal
public class TextPiece extends PropertyNode<TextPiece>
{
    private boolean _usesUnicode;

    private PieceDescriptor _pd;


    public TextPiece(int start, int end, byte[] text, PieceDescriptor pd, int cpStart)
    {
        this(start, end, text, 0, text.length, pd);
    }


    public TextPiece(int start, int end, byte[] text, int offset, int len, PieceDescriptor pd)
    {
        super(start, end, buildInitSB(text, offset, len, pd));
        _usesUnicode = pd.isUnicode();
        _pd = pd;


        int textLength = ((CharSequence)_buf).length();
        if (end - start != textLength)
        {
            throw new IllegalStateException("Told we're for characters " + start + " -> " + end
                + ", but actually covers " + textLength + " characters!");
        }
        if (end < start)
        {
            throw new IllegalStateException("Told we're of negative size! start=" + start + " end="
                + end);
        }
    }

    private  static StringBuilder buildInitSB(byte[] text, int offset, int len, PieceDescriptor pd)
    {
        String str;
        try
        {
            if (pd.isUnicode())
            {
                str = new String(text, offset, len, "UTF-16LE");
            }
            else
            {
                str = new String(text, offset, len, "Cp1252");
            }
        }
        catch(UnsupportedEncodingException e)
        {
            throw new RuntimeException(
                "Your Java is broken! It doesn't know about basic, required character encodings!");
        }
        return new StringBuilder(str);
    }


    public boolean isUnicode()
    {
        return _usesUnicode;
    }

    public PieceDescriptor getPieceDescriptor()
    {
        return _pd;
    }

    @ Deprecated
    public StringBuffer getStringBuffer()
    {
        return new StringBuffer(getStringBuilder());
    }

    public StringBuilder getStringBuilder()
    {
        return (StringBuilder)_buf;
    }

    public byte[] getRawBytes()
    {
        try
        {
            return ((CharSequence)_buf).toString().getBytes(_usesUnicode ? "UTF-16LE" : "Cp1252");
        }
        catch(UnsupportedEncodingException ignore)
        {
            throw new RuntimeException(
                "Your Java is broken! It doesn't know about basic, required character encodings!");
        }
    }


    @ Deprecated
    public String substring(int start, int end)
    {
        StringBuilder buf = (StringBuilder)_buf;

        if (start < 0)
        {
            throw new StringIndexOutOfBoundsException(
                "Can't request a substring before 0 - asked for " + start);
        }
        if (end > buf.length())
        {
            throw new StringIndexOutOfBoundsException("Index " + end + " out of range 0 -> "
                + buf.length());
        }
        if (end < start)
        {
            throw new StringIndexOutOfBoundsException("Asked for text from " + start + " to " + end
                + ", which has an end before the start!");
        }
        return buf.substring(start, end);
    }

    @ Deprecated
    public void adjustForDelete(int start, int length)
    {
        int numChars = length;

        int myStart = getStart();
        int myEnd = getEnd();
        int end = start + numChars;

        if (start <= myEnd && end >= myStart)
        {

            int overlapStart = Math.max(myStart, start);
            int overlapEnd = Math.min(myEnd, end);

            int bufStart = overlapStart - myStart;
            int bufEnd = overlapEnd - myStart;
            ((StringBuilder)_buf).delete(bufStart, bufEnd);
        }

        super.adjustForDelete(start, length);
    }

    @ Deprecated
    public int characterLength()
    {
        return (getEnd() - getStart());
    }


    public int bytesLength()
    {
        return (getEnd() - getStart()) * (_usesUnicode ? 2 : 1);
    }

    public boolean equals(Object o)
    {
        if (limitsAreEqual(o))
        {
            TextPiece tp = (TextPiece)o;
            return getStringBuilder().toString().equals(tp.getStringBuilder().toString())
                && tp._usesUnicode == _usesUnicode && _pd.equals(tp._pd);
        }
        return false;
    }

    public int getCP()
    {
        return getStart();
    }

    public String toString()
    {
        return "TextPiece from " + getStart() + " to " + getEnd() + " (" + getPieceDescriptor()
            + ")";
    }
}
