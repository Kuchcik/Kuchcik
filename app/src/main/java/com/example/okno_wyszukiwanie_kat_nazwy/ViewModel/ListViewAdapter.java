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
import com.example.okno_wyszukiwanie_kat_nazwy.View.MainActivity;
import com.squareup.picasso.Picasso;

public class ListViewAdapter extends ArrayAdapter<String> {

    Context context;
    String names[];
    String images[];


   public ListViewAdapter(Context context, String names[], String images[]) {
        super(context, R.layout.row_list_view, R.id.categoryNameTextView, names);
        this.context = context;
        this.names = names;
        this.images = images;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.getMainActivityContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row_list_view, parent, false);
        ImageView mImageView = row.findViewById(R.id.categoryNameImageView);
        TextView mTextView = row.findViewById(R.id.categoryNameTextView);
        Picasso.get().load(images[position]).into(mImageView);
        mTextView.setText(names[position]);

        return row;
    }
}
