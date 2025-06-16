

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.ArrayList;


public final class MainMaster extends SheetContainer
{
    private byte[] _header;
    private static long _type = 1016;

    public SlideAtom getSlideAtom()
    {
        return slideAtom;
    }


    public PPDrawing getPPDrawing()
    {
        return ppDrawing;
    }

    public TxMasterStyleAtom[] getTxMasterStyleAtoms()
    {
        return txmasters;
    }

    public ColorSchemeAtom[] getColorSchemeAtoms()
    {
        return clrscheme;
    }

    protected MainMaster(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);


        _children = Record.findChildRecords(source, start + 8, len - 8);

        ArrayList<TxMasterStyleAtom> tx = new ArrayList<TxMasterStyleAtom>();
        ArrayList<ColorSchemeAtom> clr = new ArrayList<ColorSchemeAtom>();

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
            else if (_children[i] instanceof TxMasterStyleAtom)
            {
                tx.add((TxMasterStyleAtom)_children[i]);
            }
            else if (_children[i] instanceof ColorSchemeAtom)
            {
                clr.add((ColorSchemeAtom)_children[i]);
            }

            if (ppDrawing != null && _children[i] instanceof ColorSchemeAtom)
            {
                _colorScheme = (ColorSchemeAtom)_children[i];
            }

        }
        txmasters = tx.toArray(new TxMasterStyleAtom[tx.size()]);
        clrscheme = clr.toArray(new ColorSchemeAtom[clr.size()]);
    }


    public long getRecordType()
    {
        return _type;
    }

    public ColorSchemeAtom getColorScheme()
    {
        return _colorScheme;
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
        if (txmasters != null)
        {
            for (TxMasterStyleAtom tms : txmasters)
            {
                tms.dispose();
            }
            txmasters = null;
        }
        if (clrscheme != null)
        {
            for (ColorSchemeAtom csa : clrscheme)
            {
                csa.dispose();
            }
            clrscheme = null;
        }
        if (_colorScheme != null)
        {
            _colorScheme.dispose();
            _colorScheme = null;
        }
    }
    

    private SlideAtom slideAtom;
    private PPDrawing ppDrawing;
    private TxMasterStyleAtom[] txmasters;
    private ColorSchemeAtom[] clrscheme;
    private ColorSchemeAtom _colorScheme;
}
