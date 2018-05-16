package com.example.mitsu.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {
    private boolean f;
    private int Length;
    private int operator;
    private boolean setOperator=false,finishOperate,setOperand;
    private BigDecimal x,y,result,zero;
    boolean over;
    TextView Answer;
    ImageButton N;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Answer = findViewById(R.id.Answer);
        f=true;
        setOperator=false;finishOperate=true;setOperand=false;
        zero=new BigDecimal("0");
        x=y=result=zero;
        Length=Answer.getText().length();
        over=false;

    }
    public void initCalculator(){
        f=true;
        setOperator=false;finishOperate=true;setOperand=false;
        x=y=result=new BigDecimal("0");
        Answer.setText(String.valueOf(0));
        Length=0;
        over=false;
    }
    public void initCalculator(View view){
        f=true;
        setOperator=false;finishOperate=true;setOperand=false;
        x=y=result=new BigDecimal("0");
        Answer.setText(String.valueOf(0));
        Length=0;
        over=false;
    }

    public void CE(View view){
        y=zero;
        f=true;
        Answer.setText(String.valueOf(0));
    }




    public void ClickNumber(View view) {
        double a,b,ans;
        Answer = findViewById(R.id.Answer);
        N = findViewById(view.getId());
        String Sans=Answer.getText().toString();
        String SN=N.getContentDescription().toString();


                if (!f) {
                    if ((N.getId() != R.id.Dot || !Sans.contains("."))&&Length < 9) {

                        Sans = Sans + SN;
                        //Toast.makeText(MainActivity.this,Sans, Toast.LENGTH_LONG).show();
                        Answer.setText(Sans);
                    }


                } else {
                    if (N.getId() != R.id.Dot) Answer.setText(SN);
                    else Answer.setText("0.");
                    //Toast.makeText(MainActivity.this, N.getContentDescription(), Toast.LENGTH_LONG).show();
                    if (N.getId() != R.id.Num0) f = false;
                }
                Length = Answer.getText().length();

                Sans = Answer.getText().toString();
                    if (!setOperator)
                        x =new BigDecimal(Sans);
                    else {
                        y =new BigDecimal(Sans);
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
        f=true;
    }

    public void Calsulate(){

        if(y.compareTo(zero)==0&&operator==4){
            Toast.makeText(MainActivity.this, "エラー", Toast.LENGTH_LONG).show();
        }else {
            //Toast.makeText(MainActivity.this,("x"+String.valueOf(x)+"y"+String.valueOf(y)), Toast.LENGTH_LONG).show();
            if (operator == 1) result = x.add(y);
            if (operator == 2) result = x.subtract(y);
            if (operator == 3) result = x.multiply(y);
            if (operator == 4) result = x.divide(y,8,BigDecimal.ROUND_HALF_UP);
            x = result;

            Answer.setText(setR(result));
            setOperator = false;
            setOperand = false;
        }
    }

    private String setR(BigDecimal result) {
        String Sresult="0";
        if(over){
            Toast.makeText(MainActivity.this, "エラー", Toast.LENGTH_LONG).show();
            initCalculator();
        }else {

            int i = result.precision() - result.scale();
            if (i > 9) {
                result = result.setScale(-5, BigDecimal.ROUND_HALF_UP);
                over = true;
            } else if (result.precision() > 9) {
                result = result.setScale(9 - i, BigDecimal.ROUND_HALF_UP);
            }
            Sresult = result.toString();
        }
        return Sresult;

    }


}
