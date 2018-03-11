package com.gita.holybooks.bhagwatgita.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.activity.ChapterSlidePagerActivity;
import com.gita.holybooks.bhagwatgita.activity.HomePageActivity;
import com.gita.holybooks.bhagwatgita.dto.Chapter;
import com.gita.holybooks.bhagwatgita.util.DataUtil;
import com.gita.holybooks.bhagwatgita.util.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**

 * https://www.androidtutorialpoint.com/material-design/listing-items-using-recyclerview/
 */
public class ChapterFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private List<Chapter> chapters;

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

        chapters = DataUtil.chapters;
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

    private class ChapterHolder extends RecyclerView.ViewHolder {
        public Chapter mChapter;
        public TextView mChapterNumberView;
        public TextView mChapterTitleView;
        public TextView mChapterDescView;

        public ChapterHolder(View itemView) {
            super(itemView);
            mChapterNumberView = (TextView) itemView.findViewById(R.id.chapter_id);
            mChapterTitleView = (TextView) itemView.findViewById(R.id.chapter_title);
            mChapterDescView = (TextView) itemView.findViewById(R.id.chapter_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), " clicked!", Toast.LENGTH_SHORT)
                            .show();

                    String shlokaId = mChapter.getChapterNumber()+"_1";
                    DataUtil.shlokaId = shlokaId;
                    Intent intent = new Intent(getActivity().getApplication(), ChapterSlidePagerActivity.class);
                    startActivity(intent);
                }
            });
        }

        public void bindData(Chapter chapter) {
            mChapter = chapter;
            mChapterNumberView.setText("Chapter "+chapter.getChapterNumber());
            mChapterTitleView.setText(chapter.getTitle());
            mChapterDescView.setText(chapter.getDescription());
        }
    }

    private class ChapterAdapter extends RecyclerView.Adapter<ChapterHolder> {
        private List<Chapter> chapters;

        public ChapterAdapter(List<Chapter> chapters) {
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
            Chapter chapter = chapters.get(position);
            holder.bindData(chapter);

        }

        @Override
        public int getItemCount() {
            return chapters.size();
        }
    }
}
