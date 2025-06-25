

package com.document.allreader.allofficefilereader.fc.hslf.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.ddf.EscherBinaryTagRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherDgRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherDggRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherRecord;
import com.document.allreader.allofficefilereader.fc.hslf.record.CString;
import com.document.allreader.allofficefilereader.fc.hslf.record.ColorSchemeAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.EscherTextboxWrapper;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExtendedParagraphAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.OEPlaceholderAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.PPDrawing;
import com.document.allreader.allofficefilereader.fc.hslf.record.Record;
import com.document.allreader.allofficefilereader.fc.hslf.record.RecordContainer;
import com.document.allreader.allofficefilereader.fc.hslf.record.RecordTypes;
import com.document.allreader.allofficefilereader.fc.hslf.record.RoundTripHFPlaceholder12;
import com.document.allreader.allofficefilereader.fc.hslf.record.SheetContainer;
import com.document.allreader.allofficefilereader.fc.hslf.record.StyleTextPropAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.TextBytesAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.TextCharsAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.TextHeaderAtom;
import com.document.allreader.allofficefilereader.fc.hslf.usermodel.SlideShow;




public abstract class Sheet
{

    private int _sheetNo;

    public Sheet(SheetContainer container, int sheetNo)
    {
        _container = container;
        _sheetNo = sheetNo;
    }

    public abstract TextRun[] getTextRuns();


    public int _getSheetRefId()
    {
        return _container.getSheetId();
    }


    public int _getSheetNumber()
    {
        return _sheetNo;
    }


    protected PPDrawing getPPDrawing()
    {
        return _container.getPPDrawing();
    }

    public SlideShow getSlideShow()
    {
        return _slideShow;
    }


    public SheetContainer getSheetContainer()
    {
        return _container;
    }


    public void setSlideShow(SlideShow ss)
    {
        _slideShow = ss;
        TextRun[] trs = getTextRuns();
        if (trs != null)
        {
            for (int i = 0; i < trs.length; i++)
            {
                trs[i].supplySlideShow(_slideShow);
            }
        }
    }

    public static TextRun[] findTextRuns(PPDrawing ppdrawing)
    {
        Vector runsV = new Vector();
        EscherTextboxWrapper[] wrappers = ppdrawing.getTextboxWrappers();
        for (int i = 0; i < wrappers.length; i++)
        {
            int s1 = runsV.size();

            RecordContainer.handleParentAwareRecords(wrappers[i]);
            findTextRuns(wrappers[i].getChildRecords(), runsV);
            int s2 = runsV.size();
            if (s2 != s1)
            {
                TextRun t = (TextRun)runsV.get(runsV.size() - 1);
                t.setShapeId(wrappers[i].getShapeId());
                boolean find = false;
                for (int j = i - 1; j >= 0; j--)
                {
                    if (wrappers[j].getShapeId() == EscherBinaryTagRecord.RECORD_ID)
                    {
                        Record[] records = wrappers[j].getChildRecords();
                        for (int n = 0; n < records.length; n++)
                        {
                            if (records[0] instanceof ExtendedParagraphAtom)
                            {
                                find = true;
                                t.setExtendedParagraphAtom((ExtendedParagraphAtom)records[n]);
                                break;
                            }
                        }
                    }
                    if (find)
                    {
                        break;
                    }
                }
            }
        }
        TextRun[] runs = new TextRun[runsV.size()];
        for (int i = 0; i < runs.length; i++)
        {
            runs[i] = (TextRun)runsV.get(i);
        }
        return runs;
    }


