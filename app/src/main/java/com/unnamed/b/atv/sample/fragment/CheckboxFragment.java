/*
package com.unnamed.b.atv.sample.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.sample.R;
import com.unnamed.b.atv.sample.holder.ChildHolder;
import com.unnamed.b.atv.sample.holder.IconTreeItemHolder;
import com.unnamed.b.atv.sample.holder.PlainHeaderHolder;
import com.unnamed.b.atv.view.AndroidTreeView;

*/
/**
 * @author : Mahesh Bhalerao, Pune.
 * @since : 07-Aug-2018, 12:48 PM.
 * For : Crest Venue & Entertainment Software Pvt Ltd, Pune.
 *//*

public class CheckboxFragment extends Fragment {

    private AndroidTreeView tView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_default, null, false);
        final ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);
        rootView.findViewById(R.id.status_bar).setVisibility(View.GONE);

        final TreeNode root = TreeNode.root();

        TreeNode facilityOne = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder("Facility One")).setViewHolder(new NodeHeaderHolder(getActivity()));
        TreeNode facilityTwo = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder("Facility Two")).setViewHolder(new NodeHeaderHolder(getActivity()));
        TreeNode facilityThree = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder("Facility Three")).setViewHolder(new NodeHeaderHolder(getActivity()));
        TreeNode facilityFour = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder("Facility Four")).setViewHolder(new NodeHeaderHolder(getActivity()));

        addFacilityData(facilityOne);
        addFacilityData(facilityTwo);
        addFacilityData(facilityThree);
        addFacilityData(facilityFour);

        root.addChildren(facilityOne, facilityTwo, facilityThree, facilityFour);

        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        containerView.addView(tView.getView());

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }

        return rootView;
    }

    private void addFacilityData(TreeNode treeNode) {
        TreeNode department1 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder("Department 45")).setViewHolder(new NodeHeaderHolder(getActivity()));
        TreeNode department2 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder("Department 74")).setViewHolder(new NodeHeaderHolder(getActivity()));
        TreeNode department3 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder("Department 82")).setViewHolder(new NodeHeaderHolder(getActivity()));

        TreeNode location1 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder("Location 36")).setViewHolder(new NodeHeaderHolder(getActivity()));
        TreeNode location2 = new TreeNode(new NodeHeaderHolder.TreeHeaderHolder("Location 12")).setViewHolder(new NodeHeaderHolder(getActivity()));

        TreeNode section1 = new TreeNode(new ChildHolder.ChildItem("Section 1425")).setViewHolder(new ChildHolder(getActivity()));
        TreeNode section2 = new TreeNode(new ChildHolder.ChildItem("Section 378")).setViewHolder(new ChildHolder(getActivity()));
        TreeNode section3 = new TreeNode(new ChildHolder.ChildItem("Section 459")).setViewHolder(new ChildHolder(getActivity()));
        TreeNode section4 = new TreeNode(new ChildHolder.ChildItem("Section 972")).setViewHolder(new ChildHolder(getActivity()));

        location1.addChildren(section1, section2, section3, section4);
        location2.addChildren(section1, section2, section3, section4);

        department1.addChildren(location1, location2);
        department2.addChildren(location1, location2);
        department3.addChildren(location1, location2);

        treeNode.addChildren(department1, department2, department3);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }
}*/
