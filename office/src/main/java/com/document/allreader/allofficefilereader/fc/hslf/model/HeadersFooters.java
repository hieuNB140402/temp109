

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.fc.hslf.record.CString;
import com.document.allreader.allofficefilereader.fc.hslf.record.Document;
import com.document.allreader.allofficefilereader.fc.hslf.record.HeadersFootersAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.HeadersFootersContainer;
import com.document.allreader.allofficefilereader.fc.hslf.record.OEPlaceholderAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.Record;
import com.document.allreader.allofficefilereader.fc.hslf.record.RecordTypes;
import com.document.allreader.allofficefilereader.fc.hslf.usermodel.SlideShow;

public final class HeadersFooters
{

    private HeadersFootersContainer _container;
    private boolean _newRecord;
    private SlideShow _ppt;
    private Sheet _sheet;
    private boolean _ppt2007;

    public HeadersFooters(HeadersFootersContainer rec, SlideShow ppt, boolean newRecord,
        boolean isPpt2007)
    {
        _container = rec;
        _newRecord = newRecord;
        _ppt = ppt;
        _ppt2007 = false;
    }

    public HeadersFooters(HeadersFootersContainer rec, Sheet sheet, boolean newRecord,
        boolean isPpt2007)
    {
        _container = rec;
        _newRecord = newRecord;
        _sheet = sheet;
        _ppt2007 = false;
    }

    /**
     * Headers's text
     *
     * @return Headers's text
     */
    public String getHeaderText()
    {
        CString cs = _container == null ? null : _container.getHeaderAtom();
        return getPlaceholderText(OEPlaceholderAtom.MasterHeader, cs);
    }

    /**
     * Sets headers's text
     *
     * @param text headers's text
     */
    public void setHeaderText(String text)
    {
        if (_newRecord)
            attach();

        setHeaderVisible(true);
        CString cs = _container.getHeaderAtom();
        if (cs == null)
            cs = _container.addHeaderAtom();

        cs.setText(text);
    }

    public String getFooterText()
    {
        CString cs = _container == null ? null : _container.getFooterAtom();
        return getPlaceholderText(OEPlaceholderAtom.MasterFooter, cs);
    }


    public void setFootersText(String text)
    {
        if (_newRecord)
            attach();

        setFooterVisible(true);
        CString cs = _container.getFooterAtom();
        if (cs == null)
            cs = _container.addFooterAtom();

        cs.setText(text);
    }


    public String getDateTimeText()
    {
        CString cs = _container == null ? null : _container.getUserDateAtom();
        return getPlaceholderText(OEPlaceholderAtom.MasterDate, cs);
    }

    public void setDateTimeText(String text)
    {
        if (_newRecord)
            attach();

        setUserDateVisible(true);
        setDateTimeVisible(true);
        CString cs = _container.getUserDateAtom();
        if (cs == null)
            cs = _container.addUserDateAtom();

        cs.setText(text);
    }

    public boolean isFooterVisible()
    {
        return isVisible(HeadersFootersAtom.fHasFooter, OEPlaceholderAtom.MasterFooter);
    }


    public void setFooterVisible(boolean flag)
    {
        if (_newRecord)
            attach();
        _container.getHeadersFootersAtom().setFlag(HeadersFootersAtom.fHasFooter, flag);
    }


    public boolean isHeaderVisible()
    {
        return isVisible(HeadersFootersAtom.fHasHeader, OEPlaceholderAtom.MasterHeader);
    }


    public void setHeaderVisible(boolean flag)
    {
        if (_newRecord)
            attach();
        _container.getHeadersFootersAtom().setFlag(HeadersFootersAtom.fHasHeader, flag);
    }

    public boolean isDateTimeVisible()
    {
        return isVisible(HeadersFootersAtom.fHasDate, OEPlaceholderAtom.MasterDate);
    }


    public void setDateTimeVisible(boolean flag)
    {
        if (_newRecord)
            attach();
        _container.getHeadersFootersAtom().setFlag(HeadersFootersAtom.fHasDate, flag);
    }


    public boolean isUserDateVisible()
    {
        return isVisible(HeadersFootersAtom.fHasUserDate, OEPlaceholderAtom.MasterDate);
    }

    /**
     * whether the date is displayed in the footer.
     */
    public void setUserDateVisible(boolean flag)
    {
        if (_newRecord)
            attach();
        _container.getHeadersFootersAtom().setFlag(HeadersFootersAtom.fHasUserDate, flag);
    }


    public boolean isSlideNumberVisible()
    {
        return isVisible(HeadersFootersAtom.fHasSlideNumber, OEPlaceholderAtom.MasterSlideNumber);
    }


    public void setSlideNumberVisible(boolean flag)
    {
        if (_newRecord)
            attach();
        _container.getHeadersFootersAtom().setFlag(HeadersFootersAtom.fHasSlideNumber, flag);
    }


    public int getDateTimeFormat()
    {
        return _container.getHeadersFootersAtom().getFormatId();
    }


    public void setDateTimeFormat(int formatId)
    {
        if (_newRecord)
            attach();
        _container.getHeadersFootersAtom().setFormatId(formatId);
    }

    private void attach()
    {
        Document doc = _ppt.getDocumentRecord();
        Record[] ch = doc.getChildRecords();
        Record lst = null;
        for (int i = 0; i < ch.length; i++)
        {
            if (ch[i].getRecordType() == RecordTypes.List.typeID)
            {
                lst = ch[i];
                break;
            }
        }
        doc.addChildAfter(_container, lst);
        _newRecord = false;
    }

    private boolean isVisible(int flag, int placeholderId)
    {
        boolean visible;
        if (_ppt2007)
        {
            Sheet master = _sheet != null ? _sheet : _ppt.getSlidesMasters()[0];
            TextShape placeholder = master.getPlaceholder(placeholderId);
            visible = placeholder != null && placeholder.getText() != null;
        }
        else
        {
            visible = _container.getHeadersFootersAtom().getFlag(flag);
        }
        return visible;
    }

    private String getPlaceholderText(int placeholderId, CString cs)
    {
        String text = null;
        if (_ppt2007)
        {
            Sheet master = _sheet != null ? _sheet : _ppt.getSlidesMasters()[0];
            TextShape placeholder = master.getPlaceholder(placeholderId);
            if (placeholder != null)
                text = placeholder.getText();

            //default text in master placeholders is not visible
            if ("*".equals(text))
                text = null;
        }
        else
        {
            text = cs == null ? null : cs.getText();
        }
        return text;
    }

}
