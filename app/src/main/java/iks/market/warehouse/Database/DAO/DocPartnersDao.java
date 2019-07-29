package iks.market.warehouse.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import iks.market.warehouse.Database.Tables.DocPartners;

@Dao
public interface DocPartnersDao {
    @Query("Select * from DocPartners")
    List<DocPartners> getPartnerList();
    @Insert
    void insertDocuments(DocPartners partners);
    @Update
    void updateDocuments(DocPartners partners);
    @Delete
    void deleteDocuments(DocPartners partners);
}
