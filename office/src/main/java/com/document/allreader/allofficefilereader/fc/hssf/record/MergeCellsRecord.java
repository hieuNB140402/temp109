

package com.document.allreader.allofficefilereader.fc.hssf.record;


import com.document.allreader.allofficefilereader.fc.ss.util.CellRangeAddressList;
import com.document.allreader.allofficefilereader.fc.ss.util.HSSFCellRangeAddress;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * Title: Merged Cells Record (0x00E5)
 * <br/>
 * Description:  Optional record defining a square area of cells to "merged" into one cell. <br>
 * @author Andrew C. Oliver (acoliver at apache dot org)
 */
public final class MergeCellsRecord extends StandardRecord {
    public final static short sid = 0x00E5;
    /** sometimes the regions array is shared with other MergedCellsRecords */ 
    private HSSFCellRangeAddress[] _regions;
    private final int _startIndex;
    private final int _numberOfRegions;

    public MergeCellsRecord(HSSFCellRangeAddress[] regions, int startIndex, int numberOfRegions) {
		_regions = regions;
		_startIndex = startIndex;
		_numberOfRegions = numberOfRegions;
    }
    /**
     * Constructs a MergedCellsRecord and sets its fields appropriately
     * @param in the RecordInputstream to read the record from
     */
    public MergeCellsRecord(RecordInputStream in) {
     	int nRegions = in.readUShort();
    	HSSFCellRangeAddress[] cras = new HSSFCellRangeAddress[nRegions];
    	for (int i = 0; i < nRegions; i++) {
			cras[i] = new HSSFCellRangeAddress(in);
		}
    	_numberOfRegions = nRegions;
    	_startIndex = 0;
    	_regions = cras;
    }
    /**
     * get the number of merged areas.  If this drops down to 0 you should just go
     * ahead and delete the record.
     * @return number of areas
     */
    public short getNumAreas() {
        return (short)_numberOfRegions;
    }

    /**
     * @return MergedRegion at the given index representing the area that is Merged (r1,c1 - r2,c2)
     */
    public HSSFCellRangeAddress getAreaAt(int index) {
        return _regions[_startIndex + index];
    }

    protected int getDataSize() {
		return CellRangeAddressList.getEncodedSize(_numberOfRegions);
	}

    public short getSid() {
        return sid;
    }

    public void serialize(LittleEndianOutput out) {
        int nItems = _numberOfRegions;
        out.writeShort(nItems);
        for (int i = 0; i < _numberOfRegions; i++) {
			_regions[_startIndex + i].serialize(out);
		}
    }

    public String toString() {
        StringBuffer retval = new StringBuffer();

        retval.append("[MERGEDCELLS]").append("\n");
        retval.append("     .numregions =").append(getNumAreas()).append("\n");
        for (int k = 0; k < _numberOfRegions; k++) {
            HSSFCellRangeAddress r = _regions[_startIndex + k];

            retval.append("     .rowfrom =").append(r.getFirstRow()).append("\n");
            retval.append("     .rowto   =").append(r.getLastRow()).append("\n");
            retval.append("     .colfrom =").append(r.getFirstColumn()).append("\n");
            retval.append("     .colto   =").append(r.getLastColumn()).append("\n");
        }
        retval.append("[MERGEDCELLS]").append("\n");
        return retval.toString();
    }

    public Object clone() {
    	int nRegions = _numberOfRegions;
    	HSSFCellRangeAddress[] clonedRegions = new HSSFCellRangeAddress[nRegions];
		for (int i = 0; i < clonedRegions.length; i++) {
			clonedRegions[i] = _regions[_startIndex + i].copy();
		}
        return new MergeCellsRecord(clonedRegions, 0, nRegions);
    }
}
