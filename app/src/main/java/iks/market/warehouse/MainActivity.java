package iks.market.warehouse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import iks.market.warehouse.Database.DocumentsDatabase;
import iks.market.warehouse.Database.Tables.DocBody;
import iks.market.warehouse.Database.Tables.DocHeader;
import iks.market.warehouse.Recyclers.DocumentsViewAdapter;

public class MainActivity extends AppCompatActivity {

    Button buttonAdd, buttonGet, buttondDelete;
    RecyclerView recyclerView;
    EditText editText;

    ArrayList<String> namesArray = new ArrayList<>();
    ArrayList<String> code = new ArrayList<>();
    ArrayList<String> vendorArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, AddDocument.class);

        buttondDelete   = findViewById(R.id.deletebtn);
        buttonAdd       = findViewById(R.id.buttonAdd);
        buttonGet       = findViewById(R.id.buttonGet);
        editText        = findViewById(R.id.primaryEdit);
        recyclerView    = findViewById(R.id.documentsRecycler);

        final DocumentsDatabase documentsDatabase = DocumentsDatabase.getInstance(this);
        documentsDatabase.docHeaderDao().getDocumentsHeaderList();
        documentsDatabase.docBodyDao().getDocBodyList();

        initRecyclerView();

        buttondDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DocHeader docHeader = new DocHeader(Long.parseLong(editText.getText().toString()), null, null, null);
                // documentsDatabase.docHeaderDao().deleteDocuments(docHeader);
               Adddialog();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<DocHeader> list2 = documentsDatabase.docHeaderDao().getDocumentsHeaderList();
                namesArray.clear();
                vendorArray.clear();

                List<DocBody> list = documentsDatabase.docBodyDao().getDocBodyList();


               // DocBody docBody = new DocBody("191919", "8080", "3232323", "2", "Blyadina", "2", "10");
               // documentsDatabase.docBodyDao().insertDocuments(docBody);

                System.out.println("Database current size: " + list.size());
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(list.get(i).barcode + " " + list.get(i).inpack + " " + i);
                    namesArray.add(list.get(i).document);
                    vendorArray.add(list.get(i).name);
                }

                System.out.println(namesArray);
                System.out.println(vendorArray);

                initRecyclerView();

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
    }
}
