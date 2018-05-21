package com.example.mitsu.calculator;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity{
    private boolean input;
    private int length;
    private int operator;
    private boolean isSetOperator,finishOperate;
    private BigDecimal firstOperator,secondOperator,calculationResult;
    private final BigDecimal ZERO = new BigDecimal(0);
    private boolean isOverflow;
    TextView answer;
    ImageButton button;
    private boolean isSetOperand;
    private final String[] str= {"num0","num1","num2","num3","num4","num5","num6","num7","num8","num9","add","sub","equal","mult","div","dot"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        answer = findViewById(R.id.answer);
        initCalculator();

        int viewId;
        ImageButton imageButton;
        Resources res = getResources();
        for(int i = 0; i < str.length; i++){
            viewId = res.getIdentifier(str[i], "id", getPackageName());
            imageButton = (ImageButton)findViewById(viewId);
            imageButton.setOnClickListener(buttonClick);
        }
        final Button buttonClear = findViewById(R.id.clear);
        buttonClear.setOnClickListener(buttonClick);
        final Button buttonClearEntry = findViewById(R.id.clearEntry);
        buttonClearEntry.setOnClickListener(buttonClick);

    }
    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.clear:
                    initCalculator();
                    break;
                case R.id.clearEntry:
                    clearEntry();
                    break;
                case R.id.add:
                case R.id.mult:
                case R.id.div:
                case R.id.sub:
                    setOperator(view);
                    break;
                case R.id.equal:
                    clickEqual();
                    break;
                default:
                    clickNumber(view);
                    break;
            }
        }
    };

    public void initCalculator(){
        input=true;
        isSetOperator=false;
        finishOperate=true;
        firstOperator=ZERO;
        secondOperator=ZERO;
        isSetOperand=false;
        calculationResult=ZERO;
        answer.setText("0");
        length=1;
        isOverflow=false;
    }

    public void clearEntry(){
        secondOperator=ZERO;
        input=true;
        answer.setText(String.valueOf(0));
    }




    public void clickNumber(View view) {
        int numberId;
        numberId=view.getId();
        answer = findViewById(R.id.answer);
        button = findViewById(numberId);
        String answerString=answer.getText().toString();
        String numberString=null;
        switch (numberId){
            case R.id.num0:
                numberString="0";
                break;
            case R.id.num1:
                numberString="1";
                break;
            case R.id.num2:
                numberString="2";
                break;
            case R.id.num3:
                numberString="3";
                break;
            case R.id.num4:
                numberString="4";
                break;
            case R.id.num5:
                numberString="5";
                break;
            case R.id.num6:
                numberString="6";
                break;
            case R.id.num7:
                numberString="7";
                break;
            case R.id.num8:
                numberString="8";
                break;
            case R.id.num9:
                numberString="9";
                break;
            case R.id.dot:
                numberString=".";
                break;
        }


        if (!input&&(numberId != R.id.dot || (!answerString.contains(".")))&&length < 9) {
            answerString = answerString.concat(numberString);
            answer.setText(answerString);
        } else {
            if (numberId != R.id.dot){
                answer.setText(numberString);
            }
            else{
                answer.setText("0.");
            }
            if (numberId != R.id.num0) input = false;
        }
        length = answer.getText().length();

        answerString = answer.getText().toString();

        if (!isSetOperator)
            firstOperator =new BigDecimal(answerString);
        else {
            secondOperator =new BigDecimal(answerString);
            isSetOperand = true;
        }

    }

    public void setOperator(View view){
        if(!finishOperate&&isSetOperand){
            calculate();
        }
        button = findViewById(view.getId());
        if(button.getId()==R.id.add) operator = 1;
        else if(button.getId()==R.id.sub) operator = 2;
        else if(button.getId()==R.id.mult) operator = 3;
        else if(button.getId()==R.id.div) operator = 4;
        isSetOperator = true;
        input = true;
        finishOperate=false;
    }

    public void clickEqual(){
        calculate();
        finishOperate=true;
        input=true;
    }

    public void calculate() {
        if (secondOperator.compareTo(ZERO) == 0 && operator == 4) {
            Toast.makeText(MainActivity.this, "#DIV/0!", Toast.LENGTH_LONG).show();
        }else {
            //Toast.makeText(MainActivity.this,("x"+String.valueOf(x)+"y"+String.valueOf(y)), Toast.LENGTH_LONG).show();
            if (operator == 1) calculationResult = firstOperator.add(secondOperator);
            else if (operator == 2) calculationResult = firstOperator.subtract(secondOperator);
            else if (operator == 3) calculationResult = firstOperator.multiply(secondOperator);
            else if (operator == 4) calculationResult = firstOperator.divide(secondOperator, 8, BigDecimal.ROUND_HALF_UP);

            firstOperator = calculationResult;

            answer.setText(setResultString(calculationResult));
            isSetOperator = false;
            isSetOperand = false;
        }
    }

    private String setResultString(BigDecimal result) {
        String resultString="0";
        if(isOverflow){
            Toast.makeText(MainActivity.this, "Over Flow!", Toast.LENGTH_LONG).show();
            initCalculator();
        }else {
            int i = result.precision() - result.scale();
            int j= result.precision();
            if (i > 9) {
                result = result.setScale(-5, BigDecimal.ROUND_HALF_UP);
                isOverflow = true;
            } else if (j > 9) {
                result = result.setScale(9 - i, BigDecimal.ROUND_HALF_UP);
            }
            if(result.compareTo(ZERO)==0) result=ZERO;
            if(result.scale()>0)result=result.stripTrailingZeros();
            resultString = result.toString();
        }
        return resultString;
    }


}
