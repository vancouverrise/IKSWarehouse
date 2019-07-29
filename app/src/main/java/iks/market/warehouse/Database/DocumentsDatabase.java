package iks.market.warehouse.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import iks.market.warehouse.Database.DAO.DocBodyDao;
import iks.market.warehouse.Database.DAO.DocHeaderDao;
import iks.market.warehouse.Database.DAO.DocPartnersDao;
import iks.market.warehouse.Database.Tables.DocBody;
import iks.market.warehouse.Database.Tables.DocHeader;
import iks.market.warehouse.Database.Tables.DocPartners;


@Database(entities = {DocBody.class, DocHeader.class, DocPartners.class}, exportSchema = false, version = 1)
public abstract class DocumentsDatabase extends RoomDatabase {
    private static final String DOCUMENTS_DB = "documents_db";
    private static DocumentsDatabase instance;

    public static synchronized DocumentsDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), DocumentsDatabase.class, DOCUMENTS_DB)
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract DocHeaderDao docHeaderDao();
    public abstract DocBodyDao docBodyDao();
    public abstract DocPartnersDao partnersDao();


}
