

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.ArrayList;


public final class Document extends PositionDependentRecordContainer {

    public DocumentAtom getDocumentAtom() {
        return documentAtom;
    }


    public Environment getEnvironment() {
        return environment;
    }

    public PPDrawingGroup getPPDrawingGroup() {
        return ppDrawing;
    }


    public ExObjList getExObjList() {
        return exObjList;
    }


    public SlideListWithText[] getSlideListWithTexts() {
        return slwts;
    }

    public SlideListWithText getMasterSlideListWithText() {
        for (int i = 0; i < slwts.length; i++) {
            if (slwts[i].getInstance() == SlideListWithText.MASTER) {
                return slwts[i];
            }
        }
        return null;
    }

    public SlideListWithText getSlideSlideListWithText() {
        for (int i = 0; i < slwts.length; i++) {
            if (slwts[i].getInstance() == SlideListWithText.SLIDES) {
                return slwts[i];
            }
        }
        return null;
    }

    public SlideListWithText getNotesSlideListWithText() {
        for (int i = 0; i < slwts.length; i++) {
            if (slwts[i].getInstance() == SlideListWithText.NOTES) {
                return slwts[i];
            }
        }
        return null;
    }


    public List getList() {
        return list;
    }


    protected Document(byte[] source, int start, int len) {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        _children = Record.findChildRecords(source, start + 8, len - 8);

        if (!(_children[0] instanceof DocumentAtom)) {
            throw new IllegalStateException("The first child of a Document must be a DocumentAtom");
        }
        documentAtom = (DocumentAtom) _children[0];


        int slwtcount = 0;
        for (int i = 1; i < _children.length; i++) {
            if (_children[i] instanceof SlideListWithText) {
                slwtcount++;
            } else if (_children[i] instanceof Environment) {
                environment = (Environment) _children[i];
            } else if (_children[i] instanceof PPDrawingGroup) {
                ppDrawing = (PPDrawingGroup) _children[i];
            } else if (_children[i] instanceof ExObjList) {
                exObjList = (ExObjList) _children[i];
            } else if (_children[i] instanceof List) {
                list = (List) _children[i];
            }
        }


        slwts = new SlideListWithText[slwtcount];
        slwtcount = 0;
        for (int i = 1; i < _children.length; i++) {
            if (_children[i] instanceof SlideListWithText) {
                slwts[slwtcount] = (SlideListWithText) _children[i];
                slwtcount++;
            }
        }
    }


    public void removeSlideListWithText(SlideListWithText slwt) {
        ArrayList<SlideListWithText> lst = new ArrayList<SlideListWithText>();
        for (SlideListWithText s : slwts) {
            if (s != slwt)
                lst.add(s);
            else {
                removeChild(slwt);
            }
        }
        slwts = lst.toArray(new SlideListWithText[lst.size()]);
    }


    public long getRecordType() {
        return _type;
    }


    public void dispose() {
        _header = null;
        if (documentAtom != null) {
            documentAtom.dispose();
            documentAtom = null;
        }
        if (environment != null) {
            environment.dispose();
            environment = null;
        }
        if (ppDrawing != null) {
            ppDrawing.dispose();
            ppDrawing = null;
        }
        if (slwts != null) {
            for (SlideListWithText swt : slwts) {
                swt.dispose();
            }
            slwts = null;
        }
        if (exObjList != null) {
            exObjList.dispose();
            exObjList = null;
        }
        if (list != null) {
            list.dispose();
            list = null;
        }
    }


    private byte[] _header;
    private static long _type = 1000;

    private DocumentAtom documentAtom;
    private Environment environment;
    private PPDrawingGroup ppDrawing;
    private SlideListWithText[] slwts;
    private ExObjList exObjList;
    private List list;
}
