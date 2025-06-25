

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.hwpf.model.io.HWPFFileSystem;
import com.document.allreader.allofficefilereader.fc.hwpf.model.io.HWPFOutputStream;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;
import com.document.allreader.allofficefilereader.fc.util.POILogFactory;
import com.document.allreader.allofficefilereader.fc.util.POILogger;



@ Internal
public class SectionTable
{
    private final static POILogger _logger = POILogFactory.getLogger(SectionTable.class);
    private static final int SED_SIZE = 12;

    protected ArrayList<SEPX> _sections = new ArrayList<SEPX>();
    protected List<TextPiece> _text;


    private TextPieceTable tpt;

    public SectionTable()
    {
    }

    public SectionTable(byte[] documentStream, byte[] tableStream, int offset, int size, int fcMin,
        TextPieceTable tpt, CPSplitCalculator cps)
    {
        PlexOfCps sedPlex = new PlexOfCps(tableStream, offset, size, SED_SIZE);
        this.tpt = tpt;
        this._text = tpt.getTextPieces();

        int length = sedPlex.length();

        for (int x = 0; x < length; x++)
        {
            GenericPropertyNode node = sedPlex.getProperty(x);
            SectionDescriptor sed = new SectionDescriptor(node.getBytes(), 0);

            int fileOffset = sed.getFc();
            int startAt = node.getStart();
            int endAt = node.getEnd();

            if (fileOffset == 0xffffffff)
            {
                _sections.add(new SEPX(sed, startAt, endAt, new byte[0]));
            }
            else
            {

                int sepxSize = LittleEndian.getShort(documentStream, fileOffset);
                byte[] buf = new byte[sepxSize];
                fileOffset += LittleEndian.SHORT_SIZE;
                System.arraycopy(documentStream, fileOffset, buf, 0, buf.length);
                _sections.add(new SEPX(sed, startAt, endAt, buf));
            }
        }


        int mainEndsAt = cps.getMainDocumentEnd();
        boolean matchAt = false;
        boolean matchHalf = false;
        for (int i = 0; i < _sections.size(); i++)
        {
            SEPX s = _sections.get(i);
            if (s.getEnd() == mainEndsAt)
            {
                matchAt = true;
            }
            else if (s.getEnd() == mainEndsAt || s.getEnd() == mainEndsAt - 1)
            {
                matchHalf = true;
            }
        }
        if (!matchAt && matchHalf)
        {
            _logger
                .log(
                    POILogger.WARN,
                    "Your document seemed to be mostly unicode, but the section definition was in bytes! Trying anyway, but things may well go wrong!");
            for (int i = 0; i < _sections.size(); i++)
            {
                SEPX s = _sections.get(i);
                GenericPropertyNode node = sedPlex.getProperty(i);

                int startAt = node.getStart();
                int endAt = node.getEnd();
                s.setStart(startAt);
                s.setEnd(endAt);
            }
        }

        Collections.sort(_sections, PropertyNode.StartComparator.instance);
    }

    public void adjustForInsert(int listIndex, int length)
    {
        int size = _sections.size();
        SEPX sepx = _sections.get(listIndex);
        sepx.setEnd(sepx.getEnd() + length);

        for (int x = listIndex + 1; x < size; x++)
        {
            sepx = _sections.get(x);
            sepx.setStart(sepx.getStart() + length);
            sepx.setEnd(sepx.getEnd() + length);
        }
    }



    public ArrayList<SEPX> getSections()
    {
        return _sections;
    }

    @ Deprecated
    public void writeTo(HWPFFileSystem sys, int fcMin) throws IOException
    {
        HWPFOutputStream docStream = sys.getStream("WordDocument");
        HWPFOutputStream tableStream = sys.getStream("1Table");

        writeTo(docStream, tableStream);
    }

    public void writeTo(HWPFOutputStream wordDocumentStream, HWPFOutputStream tableStream)
        throws IOException
    {

        int offset = wordDocumentStream.getOffset();
        int len = _sections.size();
        PlexOfCps plex = new PlexOfCps(SED_SIZE);

        for (int x = 0; x < len; x++)
        {
            SEPX sepx = _sections.get(x);
            byte[] grpprl = sepx.getGrpprl();


            byte[] shortBuf = new byte[2];
            LittleEndian.putShort(shortBuf, (short)grpprl.length);

            wordDocumentStream.write(shortBuf);
            wordDocumentStream.write(grpprl);

            SectionDescriptor sed = sepx.getSectionDescriptor();
            sed.setFc(offset);

            GenericPropertyNode property = new GenericPropertyNode(sepx.getStart(), sepx.getEnd(),
                sed.toByteArray());

            plex.addProperty(property);

            offset = wordDocumentStream.getOffset();
        }
        tableStream.write(plex.toByteArray());
    }
}
