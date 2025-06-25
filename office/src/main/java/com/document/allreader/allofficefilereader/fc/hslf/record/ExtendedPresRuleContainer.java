
package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public class ExtendedPresRuleContainer extends PositionDependentRecordContainer
{

    private static long _type = 4014;
    


    protected ExtendedPresRuleContainer(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);


        _children = Record.findChildRecords(source, start + 8, len - 8);
        Vector<ExtendedParaAtomsSet> sets = new Vector<ExtendedParaAtomsSet>();
        for (int i = 0; i < _children.length; i++)
        {
            if (_children[i] instanceof ExtendedParagraphAtom)
            {
                for (int j = i - 1; j >= 0; j--)
                {
                    if (_children[j] instanceof ExtendedParagraphHeaderAtom)
                    {
                        ExtendedParaAtomsSet set = new ExtendedParaAtomsSet((ExtendedParagraphHeaderAtom)_children[j], 
                            (ExtendedParagraphAtom)_children[i]);
                        sets.add(set);
                        break;
                    }
                }
            }
        }
        _extendedAtomsSets = sets.toArray(new ExtendedParaAtomsSet[sets.size()]);
    }

    public ExtendedParaAtomsSet[] getExtendedParaAtomsSets()
    {
        return _extendedAtomsSets;
    }

    
    public long getRecordType()
    {
        return _type;
    }

    
    public void writeOut(OutputStream o) throws IOException
    {
        
    }
    

    public class ExtendedParaAtomsSet
    {

        public ExtendedParaAtomsSet(ExtendedParagraphHeaderAtom extendedParaHeaderAtom, 
            ExtendedParagraphAtom extendedParaAtom)
        {
            _extendedParaHeaderAtom = extendedParaHeaderAtom;
            _extendedParaAtom = extendedParaAtom;
        }

        public ExtendedParagraphHeaderAtom getExtendedParaHeaderAtom()
        {
            return _extendedParaHeaderAtom;
        }
        

        public ExtendedParagraphAtom getExtendedParaAtom()
        {
            return _extendedParaAtom;
        }

        public void dispose()
        {
            if (_extendedParaHeaderAtom != null)
            {
                _extendedParaHeaderAtom.dispose();
                _extendedParaHeaderAtom = null;
            }
            if (_extendedParaAtom != null)
            {
                _extendedParaAtom.dispose();
                _extendedParaAtom = null;
            }
        }

        private ExtendedParagraphHeaderAtom _extendedParaHeaderAtom;

        private ExtendedParagraphAtom _extendedParaAtom;
    }

    
    public void dispose()
    {
        _header = null;
        if (_extendedAtomsSets != null)
        {
            for (ExtendedParaAtomsSet eps : _extendedAtomsSets)
            {
                eps.dispose();
            }
            _extendedAtomsSets = null;
        }
    }

    private byte[] _header;

    private ExtendedParaAtomsSet[] _extendedAtomsSets;
}
