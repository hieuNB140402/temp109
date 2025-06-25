

package com.document.allreader.allofficefilereader.fc.hslf.model;

import java.util.Vector;

import com.document.allreader.allofficefilereader.common.shape.ShapeTypes;
import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherDgRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherDggRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSpRecord;
import com.document.allreader.allofficefilereader.fc.hslf.record.ColorSchemeAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.Comment2000;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExtendedParagraphHeaderAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.HeadersFootersContainer;
import com.document.allreader.allofficefilereader.fc.hslf.record.RecordContainer;
import com.document.allreader.allofficefilereader.fc.hslf.record.RecordTypes;
import com.document.allreader.allofficefilereader.fc.hslf.record.SlideAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.SlideProgTagsContainer;
import com.document.allreader.allofficefilereader.fc.hslf.record.SlideShowSlideInfoAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.TextHeaderAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExtendedPresRuleContainer.ExtendedParaAtomsSet;
import com.document.allreader.allofficefilereader.fc.hslf.record.SlideListWithText.SlideAtomsSet;
import com.document.allreader.allofficefilereader.java.awt.Rectangle;



public final class Slide extends Sheet
{

    public Slide(com.document.allreader.allofficefilereader.fc.hslf.record.Slide slide, Notes notes, SlideAtomsSet atomSet,
                 ExtendedParaAtomsSet[] extendedAtomsSets, int slideIdentifier, int slideNumber)
    {
        super(slide, slideIdentifier);

        _notes = notes;
        _atomSet = atomSet;
        _slideNo = slideNumber;
        _extendedAtomsSets = extendedAtomsSets;
        

        TextRun[] _otherRuns = findTextRuns(getPPDrawing());


        Vector textRuns = new Vector();
        if (_atomSet != null)
        {
            findTextRuns(_atomSet.getSlideRecords(), textRuns);
        }
        else
        {

        }


        _runs = new TextRun[textRuns.size() + _otherRuns.length];

        int i = 0;
        for (i = 0; i < textRuns.size(); i++)
        {
            _runs[i] = (TextRun)textRuns.get(i);
            _runs[i].setSheet(this);
        }
        // Grab text from slide's PPDrawing
        for (int k = 0; k < _otherRuns.length; i++, k++)
        {
            _runs[i] = _otherRuns[k];
            _runs[i].setSheet(this);
        }
        
        if (_extendedAtomsSets != null)
        {
            for (i = 0; i < _runs.length; i++)
            {
                if (_runs[i].getExtendedParagraphAtom() == null)
                {
                    int type = _runs[i].getRunType();
                    for (int j = 0; j < _extendedAtomsSets.length; j++)
                    {
                        ExtendedParagraphHeaderAtom paraHeaderAtom = _extendedAtomsSets[j].getExtendedParaHeaderAtom();
                        if (paraHeaderAtom != null && paraHeaderAtom.getTextType() == type)
                        {
                            _runs[i].setExtendedParagraphAtom(_extendedAtomsSets[j].getExtendedParaAtom());
                            break;
                        }
                    }
                }
            }
        }
    }


    public Slide(int sheetNumber, int sheetRefId, int slideNumber)
    {
        super(new com.document.allreader.allofficefilereader.fc.hslf.record.Slide(), sheetNumber);
        _slideNo = slideNumber;
        getSheetContainer().setSheetId(sheetRefId);
    }


    public void setNotes(Notes notes)
    {
        _notes = notes;


        SlideAtom sa = getSlideRecord().getSlideAtom();

        if (notes == null)
        {

            sa.setNotesID(0);
        }
        else
        {

            sa.setNotesID(notes._getSheetNumber());
        }
    }


    public void setSlideNumber(int newSlideNumber)
    {
        _slideNo = newSlideNumber;
    }


