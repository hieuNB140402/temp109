

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.TextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.TextPropCollection;
import com.document.allreader.allofficefilereader.fc.hslf.record.MainMaster;
import com.document.allreader.allofficefilereader.fc.hslf.record.TextHeaderAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.TxMasterStyleAtom;
import com.document.allreader.allofficefilereader.fc.hslf.usermodel.SlideShow;


public final class SlideMaster extends MasterSheet
{


    public SlideMaster(MainMaster record, int sheetNo)
    {
        super(record, sheetNo);

        _runs = findTextRuns(getPPDrawing());
        for (int i = 0; i < _runs.length; i++)
            _runs[i].setSheet(this);
    }

    public TextRun[] getTextRuns()
    {
        return _runs;
    }


    public MasterSheet getMasterSheet()
    {
        return null;
    }


    public TextProp getStyleAttribute(int txtype, int level, String name, boolean isCharacter)
    {

        TextProp prop = null;
        for (int i = level; i >= 0; i--)
        {
            TextPropCollection[] styles = isCharacter ? _txmaster[txtype].getCharacterStyles()
                : _txmaster[txtype].getParagraphStyles();
            if (i < styles.length)
                prop = styles[i].findByName(name);
            if (prop != null)
                break;
        }
        if (prop == null)
        {
            if (isCharacter)
            {
                switch (txtype)
                {
                    case TextHeaderAtom.CENTRE_BODY_TYPE:
                    case TextHeaderAtom.HALF_BODY_TYPE:
                    case TextHeaderAtom.QUARTER_BODY_TYPE:
                        txtype = TextHeaderAtom.BODY_TYPE;
                        break;
                    case TextHeaderAtom.CENTER_TITLE_TYPE:
                        txtype = TextHeaderAtom.TITLE_TYPE;
                        break;
                    default:
                        return null;
                }
            }
            else
            {
                switch (txtype)
                {
                    case TextHeaderAtom.CENTRE_BODY_TYPE:
                    case TextHeaderAtom.HALF_BODY_TYPE:
                    case TextHeaderAtom.QUARTER_BODY_TYPE:
                        txtype = TextHeaderAtom.BODY_TYPE;
                        break;
                    case TextHeaderAtom.CENTER_TITLE_TYPE:
                        txtype = TextHeaderAtom.TITLE_TYPE;
                        break;
                    default:
                        return null;
                }
            }
            prop = getStyleAttribute(txtype, level, name, isCharacter);
        }
        return prop;
    }

    public void setSlideShow(SlideShow ss)
    {
        super.setSlideShow(ss);


        if (_txmaster == null)
        {
            _txmaster = new TxMasterStyleAtom[9];


            TxMasterStyleAtom[] txrec = ((MainMaster)getSheetContainer()).getTxMasterStyleAtoms();
            for (int i = 0; i < txrec.length; i++)
            {
                _txmaster[txrec[i].getTextType()] = txrec[i];
            }
            TxMasterStyleAtom txdoc = getSlideShow().getDocumentRecord().getEnvironment()
                .getTxMasterStyleAtom();
            _txmaster[txdoc.getTextType()] = txdoc;
        }
    }

    protected void onAddTextShape(TextShape shape)
    {
        TextRun run = shape.getTextRun();

        if (_runs == null)
            _runs = new TextRun[]{run};
        else
        {
            TextRun[] tmp = new TextRun[_runs.length + 1];
            System.arraycopy(_runs, 0, tmp, 0, _runs.length);
            tmp[tmp.length - 1] = run;
            _runs = tmp;
        }
    }

    public TxMasterStyleAtom[] getTxMasterStyleAtoms()
    {
        return _txmaster;
    }
    

    public void dispose()
    {
        super.dispose();
        if (_runs != null)
        {
            for (TextRun tr : _runs)
            {
                tr.dispose();
            }
            _runs = null;
        }
        if (_txmaster != null)
        {
            for (TxMasterStyleAtom tsa : _txmaster)
            {
                if (tsa != null)
                {
                    tsa.dispose();
                }
            }
            _txmaster = null;
        }
        
    }
    
    private TextRun[] _runs;


    private TxMasterStyleAtom[] _txmaster;
}
