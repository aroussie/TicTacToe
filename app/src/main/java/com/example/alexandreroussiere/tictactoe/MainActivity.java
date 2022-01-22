package com.example.alexandreroussiere.tictactoe;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.alexandreroussiere.tictactoe.adapter.GridViewAdapter;
import com.example.alexandreroussiere.tictactoe.model.CellView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    @BindView(R.id.playerTurnTextView)
    TextView mPlayerTurnTextView;

    @BindView(R.id.gameGridView)
    GridView mGameGrid;

    MainActivityPresenter mPresenter;
    private GridViewAdapter mAdapter;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mPresenter = new MainActivityPresenter(getApplicationContext(), this);

    }

    @Override
    public void onStart() {
        super.onStart();

        mAdapter = new GridViewAdapter(getApplicationContext());
        mGameGrid.setAdapter(mAdapter);
        mGameGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //When the user clicks on a cell, we send the position to the presenter
                mPresenter.pawnPlaced(position);
                view.setClickable(false);
            }
        });
    }

    @Override
    public void setUserTurnTextView(CharSequence text) {
        mPlayerTurnTextView.setText(text);
    }

    @Override
    /**
     * Update the image of the cell and notify the grid to update its views
     */
    public void displayPawnInCell(int position, int pawnResourceId) {
        if (mAdapter != null) {
            CellView cellView = (CellView) mAdapter.getItem(position);
            cellView.setPawnResourceId(pawnResourceId);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    /**
     * Display a dialog when the game is over
     */
    public void displayEndGameDialog(String message) {
        mAlertDialog = new AlertDialog.Builder(this, R.style.AppAlertTheme).create();
        View infoDialog = LayoutInflater.from(this).inflate(R.layout.victory_dialog_layout, null);

        TextView messageTextView = (TextView) infoDialog.findViewById(R.id.winnerTextView);
        messageTextView.setText(message);

        Button restartButton = (Button) infoDialog.findViewById(R.id.restartButton);
        //When the user clicks on Restart, we refresh the whole grid and we dismiss the dialog
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter = new GridViewAdapter(getApplicationContext());
                mAdapter.notifyDataSetChanged();
                mGameGrid.setAdapter(mAdapter);
                mPresenter.resetData();
                mAlertDialog.dismiss();
            }
        });

        mAlertDialog.setView(infoDialog);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();


    }

    @Override
    public void subscribe(MainActivityContract.Presenter presenter) {
        mPresenter = (MainActivityPresenter) presenter;
    }

}
