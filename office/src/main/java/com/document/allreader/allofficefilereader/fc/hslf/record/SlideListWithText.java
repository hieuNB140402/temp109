

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class SlideListWithText extends RecordContainer
{


    public static final int SLIDES = 0;

    public static final int MASTER = 1;

    public static final int NOTES = 2;

    private byte[] _header;
    private static long _type = 4080;

    private SlideAtomsSet[] slideAtomsSets;



    public SlideListWithText()
    {
        _header = new byte[8];
        LittleEndian.putUShort(_header, 0, 15);
        LittleEndian.putUShort(_header, 2, (int)_type);
        LittleEndian.putInt(_header, 4, 0);

        _children = new Record[0];
        slideAtomsSets = new SlideAtomsSet[0];
    }



    public int getInstance()
    {
        return LittleEndian.getShort(_header, 0) >> 4;
    }

    public void setInstance(int inst)
    {
        LittleEndian.putShort(_header, (short)((inst << 4) | 0xF));
    }


    public SlideAtomsSet[] getSlideAtomsSets()
    {
        return slideAtomsSets;
    }


    public void setSlideAtomsSets(SlideAtomsSet[] sas)
    {
        slideAtomsSets = sas;
    }


    public long getRecordType()
    {
        return _type;
    }



    public class SlideAtomsSet
    {
        private SlidePersistAtom slidePersistAtom;
        private Record[] slideRecords;

        public SlidePersistAtom getSlidePersistAtom()
        {
            return slidePersistAtom;
        }

        public Record[] getSlideRecords()
        {
            return slideRecords;
        }

         public SlideAtomsSet(SlidePersistAtom s, Record[] r)
        {
            slidePersistAtom = s;
            slideRecords = r;
        }
        

        public void dispose()
        {
            if (slidePersistAtom != null)
            {
                slidePersistAtom.dispose();
            }
            if (slideRecords != null)
            {
                for (Record rec : slideRecords)
                {
                    rec.dispose();
                }
                slideRecords = null;
            }
        }
    }
    
    
    public void dispose()
    {
        _header = null;
        if (slideAtomsSets != null)
        {
            for (SlideAtomsSet sas : slideAtomsSets)
            {
                sas.dispose();
            }
            slideAtomsSets = null;
        }
    }
}