    public void onCreate()
    {

        EscherDggRecord dgg = getSlideShow().getDocumentRecord().getPPDrawingGroup()
            .getEscherDggRecord();
        EscherContainerRecord dgContainer = (EscherContainerRecord)getSheetContainer()
            .getPPDrawing().getEscherRecords()[0];
        EscherDgRecord dg = (EscherDgRecord)ShapeKit.getEscherChild(dgContainer,
            EscherDgRecord.RECORD_ID);
        int dgId = dgg.getMaxDrawingGroupId() + 1;
        dg.setOptions((short)(dgId << 4));
        dgg.setDrawingsSaved(dgg.getDrawingsSaved() + 1);
        dgg.setMaxDrawingGroupId(dgId);

        for (EscherContainerRecord c : dgContainer.getChildContainers())
        {
            EscherSpRecord spr = null;
            switch (c.getRecordId())
            {
                case EscherContainerRecord.SPGR_CONTAINER:
                    EscherContainerRecord dc = (EscherContainerRecord)c.getChild(0);
                    spr = dc.getChildById(EscherSpRecord.RECORD_ID);
                    break;
                case EscherContainerRecord.SP_CONTAINER:
                    spr = c.getChildById(EscherSpRecord.RECORD_ID);
                    break;
            }
            if (spr != null)
                spr.setShapeId(allocateShapeId());
        }

        dg.setNumShapes(1);
    }

    public TextBox addTitle()
    {
        Placeholder pl = new Placeholder();
        pl.setShapeType(ShapeTypes.Rectangle);
        pl.getTextRun().setRunType(TextHeaderAtom.TITLE_TYPE);
        pl.setText("Click to edit title");
        pl.setAnchor(new Rectangle(54, 48, 612, 90));
        addShape(pl);
        return pl;
    }




    public String getTitle()
    {
        TextRun[] txt = getTextRuns();
        for (int i = 0; i < txt.length; i++)
        {
            int type = txt[i].getRunType();
            if (type == TextHeaderAtom.CENTER_TITLE_TYPE || type == TextHeaderAtom.TITLE_TYPE)
            {
                String title = txt[i].getText();
                return title;
            }
        }
        return null;
    }


    public TextRun[] getTextRuns()
    {
        return _runs;
    }

    public int getSlideNumber()
    {
        return _slideNo;
    }

    public com.document.allreader.allofficefilereader.fc.hslf.record.Slide getSlideRecord()
    {
        return (com.document.allreader.allofficefilereader.fc.hslf.record.Slide)getSheetContainer();
    }

    public Notes getNotesSheet()
    {
        return _notes;
    }


    protected SlideAtomsSet getSlideAtomsSet()
    {
        return _atomSet;
    }


    public MasterSheet getMasterSheet()
    {
        SlideMaster[] master = getSlideShow().getSlidesMasters();
        SlideAtom sa = getSlideRecord().getSlideAtom();
        int masterId = sa.getMasterID();
        MasterSheet sheet = null;
        for (int i = 0; i < master.length; i++)
        {
            if (masterId == master[i]._getSheetNumber())
            {
                sheet = master[i];
                break;
            }
        }
        if (sheet == null)
        {
            TitleMaster[] titleMaster = getSlideShow().getTitleMasters();
            if (titleMaster != null)
                for (int i = 0; i < titleMaster.length; i++)
                {
                    if (masterId == titleMaster[i]._getSheetNumber())
                    {
                        sheet = titleMaster[i];
                        break;
                    }
                }
        }
        return sheet;
    }


    public void setMasterSheet(MasterSheet master)
    {
        SlideAtom sa = getSlideRecord().getSlideAtom();
        int sheetNo = master._getSheetNumber();
        sa.setMasterID(sheetNo);
    }
    
    public HeadersFooters getSlideHeadersFooters()
    {
        HeadersFootersContainer container = getSlideRecord().getHeadersFootersContainer();
        if (container != null)
        {
            return new HeadersFooters(container, this, false, false);
        }
        return null;
    }



    public boolean getFollowMasterBackground()
    {
        SlideAtom sa = getSlideRecord().getSlideAtom();
        return sa.getFollowMasterBackground();
    }

    public void setFollowMasterObjects(boolean flag)
    {
        SlideAtom sa = getSlideRecord().getSlideAtom();
        sa.setFollowMasterObjects(flag);
    }


    public boolean getFollowMasterScheme()
    {
        SlideAtom sa = getSlideRecord().getSlideAtom();
        return sa.getFollowMasterScheme();
    }


