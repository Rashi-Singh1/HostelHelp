package com.example.hostelhelpbox;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.ProgressDialog;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference ref;
    private Button Submit;
    private EditText Name;
    private EditText Email;
    private EditText Password;
    private ProgressDialog progress;
    private Spinner Hosteldrop;
    private String Hostel;
    private String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        firebaseAuth = FirebaseAuth.getInstance();
        Submit = (Button) findViewById(R.id.submit);
        Name = (EditText) findViewById(R.id.name);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.passw);
        Hosteldrop = (Spinner) findViewById(R.id.hostel);

        //putting values in the dropdown list
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(SignUp.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.hostelNames));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Hosteldrop.setAdapter(myAdapter);

        progress = new ProgressDialog(this);
        Submit.setOnClickListener(this);
    }

    private void RegisterUser() {
        String getName = Name.getText().toString().trim();
        String getEMail = Email.getText().toString().trim();
        String getPassw = Password.getText().toString().trim();
        if(TextUtils.isEmpty(getName)){
            Toast.makeText(this,"Please enter your name",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(getEMail)){
            Toast.makeText(this,"Please enter your Email",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            if(!getEMail.endsWith("@iitg.ac.in"))
            {
                Toast.makeText(getApplicationContext(),"Only IITG members, use IITG email",Toast.LENGTH_LONG).show();
                return;
            }

        }

        UserName = getEMail.substring(0,getEMail.lastIndexOf('@'));
        if(Hostel.equals("Select Hostel"))
        {
            Toast.makeText(this,"Please choose your hostel",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(getPassw)){
            Toast.makeText(this,"Please enter your Password",Toast.LENGTH_SHORT).show();
            return;
        }
        progress.setMessage("Signing In ...");
        progress.show();

        ref = FirebaseDatabase.getInstance().getReference().child("Users");

        User newUser = new User(getName,getEMail,getPassw,Hostel);
        String key = ref.push().getKey();
        newUser.setKey(key);
        newUser.setUsername(UserName);
        //reff.push().setValue(Temp);
        ref.child(key).setValue(newUser);

        Toast.makeText(this,"Done!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v == Submit){
            Hostel = Hosteldrop.getSelectedItem().toString();
            RegisterUser();
        }
    }

}
