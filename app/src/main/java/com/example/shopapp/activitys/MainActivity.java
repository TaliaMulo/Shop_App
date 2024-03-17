package com.example.shopapp.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopapp.R;
import com.example.shopapp.data.Product;
import com.example.shopapp.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    }

    public void loginFunc(View view)
    {

        String email = ((EditText)findViewById(R.id.nameLogin)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.passwordLogin)).getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this , "login ok" , Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainScreenFragment );
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this , "Incorrect username or password" , Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
    public void regFunc(View view){

        String email = ((EditText)findViewById(R.id.userEmail)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.password)).getText().toString().trim();

        if (checkEmpty()){
            Toast.makeText(MainActivity.this , "Fill all the fields" , Toast.LENGTH_LONG).show();
        }
        else {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //           FirebaseUser user = mAuth.getCurrentUser();
                                writeUser();
                                //readUser();

                                Toast.makeText(MainActivity.this , "register ok" , Toast.LENGTH_LONG).show();
                                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_mainScreenFragment);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this , "register fail" , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public boolean checkEmpty(){
        String email = ((EditText)findViewById(R.id.userEmail)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.password)).getText().toString().trim();
        String phone = ((EditText)findViewById(R.id.phone)).getText().toString().trim();
        String age = ((EditText)findViewById(R.id.age)).getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || phone.isEmpty() || age.isEmpty()){
            return true;
        }
        else{
            return false;
        }

    }

    public void readUser(){


        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(userId);



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue(User.class);
                TextView title = findViewById(R.id.textView3);
                title.setText("Welcome " + value.getName() );
                Toast.makeText(MainActivity.this, value.getName(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, " Failed to read value", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void writeUser(){

        if (checkEmpty()){ }
        else {
            EditText email = findViewById(R.id.userEmail);
            EditText phone = findViewById(R.id.phone);
            EditText name = findViewById(R.id.userName);
            EditText password=findViewById(R.id.password);
            EditText age = findViewById(R.id.age);

            FirebaseUser currentUser=mAuth.getCurrentUser();
            String str = currentUser.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users").child(str);

            User data = new User( age.getText().toString(),email.getText().toString() ,name.getText().toString() , phone.getText().toString());


            myRef.setValue(data);
        }

    }


    public void readProduct(){

        // EditText password = findViewById(R.id.passwordLogin);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentProduct = mAuth.getCurrentUser();
        String productId = currentProduct.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("products").child(productId);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Product value = dataSnapshot.getValue(Product.class);



                Toast.makeText(MainActivity.this, value.getNameProduct(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, " Failed to read value", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void writeProduct(){

        if (checkEmptyProduct()){ }
        else {
            EditText nameProduct = findViewById(R.id.nameProduct2);
            EditText amountProduct = findViewById(R.id.amountProduct2);

            FirebaseUser currentProduct=mAuth.getCurrentUser();
            String str = currentProduct.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("products").child(str).child(nameProduct.getText().toString());

            Product data = new Product( nameProduct.getText().toString(),amountProduct.getText().toString());

            myRef.setValue(data);
        }
    }

    public boolean checkEmptyProduct(){
        String namePro = ((EditText)findViewById(R.id.nameProduct2)).getText().toString().trim();
        String anountPro = ((EditText)findViewById(R.id.amountProduct2)).getText().toString().trim();

        if (namePro.isEmpty() || anountPro.isEmpty()){
            return true;
        }
        else{
            return false;
        }

    }



}