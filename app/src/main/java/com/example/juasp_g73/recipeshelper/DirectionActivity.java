package com.example.juasp_g73.recipeshelper;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.squareup.picasso.Picasso;
import core.Direction;
import core.Ingredient;
import core.Recipe;
import core.Tool;
import org.javatuples.Pair;

import java.util.Locale;
import java.util.Vector;

public class DirectionActivity extends AppCompatActivity {
    private Recipe r;
    private int stepIndex = 0;
    private Direction currentDirection;
    private TextView direction_step;
    private TextView direction_description;
    private ImageView direction_image_url;
    private ImageView iv_item_image_url;
    private GridView direction_ingredients;
    private GridView direction_tools;
    private TextView tv_timer;
    private ImageView iv_timer;
    private Button btn_previous;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        direction_step = (TextView) findViewById(R.id.direction_step);
        direction_description = (TextView) findViewById(R.id.direction_description);
        direction_image_url = (ImageView) findViewById(R.id.direction_image_url);
        direction_ingredients = (GridView) findViewById(R.id.direction_ingredients);
        direction_tools = (GridView) findViewById(R.id.direction_tools);
        direction_description.setMovementMethod(new ScrollingMovementMethod());
        iv_item_image_url = (ImageView) findViewById(R.id.iv_item_image_url);
        tv_timer = (TextView) findViewById(R.id.tv_timer);
        iv_timer = (ImageView) findViewById(R.id.iv_timer);
        btn_next = (Button) findViewById(R.id.button_next);
        btn_previous = (Button) findViewById(R.id.button_previous);

        //direction_tools OnItemClickListener
        direction_tools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tool t = (Tool) direction_tools.getItemAtPosition(position);
                if(t.getImage_url() != null) {
                    Picasso.with(getApplicationContext())
                            .load(t.getImage_url())
                            .into(iv_item_image_url);
                }else{
                    Picasso.with(getApplicationContext())
                            .load(R.drawable.phototbd)
                            .resize(50, 50)
                            .centerCrop()
                            .into(iv_item_image_url);
                }
                t.showHint();
                iv_timer.setVisibility(View.VISIBLE);
                tv_timer.setVisibility(View.VISIBLE);
                new CountDownTimer(15000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        tv_timer.setText("seconds remaining: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        tv_timer.setText("");
                        tv_timer.setVisibility(View.INVISIBLE);
                        iv_timer.setVisibility(View.INVISIBLE);
                        iv_item_image_url.setVisibility(View.INVISIBLE);
                    }
                }.start();

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepIndex++;
                hydrateView();
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepIndex--;
                hydrateView();
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             r = (Recipe) extras.getSerializable("recipe");

            Log.i("DirectionActivity","[Recipe#id:"+r.getId()+"] has " + r.getDirections().size() + " directions");
            hydrateView();
        }

    }

    private void hydrateView() {
        if(r != null){

            if(stepIndex > 0){
                btn_previous.setVisibility(View.VISIBLE);
            }else btn_previous.setVisibility(View.INVISIBLE);

            //Hydrating the interface
            try {
                currentDirection = r.getDirections().get(stepIndex);

                if(currentDirection.getImage_url() != null) {
                    Picasso.with(this)
                            .load(currentDirection.getImage_url())
                            .into(direction_image_url);
                }

                //attributes are not null?
                if (  currentDirection.getOrder() != null && currentDirection.getDescription() != null )
                {
                    direction_step.setText(String.format("step #%d", currentDirection.getOrder()).toUpperCase(Locale.getDefault()));
                    direction_description.setText(currentDirection.getDescription());
                }

                //ingredient vector empty?
                if(generateIngredientsFromDirection(currentDirection).size() > 0){
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.test_list_item, generateIngredientsFromDirection(currentDirection));

                    direction_ingredients.setAdapter(adapter);
                }else direction_ingredients.setVisibility(View.INVISIBLE);

                //tool vector empty?
                if(currentDirection.getDirection_tools().size() > 0){
                    ArrayAdapter<Tool> adapter = new ArrayAdapter<>(this,
                            android.R.layout.test_list_item, currentDirection.getDirection_tools());

                    direction_tools.setAdapter(adapter);
                }else direction_tools.setVisibility(View.INVISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Vector<String> generateIngredientsFromDirection(Direction d){
        Vector<String> ingredients = new Vector<>();

        for(Pair<Ingredient,String> p : d.getDirection_ingredients()){
            ingredients.add(p.getValue1() + " of " + p.getValue0().getName() );
        }

        return ingredients;
    }
}
