package oeroaq.moviles.unal.tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class AndroidTicTacToeActivity extends AppCompatActivity {
    private static final String TAG = "AndroidTicTacToeActivity";


    private TicTacToeGame mGame;
    //the numbers of wins
    private int mAndroidWins;
    private int mHumanWins;
    private int mTie;

    //quel joueur
    private boolean J2;
    private char mJoueur;

    //gameID
    private String gameID;

    //text views
    private TextView mNumberHuman;
    private TextView mNumberTie;
    private TextView mNumberAndroid;
    private TextView mPlayer;

    //game over
    private boolean mGameOver;


    //various text displayed
    private TextView mInfoTextView;

    static final int DIALOG_QUIT_ID = 1;
    static final int DIAlOG_ABOUT_ID = 2;

    private BoardView mBoardView;

    private MediaPlayer mHumanMediaPlayer;
    private MediaPlayer mComputereMediaPlayer;
    private boolean mSoundOn;

    private FirebaseFirestore db;

    private SharedPreferences mPrefs;

    @Override
    protected void onResume(){
        super.onResume();

        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.poker);
        mComputereMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alert);
    }
    @Override
    protected void onPause(){
        super.onPause();

        mHumanMediaPlayer.release();
        mComputereMediaPlayer.release();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_tic_tac_toe);
        FirebaseApp.initializeApp(getApplicationContext());
        db = FirebaseFirestore.getInstance();

        getIncomingIntent();

        mInfoTextView = (TextView) findViewById(R.id.information);


        mPlayer = (TextView) findViewById(R.id.player_id);

        mGame = new TicTacToeGame();
        mBoardView = (BoardView) findViewById(R.id.board);
        mBoardView.setOnTouchListener(mTouchListener);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        mSoundOn = mPrefs.getBoolean("sound", true);

        mBoardView.setGame(mGame);

        if(mJoueur == TicTacToeGame.HUMAN_PLAYER)
            mPlayer.setText("You are Player 2");
        else
            mPlayer.setText("You are Player 1");



        startNewGame();

        displayScores();

        db.collection("games").document(gameID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {
                            Log.d(TAG, "Current data: " + snapshot.getData());
                            ArrayList<String> list = (ArrayList<String>) snapshot.getData().get("boardState");
                            mGame.setJ1turn((boolean) snapshot.getData().get("j1turn"));
                            mGameOver = (boolean) snapshot.getData().get("gameOver");
                            if(mJoueur == TicTacToeGame.COMPUTER_PLAYER && (boolean)snapshot.getData().get("p2arrived")){
                                Toast.makeText(AndroidTicTacToeActivity.this, "An opponent has been found", Toast.LENGTH_SHORT).show();
                                db.collection("games").document(gameID)
                                        .update("p2arrived",false);
                            }
                            if(mGame.getJ1turn() && !mGameOver)
                                mInfoTextView.setText("Player 1 turn");
                            if(!mGame.getJ1turn() && !mGameOver)
                                mInfoTextView.setText("Player 2 turn");
                            mGame.setBoardState(list);
                            mBoardView.invalidate();

                            int winner = mGame.checkForWinner();
                            if ( winner == 1){
                                mInfoTextView.setText(R.string.result_tie);
                                mTie++;
                                //mNumberTie.setText("Ties : " + mTie);
                                mGameOver=true;
                            } else if (winner == 2){
                                mInfoTextView.setText("Player 2 won");
                                mHumanWins++;
                                //mNumberHuman.setText("Player 2 : " + mHumanWins);
                                mGameOver=true;
                            } else if(winner == 3){
                                mInfoTextView.setText("Player 1 won");
                                mAndroidWins++;
                                // mNumberAndroid.setText("Player 1 : " + mAndroidWins);
                                mGameOver=true;
                            }

                        } else {
                            Log.d(TAG, "Current data: null");
                        }
                    }
                });
    }



    @Override
    protected void onStop(){
        super.onStop();

        SharedPreferences.Editor ed = mPrefs.edit();
        ed.commit();
    }

    private void displayScores(){
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        mGameOver = savedInstanceState.getBoolean("mGameOver");
        mInfoTextView.setText(savedInstanceState.getCharSequence("info"));

    }

    private void startNewGame(){
        mGame.clearBoard();
        mBoardView.invalidate();
        db.collection("games").document(gameID)
                .update("boardState",mGame.getBoardState());

        mGameOver=false;
        mInfoTextView.setText("Player 1 turn");
        mGame.setJ1turn(true);
        db.collection("games").document(gameID)
                .update("j1turn",true);

        db.collection("games").document(gameID)
                .update("gameOver",false);


    }

    // a modifier
    private boolean setMove(char player,int location){
        Log.d(TAG, "setMove: "+player+" want to make a move on : "+location);

        if(mGameOver==false && mGame.setMove(player, location)){
            Log.d(TAG, "setMove: "+player+" made a move on : "+location);
            mHumanMediaPlayer.start();
            db.collection("games").document(gameID)
                    .update("boardState", mGame.getBoardState());


            mBoardView.invalidate();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.ai_preferences:
                startActivityForResult(new Intent(this, Settings.class),0);
                return true;
            case R.id.ai_about:
                showDialog(DIAlOG_ABOUT_ID);
            case R.id.ai_reset:
                SharedPreferences.Editor ed = mPrefs.edit();
        }
        return false;
    }

    @Override
    protected Dialog onCreateDialog(int id){
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch(id){
            case DIAlOG_ABOUT_ID:

                Context context = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.about_dialog, null);
                builder.setView(layout);
                builder.setPositiveButton("OK", null);
                dialog = builder.create();
                break;


            case DIALOG_QUIT_ID:

                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                AndroidTicTacToeActivity.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                dialog = builder.create();
                break;
        }
        return dialog;
    }

    // a modifier
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int col = (int) event.getX() / mBoardView.getBoardCellWidth();
            int row = (int) event.getY() / mBoardView.getBoardCellHeight();
            int pos = row * 3 + col;
            Log.d(TAG, "onTouch: " + mJoueur +" a touché la pos "+ pos);

            //verfification que c'est bien à J1 de jouer et non J2
            //J1 = COMPUTER_PLAYER
            if(mJoueur == TicTacToeGame.COMPUTER_PLAYER && mGame.getJ1turn() && setMove(mJoueur, pos)) {
                int winner = mGame.checkForWinner();
                mGame.setJ1turn(false);
                db.collection("games").document(gameID)
                        .update("j1turn", false);
                if(winner==0 && mGame.getJ1turn()){
                    mInfoTextView.setText("Player 1 turn");
                    winner = mGame.checkForWinner();
                }

                if(winner==0 && !mGame.getJ1turn()){
                    mInfoTextView.setText("Player 2 turn");
                    winner = mGame.checkForWinner();

                } else if ( winner == 1){
                    mInfoTextView.setText(R.string.result_tie);
                    mTie++;
                    // mNumberTie.setText("Ties : " + mTie);
                    mGameOver=true;
                } else if (winner == 2){
                    String defaultMessage = getResources().getString(R.string.result_human_wins);
                    mInfoTextView.setText(mPrefs.getString("victory_message", defaultMessage));
                    mHumanWins++;
                    //mNumberHuman.setText("Player 2 : " + mHumanWins);
                    mGameOver=true;
                } else {
                    mInfoTextView.setText(R.string.result_computer_wins);
                    mAndroidWins++;
                    //mNumberAndroid.setText("Player 1 : " + mAndroidWins);
                    mGameOver=true;
                }            }

            //verfification que c'est bien à J2 de jouer et non J1
            //J2 = HUMAN_PLAYER
            if(mJoueur == TicTacToeGame.HUMAN_PLAYER && !mGame.getJ1turn() && setMove(mJoueur, pos)) {
                int winner = mGame.checkForWinner();
                mGame.setJ1turn(true);
                db.collection("games").document(gameID)
                        .update("j1turn", true);
                if(winner==0){
                    mInfoTextView.setText("Player 1 turn");
                    winner = mGame.checkForWinner();
                }

                if(winner==0){
                    mInfoTextView.setText("Player 2 turn");
                } else if ( winner == 1){
                    mInfoTextView.setText(R.string.result_tie);
                    mTie++;
                    // mNumberTie.setText("Ties : " + mTie);
                    mGameOver=true;
                } else if (winner == 2){
                    String defaultMessage = "Player 2 won";
                    mInfoTextView.setText("Player 2 won");
                    mHumanWins++;
                    // mNumberHuman.setText("Player 2 : " + mHumanWins);
                    mGameOver=true;
                } else {
                    mInfoTextView.setText("Player 1 won");
                    mAndroidWins++;
                    // mNumberAndroid.setText("Player 1 : " + mAndroidWins);
                    mGameOver=true;
                }            }

            return false;
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        //outState.putCharArray("board",mGame.getBoardState().toArray());
        outState.putBoolean("mGameOver",mGameOver);
        // outState.putInt("mHumanWins", Integer.valueOf(mHumanWins));
        //   outState.putInt("mComputerWins", Integer.valueOf(mAndroidWins));
        //   outState.putInt("mTies", Integer.valueOf(mTie));
        outState.putCharSequence("info",mInfoTextView.getText());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == RESULT_CANCELED){
            //apply potential new settings

            mSoundOn = mPrefs.getBoolean("sound", true);


        }
    }


    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checkin for incoming intent");

        if(getIntent().hasExtra("gameID")){
            Log.d(TAG, "getIncomingIntent: found extra string ");

            gameID = getIntent().getStringExtra("gameID");

        } if (getIntent().hasExtra("J2")){
            Log.d(TAG, "getIncomingIntent:  is J2");
            mJoueur = TicTacToeGame.HUMAN_PLAYER;
            db.collection("games").document(gameID)
                    .update("mDispo", false);
            db.collection("games").document(gameID)
                    .update("p2arrived",true);
        } else if(!getIntent().hasExtra("J2")){
            Log.d(TAG, "getIncomingIntent: is not J2");
            mJoueur=TicTacToeGame.COMPUTER_PLAYER;
        }
    }
}
