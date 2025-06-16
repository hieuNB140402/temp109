

package com.document.allreader.allofficefilereader.fc.dom4j;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * <p>
 * <code>DocumentException</code> is a nested Exception which may be thrown
 * during the processing of a DOM4J document.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.7 $
 */
public class DocumentException extends Exception {
    /** A wrapped <code>Throwable</code> */
    private Throwable nestedException;

    public DocumentException() {
        super("Error occurred in DOM4J application.");
    }

    public DocumentException(String message) {
        super(message);
    }

    public DocumentException(Throwable nestedException) {
        super(nestedException.getMessage());
        this.nestedException = nestedException;
    }

    public DocumentException(String message, Throwable nestedException) {
        super(message);
        this.nestedException = nestedException;
    }

    public Throwable getNestedException() {
        return nestedException;
    }

    public String getMessage() {
        if (nestedException != null) {
            return super.getMessage() + " Nested exception: "
                    + nestedException.getMessage();
        } else {
            return super.getMessage();
        }
    }

    public void printStackTrace() {
        super.printStackTrace();

        if (nestedException != null) {
            System.err.print("Nested exception: ");
            nestedException.printStackTrace();
        }
    }

    public void printStackTrace(PrintStream out) {
        super.printStackTrace(out);

        if (nestedException != null) {
            out.println("Nested exception: ");
            nestedException.printStackTrace(out);
        }
    }

    public void printStackTrace(PrintWriter writer) {
        super.printStackTrace(writer);

        if (nestedException != null) {
            writer.println("Nested exception: ");
            nestedException.printStackTrace(writer);
        }
    }
}

