

package com.document.allreader.allofficefilereader.fc.ss.usermodel;

public interface Name {


    String getSheetName();

    String getNameName();


    void setNameName(String name);

    String getRefersToFormula();


    void setRefersToFormula(String formulaText);


    boolean isFunctionName();


    boolean isDeleted();


    public void setSheetIndex(int sheetId);

    public int getSheetIndex();


    public String getComment();

   
    public void setComment(String comment);


    void setFunction(boolean value);
}
