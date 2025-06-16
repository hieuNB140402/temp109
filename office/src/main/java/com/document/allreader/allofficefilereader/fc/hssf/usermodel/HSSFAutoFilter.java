

package com.document.allreader.allofficefilereader.fc.hssf.usermodel;
import com.document.allreader.allofficefilereader.fc.ss.usermodel.AutoFilter;

/**
 * Represents autofiltering for the specified worksheet.
 *
 * @author Yegor Kozlov
 */
public final class HSSFAutoFilter implements AutoFilter {
    private HSSFSheet _sheet;

    HSSFAutoFilter(HSSFSheet sheet){
        _sheet = sheet;
    }
}
