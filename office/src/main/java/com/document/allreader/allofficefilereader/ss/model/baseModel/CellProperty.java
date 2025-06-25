
package com.document.allreader.allofficefilereader.ss.model.baseModel;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.document.allreader.allofficefilereader.common.hyperlink.Hyperlink;
import com.document.allreader.allofficefilereader.simpletext.view.STRoot;
import com.document.allreader.allofficefilereader.ss.model.table.SSTable;


public class CellProperty
{
    //numeric type
    public static final short CELLPROPID_NUMERICTYPE = 0;
    // rangeAddress index of merge cell
    public static final short CELLPROPID_MERGEDRANGADDRESS = 1;
    //expanded range address index of cell(contents width larger than cell width)
    public static final short CELLPROPID_EXPANDRANGADDRESS = 2;
    //hyperlink
    public static final short CELLPROPID_HYPERLINK = 3;
    //for wraptext cell(its width larger than cell width)
    public static final short CELLPROPID_STROOT = 4;
    //table style
    public static final short CELLPROPID_TABLEINFO = 5;
    
    public CellProperty()
    {
        props = new TreeMap<Short, Object>();
    }
    
    /**
     * 
     * @param id
     * @param value
     */
    public void setCellProp(short id, Object value)
    {
        props.put(id, value);
    }
    
    /**
     * 
     * @param id
     * @return
     */
    public Object getCellProp(short id)
    {
        return props.get(id);
    }
    
    /**
     * 
     * @return
     */
    public short getCellNumericType()
    {
        Object obj = props.get(CELLPROPID_NUMERICTYPE);
        if(obj != null)
        {
            return (Short)obj;
        }
        else
        {
            return -1;
        }
    }
    
    /**
     * 
     * @return
     */
    public int getCellMergeRangeAddressIndex()
    {
        Object obj = props.get(CELLPROPID_MERGEDRANGADDRESS);
        if(obj != null)
        {
            return (Integer)obj;
        }
        else
        {
            return -1;
        }
    }
    
    /**
     * 
     * @return
     */
    public int getExpandCellRangeAddressIndex()
    {
        Object obj = props.get(CELLPROPID_EXPANDRANGADDRESS);
        if(obj != null)
        {
            return (Integer)obj;
        }
        else
        {
            return -1;
        }
    }
    
    /**
     * 
     * @return
     */
    public Hyperlink getCellHyperlink()
    {
        Object obj = props.get(CELLPROPID_HYPERLINK);
        if(obj != null)
        {
            return (Hyperlink)obj;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 
     * @return
     */
    public int getCellSTRoot()
    {
        Object obj = props.get(CELLPROPID_STROOT);
        if(obj != null)
        {
            return (Integer)obj;
        }
        else
        {
            return -1;
        }
    }
    
    public SSTable getTableInfo()
    {
        Object obj = props.get(CELLPROPID_TABLEINFO);
        if(obj != null)
        {
            return (SSTable)obj;
        }
        else
        {
            return null;
        }
    }
    

    public void removeCellSTRoot()
    {
        props.remove(CELLPROPID_STROOT);
    }
    
    public void dispose()
    {
        Collection<Object> objs = props.values();
        for(Object obj : objs)
        {
            if(obj instanceof Hyperlink)
            {
                ((Hyperlink)obj).dispose();
            }
            else if(obj instanceof STRoot)
            {
                ((STRoot)obj).dispose();
            }
        }       
    }
    
    //propertys of cell
    private Map<Short, Object> props;
    
}
