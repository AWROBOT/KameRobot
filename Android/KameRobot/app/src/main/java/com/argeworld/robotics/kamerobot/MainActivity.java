package com.argeworld.robotics.kamerobot;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener
{
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int INIT       = 0;
    private static final int WALK       = 1;
    private static final int RUN        = 2;
    private static final int LEFT       = 3;
    private static final int RIGHT      = 4;
    private static final int STOP       = 5;
    private static final int HELLO      = 6;
    private static final int UPDOWN     = 7;
    private static final int DANCE      = 8;
    private static final int PUSHUP     = 9;
    private static final int FRONTBACK  = 10;
    private static final int MOONWALK    = 11;

    private FButton btnWalk;
    private FButton btnRun;
    private FButton btnLeft;
    private FButton btnRight;
    private FButton btnHello;
    private FButton btnUpDown;
    private FButton btnDance;
    private FButton btnPushUp;
    private FButton btnFrontBack;
    private FButton btnMoonWalk;

    private MaterialDialog dialog;

    private boolean bInited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetupButtons();
    }

    public void onStart()
    {
        Log.i(TAG, "onStart");

        super.onStart();

        InitConnection();
    }

    public void InitConnection()
    {
        bInited = false;

        dialog = new MaterialDialog.Builder(this)
                .title("Please Wait")
                .content("Connecting to KameRobot...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .show();

        SendCommand(INIT);
    }

    public void SetupButtons()
    {
        btnWalk = (FButton) findViewById(R.id.walk_button);
        btnWalk.setButtonColor(getResources().getColor(R.color.fbutton_color_orange));
        btnWalk.setOnTouchListener(this);

        btnLeft = (FButton) findViewById(R.id.left_button);
        btnLeft.setButtonColor(getResources().getColor(R.color.fbutton_color_orange));
        btnLeft.setOnTouchListener(this);

        btnRight = (FButton) findViewById(R.id.right_button);
        btnRight.setButtonColor(getResources().getColor(R.color.fbutton_color_orange));
        btnRight.setOnTouchListener(this);

        btnRun = (FButton) findViewById(R.id.run_button);
        btnRun.setButtonColor(getResources().getColor(R.color.fbutton_color_alizarin));
        btnRun.setOnTouchListener(this);

        btnHello = (FButton) findViewById(R.id.hello_button);
        btnHello.setButtonColor(getResources().getColor(R.color.fbutton_color_nephritis));
        btnHello.setOnTouchListener(this);

        btnUpDown = (FButton) findViewById(R.id.updown_button);
        btnUpDown.setButtonColor(getResources().getColor(R.color.fbutton_color_nephritis));
        btnUpDown.setOnTouchListener(this);

        btnDance = (FButton) findViewById(R.id.dance_button);
        btnDance.setButtonColor(getResources().getColor(R.color.fbutton_color_nephritis));
        btnDance.setOnTouchListener(this);

        btnPushUp = (FButton) findViewById(R.id.pushup_button);
        btnPushUp.setButtonColor(getResources().getColor(R.color.fbutton_color_nephritis));
        btnPushUp.setOnTouchListener(this);

        btnFrontBack = (FButton) findViewById(R.id.frontback_button);
        btnFrontBack.setButtonColor(getResources().getColor(R.color.fbutton_color_nephritis));
        btnFrontBack.setOnTouchListener(this);

        btnMoonWalk = (FButton) findViewById(R.id.moonwalk_button);
        btnMoonWalk.setButtonColor(getResources().getColor(R.color.fbutton_color_nephritis));
        btnMoonWalk.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(v.getId() == R.id.walk_button)
            {
                Log.i(TAG,"Walk Button Down");

                btnWalk.onTouch(v, event);

                SendCommand(WALK);
            }
            else if(v.getId() == R.id.run_button)
            {
                Log.i(TAG,"Run Button Down");

                btnRun.onTouch(v, event);

                SendCommand(RUN);
            }
            else if(v.getId() == R.id.left_button)
            {
                Log.i(TAG,"Left Button Down");

                btnLeft.onTouch(v, event);

                SendCommand(LEFT);
            }
            else if(v.getId() == R.id.right_button)
            {
                Log.i(TAG,"Right Button Down");

                btnRight.onTouch(v, event);

                SendCommand(RIGHT);
            }
            else if(v.getId() == R.id.hello_button)
            {
                Log.i(TAG,"Hello Button Down");

                btnHello.onTouch(v, event);

                SendCommand(HELLO);
            }
            else if(v.getId() == R.id.updown_button)
            {
                Log.i(TAG,"UpDown Button Down");

                btnUpDown.onTouch(v, event);

                SendCommand(UPDOWN);
            }
            else if(v.getId() == R.id.dance_button)
            {
                Log.i(TAG,"Dance Button Down");

                btnDance.onTouch(v, event);

                SendCommand(DANCE);
            }
            else if(v.getId() == R.id.pushup_button)
            {
                Log.i(TAG,"PushUp Button Down");

                btnPushUp.onTouch(v, event);

                SendCommand(PUSHUP);
            }
            else if(v.getId() == R.id.frontback_button)
            {
                Log.i(TAG,"FrontBack Button Down");

                btnFrontBack.onTouch(v, event);

                SendCommand(FRONTBACK);
            }
            else if(v.getId() == R.id.moonwalk_button)
            {
                Log.i(TAG,"MoonWalk Button Down");

                btnMoonWalk.onTouch(v, event);

                SendCommand(MOONWALK);
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP)
        {
            if(v.getId() == R.id.walk_button)
            {
                Log.i(TAG,"Walk Button Up");

                btnWalk.onTouch(v, event);

                SendCommand(STOP);
            }
            else if(v.getId() == R.id.run_button)
            {
                Log.i(TAG,"Run Button Up");

                btnRun.onTouch(v, event);

                SendCommand(STOP);
            }
            else if(v.getId() == R.id.left_button)
            {
                Log.i(TAG,"Left Button Up");

                btnLeft.onTouch(v, event);

                SendCommand(STOP);
            }
            else if(v.getId() == R.id.right_button)
            {
                Log.i(TAG,"Right Button Up");

                btnRight.onTouch(v, event);

                SendCommand(STOP);
            }
            else if(v.getId() == R.id.hello_button)
            {
                Log.i(TAG,"Hello Button Up");

                btnHello.onTouch(v, event);
            }
            else if(v.getId() == R.id.updown_button)
            {
                Log.i(TAG,"UpDown Button Up");

                btnUpDown.onTouch(v, event);
            }
            else if(v.getId() == R.id.dance_button)
            {
                Log.i(TAG,"Dance Button Up");

                btnDance.onTouch(v, event);
            }
            else if(v.getId() == R.id.pushup_button)
            {
                Log.i(TAG,"PushUp Button Up");

                btnPushUp.onTouch(v, event);
            }
            else if(v.getId() == R.id.frontback_button)
            {
                Log.i(TAG,"FrontBack Button Up");

                btnFrontBack.onTouch(v, event);
            }
            else if(v.getId() == R.id.moonwalk_button)
            {
                Log.i(TAG,"MoonWalk Button Up");

                btnMoonWalk.onTouch(v, event);
            }
        }

        return true;
    }

    public void SendCommand(int cmd)
    {
        Log.i(TAG,"SendCommand: " + cmd);

        String url = "http://192.168.4.1/commands?data=" + cmd;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response)
            {
                Log.i(TAG, "SendCommand onSuccess: " + new String(response));

                if(!bInited)
                {
                    bInited = true;

                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e)
            {
                Log.e(TAG, "SendCommand onFailure: " + e.getMessage());

                if(!bInited)
                {
                    dialog.dismiss();

                    new MaterialDialog.Builder(MainActivity.this)
                            .title("Connection Error!")
                            .content("Please select 'KameRobot' as WIFI Access Point.")
                            .positiveText("TRY AGAIN")
                            .negativeText("EXIT")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    dialog.dismiss();

                                    InitConnection();
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    System.exit(0);
                                }
                            })
                            .canceledOnTouchOutside(false)
                            .cancelable(false)
                            .show();
                }
                else
                {
                    InitConnection();
                }
            }
        });
    }
}
