package com.example.juasp_g73.recipeshelper;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.*;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.*;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.nfc.tech.Ndef;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String NFC_TOKEN_MIME_TYPE = "application/vnd.wfa.wsc";
    private NfcAdapter nfcAdapter = null;
    private Tag mytag;
    private Context ctx;
    ImageView iv_nfc;
    private PendingIntent mPendingIntent = null;
    private IntentFilter writeTagFilters[];

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED)) {
                final int state = intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE, NfcAdapter.STATE_OFF);
                switch (state) {
                    case NfcAdapter.STATE_OFF:
                        Toast.makeText(ctx,"NFC not available!", Toast.LENGTH_LONG).show();
                        iv_nfc.setImageResource(R.drawable.nfcred300x300);
                        break;
                    case NfcAdapter.STATE_TURNING_OFF:
                        break;
                    case NfcAdapter.STATE_ON:
                        Toast.makeText(ctx,"NFC available!", Toast.LENGTH_LONG).show();
                        iv_nfc.setImageResource(R.drawable.nfcgreen300x300);
                        break;
                    case NfcAdapter.STATE_TURNING_ON:
                        break;
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_nfc = (ImageView) findViewById(R.id.imageView_nfc);
        setSupportActionBar(toolbar);
        ctx = this;

        try {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            if(nfcAdapter != null && nfcAdapter.isEnabled()){
                Toast.makeText(this,"NFC available!", Toast.LENGTH_LONG).show();

                //Prevent out of memory errors
                ((BitmapDrawable)iv_nfc.getDrawable()).getBitmap().recycle();

                iv_nfc.setImageResource(R.drawable.nfcgreen300x300);
            }
            else{
                Toast.makeText(this,"NFC not available!", Toast.LENGTH_LONG).show();

                //Prevent out of memory errors
                ((BitmapDrawable)iv_nfc.getDrawable()).getBitmap().recycle();

                iv_nfc.setImageResource(R.drawable.nfcred300x300);

                //AlertDialog to turn on NFC
                AlertDialog.Builder alertbox = new AlertDialog.Builder(ctx);
                alertbox.setTitle("NFC disabled");
                alertbox.setMessage("You must turn on your phone's NFC functionnality to use this app.");
                alertbox.setPositiveButton("Turn On", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(intent);
                        }
                    }
                });
                alertbox.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertbox.show();
            }
            mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                    getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

            IntentFilter if_nfc_state_changed = new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);
            this.registerReceiver(mReceiver, if_nfc_state_changed);

            IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

            tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
            writeTagFilters = new IntentFilter[] { tagDetected };
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onNewIntent(Intent intent){
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Toast.makeText(this, "new intent: " + mytag.toString(), Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