    public void setFollowMasterScheme(boolean flag)
    {
        SlideAtom sa = getSlideRecord().getSlideAtom();
        sa.setFollowMasterScheme(flag);
    }


    public boolean getFollowMasterObjects()
    {
        SlideAtom sa = getSlideRecord().getSlideAtom();
        return sa.getFollowMasterObjects();
    }


    public Background getBackground()
    {
        if (getFollowMasterBackground())
        {
            return getMasterSheet().getBackground();
        }
        return super.getBackground();
    }


    public ColorSchemeAtom getColorScheme()
    {
        if (getFollowMasterScheme())
        {
            return getMasterSheet().getColorScheme();
        }
        return super.getColorScheme();
    }


    public Comment[] getComments()
    {

        RecordContainer progTags = (RecordContainer)getSheetContainer().findFirstOfType(
            RecordTypes.SlideProgTagsContainer.typeID);
        if (progTags != null)
        {
            RecordContainer progBinaryTag = (RecordContainer)progTags
                .findFirstOfType(RecordTypes.SlideProgBinaryTagContainer.typeID);
            if (progBinaryTag != null)
            {
                RecordContainer binaryTags = (RecordContainer)progBinaryTag
                    .findFirstOfType(RecordTypes.BinaryTagDataBlob.typeID);
                if (binaryTags != null)
                {
                    // This is where they'll be
                    int count = 0;
                    for (int i = 0; i < binaryTags.getChildRecords().length; i++)
                    {
                        if (binaryTags.getChildRecords()[i] instanceof Comment2000)
                        {
                            count++;
                        }
                    }

                    Comment[] comments = new Comment[count];
                    count = 0;
                    for (int i = 0; i < binaryTags.getChildRecords().length; i++)
                    {
                        if (binaryTags.getChildRecords()[i] instanceof Comment2000)
                        {
                            comments[i] = new Comment((Comment2000)binaryTags.getChildRecords()[i]);
                            count++;
                        }
                    }

                    return comments;
                }
            }
        }


        return new Comment[0];
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
    
    public void setExtendedAtom(ExtendedParaAtomsSet[] extendAtomsSets)
    {
        this._extendedAtomsSets = extendAtomsSets;
    }
    

    public void setSlideShowSlideInfoAtom(SlideShowSlideInfoAtom ssSlideInfoAtom)
    {
        this.ssSlideInfoAtom = ssSlideInfoAtom;
    }
    

    public SlideShowSlideInfoAtom getSlideShowSlideInfoAtom()
    {
        return ssSlideInfoAtom;
    }
    

    public void setSlideProgTagsContainer(SlideProgTagsContainer propTagsContainer)
    {
        this.propTagsContainer = propTagsContainer;
    }
    

    public SlideProgTagsContainer getSlideProgTagsContainer()
    {
        return propTagsContainer;
    }
    
    
    public void dispose()
    {
        super.dispose();
        if (_atomSet != null)
        {
            _atomSet.dispose();
            _atomSet = null;
        }
        if (_runs != null)
        {
            for (TextRun tr : _runs)
            {
                tr.dispose();
            }
            _runs = null;
        }
        if (_notes != null)
        {
            _notes.dispose();
            _notes = null;
        }
        
        if (_extendedAtomsSets != null)
        {
            for (ExtendedParaAtomsSet eps : _extendedAtomsSets)
            {
                eps.dispose();
            }
            _extendedAtomsSets = null;
        }
        
        if(ssSlideInfoAtom != null)
        {
            ssSlideInfoAtom.dispose();
            ssSlideInfoAtom = null;
        }
        
        if(propTagsContainer != null)
        {
            propTagsContainer.dispose();
            propTagsContainer = null;
        }
    }
    
    
    private int _slideNo;
    private SlideAtomsSet _atomSet;
    private TextRun[] _runs;
    private Notes _notes;
    private ExtendedParaAtomsSet[] _extendedAtomsSets;

    private SlideShowSlideInfoAtom ssSlideInfoAtom;

    private SlideProgTagsContainer propTagsContainer;
    
}
