

        
package com.document.allreader.allofficefilereader.fc.util;



import java.io.FileInputStream;
import java.io.IOException;

import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFSheet;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFWorkbook;
import com.document.allreader.allofficefilereader.fc.poifs.filesystem.POIFSFileSystem;

/**
 * Dump out the aggregated escher records
 */
public class DrawingDump
{
    public static void main( String[] args ) throws IOException
    {
        POIFSFileSystem fs      =
                new POIFSFileSystem(new FileInputStream(args[0]));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        System.out.println( "Drawing group:" );
        wb.dumpDrawingGroupRecords(true);

        for (int sheetNum = 1; sheetNum <= wb.getNumberOfSheets(); sheetNum++)
        {
            System.out.println( "Sheet " + sheetNum + ":" );
            HSSFSheet sheet = wb.getSheetAt(sheetNum - 1);
            sheet.dumpDrawingRecords(true);
        }

    }
}
