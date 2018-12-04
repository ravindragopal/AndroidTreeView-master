package com.unnamed.b.atv.sample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.unnamed.b.atv.sample.entity.Entity;

import java.util.List;

/**
 * @author : Dhanashree Chavan, Pune.
 * @since : 13-Apr-2018, 11:19 AM.
 * For : Crest Venue & Entertainment Software Pvt Ltd, Pune.
 */
public class SingleSelectionDialog extends AlertDialog.Builder implements DialogInterface.OnClickListener, View.OnClickListener {

    private boolean mIsResetButtonDisable;

    private String mNoDataFound;
    private String mMandatoryMessage;

    private TextView mTextViewTitle;

    private List<Entity> mListOfItems;
    private List<Entity> mListOfAllItems;

    private AlertDialog mAlertDialog;

    private final Context mContext;
    private final Entity mDefaultSelectedEntity;

    private final SingleSelectionDialogListener mSelectionListener;

    private SingleSelectionAdapter mSingleSelectionAdapter;

    public SingleSelectionDialog(@NonNull Context context, SingleSelectionDialogListener selectionDialogListener,
                                 Entity defaultSelectedEntity, List<Entity> list,
                                 boolean isResetButtonDisable) {
        super(context/*, R.style.MyAlertDialogTheme*/);
        mContext = context;
        mDefaultSelectedEntity = defaultSelectedEntity;
        mSelectionListener = selectionDialogListener;
        mListOfItems = list;
        mIsResetButtonDisable = isResetButtonDisable;

        int index = mListOfItems.indexOf(mDefaultSelectedEntity);

        initDialog(index);
    }

    /**
     * This constructor is used for link location section functionality.
     *
     * @param context                 context
     * @param selectionDialogListener listener
     * @param defaultSelectedEntity   selected location or section
     * @param list                    of all locations or sections
     * @param linkedItemList          list of linked locations or sections
     * @param isResetButtonDisable    whether to disable reset button or not
     */
    public SingleSelectionDialog(@NonNull Context context, SingleSelectionDialogListener selectionDialogListener,
                                 Entity defaultSelectedEntity, List<Entity> list, List<Entity> linkedItemList,
                                 boolean isResetButtonDisable) {
        super(context);

        mContext = context;
        mDefaultSelectedEntity = defaultSelectedEntity;
        mSelectionListener = selectionDialogListener;
        mIsResetButtonDisable = isResetButtonDisable;
        mListOfAllItems = list;

        //if linkedItemList is not empty then show below list in selection dialog.
        if (linkedItemList != null && !linkedItemList.isEmpty()) {
            mListOfItems = linkedItemList;
        } else {
            mListOfItems = list;
        }

        int index = mListOfItems.indexOf(mDefaultSelectedEntity);

        initDialog(index);
    }

    private void initDialog(int defaultSelected) {

        mTextViewTitle = new TextView(mContext);
        mTextViewTitle.setText(mContext.getString(R.string.app_name));
        mTextViewTitle.setMaxLines(2);
        mTextViewTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTextViewTitle.setTextColor(ContextCompat.getColor(mContext, R.color.large_text_view));
        mTextViewTitle.setTextSize(20);

        Typeface typeface = Typeface.DEFAULT;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTextViewTitle.setTypeface(typeface, Typeface.BOLD);
        } else {
            mTextViewTitle.setTypeface(typeface);
        }

        int leftRightPadding = convertDpToPx(mContext, 26);
        int topBottomPadding = convertDpToPx(mContext, 14);

        mTextViewTitle.setPadding(leftRightPadding, topBottomPadding, leftRightPadding, topBottomPadding);
        setCustomTitle(mTextViewTitle);

        mNoDataFound = "No data found";
        mMandatoryMessage = "Please select atleast one";

        if (!isResetButtonDisable()) {
            setNeutralButton(mContext.getString(R.string.dialog_reset), this);
        }

