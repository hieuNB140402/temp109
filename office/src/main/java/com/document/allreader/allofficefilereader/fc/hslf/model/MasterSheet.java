

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.TextProp;
import com.document.allreader.allofficefilereader.fc.hslf.record.SheetContainer;


public abstract class MasterSheet extends Sheet
{
    public MasterSheet(SheetContainer container, int sheetNo)
    {
        super(container, sheetNo);
    }


    public abstract TextProp getStyleAttribute(int txtype, int level, String name,
        boolean isCharacter);

    public static boolean isPlaceholder(Shape shape)
    {
        if (!(shape instanceof TextShape))
            return false;

        TextShape tx = (TextShape)shape;
        return tx.getPlaceholderAtom() != null;
    }
}
