

package com.document.allreader.allofficefilereader.fc.hslf.usermodel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.document.allreader.allofficefilereader.constant.MainConstant;
import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.hslf.HSLFSlideShow;
import com.document.allreader.allofficefilereader.fc.hslf.exceptions.CorruptPowerPointFileException;
import com.document.allreader.allofficefilereader.fc.hslf.model.HeadersFooters;
import com.document.allreader.allofficefilereader.fc.hslf.model.Hyperlink;
import com.document.allreader.allofficefilereader.fc.hslf.model.MovieShape;
import com.document.allreader.allofficefilereader.fc.hslf.model.Notes;
import com.document.allreader.allofficefilereader.fc.hslf.model.Slide;
import com.document.allreader.allofficefilereader.fc.hslf.model.SlideMaster;
import com.document.allreader.allofficefilereader.fc.hslf.model.TitleMaster;
import com.document.allreader.allofficefilereader.fc.hslf.record.Document;
import com.document.allreader.allofficefilereader.fc.hslf.record.DocumentAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExAviMovie;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExControl;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExHyperlink;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExHyperlinkAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExMCIMovie;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExObjList;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExObjListAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExOleObjAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExVideoContainer;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExtendedParagraphHeaderAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExtendedPresRuleContainer;
import com.document.allreader.allofficefilereader.fc.hslf.record.FontCollection;
import com.document.allreader.allofficefilereader.fc.hslf.record.HeadersFootersContainer;
import com.document.allreader.allofficefilereader.fc.hslf.record.PersistPtrHolder;
import com.document.allreader.allofficefilereader.fc.hslf.record.PositionDependentRecord;
import com.document.allreader.allofficefilereader.fc.hslf.record.PositionDependentRecordContainer;
import com.document.allreader.allofficefilereader.fc.hslf.record.Record;
import com.document.allreader.allofficefilereader.fc.hslf.record.RecordContainer;
import com.document.allreader.allofficefilereader.fc.hslf.record.RecordTypes;
import com.document.allreader.allofficefilereader.fc.hslf.record.SlideListWithText;
import com.document.allreader.allofficefilereader.fc.hslf.record.SlidePersistAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExtendedPresRuleContainer.ExtendedParaAtomsSet;
import com.document.allreader.allofficefilereader.fc.hslf.record.SlideListWithText.SlideAtomsSet;
import com.document.allreader.allofficefilereader.java.awt.Dimension;



public final class SlideShow
{


    public SlideShow(HSLFSlideShow hslfSlideShow)
    {
        this(hslfSlideShow, false);
    }

    public SlideShow(HSLFSlideShow hslfSlideShow, boolean isGetThumbnail)
    {

        _hslfSlideShow = hslfSlideShow;
        _records = _hslfSlideShow.getRecords();
        this.isGetThumbnail = isGetThumbnail;


        for (Record record : _records)
        {
            if (record instanceof RecordContainer)
            {
                RecordContainer.handleParentAwareRecords((RecordContainer)record);
            }
        }


        findMostRecentCoreRecords();

        buildSlidesAndNotes();
    }
    

