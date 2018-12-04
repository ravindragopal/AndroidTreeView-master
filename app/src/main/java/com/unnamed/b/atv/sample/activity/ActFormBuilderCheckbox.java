package com.unnamed.b.atv.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.sample.DialogNestedPicker;
import com.unnamed.b.atv.sample.NestedPickerDialog;
import com.unnamed.b.atv.sample.Pref;
import com.unnamed.b.atv.sample.R;
import com.unnamed.b.atv.sample.entity.Contact;
import com.unnamed.b.atv.sample.entity.Entity;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActFormBuilderCheckbox extends AppCompatActivity implements NestedPickerDialog.SelectionDialogListener {

    private JSONObject jsonObject = null;

    private String mSelectedNode = "";

    private TextInputEditText mTextInputLayoutJSON;
    private TextInputEditText mTextInputLayoutList;

    public AndroidTreeView androidTreeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_form_builder_layout);

        Pref.initializePref(getApplicationContext());

        initialiseDialogFragmentsJSONData();

        initialiseAlertDialogListData();
    }

    private void initialiseDialogFragmentsJSONData() {
        final int[] ids = {1, 2, 3, 4, 5};

        final String[] facilities = {"facility 1", "facility 2", "facility 3", "facility 4", "facility 5"};

        try {
            jsonObject = makeJsonObject(ids, facilities, 3);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextInputLayout textInputLayout = findViewById(R.id.tilDialogFragmentJsonData);
        mTextInputLayoutJSON = findViewById(R.id.tieDialogFragmentJsonData);

        if (textInputLayout.getEditText() != null) {
            textInputLayout.getEditText().setKeyListener(null);
        }

        textInputLayout.getEditText().setId(R.id.tilDialogFragmentJsonData);
        textInputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NestedPickerDialog nestedPickerDialog = new NestedPickerDialog();
                Bundle bundle = new Bundle();
                bundle.putString("json", jsonObject.toString());

                nestedPickerDialog.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(nestedPickerDialog, "NestedDialogFragment");
                fragmentTransaction.commit();
            }
        });
    }

    private void initialiseAlertDialogListData() {
        TextInputLayout textInputLayout = findViewById(R.id.tilAlertDialogListData);
        mTextInputLayoutList = findViewById(R.id.tieAlertDialogListData);

        final TreeNode root = TreeNode.root();
        androidTreeView = new AndroidTreeView(this, root);
        androidTreeView.setDefaultAnimation(true);
        androidTreeView.setSelectionModeEnabled(true);
        androidTreeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);

        if (textInputLayout.getEditText() != null) {
            textInputLayout.getEditText().setKeyListener(null);
        }

        textInputLayout.getEditText().setId(R.id.tilDialogFragmentJsonData);
        textInputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogNestedPicker dialogNestedPicker = new DialogNestedPicker(root,androidTreeView,ActFormBuilderCheckbox.this, getContactList(), new DialogNestedPicker.SelectionDialogListener() {
                    @Override
                    public void selectedNode(long key, String selectedEntityValue) {
                        mTextInputLayoutList.setText(selectedEntityValue);
                    }

                });
                dialogNestedPicker.setMandatoryMessage("Please select data");
                dialogNestedPicker.setNoDataFoundMessage("No data found");
                dialogNestedPicker.setTitle("Nested picker dialog");
                dialogNestedPicker.show();
            }
        });
    }

    public JSONObject makeJsonObject(int id[], String name[], int number_of_students) throws JSONException {
        JSONObject childObject1;
        JSONObject childObject2;
        JSONObject object1 = new JSONObject();
        JSONObject object2 = new JSONObject();
        JSONObject object3 = new JSONObject();
        JSONObject object4 = new JSONObject();
        JSONObject object5 = new JSONObject();
        JSONObject object6 = new JSONObject();

        JSONArray jsonArray1 = new JSONArray();
        for (int i = 0; i < number_of_students; i++) {
            childObject1 = new JSONObject();
            try {
                childObject1.put("id", id[i]);
                childObject1.put("name", name[i]);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray1.put(childObject1);
        }

        JSONArray jsonArray2 = new JSONArray();
        for (int i = 0; i < number_of_students; i++) {
            childObject2 = new JSONObject();
            try {
                childObject2.put("id", id[i]);
                childObject2.put("name", name[i]);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray2.put(childObject2);
        }

        JSONObject finalOject1 = new JSONObject();
        finalOject1.put("Incident facilities", jsonArray1);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dc", 1);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        object1.put("Pune Facility", jsonArray);
        object2.put("Pune Department", object3);
        object3.put("Pune Location", object4);
        object4.put("Pune Section", object5);
        object5.put("Facilities with special characters12345!@#$%^&**(__/>,<>'{}", object6);
        object6.put("Devices", finalOject1);

        return object1;
    }

    private ArrayList<Entity> getContactList() {
        ArrayList<Entity> contacts = new ArrayList<>();
        Contact contact = new Contact();
        contact.setContactId(1);
        contact.setContactName("Pune Facility");
        contacts.add(contact);

        Contact contact1 = new Contact();
        contact1.setContactId(2);
        contact1.setContactName("Pacific Facility");
        contacts.add(contact1);

        Contact contact2 = new Contact();
        contact2.setContactId(3);
        contact2.setContactName("Delhi Facility");
        contacts.add(contact2);

        Contact contact3 = new Contact();
        contact3.setContactId(4);
        contact3.setContactName("Long name facility with special characters12345!@#$%^&**(__/>,<>'{}");
        contacts.add(contact3);

        return contacts;
    }

    @Override
    public void selectedNode(long key, String selectedNode) {
        mTextInputLayoutJSON.setText(selectedNode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Pref.getInstance().clearSharedPref();
    }
}
