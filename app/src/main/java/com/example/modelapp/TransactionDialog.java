package com.example.modelapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class TransactionDialog extends AppCompatDialogFragment {
    private stringListener listener;
    EditText etype,eamount,ereason;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.transaction_dialog, null);


        builder.setView(view)
                .setTitle("Add Transaction")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String stype = etype.getText().toString();
                        String samount = eamount.getText().toString();
                        String sreason = ereason.getText().toString();
                        listener.applyfun(stype,samount,sreason);
                    }
                });

        etype = view.findViewById(R.id.type);
        eamount = view.findViewById(R.id.amount);
        ereason = view.findViewById(R.id.reason);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (stringListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface stringListener {
        void applyfun(String itype, String iamount,String ireason);
    }
}
