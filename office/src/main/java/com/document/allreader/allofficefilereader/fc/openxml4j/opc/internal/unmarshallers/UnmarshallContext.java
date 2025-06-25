

package com.document.allreader.allofficefilereader.fc.openxml4j.opc.internal.unmarshallers;

import java.util.zip.ZipEntry;

import com.document.allreader.allofficefilereader.fc.openxml4j.opc.PackagePartName;
import com.document.allreader.allofficefilereader.fc.openxml4j.opc.ZipPackage;


/**
 * Context needed for the unmarshall process of a part. This class is immutable.
 * 
 * @author Julien Chable
 * @version 1.0
 */
public final class UnmarshallContext
{

    private ZipPackage _package;

    private PackagePartName partName;

    private ZipEntry zipEntry;

    /**
     * Constructor.
     * 
     * @param targetPackage
     *            Container.f
     * @param partName
     *            Name of the part to unmarshall.
     */
    public UnmarshallContext(ZipPackage targetPackage, PackagePartName partName)
    {
        this._package = targetPackage;
        this.partName = partName;
    }

    /**
     * @return the container
     */
    public ZipPackage getPackage()
    {
        return _package;
    }

    /**
     * @param container
     *            the container to set
     */
    public void setPackage(ZipPackage container)
    {
        this._package = container;
    }

    /**
     * @return the partName
     */
    PackagePartName getPartName()
    {
        return partName;
    }

    /**
     * @param partName
     *            the partName to set
     */
    public void setPartName(PackagePartName partName)
    {
        this.partName = partName;
    }

    /**
     * @return the zipEntry
     */
    ZipEntry getZipEntry()
    {
        return zipEntry;
    }

    /**
     * @param zipEntry
     *            the zipEntry to set
     */
    public void setZipEntry(ZipEntry zipEntry)
    {
        this.zipEntry = zipEntry;
    }
}
