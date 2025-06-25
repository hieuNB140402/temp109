

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.util.Internal;



@ Deprecated
@ Internal
public final class CPSplitCalculator
{
    private FileInformationBlock fib;

    public CPSplitCalculator(FileInformationBlock fib)
    {
        this.fib = fib;
    }


    public int getMainDocumentStart()
    {
        return 0;
    }


    public int getMainDocumentEnd()
    {
        return fib.getCcpText();
    }


    public int getFootnoteStart()
    {
        return getMainDocumentEnd();
    }

    public int getFootnoteEnd()
    {
        return getFootnoteStart() + fib.getCcpFtn();
    }


    public int getHeaderStoryStart()
    {
        return getFootnoteEnd();
    }


    public int getHeaderStoryEnd()
    {
        return getHeaderStoryStart() + fib.getCcpHdd();
    }



    public int getCommentsStart()
    {
        return getHeaderStoryEnd();
    }


    public int getCommentsEnd()
    {
        return getCommentsStart() + fib.getCcpCommentAtn();
    }


    public int getEndNoteStart()
    {
        return getCommentsEnd();
    }

    public int getEndNoteEnd()
    {
        return getEndNoteStart() + fib.getCcpEdn();
    }


    public int getMainTextboxStart()
    {
        return getEndNoteEnd();
    }

    public int getMainTextboxEnd()
    {
        return getMainTextboxStart() + fib.getCcpTxtBx();
    }

    public int getHeaderTextboxStart()
    {
        return getMainTextboxEnd();
    }

    public int getHeaderTextboxEnd()
    {
        return getHeaderTextboxStart() + fib.getCcpHdrTxtBx();
    }
}
