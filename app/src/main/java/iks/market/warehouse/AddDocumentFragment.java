package iks.market.warehouse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import iks.market.warehouse.Database.DocumentsDatabase;
import iks.market.warehouse.Database.Tables.DocHeader;

public class AddDocumentFragment extends DialogFragment {

    private static final String TAG = "MyAddDocument";

    //EditText
    private EditText document_Number;
    private EditText document_Description;
    private EditText document_Name;
    private EditText document_Code;
    //Buttons
    private Button addDocument;
    private Button cancelAdd;

    private DocumentsDatabase documentsDatabase;

    private List<DocHeader> list;
    private ArrayList<Long> DOCNUMBER = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog, container, false);

        addDocument          = view.findViewById(R.id.addDocument);
        cancelAdd            = view.findViewById(R.id.cancelAddDocument);

        document_Number      = view.findViewById(R.id.documentNumber_text);
        document_Description = view.findViewById(R.id.documentDescription_text);
        document_Name        = view.findViewById(R.id.documentName_text);
        document_Code        = view.findViewById(R.id.documentCode_text);

        documentsDatabase = MainActivity.documentsDatabase;

        addDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DOCNUMBER.clear();
                if (!document_Number.getText().toString().equals("")) {

                        list = documentsDatabase.docHeaderDao().getDocumentsHeaderList();
                        for (int i = 0; i < list.size(); i++) {
                            DOCNUMBER.add(list.get(i).documentNumber);
                        }

                        if (DOCNUMBER.contains(Long.valueOf(document_Number.getText().toString()))) {
                            Toast.makeText(getContext(), "Уже существует\nВведите другое имя", Toast.LENGTH_SHORT).show();
                        } else {
                            DocHeader docHeader = new DocHeader(
                                    Long.valueOf(document_Number.getText().toString()),
                                    document_Description.getText().toString(),
                                    document_Name.getText().toString(),
                                    document_Code.getText().toString());
                            documentsDatabase.docHeaderDao().insertDocuments(docHeader);
                            System.out.println("Done!");
                        }
                    }

                else {
                    Toast.makeText(getContext(), "Введите навание документа", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;


    }
}
