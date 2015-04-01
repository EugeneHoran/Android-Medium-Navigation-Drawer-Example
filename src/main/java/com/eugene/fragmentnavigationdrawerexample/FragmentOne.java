package com.eugene.fragmentnavigationdrawerexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentOne extends Fragment {
    private FragmentCallbacks mCallbacks;

    public static interface FragmentCallbacks {
        void menuClick();

        void openRandomFrag();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (FragmentCallbacks) activity;
            Log.e("onAttach", "Attached");
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement Fragment One.");
        }
    }

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
        } else {
            Log.e("restoreInstanceState", "Not Restored, Nothing Saved");
        }
        setEditTextSaved();
        getStringFromActivity();
        openRandomFragment();
        openRandomActivity();
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
        if (strEditTextSaved != null)
            outState.putString("edit_text_saved", etEditText.getText().toString());
    }

    Toolbar toolbar;
    TextView txtFragments;
    EditText etEditText;
    Button btnOpenRandomFrag, btnOpenRandomActivity;

    private void findViewsById() {
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.fragment_one));
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_menu_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.menuClick();
            }
        });
        txtFragments = (TextView) v.findViewById(R.id.txtFragments);
        txtFragments.setText(getResources().getString(R.string.fragment_one));
        etEditText = (EditText) v.findViewById(R.id.etEditText);
        btnOpenRandomFrag = (Button) v.findViewById(R.id.btnOpenRandomFrag);
        btnOpenRandomActivity = (Button) v.findViewById(R.id.btnOpenRandomActivity);
    }

    private void getStringFromActivity() { // Receiving data from activity
        Bundle bundle = getArguments();
        Toast.makeText(getActivity(), bundle.get("from_activity").toString(), Toast.LENGTH_SHORT).show();
    }

    private void openRandomFragment() { // Open another fragment
        btnOpenRandomFrag.setVisibility(View.VISIBLE);
        btnOpenRandomFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.openRandomFrag();
            }
        });
    }

    private void openRandomActivity() {
        btnOpenRandomActivity.setVisibility(View.VISIBLE);
        btnOpenRandomActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RandomActivity.class);
                getActivity().startActivity(i);
            }
        });
    }
}
