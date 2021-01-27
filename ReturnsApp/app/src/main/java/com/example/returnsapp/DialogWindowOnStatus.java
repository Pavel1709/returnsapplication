package com.example.returnsapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogWindowOnStatus extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Cтатус")
                .setMessage("KRUTOTA")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        switch(Requester.status) {
            case "success" : builder.setMessage("Запрос на возврат успешно отправлен");
            break;
            case "pending" : builder.setMessage("Запрос на возврат в очереди");
            break;
            case "failed" : builder.setMessage("Запрос на возврат не отправлен");
            break;
        }
        return builder.create();
    }
}