    protected static void findTextRuns(Record[] records, Vector found)
    {

        for (int i = 0, slwtIndex = 0; i < (records.length - 1); i++)
        {
            if (records[i] instanceof TextHeaderAtom)
            {
                TextRun trun = null;
                TextHeaderAtom tha = (TextHeaderAtom)records[i];
                StyleTextPropAtom stpa = null;

                if (i < (records.length - 2))
                {
                    if (records[i + 2] instanceof StyleTextPropAtom)
                    {
                        stpa = (StyleTextPropAtom)records[i + 2];
                    }
                }

                if (records[i + 1] instanceof TextCharsAtom)
                {
                    TextCharsAtom tca = (TextCharsAtom)records[i + 1];
                    trun = new TextRun(tha, tca, stpa);
                }
                else if (records[i + 1] instanceof TextBytesAtom)
                {
                    TextBytesAtom tba = (TextBytesAtom)records[i + 1];
                    trun = new TextRun(tha, tba, stpa);
                }
                else if (records[i + 1].getRecordType() == 4001l)
                {

                }
                else if (records[i + 1].getRecordType() == 4010l) {
                }
                else
                {
                    System.err
                        .println("Found a TextHeaderAtom not followed by a TextBytesAtom or TextCharsAtom: Followed by "
                            + records[i + 1].getRecordType());
                }

                if (trun != null)
                {
                    ArrayList lst = new ArrayList();
                    for (int j = i; j < records.length; j++)
                    {
                        if (j > i && records[j] instanceof TextHeaderAtom)
                            break;
                        lst.add(records[j]);
                    }
                    Record[] recs = new Record[lst.size()];
                    lst.toArray(recs);
                    trun._records = recs;
                    trun.setIndex(slwtIndex);

                    found.add(trun);
                    i++;
                }
                else
                {


                }
                slwtIndex++;
            }
        }
    }


    public Shape[] getShapes()
    {
        if (_shapes != null)
        {
            return _shapes;
        }
        PPDrawing ppdrawing = getPPDrawing();

        EscherContainerRecord dg = (EscherContainerRecord)ppdrawing.getEscherRecords()[0];
        EscherContainerRecord spgr = null;

        for (Iterator<EscherRecord> it = dg.getChildIterator(); it.hasNext();)
        {
            EscherRecord rec = it.next();
            if (rec.getRecordId() == EscherContainerRecord.SPGR_CONTAINER)
            {
                spgr = (EscherContainerRecord)rec;
                break;
            }
        }
        if (spgr == null)
        {
            throw new IllegalStateException("spgr not found");
        }

        List<Shape> shapes = new ArrayList<Shape>();
        Iterator<EscherRecord> it = spgr.getChildIterator();
        if (it.hasNext())
        {
            // skip first item
            it.next();
        }
        for (; it.hasNext();)
        {
            EscherContainerRecord sp = (EscherContainerRecord)it.next();
            Shape sh = ShapeFactory.createShape(sp, null);
            sh.setSheet(this);
            shapes.add(sh);
        }
        _shapes = new Shape[shapes.size()];
        shapes.toArray(_shapes);
        return _shapes;
    }


    public void addShape(Shape shape)
    {
        PPDrawing ppdrawing = getPPDrawing();

        EscherContainerRecord dgContainer = (EscherContainerRecord)ppdrawing.getEscherRecords()[0];
        EscherContainerRecord spgr = (EscherContainerRecord)ShapeKit.getEscherChild(dgContainer,
            EscherContainerRecord.SPGR_CONTAINER);
        spgr.addChildRecord(shape.getSpContainer());

        shape.setSheet(this);
        shape.setShapeId(allocateShapeId());
        shape.afterInsert(this);
    }

    public int allocateShapeId()
    {
        EscherDggRecord dgg = _slideShow.getDocumentRecord().getPPDrawingGroup()
            .getEscherDggRecord();
        EscherDgRecord dg = _container.getPPDrawing().getEscherDgRecord();

        dgg.setNumShapesSaved(dgg.getNumShapesSaved() + 1);


        for (int i = 0; i < dgg.getFileIdClusters().length; i++)
        {
            EscherDggRecord.FileIdCluster c = dgg.getFileIdClusters()[i];
            if (c.getDrawingGroupId() == dg.getDrawingGroupId() && c.getNumShapeIdsUsed() != 1024)
            {
                int result = c.getNumShapeIdsUsed() + (1024 * (i + 1));
                c.incrementShapeId();
                dg.setNumShapes(dg.getNumShapes() + 1);
                dg.setLastMSOSPID(result);
                if (result >= dgg.getShapeIdMax())
                    dgg.setShapeIdMax(result + 1);
                return result;
            }
        }

        dgg.addCluster(dg.getDrawingGroupId(), 0, false);
        dgg.getFileIdClusters()[dgg.getFileIdClusters().length - 1].incrementShapeId();
        dg.setNumShapes(dg.getNumShapes() + 1);
        int result = (1024 * dgg.getFileIdClusters().length);
        dg.setLastMSOSPID(result);
        if (result >= dgg.getShapeIdMax())
            dgg.setShapeIdMax(result + 1);
        return result;
    }

