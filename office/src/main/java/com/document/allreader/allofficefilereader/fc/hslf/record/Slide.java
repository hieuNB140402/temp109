

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class Slide extends SheetContainer
{
    private byte[] _header;
    private static long _type = 1006l;


    private SlideAtom slideAtom;
    private PPDrawing ppDrawing;
    private ColorSchemeAtom _colorScheme;
    private SlideShowSlideInfoAtom ssSlideInfoAtom;
    private SlideProgTagsContainer propTagsContainer;
    private HeadersFootersContainer headersFootersContainer;

    public SlideAtom getSlideAtom()
    {
        return slideAtom;
    }

    public PPDrawing getPPDrawing()
    {
        return ppDrawing;
    }
    
    public HeadersFootersContainer getHeadersFootersContainer()
    {
        return headersFootersContainer;
    }


    protected Slide(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);


        _children = Record.findChildRecords(source, start + 8, len - 8);


        for (int i = 0; i < _children.length; i++)
        {
            if (_children[i] instanceof SlideAtom)
            {
                slideAtom = (SlideAtom)_children[i];
            }
            else if (_children[i] instanceof PPDrawing)
            {
                ppDrawing = (PPDrawing)_children[i];
            }
            else if(_children[i] instanceof SlideShowSlideInfoAtom)
            {
                ssSlideInfoAtom = (SlideShowSlideInfoAtom)_children[i];
            }
            else if(_children[i] instanceof SlideProgTagsContainer)
            {
                propTagsContainer = (SlideProgTagsContainer)_children[i];
            }
            else if (_children[i] instanceof HeadersFootersContainer)
            {
                headersFootersContainer = (HeadersFootersContainer)_children[i];
            }
            
            if (ppDrawing != null && _children[i] instanceof ColorSchemeAtom)
            {
                _colorScheme = (ColorSchemeAtom)_children[i];
            }
        }
    }


    public Slide()
    {
        _header = new byte[8];
        LittleEndian.putUShort(_header, 0, 15);
        LittleEndian.putUShort(_header, 2, (int)_type);
        LittleEndian.putInt(_header, 4, 0);

        slideAtom = new SlideAtom();
        ppDrawing = new PPDrawing();

        ColorSchemeAtom colorAtom = new ColorSchemeAtom();

        _children = new Record[]{slideAtom, ppDrawing, colorAtom};
    }

    public long getRecordType()
    {
        return _type;
    }

    public ColorSchemeAtom getColorScheme()
    {
        return _colorScheme;
    }
    

    public SlideShowSlideInfoAtom getSlideShowSlideInfoAtom()
    {
        return ssSlideInfoAtom;
    }
    

    public SlideProgTagsContainer getSlideProgTagsContainer()
    {
        return propTagsContainer;
    }
    

    public void dispose()
    {
        super.dispose();
        _header = null;
        if (slideAtom != null)
        {
            slideAtom.dispose();
            slideAtom = null;
        }
        if (ppDrawing != null)
        {
            ppDrawing.dispose();
            ppDrawing = null;
        }
        if (_colorScheme != null)
        {
            _colorScheme.dispose();
            _colorScheme = null;
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
}
