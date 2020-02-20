package com.bnk.comapny.bnksys.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
 
public class NumberFormat implements TextWatcher {
 
    private EditText txt;
    private String sensor;

    public NumberFormat(EditText txt)
    {
        this.txt = txt;
    }

    @Override
    public void afterTextChanged(Editable s)
    {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        if(s.length() != 0 && !s.toString().equals(sensor)){
            String text = txt.getText().toString();
            text = text.replace(",", "");
            int cnt = 1;
            for(int i = text.length() - 1; i >= 0; i--){
                if(text.charAt(i) != ','){
                    if(cnt == 3){
                        String tmp1 = text.substring(0, i);
                        String tmp2 = text.substring(i);
                        text = tmp1 + "," + tmp2;
                        cnt = 1;
                    }else{
                        cnt++;
                    }
                }
            }
            if(text.charAt(0) == ','){
                text = text.substring(1);
            }
            sensor=text;
            txt.setText(text);
            txt.setSelection(text.length());
        }
    }
}