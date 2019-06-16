package fikt.pmp.weatherreminder;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

import fikt.pmp.weatherreminder.DataModel.User;

public class UserAlertActivity extends AppCompatActivity {
    public static final String TAG = "Weather Reminder:";
    private FirebaseFirestore mDb;
    private FirebaseAuth mAuth;
    private FirebaseUser mAuthUser;
    private String mUserID;
    private User mUser;

    private DocumentReference userRef;

    private CheckBox chkrain;
    private CheckBox chksnow;
    private CheckBox chkthunder;
    private CheckBox chkmist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useralert);
        mDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAuthUser = mAuth.getCurrentUser();
        mUserID = mAuth.getCurrentUser().getUid();

        chkmist = findViewById(R.id.checkbox_mist);
        chkrain = findViewById(R.id.checkbox_rain);
        chksnow = findViewById(R.id.checkbox_snow);
        chkthunder = findViewById(R.id.checkbox_thunder);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUser();
        userRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    User user = documentSnapshot.toObject(User.class);

                    chkmist.setChecked(user.getMist());
                    chkrain.setChecked(user.getRain());
                    chksnow.setChecked(user.getSnow());
                    chkthunder.setChecked(user.getThunder());
                }
            }
        });
        Button saveButton = findViewById(R.id.btn_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mAuthUser.getDisplayName();
                String email = mAuthUser.getEmail();
                Boolean rain = chkrain.isChecked();
                Boolean snow = chksnow.isChecked();
                Boolean thunder = chkthunder.isChecked();
                Boolean mist = chkmist.isChecked();
                User saveUser = new User(username, email, rain, snow, thunder, mist);
                addUserConfig(saveUser);
            }
        });
    }

    public void getUser() {
        userRef = mDb.collection("users").document(mUserID);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    User user = documentSnapshot.toObject(User.class);
                    chkmist.setChecked(user.getMist());
                    chkrain.setChecked(user.getRain());
                    chksnow.setChecked(user.getSnow());
                    chkthunder.setChecked(user.getThunder());
                }
            }
        });
    }

    public void addUserConfig(User saveUser) {
        mDb.collection("users").document(mUserID).set(saveUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UserAlertActivity.this, "Configuration saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserAlertActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}
