package iks.market.warehouse;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;

import iks.market.warehouse.Database.DocumentsDatabase;
import iks.market.warehouse.Database.Tables.DocPartners;


public class AddDocument extends AppCompatActivity {

    DocumentsDatabase documentsDatabase;

    private static final String LOG_TAG = "DB";
    ArrayList<String> document_vendorname_list = new ArrayList<>();
    ArrayList<String> document_vendorcode_list = new ArrayList<>();
    ListView listView;

    Button btn_back, btn_reset, btn_add;

    VendorAdapter vendorAdapter = new VendorAdapter();

    TextView code, name, vendor_code, vendor_name;
    EditText document_number, document_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        documentsDatabase = DocumentsDatabase.getInstance(this);
        documentsDatabase.partnersDao().getPartnerList();


        listView = findViewById(R.id.vendor_list);

        btn_add = findViewById(R.id.add_btn);
        btn_back = findViewById(R.id.back_btn);
        btn_reset = findViewById(R.id.reset_btn);

        code = findViewById(R.id.vendor_code_static);
        name = findViewById(R.id.vendor_name_static);
        vendor_code = findViewById(R.id.vendor_code);
        vendor_name = findViewById(R.id.vendor_name);

        document_number = findViewById(R.id.doc_number_edit);
        document_description = findViewById(R.id.doc_description_edit);

        listView.setAdapter(vendorAdapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    importDB();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        /*btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              exportDB();
            }
        });*/


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                vendor_name.setText(document_vendorname_list.get(position));
                vendor_code.setText(document_vendorcode_list.get(position));
            }
        });

    /*    btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("document", document_number.getText().toString());
                contentValues.put("description", document_description.getText().toString());
                contentValues.put("vendorcode", vendor_code.getText().toString());
                contentValues.put("vendor", vendor_name.getText().toString());
                long rowID = documentDatabase.insert("documenttable", null, contentValues);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
            }
        });*/




    }

    class VendorAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return document_vendorname_list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.customdocumentlistinside, null);
            TextView document_vendor = convertView.findViewById(R.id.DOCUMENT_VENDOR);
            TextView document_vendor_code = convertView.findViewById(R.id.DOCUMENT_VENDOR_CODE);
            document_vendor.setText(document_vendorname_list.get(position));
            document_vendor_code.setText(document_vendorcode_list.get(position));
            System.out.println("[");


            return convertView;
        }
    }

    public void initVendorDB() {}

    private void importDB() throws IOException {
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        File file = new File(exportDir, "part.csv");
        CsvReader csvReader = new CsvReader();
        csvReader.setFieldSeparator(';');



        
        CsvContainer csv = csvReader.read(file, StandardCharsets.UTF_8);
        System.out.println(csv.getRowCount());


        for (int i = 0; i < csv.getRowCount(); i++) {
            DocPartners partners = new DocPartners(Long.parseLong(csv.getRow(i).getField(0)), csv.getRow(i).getField(1));
            documentsDatabase.partnersDao().insertDocuments(partners);


            String LOG_TAG = "DATABASE";
            Log.d(LOG_TAG, "row inserted, ID = " + i);
        }
    }
}
