

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.common.shape.TextBox;
import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherOptRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperties;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSimpleProperty;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSpRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherTextboxRecord;
import com.document.allreader.allofficefilereader.fc.hslf.record.EscherTextboxWrapper;
import com.document.allreader.allofficefilereader.fc.hslf.record.InteractiveInfo;
import com.document.allreader.allofficefilereader.fc.hslf.record.InteractiveInfoAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.OEPlaceholderAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.OutlineTextRefAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.PPDrawing;
import com.document.allreader.allofficefilereader.fc.hslf.record.Record;
import com.document.allreader.allofficefilereader.fc.hslf.record.RecordTypes;
import com.document.allreader.allofficefilereader.fc.hslf.record.RoundTripHFPlaceholder12;
import com.document.allreader.allofficefilereader.fc.hslf.record.StyleTextPropAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.TextCharsAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.TextHeaderAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.TextRulerAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.TxInteractiveInfoAtom;
import com.document.allreader.allofficefilereader.fc.hslf.usermodel.RichTextRun;
import com.document.allreader.allofficefilereader.java.awt.Rectangle;
import com.document.allreader.allofficefilereader.java.awt.geom.Rectangle2D;


public abstract class TextShape extends SimpleShape
{

    public static final int AnchorTop = 0;
    public static final int AnchorMiddle = 1;
    public static final int AnchorBottom = 2;
    public static final int AnchorTopCentered = 3;
    public static final int AnchorMiddleCentered = 4;
    public static final int AnchorBottomCentered = 5;
    public static final int AnchorTopBaseline = 6;
    public static final int AnchorBottomBaseline = 7;
    public static final int AnchorTopCenteredBaseline = 8;
    public static final int AnchorBottomCenteredBaseline = 9;


    public static final int WrapSquare = 0;
    public static final int WrapByPoints = 1;
    public static final int WrapNone = 2;
    public static final int WrapTopBottom = 3;
    public static final int WrapThrough = 4;


    public static final int AlignLeft = 0;
    public static final int AlignCenter = 1;
    public static final int AlignRight = 2;
    public static final int AlignJustify = 3;


    protected TextRun _txtrun;


    protected EscherTextboxWrapper _txtbox;



    protected TextShape(EscherContainerRecord escherRecord, Shape parent)
    {
        super(escherRecord, parent);

    }


    public TextShape(Shape parent)
    {
        super(null, parent);
        _escherContainer = createSpContainer(parent instanceof ShapeGroup);
    }


    public TextShape()
    {
        this(null);
    }

    public TextRun createTextRun()
    {
        _txtbox = getEscherTextboxWrapper();
        if (_txtbox == null)
            _txtbox = new EscherTextboxWrapper();

        _txtrun = getTextRun();
        if (_txtrun == null)
        {
            TextHeaderAtom tha = new TextHeaderAtom();
            tha.setParentRecord(_txtbox);
            _txtbox.appendChildRecord(tha);

            TextCharsAtom tca = new TextCharsAtom();
            _txtbox.appendChildRecord(tca);

            StyleTextPropAtom sta = new StyleTextPropAtom(0);
            _txtbox.appendChildRecord(sta);

            _txtrun = new TextRun(tha, tca, sta);
            _txtrun._records = new Record[]{tha, tca, sta};
            _txtrun.setText("");

            _escherContainer.addChildRecord(_txtbox.getEscherRecord());

            setDefaultTextProperties(_txtrun);
        }

        return _txtrun;
    }


    protected void setDefaultTextProperties(TextRun _txtrun)
    {

    }

    public String getText() {
        TextRun tx = getTextRun();
        return tx == null ? null : tx.getText();
    }
    public void setText(String text)
    {
        TextRun tx = getTextRun();
        if (tx == null)
        {
            tx = createTextRun();
        }
        tx.setText(text);
        setTextId(text.hashCode());
    }


    protected void afterInsert(Sheet sh)
    {
        super.afterInsert(sh);

        EscherTextboxWrapper _txtbox = getEscherTextboxWrapper();
        if (_txtbox != null)
        {
            PPDrawing ppdrawing = sh.getPPDrawing();
            ppdrawing.addTextboxWrapper(_txtbox);

            if (getAnchor().equals(new Rectangle()) && !"".equals(getText()))
                resizeToFitText();
        }
        if (_txtrun != null)
        {
            _txtrun.setShapeId(getShapeId());
            sh.onAddTextShape(this);
        }
    }

    protected EscherTextboxWrapper getEscherTextboxWrapper()
    {
        if (_txtbox == null)
        {
            EscherTextboxRecord textRecord = (EscherTextboxRecord)ShapeKit.getEscherChild(
                _escherContainer, EscherTextboxRecord.RECORD_ID);
            if (textRecord != null)
                _txtbox = new EscherTextboxWrapper(textRecord);
        }
        return _txtbox;
    }
    

