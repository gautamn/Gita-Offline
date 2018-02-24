package com.gita.holybooks.bhagwatgita.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.fragment.dummy.DummyContent.DummyItem;
import com.gita.holybooks.bhagwatgita.util.DataUtil;
import com.gita.holybooks.bhagwatgita.util.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 * https://www.androidtutorialpoint.com/material-design/listing-items-using-recyclerview/
 */
public class ChapterFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<String> chapters;

    private final String TAG = "ListChaptersFragment";
    private RecyclerView mChaptersRecyclerView;
    private ChapterAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChapterFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ChapterFragment newInstance(int columnCount) {
        ChapterFragment fragment = new ChapterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        chapters = new ArrayList<>(18);
        for (Map.Entry<String, String> entry : DataUtil.chapterTextMap.entrySet()) {
            chapters.add("Chapter " + entry.getKey() + " " + entry.getValue());
            Log.d("Chapter",entry.getValue());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        mChaptersRecyclerView = (RecyclerView) view.findViewById(R.id.chapterRecyclerView);
        mChaptersRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        mChaptersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        mAdapter = new ChapterAdapter(chapters);
        mChaptersRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    private class ChapterHolder extends RecyclerView.ViewHolder {
        public TextView mTextTextView;

        public ChapterHolder(View itemView) {
            super(itemView);
            mTextTextView = (TextView) itemView.findViewById(R.id.chapter_content);
        }

        public void bindData(String text) {
            mTextTextView.setText(text);
        }
    }

    private class ChapterAdapter extends RecyclerView.Adapter<ChapterHolder> {
        private List<String> chapters;

        public ChapterAdapter(List<String> chapters) {
            this.chapters = chapters;
        }

        @Override
        public ChapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_chapter, parent, false);
            return new ChapterHolder(view);
        }

        @Override
        public void onBindViewHolder(ChapterHolder holder, int position) {
            String s = chapters.get(position);
            holder.bindData(s);

        }

        @Override
        public int getItemCount() {
            return chapters.size();
        }
    }
}
