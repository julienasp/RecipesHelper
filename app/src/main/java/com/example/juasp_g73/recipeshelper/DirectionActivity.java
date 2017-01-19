package com.example.juasp_g73.recipeshelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.javatuples.Pair;

import java.util.Locale;
import java.util.Vector;

import core.AbstractLightLover;
import core.Direction;
import core.Ingredient;
import core.PairIngredientString;
import core.Recipe;
import core.Tool;

public class DirectionActivity extends AppCompatActivity {
    /***************************************************/
    /***********    PRIVATE ATTRIBUTES   ***************/
    /***************************************************/
    private Recipe r;
    private int stepIndex = 0;
    private TextView direction_step;
    private TextView direction_description;
    private TextView label_information;
    private TextView label_items;
    private ImageView direction_image_url;
    private GridView direction_ingredients;
    private GridView direction_tools;
    private Button btn_previous;
    private Button btn_next;
    private Integer nbDirection = 0;
    private TextToSpeech tts;
    private ImageButton ttsButton;
    private Context ctx;
    private PopupWindow mPopupWindow;
    private Boolean popUpOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ctx = this;

        /***************************************************/
        /***********    INTERFACE BINDING   ****************/
        /***************************************************/
        direction_step = (TextView) findViewById(R.id.direction_step);
        direction_description = (TextView) findViewById(R.id.direction_description);
        direction_image_url = (ImageView) findViewById(R.id.direction_image_url);
        direction_ingredients = (GridView) findViewById(R.id.direction_ingredients);
        direction_tools = (GridView) findViewById(R.id.direction_tools);
        direction_description.setMovementMethod(new ScrollingMovementMethod());
        btn_next = (Button) findViewById(R.id.button_next);
        btn_previous = (Button) findViewById(R.id.button_previous);
        label_information = (TextView) findViewById(R.id.label_information);
        label_items = (TextView) findViewById(R.id.label_items);
        ttsButton = (ImageButton) findViewById(R.id.ttsbutton);

