package com.unnamed.b.atv.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.sample.entity.Entity;
import com.unnamed.b.atv.sample.holder.NodeHeaderHolder;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DialogNestedPicker extends AlertDialog.Builder implements DialogInterface.OnClickListener, View.OnClickListener,
        NodeHeaderHolder.NodeHeaderInterface {

    private String mSelectedNodeValue;
    private String mEmptyString;
    private String mMandatoryMessage;

    private long mSelectedNodeKey;

    private TreeNode mSelectedNode = TreeNode.root();

    private Context mContext;

    private ArrayList<Entity> mListOfMainNodes = new ArrayList<>();

    private SelectionDialogListener mSelectionDialogListener;

    private StatusEntity mSelectAllEntity;

    private AlertDialog mAlertDialog;

    private View mView;

    private JSONObject mJsonObject;

    public  AndroidTreeView androidTreeView;

    public TreeNode root;

    public DialogNestedPicker(TreeNode root, AndroidTreeView androidTreeView, @NonNull Context context, @NonNull ArrayList<Entity> items,
                              @NonNull SelectionDialogListener selectionDialogListener) {
        super(context);
        mContext = context;

        this.root = root;
        this.androidTreeView = androidTreeView;

        mListOfMainNodes.addAll(items);

        mSelectAllEntity = new StatusEntity(-1, "SELECT ALL");
        mListOfMainNodes.add(0, mSelectAllEntity);

        mSelectionDialogListener = selectionDialogListener;
        initDialog();
    }

    /*public DialogNestedPicker(@NonNull Context context, JSONObject jsonObject,
                              @NonNull SelectionDialogListener selectionDialogListener) {
        super(context);

        mContext = context;
        mJsonObject = jsonObject;
        mSelectAllEntity = new StatusEntity(-1, "SELECT ALL");
        mListOfMainNodes.add(0, mSelectAllEntity);

        mSelectionDialogListener = selectionDialogListener;
        initDialog();
    }*/

    @SuppressLint("SetTextI18n")
    private void initDialog() {

        TextView textViewTitle = new TextView(mContext);
        textViewTitle.setText("Nested picker dialog");
        textViewTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        textViewTitle.setTextSize(20);

        Typeface typeface = Typeface.DEFAULT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textViewTitle.setTypeface(typeface, Typeface.BOLD);
        } else {
            textViewTitle.setTypeface(typeface);
        }

        int leftRightPadding = convertDpToPx(mContext, 26);
        int topBottomPadding = convertDpToPx(mContext, 14);

        textViewTitle.setPadding(leftRightPadding, topBottomPadding, leftRightPadding, topBottomPadding);
        setCustomTitle(textViewTitle);

        mEmptyString = mContext.getString(R.string.no_data_found);
        mMandatoryMessage = mContext.getString(R.string.single_selection_select_on_alert);

        setPositiveButton(mContext.getString(R.string.btn_positive_single_selection_dialog), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        setNegativeButton(mContext.getString(R.string.btn_negative_single_selection_dialog), this);

        mView = createNestedPickerDialog();
    }

    private View createNestedPickerDialog() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View rootView = inflater.inflate(R.layout.fragment_nested_picker, null, false);
        final ViewGroup containerView = rootView.findViewById(R.id.rlNestedPicker);

        Log.d("@@node3", "" + getSelectedNode());

//        final TreeNode root = TreeNode.root();

        /*if(androidTreeView == null) {
            androidTreeView = new AndroidTreeView(mContext, root);
            androidTreeView.setDefaultAnimation(true);
            androidTreeView.setSelectionModeEnabled(true);
            androidTreeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        }*/

        if (mListOfMainNodes.size() > 1) {
            addHardCodedData(root);
        } else {
            addDataFromJSON(root);
        }

        containerView.removeAllViews();
        containerView.addView(androidTreeView.getView());
        root.getLevel();
        //show selected level expanded
        if (Pref.getInstance().getInt("level") > 0) {
//            androidTreeView.expandLevel((Pref.getInstance().getInt("level") - 1));
            Log.d("@@node1", "" + getSelectedNode());
            androidTreeView.expandLevel(getSelectedNode(), (Pref.getInstance().getInt("level") - 1));
//            androidTreeView.expandNode(getSelectedNode());
            Message m = new Message();
            NodeHeaderHolder.mHandler.sendMessage(m);
        }

        return rootView;
    }


    private void addDataFromJSON(TreeNode root) {
        try {
            JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < 10; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", i);
                jsonObject.put("node", "Pune Facility " + i);
                jsonArray.put(jsonObject);
            }

            TreeNode file = null;
            for (int i = 0; i < 10; i++) {
                file = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(jsonArray.getJSONObject(i).getLong("id"),
                        jsonArray.getJSONObject(i).getString("node"), this))
                        .setViewHolder(new NodeHeaderHolder(mContext));
                root.addChild(file);
                root = file;
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

    private void addHardCodedData(TreeNode root) {
        TreeNode puneFacility = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(mListOfMainNodes.get(1).getKey(),
                mListOfMainNodes.get(1).getValue(), this))
                .setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode pacificFacility = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(mListOfMainNodes.get(2).getKey(),
                mListOfMainNodes.get(2).getValue(), this))
                .setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode delhiFacility = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(mListOfMainNodes.get(3).getKey(),
                mListOfMainNodes.get(3).getValue(), this))
                .setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode longNameFour = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(mListOfMainNodes.get(4).getKey(),
                mListOfMainNodes.get(4).getValue(), this))
                .setViewHolder(new NodeHeaderHolder(mContext));

        addFacilityData(puneFacility);
        addFacilityData(pacificFacility);
        addFacilityData(delhiFacility);
        addFacilityData(longNameFour);

        root.addChild(puneFacility);
        root.addChild(pacificFacility);
        root.addChild(delhiFacility);
        root.addChild(longNameFour);
    }

    private void addFacilityData(TreeNode treeNode) {
        TreeNode department1 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(0, "Department 1", this))
                .setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode department2 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(1, "Department 2", this))
                .setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode department3 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(2, "Department 3", this))
                .setViewHolder(new NodeHeaderHolder(mContext));

        TreeNode location = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(3, "Location 1", this))
                .setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode location0 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(4, "Location 2", this))
                .setViewHolder(new NodeHeaderHolder(mContext));

        TreeNode location1 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(5, "Location 1", this))
                .setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode location2 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(6, "Location 2", this))
                .setViewHolder(new NodeHeaderHolder(mContext));

        TreeNode location11 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(7, "Location 1", this))
                .setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode location22 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(8, "Location 2", this))
                .setViewHolder(new NodeHeaderHolder(mContext));

        TreeNode insideLocation = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(9, "Inside Location",
                this)).setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode insideLocation1 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(10, "Inside Location 1",
                this)).setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode insideLocation2 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(11, "Inside Location 2",
                this)).setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode insideLocation3 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(12, "Inside Location 3",
                this)).setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode insideLocation4 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(13, "Inside Location 4",
                this)).setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode insideLocation5 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(14, "Inside Location 5",
                this)).setViewHolder(new NodeHeaderHolder(mContext));

        TreeNode outsideLocation = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(15, "Outside Location",
                this)).setViewHolder(new NodeHeaderHolder(mContext));

        TreeNode insideSection1 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(16, "Inside Section 1",
                this)).setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode insideSection2 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(17, "Inside Section 2",
                this)).setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode insideSection3 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(18, "Inside Section 3",
                this)).setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode insideSection4 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(19, "Inside Section 4 with long name",
                this)).setViewHolder(new NodeHeaderHolder(mContext));

        TreeNode outsideSection1 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(20, "Outside Section 1",
                this)).setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode outsideSection2 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(21, "Outside Section 2",
                this)).setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode outsideSection3 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(22, "Outside Section 3",
                this)).setViewHolder(new NodeHeaderHolder(mContext));
        TreeNode outsideSection4 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder(23, "Outside Section 4",
                this)).setViewHolder(new NodeHeaderHolder(mContext));

        insideLocation5.addChildren(insideSection1, insideSection2, insideSection3, insideSection4);
        outsideLocation.addChildren(outsideSection1, outsideSection2, outsideSection3, outsideSection4);

        insideLocation4.addChild(insideLocation5);
        insideLocation3.addChild(insideLocation4);
        insideLocation2.addChild(insideLocation3);
        insideLocation1.addChild(insideLocation2);
        insideLocation.addChild(insideLocation1);

        location1.addChild(insideLocation);
        location2.addChild(outsideLocation);

        department1.addChildren(location1, location2);
        department2.addChildren(location11, location22);
        department3.addChildren(location, location0);

        treeNode.addChildren(department1, department2, department3);
    }

    @Override
    public AlertDialog show() {
        mAlertDialog = this.create();
        if (mListOfMainNodes.size() > 1 || mJsonObject != null) {
            mAlertDialog.setView(mView);
            mAlertDialog.show();

            //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
            mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(mSelectedNodeValue)) {
                        mSelectionDialogListener.selectedNode(mSelectedNodeKey, mSelectedNodeValue);
                        mAlertDialog.dismiss();
                    } else {
                        Toast.makeText(mContext, mMandatoryMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(mContext, mEmptyString, Toast.LENGTH_SHORT).show();
        }
        return mAlertDialog;
    }

    public void setNoDataFoundMessage(String message) {
        mEmptyString = message;
    }

    public void setMandatoryMessage(String message) {
        mMandatoryMessage = message;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case android.R.id.button1:
                /*String selectedNode = NodeHeaderHolder.getSelectedNode();
                if (TextUtils.isEmpty(selectedNode)) {
                    mSelectionDialogListener.selectedNode(selectedNode);
                } else {
                    Toast.makeText(mContext, mMandatoryMessage, Toast.LENGTH_SHORT).show();
                }*/
                break;

            default:
                break;
        }
    }

    private static int convertDpToPx(Context context, int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics()));
    }

    @Override
    public void onNodeSelected(long key, String value) {
        mSelectedNodeValue = value;
        mSelectedNodeKey = key;
    }

    @Override
    public void onSelectedNode(TreeNode treeNode) {
        Log.d("@@node0", "" + treeNode);
        setSelectedNode(treeNode);
    }

    private void setSelectedNode(TreeNode treeNode) {
        this.mSelectedNode = treeNode;
    }

    private TreeNode getSelectedNode() {
        return this.mSelectedNode;
    }

    public interface SelectionDialogListener {
        void selectedNode(long key, String selectedNode);
    }
}