        setPositiveButton(mContext.getString(R.string.dialog_ok), this);
        setNegativeButton(mContext.getString(R.string.dialog_cancel), this);

        TypedArray typedArray = mContext.obtainStyledAttributes(null, android.support.v7.appcompat.R.styleable.AlertDialog,
                android.support.v7.appcompat.R.attr.alertDialogStyle, 0);

        int singleChoiceItemLayoutId = typedArray
                .getResourceId(android.support.v7.appcompat.R.styleable.AlertDialog_singleChoiceItemLayout, 0);

        mSingleSelectionAdapter = new SingleSelectionAdapter(mContext, singleChoiceItemLayoutId);

        setSingleChoiceItems(mSingleSelectionAdapter, defaultSelected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long id = mSingleSelectionAdapter.getItem(which).getKey();

                /*
                 * If selected item position is at 0th index and if id is -1 then it is 'Show all' entity.'
                 * In this case we need to show all locations and sections data in selection dialog
                 */
                if (which == 0 && id == -1) {
                    mListOfItems.clear(); //clear current list of items in selection dialog
                    mListOfItems.addAll(mListOfAllItems); // add all items in selection dialog
                    mSingleSelectionAdapter.notifyDataSetInvalidated(); // notify adapter about data change

                    // as 'Show All' will be checked so we don't want first item to be checked so unchecked it.
                    mAlertDialog.getListView().setItemChecked(which, false);

                    // get item which was selected so that we can show it as checked in selection dialog list
                    int index = mListOfItems.indexOf(mDefaultSelectedEntity);

                    mAlertDialog.getListView().setItemChecked(index, true);
                } else {
                    mAlertDialog.getListView().setItemChecked(which, true);
                }
            }
        });
    }

    public void setTitle(String title) {
        mTextViewTitle.setText(title);
    }

    public void setEmptyString(String emptyString) {
        mNoDataFound = emptyString;
    }

    public void setMandatoryMessage(String mandatoryMessage) {
        mMandatoryMessage = mandatoryMessage;
    }

    private boolean isResetButtonDisable() {
        return mIsResetButtonDisable;
    }

    public void setResetButtonDisable(boolean resetButtonDisable) {
        this.mIsResetButtonDisable = resetButtonDisable;
    }

    @Override
    public AlertDialog show() {
        mAlertDialog = this.create();
        mAlertDialog.getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        if (mListOfItems.size() > 0) {
            mAlertDialog.show();
            mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(this);
            mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(this);
            mAlertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(this);
        } else {
            Toast.makeText(mContext, mNoDataFound, Toast.LENGTH_SHORT).show();
        }
        return mAlertDialog;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case android.R.id.button1:
                int index = mAlertDialog.getListView().getCheckedItemPosition();
                if (index >= 0) {
                    Entity entity = mListOfItems.get(index);
                    mSelectionListener.onSelected(entity, mAlertDialog);
                } else {
                    Toast.makeText(mContext, mMandatoryMessage, Toast.LENGTH_SHORT).show();
                }
                break;

            case android.R.id.button2:
                mSelectionListener.onCancel();
                mAlertDialog.dismiss();
                break;

            case android.R.id.button3:
                mSelectionListener.onReset();
                mAlertDialog.dismiss();
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    private class SingleSelectionAdapter extends ArrayAdapter<Entity> {

        SingleSelectionAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
        }

        @Nullable
        @Override
        public Entity getItem(int position) {
            return mListOfItems.get(position);
        }

        @Override
        public int getCount() {
            return mListOfItems.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final CheckedTextView view = (CheckedTextView) super.getView(position, convertView, parent);
            view.setChecked(mListOfItems.get(position).getSelected());
            return view;
        }
    }

    public interface SingleSelectionDialogListener {
        void onSelected(Entity selectedEntity, AlertDialog alertDialog);

        void onReset();

        void onCancel();
    }

    public static int convertDpToPx(Context context, int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics()));
    }
}