

package com.document.allreader.allofficefilereader.fc.hslf.model;


import com.document.allreader.allofficefilereader.fc.ddf.*;


public final class Placeholder extends TextBox
{

    protected Placeholder(EscherContainerRecord escherRecord, Shape parent)
    {
        super(escherRecord, parent);
    }

    public Placeholder(Shape parent)
    {
        super(parent);
    }

    public Placeholder()
    {
        super();
    }


    protected EscherContainerRecord createSpContainer(boolean isChild)
    {
        _escherContainer = super.createSpContainer(isChild);


        return _escherContainer;
    }
}
