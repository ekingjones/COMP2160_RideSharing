package com.comp2160.robot.comp2160_ridesharing;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button button_1,button_2;
    EditText edit_1,edit_2;

    TextView text_1;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_1 = (Button)findViewById(R.id.button);
        edit_1 = (EditText)findViewById(R.id.editText);
        edit_2 = (EditText)findViewById(R.id.editText2);

        button_2 = (Button)findViewById(R.id.button2);
        text_1 = (TextView)findViewById(R.id.textView3);
        text_1.setVisibility(View.GONE);

        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_1.getText().toString().equals("admin") &&
                        edit_2.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Incorrect Information.",
                            Toast.LENGTH_SHORT).show();

                    text_1.setVisibility(View.VISIBLE);
                    text_1.setBackgroundColor(Color.RED);
                    counter--;
                    text_1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        button_1.setEnabled(false);
                    }
                }
            }
        });

        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void loginToApp(View v){
        Intent startApp = new Intent(LoginActivity.this, FindRideActivity.class);
        startActivity(startApp);
    }

    public void signUp(View v){
        Intent signUpAccount = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(signUpAccount);
    }

}
