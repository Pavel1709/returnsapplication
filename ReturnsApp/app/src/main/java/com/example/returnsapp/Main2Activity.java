package com.example.returnsapp;

import android.annotation.SuppressLint;

import android.app.Instrumentation;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import android.text.Editable;
import android.text.TextWatcher;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {

    private ListView lv;
    private CustomeAdapter customeAdapter;
    EditText edittext2 = null;

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
            StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);


        TextView textview = (TextView) findViewById(R.id.textView4);

        textview.setTextSize(23);
        textview.setText("   Документ №" + Handler.getDocumentName() + "\n       Сканируйте");

        lv = (ListView) findViewById(R.id.listView);

        CustomeAdapter.editModelArrayList = populateList(1);
        customeAdapter = new CustomeAdapter(this,CustomeAdapter.editModelArrayList);
        lv.setAdapter(customeAdapter);

        System.out.println(lv.getCount());

        edittext2 = findViewById(R.id.editText2);

        edittext2.setFocusableInTouchMode(true);
                edittext2.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager =
                        (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                edittext2.getApplicationWindowToken(),InputMethodManager.SHOW_IMPLICIT, 0);
                edittext2.requestFocus();
                CustomeAdapter.editModelArrayList.remove(0);

            }
        });

        edittext2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("ONKEY");
                if (edittext2.getText().toString().length() >= 31) {
                    System.out.println("PERENOS");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Instrumentation inst = new Instrumentation();
                                for (int i = 0; i < 1; ++i) {
                                    ;
                                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
                                    Thread.sleep(1000);
                                }
                            } catch (InterruptedException e) {
                            }
                        }
                    }).start();
                }
            }
        });
        edittext2.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {



                    Handler.codesMaker(CustomeAdapter.editModelArrayList);
                    lv = (ListView) findViewById(R.id.listView);
                    EditModel editModel = new EditModel();
                    String dm = edittext2.getText().toString();

                    setCursor();
                    System.out.println(Requester.count);
                    if (dm.length() > 30) {
                        try {
                            Requester.sendGETForRepeat(dm.substring(18,31));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                         if(!Requester.count.equals("0")) {
                            if (!Handler.codes.contains(dm.substring(0, 31))) {
                                editModel.setEditTextValue(dm.substring(0, 31));
                                CustomeAdapter.editModelArrayList.add(editModel);
                                customeAdapter.notifyDataSetChanged();
                                lv.setAdapter(customeAdapter);
                                edittext2.setText("");
                                edittext2.setFocusableInTouchMode(true);
                                setCursor();
                            }
                            else {
                                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.mgs_alert_sound);
                                mp.start();
                                DialogWindowOnRepeat dwor = new DialogWindowOnRepeat();
                                showDialog(dwor);
                                edittext2.setText("");
                                return false;
                            }
                         }
                         else {
                             MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.mgs_alert_sound);
                             mp.start();
                             DialogWindowOnDuplicateInShping dwodis = new DialogWindowOnDuplicateInShping(dm.substring(18,31));
                             showDialog(dwodis);
                             edittext2.setText("");
                             return false;
                         }
                    }

                    return true;
                }
                setCursor();
                return false;
            }
        });




        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Requester.sendGETForCodesStatus();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                showDialog(new DialogWindowOnStatus());
            }




        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Handler.codesMaker(CustomeAdapter.editModelArrayList);
               JSONParser jsonparser = new JSONParser();
                try {
                    jsonparser.jsonMake();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Requester.sendPOSTForCodes();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(EditModel em: CustomeAdapter.editModelArrayList) {
                    System.out.println(em.getEditTextValue());
                }
                CustomeAdapter.editModelArrayList = new ArrayList<EditModel>();
                Handler.codesMaker(CustomeAdapter.editModelArrayList);
                JSONParser.jsonObject = new JSONObject();
                showDialog(new DialogWindowOnContinue());
            }
        });







    }
    private ArrayList<EditModel> populateList(int l){
        ArrayList<EditModel> list = new ArrayList<>();
        for(int i = 0; i < l; i++){
            EditModel editModel = new EditModel();
            editModel.setEditTextValue("");
            list.add(editModel);
        }
        return list;
    }
    private void showDialog(AppCompatDialogFragment dialog) {
        //DialogWindowOnStatus dialog = new DialogWindowOnStatus();
        dialog.show(getSupportFragmentManager(),"status dialog");
    }
    public void setCursor() {
        edittext2.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager =
                        (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                        edittext2.getApplicationWindowToken(),InputMethodManager.SHOW_IMPLICIT, 0);
                edittext2.requestFocus();
            }
        });
    }
}