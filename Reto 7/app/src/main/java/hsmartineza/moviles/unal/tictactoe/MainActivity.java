package hsmartineza.moviles.unal.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FloatingActionButton mButtonCreate;
    private FirebaseFirestore db;
    private ArrayList<String> mListGameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);
        mButtonCreate = (FloatingActionButton) findViewById(R.id.fab);
        mListGameID = new ArrayList<>();

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        db.collection("games")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null ){
                            Log.w(TAG, "onEvent: listen:error", e);
                        }
                        else {
                            for(DocumentChange dc : snapshots.getDocumentChanges()) {
                                switch(dc.getType()){
                                    case ADDED:
                                        if(dc.getDocument().get("mDispo").equals(true)){
                                            mListGameID.add(dc.getDocument().getId());
                                        }
                                        break;
                                    case REMOVED:
                                        mListGameID.remove(dc.getDocument().getId());
                                        break;
                                    case MODIFIED:
                                        if(dc.getDocument().get("mDispo").equals(false))
                                            mListGameID.remove(dc.getDocument().getId());

                                }
                            }
                            initRecyclerView(mListGameID);
                        }
                    }
                });

        mButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TicTacToeGame mGame = new TicTacToeGame();
                db.collection("games")
                        .add(mGame)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG,"GAME ADD   :  " + documentReference.getId());

                                Intent intent = new Intent(MainActivity.this, AndroidTicTacToeActivity.class);
                                intent.putExtra("gameID", documentReference.getId());
                                MainActivity.this.startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"FAILURE",e);
                            }
                        });
            }
        });

        initRecyclerView(mListGameID);

    }

    private void initRecyclerView(ArrayList<String> list){
        Log.d(TAG, "initRecyclerView:  init recycler view");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
