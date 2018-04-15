package com.gita.holybooks.bhagwatgita.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.dto.Note;
import com.gita.holybooks.bhagwatgita.service.DatabaseService;
import com.gita.holybooks.bhagwatgita.util.DataUtil;

import java.util.List;

/**
 * Created by Nitin Gautam on 4/13/2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NotesViewHolder> {

    List<Note> notes;
    Context context;
    DatabaseService databaseService;

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        public TextView title, noteText;
        public Note mNote;


        public NotesViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            noteText = (TextView) view.findViewById(R.id.note);

            ImageView delBtn = (ImageView) view.findViewById(R.id.bt_delete);
            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currentShloka = mNote.getShlokaId();
                    String[] arr = currentShloka.split("_");

                    Integer rowsDeleted = databaseService.deleteBookmark(currentShloka);
                    if (rowsDeleted > 0)
                        Toast.makeText(context, "note " + mNote.getShlokaId() + " deleted  from db", Toast.LENGTH_SHORT).show();
                }
            });

            ImageView shareBtn = (ImageView) view.findViewById(R.id.bt_share);
            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String currentShloka = mNote.getShlokaId();
                    String[] arr = currentShloka.split("_");

                    String lastShloka = arr[0] + "_" + (Integer.valueOf(arr[1]));
                    StringBuffer sb = new StringBuffer();
                    String chapterName = DataUtil.chapters.get(Integer.valueOf(arr[0])).getTitle();
                    sb.append("Chapter " + arr[0] + " Shloka -- " + (Integer.valueOf(arr[1])) + " " + chapterName);
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
                    context.startActivity(sendIntent);
                }
            });


            ImageView copyBtn = (ImageView) view.findViewById(R.id.bt_copy);
            copyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String currentShloka = mNote.getShlokaId();
                    String[] arr = currentShloka.split("_");

                    String lastShloka = arr[0] + "_" + (Integer.valueOf(arr[1]));
                    StringBuffer sb = new StringBuffer();
                    String chapterName = DataUtil.chapters.get(Integer.valueOf(arr[0])).getTitle();
                    sb.append("Chapter " + arr[0] + " Shloka -- " + (Integer.valueOf(arr[1])) + " " + chapterName);
                    sb.append("\n\n");
                    sb.append(DataUtil.shlokaTextMap.get(lastShloka));
                    sb.append("\n");
                    sb.append(DataUtil.englishTransTextMap.get(lastShloka));
                    sb.append("\n\n");
                    sb.append("Shared via --- Srimad Bhagawad Gita");

                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("simple text", sb.toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, "Text View Copied to Clipboard",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bindData(Note note) {
            mNote = note;
            title.setText(note.getTitle());
            noteText.setText(note.getNote());
        }
    }

    public NoteAdapter(List<Note> notes, Context context, DatabaseService databaseService) {
        this.notes = notes;
        this.context = context;
        this.databaseService = databaseService;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {

        Note note = notes.get(position);
        holder.bindData(note);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


}
