package com.apkprovider.swipeItemListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ListView list;

    public View.OnTouchListener gestureListener;
    public View.OnTouchListener gestureListener2;
    int windowwidth;
    public float x,y=0;
    public boolean isMoving = false;
    int halfWidth;
    float tempW;
    public boolean isNewActivity = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        windowwidth = getWindowManager().getDefaultDisplay().getWidth();

        list = (ListView)findViewById(R.id.list);

        String[] items = new String[]{"Parent Zone","Child Zone","Test Zone"};

        gestureListener = new View.OnTouchListener() {
            private int padding = 0;
            private int initialx = 0;
            private int currentx = 0;
            private ViewHolder viewHolder;
            private boolean isNewActivityOpen = false;

            public boolean onTouch(View v, MotionEvent event) {

                if ( event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    padding = 0;
                    initialx = (int) event.getX();
                    currentx = (int) event.getX();
                    viewHolder = ((ViewHolder) v.getTag());
                }

                else if ( event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    currentx = (int) event.getX();
                    padding = currentx - initialx;
                }


                else if ( event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    padding = 0;
                    initialx = 0;
                    currentx = 0;
                }

                Display display = getWindowManager().getDefaultDisplay();
                int width = display.getWidth();
                 halfWidth = width /2;
                Log.e("Width ",""+width);
                Log.e("Half Width ",""+halfWidth);


                if(viewHolder != null)
                {

                    //Log.e("## PADDING",""+padding);
                    if(padding == 0)
                    {
                        v.setBackgroundColor(0xFF000000 );
                    }
                    if(padding > halfWidth)
                    {
                        viewHolder.setRunning(true);
                        padding =0;
                        isNewActivityOpen = true;

                      //  Toast.makeText(MainActivity.this,"Reached...",Toast.LENGTH_SHORT).show();



                    }
                    if(padding < -75)
                    {
                        viewHolder.setRunning(false);

                    }

                    Log.e("Is ACtivity opne",""+isNewActivityOpen);

                    if(isNewActivityOpen) {
                        Intent i = new Intent(MainActivity.this, Main2Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }


                    v.setBackgroundColor(viewHolder.getColor());
                    v.setPadding(padding, 0, 0, 0);
                }

                return true;
            }
        };



        gestureListener2 = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                return true;
            }
        };



        list.setAdapter(new ModelArrayAdapter(this, getData(),gestureListener));

    }


    public class Model {
        private String name;
        private boolean selected;
        private Boolean running;
        public Model(String name) {
            this.name = name;
            selected = false;
            running = false;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public boolean isSelected() {
            return selected;
        }
        public void setSelected(boolean selected) {
            this.selected = selected;
        }
        public boolean isRunning() {
            return running;
        }
        public void setRuning(boolean running) {
            this.running = running;
        }
    }

    public ArrayList<Model> getData()
    {
        ArrayList<Model> models = new ArrayList<Model>();
        for(int a=0;a<5;a++)
        {
            Model m = new Model(String.format("Item %d", a));
            models.add(m);
        }
        return models;
    }



    public class ModelArrayAdapter extends ArrayAdapter<MainActivity.Model>
    {
        public View rowView = null;

        private ArrayList<MainActivity.Model> allModelItemsArray;
        private Activity context;
        private LayoutInflater inflator;
        private View.OnTouchListener listener;

        public ModelArrayAdapter(Activity context, ArrayList<MainActivity.Model> list,View.OnTouchListener _listener) {
            super(context, R.layout.row_item, list);
            this.listener = _listener;
            this.context = context;
            this.allModelItemsArray = new ArrayList<MainActivity.Model>();

            this.allModelItemsArray.addAll(list);
            inflator = context.getLayoutInflater();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(position > allModelItemsArray.size())
                return null;
            MainActivity.Model m = allModelItemsArray.get(position);

                rowView = inflator.inflate(R.layout.row_item, null);

               final TextView text = (TextView) rowView.findViewById(R.id.label);
             //   viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
                ImageView icon = (ImageView) rowView.findViewById(R.id.icon);



            //if(this.listener != null)
              //  Holder.icon.setOnTouchListener(this.listener);

            icon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    int xPoistion =0;
                    switch (motionEvent.getAction()){


                        case MotionEvent.ACTION_CANCEL:
                            xPoistion = 0;
                            break;
                       /*
                        case MotionEvent.ACTION_UP:
                            xPoistion =0;

                            break;*/


                        case MotionEvent.ACTION_DOWN:
                            isMoving = true;

                            break;
                        case MotionEvent.ACTION_MOVE:

                            if(isMoving){

                                isNewActivity = true;

                                x = motionEvent.getRawX()-view.getWidth()/2;

                                tempW = x;

                                if(x>=(windowwidth/2)){
                                    xPoistion = 0;

                                }else if(x<0){
                                    xPoistion = 0;
                                }
                                else {

                                    xPoistion = (int)x;
                                    view.bringToFront();

                                }

                            }


                            break;
                        default:
                            break;
                    }


                   // Log.e("# text width",""+text.getWidth());
                    view.setX(xPoistion);
                 //   text.getLayoutParams().width = text.getWidth()- (int) tempW;
                  //  text.requestLayout();



                    if(xPoistion>=halfWidth+200){

                       Intent i = new Intent(MainActivity.this,Main2Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }

                    if(xPoistion!=0){
                        if(xPoistion % 10 == 0) {
                            Random myColor = new Random();
                            text.setTextColor(Color.rgb(myColor.nextInt(255), myColor.nextInt(255), myColor.nextInt(255)));
                            view.bringToFront();

                        }
                    }else{
                        int orgCol = Color.parseColor("#494949");
                        text.setTextColor(orgCol);
                    }

                    return true;
                }
            });

             text.setText(m.getName());




            return rowView;
        }



    }



    //end of main class
}
