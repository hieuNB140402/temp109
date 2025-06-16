

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.hwpf.model.io.HWPFOutputStream;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;
import com.document.allreader.allofficefilereader.fc.util.StringUtil;


@ Internal
public final class RevisionMarkAuthorTable
{

    private short fExtend = (short)0xFFFF;

    private short cData = 0;


    private short cbExtra = 0;


    private String[] entries;

    public RevisionMarkAuthorTable(byte[] tableStream, int offset, int size) throws IOException
    {

        fExtend = LittleEndian.getShort(tableStream, offset);
        if (fExtend != 0xFFFF)
        {

        }
        offset += 2;


        cData = LittleEndian.getShort(tableStream, offset);
        offset += 2;

        cbExtra = LittleEndian.getShort(tableStream, offset);
        if (cbExtra != 0)
        {

        }
        offset += 2;

        entries = new String[cData];
        for (int i = 0; i < cData; i++)
        {
            int len = LittleEndian.getShort(tableStream, offset);
            offset += 2;

            String name = StringUtil.getFromUnicodeLE(tableStream, offset, len);
            offset += len * 2;

            entries[i] = name;
        }
    }

    public List<String> getEntries()
    {
        return Collections.unmodifiableList(Arrays.asList(entries));
    }


    public String getAuthor(int index)
    {
        String auth = null;
        if (index >= 0 && index < entries.length)
        {
            auth = entries[index];
        }
        return auth;
    }


    public int getSize()
    {
        return cData;
    }


    public void writeTo(HWPFOutputStream tableStream) throws IOException
    {
        byte[] header = new byte[6];
        LittleEndian.putShort(header, 0, fExtend);
        LittleEndian.putShort(header, 2, cData);
        LittleEndian.putShort(header, 4, cbExtra);
        tableStream.write(header);

        for (String name : entries)
        {
            byte[] buf = new byte[name.length() * 2 + 2];
            LittleEndian.putShort(buf, 0, (short)name.length());
            StringUtil.putUnicodeLE(name, buf, 2);
            tableStream.write(buf);
        }
    }

}
