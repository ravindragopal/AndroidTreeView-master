package com.unnamed.b.atv.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;

public class ChildHolderSingleSelect extends TreeNode.BaseNodeViewHolder<ChildHolderSingleSelect.ChildItem> {

    private Context mContext;

    private RadioButton nodeSelector;

    public ChildHolderSingleSelect(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View createNodeView(final TreeNode node, ChildItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_selectable_item_radio_button, null, false);

        nodeSelector = view.findViewById(R.id.radioButtonNestedListChild);

        nodeSelector.setText(value.getChildText());
        nodeSelector.setSelected(value.isChecked());

        nodeSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (nodeSelector.getParent() instanceof RadioGroup) {
                        ((RadioGroup) nodeSelector.getParent()).clearCheck();
                    }
                } else {
                    nodeSelector.setSelected(!nodeSelector.isChecked());
                }
            }
        });

        return view;
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
