package com.gita.holybooks.bhagwatgita.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.util.DataUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChapterSliderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChapterSliderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChapterSliderFragment extends Fragment {

    private String title;
    private String position;
    private String shlokaId;
    private Button bookMarkButton;

    private OnFragmentInteractionListener mListener;

    public ChapterSliderFragment() {}

    public static ChapterSliderFragment newInstance(String title, String position) {
        ChapterSliderFragment fragment = new ChapterSliderFragment();
        Bundle args = new Bundle();
        args.putString("position", position);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shlokaId = getArguments().getString("shlokaId", "1_1");
        title = getArguments().getString("title", "This is default title");
        position = getArguments().getString("position", "0");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chapter_slider_page, container, false);
        TextView tvShlokaNumber=(TextView) view.findViewById(R.id.shlokaNumber);
        TextView tvShlokaText=(TextView) view.findViewById(R.id.shlokaText);
        TextView tvTransText=(TextView) view.findViewById(R.id.shlokaTrans);

        int currentShloka = Integer.parseInt(position)+1;
        String[] arr = DataUtil.shlokaId.split("_");
        int chapterNumber = Integer.parseInt(arr[0]);
        String currentShlokaId = chapterNumber + "_" + currentShloka;
        DataUtil.shlokaId = chapterNumber + "_" + (currentShloka);
        Log.d("ChapterSliderFragment", "currentShlokaId="+currentShlokaId);
        currentShlokaId = (currentShlokaId==null)?"1_1":currentShlokaId;

        String strToRemove = currentShlokaId.replace("_",".");

        String shlokaText = DataUtil.shlokaTextMap.get(currentShlokaId);
        Log.d("ChapterSliderFragment", "shlokaText="+shlokaText);
        shlokaText = (shlokaText==null)?"Hello World":shlokaText;

        tvShlokaNumber.setText("Chapter "+ chapterNumber+" Shloka "+currentShloka);
        shlokaText = shlokaText.replace(".."+strToRemove+"..", "");
        tvShlokaText.setText(shlokaText);
        String trans = DataUtil.englishTransTextMap.get(currentShlokaId);
        trans = trans.replace(strToRemove, "");
        tvTransText.setText(trans);

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*private String getNextShlokaId(String finalCurrentShlokaId) {

        String[] arr = finalCurrentShlokaId.split("_");
        return arr[0]+"_"+(Integer.valueOf(arr[1])+1);
    }

    private String getPreviousShlokaId(String finalCurrentShlokaId) {

        String[] arr = finalCurrentShlokaId.split("_");
        return arr[0]+"_"+(Integer.valueOf(arr[1])+1);
    }*/
}
