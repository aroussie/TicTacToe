package com.example.alexandreroussiere.tictactoe.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.alexandreroussiere.tictactoe.R;
import com.example.alexandreroussiere.tictactoe.model.CellView;

import java.util.ArrayList;

/**
 * Created by alexandreroussiere on 7/27/17.
 * Adapter for the gridView used as the game board
 */

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<CellView> mData = new ArrayList<>();

    public GridViewAdapter(Context mContext) {
        this.mContext = mContext;
        setData();
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View cell = convertView;
        ViewHolder holder;

        if (cell == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cell = layoutInflater.inflate(R.layout.grid_cell_layout, parent, false);
            holder = new ViewHolder();
            holder.cellViewImageView = (CellView) cell.findViewById(R.id.gridCellImageView);
            cell.setTag(holder);
        } else {
            holder = (ViewHolder) cell.getTag();
        }

        if (mData.get(position).getPawnResourceId() != 0) {
            holder.cellViewImageView.setImageDrawable(ContextCompat.getDrawable(mContext, mData.get(position).getPawnResourceId()));
        }

        return cell;
    }

    public class ViewHolder {
        public CellView cellViewImageView;
    }

    /**
     * Initialize all the cells
     */
    private void setData() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mData.add(new CellView(mContext));
            }
        }
    }

}