    private void findMostRecentCoreRecords()
    {
         Hashtable<Integer, Integer> mostRecentByBytes = new Hashtable<Integer, Integer>();
        for (int i = 0; i < _records.length; i++)
        {
            if (_records[i] instanceof PersistPtrHolder)
            {
                PersistPtrHolder pph = (PersistPtrHolder)_records[i];

                int[] ids = pph.getKnownSlideIDs();
                for (int j = 0; j < ids.length; j++)
                {
                    Integer id = Integer.valueOf(ids[j]);
                    if (mostRecentByBytes.containsKey(id))
                    {
                        mostRecentByBytes.remove(id);
                    }
                }

               Hashtable<Integer, Integer> thisSetOfLocations = pph.getSlideLocationsLookup();
                for (int j = 0; j < ids.length; j++)
                {
                    Integer id = Integer.valueOf(ids[j]);
                    mostRecentByBytes.put(id, thisSetOfLocations.get(id));
                }
            }
        }

        _mostRecentCoreRecords = new Record[mostRecentByBytes.size()];

        _sheetIdToCoreRecordsLookup = new Hashtable<Integer, Integer>();
        int[] allIDs = new int[_mostRecentCoreRecords.length];
        Enumeration<Integer> ids = mostRecentByBytes.keys();
        for (int i = 0; i < allIDs.length; i++)
        {
            Integer id = ids.nextElement();
            allIDs[i] = id.intValue();
        }
        Arrays.sort(allIDs);
        for (int i = 0; i < allIDs.length; i++)
        {
            _sheetIdToCoreRecordsLookup.put(Integer.valueOf(allIDs[i]), Integer.valueOf(i));
        }

        for (int i = 0; i < _records.length; i++)
        {
            if (_records[i] instanceof PositionDependentRecord)
            {
                PositionDependentRecord pdr = (PositionDependentRecord)_records[i];
                Integer recordAt = Integer.valueOf(pdr.getLastOnDiskOffset());

                for (int j = 0; j < allIDs.length; j++)
                {
                    Integer thisID = Integer.valueOf(allIDs[j]);
                    Integer thatRecordAt = mostRecentByBytes.get(thisID);

                    if (thatRecordAt.equals(recordAt))
                    {
                        Integer storeAtI = _sheetIdToCoreRecordsLookup.get(thisID);
                        int storeAt = storeAtI.intValue();

                        if (pdr instanceof PositionDependentRecordContainer)
                        {
                            PositionDependentRecordContainer pdrc = (PositionDependentRecordContainer)_records[i];
                            pdrc.setSheetId(thisID.intValue());
                        }

                        _mostRecentCoreRecords[storeAt] = _records[i];
                    }
                }
            }
        }

        for (int i = 0; i < _mostRecentCoreRecords.length; i++)
        {
            if (_mostRecentCoreRecords[i] != null)
            {
                 if (_mostRecentCoreRecords[i].getRecordType() == RecordTypes.Document.typeID)
                {
                    _documentRecord = (Document)_mostRecentCoreRecords[i];
                    _fonts = _documentRecord.getEnvironment().getFontCollection();
                }
            }
            else
            {

            }
        }
    }


    private Record getCoreRecordForSAS(SlideAtomsSet sas)
    {
        SlidePersistAtom spa = sas.getSlidePersistAtom();
        int refID = spa.getRefID();
        return getCoreRecordForRefID(refID);
    }


    private Record getCoreRecordForRefID(int refID)
    {
        Integer coreRecordId = _sheetIdToCoreRecordsLookup.get(Integer.valueOf(refID));
        if (coreRecordId != null)
        {
            Record r = _mostRecentCoreRecords[coreRecordId.intValue()];
            return r;
        }

        return null;
    }


