package com.unnamed.b.atv.sample;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.unnamed.b.atv.model.TreeNode;

public class Pref {

    private SharedPreferences mSharedPreferences;

    private final String PREF_NAME = "CMMS_PREF";

    private final String TREE_NODE = "tree_node";
    private final String TREE_NODE_ID = "tree_node_id";

    private static Pref mPref;

    /**
     * App Constants
     */
    public final String REMEMBER_USERNAME = "rememberUserName";


    /**
     * The constructor made private here so that one cannot
     * instantiate this class furthermore and must use it
     * as a singleton throughout the Application
     *
     * @param context Application context
     */
    public Pref(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
    }

    public static void initializePref(Context context) {
        if (mPref == null) {
            mPref = new Pref(context);
        }
    }

    public static Pref getInstance() {
        return mPref;
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    /**
     * To put a value in shared preferences with provided key
     */
    public void putInt(String key, int value) {

        mSharedPreferences.edit().putInt(key, value).apply();
    }


    public void putLong(String key, long value) {

        mSharedPreferences.edit().putLong(key, value).apply();
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    /**
     * To get a value from shared preferences with provided key
     */
    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    /**
     * To put a value in shared preferences with provided key
     */
    public void putString(String key, String value) {

        mSharedPreferences.edit().putString(key, value).apply();
    }

    /**
     * To get a value from shared preferences with provided key
     */
    public String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public void putTreeNode(TreeNode treeNode) {

        /*Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
        String json = gson.toJson(treeNode);
        Log.d("dhanno", "put node 1 : " + json);*/

        mSharedPreferences.edit().putString(TREE_NODE, String.valueOf(treeNode)).apply();
    }

    public String getTreeNode() {
        /*Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
        String json = mSharedPreferences.getString(key, "");
        TreeNode treeNode = gson.fromJson(json, TreeNode.class);
        Log.d("dhanno", "get node 1 : " + json);*/
        return mSharedPreferences.getString(TREE_NODE, "");
    }

    public void clearSharedPref() {
        mSharedPreferences.edit().clear().apply();
    }
}