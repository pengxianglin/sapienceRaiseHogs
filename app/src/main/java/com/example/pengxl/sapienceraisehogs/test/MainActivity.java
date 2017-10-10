package com.example.pengxl.sapienceraisehogs.test;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pengxl.sapienceraisehogs.R;
import com.example.pengxl.sapienceraisehogs.base.AppBaseActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends AppBaseActivity<IMainPresenter> implements IMainView {

    private static final String TAG = "MainActivity";

    @Bind(R.id.btn_login)
    Button btLogin;
    @Bind(R.id.login_number)
    EditText loginNumber;
    @Bind(R.id.login_password)
    EditText loginPassword;
    @Bind(R.id.login_checknumber)
    EditText loginChecknumber;

    @OnClick(R.id.btn_send_code)
    public void onSendCode(View v) {
        String userName = loginNumber.getText().toString();
        String password = loginPassword.getText().toString();
        String checkcode = loginChecknumber.getText().toString();
        getPresenter().sendLoginCheckCode(userName, password);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
    }


    @Override
    protected IMainPresenter initPresenter() {
        return new MainPresenter();
    }

}
