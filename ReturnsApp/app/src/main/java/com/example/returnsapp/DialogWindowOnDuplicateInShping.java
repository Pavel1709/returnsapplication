package com.example.returnsapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


public class DialogWindowOnDuplicateInShping extends AppCompatDialogFragment {
    String dataMatrix;
    DialogWindowOnDuplicateInShping(String dataMatrix) {
        this.dataMatrix = dataMatrix;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Данного дм нет в системе Shping")
                .setMessage("Данный datamatrix не занесен в систему Shping.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}