    public byte getMetaCharactersType()
    {
        EscherTextboxWrapper clientTextBox = getEscherTextboxWrapper();
        if (clientTextBox != null)
        {
            Record[] children = clientTextBox.getChildRecords();
            for (int i = 0; i < children.length; i++)
            {
                if (children[i] != null)
                {
                	long rType = children[i].getRecordType();
                	if (rType == RecordTypes.SlideNumberMCAtom.typeID)
                	{
                		return TextBox.MC_SlideNumber;
                	}
                	else if (rType == RecordTypes.DateTimeMCAtom.typeID)
                	{
                		return TextBox.MC_DateTime;
                	}
                	else if(rType == RecordTypes.GenericDateMCAtom.typeID)
                    {
                        return TextBox.MC_GenericDate;
                    }
                	else if(rType == RecordTypes.RTFDateTimeMCAtom.typeID)
                    {
                        return TextBox.MC_RTFDateTime;
                    }
                	else if(rType == RecordTypes.FooterMCAtom.typeID)
                    {
                        return TextBox.MC_Footer;
                    }
                }
            }
        }
        return -1;
    }
    

    public Rectangle2D resizeToFitText()
    {

        Rectangle2D anchor = getAnchor2D();
        return anchor;
    }


    public int getVerticalAlignment()
    {
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        EscherSimpleProperty prop = (EscherSimpleProperty)ShapeKit.getEscherProperty(opt,
            EscherProperties.TEXT__ANCHORTEXT);
        int valign = TextShape.AnchorTop;
        if (prop == null)
        {

            int type = getTextRun().getRunType();
            MasterSheet master = getSheet().getMasterSheet();
            TextShape masterShape = null;
            if (master != null && getPlaceholderAtom() != null)
            {
                masterShape = master.getPlaceholderByTextType(type);
            }
            if (masterShape != null)
            {
                valign = masterShape.getVerticalAlignment();
            }
            else
            {
                switch (type)
                {
                    case TextHeaderAtom.TITLE_TYPE:
                    case TextHeaderAtom.CENTER_TITLE_TYPE:
                        valign = TextShape.AnchorMiddle;
                        break;
                    default:
                        valign = TextShape.AnchorTop;
                        break;
                }
            }
        }
        else
        {
            valign = prop.getPropertyValue();
        }
        return valign;
    }


    public void setVerticalAlignment(int align)
    {
        setEscherProperty(EscherProperties.TEXT__ANCHORTEXT, align);
    }


    public void setHorizontalAlignment(int align)
    {
        TextRun tx = getTextRun();
        if (tx != null)
            tx.getRichTextRuns()[0].setAlignment(align);
    }

    public int getHorizontalAlignment()
    {
        TextRun tx = getTextRun();
        return tx == null ? -1 : tx.getRichTextRuns()[0].getAlignment();
    }


    public float getMarginBottom()
    {
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        EscherSimpleProperty prop = (EscherSimpleProperty)ShapeKit.getEscherProperty(opt,
            EscherProperties.TEXT__TEXTBOTTOM);
        int val = prop == null ? ShapeKit.EMU_PER_INCH / 20 : prop.getPropertyValue();
        return (float)val / ShapeKit.EMU_PER_POINT;
    }


    public void setMarginBottom(float margin)
    {
        setEscherProperty(EscherProperties.TEXT__TEXTBOTTOM, (int)(margin * ShapeKit.EMU_PER_POINT));
    }


    public float getMarginLeft()
    {
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        EscherSimpleProperty prop = (EscherSimpleProperty)ShapeKit.getEscherProperty(opt,
            EscherProperties.TEXT__TEXTLEFT);
        int val = prop == null ? ShapeKit.EMU_PER_INCH / 10 : prop.getPropertyValue();
        return (float)val / ShapeKit.EMU_PER_POINT;
    }

    public void setMarginLeft(float margin)
    {
        setEscherProperty(EscherProperties.TEXT__TEXTLEFT, (int)(margin * ShapeKit.EMU_PER_POINT));
    }


    public float getMarginRight()
    {
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        EscherSimpleProperty prop = (EscherSimpleProperty)ShapeKit.getEscherProperty(opt,
            EscherProperties.TEXT__TEXTRIGHT);
        int val = prop == null ? ShapeKit.EMU_PER_INCH / 10 : prop.getPropertyValue();
        return (float)val / ShapeKit.EMU_PER_POINT;
    }


    public void setMarginRight(float margin)
    {
        setEscherProperty(EscherProperties.TEXT__TEXTRIGHT, (int)(margin * ShapeKit.EMU_PER_POINT));
    }


    public float getMarginTop()
    {
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        EscherSimpleProperty prop = (EscherSimpleProperty)ShapeKit.getEscherProperty(opt,
            EscherProperties.TEXT__TEXTTOP);
        int val = prop == null ? ShapeKit.EMU_PER_INCH / 20 : prop.getPropertyValue();
        return (float)val / ShapeKit.EMU_PER_POINT;
    }





