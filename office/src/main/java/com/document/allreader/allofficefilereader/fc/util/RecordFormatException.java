

        

package com.document.allreader.allofficefilereader.fc.util;

/**
 * A common exception thrown by our binary format parsers
 *  (especially HSSF and DDF), when they hit invalid
 *  format or data when processing a record.
 */
public class RecordFormatException
    extends RuntimeException
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
