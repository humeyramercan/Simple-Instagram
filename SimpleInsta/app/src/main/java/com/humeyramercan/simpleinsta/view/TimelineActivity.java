package com.humeyramercan.simpleinsta.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.humeyramercan.simpleinsta.R;
import com.humeyramercan.simpleinsta.adapter.TimelineRecyclerAdapter;
import com.humeyramercan.simpleinsta.model.Post;

import java.util.ArrayList;
import java.util.Map;

public class TimelineActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Post> posts;
    RecyclerView timelineRecyclerView;
    TimelineRecyclerAdapter timelineRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        timelineRecyclerView=findViewById(R.id.timelineRecyclerView);
        timelineRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        posts=new ArrayList<>();

        timelineRecyclerAdapter=new TimelineRecyclerAdapter(posts);

        timelineRecyclerView.setAdapter(timelineRecyclerAdapter);

        getDataFromFirebase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=this.getMenuInflater();
        menuInflater.inflate(R.menu.insta_options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_post){
            Intent intent=new Intent(TimelineActivity.this,UploadActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.sign_Out){
            firebaseAuth.signOut();
            Intent intent=new Intent(TimelineActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getDataFromFirebase(){
        CollectionReference collectionReference=firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(TimelineActivity.this,error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
                else {
                    if (value != null) {
                        for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                            Map<String, Object> data = documentSnapshot.getData();
                            String userEmail = data.get("userEmail").toString();
                            String userComment = data.get("userComment").toString();
                            String dowloandUrl = data.get("dowloandUrl").toString();

                            Post post = new Post(dowloandUrl, userEmail, userComment);
                            posts.add(post);
                            timelineRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
}