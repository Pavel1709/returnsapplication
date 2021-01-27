package com.example.returnsapp;

import java.util.ArrayList;

class Handler {

    static String documentName;
    static ArrayList<String> codes;

    Handler() {
        codes = new ArrayList<>();
    }

    public static String getDocumentName() {
        return documentName;
    }

    public  void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public static void codesMaker(ArrayList<EditModel> editModels) {
        for (EditModel editModel : editModels) {
            if (!editModel.getEditTextValue().equals("")) {
                codes.add(editModel.getEditTextValue());
            }
        }
    }

    public static ArrayList<String> getCodes() {
        return codes;
    }
}
