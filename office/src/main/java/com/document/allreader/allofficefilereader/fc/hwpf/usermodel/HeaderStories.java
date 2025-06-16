

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import com.document.allreader.allofficefilereader.fc.hwpf.HWPFDocument;
import com.document.allreader.allofficefilereader.fc.hwpf.model.FileInformationBlock;
import com.document.allreader.allofficefilereader.fc.hwpf.model.GenericPropertyNode;
import com.document.allreader.allofficefilereader.fc.hwpf.model.PlexOfCps;
import com.document.allreader.allofficefilereader.fc.hwpf.model.SubdocumentType;



public final class HeaderStories
{
    private Range headerStories;
    private PlexOfCps plcfHdd;

    private boolean stripFields = false;

    public HeaderStories(HWPFDocument doc)
    {
        this.headerStories = doc.getHeaderStoryRange();
        FileInformationBlock fib = doc.getFileInformationBlock();

        if (fib.getSubdocumentTextStreamLength(SubdocumentType.HEADER) == 0)
            return;

        if (fib.getPlcfHddSize() == 0)
        {
            return;
        }


        plcfHdd = new PlexOfCps(doc.getTableStream(), fib.getPlcfHddOffset(), fib.getPlcfHddSize(),
            0);
    }

    @ Deprecated
    public String getFootnoteSeparator()
    {
        return getAt(0);
    }

    @ Deprecated
    public String getFootnoteContSeparator()
    {
        return getAt(1);
    }

    @ Deprecated
    public String getFootnoteContNote()
    {
        return getAt(2);
    }

    @ Deprecated
    public String getEndnoteSeparator()
    {
        return getAt(3);
    }

    @ Deprecated
    public String getEndnoteContSeparator()
    {
        return getAt(4);
    }

    @ Deprecated
    public String getEndnoteContNote()
    {
        return getAt(5);
    }

    public Range getFootnoteSeparatorSubrange()
    {
        return getSubrangeAt(0);
    }

    public Range getFootnoteContSeparatorSubrange()
    {
        return getSubrangeAt(1);
    }

    public Range getFootnoteContNoteSubrange()
    {
        return getSubrangeAt(2);
    }

    public Range getEndnoteSeparatorSubrange()
    {
        return getSubrangeAt(3);
    }

    public Range getEndnoteContSeparatorSubrange()
    {
        return getSubrangeAt(4);
    }

    public Range getEndnoteContNoteSubrange()
    {
        return getSubrangeAt(5);
    }

    @ Deprecated
    public String getEvenHeader()
    {
        return getAt(6 + 0);
    }

    @ Deprecated
    public String getOddHeader()
    {
        return getAt(6 + 1);
    }

    @ Deprecated
    public String getFirstHeader()
    {
        return getAt(6 + 4);
    }

    public Range getEvenHeaderSubrange()
    {
        return getSubrangeAt(6 + 0);
    }

    public Range getOddHeaderSubrange()
    {
        return getSubrangeAt(6 + 1);
    }

    public Range getFirstHeaderSubrange()
    {
        return getSubrangeAt(6 + 4);
    }


    public String getHeader(int pageNumber)
    {

        if (pageNumber == 1)
        {
            if (getFirstHeader().length() > 0)
            {
                return getFirstHeader();
            }
        }

        if (pageNumber % 2 == 0)
        {
            if (getEvenHeader().length() > 0)
            {
                return getEvenHeader();
            }
        }

        return getOddHeader();
    }

    @ Deprecated
    public String getEvenFooter()
    {
        return getAt(6 + 2);
    }

    @ Deprecated
    public String getOddFooter()
    {
        return getAt(6 + 3);
    }

    @ Deprecated
    public String getFirstFooter()
    {
        return getAt(6 + 5);
    }

    public Range getEvenFooterSubrange()
    {
        return getSubrangeAt(6 + 2);
    }

    public Range getOddFooterSubrange()
    {
        return getSubrangeAt(6 + 3);
    }

    public Range getFirstFooterSubrange()
    {
        return getSubrangeAt(6 + 5);
    }


    public String getFooter(int pageNumber)
    {

        if (pageNumber == 1)
        {
            if (getFirstFooter().length() > 0)
            {
                return getFirstFooter();
            }
        }

        if (pageNumber % 2 == 0)
        {
            if (getEvenFooter().length() > 0)
            {
                return getEvenFooter();
            }
        }


        return getOddFooter();
    }



    @ Deprecated
    private String getAt(int plcfHddIndex)
    {
        if (plcfHdd == null)
            return null;

        GenericPropertyNode prop = plcfHdd.getProperty(plcfHddIndex);
        if (prop.getStart() == prop.getEnd())
        {


            return "";
        }
        if (prop.getEnd() < prop.getStart())
        {


            return "";
        }



        String rawText = headerStories.text();
        int start = Math.min(prop.getStart(), rawText.length());
        int end = Math.min(prop.getEnd(), rawText.length());


        String text = rawText.substring(start, end);

        if (stripFields)
        {
            return Range.stripFields(text);
        }

        if (text.equals("\r\r"))
        {
            return "";
        }

        return text;
    }

    private Range getSubrangeAt(int plcfHddIndex)
    {
        if (plcfHdd == null)
            return null;

        GenericPropertyNode prop = plcfHdd.getProperty(plcfHddIndex);
        if (prop.getStart() == prop.getEnd())
        {
            // Empty story
            return null;
        }
        if (prop.getEnd() < prop.getStart())
        {
            // Broken properties?
            return null;
        }

        final int headersLength = (int)(headerStories.getEndOffset() - headerStories.getStartOffset());
        int start = Math.min(prop.getStart(), headersLength);
        int end = Math.min(prop.getEnd(), headersLength);

        return new Range((int)headerStories.getStartOffset() + start, (int)headerStories.getStartOffset()
            + end, headerStories);
    }

    public Range getRange()
    {
        return headerStories;
    }

    protected PlexOfCps getPlcfHdd()
    {
        return plcfHdd;
    }


    public boolean areFieldsStripped()
    {
        return stripFields;
    }


    public void setAreFieldsStripped(boolean stripFields)
    {
        this.stripFields = stripFields;
    }
}