    private void buildSlidesAndNotes()
    {
        if (_documentRecord == null)
        {
            throw new CorruptPowerPointFileException(
                "The PowerPoint file didn't contain a Document Record in its PersistPtr blocks. It is probably corrupt.");
        }


        SlideListWithText masterSLWT = _documentRecord.getMasterSlideListWithText();
        SlideListWithText slidesSLWT = _documentRecord.getSlideSlideListWithText();
        SlideListWithText notesSLWT = _documentRecord.getNotesSlideListWithText();

        SlideAtomsSet[] masterSets = new SlideAtomsSet[0];
        if (masterSLWT != null)
        {
            masterSets = masterSLWT.getSlideAtomsSets();

            ArrayList<SlideMaster> mmr = new ArrayList<SlideMaster>();
            ArrayList<TitleMaster> tmr = new ArrayList<TitleMaster>();

            for (int i = 0; i < masterSets.length; i++)
            {
                Record r = getCoreRecordForSAS(masterSets[i]);
                SlideAtomsSet sas = masterSets[i];
                int sheetNo = sas.getSlidePersistAtom().getSlideIdentifier();
                if (r instanceof com.document.allreader.allofficefilereader.fc.hslf.record.Slide)
                {
                    TitleMaster master = new TitleMaster((com.document.allreader.allofficefilereader.fc.hslf.record.Slide)r, sheetNo);
                    master.setSlideShow(this);
                    tmr.add(master);
                }
                else if (r instanceof com.document.allreader.allofficefilereader.fc.hslf.record.MainMaster)
                {
                    SlideMaster master = new SlideMaster((com.document.allreader.allofficefilereader.fc.hslf.record.MainMaster)r, sheetNo);
                    master.setSlideShow(this);
                    mmr.add(master);
                }
            }

            _masters = new SlideMaster[mmr.size()];
            mmr.toArray(_masters);

            _titleMasters = new TitleMaster[tmr.size()];
            tmr.toArray(_titleMasters);
        }


        com.document.allreader.allofficefilereader.fc.hslf.record.Notes[] notesRecords;
        SlideAtomsSet[] notesSets = new SlideAtomsSet[0];
        Hashtable<Integer, Integer> slideIdToNotes = new Hashtable<Integer, Integer>();
        if (notesSLWT == null)
        {

            notesRecords = new com.document.allreader.allofficefilereader.fc.hslf.record.Notes[0];
        }
        else
        {
            notesSets = notesSLWT.getSlideAtomsSets();
            ArrayList<com.document.allreader.allofficefilereader.fc.hslf.record.Notes> notesRecordsL = new ArrayList<com.document.allreader.allofficefilereader.fc.hslf.record.Notes>();
            for (int i = 0; i < notesSets.length; i++)
            {
                Record r = getCoreRecordForSAS(notesSets[i]);

                if (r instanceof com.document.allreader.allofficefilereader.fc.hslf.record.Notes)
                {
                    com.document.allreader.allofficefilereader.fc.hslf.record.Notes notesRecord = (com.document.allreader.allofficefilereader.fc.hslf.record.Notes)r;
                    notesRecordsL.add(notesRecord);

                     SlidePersistAtom spa = notesSets[i].getSlidePersistAtom();
                    Integer slideId = Integer.valueOf(spa.getSlideIdentifier());
                    slideIdToNotes.put(slideId, Integer.valueOf(i));
                }

            }
            notesRecords = new com.document.allreader.allofficefilereader.fc.hslf.record.Notes[notesRecordsL.size()];
            notesRecords = notesRecordsL.toArray(notesRecords);
        }

        com.document.allreader.allofficefilereader.fc.hslf.record.Slide[] slidesRecords;
        SlideAtomsSet[] slidesSets = new SlideAtomsSet[0];
        if (slidesSLWT == null)
        {
            slidesRecords = new com.document.allreader.allofficefilereader.fc.hslf.record.Slide[0];
        }
        else
        {

            slidesSets = slidesSLWT.getSlideAtomsSets();
            slidesRecords = new com.document.allreader.allofficefilereader.fc.hslf.record.Slide[slidesSets.length];
            for (int i = 0; i < slidesSets.length; i++)
            {

                Record r = getCoreRecordForSAS(slidesSets[i]);

                if (r instanceof com.document.allreader.allofficefilereader.fc.hslf.record.Slide)
                {
                    slidesRecords[i] = (com.document.allreader.allofficefilereader.fc.hslf.record.Slide)r;
                }

            }
        }


        _notes = new Notes[isGetThumbnail ? Math.min(notesRecords.length, 1) : notesRecords.length];
        for (int i = 0; i < _notes.length; i++)
        {
            _notes[i] = new Notes(notesRecords[i]);
            _notes[i].setSlideShow(this);
        }

        ExtendedParaAtomsSet[] extendedParaAtomsSets = null;
        if (_documentRecord.getList() != null)
        {
            ExtendedPresRuleContainer extendedPresRule = _documentRecord.getList().getExtendedPresRuleContainer();
            if (extendedPresRule != null)
            {
                extendedParaAtomsSets = extendedPresRule.getExtendedParaAtomsSets();
            }
        }
        _slides = new Slide[isGetThumbnail ? 1 : slidesRecords.length];
        for (int i = 0; i < _slides.length; i++)
        {
            SlideAtomsSet sas = slidesSets[i];
            int slideIdentifier = sas.getSlidePersistAtom().getSlideIdentifier();
            
            //
            Vector<ExtendedParaAtomsSet> extendedSets = new Vector<ExtendedParaAtomsSet>();
            if (extendedParaAtomsSets != null)
            {
                for (int j = 0; j < extendedParaAtomsSets.length; j++)
                {
                    ExtendedParagraphHeaderAtom paraHeaderAtom = extendedParaAtomsSets[j].getExtendedParaHeaderAtom();
                    if (paraHeaderAtom != null && paraHeaderAtom.getRefSlideID() == slideIdentifier)
                    {
                        extendedSets.add(extendedParaAtomsSets[j]);
                    }
                }
            }
            ExtendedParaAtomsSet[] extendedAtoms = null;
            if (extendedSets.size() > 0)
            {
                extendedAtoms = extendedSets.toArray(new ExtendedParaAtomsSet[extendedSets.size()]);
            }


            Notes notes = null;

            int noteId = slidesRecords[i].getSlideAtom().getNotesID();
            if (noteId != 0)
            {
                Integer notesPos = (Integer)slideIdToNotes.get(Integer.valueOf(noteId));
                if (notesPos != null && notesPos.intValue() < _notes.length)
                    notes = _notes[notesPos.intValue()];
                  }


            _slides[i] = new Slide(slidesRecords[i], notes, sas, extendedAtoms, slideIdentifier, (i + 1));
            _slides[i].setSlideShow(this);
            

            _slides[i].setSlideShowSlideInfoAtom(slidesRecords[i].getSlideShowSlideInfoAtom());
            _slides[i].setSlideProgTagsContainer(slidesRecords[i].getSlideProgTagsContainer());
        }
    }


