

package com.document.allreader.allofficefilereader.fc.hslf.model;

import java.util.LinkedList;
import java.util.Vector;

import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.AutoNumberTextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.TextPropCollection;
import com.document.allreader.allofficefilereader.fc.hslf.record.*;
import com.document.allreader.allofficefilereader.fc.hslf.record.Record;
import com.document.allreader.allofficefilereader.fc.hslf.usermodel.RichTextRun;
import com.document.allreader.allofficefilereader.fc.hslf.usermodel.SlideShow;
import com.document.allreader.allofficefilereader.fc.util.StringUtil;



public final class TextRun
{

    protected Record[] _records;


    public TextRun(TextHeaderAtom tha, TextCharsAtom tca, StyleTextPropAtom sta)
    {
        this(tha, null, tca, sta);
    }


    public TextRun(TextHeaderAtom tha, TextBytesAtom tba, StyleTextPropAtom sta)
    {
        this(tha, tba, null, sta);
    }

    private TextRun(TextHeaderAtom tha, TextBytesAtom tba, TextCharsAtom tca, StyleTextPropAtom sta)
    {
        _headerAtom = tha;
        _styleAtom = sta;
        if (tba != null)
        {
            _byteAtom = tba;
            _isUnicode = false;
        }
        else
        {
            _charAtom = tca;
            _isUnicode = true;
        }
        String runRawText = getText();

        LinkedList pStyles = new LinkedList();
        LinkedList cStyles = new LinkedList();
        if (_styleAtom != null)
        {
            _styleAtom.setParentTextSize(runRawText.length());
            pStyles = _styleAtom.getParagraphStyles();
            cStyles = _styleAtom.getCharacterStyles();
        }
        buildRichTextRuns(pStyles, cStyles, runRawText);
    }

    public void buildRichTextRuns(LinkedList pStyles, LinkedList cStyles, String runRawText)
    {


        if (pStyles.size() == 0 || cStyles.size() == 0)
        {
            _rtRuns = new RichTextRun[1];
            _rtRuns[0] = new RichTextRun(this, 0, runRawText.length());
        }
        else
        {
            Vector rtrs = new Vector();

            int pos = 0;

            int curP = 0;
            int curC = 0;
            int pLenRemain = -1;
            int cLenRemain = -1;

            while (pos <= runRawText.length() && curP < pStyles.size() && curC < cStyles.size())
            {
                TextPropCollection pProps = (TextPropCollection)pStyles.get(curP);
                TextPropCollection cProps = (TextPropCollection)cStyles.get(curC);

                int pLen = pProps.getCharactersCovered();
                int cLen = cProps.getCharactersCovered();

                boolean freshSet = false;
                if (pLenRemain == -1 && cLenRemain == -1)
                {
                    freshSet = true;
                }
                if (pLenRemain == -1)
                {
                    pLenRemain = pLen;
                }
                if (cLenRemain == -1)
                {
                    cLenRemain = cLen;
                }

                int runLen = -1;
                boolean pShared = false;
                boolean cShared = false;


                if (pLen == cLen && freshSet)
                {
                    runLen = cLen;
                    pShared = false;
                    cShared = false;
                    curP++;
                    curC++;
                    pLenRemain = -1;
                    cLenRemain = -1;
                }
                else
                {

                    if (pLenRemain < pLen)
                    {

                        pShared = true;

                        if (pLenRemain == cLenRemain)
                        {

                            cShared = false;
                            runLen = pLenRemain;
                            curP++;
                            curC++;
                            pLenRemain = -1;
                            cLenRemain = -1;
                        }
                        else if (pLenRemain < cLenRemain)
                        {

                            cShared = true;
                            runLen = pLenRemain;
                            curP++;
                            cLenRemain -= pLenRemain;
                            pLenRemain = -1;
                        }
                        else
                        {

                            cShared = false;
                            runLen = cLenRemain;
                            curC++;
                            pLenRemain -= cLenRemain;
                            cLenRemain = -1;
                        }
                    }
                    else if (cLenRemain < cLen)
                    {
                        cShared = true;


                        if (pLenRemain == cLenRemain)
                        {

                            pShared = false;
                            runLen = cLenRemain;
                            curP++;
                            curC++;
                            pLenRemain = -1;
                            cLenRemain = -1;
                        }
                        else if (cLenRemain < pLenRemain)
                        {
                            // We end before the p block
                            pShared = true;
                            runLen = cLenRemain;
                            curC++;
                            pLenRemain -= cLenRemain;
                            cLenRemain = -1;
                        }
                        else
                        {

                            pShared = false;
                            runLen = pLenRemain;
                            curP++;
                            cLenRemain -= pLenRemain;
                            pLenRemain = -1;
                        }
                    }
                    else
                    {

                        if (pLenRemain < cLenRemain)
                        {
                            pShared = false;
                            cShared = true;
                            runLen = pLenRemain;
                            curP++;
                            cLenRemain -= pLenRemain;
                            pLenRemain = -1;
                        }
                        else
                        {

                            pShared = true;
                            cShared = false;
                            runLen = cLenRemain;
                            curC++;
                            pLenRemain -= cLenRemain;
                            cLenRemain = -1;
                        }
                    }
                }


                int prevPos = pos;
                pos += runLen;

                if (pos > runRawText.length())
                {
                    runLen--;
                }


                RichTextRun rtr = new RichTextRun(this, prevPos, runLen, pProps, cProps, pShared,
                    cShared);
                rtrs.add(rtr);
            }

            _rtRuns = new RichTextRun[rtrs.size()];
            rtrs.copyInto(_rtRuns);
        }

    }


