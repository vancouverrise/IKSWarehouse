package iks.market.warehouse.Database.Tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "DocHeader")
public class DocHeader {
    @PrimaryKey
    public long documentNumber;
    @ColumnInfo(name = "DOCNUMBER")
    public String docnumber;
    @ColumnInfo(name = "CODE")
    public String code;
    @ColumnInfo(name = "NAME")
    public String name;
    @ColumnInfo(name = "DESCRIPTION")
    public String description;

    public DocHeader(long documentNumber, String code, String name, String description){
        this.documentNumber = documentNumber;
        this.code = code;
        this.name = name;

        this.description = description;
    }

    @Ignore
    public DocHeader(String code, String name, String description){
        this.code = code;
        this.name = name;
        this.description = description;
    }

}
