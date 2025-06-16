

package com.document.allreader.allofficefilereader.fc.hssf.util;


import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFSheet;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFWorkbook;
import com.document.allreader.allofficefilereader.fc.ss.util.HSSFCellRangeAddress;
import com.document.allreader.allofficefilereader.fc.ss.util.Region;
import com.document.allreader.allofficefilereader.fc.ss.util.RegionUtil;


/**
 * Various utility functions that make working with a region of cells easier.
 *
 * @author Eric Pugh epugh@upstate.com
 */
public final class HSSFRegionUtil {

	private HSSFRegionUtil() {
		// no instances of this class
	}

	private static HSSFCellRangeAddress toCRA(Region region) {
		return Region.convertToCellRangeAddress(region);
	}

	/**
	 * @deprecated (Aug 2008) use {@link HSSFCellRangeAddress} instead of {@link Region}
	 */
	public static void setBorderLeft(short border, Region region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		setBorderLeft(border, toCRA(region), sheet, workbook);
	}
	/**
	 * Sets the left border for a region of cells by manipulating the cell style
	 * of the individual cells on the left
	 *
	 * @param border The new border
	 * @param region The region that should have the border
	 * @param workbook The workbook that the region is on.
	 * @param sheet The sheet that the region is on.
	 */
	public static void setBorderLeft(int border, HSSFCellRangeAddress region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		RegionUtil.setBorderLeft(border, region, sheet, workbook);
	}

	/**
	 * @deprecated (Aug 2008) use {@link HSSFCellRangeAddress} instead of {@link Region}
	 */
	public static void setLeftBorderColor(short color, Region region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		setLeftBorderColor(color, toCRA(region), sheet, workbook);
	}
	/**
	 * Sets the leftBorderColor attribute of the HSSFRegionUtil object
	 *
	 * @param color The color of the border
	 * @param region The region that should have the border
	 * @param workbook The workbook that the region is on.
	 * @param sheet The sheet that the region is on.
	 */
	public static void setLeftBorderColor(int color, HSSFCellRangeAddress region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		RegionUtil.setLeftBorderColor(color, region, sheet, workbook);
	}

	/**
	 * @deprecated (Aug 2008) use {@link HSSFCellRangeAddress} instead of {@link Region}
	 */
	public static void setBorderRight(short border, Region region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		setBorderRight(border, toCRA(region), sheet, workbook);
	}
	/**
	 * Sets the borderRight attribute of the HSSFRegionUtil object
	 *
	 * @param border The new border
	 * @param region The region that should have the border
	 * @param workbook The workbook that the region is on.
	 * @param sheet The sheet that the region is on.
	 */
	public static void setBorderRight(int border, HSSFCellRangeAddress region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		RegionUtil.setBorderRight(border, region, sheet, workbook);
	}

	/**
	 * @deprecated (Aug 2008) use {@link HSSFCellRangeAddress} instead of {@link Region}
	 */
	public static void setRightBorderColor(short color, Region region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		setRightBorderColor(color, toCRA(region), sheet, workbook);
	}
	/**
	 * Sets the rightBorderColor attribute of the HSSFRegionUtil object
	 *
	 * @param color The color of the border
	 * @param region The region that should have the border
	 * @param workbook The workbook that the region is on.
	 * @param sheet The sheet that the region is on.
	 */
	public static void setRightBorderColor(int color, HSSFCellRangeAddress region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		RegionUtil.setRightBorderColor(color, region, sheet, workbook);
	}

	/**
	 * @deprecated (Aug 2008) use {@link HSSFCellRangeAddress} instead of {@link Region}
	 */
	public static void setBorderBottom(short border, Region region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		setBorderBottom(border, toCRA(region), sheet, workbook);
	}
	/**
	 * Sets the borderBottom attribute of the HSSFRegionUtil object
	 *
	 * @param border The new border
	 * @param region The region that should have the border
	 * @param workbook The workbook that the region is on.
	 * @param sheet The sheet that the region is on.
	 */
	public static void setBorderBottom(int border, HSSFCellRangeAddress region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		RegionUtil.setBorderBottom(border, region, sheet, workbook);
	}

	/**
	 * @deprecated (Aug 2008) use {@link HSSFCellRangeAddress} instead of {@link Region}
	 */
	public static void setBottomBorderColor(short color, Region region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		setBottomBorderColor(color, toCRA(region), sheet, workbook);
	}
	/**
	 * Sets the bottomBorderColor attribute of the HSSFRegionUtil object
	 *
	 * @param color The color of the border
	 * @param region The region that should have the border
	 * @param workbook The workbook that the region is on.
	 * @param sheet The sheet that the region is on.
	 */
	public static void setBottomBorderColor(int color, HSSFCellRangeAddress region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		RegionUtil.setBottomBorderColor(color, region, sheet, workbook);
	}

	/**
	 * @deprecated (Aug 2008) use {@link HSSFCellRangeAddress} instead of {@link Region}
	 */
	public static void setBorderTop(short border, Region region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		setBorderTop(border, toCRA(region), sheet, workbook);
	}
	/**
	 * Sets the borderBottom attribute of the HSSFRegionUtil object
	 *
	 * @param border The new border
	 * @param region The region that should have the border
	 * @param workbook The workbook that the region is on.
	 * @param sheet The sheet that the region is on.
	 */
	public static void setBorderTop(int border, HSSFCellRangeAddress region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		RegionUtil.setBorderTop(border, region, sheet, workbook);
	}

	/**
	 * @deprecated (Aug 2008) use {@link HSSFCellRangeAddress} instead of {@link Region}
	 */
	public static void setTopBorderColor(short color, Region region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		setTopBorderColor(color, toCRA(region), sheet, workbook);
	}
	/**
	 * Sets the topBorderColor attribute of the HSSFRegionUtil object
	 *
	 * @param color The color of the border
	 * @param region  The region that should have the border
	 * @param workbook The workbook that the region is on.
	 * @param sheet The sheet that the region is on.
	 */
	public static void setTopBorderColor(int color, HSSFCellRangeAddress region, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		RegionUtil.setTopBorderColor(color, region, sheet, workbook);
	}
}
