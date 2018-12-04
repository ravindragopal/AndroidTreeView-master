package com.unnamed.b.atv.sample.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.sample.Pref;
import com.unnamed.b.atv.sample.R;

import org.w3c.dom.Node;

public class NodeHeaderHolder extends TreeNode.BaseNodeViewHolder<NodeHeaderHolder.TreeHeaderHolder> {

    private Context mContext;

    private TextView mTextViewHeader;

    private CheckBox mCheckboxChild;

    private TreeNode mTreeNode;

    public NodeHeaderHolder(Context context) {
        super(context);
        mContext = context;
    }

    private String mNodeValue = "";
    private long mNodeKey = 0L;

    public static Handler mHandler;

    @SuppressLint("HandlerLeak")
    @Override
    public View createNodeView(final TreeNode node, final TreeHeaderHolder value) {
        @SuppressLint("InflateParams") final View view = LayoutInflater.from(mContext).inflate(R.layout.layout_node_holder,
                null, false);

        mTextViewHeader = view.findViewById(R.id.txtNestedListHeader);

        TextView textViewChildCount = view.findViewById(R.id.txtNestedChildCount);

        mCheckboxChild = view.findViewById(R.id.chkNestedListChild);

        mNodeValue = value.getHeaderText();
        mNodeKey = value.getHeaderId();

        mCheckboxChild.setText(mNodeValue);
        mCheckboxChild.setTag(mNodeKey);

        mTreeNode = node;

        textViewChildCount.append(mTreeNode.getChildren().size() + ")");

        mCheckboxChild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                getTreeView().deselectAll();//deselect all nodes

//                Log.d("@@level", node.getPath().charAt(node.getPath().length()-1) + "\n");
//                Log.d("@@level", node.getId() + "\n");


                if (isChecked) {
                    mTreeNode.setSelected(isChecked);//selected node
                    mCheckboxChild.setChecked(mTreeNode.isSelected());

                    value.getNodeHeaderInterface().onNodeSelected(mNodeKey, mNodeValue);
                    value.getNodeHeaderInterface().onSelectedNode(node.getParent());

                    Pref.getInstance().putInt("level", mTreeNode.getLevel());
                    Pref.getInstance().putString("row", "" + node.getPath().charAt(node.getPath().length() - 1));
                    Pref.getInstance().putTreeNode(mTreeNode);
                } else {
                    value.getNodeHeaderInterface().onNodeSelected(0, "");
                    value.getNodeHeaderInterface().onSelectedNode(node.getParent());
                    Pref.getInstance().putInt("level", 0);
                    Pref.getInstance().putTreeNode(mTreeNode);
                }
            }
        });

        if (mTreeNode.getChildren().isEmpty()) {
            mTextViewHeader.setVisibility(View.INVISIBLE);
            textViewChildCount.setVisibility(View.GONE);
        }

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mCheckboxChild.setChecked(true);
            }
        };

        return view;
    }

    @Override
    public void toggle(boolean active) {
        if (!mTreeNode.getChildren().isEmpty()) {
            mTextViewHeader.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext,
                    active ? R.drawable.wrapper_down_arrow : R.drawable.wrapper_right_arrow), null, null, null);
        }
        super.toggle(active);
    }

    @Override
    public void toggleSelectionMode(boolean editModeEnabled) {
        mCheckboxChild.setChecked(mNode.isSelected());
    }

    public static class TreeHeaderHolder {

        String headerText;
        long headerId;
        NodeHeaderInterface nodeHeaderInterface;

        public TreeHeaderHolder(long id, String textHeader, NodeHeaderInterface nodeHeaderInterface) {
            this.headerText = textHeader;
            this.headerId = id;
            this.nodeHeaderInterface = nodeHeaderInterface;
        }

        public TreeHeaderHolder(String textHeader) {
            this.headerText = textHeader;
        }

        private String getHeaderText() {
            return headerText;
        }

        private long getHeaderId() {
            return headerId;
        }

        private NodeHeaderInterface getNodeHeaderInterface() {
            return nodeHeaderInterface;
        }
    }

    public interface NodeHeaderInterface {
        void onNodeSelected(long key, String value);

        void onSelectedNode(TreeNode treeNode);
    }
}
