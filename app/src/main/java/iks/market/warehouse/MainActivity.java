package iks.market.warehouse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import iks.market.warehouse.Database.DocumentsDatabase;
import iks.market.warehouse.Database.Tables.DocBody;
import iks.market.warehouse.Database.Tables.DocHeader;
import iks.market.warehouse.Database.Tables.DocPartners;
import iks.market.warehouse.Recyclers.DocumentsViewAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 12;
    private static final int REQUEST_CODE = 12;
    Button buttonAdd, buttonGet, buttondDelete;
    EditText searchCode, searchName;
    RecyclerView recyclerView;
    EditText editText;

    ArrayList<String> namesArray = new ArrayList<>();
    ArrayList<String> code = new ArrayList<>();
    ArrayList<String> vendorArray = new ArrayList<>();

    static DocumentsDatabase documentsDatabase;

    List<DocPartners> DOCNUMBERO;
    ArrayList<String> temp = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        documentsDatabase = DocumentsDatabase.getInstance(this);

        buttondDelete   = findViewById(R.id.deletebtn);
        buttonAdd       = findViewById(R.id.buttonAdd);
        buttonGet       = findViewById(R.id.buttonGet);
        editText        = findViewById(R.id.primaryEdit);
        recyclerView    = findViewById(R.id.documentsRecycler);

        documentsDatabase.docHeaderDao().getDocumentsHeaderList();
        documentsDatabase.docBodyDao().getDocBodyList();
        DOCNUMBERO = documentsDatabase.partnersDao().getPartnerList();
        initRecyclerView();

        buttondDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDocumentFragment addDocumentFragment = new AddDocumentFragment();
                addDocumentFragment.show(getSupportFragmentManager(), "MyAddFragment");
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < DOCNUMBERO.size(); i++) {
                    temp.add(DOCNUMBERO.get(i).code + " " + DOCNUMBERO.get(i).name);
                    Log.d("k", temp.get(i));
                }


            }
        });

        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    requestPermissionForReadExtertalStorage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                File file = new File("/storage/emulated/0/partners.txt");
                CsvReader csvReader = new CsvReader();
                csvReader.setFieldSeparator(';');
                try(CsvParser csvParser = csvReader.parse(file, StandardCharsets.UTF_8)) {
                    CsvRow row;
                    List<DocPartners> docPartners = new ArrayList<>();

                    while((row = csvParser.nextRow()) != null){
                        System.out.println("First: " + row.getField(0));
                        docPartners.add( new DocPartners(row.getField(0), row.getField(1)));

                    }

                    documentsDatabase.partnersDao().insertDocuments(docPartners);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.documentsRecycler);
        DocumentsViewAdapter documentsViewAdapter = new DocumentsViewAdapter(this, namesArray, vendorArray);
        recyclerView.setAdapter(documentsViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void Adddialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View dialogView = layoutInflater.inflate(R.layout.dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(dialogView);
        dialog.show();
        System.out.println("This is the end");
        System.out.println("This is the end");

    }

    public void requestPermissionForReadExtertalStorage() throws Exception {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }
}
