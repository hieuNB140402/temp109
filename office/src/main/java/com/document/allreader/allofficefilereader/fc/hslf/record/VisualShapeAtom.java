
package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.Hashtable;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public class VisualShapeAtom extends PositionDependentRecordAtom
{
    public static final int TVET_Shape = 0;         //Applies to the shape and all its text.
    public static final int TVET_Page = 1;          //Applies to the slide.
    public static final int TVET_TextRange = 2;     //Applies to a specified range of text of the shape.
    public static final int TVET_Audio = 3;         //Applies to the audio of the shape.
    public static final int TVET_Video = 4;         //Applies to the video of the shape.
    public static final int TVET_ChartElement = 5;  //Applies to the elements of the chart.
    public static final int TVET_ShapeOnly = 6;     //Applies to the shape but not its text.
    public static final int TVET_AllTextRange = 8;  //Applies to all text of the shape.
    
    public static final int ET_ShapeType = 1;
    public static final int ET_SoundType = 2;
    
    
    private byte[] _header;
    public static long RECORD_ID = 0x2AFB;
   private int animType;
    private int refType;
    private int shapeIdRef;
    

    private int data1;
    private int data2;
    

    public long getRecordType()
    {
        return RECORD_ID;
    }
    

    protected VisualShapeAtom(byte[] source, int start, int len)
    {
        if(len < 28)
        {
            len = 28;
        }

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);
        
        animType = LittleEndian.getInt(source, start + 8);
        refType = LittleEndian.getInt(source, start + 12);
        shapeIdRef = LittleEndian.getInt(source, start + 16);
        
        data1 = LittleEndian.getInt(source, start + 20);
        data2 = LittleEndian.getInt(source, start + 24);
    }
    

    public int getTargetElementType()
    {
        return animType;
    }
    

    public int getTargetElementID()
    {
        return shapeIdRef;
    }
    
    public int getData1()
    {
        return data1;        
    }
    
    public int getData2()
    {
        return data2;        
    }
    

    public void updateOtherRecordReferences(Hashtable<Integer,Integer> oldToNewReferencesLookup)
    {
       
    }
    

    public void dispose()
    {
        _header = null;
    }
}
