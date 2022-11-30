package com.example.baads.savedList;

import static android.content.ContentValues.TAG;
import static android.util.Log.d;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.baads.R;
import com.example.baads.databinding.ActivitySelfCareListBinding;
import com.example.baads.mainFiles.usernameStorage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class savedListFragment extends Fragment {

    private FirebaseFirestore databaseLoginInfoConnection;
    private ActivitySelfCareListBinding binding;

    //DocumentReference savedIdeasRef = db.collection("Practice mindfulness").document("SavedIdeasList");

    String tips[]
            = { "Practice mindfulness", "Take a break",
            "Play video games", "Listen to music",
            "Read a book", "Listen to a podcast",
            "Reflect on things you are grateful for",
            "Eat a healthy meal", "Engage in exercise",
            "Go for a walk", "Drink water", "Practice good sleep hygiene",
            "Call-text a friend", "Connect with nature",
            "Meditate", "Treat yourself", "Maintain a routine", "Take deep breaths"};
    ArrayList<String> savedArrayList = new ArrayList<>();
          //  = {
         //   "0"//, "1", "2"
    //};
    String value;
    //List<ApiFuture<WriteResult>> saves = new ArrayList<>();
           // int countVariable;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ActivitySelfCareListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseLoginInfoConnection = FirebaseFirestore.getInstance();
        //TextView retrieveDataText = getActivity().findViewById(R.id.retrieveData);

        String[] result = new String[1];
        result[0] ="";
       // savedArrayList.add("Call-text a friend");

        CollectionReference colRef = databaseLoginInfoConnection.collection("users")
                .document(usernameStorage.username)
                .collection("SavedIdeas");
/*
                colRef.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        savedArrayList.add("test");
                        savedArrayList.add(String.valueOf(value.getDocuments()));
                    }
                });
*/        ArrayAdapter<String> tasks = new ArrayAdapter<>(getActivity(),
                R.layout.selfcarelist_item_view, R.id.itemTextView,
                savedArrayList);
       // tasks.notifyDataSetChanged();
//set to tips for full list
//set to savedArrayList for saved


        ListView listTasks = getActivity().findViewById(R.id.listView);

        listTasks.setAdapter(tasks);
       // tasks.notifyDataSetChanged();



                colRef.get()
                .addOnCompleteListener(task -> {
                   // savedArrayList.add("0");
                        if (task.isSuccessful()) {
                            int counter = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                               // QuerySnapshot document2 = task.getResult();

                                savedArrayList.add(document.getId()); //FAILS PROGRAM
                                tasks.notifyDataSetChanged();
                                //requestLayout();
                                //notifyDataSetChanged();
                              //  savedArrayList.add("1");
                                counter ++;
                              //  int numInt = Integer.parseInt(document.getData().get("Idea").toString().replaceAll("[\\D]",""));
                                // datapoint ...
                               // result[0] += document.getData().get("Idea") + "\n";
                                //result[0] += document.getData().values().toString();

                                //savedArrayList.add(String.valueOf(document2));
                                //if (document2.exists()) {
                                   // saved[0] = document2.toString();
                                //value = document.getData().values().toString();

                               //value = task.getResult().toString();

                                //QuerySnapshot value1 = task.getResult();
                                //DocumentSnapshot value2 = (DocumentSnapshot) value1;

                                //value = task.getResult().getData().values().toString();
                               if (document.exists()) {
                                    //value = task.getResult().getDocuments().toString();
                                    //value = task.get();
                                  // savedArrayList.add(document.getId().toString());
                                  // value = document.getData().values().toString();
                                  // savedArrayList.add(value);
                                   //savedArrayList.add("!!!!!!!");
                                } else {
                                    Log.d(TAG, "No such document");
                                  // savedArrayList.add("AAAAAHHHHHH");
                                }


                                // }
                            }   // _/
                           // retrieveDataText.setText(result[0]);
                            //savedArrayList.add(result[0]);
                        } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                           // savedArrayList.add("UNSUCCESSFUL");
                            }
                        //} else {
                         //   Log.d(TAG, "get failed with ", task.getException());
                         //
                    });

                //AHHHHHH */

                /*
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/




        //savedArrayList.add(task.getData().values().toString());
       // savedArrayList.add("2");



        //document(affirmation[0])
        //savedArrayList.add(newValue.getResult().getData().values().toString());
        //savedArrayList.add(newValue.getResult().getId().toString());
        //savedArrayList.add(newValue.getResult().getId());

        String [] saved = savedArrayList.toArray(new String[savedArrayList.size()]);

        /*
        final QuerySnapshot<Map<String, dynamic>> docQuery =
        await databaseLoginInfoConnection.collection("users")
                .document(usernameStorage.username)
                .collection("SavedIdeas")
                .get();
    */


      /*  async getMarker() {
    const snapshot = databaseLoginInfoConnection.collection("users")
                    .document(usernameStorage.username)
                    .collection("SavedIdeas")
                    .get();
            return snapshot.docs.map(doc => doc.data());
        }
*/
            //saved[0] = task[0];
           // saved[0] = String.valueOf(sList);



