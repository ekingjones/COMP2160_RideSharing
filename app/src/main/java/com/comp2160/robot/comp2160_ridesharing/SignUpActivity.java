package com.comp2160.robot.comp2160_ridesharing;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class SignUpActivity extends Activity implements View.OnClickListener
{

    private EditText firstnameEdittext,lastnameEdittext,emailEdittext,passEdittext,passAgainEdittext,
            birthdayEdittext;
    private RadioGroup genderRadioGroup;
    private Button registerButton;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bindViews();
        setViewActions();
        prepareDatePickerDialog();
    }

    private void bindViews()
    {
        firstnameEdittext=(EditText)findViewById(R.id.firstname_edittext);
        lastnameEdittext=(EditText)findViewById(R.id.lastname_edittext);
        emailEdittext=(EditText)findViewById(R.id.email_edittext);
        passEdittext=(EditText)findViewById(R.id.password_edittext);
        passAgainEdittext=(EditText)findViewById(R.id.password_again_edittext);
        birthdayEdittext=(EditText)findViewById(R.id.birthday_edittext);
        genderRadioGroup=(RadioGroup)findViewById(R.id.gender_radiogroup);
        registerButton=(Button)findViewById(R.id.register_button);
    }

    private void setViewActions()
    {
        birthdayEdittext.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    private void prepareDatePickerDialog()
    {
        Calendar calendar=Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                birthdayEdittext.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                datePickerDialog.dismiss();
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void showToastWithFormValues()
    {
        String firstname=firstnameEdittext.getText().toString();
        String lastname=lastnameEdittext.getText().toString();
        String email=emailEdittext.getText().toString();
        String pass=passEdittext.getText().toString();
        String passAgain=passAgainEdittext.getText().toString();
        String birthday=birthdayEdittext.getText().toString();

        RadioButton selectedRadioButton=(RadioButton)findViewById
                (genderRadioGroup.getCheckedRadioButtonId());
        String gender=selectedRadioButton==null ? "":selectedRadioButton.getText().toString();

        if(!firstname.equals("")&&!lastname.equals("")&&!email.equals("")&&!pass.equals("")&&
                !passAgain.equals("")&&!birthday.equals("")&&!gender.equals("")){

            if(pass.equals(passAgain))
            {

                Toast.makeText(this,getResources().getString(R.string.here_is_values,
                        ("\nFirstname:"+firstname+"\nLastname:"+lastname+"\nEmail:"+email+"" +
                                "\nBirthday:"
                                +birthday+"" +
                                "\nGender:"+gender)),
                        Toast.LENGTH_SHORT).show();
            }
            else
                {
                Toast.makeText(this,getResources().getString(R.string.passwords_must_be_the_same),
                        Toast.LENGTH_SHORT).show();
            }
        }
        else
            {
            Toast.makeText(this,getResources().getString(R.string.no_field_can_be_empty),
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.birthday_edittext:
                datePickerDialog.show();
                break;
            case R.id.register_button:
                showToastWithFormValues();
                break;
        }
    }
}