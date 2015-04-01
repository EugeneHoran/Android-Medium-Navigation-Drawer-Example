package com.eugene.fragmentnavigationdrawerexample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentTwo extends Fragment {
    private FragmentCallbacks mCallbacks;

    public static interface FragmentCallbacks {
        void menuClick();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (FragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement Fragment Two.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    private View v;
    private String strEditTextSaved;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_layout, container, false);
        findViewsById();
        if (savedInstanceState != null) {
            strEditTextSaved = savedInstanceState.getString("edit_text_saved");
        }
        setEditTextSaved();
        getStringFromActivity();
        return v;
    }

    private void setEditTextSaved() {
        if (strEditTextSaved != null) {
            etEditText.setText(strEditTextSaved);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("edit_text_saved", etEditText.getText().toString());
    }

    Toolbar toolbar;
    TextView txtFragments;
    EditText etEditText;

    private void findViewsById() {
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.fragment_two));
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_menu_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.menuClick();
            }
        });
        txtFragments = (TextView) v.findViewById(R.id.txtFragments);
        txtFragments.setText(getResources().getString(R.string.fragment_two));
        etEditText = (EditText) v.findViewById(R.id.etEditText);
    }

    private void getStringFromActivity() {
        Bundle bundle = getArguments();
        Toast.makeText(getActivity(), bundle.get("from_activity").toString(), Toast.LENGTH_SHORT).show();
    }
}
