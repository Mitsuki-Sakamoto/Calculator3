package com.example.mitsu.calculator;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity{
    private final BigDecimal BigDecimalZERO = new BigDecimal(0);
    private final String[] buttonName = {"num0","num1","num2","num3","num4","num5","num6","num7","num8","num9","add","sub","equal","mult","div","dot","clear","clearEntry"};
    private final int add = 1;
    private final int sub = 2;
    private final int mult = 3;
    private final int div = 4;
    private boolean isInputted;
    private TextView answerText;
    private int operator;
    private boolean isSetOperator, isOperateFinished;
    private BigDecimal firstOperator,secondOperator,calculationResult;
    private boolean isOverflow;
    private boolean isSetOperand;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        answerText = findViewById(R.id.answer);
        initCalculator();
        int viewId;
        View imageButton;
        Resources res = getResources();
        for(int i = 0; i < buttonName.length; i++) {
            viewId = res.getIdentifier(buttonName[i], "id", getPackageName());
            imageButton = findViewById(viewId);
            imageButton.setOnClickListener(buttonClick);
        }
    }

    public void initCalculator(){
        isInputted = true;
        isSetOperator = false;
        isOperateFinished = true;
        firstOperator = BigDecimalZERO;
        secondOperator = BigDecimalZERO;
        isSetOperand = false;
        calculationResult= BigDecimalZERO;
        answerText.setText("0");
        isOverflow = false;
    }

    public void clearEntry(){
        secondOperator = BigDecimalZERO;
        isInputted = true;
        answerText.setText("0");
    }

    public void clickNumber(View view) {
        int numberId = view.getId();
        String numberString = null;
        switch (numberId){
            case R.id.num0:
                numberString="0";
                break;
            case R.id.num1:
                numberString = "1";
                break;
            case R.id.num2:
                numberString = "2";
                break;
            case R.id.num3:
                numberString = "3";
                break;
            case R.id.num4:
                numberString = "4";
                break;
            case R.id.num5:
                numberString = "5";
                break;
            case R.id.num6:
                numberString = "6";
                break;
            case R.id.num7:
                numberString = "7";
                break;
            case R.id.num8:
                numberString = "8";
                break;
            case R.id.num9:
                numberString = "9";
                break;
            case R.id.dot:
                numberString = ".";
                break;
        }
        answerText = findViewById(R.id.answer);
        String answerString = this.answerText.getText().toString();
        if (!isInputted && (numberId != R.id.dot || (!answerString.contains("."))) && answerText.length() < 9) {
            answerString = answerString.concat(numberString);
            answerText.setText(answerString);
        } else {
            if (numberId != R.id.dot){
                answerText.setText(numberString);
            }
            else{
                answerText.setText("0.");
            }
            if (numberId != R.id.num0) isInputted = false;
        }
        answerString = this.answerText.getText().toString();
        if (!isSetOperator) {
            firstOperator = new BigDecimal(answerString);
        }else {
            secondOperator = new BigDecimal(answerString);
            isSetOperand = true;
        }
    }

    public void setOperator(View view){
        ImageButton button;
        if(!isOperateFinished && isSetOperand){
            calculate();
        }
        button = findViewById(view.getId());
        if(button.getId() == R.id.add) operator = add;
        else if(button.getId() == R.id.sub) operator = sub;
        else if(button.getId() == R.id.mult) operator = mult;
        else if(button.getId() == R.id.div) operator = div;
        isSetOperator = true;
        isInputted = true;
        isOperateFinished = false;
    }

    public void clickEqual(){
        calculate();
        isOperateFinished = true;
        isInputted =true;
    }

    public void calculate() {
        if (secondOperator.compareTo(BigDecimalZERO) == 0 && operator == div) {
            Toast.makeText(MainActivity.this, "#DIV/0!", Toast.LENGTH_LONG).show();
        }else {
            //Toast.makeText(MainActivity.this,("x"+String.valueOf(x)+"y"+String.valueOf(y)), Toast.LENGTH_LONG).show();
            if (operator == add) calculationResult = firstOperator.add(secondOperator);
            else if (operator == sub) calculationResult = firstOperator.subtract(secondOperator);
            else if (operator == mult) calculationResult = firstOperator.multiply(secondOperator);
            else if (operator == div) calculationResult = firstOperator.divide(secondOperator, 8, BigDecimal.ROUND_HALF_UP);
            firstOperator = calculationResult;
            answerText.setText(setResultString(calculationResult));
            isSetOperator = false;
            isSetOperand = false;
        }
    }

    private String setResultString(BigDecimal result) {
        String resultString = "0";
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
            if(result.compareTo(BigDecimalZERO) == 0) result= BigDecimalZERO;
            if(result.scale() > 0)result=result.stripTrailingZeros();
            resultString = result.toString();
        }
        return resultString;
    }
}
