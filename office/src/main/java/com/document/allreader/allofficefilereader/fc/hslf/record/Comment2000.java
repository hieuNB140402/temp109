

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public final class Comment2000 extends RecordContainer
{
    private byte[] _header;
    private static long _type = 12000;

    public Comment2000Atom getComment2000Atom()
    {
        return commentAtom;
    }

    public String getAuthor()
    {
        return authorRecord == null ? null : authorRecord.getText();
    }

    public void setAuthor(String author)
    {
        authorRecord.setText(author);
    }


    public String getAuthorInitials()
    {
        return authorInitialsRecord == null ? null : authorInitialsRecord.getText();
    }


    public void setAuthorInitials(String initials)
    {
        authorInitialsRecord.setText(initials);
    }

    public String getText()
    {
        return commentRecord == null ? null : commentRecord.getText();
    }

    public void setText(String text)
    {
        commentRecord.setText(text);
    }


    private void findInterestingChildren()
    {

        for (Record r : _children)
        {
            if (r instanceof CString)
            {
                CString cs = (CString)r;
                int recInstance = cs.getOptions() >> 4;
                switch (recInstance)
                {
                    case 0:
                        authorRecord = cs;
                        break;
                    case 1:
                        commentRecord = cs;
                        break;
                    case 2:
                        authorInitialsRecord = cs;
                        break;
                }
            }
            else if (r instanceof Comment2000Atom)
            {
                commentAtom = (Comment2000Atom)r;
            }

        }

    }

    public Comment2000()
    {
        _header = new byte[8];
        _children = new Record[4];

        _header[0] = 0x0f;
        LittleEndian.putShort(_header, 2, (short)_type);

        CString csa = new CString();
        CString csb = new CString();
        CString csc = new CString();
        csa.setOptions(0x00);
        csb.setOptions(0x10);
        csc.setOptions(0x20);
        _children[0] = csa;
        _children[1] = csb;
        _children[2] = csc;
        _children[3] = new Comment2000Atom();
        findInterestingChildren();
    }


    public long getRecordType()
    {
        return _type;
    }

    
    public void dispose()
    {
        _header = null;
        if (authorRecord != null)
        {
            authorRecord.dispose();
            authorRecord = null;
        }
        if (authorInitialsRecord != null)
        {
            authorInitialsRecord.dispose();
            authorInitialsRecord = null;
        }
        if (commentRecord != null)
        {
            commentRecord.dispose();
            commentRecord = null;
        }
        if (commentAtom != null)
        {
            commentAtom.dispose();
            commentAtom = null;
        }
    }
    


    private CString authorRecord;

    private CString authorInitialsRecord;

    private CString commentRecord;


    private Comment2000Atom commentAtom;

}
