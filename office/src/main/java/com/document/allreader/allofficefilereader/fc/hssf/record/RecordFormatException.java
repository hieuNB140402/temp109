

        

package com.document.allreader.allofficefilereader.fc.hssf.record;

/**
 * Title:     Record Format Exception
 * Description: Used by records to indicate invalid format/data.<P>
 */

public class RecordFormatException
    extends com.document.allreader.allofficefilereader.fc.util.RecordFormatException
{
    public RecordFormatException(String exception)
    {
        super(exception);
    }
    
    public RecordFormatException(String exception, Throwable thr) {
      super(exception, thr);
    }
    
    public RecordFormatException(Throwable thr) {
      super(thr);
    }
}
