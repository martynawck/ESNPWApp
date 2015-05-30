package com.wiacek.martyna.esnpwapp.ViewEnhancements;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.wiacek.martyna.esnpwapp.AsyncTask.ChangePasswordTask;
import com.wiacek.martyna.esnpwapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Martyna on 2015-05-14.
 */
public class PasswordDialog extends DialogPreference {

    @InjectView(R.id.originalPassword)
    EditText originalPwd;
    @InjectView(R.id.newPasswordFirst)
    EditText newPwdFirst;
    @InjectView(R.id.newPasswordSecond)
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
            if (newPwdFirst.getText().toString().equals(newPwdSecond.getText().toString())) {
                ChangePasswordTask task = new ChangePasswordTask(originalPwd.getText().toString(), newPwdSecond.getText().toString(), getContext());
                task.runVolley();
                setTitle("Password: changed");
            }
        }
    }

    @Override
    public void onBindDialogView(View view){
        ButterKnife.inject(this,view);
        super.onBindDialogView(view);
    }


}
