

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import java.util.Collections;

import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



@ Internal
public final class OldSectionTable extends SectionTable
{

    @ Deprecated
    @ SuppressWarnings("unused")
    public OldSectionTable(byte[] documentStream, int offset, int size, int fcMin,
        TextPieceTable tpt)
    {
        this(documentStream, offset, size);
    }

    public OldSectionTable(byte[] documentStream, int offset, int size)
    {
        PlexOfCps sedPlex = new PlexOfCps(documentStream, offset, size, 12);

        int length = sedPlex.length();

        for (int x = 0; x < length; x++)
        {
            GenericPropertyNode node = sedPlex.getProperty(x);
            SectionDescriptor sed = new SectionDescriptor(node.getBytes(), 0);

            int fileOffset = sed.getFc();
            int startAt = node.getStart();
            int endAt = node.getEnd();

            SEPX sepx;
            if (fileOffset == 0xffffffff)
            {
                sepx = new SEPX(sed, startAt, endAt, new byte[0]);
            }
            else
            {

                int sepxSize = LittleEndian.getShort(documentStream, fileOffset);

                byte[] buf = new byte[sepxSize + 2];
                fileOffset += LittleEndian.SHORT_SIZE;
                System.arraycopy(documentStream, fileOffset, buf, 0, buf.length);
                sepx = new SEPX(sed, startAt, endAt, buf);
            }

            _sections.add(sepx);
        }
        Collections.sort(_sections, PropertyNode.StartComparator.instance);
    }
}
