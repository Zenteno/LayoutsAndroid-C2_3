package com.xberto.calculadora;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView res;
    private Float val;
    private char op='\0';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = findViewById(R.id.textView);

        View.OnClickListener evento = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button boton = (Button)v;
                String operadoras = "/X+-";
                if(operadoras.contains(boton.getText().toString())){
                    op = boton.getText().charAt(0);
                    val = null;
                    return;
                }
                switch (boton.getText().toString().charAt(0)){
                    case 'C':
                        res.setText("0");
                        val = null;
                        op = '\0';
                        break;
                    case '.':
                        if (!res.getText().toString().contains("."))
                            res.append(boton.getText());
                        break;

                    case '=':
                        float t = Float.parseFloat(res.getText().toString());
                        switch (op){
                            case '+':
                                res.setText(""+(t+val));
                                break;
                            case '-':
                                res.setText(""+(val-t));
                                break;
                            case '/':
                                if(val!=0)
                                    res.setText(""+(val/t));
                                break;
                            case 'X':
                                    res.setText(""+(t*val));
                                break;
                        }
                        op = '\0';
                        break;
                    default:
                        if(op!='\0' && val==null){
                            val = Float.parseFloat(res.getText().toString());
                            res.setText("");
                        }
                        if(res.getText().toString().equals("0"))
                            res.setText("");
                        res.append(boton.getText());
                        break;
                }
            }
        };
        String name = getPackageName();
        Resources r = getResources();
        /*int id;
        for(int i = 0; i <=16; i++) {
            id =  r.getIdentifier("btn" + i, "id", name);
            findViewById(id).setOnClickListener(evento);
        }*/

        TableLayout tl = findViewById(R.id.tabla);
        TableRow tr=null;
        Button btn;
        String botones = "7894561230.=";
        for(int i=0;i<botones.length();i++){
            if(i%3==0){
                if(tr!=null)
                    tl.addView(tr);
                tr = new TableRow(this);

                TableLayout.LayoutParams lp = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT
                );
                tr.setLayoutParams(lp);
                tr.setWeightSum(3);
            }
            btn = new Button(this);
            TableRow.LayoutParams blp = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            blp.weight = 1;
            btn.setLayoutParams(blp);
            btn.setText(String.valueOf(botones.charAt(i)));
            btn.setOnClickListener(evento);
            tr.addView(btn);
        }
        tl.addView(tr);

        LinearLayout ly = findViewById(R.id.operaciones);
        for(Character c : "/X-+".toCharArray()){
            btn = new Button(this);
            btn.setText(String.valueOf(c));
            btn.setOnClickListener(evento);

            LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0
            );
            blp.weight = 1;
            btn.setLayoutParams(blp);

            ly.addView(btn);
        }
    }
}
