package com.eugene.fragmentnavigationdrawerexample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class RandomFragment extends Fragment {
    private FragmentBackStack mCallbacks;

    public static interface FragmentBackStack {
        void menuPopBack();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (FragmentBackStack) activity;
            Log.e("onAttach", "Attached");
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement Fragment One.");
        }
    }

    Fragment fragment;

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
        Log.e("onDetach", "Detached");

    }

    private View v;
    private String strEditTextSaved;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_layout, container, false);
        findViewsById();
        if (savedInstanceState != null) {
            strEditTextSaved = savedInstanceState.getString("edit_text_saved");
            Log.e("restoreInstanceState", "Restored");
        }
        setEditTextSaved();
        return v;
    }

    private void setEditTextSaved() {
        if (strEditTextSaved != null) {
            etEditText.setText(strEditTextSaved);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e("onSaveInstanceState", "Saved");
        outState.putString("edit_text_saved", etEditText.getText().toString());
    }

    Toolbar toolbar;
    TextView txtFragments;
    EditText etEditText;

    private void findViewsById() {
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.random_fragment));
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_back_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.menuPopBack();
            }
        });
        txtFragments = (TextView) v.findViewById(R.id.txtFragments);
        txtFragments.setText(getResources().getString(R.string.random_fragment));
        etEditText = (EditText) v.findViewById(R.id.etEditText);
    }
}
