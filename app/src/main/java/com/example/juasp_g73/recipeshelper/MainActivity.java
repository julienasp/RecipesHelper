package com.example.juasp_g73.recipeshelper;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import dao.RecipeDaoTasker;

/**
* MainActivity is the activity that make sure that the NFC chip is activated and let the user scan the recipe
*
* @author  Julien Aspirot
* @version 1.0
* @since   2017-02-04 
*/
public class MainActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter = null;
    private Context ctx;
    private ImageView iv_nfc;
    private TextView tv_nfc_ok;
    private PendingIntent mPendingIntent = null;

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
                        tv_nfc_ok.setBackgroundResource(android.R.color.holo_red_light);
                        tv_nfc_ok.setText(R.string.turnNFCon);
                        break;
                    case NfcAdapter.STATE_TURNING_OFF:
                        break;
                    case NfcAdapter.STATE_ON:
                        Toast.makeText(ctx,"NFC available!", Toast.LENGTH_LONG).show();
                        iv_nfc.setImageResource(R.drawable.nfcgreen300x300);
                        tv_nfc_ok.setVisibility(View.VISIBLE);
                        tv_nfc_ok.setBackgroundResource(android.R.color.holo_green_light);
                        tv_nfc_ok.setText(R.string.canScan);
                        break;
                    case NfcAdapter.STATE_TURNING_ON:
                        break;
                }
            }
        }
    };
    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_nfc = (ImageView) findViewById(R.id.imageView_nfc);
        tv_nfc_ok = (TextView) findViewById(R.id.textView_nfc_ok);
        setSupportActionBar(toolbar);
        ctx = this;

        try {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            if(nfcAdapter != null && nfcAdapter.isEnabled()){
                Toast.makeText(this,"NFC available!", Toast.LENGTH_LONG).show();

                //Prevent out of memory errors
                ((BitmapDrawable)iv_nfc.getDrawable()).getBitmap().recycle();

                iv_nfc.setImageResource(R.drawable.nfcgreen300x300);
                tv_nfc_ok.setVisibility(View.VISIBLE);
            }
            else{
                Toast.makeText(this,"NFC not available!", Toast.LENGTH_LONG).show();

                //Prevent out of memory errors
                ((BitmapDrawable)iv_nfc.getDrawable()).getBitmap().recycle();

                iv_nfc.setImageResource(R.drawable.nfcred300x300);
                tv_nfc_ok.setBackgroundResource(android.R.color.holo_red_light);
                tv_nfc_ok.setText(R.string.turnNFCon);
                tv_nfc_ok.setVisibility(View.VISIBLE);

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
            mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(ctx,
                    getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

            IntentFilter if_nfc_state_changed = new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);
            this.registerReceiver(mReceiver, if_nfc_state_changed);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop(){
        unregisterReceiver(mReceiver);
        super.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent){
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())){
            String nfcTagMsg;
            Tag mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Toast.makeText(this, "NFC Tag detected!", Toast.LENGTH_LONG ).show();
            Ndef ndef = Ndef.get(mytag);
            if (ndef != null) {
                NdefMessage ndefMessage = ndef.getCachedNdefMessage();

                NdefRecord[] records = ndefMessage.getRecords();
                for (NdefRecord ndefRecord : records) {
                    if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                        try {
                            nfcTagMsg = readText(ndefRecord);
                            //Toast.makeText(this, "Recipe ID: " + nfcTagMsg, Toast.LENGTH_SHORT ).show();

                            ProgressDialog progress = new ProgressDialog(this);
                            progress.setMessage("Loading the recipe...");
                            new RecipeDaoTasker(progress,Integer.parseInt(nfcTagMsg),this,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
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

    
   /**
   * Helper method to generate ingredients from a recipe
   *
   * @param record NdefRecord object which contains all the data of the nfc tag
   * @return String it returns a string that contrains the data of a nfc tag
   */
    private String readText(NdefRecord record) throws UnsupportedEncodingException {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

        byte[] payload = record.getPayload();

        // Get the Text Encoding
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

        // Get the Language Code
        int languageCodeLength = payload[0] & 0063;

        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
        // e.g. "en"

        // Get the Text
        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }
}
