package com.unnamed.b.atv.sample.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.sample.R;
import com.unnamed.b.atv.view.AndroidTreeView;

/**
 * @author : Mahesh Bhalerao, Pune.
 * @since : 07-Aug-2018, 6:34 PM.
 * For : Crest Venue & Entertainment Software Pvt Ltd, Pune.
 */
public class ChildHolder extends TreeNode.BaseNodeViewHolder<ChildHolder.ChildItem> {

    private Context mContext;

    private CheckBox mCheckBoxChild;

    private AndroidTreeView mAndroidTreeView;

    public ChildHolder(Context context) {
        super(context);
        mContext = context;
    }

    public ChildHolder(Context context, AndroidTreeView androidTreeView) {
        super(context);
        mContext = context;
        mAndroidTreeView = androidTreeView;
    }

    @Override
    public View createNodeView(final TreeNode node, final ChildItem value) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.layout_child_new, null, false);
        mCheckBoxChild = view.findViewById(R.id.chkNestedListChild);
        mCheckBoxChild.setText(value.getChildText());
        mCheckBoxChild.setChecked(value.isChecked());

        mCheckBoxChild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAndroidTreeView.deselectAll();
                node.setSelected(isChecked);
            }
        });
        mCheckBoxChild.setChecked(node.isSelected());

        return view;
    }

    @Override
    public void toggle(boolean active) {

        /*mCheckBoxChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCheckBoxChild.setChecked(!mCheckBoxChild.isChecked());
            }
        });*/
        super.toggle(active);
    }

    @Override
    public void toggleSelectionMode(boolean editModeEnabled) {
        /*mCheckBoxChild.setVisibility(editModeEnabled ? View.VISIBLE : View.GONE);*/
        /*nodeSelector.setChecked(mNode.isSelected());*/
    }

    public static class ChildItem {

        private boolean isChecked = false;

        private String childText;

        public ChildItem(String text) {
            this.childText = text;
        }

        public ChildItem(String text, boolean checked) {
            this.childText = text;
            this.isChecked = checked;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public String getChildText() {
            return childText;
        }
    }
}
