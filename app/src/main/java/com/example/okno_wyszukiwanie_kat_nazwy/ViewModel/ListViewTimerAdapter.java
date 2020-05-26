package com.example.okno_wyszukiwanie_kat_nazwy.ViewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.okno_wyszukiwanie_kat_nazwy.R;
import com.example.okno_wyszukiwanie_kat_nazwy.View.Timer;
import com.squareup.picasso.Picasso;
import android.widget.ArrayAdapter;

public class ListViewTimerAdapter extends ArrayAdapter<String> {
    Context context;
    String names[];
    String time[];


    public ListViewTimerAdapter(Context context, String names[], String time[]) {
        super(context, R.layout.row_list_view_timer, R.id.timerName, names);
        this.context = context;
        this.names = names;
        this.time = time;
    }

    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) Timer.getTimerContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row_list_view_timer, parent, false);
        TextView mTextViewName = row.findViewById(R.id.timerName);
        TextView mTextViewTime = row.findViewById(R.id.timerTime);
        mTextViewName.setText(names[position]);
        mTextViewTime.setText(time[position]);


        return row;
    }
}
