

package com.document.allreader.allofficefilereader.fc.hslf.model;

public final class Notes extends Sheet
{
    private TextRun[] _runs;

    public Notes(com.document.allreader.allofficefilereader.fc.hslf.record.Notes notes)
    {
        super(notes, notes.getNotesAtom().getSlideID());


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