//https://firebase.google.com/docs/firestore/query-data/get-data#java_8

        /*
        ApiFuture<QuerySnapshot> saved =  databaseLoginInfoConnection.collection("users")
                .document(usernameStorage.username)
                .collection("SavedIdeas").get();

         */
/*
        CollectionReference colRef = databaseLoginInfoConnection.collection("users")
                .document(usernameStorage.username)
                .collection("SavedIdeas");
    Query query = colRef;
    AggregateQuery count = query.count();
        count.get(AggregateSource.SERVER).addOnCompleteListener(task ->{
            if(task.isSuccessful()){
                AggregateQuerySnapshot snapshot = task.getResult();
                countVariable = (int) snapshot.getCount();
            }
        });
        //Try catch for thread. necessary in-case interrupted. Also necessary as it takes some time to pull.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < countVariable; i++){
           tips[i] = databaseLoginInfoConnection.collection("users")
                   .document(usernameStorage.username)
                   .collection("SavedIdeas")
                   .document(i);
        }

*/

        listTasks.setOnItemClickListener((adapter, v, position, id) -> {
            String item = adapter.getItemAtPosition(position).toString();
            Map<String, Object> data1 = new HashMap<>();
            databaseLoginInfoConnection.collection("users")
                    .document(usernameStorage.username)
                    .collection("SavedIdeas")
                    .document(item)
                    .delete();
        });
       /*
        listTasks.setOnItemClickListener((adapter, v, position, id) -> {
            String item = adapter.getItemAtPosition(position).toString();
            DocumentReference docIdRef = databaseLoginInfoConnection.collection("users")
                    .document(usernameStorage.username)
                    .collection("SavedIdeas")
                    .document(item);

            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {

                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "Document exists!");
                            Map<String, Object> data1 = new HashMap<>();
                            databaseLoginInfoConnection.collection("users")
                                    .document(usernameStorage.username)
                                    .collection("SavedIdeas")
                                    .document(item)
                                    .delete();
                        } else {
                            Log.d(TAG, "Document does not exist!");
                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                }
            });

        });
*/
        //Unused stuff
        //DocumentReference docRef = databaseLoginInfoConnection.collection("SavedIdeas").document("SavedIdeasList");
        //CollectionReference colRef = databaseLoginInfoConnection.collection("SavedIdeas");


        /*
                binding.listView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Map<String, Object> data1 = new HashMap<>();

                        data1.put("idea1", "Practice mindfulness");
                        databaseLoginInfoConnection.collection("users")
                                .document(usernameStorage.username)
                                .collection("SavedIdeas")
                                .document("SavedIdeasList")
                                .set(data1);
                    }
                });
               //*/

        //.document(item.toString()).set(colRef);
        // Set the "isCapital" field of the city 'DC'
        // docRef
        //        .update("Take a break", true);
        //   .add(field: item)
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

