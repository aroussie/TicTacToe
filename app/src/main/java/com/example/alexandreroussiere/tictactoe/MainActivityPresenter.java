package com.example.alexandreroussiere.tictactoe;

import android.content.Context;
import android.text.Html;

/**
 * Created by alexandreroussiere on 7/25/17.
 * Presenter for the view of the Main Activity
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mView;
    private Context mContext;

    private int mUserTurn = 0;
    private int[][] mGridCells = new int[3][3];

    public MainActivityPresenter(Context context, MainActivityContract.View mView) {
        mContext = context;
        this.mView = mView;
        mView.subscribe(this);
        mView.setUserTurnTextView(mContext.getText(R.string.first_player_turn));
    }

    @Override
    /**
     * Called by the view when the user clicks on a cell.
     */
    public void pawnPlaced(int position) {

        //We get the position of the cell where the user clicked
        int row = position / 3;
        int column = position % 3;

        //We display the user pawn on the grid and save the position
        if (mUserTurn % 2 == 0) {
            mView.displayPawnInCell(position, R.drawable.cross_pawn);
            mGridCells[row][column] = R.drawable.cross_pawn;
        } else {
            mView.displayPawnInCell(position, R.drawable.circle_pawn);
            mGridCells[row][column] = R.drawable.circle_pawn;
        }

        //We check for victory or tie. If not, the game goes on with the next player
        if (checkVictory(mGridCells)) {
            mView.displayEndGameDialog(mContext.getString(R.string.winner, (mUserTurn % 2) + 1));
        } else if (mUserTurn == 8) {
            mView.displayEndGameDialog(mContext.getString(R.string.tie));
        } else {
            mUserTurn++;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                mView.setUserTurnTextView(Html.fromHtml(mContext.getString(R.string.player_turn, (mUserTurn % 2) + 1), 0));
            } else {
                mView.setUserTurnTextView(Html.fromHtml(mContext.getString(R.string.player_turn, (mUserTurn % 2) + 1)));
            }
        }
    }

    /**
     * Check if one of the player won
     *
     * @param gameBoard a double int array representing the cells of the game board
     * @return true if victory
     */
    private boolean checkVictory(int[][] gameBoard) {

        for (int i = 0; i < 3; i++) {

            //Check row
            if (gameBoard[i][0] != 0 && gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][1] == gameBoard[i][2]) {
                return true;

                //Check column
            } else if (gameBoard[0][i] != 0 && gameBoard[0][i] == gameBoard[1][i] && gameBoard[1][i] == gameBoard[2][i]) {
                return true;
            }
        }

        //Check 1st diagonal
        if (gameBoard[0][0] != 0 && gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2]) {
            return true;
        }

        //Check 2nd diagonal
        else if (gameBoard[0][2] != 0 && gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0]) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    /**
     * Reset all the data to start over the game
     */
    public void resetData() {
        mGridCells = new int[3][3];
        mUserTurn = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mView.setUserTurnTextView(Html.fromHtml(mContext.getString(R.string.player_turn, (mUserTurn % 2) + 1), 0));
        } else {
            mView.setUserTurnTextView(Html.fromHtml(mContext.getString(R.string.player_turn, (mUserTurn % 2 + 1))));
        }
    }

    @Override
    /**
     * Bind the view to the presenter
     */
    public void setView(MainActivityContract.View view) {
        mView = view;
    }


    //FOR TESTING PURPOSES

    protected void setmUserTurn(int mUserTurn) {
        this.mUserTurn = mUserTurn;
    }

    public int getmUserTurn() {
        return mUserTurn;
    }

    protected void setmGridCells(int[][] mGridCells) {
        this.mGridCells = mGridCells;
    }

    public int[][] getmGridCells() {
        return mGridCells;
    }
}
