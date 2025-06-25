

package com.document.allreader.allofficefilereader.fc.openxml4j.opc;

/**
 * Specifies whether the target of a PackageRelationship is inside or outside a
 * Package.
 *
 * @author Julien Chable
 * @version 1.0
 */
public enum TargetMode {
	/** The relationship references a resource that is external to the package. */
	INTERNAL,
	/** The relationship references a part that is inside the package. */
	EXTERNAL
}
