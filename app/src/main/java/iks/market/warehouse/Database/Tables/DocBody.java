package iks.market.warehouse.Database.Tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "DocBody")
public class DocBody {
@PrimaryKey
@NonNull
    public String document;
    public String code;
    public String barcode;
    public String inpack;

    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "qty")
    public String qty;
    @ColumnInfo(name = "qtypredict")
    public String qtypredict;



    public DocBody(String document, String code, String barcode, String inpack, String name, String qty, String qtypredict){

        this.document = document;
        this.code = code;
        this.barcode = barcode;
        this.inpack = inpack;
        this.name = name;
        this.qty = qty;
        this.qtypredict = qtypredict;
    }
}