        /***************************************************/
        /***************    LISTENERS   ********************/
        /***************************************************/
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.CANADA);
                }
            }
        });

        ttsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tts.isSpeaking()){
                    tts.stop();
                    Picasso.with(getApplicationContext())
                            .load(R.drawable.tts)
                            .into(ttsButton);
                }
                else {
                    tts.speak(direction_description.getText(), TextToSpeech.QUEUE_FLUSH, null, null);
                    Picasso.with(getApplicationContext())
                            .load(R.drawable.notts)
                            .into(ttsButton);
                }
            }
        });

        //direction_tools OnItemClickListener
        direction_tools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!popUpOpen) {
                    Tool tool = (Tool) direction_tools.getItemAtPosition(position);
                    showHint(tool);
                }

            }
        });

        //direction_ingredients OnItemClickListener
        direction_ingredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!popUpOpen) {
                    PairIngredientString p = (PairIngredientString) direction_ingredients.getItemAtPosition(position);
                    showHint(p.getP().getValue0());
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tts !=null){
                    tts.stop();
                }
                if(stepIndex == nbDirection - 1 ){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
                else{
                    direction_description.scrollTo(0,0);
                    stepIndex++;
                    hydrateView();
                }

            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tts !=null){
                    tts.stop();
                }
                direction_description.scrollTo(0,0);
                stepIndex--;
                hydrateView();
            }
        });


        /***************************************************/
        /*********    INITIAL HYDRATION   ****************/
        /***************************************************/

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             r = (Recipe) extras.getSerializable("recipe");
            if (r != null) {
                nbDirection = r.getDirections().size();
            }
            hydrateView();
        }

    }

    private void hydrateView() {
        if(r != null){

            Picasso.with(ctx)
                    .load(R.drawable.tts)
                    .into(ttsButton);

            if(stepIndex == nbDirection - 1){
                btn_next.setText(R.string.exit);
                btn_next.setBackgroundColor(Color.RED);
            }else {
                btn_next.setText(R.string.next);
                btn_next.setBackgroundResource(android.R.color.holo_green_light);
            }

            if(stepIndex > 0){
                btn_previous.setVisibility(View.VISIBLE);
            }else btn_previous.setVisibility(View.INVISIBLE);

            //Hydrating the interface
            try {
                Direction currentDirection = r.getDirections().get(stepIndex);

                //Labels visibility conditions
                if(generateIngredientsFromDirection(currentDirection).size() == 0 &&  currentDirection.getDirection_tools().size() == 0){
                    label_information.setVisibility(View.INVISIBLE);
                    label_items.setVisibility(View.INVISIBLE);
                }else{
                    label_information.setVisibility(View.VISIBLE);
                    label_items.setVisibility(View.VISIBLE);
                }

                if(currentDirection.getImage_url() != null) {
                Picasso.with(ctx)
                        .load(currentDirection.getImage_url())
                        .placeholder( R.drawable.animation_progress)
                        .into(direction_image_url);
                }

                //attributes are not null?
                if (  currentDirection.getOrder() != null && currentDirection.getDescription() != null )
                {
                    direction_step.setText(String.format(Locale.CANADA, "step #%d", currentDirection.getOrder()).toUpperCase(Locale.getDefault()));
                    direction_description.setText(currentDirection.getDescription());
                }

                //ingredient vector empty?
                if(generateIngredientsFromDirection(currentDirection).size() > 0){
                    direction_ingredients.setVisibility(View.VISIBLE);
                    ArrayAdapter<PairIngredientString> adapter = new ArrayAdapter<>(this,
                            android.R.layout.test_list_item, generateIngredientsFromDirection(currentDirection));

                    direction_ingredients.setAdapter(adapter);
                }else direction_ingredients.setVisibility(View.INVISIBLE);

                //tool vector empty?
                if(currentDirection.getDirection_tools().size() > 0){
                    direction_tools.setVisibility(View.VISIBLE);
                    ArrayAdapter<Tool> adapter = new ArrayAdapter<>(this,
                            android.R.layout.test_list_item, currentDirection.getDirection_tools());

                    direction_tools.setAdapter(adapter);
                }else direction_tools.setVisibility(View.INVISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void showHint(final AbstractLightLover ll){
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popup, null);
        ImageView iv = (ImageView) customView.findViewById(R.id.iv_image);
        TextView tv = (TextView) customView.findViewById(R.id.txt_item);

        popUpOpen = true;

        if(ll.getImage_url() != null) {
            Picasso.with(ctx)
                    .load(ll.getImage_url())
                    .resize(500,500)
                    .centerInside()
                    .into(iv);
        }
        tv.setText(ll.getName());
        tv.setTextSize(15);

        new AsyncTask<AbstractLightLover, Void, Void>() {
            protected Void doInBackground(AbstractLightLover... params) {
                // Background Code
                AbstractLightLover t = params[0];
                t.showHint();
                return null;
            }
        }.execute(ll);

        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        mPopupWindow.setElevation(5.0f);
        mPopupWindow.setOutsideTouchable(false);

        Button closeButton = (Button) customView.findViewById(R.id.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<AbstractLightLover, Void, Void>() {
                    protected Void doInBackground(AbstractLightLover... params) {
                        AbstractLightLover t = params[0];
                        t.dismissHint();
                        return null;
                    }
                }.execute(ll);

                popUpOpen = false;
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow.showAtLocation(direction_description, Gravity.CENTER,0,0);

    }

    private Vector<PairIngredientString> generateIngredientsFromDirection(Direction d){
        Vector<PairIngredientString> ingredients = new Vector<>();

        for(Pair<Ingredient,String> p : d.getDirection_ingredients()){
            ingredients.add(new PairIngredientString(p));
        }

        return ingredients;
    }

    public void onPause(){
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
}