    public int getWordWrap()
    {
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        EscherSimpleProperty prop = (EscherSimpleProperty)ShapeKit.getEscherProperty(opt,
            EscherProperties.TEXT__WRAPTEXT);
        return prop == null ? WrapSquare : prop.getPropertyValue();
    }


    public void setWordWrap(int wrap)
    {
        setEscherProperty(EscherProperties.TEXT__WRAPTEXT, wrap);
    }


    public int getTextId()
    {
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        EscherSimpleProperty prop = (EscherSimpleProperty)ShapeKit.getEscherProperty(opt,
            EscherProperties.TEXT__TEXTID);
        return prop == null ? 0 : prop.getPropertyValue();
    }


    public void setTextId(int id)
    {
        setEscherProperty(EscherProperties.TEXT__TEXTID, id);
    }


    public TextRun getTextRun()
    {
        if (_txtrun == null)
            initTextRun();
        return _txtrun;
    }

    public void setSheet(Sheet sheet)
    {
        _sheet = sheet;


        TextRun tx = getTextRun();
        if (tx != null)
        {

            tx.setSheet(_sheet);
            RichTextRun[] rt = tx.getRichTextRuns();
            for (int i = 0; i < rt.length; i++)
            {
                rt[i].supplySlideShow(_sheet.getSlideShow());
            }
        }

    }

    protected void initTextRun()
    {
        EscherTextboxWrapper txtbox = getEscherTextboxWrapper();
        Sheet sheet = getSheet();

        if (sheet == null || txtbox == null)
            return;

        OutlineTextRefAtom ota = null;

        Record[] child = txtbox.getChildRecords();
        for (int i = 0; i < child.length; i++)
        {
            if (child[i] instanceof OutlineTextRefAtom)
            {
                ota = (OutlineTextRefAtom)child[i];
                break;
            }
        }

        TextRun[] runs = _sheet.getTextRuns();
        if (ota != null)
        {
            int idx = ota.getTextIndex();
            for (int i = 0; i < runs.length; i++)
            {
                if (runs[i].getIndex() == idx && runs[i].getShapeId() < 0)
                {
                    _txtrun = runs[i];
                    break;
                }
            }


        }
        else
        {
            EscherSpRecord escherSpRecord = _escherContainer.getChildById(EscherSpRecord.RECORD_ID);
            int shapeId = escherSpRecord.getShapeId();
            if (runs != null)
                for (int i = 0; i < runs.length; i++)
                {
                    if (runs[i].getShapeId() == shapeId)
                    {
                        _txtrun = runs[i];
                        break;
                    }
                }
        }

        if (_txtrun != null)
            for (int i = 0; i < child.length; i++)
            {
                if (_txtrun._ruler == null && child[i] instanceof TextRulerAtom)
                {
                    _txtrun._ruler = (TextRulerAtom)child[i];
                }
                for (Record r : _txtrun.getRecords())
                {
                    if (child[i].getRecordType() == r.getRecordType())
                    {
                        child[i] = r;
                    }
                }
            }
    }


    public OEPlaceholderAtom getPlaceholderAtom()
    {
        return (OEPlaceholderAtom)getClientDataRecord(RecordTypes.OEPlaceholderAtom.typeID);
    }


    public int getPlaceholderId()
    {
    	int placeholderId = 0;
        OEPlaceholderAtom oep = getPlaceholderAtom();
        if (oep != null)
        {
            placeholderId = oep.getPlaceholderId();
        }
        else
        {
            RoundTripHFPlaceholder12 hldr = 
            		(RoundTripHFPlaceholder12)getClientDataRecord(RecordTypes.RoundTripHFPlaceholder12.typeID);
            if (hldr != null)
                placeholderId = hldr.getPlaceholderId();
        }
        
        return placeholderId;
    }
    

    public void setHyperlink(int linkId, int beginIndex, int endIndex)
    {


        InteractiveInfo info = new InteractiveInfo();
        InteractiveInfoAtom infoAtom = info.getInteractiveInfoAtom();
        infoAtom.setAction(InteractiveInfoAtom.ACTION_HYPERLINK);
        infoAtom.setHyperlinkType(InteractiveInfoAtom.LINK_Url);
        infoAtom.setHyperlinkID(linkId);

        _txtbox.appendChildRecord(info);

        TxInteractiveInfoAtom txiatom = new TxInteractiveInfoAtom();
        txiatom.setStartIndex(beginIndex);
        txiatom.setEndIndex(endIndex);
        _txtbox.appendChildRecord(txiatom);

    }
    
    
    public Sheet getSheet()
    {
    	return _sheet;
    }
    

    public String getUnicodeGeoText()
    {
    	return ShapeKit.getUnicodeGeoText(_escherContainer);
    }
    
    
    public void dispose()
    {
        super.dispose();
        if (_txtrun != null)
        {
            _txtrun.dispose();
            _txtrun = null;
        }
        if (_txtbox != null)
        {
            _txtbox.dispose();
            _txtbox = null;
        }
    }
}
