package com.gita.holybooks.bhagwatgita.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.activity.ChapterSlidePagerActivity;
import com.gita.holybooks.bhagwatgita.dao.DataBaseHelper;
import com.gita.holybooks.bhagwatgita.dto.Chapter;
import com.gita.holybooks.bhagwatgita.service.DatabaseService;
import com.gita.holybooks.bhagwatgita.util.DataUtil;
import com.gita.holybooks.bhagwatgita.util.SharedPreferenceUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChapterSliderFragment extends Fragment {

    private String title;
    private String position;
    private String shlokaId;
    private Button bookMarkButton;

    private OnFragmentInteractionListener mListener;

    DataBaseHelper dataBaseHelper;
    DataBaseHelper noteDataBaseHelper;
    DatabaseService databaseService;

    public ChapterSliderFragment() {
    }

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
        dataBaseHelper = new DataBaseHelper(this.getActivity());
        noteDataBaseHelper = new DataBaseHelper(this.getActivity());
        databaseService = new DatabaseService(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chapter_slider_page, container, false);
        //TextView tvShlokaNumber = (TextView) view.findViewById(R.id.shlokaNumber);
        TextView tvShlokaText = (TextView) view.findViewById(R.id.shlokaText);
        TextView tvTransText = (TextView) view.findViewById(R.id.shlokaTrans);

        int currentShloka = Integer.parseInt(position) + 1;
        String[] arr = DataUtil.shlokaId.split("_");
        int chapterNumber = Integer.parseInt(arr[0]);
        String currentShlokaId = chapterNumber + "_" + currentShloka;
        DataUtil.shlokaId = chapterNumber + "_" + (currentShloka);
        Log.d("ChapterSliderFragment", "currentShlokaId=" + currentShlokaId);
        currentShlokaId = (currentShlokaId == null) ? "1_1" : currentShlokaId;

        String strToRemove = currentShlokaId.replace("_", ".");

        String shlokaText = DataUtil.shlokaTextMap.get(currentShlokaId);
        Log.d("ChapterSliderFragment", "shlokaText=" + shlokaText);
        shlokaText = (shlokaText == null) ? "Hello World" : shlokaText;

        //tvShlokaNumber.setText("Chapter " + chapterNumber + " Shloka " + currentShloka);
        shlokaText = shlokaText.replace(".." + strToRemove + "..", "");
        tvShlokaText.setText(shlokaText);
        String trans = DataUtil.englishTransTextMap.get(currentShlokaId);
        trans = trans.replace(strToRemove, "");
        tvTransText.setText(trans);

        ImageView noteBtn = (ImageView) view.findViewById(R.id.bt_note);
        noteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // custom dialog
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_notes);
                /*EditText editText = (EditText)view.findViewById(R.id.editNote);
                editText.setText(DataUtil.shlokaId, TextView.BufferType.EDITABLE);*/

                ImageView saveBtn = (ImageView) dialog.findViewById(R.id.bt_save);
                // if button is clicked, close the custom dialog
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = (EditText) v.findViewById(R.id.editNote);
                        String text = "This is note";
                        dialog.dismiss();
                        databaseService.insertNote(DataUtil.shlokaId, text);
                        Toast.makeText(getContext(), "Note saved!!!", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
            }
        });

        ImageView bt = (ImageView) view.findViewById(R.id.bt_image);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentShloka = DataUtil.shlokaId;
                String[] arr = currentShloka.split("_");
                currentShloka = arr[0]+"_"+(Integer.valueOf(arr[1])-1);
                //SharedPreferenceUtil.bookmarkShloka(getActivity(), currentShloka);
                boolean isBookmarkSaved = dataBaseHelper.insertBookmark(currentShloka);
                if(isBookmarkSaved)
                    Toast.makeText(getContext(), "Shloka saved db", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView shareBtn = (ImageView) view.findViewById(R.id.bt_share);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentShloka = DataUtil.shlokaId;
                String[] arr = currentShloka.split("_");

                String lastShloka = arr[0]+"_"+(Integer.valueOf(arr[1])-1);
                StringBuffer sb = new StringBuffer();
                String chapterName = DataUtil.chapters.get(Integer.valueOf(arr[0])).getTitle();
                sb.append("Chapter "+arr[0]+" Shloka -- " + (Integer.valueOf(arr[1])-1)+" "+chapterName);
                sb.append("\n\n");
                sb.append(DataUtil.shlokaTextMap.get(lastShloka));
                sb.append("\n");
                sb.append(DataUtil.englishTransTextMap.get(lastShloka));
                sb.append("\n\n");
                sb.append("Shared via --- Srimad Bhagawad Gita");

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });


        ImageView copyBtn = (ImageView) view.findViewById(R.id.bt_copy);
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentShloka = DataUtil.shlokaId;
                String[] arr = currentShloka.split("_");

                String lastShloka = arr[0]+"_"+(Integer.valueOf(arr[1])-1);
                StringBuffer sb = new StringBuffer();
                String chapterName = DataUtil.chapters.get(Integer.valueOf(arr[0])).getTitle();
                sb.append("Chapter "+arr[0]+" Shloka -- " + (Integer.valueOf(arr[1])-1)+" "+chapterName);
                sb.append("\n\n");
                sb.append(DataUtil.shlokaTextMap.get(lastShloka));
                sb.append("\n");
                sb.append(DataUtil.englishTransTextMap.get(lastShloka));
                sb.append("\n\n");
                sb.append("Shared via --- Srimad Bhagawad Gita");

                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", sb.toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Text View Copied to Clipboard",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
