

package com.document.allreader.allofficefilereader.fc.hslf.model.textproperties;

import java.util.LinkedList;

import com.document.allreader.allofficefilereader.fc.hslf.record.StyleTextPropAtom;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


/**
 * For a given run of characters, holds the properties (which could
 *  be paragraph properties or character properties).
 * Used to hold the number of characters affected, the list of active
 *  properties, and the random reserved field if required.
 */
public class TextPropCollection
{
    public int getSpecialMask()
    {
        return maskSpecial;
    }

    /** Fetch the number of characters this styling applies to */
    public int getCharactersCovered()
    {
        return charactersCovered;
    }

    /** Fetch the TextProps that define this styling */
    public LinkedList<TextProp> getTextPropList()
    {
        return textPropList;
    }

    /** Fetch the TextProp with this name, or null if it isn't present */
    public TextProp findByName(String textPropName)
    {
        for (int i = 0; i < textPropList.size(); i++)
        {
            TextProp prop = textPropList.get(i);
            if (prop.getName().equals(textPropName))
            {
                return prop;
            }
        }
        return null;
    }

    /** Add the TextProp with this name to the list */
    public TextProp addWithName(String name)
    {

        TextProp base = null;
        for (int i = 0; i < StyleTextPropAtom.characterTextPropTypes.length; i++)
        {
            if (StyleTextPropAtom.characterTextPropTypes[i].getName().equals(name))
            {
                base = StyleTextPropAtom.characterTextPropTypes[i];
            }
        }
        for (int i = 0; i < StyleTextPropAtom.paragraphTextPropTypes.length; i++)
        {
            if (StyleTextPropAtom.paragraphTextPropTypes[i].getName().equals(name))
            {
                base = StyleTextPropAtom.paragraphTextPropTypes[i];
            }
        }
        if (base == null)
        {
            throw new IllegalArgumentException("No TextProp with name " + name
                + " is defined to add from");
        }

        TextProp textProp = (TextProp)base.clone();
        int pos = 0;
        for (int i = 0; i < textPropList.size(); i++)
        {
            TextProp curProp = textPropList.get(i);
            if (textProp.getMask() > curProp.getMask())
            {
                pos++;
            }
        }
        textPropList.add(pos, textProp);
        return textProp;
    }

    /**
     * For an existing set of text properties, build the list of 
     *  properties coded for in a given run of properties.
     * @return the number of bytes that were used encoding the properties list
     */
    public int buildTextPropList(int containsField, TextProp[] potentialProperties, byte[] data,
        int dataOffset)
    {
        int bytesPassed = 0;


        for (int i = 0; i < potentialProperties.length; i++)
        {

            if ((containsField & potentialProperties[i].getMask()) != 0)
            {
                if (dataOffset + bytesPassed >= data.length)
                {

                    maskSpecial |= potentialProperties[i].getMask();
                    return bytesPassed;
                }


                TextProp prop = (TextProp)potentialProperties[i].clone();
                int val = 0;
                if (prop.getSize() == 2)
                {
                    val = LittleEndian.getShort(data, dataOffset + bytesPassed);
                }
                else if (prop.getSize() == 4)
                {
                    val = LittleEndian.getInt(data, dataOffset + bytesPassed);
                }
                else if (prop.getSize() == 0)
                {

                    maskSpecial |= potentialProperties[i].getMask();
                    continue;
                }
                if (CharFlagsTextProp.NAME.equals(prop.getName()) && val < 0)
                {
                    val = 0;
                }
                prop.setValue(val);
                bytesPassed += prop.getSize();
                if ("tabStops".equals(prop.getName()))
                {
                    bytesPassed += val * 4;
                }
                textPropList.add(prop);
            }
        }


        return bytesPassed;
    }


    public TextPropCollection(int charactersCovered, short reservedField)
    {
        this.charactersCovered = charactersCovered;
        this.reservedField = reservedField;
        textPropList = new LinkedList<TextProp>();
    }

    /**
     * Create a new collection of text properties (be they paragraph
     *  or character) for a run of text without any
     */
    public TextPropCollection(int textSize)
    {
        charactersCovered = textSize;
        reservedField = -1;
        textPropList = new LinkedList<TextProp>();
    }

    /**
     * Update the size of the text that this set of properties
     *  applies to 
     */
    public void updateTextSize(int textSize)
    {
        charactersCovered = textSize;
    }

    public short getReservedField()
    {
        return reservedField;
    }

    public void setReservedField(short val)
    {
        reservedField = val;
    }
    

    public void dispose()
    {
        if (textPropList != null)
        {   
            for (int i = 0; i < textPropList.size(); i++)
            {
                textPropList.get(i).dispose();
            }
            textPropList.clear();
            textPropList = null;
        }
    }
    
    private int charactersCovered;
    private short reservedField;
    private LinkedList<TextProp> textPropList;
    private int maskSpecial = 0;
}
