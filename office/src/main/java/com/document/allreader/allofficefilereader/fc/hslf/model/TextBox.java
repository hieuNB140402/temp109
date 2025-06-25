

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperties;


public class TextBox extends TextShape
{

    protected TextBox(EscherContainerRecord escherRecord, Shape parent)
    {
        super(escherRecord, parent);
    }


    public TextBox(Shape parent)
    {
        super(parent);
    }


    public TextBox()
    {
        this(null);
    }


    protected EscherContainerRecord createSpContainer(boolean isChild)
    {
        _escherContainer = super.createSpContainer(isChild);

        setShapeType(ShapeTypes.TextBox);


        setEscherProperty(EscherProperties.FILL__FILLCOLOR, 0x8000004);
        setEscherProperty(EscherProperties.FILL__FILLBACKCOLOR, 0x8000000);
        setEscherProperty(EscherProperties.FILL__NOFILLHITTEST, 0x100000);
        setEscherProperty(EscherProperties.LINESTYLE__COLOR, 0x8000001);
        setEscherProperty(EscherProperties.LINESTYLE__NOLINEDRAWDASH, 0x80000);
        setEscherProperty(EscherProperties.SHADOWSTYLE__COLOR, 0x8000002);

        _txtrun = createTextRun();

        return _escherContainer;
    }

    protected void setDefaultTextProperties(TextRun _txtrun)
    {
        setVerticalAlignment(TextBox.AnchorTop);
        setEscherProperty(EscherProperties.TEXT__SIZE_TEXT_TO_FIT_SHAPE, 0x20002);
    }

}
