package com.example.alexandreroussiere.tictactoe;

/**
 * Created by alexandreroussiere on 7/25/17.
 * Class that binds the presenter and its view together
 */

public class MainActivityContract {
    public interface View{
        void subscribe(MainActivityContract.Presenter presenter);
        void setUserTurnTextView(CharSequence text);
        void displayPawnInCell(int position, int pawnResourceId);
        void displayEndGameDialog(String message);
    }

    public interface Presenter{
        void setView(MainActivityContract.View view);
        void pawnPlaced(int position);
        void resetData();
    }
}
