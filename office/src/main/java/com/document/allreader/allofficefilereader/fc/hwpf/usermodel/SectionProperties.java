

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import com.document.allreader.allofficefilereader.fc.hwpf.model.types.SEPAbstractType;


public final class SectionProperties extends SEPAbstractType
{
    public SectionProperties()
    {
        field_20_brcTop = new BorderCode();
        field_21_brcLeft = new BorderCode();
        field_22_brcBottom = new BorderCode();
        field_23_brcRight = new BorderCode();
        field_26_dttmPropRMark = new DateAndTime();
    }

    public Object clone() throws CloneNotSupportedException
    {
        SectionProperties copy = (SectionProperties) super.clone();
        copy.field_20_brcTop = (BorderCode) field_20_brcTop.clone();
        copy.field_21_brcLeft = (BorderCode) field_21_brcLeft.clone();
        copy.field_22_brcBottom = (BorderCode) field_22_brcBottom.clone();
        copy.field_23_brcRight = (BorderCode) field_23_brcRight.clone();
        copy.field_26_dttmPropRMark = (DateAndTime) field_26_dttmPropRMark
                .clone();

        return copy;
    }
    

    public BorderCode getTopBorder()
    {
        return field_20_brcTop;
    }
    

    public BorderCode getBottomBorder()
    {
        return field_22_brcBottom;
    }

    public BorderCode getLeftBorder()
    {
        return field_21_brcLeft;
    }

    public BorderCode getRightBorder()
    {
        return field_23_brcRight;
    }
    
    
    public int getSectionBorder()
    {
        return getPgbProp();
    }

    public boolean equals( Object obj )
    {
        Field[] fields = SectionProperties.class.getSuperclass()
                .getDeclaredFields();
        AccessibleObject.setAccessible( fields, true );
        try
        {
            for ( int x = 0; x < fields.length; x++ )
            {
                Object obj1 = fields[x].get( this );
                Object obj2 = fields[x].get( obj );
                if ( obj1 == null && obj2 == null )
                {
                    continue;
                }
                if ( !obj1.equals( obj2 ) )
                {
                    return false;
                }
            }
            return true;
        }
        catch ( Exception e )
        {
            return false;
        }
    }

}
