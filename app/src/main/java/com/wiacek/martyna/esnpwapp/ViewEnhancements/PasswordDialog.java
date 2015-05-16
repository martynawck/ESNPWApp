package com.wiacek.martyna.esnpwapp.ViewEnhancements;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.AsyncTask.ChangePasswordTask;
import com.wiacek.martyna.esnpwapp.R;

/**
 * Created by Martyna on 2015-05-14.
 */
public class PasswordDialog extends DialogPreference {

    EditText originalPwd;
    EditText newPwdFirst;
    EditText newPwdSecond;

    public PasswordDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.custom_preference_password_dialog);
        setPersistent(true);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        builder.setTitle("Change your password");
        super.onPrepareDialogBuilder(builder);

    }

    @Override
    public void onClick(DialogInterface dialog, int which){

        if(which == DialogInterface.BUTTON_POSITIVE) {
            Log.d("POS","POSITIVE");
            Log.d("LSLDA", newPwdFirst.getText().toString());
            Log.d("LSLDA",newPwdSecond.getText().toString());
            if (newPwdFirst.getText().toString().equals(newPwdSecond.getText().toString())) {
                Log.d("RES", "TAK");
                ChangePasswordTask task = new ChangePasswordTask(getContext());
                task.execute(new String[] {originalPwd.getText().toString(), newPwdSecond.getText().toString()});

                Toast.makeText(getContext(), "Value updated!", Toast.LENGTH_LONG).show();
                setTitle("Password: changed");
            } else {

            }
        }
    }

    @Override
    public void onBindDialogView(View view){

        originalPwd = (EditText)view.findViewById(R.id.originalPassword);
        newPwdFirst = (EditText) view.findViewById(R.id.newPasswordFirst);
        newPwdSecond = (EditText) view.findViewById(R.id.newPasswordSecond);
        super.onBindDialogView(view);
    }


}
