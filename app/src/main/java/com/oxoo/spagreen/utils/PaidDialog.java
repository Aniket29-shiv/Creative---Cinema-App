package com.oxoo.spagreen.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.oxoo.spagreen.R;

public class PaidDialog extends Dialog implements
        android.view.View.OnClickListener{

    private Context context;
    private Dialog d;
    private Button continueBtn;
    private TextView instruction_text_view;


    public PaidDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.paid_alert_dialog);
        continueBtn = findViewById(R.id.continueBtn);
        instruction_text_view = findViewById(R.id.instruction_text_view);

        String instruction_message_1 = getContext().getResources().getString(R.string.dialog_instruction_1);
        String app_name = getContext().getResources().getString(R.string.app_name);
        String instruction_message_2 = getContext().getResources().getString(R.string.dialog_instruction_2);
        instruction_text_view.setText(instruction_message_1 + " " + app_name + " " + instruction_message_2);

        continueBtn.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continueBtn:
                dismiss();
                break;

            default:
                break;
        }
        dismiss();
    }

    }