    public void write(OutputStream out) throws IOException
    {

    }


    public Record[] getMostRecentCoreRecords()
    {
        return _mostRecentCoreRecords;
    }


    public Slide[] getSlides()
    {
        return _slides;
    }


    public Notes[] getNotes()
    {
        return _notes;
    }


    public SlideMaster[] getSlidesMasters()
    {
        return _masters;
    }

    public TitleMaster[] getTitleMasters()
    {
        return _titleMasters;
    }


    public PictureData[] getPictureData()
    {
        return _hslfSlideShow.getPictures();
    }


    public ObjectData[] getEmbeddedObjects()
    {
        return _hslfSlideShow.getEmbeddedObjects();
    }


    public SoundData[] getSoundData()
    {
        return SoundData.find(_documentRecord);
    }


    public Dimension getPageSize()
    {
        DocumentAtom docatom = _documentRecord.getDocumentAtom();
        int pgx = (int)(docatom.getSlideSizeX() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI);
        int pgy = (int)(docatom.getSlideSizeY() * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI);
        return new Dimension(pgx, pgy);
    }

    public void setPageSize(Dimension pgsize)
    {
        DocumentAtom docatom = _documentRecord.getDocumentAtom();
        docatom.setSlideSizeX((long)(pgsize.width * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
        docatom.setSlideSizeY((long)(pgsize.height * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
    }


    protected FontCollection getFontCollection()
    {
        return _fonts;
    }


    public Document getDocumentRecord()
    {
        return _documentRecord;
    }


    public void reorderSlide(int oldSlideNumber, int newSlideNumber)
    {

        if (oldSlideNumber < 1 || newSlideNumber < 1)
        {
            throw new IllegalArgumentException("Old and new slide numbers must be greater than 0");
        }
        if (oldSlideNumber > _slides.length || newSlideNumber > _slides.length)
        {
            throw new IllegalArgumentException(
                "Old and new slide numbers must not exceed the number of slides (" + _slides.length
                    + ")");
        }


        SlideListWithText slwt = _documentRecord.getSlideSlideListWithText();
        SlideAtomsSet[] sas = slwt.getSlideAtomsSets();

        SlideAtomsSet tmp = sas[oldSlideNumber - 1];
        sas[oldSlideNumber - 1] = sas[newSlideNumber - 1];
        sas[newSlideNumber - 1] = tmp;

        ArrayList<Record> lst = new ArrayList<Record>();
        for (int i = 0; i < sas.length; i++)
        {
            lst.add(sas[i].getSlidePersistAtom());
            Record[] r = sas[i].getSlideRecords();
            for (int j = 0; j < r.length; j++)
            {
                lst.add(r[j]);
            }
            _slides[i].setSlideNumber(i + 1);
        }
        Record[] r = lst.toArray(new Record[lst.size()]);
        slwt.setChildRecord(r);
    }


    public Slide removeSlide(int index)
    {
        int lastSlideIdx = _slides.length - 1;
        if (index < 0 || index > lastSlideIdx)
        {
            throw new IllegalArgumentException("Slide index (" + index + ") is out of range (0.."
                + lastSlideIdx + ")");
        }

        SlideListWithText slwt = _documentRecord.getSlideSlideListWithText();
        SlideAtomsSet[] sas = slwt.getSlideAtomsSets();

        Slide removedSlide = null;
        ArrayList<Record> records = new ArrayList<Record>();
        ArrayList<SlideAtomsSet> sa = new ArrayList<SlideAtomsSet>();
        ArrayList<Slide> sl = new ArrayList<Slide>();

        ArrayList<Notes> nt = new ArrayList<Notes>();
        for (Notes notes : getNotes())
            nt.add(notes);

        for (int i = 0, num = 0; i < _slides.length; i++)
        {
            if (i != index)
            {
                sl.add(_slides[i]);
                sa.add(sas[i]);
                _slides[i].setSlideNumber(num++);
                records.add(sas[i].getSlidePersistAtom());
                records.addAll(Arrays.asList(sas[i].getSlideRecords()));
            }
            else
            {
                removedSlide = _slides[i];
                nt.remove(_slides[i].getNotesSheet());
            }
        }
        if (sa.size() == 0)
        {
            _documentRecord.removeSlideListWithText(slwt);
        }
        else
        {
            slwt.setSlideAtomsSets(sa.toArray(new SlideAtomsSet[sa.size()]));
            slwt.setChildRecord(records.toArray(new Record[records.size()]));
        }
        _slides = sl.toArray(new Slide[sl.size()]);

        if (removedSlide != null)
        {
            int notesId = removedSlide.getSlideRecord().getSlideAtom().getNotesID();
            if (notesId != 0)
            {
                SlideListWithText nslwt = _documentRecord.getNotesSlideListWithText();
                records = new ArrayList<Record>();
                ArrayList<SlideAtomsSet> na = new ArrayList<SlideAtomsSet>();
                for (SlideAtomsSet ns : nslwt.getSlideAtomsSets())
                {
                    if (ns.getSlidePersistAtom().getSlideIdentifier() != notesId)
                    {
                        na.add(ns);
                        records.add(ns.getSlidePersistAtom());
                        if (ns.getSlideRecords() != null)
                            records.addAll(Arrays.asList(ns.getSlideRecords()));
                    }
                }
                if (na.size() == 0)
                {
                    _documentRecord.removeSlideListWithText(nslwt);
                }
                else
                {
                    nslwt.setSlideAtomsSets(na.toArray(new SlideAtomsSet[na.size()]));
                    nslwt.setChildRecord(records.toArray(new Record[records.size()]));
                }

            }
        }
        _notes = nt.toArray(new Notes[nt.size()]);

        return removedSlide;
    }




    public int getNumberOfFonts()
    {
        return getDocumentRecord().getEnvironment().getFontCollection().getNumberOfFonts();
    }


    public HeadersFooters getSlideHeadersFooters()
    {

        String tag = getSlidesMasters()[0].getProgrammableTag();
        boolean ppt2007 = "___PPT12".equals(tag);

        HeadersFootersContainer hdd = null;
        Record[] ch = _documentRecord.getChildRecords();
        for (int i = 0; i < ch.length; i++)
        {
            if (ch[i] instanceof HeadersFootersContainer
                && ((HeadersFootersContainer)ch[i]).getOptions() == HeadersFootersContainer.SlideHeadersFootersContainer)
            {
                hdd = (HeadersFootersContainer)ch[i];
                break;
            }
        }
        boolean newRecord = false;
        if (hdd == null)
        {
            hdd = new HeadersFootersContainer(HeadersFootersContainer.SlideHeadersFootersContainer);
            newRecord = true;
        }
        return new HeadersFooters(hdd, this, newRecord, ppt2007);
    }

    public HeadersFooters getNotesHeadersFooters()
    {
        String tag = getSlidesMasters()[0].getProgrammableTag();
        boolean ppt2007 = "___PPT12".equals(tag);

        HeadersFootersContainer hdd = null;
        Record[] ch = _documentRecord.getChildRecords();
        for (int i = 0; i < ch.length; i++)
        {
            if (ch[i] instanceof HeadersFootersContainer
                && ((HeadersFootersContainer)ch[i]).getOptions() == HeadersFootersContainer.NotesHeadersFootersContainer)
            {
                hdd = (HeadersFootersContainer)ch[i];
                break;
            }
        }
        boolean newRecord = false;
        if (hdd == null)
        {
            hdd = new HeadersFootersContainer(HeadersFootersContainer.NotesHeadersFootersContainer);
            newRecord = true;
        }
        if (ppt2007 && _notes.length > 0)
        {
            return new HeadersFooters(hdd, _notes[0], newRecord, ppt2007);
        }
        return new HeadersFooters(hdd, this, newRecord, ppt2007);
    }


    public int addMovie(String path, int type)
    {
        ExObjList lst = (ExObjList)_documentRecord.findFirstOfType(RecordTypes.ExObjList.typeID);
        if (lst == null)
        {
            lst = new ExObjList();
            _documentRecord.addChildAfter(lst, _documentRecord.getDocumentAtom());
        }

        ExObjListAtom objAtom = lst.getExObjListAtom();
        // increment the object ID seed
        int objectId = (int)objAtom.getObjectIDSeed() + 1;
        objAtom.setObjectIDSeed(objectId);
        ExMCIMovie mci;
        switch (type)
        {
            case MovieShape.MOVIE_MPEG:
                mci = new ExMCIMovie();
                break;
            case MovieShape.MOVIE_AVI:
                mci = new ExAviMovie();
                break;
            default:
                throw new IllegalArgumentException("Unsupported Movie: " + type);
        }

        lst.appendChildRecord(mci);
        ExVideoContainer exVideo = mci.getExVideo();
        exVideo.getExMediaAtom().setObjectId(objectId);
        exVideo.getExMediaAtom().setMask(0xE80000);
        exVideo.getPathAtom().setText(path);
        return objectId;
    }



    public int addControl(String name, String progId)
    {
        ExObjList lst = (ExObjList)_documentRecord.findFirstOfType(RecordTypes.ExObjList.typeID);
        if (lst == null)
        {
            lst = new ExObjList();
            _documentRecord.addChildAfter(lst, _documentRecord.getDocumentAtom());
        }
        ExObjListAtom objAtom = lst.getExObjListAtom();
        // increment the object ID seed
        int objectId = (int)objAtom.getObjectIDSeed() + 1;
        objAtom.setObjectIDSeed(objectId);
        ExControl ctrl = new ExControl();
        ExOleObjAtom oleObj = ctrl.getExOleObjAtom();
        oleObj.setObjID(objectId);
        oleObj.setDrawAspect(ExOleObjAtom.DRAW_ASPECT_VISIBLE);
        oleObj.setType(ExOleObjAtom.TYPE_CONTROL);
        oleObj.setSubType(ExOleObjAtom.SUBTYPE_DEFAULT);

        ctrl.setProgId(progId);
        ctrl.setMenuName(name);
        ctrl.setClipboardName(name);
        lst.addChildAfter(ctrl, objAtom);

        return objectId;
    }


    public int addHyperlink(Hyperlink link)
    {
        ExObjList lst = (ExObjList)_documentRecord.findFirstOfType(RecordTypes.ExObjList.typeID);
        if (lst == null)
        {
            lst = new ExObjList();
            _documentRecord.addChildAfter(lst, _documentRecord.getDocumentAtom());
        }
        ExObjListAtom objAtom = lst.getExObjListAtom();
        // increment the object ID seed
        int objectId = (int)objAtom.getObjectIDSeed() + 1;
        objAtom.setObjectIDSeed(objectId);

        ExHyperlink ctrl = new ExHyperlink();
        ExHyperlinkAtom obj = ctrl.getExHyperlinkAtom();
        obj.setNumber(objectId);
        ctrl.setLinkURL(link.getAddress());
        ctrl.setLinkTitle(link.getTitle());
        lst.addChildAfter(ctrl, objAtom);
        link.setId(objectId);

        return objectId;
    }
    

    public int getSlideCount()
    {
        return _slides.length;
    }
    

    public Slide getSlide(int index)
    {
        if (index < 0 || index >= getSlideCount())
        {
            return null;
        }
        return _slides[index];
    }
    

    public void dispose()
    {
        if (_hslfSlideShow != null)
        {
            _hslfSlideShow.dispose();
            _hslfSlideShow = null;
        }
        if (_records != null)
        {
            for (Record rd : _records)
            {
                rd.dispose();
            }
            _records = null;
        }
        if (_mostRecentCoreRecords != null)
        {
            for (Record rd : _mostRecentCoreRecords)
            {
                rd.dispose();
            }
            _mostRecentCoreRecords = null;
        }
        if (_sheetIdToCoreRecordsLookup != null)
        {
            _sheetIdToCoreRecordsLookup.clear();
            _sheetIdToCoreRecordsLookup = null;
        }
        if (_documentRecord != null)
        {
            _documentRecord.dispose();
            _documentRecord = null;
        }
        
        if (_masters != null)
        {
            for (SlideMaster sm : _masters)
            {
                sm.dispose();
            }
            _masters = null;
        }
        
        if (_titleMasters != null)
        {
            for (TitleMaster tm : _titleMasters)
            {
                tm.dispose();
            }
            _titleMasters = null;
        }
        
        if (_slides != null)
        {
            for (Slide slide : _slides)
            {
                slide.dispose();
            }
            _slides = null;
        }
        
        if (_notes != null)
        {
            for (Notes note : _notes)
            {
                note.dispose();
            }
            _notes = null;
        }
        
        if (_fonts != null)
        {
            _fonts.dispose();
            _fonts = null;
        }
        
    }

    private HSLFSlideShow _hslfSlideShow;

    private Record[] _records;

    private Record[] _mostRecentCoreRecords;
    private Hashtable<Integer, Integer> _sheetIdToCoreRecordsLookup;

    private Document _documentRecord;

    private SlideMaster[] _masters;
    private TitleMaster[] _titleMasters;
    private Slide[] _slides;
    private Notes[] _notes;
    private FontCollection _fonts;
    
        private boolean isGetThumbnail;
}
