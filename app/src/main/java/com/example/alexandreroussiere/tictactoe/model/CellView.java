package com.example.alexandreroussiere.tictactoe.model;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by alexandreroussiere on 7/27/17.
 * Model of a cell the the tic tac toe board
 */

public class CellView extends ImageView {

    int pawnResourceId;

    public CellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CellView(Context context) {
        super(context);
        pawnResourceId = 0;
    }

    @Override
    /**
     * This is used to have square cells
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public int getPawnResourceId() {
        return pawnResourceId;
    }

    public void setPawnResourceId(int pawnResourceId) {
        this.pawnResourceId = pawnResourceId;
    }
}
