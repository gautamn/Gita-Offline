package com.gita.holybooks.bhagwatgita.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gita.holybooks.bhagwatgita.R;


public class ShareAppFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    static ShareAppFragment fragment;

    public ShareAppFragment() {
    }

    public static ShareAppFragment newInstance(String param1, String param2) {
        fragment = new ShareAppFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Share the Gita App");

        /*toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Toast.makeText(getContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
                //getActivity().getFragmentManager().beginTransaction().remove(this.fragment).commit();
            }
        });*/


        return inflater.inflate(R.layout.activity_share, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public boolean allowBackPressed() {

        return true;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
