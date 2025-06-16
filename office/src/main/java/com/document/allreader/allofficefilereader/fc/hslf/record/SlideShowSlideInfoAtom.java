
package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.Hashtable;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public class SlideShowSlideInfoAtom extends PositionDependentRecordAtom
{
    private byte[] _header;
    private static long _type = 0x03F9;

    private int slideTime;

    private int soundIdRef;
    

    private byte effectDirection;
    

    private byte effectType;
    
   private boolean fManualAdvance;

    private boolean reserved1;

    private boolean fHidden;

    private boolean reserved2;

    private boolean fSound;

    private boolean reserved3;

    private boolean fLoopSound;

    private boolean reserved4;

    private boolean fStopSound;

    private boolean reserved5;

    private boolean fAutoAdvance;

    private boolean reserved6;

    private boolean fCursorVisible;

    private byte reserved7;

    private byte speed;

    private byte[] unused;



    protected SlideShowSlideInfoAtom(byte[] source, int start, int len)
    {

        if(len < 24) { len = 24; }

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);
        
        slideTime = LittleEndian.getInt(source, start + 8);
        soundIdRef = LittleEndian.getInt(source, start + 12);
        effectDirection = source[start + 16];
        effectType = source[start + 17];
        
        speed = source[start + 20];
    }
    
    public boolean isValidateTransition()
    {
    	switch(effectType)
    	{
	    	case 0:
	    		return effectDirection >= 0 && effectDirection <= 1;
	    	case 1:
	    		return true;
	    	case 2:
	    	case 3:
	    		return effectDirection >= 0 && effectDirection <= 1;
	    	case 4:
	    		return effectDirection >= 0 && effectDirection <= 7;
	    	case 5:
	    		return effectDirection == 0;
	    	case 6:
	    		return effectDirection == 0;
	    	case 7:
	    		return effectDirection >= 0 && effectDirection <= 7;
	    	case 8:
	    		return effectDirection >= 0 && effectDirection <= 1;
	    	case 9:
	    		return effectDirection >= 4 && effectDirection <= 7;
	    	case 10:
	    		return effectDirection >= 0 && effectDirection <= 3;
	    	case 11:
	    		return effectDirection >= 0 && effectDirection <= 1;
	    	case 13:
	    		return effectDirection >= 0 && effectDirection <= 3;
	    	case 17:
	    	case 18:
	    	case 19:
	    		return effectDirection == 0;
	    	case 20:
	    		return effectDirection >= 0 && effectDirection <= 3;
	    	case 21:
	    		return effectDirection >= 0 && effectDirection <= 1;
	    	case 22:
	    	case 23:
	    		return effectDirection == 0;
	    	case 26:
	    		return (effectDirection >= 1 && effectDirection <= 4) || (effectDirection == 8);
	    	case 27:
	    		return effectDirection == 0;
    	}
    	
    	return false;
    }
    

    public long getRecordType()
    { 
        return _type; 
    }
    

    public void updateOtherRecordReferences(Hashtable<Integer,Integer> oldToNewReferencesLookup)
    {
       
    }
    

    public void dispose()
    {
        _header = null;
    }
}