    public RichTextRun appendText(String s)
    {

        ensureStyleAtomPresent();

        int oldSize = getRawText().length();
        storeText(getRawText() + s);
        int pOverRun = _styleAtom.getParagraphTextLengthCovered() - oldSize;
        int cOverRun = _styleAtom.getCharacterTextLengthCovered() - oldSize;
        if (pOverRun > 0)
        {
            TextPropCollection tpc = (TextPropCollection)_styleAtom.getParagraphStyles().getLast();
            tpc.updateTextSize(tpc.getCharactersCovered() - pOverRun);
        }
        if (cOverRun > 0)
        {
            TextPropCollection tpc = (TextPropCollection)_styleAtom.getCharacterStyles().getLast();
            tpc.updateTextSize(tpc.getCharactersCovered() - cOverRun);
        }

        TextPropCollection newPTP = _styleAtom
            .addParagraphTextPropCollection(s.length() + pOverRun);
        TextPropCollection newCTP = _styleAtom
            .addCharacterTextPropCollection(s.length() + cOverRun);

        RichTextRun nr = new RichTextRun(this, oldSize, s.length(), newPTP, newCTP, false, false);

        RichTextRun[] newRuns = new RichTextRun[_rtRuns.length + 1];
        System.arraycopy(_rtRuns, 0, newRuns, 0, _rtRuns.length);
        newRuns[newRuns.length - 1] = nr;
        _rtRuns = newRuns;

        return nr;
    }

    private void storeText(String s)
    {

        if (s.endsWith("\r"))
        {
            s = s.substring(0, s.length() - 1);
        }


        if (_isUnicode)
        {

            _charAtom.setText(s);
        }
        else
        {

            boolean hasMultibyte = StringUtil.hasMultibyte(s);
            if (!hasMultibyte)
            {

                byte[] text = new byte[s.length()];
                StringUtil.putCompressedUnicode(s, text, 0);
                _byteAtom.setText(text);
            }
            else
            {

                _charAtom = new TextCharsAtom();
                _charAtom.setText(s);


                RecordContainer parent = _headerAtom.getParentRecord();
                Record[] cr = parent.getChildRecords();
                for (int i = 0; i < cr.length; i++)
                {
                    if (cr[i].equals(_byteAtom))
                    {

                        cr[i] = _charAtom;
                        break;
                    }
                }


                _byteAtom = null;
                _isUnicode = true;
            }
        }

        if (_records != null)
            for (int i = 0; i < _records.length; i++)
            {
                if (_records[i] instanceof TextSpecInfoAtom)
                {
                    TextSpecInfoAtom specAtom = (TextSpecInfoAtom)_records[i];
                    if ((s.length() + 1) != specAtom.getCharactersCovered())
                    {
                        specAtom.reset(s.length() + 1);
                    }
                }
            }
    }


