

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.TextProp;
import com.document.allreader.allofficefilereader.fc.hslf.record.SlideAtom;



public final class TitleMaster extends MasterSheet
{
    private TextRun[] _runs;

    public TitleMaster(com.document.allreader.allofficefilereader.fc.hslf.record.Slide record, int sheetNo)
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


    public TextProp getStyleAttribute(int txtype, int level, String name, boolean isCharacter)
    {
        MasterSheet master = getMasterSheet();
        return master == null ? null : master.getStyleAttribute(txtype, level, name, isCharacter);
    }

    public MasterSheet getMasterSheet()
    {
        SlideMaster[] master = getSlideShow().getSlidesMasters();
        SlideAtom sa = ((com.document.allreader.allofficefilereader.fc.hslf.record.Slide)getSheetContainer()).getSlideAtom();
        int masterId = sa.getMasterID();
        for (int i = 0; i < master.length; i++)
        {
            if (masterId == master[i]._getSheetNumber())
                return master[i];
        }
        return null;
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
    }
}
