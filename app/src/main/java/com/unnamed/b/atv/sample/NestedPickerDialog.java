package com.unnamed.b.atv.sample;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.sample.holder.NodeHeaderHolder;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NestedPickerDialog extends DialogFragment implements NodeHeaderHolder.NodeHeaderInterface {

    private String mSelectedNodeValue;

    private long mSelectedNodeKey;

    private TreeNode mSelectedTreeNode;

    private Context mContext;

    private AlertDialog.Builder mAlertDialogBuilder;

    private AlertDialog mAlertDialog;

    private AndroidTreeView mAndroidTreeView;

    private JSONObject mJsonObject;

    private SelectionDialogListener mSelectionDialogListener;

    TreeNode mRoot;
    TreeNode tRoot;
    TreeNode sRoot;

    public TreeNode selectedTreeNode;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mSelectionDialogListener = (SelectionDialogListener) mContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCancelable(false);
        if (getArguments() != null) {
            try {
                mJsonObject = new JSONObject(getArguments().getString("json"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return initDialog();
    }

    @SuppressLint("SetTextI18n")
    private AlertDialog initDialog() {

        mAlertDialogBuilder = new AlertDialog.Builder(mContext);

        TextView textViewDialogTitle = new TextView(mContext);
        textViewDialogTitle.setText("Nested picker dialog");
        textViewDialogTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        textViewDialogTitle.setTextSize(20);

        Typeface typeface = Typeface.DEFAULT;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textViewDialogTitle.setTypeface(typeface, Typeface.BOLD);
        } else {
            textViewDialogTitle.setTypeface(typeface);
        }

        int leftRightPadding = convertDpToPx(mContext, 26);
        int topBottomPadding = convertDpToPx(mContext, 14);

        textViewDialogTitle.setPadding(leftRightPadding, topBottomPadding, leftRightPadding, topBottomPadding);
        mAlertDialogBuilder.setCustomTitle(textViewDialogTitle);

        mAlertDialogBuilder.setPositiveButton(mContext.getString(R.string.btn_positive_single_selection_dialog),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelectionDialogListener.selectedNode(mSelectedNodeKey, mSelectedNodeValue);
                    }
                });

        mAlertDialogBuilder.setNegativeButton(mContext.getString(R.string.btn_negative_single_selection_dialog), null);

        mAlertDialog = mAlertDialogBuilder.create();
        mAlertDialog.setView(createNestedPickerDialog(), 0, 0, 0, 0);

        return mAlertDialog;
    }

    private View createNestedPickerDialog() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View rootView = inflater.inflate(R.layout.fragment_nested_picker, null, false);
        final ViewGroup containerView = rootView.findViewById(R.id.rlNestedPicker);

        final TreeNode root = TreeNode.root();

        addDataFromJSON(root);

        mAndroidTreeView = new AndroidTreeView(mContext, root);
        mAndroidTreeView.setDefaultAnimation(true);
        mAndroidTreeView.setSelectionModeEnabled(true);
        mAndroidTreeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);

        containerView.addView(mAndroidTreeView.getView());
        root.getLevel();
        //show selected level expanded

        if (Pref.getInstance().getLong("nestedkey") > 0) {

//            mAndroidTreeView.expandLevel(Pref.getInstance().getInt("level") - 1);
            if (selectedTreeNode != null) {
//                mAndroidTreeView.expandNode(selectedTreeNode);
//                mAndroidTreeView.expandLevel(Pref.getInstance().getInt("level") - 1);
                mAndroidTreeView.expandNodeLevel(Integer.parseInt(Pref.getInstance().getString("row")),Pref.getInstance().getInt("level") - 1);
//                mAndroidTreeView.expandLevel(Pref.getInstance().getInt("level") - 1);
//                mAndroidTreeView.expandLevel(selectedTreeNode,Pref.getInstance().getInt("level") - 1);

                Message m = new Message();
                NodeHeaderHolder.mHandler.sendMessage(m);
            }

        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void addDataFromJSON(TreeNode root) {
        try {
            mRoot = root;
            sRoot = root;
            tRoot = root;
            int nodeKey = 0;

            JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < 10; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", i);
                jsonObject.put("node", "Pune Facility Long name facility with special characters12345!@#$%^&**(__/>,<>'{}" + i);
                jsonArray.put(jsonObject);
            }

            TreeNode file = null;
            for (int i = 0; i < 5; i++) {
                long key = nodeKey++;
                String value = jsonArray.getJSONObject(i).getString("node");
                file = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(jsonArray.getJSONObject(i).getLong("id"),
                        jsonArray.getJSONObject(i).getString("node"), this))
                        .setKeyValue(key, value)
                        .setViewHolder(new NodeHeaderHolder(mContext));
                if (Pref.getInstance().getLong("nestedkey") == key) {
                    selectedTreeNode = file;
                }
                root.addChild(file);
                root = file;
            }

            for (int i = 0; i < 5; i++) {
                long key = nodeKey++;
                String value = jsonArray.getJSONObject(i).getString("node");
                file = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(key,
                        value, this))
                        .setKeyValue(key, value)
                        .setViewHolder(new NodeHeaderHolder(mContext));
                if (Pref.getInstance().getLong("nestedkey") == key) {
                    selectedTreeNode = file;
                }
                mRoot.addChild(file);
                mRoot = file;
            }

            for (int i = 0; i < 5; i++) {
                long key = nodeKey++;
                String value = jsonArray.getJSONObject(i).getString("node");
                file = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(jsonArray.getJSONObject(i).getLong("id"),
                        jsonArray.getJSONObject(i).getString("node"), this))
                        .setKeyValue(key, value)
                        .setViewHolder(new NodeHeaderHolder(mContext));
                if (Pref.getInstance().getLong("nestedkey") == key) {
                    selectedTreeNode = file;
                }
                tRoot.addChild(file);
                tRoot = file;
            }

            for (int i = 0; i < 5; i++) {
                long key = nodeKey++;
                String value = jsonArray.getJSONObject(i).getString("node");
                file = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(jsonArray.getJSONObject(i).getLong("id"),
                        jsonArray.getJSONObject(i).getString("node"), this))
                        .setKeyValue(key, value)
                        .setViewHolder(new NodeHeaderHolder(mContext));
                if (Pref.getInstance().getLong("nestedkey") == key) {
                    selectedTreeNode = file;
                }
                sRoot.addChild(file);
                sRoot = file;
            }


            for (int j = 0; j < 1; j++) {
                file.addChild(new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(jsonArray.getJSONObject(j).getLong("id"),
                        jsonArray.getJSONObject(j).getString("node"), this))
                        .setViewHolder(new NodeHeaderHolder(mContext)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static int convertDpToPx(Context context, int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics()));
    }

    @Override
    public void onNodeSelected(long key, String value) {
        mSelectedNodeValue = value;
        mSelectedNodeKey = key;
        Pref.getInstance().putLong("nestedkey", key);
    }

    @Override
    public void onSelectedNode(TreeNode treeNode) {

    }

    public interface SelectionDialogListener {
        void selectedNode(long key, String selectedNode);
    }
}