    public void changeTextInRichTextRun(RichTextRun run, String s)
    {

        int runID = -1;
        for (int i = 0; i < _rtRuns.length; i++)
        {
            if (run.equals(_rtRuns[i]))
            {
                runID = i;
            }
        }
        if (runID == -1)
        {
            throw new IllegalArgumentException("Supplied RichTextRun wasn't from this TextRun");
        }


        ensureStyleAtomPresent();


        TextPropCollection pCol = run._getRawParagraphStyle();
        TextPropCollection cCol = run._getRawCharacterStyle();
        int newSize = s.length();
        if (runID == _rtRuns.length - 1)
        {
            newSize++;
        }

        if (run._isParagraphStyleShared())
        {
            pCol.updateTextSize(pCol.getCharactersCovered() - run.getLength() + s.length());
        }
        else
        {
            pCol.updateTextSize(newSize);
        }
        if (run._isCharacterStyleShared())
        {
            cCol.updateTextSize(cCol.getCharactersCovered() - run.getLength() + s.length());
        }
        else
        {
            cCol.updateTextSize(newSize);
        }

        StringBuffer newText = new StringBuffer();
        for (int i = 0; i < _rtRuns.length; i++)
        {
            int newStartPos = newText.length();

            if (i != runID)
            {
                newText.append(_rtRuns[i].getRawText());
            }
            else
            {
                newText.append(s);
            }

            if (i <= runID)
            {
            }
            else
            {
                _rtRuns[i].updateStartPosition(newStartPos);
            }
        }

        storeText(newText.toString());
    }


    public void setRawText(String s)
    {

        storeText(s);
        RichTextRun fst = _rtRuns[0];

        for (int i = 0; i < _rtRuns.length; i++)
        {
            _rtRuns[i] = null;
        }
        _rtRuns = new RichTextRun[1];
        _rtRuns[0] = fst;


        if (_styleAtom != null)
        {
            LinkedList pStyles = _styleAtom.getParagraphStyles();
            while (pStyles.size() > 1)
            {
                pStyles.removeLast();
            }

            LinkedList cStyles = _styleAtom.getCharacterStyles();
            while (cStyles.size() > 1)
            {
                cStyles.removeLast();
            }

            _rtRuns[0].setText(s);
        }
        else
        {

            _rtRuns[0] = new RichTextRun(this, 0, s.length());
        }

    }


    public void setText(String s)
    {
        String text = normalize(s);
        setRawText(text);
    }


    public void ensureStyleAtomPresent()
    {
        if (_styleAtom != null)
        {

            return;
        }


        _styleAtom = new StyleTextPropAtom(getRawText().length() + 1);

        RecordContainer runAtomsParent = _headerAtom.getParentRecord();

        Record addAfter = _byteAtom;
        if (_byteAtom == null)
        {
            addAfter = _charAtom;
        }
        runAtomsParent.addChildAfter(_styleAtom, addAfter);

        if (_rtRuns.length != 1)
        {
            throw new IllegalStateException(
                "Needed to add StyleTextPropAtom when had many rich text runs");
        }
        _rtRuns[0].supplyTextProps((TextPropCollection)_styleAtom.getParagraphStyles().get(0),
            (TextPropCollection)_styleAtom.getCharacterStyles().get(0), false, false);
    }

    /**
     * Returns the text content of the run, which has been made safe
     * for printing and other use.
     */
    public String getText()
    {
        String rawText = getRawText();

        String text = rawText.replace('\r', '\n');


        text = text.replace((char)0x0B, '\u000b');
        return text;
    }

    public String getRawText()
    {
        if (_isUnicode)
        {
            return _charAtom.getText();
        }
        return _byteAtom.getText();
    }

    public RichTextRun[] getRichTextRuns()
    {
        return _rtRuns;
    }


    public int getRunType()
    {
        return _headerAtom.getTextType();
    }

    public void setRunType(int type)
    {
        _headerAtom.setTextType(type);
    }


    public void supplySlideShow(SlideShow ss)
    {
        slideShow = ss;
        if (_rtRuns != null)
        {
            for (int i = 0; i < _rtRuns.length; i++)
            {
                _rtRuns[i].supplySlideShow(slideShow);
            }
        }
    }

    public void setSheet(Sheet sheet)
    {
        this._sheet = sheet;
    }

    public Sheet getSheet()
    {
        return this._sheet;
    }

    protected int getShapeId()
    {
        return shapeId;
    }

    protected void setShapeId(int id)
    {
        shapeId = id;
    }


    protected int getIndex()
    {
        return slwtIndex;
    }


    protected void setIndex(int id)
    {
        slwtIndex = id;
    }


    public Hyperlink[] getHyperlinks()
    {
        return Hyperlink.find(this);
    }



    public RichTextRun getRichTextRunAt(int pos)
    {
        for (int i = 0; i < _rtRuns.length; i++)
        {
            int start = _rtRuns[i].getStartIndex();
            int end = _rtRuns[i].getEndIndex();
            if (pos >= start && pos < end)
                return _rtRuns[i];
        }
        return null;
    }