    public boolean removeShape(Shape shape)
    {
        PPDrawing ppdrawing = getPPDrawing();

        EscherContainerRecord dg = (EscherContainerRecord)ppdrawing.getEscherRecords()[0];
        EscherContainerRecord spgr = null;

        for (Iterator<EscherRecord> it = dg.getChildIterator(); it.hasNext();)
        {
            EscherRecord rec = it.next();
            if (rec.getRecordId() == EscherContainerRecord.SPGR_CONTAINER)
            {
                spgr = (EscherContainerRecord)rec;
                break;
            }
        }
        if (spgr == null)
        {
            return false;
        }

        List<EscherRecord> lst = spgr.getChildRecords();
        boolean result = lst.remove(shape.getSpContainer());
        spgr.setChildRecords(lst);
        return result;
    }


    public void onCreate()
    {

    }


    public abstract MasterSheet getMasterSheet();


    public ColorSchemeAtom getColorScheme()
    {
        return _container.getColorScheme();
    }


    public Background getBackground()
    {
        if (_background == null)
        {
            PPDrawing ppdrawing = getPPDrawing();

            EscherContainerRecord dg = (EscherContainerRecord)ppdrawing.getEscherRecords()[0];
            EscherContainerRecord spContainer = null;

            for (Iterator<EscherRecord> it = dg.getChildIterator(); it.hasNext();)
            {
                EscherRecord rec = it.next();
                if (rec.getRecordId() == EscherContainerRecord.SP_CONTAINER)
                {
                    spContainer = (EscherContainerRecord)rec;
                    break;
                }
            }
            if (spContainer != null)
            {
                _background = new Background(spContainer, null);
                _background.setSheet(this);
            }
        }
        return _background;
    }


    protected void onAddTextShape(TextShape shape)
    {

    }

    public TextShape getPlaceholderByTextType(int type)
    {
        Shape[] shape = getShapes();
        for (int i = 0; i < shape.length; i++)
        {
            if (shape[i] instanceof TextShape)
            {
                TextShape tx = (TextShape)shape[i];
                TextRun run = tx.getTextRun();
                if (run != null && run.getRunType() == type)
                {
                    return tx;
                }
            }
        }
        return null;
    }


    public TextShape getPlaceholder(int type)
    {
        Shape[] shape = getShapes();
        for (int i = 0; i < shape.length; i++)
        {
            if (shape[i] instanceof TextShape)
            {
                TextShape tx = (TextShape)shape[i];
                int placeholderId = 0;
                OEPlaceholderAtom oep = tx.getPlaceholderAtom();
                if (oep != null)
                {
                    placeholderId = oep.getPlaceholderId();
                }
                else
                {

                    RoundTripHFPlaceholder12 hldr = (RoundTripHFPlaceholder12)tx
                        .getClientDataRecord(RecordTypes.RoundTripHFPlaceholder12.typeID);
                    if (hldr != null)
                        placeholderId = hldr.getPlaceholderId();
                }
                if (placeholderId == type)
                {
                    return tx;
                }
            }
        }
        return null;
    }


    public String getProgrammableTag()
    {
        String tag = null;
        RecordContainer progTags = (RecordContainer)getSheetContainer().findFirstOfType(
            RecordTypes.SlideProgTagsContainer.typeID);
        if (progTags != null)
        {
            RecordContainer progBinaryTag = (RecordContainer)progTags
                .findFirstOfType(RecordTypes.SlideProgBinaryTagContainer.typeID);
            if (progBinaryTag != null)
            {
                CString binaryTag = (CString)progBinaryTag
                    .findFirstOfType(RecordTypes.CString.typeID);
                if (binaryTag != null)
                    tag = binaryTag.getText();
            }
        }

        return tag;

    }
    
    

    public void dispose()
    {
        _slideShow = null;
        if (_background != null)
        {
            _background.dispose();
            _background = null;
        }
        if (_shapes != null)
        {
            for (Shape sp : _shapes)
            {
                sp.dispose();
            }
            _shapes = null;
        }
        if (_container != null)
        {
            _container.dispose();
            _container = null;
        }
            
    }
    

    private SlideShow _slideShow;


    private Background _background;


    private Shape[] _shapes;
    

    private SheetContainer _container;

}
