package com.unnamed.b.atv.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dhanashree C.
 *
 * @SINCE : 30/8/18
 * FOR: Crest Venue & Entertainment Software Pvt Ltd, Pune.
 */
public class ActNestedPickerDialog extends AppCompatActivity {

    private JSONObject jsonObject = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null) {
            try {
                jsonObject = new JSONObject(getIntent().getExtras().getString("json"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        NestedPickerDialog nestedPickerDialog = new NestedPickerDialog();
        Bundle bundle = new Bundle();
        bundle.putString("json", jsonObject.toString());

        nestedPickerDialog.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(nestedPickerDialog, "NestedDialogFragment");
        fragmentTransaction.commit();
    }
}
