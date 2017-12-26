package shm.dim.lab_16;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    private ListAdapter listAdapter;
    private ListView contactList;
    private ArrayList<Contact> contacts;

    Button mDelete, mAdd, mUpdate;
    EditText mName, mPhone;

    String selected_contact_ID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts = new ArrayList<>();
        contactList = findViewById(R.id.list);

        mAdd =  findViewById(R.id.button_add);
        mDelete = findViewById(R.id.button_delete);
        mUpdate = findViewById(R.id.button_update);

        loadContacts();

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object  selected = contactList.getItemAtPosition(position);
                Contact cont;
                cont = (Contact) selected;
                selected_contact_ID = cont.getID();
            }
        });


        mName = findViewById(R.id.name);
        mPhone = findViewById(R.id.phone);


        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone, name;
                phone = mPhone.getText().toString();
                name = mName.getText().toString();
                ArrayList <ContentProviderOperation> ops = new ArrayList<>();

                ops.add(ContentProviderOperation.newInsert(
                        ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build());

                //Names
                if (name != null) {
                    ops.add(ContentProviderOperation.newInsert(
                            ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                            .withValue(
                                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                    name).build());
                }

                //Mobile Number
                if (phone != null) {
                    ops.add(ContentProviderOperation.
                            newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                            .build());
                }

                try {
                    getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected_contact_ID != null) {
                    final ArrayList ops = new ArrayList();
                    final ContentResolver cr = getContentResolver();
                    ops.add(ContentProviderOperation
                            .newDelete(ContactsContract.RawContacts.CONTENT_URI)
                            .withSelection(
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                            + " = ?",
                                    new String[]{selected_contact_ID})
                            .build());

                    try {
                        cr.applyBatch(ContactsContract.AUTHORITY, ops);
                        ops.clear();
                    } catch (OperationApplicationException e) {
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Выберите контакт", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void loadContacts() {
        String phoneNumber, phoneID;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null,null, null, null);

        if(cursor != null){
            while (cursor.moveToNext()) {
                Contact contact = new Contact();

                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {
                    contact.setName(contactName);

                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { contactId }, null);

                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneID = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

                        contact.setPhoneNumber(phoneNumber);
                        contact.setID(phoneID);
                    }
                    phoneCursor.close();
                }
                contacts.add(contact);
            }
        }

        listAdapter = new ListAdapter(MainActivity.this, R.layout.item, contacts);
        contactList.setAdapter(listAdapter);
    }
}