    public TextRulerAtom getTextRuler()
    {
        if (_ruler == null)
        {
            if (_records != null)
                for (int i = 0; i < _records.length; i++)
                {
                    if (_records[i] instanceof TextRulerAtom)
                    {
                        _ruler = (TextRulerAtom)_records[i];
                        break;
                    }
                }

        }
        return _ruler;

    }

    public TextRulerAtom createTextRuler()
    {
        _ruler = getTextRuler();
        if (_ruler == null)
        {
            _ruler = TextRulerAtom.getParagraphInstance();
            _headerAtom.getParentRecord().appendChildRecord(_ruler);
        }
        return _ruler;
    }



    public String normalize(String s)
    {
        String ns = s.replaceAll("\\r?\\n", "\r");
        return ns;
    }


    public Record[] getRecords()
    {
        return _records;
    }
    

    public ExtendedParagraphAtom getExtendedParagraphAtom()
    {
        return _extendedParagraphAtom;
    }
    

    public void setExtendedParagraphAtom(ExtendedParagraphAtom extendedParaAtom)
    {
        _extendedParagraphAtom = extendedParaAtom;
    }
    

    public int getNumberingType(int characterIndex)
    {
        if (_extendedParagraphAtom != null)
        {
            int index = getAutoNumberIndex(characterIndex);
            if (index >= 0)
            {
                LinkedList<AutoNumberTextProp> paraPropList =  _extendedParagraphAtom.getExtendedParagraphPropList();
                if (paraPropList != null && paraPropList.size() > 0 && index < paraPropList.size())
                {
                    AutoNumberTextProp paraProp = paraPropList.get(index);
                    if (paraProp != null)
                    {
                        return paraProp.getNumberingType();
                    }
                }
            }
        }
        return -1;
    }
    

    public int getNumberingStart(int characterIndex)
    {
        if (_extendedParagraphAtom != null)
        {
            int index = getAutoNumberIndex(characterIndex);
            if (index >= 0)
            {
                LinkedList<AutoNumberTextProp> paraPropList =  _extendedParagraphAtom.getExtendedParagraphPropList();
                if (paraPropList != null && paraPropList.size() > 0 && index < paraPropList.size())
                {
                    AutoNumberTextProp paraProp = paraPropList.get(index);
                    if (paraProp != null)
                    {
                        return paraProp.getStart();
                    }
                }
            }
        }
        return 0;
    }
    

    public int getAutoNumberIndex(int characterIndex)
    {
        if (_records != null)
        {
            for (int i = 0; i < _records.length; i++)
            {
                if (_records[i] instanceof StyleTextPropAtom)
                {
                    StyleTextPropAtom stp = (StyleTextPropAtom)_records[i];
                    if (stp != null)
                    {
                        return stp.getAutoNumberIndex(characterIndex);
                    }
                }
            }
        }
        return -1;
    }
    

    public void dispose()
    {
        slideShow = null;
        _sheet = null;
        if (_headerAtom != null)
        {
            _headerAtom.dispose();
            _headerAtom = null;
        }
        if (_byteAtom != null)
        {
            _byteAtom.dispose();
            _byteAtom = null;
        }
        if (_charAtom != null)
        {
            _charAtom.dispose();
            _charAtom = null;
        }
        if (_styleAtom != null)
        {
            _styleAtom.dispose();
            _styleAtom = null;
        }
        if (_ruler != null)
        {
            _ruler.dispose();
            _ruler = null;
        }
        if (_extendedParagraphAtom != null)
        {
            _extendedParagraphAtom.dispose();
            _extendedParagraphAtom = null;
        }
        if (_rtRuns != null)
        {
            for (RichTextRun rt : _rtRuns)
            {
                rt.dispose();
            }
            _rtRuns = null;
        }
    }
    
    

    protected TextHeaderAtom _headerAtom;
    protected TextBytesAtom _byteAtom;
    protected TextCharsAtom _charAtom;
    protected StyleTextPropAtom _styleAtom;
    protected TextRulerAtom _ruler;
    protected ExtendedParagraphAtom _extendedParagraphAtom;
    protected boolean _isUnicode;
    protected RichTextRun[] _rtRuns;
    private SlideShow slideShow;
    private Sheet _sheet;
    private int shapeId = -1;
    private int slwtIndex; //position in the owning SlideListWithText
}
