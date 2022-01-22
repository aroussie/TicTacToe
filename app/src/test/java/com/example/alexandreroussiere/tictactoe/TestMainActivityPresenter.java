package com.example.alexandreroussiere.tictactoe;

import android.content.Context;
import android.os.Build;
import android.text.Html;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by alexandreroussiere on 7/30/17.
 * Test Class for the presenter of Main Activity. The idea is to create a testCLass that behaves as the
 * View of the presenter. The goal is to check that when we call a function from the presenter, it passes
 * the correct values to the view
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TestMainActivityPresenter implements MainActivityContract.View{


    private MainActivityPresenter mPresenter;
    private Context mContext;
    private int mUserTurn = 0;
    private int mPawnPosition;


    private int endDialogCall = 0;
    private boolean expectedVictory;
    private boolean presenterCreated;


    @Before
    public void setup(){
        mContext = RuntimeEnvironment.application;
        mPresenter = new MainActivityPresenter(mContext, this);
        presenterCreated = true;
        endDialogCall = 0;
        expectedVictory = false;
    }


    @Test
    public void testCirclePawnPlaced(){

        mUserTurn = 3;
        mPawnPosition = 4;
        mPresenter.setmUserTurn(mUserTurn);

        mPresenter.pawnPlaced(mPawnPosition);

        int[][] expectedGrid = new int[][]{
                {0, 0, 0},
                {0, R.drawable.circle_pawn, 0},
                {0, 0, 0}
        };

        assertArrayEquals("The grid is wrong", expectedGrid, mPresenter.getmGridCells());
        assertEquals("user turn not updated", mUserTurn, mPresenter.getmUserTurn());

    }

    @Test
    public void testCrossPawnPlaced(){

        mUserTurn = 4;
        mPawnPosition = 5;
        mPresenter.setmUserTurn(mUserTurn);
        mPresenter.pawnPlaced(mPawnPosition);

        int[][] expectedGrid = new int[][]{
                {0, 0, 0},
                {0, 0, R.drawable.cross_pawn},
                {0, 0, 0}
        };

        assertArrayEquals("The grid is wrong", expectedGrid, mPresenter.getmGridCells());
        assertEquals("user turn not updated", mUserTurn, mPresenter.getmUserTurn());

    }

    @Test
    public void testCheckVictoryWithRow(){

        int[][] currentGrid = new int[][]{
                {R.drawable.cross_pawn, 0, R.drawable.cross_pawn},
                {R.drawable.circle_pawn, 0, R.drawable.circle_pawn},
                {R.drawable.circle_pawn, R.drawable.cross_pawn, 0}
        };

        mPresenter.setmGridCells(currentGrid);
        mUserTurn = 6;
        mPawnPosition = 1;
        mPresenter.setmUserTurn(mUserTurn);

        //This will place a cross pawn on the last cell of the first row and thus throw a victory
        expectedVictory = true;
        mPresenter.pawnPlaced(mPawnPosition);

        assertEquals("Victory Dialog not showed", 1, endDialogCall);

    }

    @Test
    public void testCheckVictoryWithColumn(){

        int[][] currentGrid = new int[][]{
                {0, R.drawable.cross_pawn, R.drawable.cross_pawn},
                {R.drawable.circle_pawn, 0, R.drawable.circle_pawn},
                {R.drawable.circle_pawn, R.drawable.cross_pawn, 0}
        };

        mPresenter.setmGridCells(currentGrid);
        mUserTurn = 7;
        mPawnPosition = 0;
        mPresenter.setmUserTurn(mUserTurn);

        //This will place a circle pawn on the first cell of the first row and thus throw a victory
        expectedVictory = true;
        mPresenter.pawnPlaced(mPawnPosition);

        assertEquals("Victory Dialog not showed", 1, endDialogCall);

    }

    @Test
    public void testCheckVictoryWithFirstDiagonal(){

        int[][] currentGrid = new int[][]{
                {R.drawable.cross_pawn, 0, R.drawable.cross_pawn},
                {R.drawable.circle_pawn, 0, R.drawable.circle_pawn},
                {R.drawable.circle_pawn, 0, R.drawable.cross_pawn}
        };

        mPresenter.setmGridCells(currentGrid);
        mUserTurn = 6;
        mPawnPosition = 4;
        mPresenter.setmUserTurn(mUserTurn);

        //This will place a cross pawn in the middle of the grid and thus throw a victory
        expectedVictory = true;
        mPresenter.pawnPlaced(mPawnPosition);

        assertEquals("Victory Dialog not showed", 1, endDialogCall);

    }

    @Test
    public void testCheckVictoryWithSecondDiagonal(){

        int[][] currentGrid = new int[][]{
                {R.drawable.cross_pawn, 0, R.drawable.cross_pawn},
                {R.drawable.circle_pawn, 0, R.drawable.circle_pawn},
                {R.drawable.cross_pawn, 0, R.drawable.circle_pawn}
        };

        mPresenter.setmGridCells(currentGrid);
        mUserTurn = 6;
        mPawnPosition = 4;
        mPresenter.setmUserTurn(mUserTurn);

        //This will place a cross pawn in the middle of the grid and thus throw a victory
        expectedVictory = true;
        mPresenter.pawnPlaced(mPawnPosition);

        assertEquals("Victory Dialog not showed", 1, endDialogCall);

    }

    @Test
    public void testCheckTie(){

        int[][] currentGrid = new int[][]{
                {R.drawable.cross_pawn, R.drawable.circle_pawn, R.drawable.cross_pawn},
                {R.drawable.circle_pawn, R.drawable.cross_pawn, R.drawable.circle_pawn},
                {R.drawable.circle_pawn, 0, R.drawable.circle_pawn}
        };

        mPresenter.setmGridCells(currentGrid);
        mUserTurn = 8;
        mPawnPosition = 7;
        mPresenter.setmUserTurn(mUserTurn);

        //This will place a circle pawn in the middle of the last row and thus throw a tie
        expectedVictory = false;
        mPresenter.pawnPlaced(mPawnPosition);

        assertEquals("Tie Dialog not showed", 1, endDialogCall);

    }

    @Test
    public void testResetData(){

        int[][] currentGrid = new int[][]{
                {R.drawable.cross_pawn, R.drawable.circle_pawn, R.drawable.cross_pawn},
                {R.drawable.circle_pawn, R.drawable.cross_pawn, R.drawable.circle_pawn},
                {R.drawable.circle_pawn, 0, R.drawable.circle_pawn}
        };

        mPresenter.setmUserTurn(4);
        mPresenter.setmGridCells(currentGrid);

        assertEquals(4, mPresenter.getmUserTurn());
        assertArrayEquals(currentGrid, mPresenter.getmGridCells());

        mUserTurn = 0;
        mPresenter.resetData();

        assertEquals("userTurn did not reset", 0, mPresenter.getmUserTurn());
        assertArrayEquals("grid did not reset", new int[3][3], mPresenter.getmGridCells());

    }


    @Override
    public void setUserTurnTextView(CharSequence text) {
        if (!presenterCreated) {
            assertEquals("Wrong text", mContext.getText(R.string.first_player_turn), text);
        } else {
             mUserTurn = mUserTurn == 0 ? 0 : mUserTurn + 1;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                assertEquals("Wrong text",Html.fromHtml(mContext.getString(R.string.player_turn, ((mUserTurn ) % 2) + 1), 0), text);
            } else {
                assertEquals("Wrong text",Html.fromHtml(mContext.getString(R.string.player_turn, ((mUserTurn ) % 2) + 1)), text);
            }
        }
    }

    @Override
    public void displayPawnInCell(int position, int pawnResourceId) {

        assertEquals("Wrong position", mPawnPosition, position);
        if (mUserTurn % 2 == 0) {
            assertEquals("Wrong pawn image", R.drawable.cross_pawn, pawnResourceId);
        } else {
            assertEquals("Wrong pawn image", R.drawable.circle_pawn, pawnResourceId);
        }
    }

    @Override
    public void displayEndGameDialog(String message) {
        endDialogCall++;
        if (expectedVictory){
            assertEquals("Wrong message", mContext.getString(R.string.winner, (mUserTurn % 2) + 1), message);
        } else {
            assertEquals("Wrong message", mContext.getString(R.string.tie), message);
        }

    }

    @Override
    public void subscribe(MainActivityContract.Presenter presenter) {
        assertNotNull("the presenter is null", presenter);
    }
}
