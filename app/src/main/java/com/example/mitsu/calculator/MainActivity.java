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
    private boolean f;
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
        f=true;
        setOperator=false;finishOperate=true;setOperand=false;
        x=y=result=0;
        Length=Answer.getText().length();


    }

    public void initCalculator(View view){
        Answer.setFocusable(false);
        ;f=true;
        setOperator=false;finishOperate=true;setOperand=false;
        x=y=result=0;
        Answer.setText(String.valueOf(0));
        Length=0;
    }

    public void CE(View view){
        y=0;
        f=true;
        Answer.setText(String.valueOf(0));
    }

    boolean int_num(double a){
        return a==(int)a;
    }


    public void ClickNumber(View view) {
        double a,b,ans;
        Answer = findViewById(R.id.Answer);
        N = findViewById(view.getId());
        String Sans=Answer.getText().toString();
        String SN=N.getContentDescription().toString();

            if (Length < 10) {
                if (!f) {
                        if (N.getId() != R.id.Dot||!Sans.contains(".")) {

                            Sans = Sans + SN;
                            //Toast.makeText(MainActivity.this,Sans, Toast.LENGTH_LONG).show();
                            Answer.setText(Sans);
                        }


                } else {
                    if(N.getId()!=R.id.Dot)Answer.setText(SN);
                    else Answer.setText("0.");
                    //Toast.makeText(MainActivity.this, N.getContentDescription(), Toast.LENGTH_LONG).show();
                    if (N.getId() != R.id.Num0) f = false;
                }
                Length = Answer.getText().length();
            }
        Sans=Answer.getText().toString();
            if (!setOperator)
                x = Double.parseDouble(Sans);
            else {
                y = Double.parseDouble(Sans);
                setOperand = true;
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
        if(y==0&&operator==4){
            Toast.makeText(MainActivity.this, "エラー", Toast.LENGTH_LONG).show();
        }else {
            if (operator == 1) result = x + y;
            if (operator == 2) result = x - y;
            if (operator == 3) result = x * y;
            if (operator == 4) result = x / y;
            x = result;
            if(int_num(result))Answer.setText(String.valueOf((int)result));
            else Answer.setText(String.valueOf(result));
            setOperator = false;
            setOperand = false;
        }
    }


}
