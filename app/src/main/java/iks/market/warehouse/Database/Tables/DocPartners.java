package iks.market.warehouse.Database.Tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "DocPartners")
public class DocPartners {
    @PrimaryKey
    public long code;
    @ColumnInfo
    public String name;

    public DocPartners(long code, String name){
        this.code = code;
        this.name = name;
    }

    @Ignore
    public DocPartners(String name){
        this.name = name;
    }
}
