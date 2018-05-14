package com.example.mitsu.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {
    private boolean d,f;
    private int Length;
    private int operator;
    private boolean setOperator=false,finishOperate,setOperand;
    private double x,y,result;
    EditText Answer;
    ImageButton N;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Answer = findViewById(R.id.Answer);
        Answer.setFocusable(false);
        d=false;f=true;
        setOperator=false;finishOperate=true;setOperand=false;
        x=y=result=0;
        Length=Answer.getText().length();


    }

    public void initCalculator(View view){
        Answer.setFocusable(false);
        d=false;f=true;
        setOperator=false;finishOperate=true;setOperand=false;
        x=y=result=0;
        Answer.setText(String.valueOf(result));
    }

    public void CE(View view){
        y=0;
        f=true;
        Answer.setText(String.valueOf(0));
    }

    public void ClickNumber(View view) {
        Answer = findViewById(R.id.Answer);
        N = findViewById(view.getId());
        if (Length < 10) {
            if (!f) {
                //Toast.makeText(MainActivity.this,Answer.getText().toString() , Toast.LENGTH_LONG).show();
                Answer.setText(Answer.getText().toString() + N.getContentDescription().toString());
            } else if (N.getId() == R.id.Dot) {
                if (!d) {
                    Answer.setText(Answer.getText().toString() + N.getContentDescription().toString());
                    d = true;
                }
            } else {
                Answer.setText(N.getContentDescription());
                f = false;
            }
            Length = Answer.getText().length();
            Toast.makeText(MainActivity.this, String.valueOf(Answer.getText().length()), Toast.LENGTH_LONG).show();
        }
        if(!setOperator)
            x=Double.parseDouble(Answer.getText().toString());
        else{
            y=Double.parseDouble(Answer.getText().toString());
            setOperand=true;
        }

    }

    public void setOperator(View view){
        if(!finishOperate&&setOperand){
            Calsulate();
        }
        N = findViewById(view.getId());
        if(N.getId()==R.id.Add) operator=1;
        if(N.getId()==R.id.Sub) operator=2;
        if(N.getId()==R.id.Mult) operator=3;
        if(N.getId()==R.id.Div) operator=4;
        setOperator=true;
        f=true;finishOperate=false;
    }

    public void clickEqual(View view){
        Calsulate();
        finishOperate=true;
    }

    public void Calsulate(){
        if(operator==1)result=x+y;
        if(operator==2)result=x-y;
        if(operator==3)result=x*y;
        if(operator==4)result=x/y;
        x=result;
        Answer.setText(String.valueOf(result));
        setOperator=false;
        setOperand=false;

    }

}
