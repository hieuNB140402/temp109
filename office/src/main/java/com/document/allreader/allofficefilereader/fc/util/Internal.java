

package com.document.allreader.allofficefilereader.fc.util;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;


/**
 * Program elements annotated &#64;Internal are intended for
 * POI internal use only. Such elements are not public by design
 * and likely to be removed in future versions of POI  or access
 * to such elements will be changed from 'public' to 'default' or less.
 *
 * @author Yegor Kozlov
 * @since POI-3.6
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Internal {
    String value() default "";    
}
