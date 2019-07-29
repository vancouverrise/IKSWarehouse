package iks.market.warehouse.Database.Tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "DocBody")
public class DocBody {
    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = "DOCNUMBER")
    private String docnumber;
    @ColumnInfo(name = "CODE")
    private String code;
    @ColumnInfo(name = "BARCODE")
    private String barcode;
    @ColumnInfo(name = "INPACK")
    private String inpack;
    @ColumnInfo(name = "NAME")
    private String name;
    @ColumnInfo(name = "QTY")
    private String qty;
    @ColumnInfo(name = "QTYPREDICT")
    private String qtypredict;

    public DocBody(String id, String docnumber, String code, String barcode, String inpack, String name, String qty, String qtypredict){
        this.id = id;
        this.docnumber = docnumber;
        this.code = code;
        this.barcode = barcode;
        this.inpack = inpack;
        this.name = name;
        this.qty = qty;
        this.qtypredict = qtypredict;
    }




    public String getDocnumber() {
        return docnumber;
    }

    public void setDocnumber(String docnumber) {
        this.docnumber = docnumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getInpack() {
        return inpack;
    }

    public void setInpack(String inpack) {
        this.inpack = inpack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getQtypredict() {
        return qtypredict;
    }

    public void setQtypredict(String qtypredict) {
        this.qtypredict = qtypredict;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
