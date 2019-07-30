package iks.market.warehouse;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import iks.market.warehouse.Database.DocumentsDatabase;
import iks.market.warehouse.Database.Tables.DocHeader;
import iks.market.warehouse.Database.Tables.DocPartners;

public class AddDocumentFragment extends DialogFragment implements TextWatcher, AdapterView.OnItemClickListener {

    private static final String TAG = "MyAddDocument";

    //EditText
    private AutoCompleteTextView document_Number;
    private AutoCompleteTextView document_Description;
    private AutoCompleteTextView document_Name;
    private AutoCompleteTextView document_Code;
    //Buttons
    private Button addDocument;
    private Button cancelAdd;
    //Database
    private DocumentsDatabase documentsDatabase;
    //Temp Arrays
    private List<DocHeader> list;
    private List<DocPartners> list2;
    private ArrayList<String> DOCNUMBER = new ArrayList<>();
    private ArrayList<String> VendorCode = new ArrayList<>();
    private ArrayList<String> VendorDescription = new ArrayList<>();
    //Auto


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
        list = documentsDatabase.docHeaderDao().getDocumentsHeaderList();
        list2 = documentsDatabase.partnersDao().getPartnerList();


        DOCNUMBER.clear();
        for (int i = 0; i < list.size(); i++) {
            DOCNUMBER.add(list.get(i).documentNumber);
        }

        VendorCode.clear();
        for (int i = 0; i < list2.size(); i++) {
            VendorCode.add(list2.get(i).code);
        }

        VendorDescription.clear();
        for (int i = 0; i < list2.size(); i++) {
            VendorDescription.add(list2.get(i).name);
        }

        addDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DOCNUMBER.clear();
                if (!document_Number.getText().toString().equals("")) {


                        for (int i = 0; i < list.size(); i++) {
                            DOCNUMBER.add(list.get(i).documentNumber);
                        }

                        if (DOCNUMBER.contains(document_Number.getText().toString())) {
                            Toast.makeText(getContext(), "Уже существует\nВведите другое имя", Toast.LENGTH_SHORT).show();
                        } else {
                            DocHeader docHeader = new DocHeader(
                                    document_Number.getText().toString(),
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

        document_Number.addTextChangedListener(this);
        document_Number.setAdapter(new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_dropdown_item_1line, DOCNUMBER));

        document_Code.addTextChangedListener(this);
        document_Code.setAdapter(new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_dropdown_item_1line, VendorCode));
        document_Code.setOnItemClickListener(this);

        document_Name.addTextChangedListener(this);
        document_Name.setAdapter(new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_dropdown_item_1line, VendorDescription));
        document_Name.setOnItemClickListener(this);



        return view;


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println(l);


        String item = adapterView.getItemAtPosition(i).toString();
        int position = 0;
        for (int j = 0; j < list2.size(); j++) {
            if (list2.get(j).code.equals(item)){
                System.out.println(j);
                position = j;
            }
        }
        document_Name.setText(list2.get(position).name);
    }
}
