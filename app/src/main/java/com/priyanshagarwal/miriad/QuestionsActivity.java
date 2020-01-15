package com.priyanshagarwal.miriad;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.priyanshagarwal.miriad.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

 class ThemeCardAdapter extends RecyclerView.Adapter<ThemeCardHolder>{
    List<ThemeData> themeData;
    String imageString;
    byte[] imageBytes;
    QuestionsActivity q;
     LinearLayout gameOverLayout;ImageView imageView;CardView imageCard;TextView question;TextView answer1;TextView answer2, answer3,answer4;CardView cardView1,cardView2,cardView3,cardView4;
    NestedScrollView themeGrid;
//    LinearLayout questionLayout,loadingLayout;
LinearLayout loadingLayout;
//RelativeLayout timeLayout;
RelativeLayout questionLayout;
    //    Context context;
    public ThemeCardAdapter(List<ThemeData> themeData,QuestionsActivity q,NestedScrollView themeGrid,RelativeLayout questionLayout,LinearLayout loadingLayout,LinearLayout gameOverLayout,ImageView imageView,CardView imageCard,TextView question,TextView answer1,TextView answer2,TextView answer3,TextView answer4,CardView cardView1,CardView cardView2,CardView cardView3,CardView cardView4)
    {
        this.themeData=themeData;
        this.q=q;
        this.questionLayout=questionLayout;
        this.themeGrid=themeGrid;
        this.loadingLayout=loadingLayout;
        this.gameOverLayout=gameOverLayout;
        this.imageView=imageView;
        this.imageCard=imageCard;this.question=question;this.answer1=answer1;this.answer2=answer2;this.answer3=answer3;this.answer4=answer4;this.cardView1=cardView1;this.cardView2=cardView2;this.cardView3=cardView3;this.cardView4=cardView4;
//this.context=context;
    }
    void setMusicPlayer(MediaPlayer mp, String data)
    {
        try{
            final byte[] xbytes;
            xbytes=Base64.decode(data,Base64.DEFAULT);
            mp.setDataSource(new MediaDataSource() {
                @Override
                public long getSize() {
                    return xbytes.length;
                }

                @Override
                public int readAt(long position, byte[] buffer, int offset, int size) {
                    long length = getSize();
                    if (position >= length) return -1; // EOF
                    if (position + size > length) // requested more than available
                        size = (int)(length - position); // set size to maximum size possible
                    // at given position

                    System.arraycopy(xbytes, (int) position, buffer, offset, size);
                    return size;
                }

                @Override
                public synchronized void close() throws IOException {

                }
            });
            mp.prepare();

        }
        catch (Exception ex)
        {
            Log.d("music error",ex.toString());
        }
    }
     @Override
     public int getItemViewType(int position) {
         return position % 3;
     }
    @Override
    public ThemeCardHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType)
    {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //
        int layoutId = R.layout.mrd_staggered_product_card_first;
        if (viewType == 1) {
            layoutId = R.layout.mrd_staggered_product_card_second;
        } else if (viewType == 2) {
            layoutId = R.layout.mrd_staggered_product_card_third;
        }
        //
//        View photoView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        final View photoView = inflater.inflate(layoutId, parent, false);
        final ThemeCardHolder viewHolder = new ThemeCardHolder(photoView);

        final LinearLayout themeCard=photoView.findViewById(R.id.themeCard);
        themeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    new AlertDialog.Builder(context).setTitle("Start Game")
                            .setMessage(viewHolder.themeName.getText())
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    q.setTheme(viewHolder.themeName.getText());
                                    q.exitSound=new MediaPlayer();
                                    setMusicPlayer(q.exitSound,viewHolder.exitSound);
                                    q.correctSound=new MediaPlayer();
                                    q.wrongSound=new MediaPlayer();
                                    q.stuckSound=new MediaPlayer();
                                    setMusicPlayer(q.correctSound,viewHolder.correctSound);
                                    setMusicPlayer(q.wrongSound,viewHolder.wrongSound);
                                    setMusicPlayer(q.stuckSound,viewHolder.stuckSound);
                                    q.positiveMessage=viewHolder.positiveMessage;
                                    q.negativeMessage=viewHolder.negativeMessage;
                                    q.exitMessage=viewHolder.exitMessage;
                                    q.startCountDownTimer(themeGrid,questionLayout,loadingLayout,gameOverLayout,imageView,imageCard,question,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);

                                }
                            }).setNegativeButton("No", null).show();
                } catch (Exception e) {

                }

            }
        });

        return viewHolder;
    }
    @Override
    public void
    onBindViewHolder(final ThemeCardHolder viewHolder,
                     final int position)
    {

//        viewHolder.correctSound.setDataSource( Uri.parse(themeData.get(position).getCorrectSound()));
        viewHolder.positiveMessage=themeData.get(position).getPositiveMessage();
        viewHolder.negativeMessage=themeData.get(position).getNegativeMessage();
//        mediaPlayer.prepare();
//        mediaPlayer.start();
        viewHolder.correctSound=themeData.get(position).getCorrectSound();
        viewHolder.wrongSound=themeData.get(position).getWrongSound();
        viewHolder.exitSound=themeData.get(position).getExitSound();
        viewHolder.stuckSound=themeData.get(position).getStuckSound();
        //sound set
        viewHolder.exitMessage=themeData.get(position).getExitMessage();
        viewHolder.themeName.setText(themeData.get(position).getThemeName());
        try {
            imageString="";
            imageString=themeData.get(position).getImageURI();
            imageBytes= Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            viewHolder.themeImage.setImageBitmap(decodedImage);

        } catch (Exception ex) {
        Log.d("Error1:",Integer.toString(position)+imageString);
        }
    }

    @Override
    public int getItemCount()
    {
        return themeData.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }
}








public class QuestionsActivity extends AppCompatActivity {

    List<Questions> questionsList= Collections.emptyList();
    CountDownTimer1 countDownTimer;
//    TextView answer1,answer2,answer3,answer4,question;
 int i=0;
    int timer_flag=1;
    int delay=500,delay1=delay;
    int total_time=10000;
    int received=0;
    int cardNumber=0;
    String correctAnswer="";
//    int exitSound=0,correctSound=0,wrongSound=0;
    byte[] imageBytes;
    String theme="ARROW";
    long extra=0,extra1=0;
    List<ThemeData> themeDataList;
   TextView displayBestScore,questionNumber,questionTimerLayout;
   int skipPenalty=0,wrongPenalty=4,correctPoint=10,timePenalty=1;
    int counter=total_time/1000+1;
    int counter1=21;
    int questionTimer=20;
    TextView displayScore,displayTime;
    int scoreKeeper=0;
    String imageString;
    String exitMessage="Are you sure you want to leave the game without completing?";
    String message="Are you sure you want to exit?";
    String positiveMessage="Yes",negativeMessage="No";
    String positiveMessage1,negativeMessage1;
    int questionnumber=1;
    int messageNumber=0;
    LinearLayout gameOverLayout1,loadingLayout1;
    RelativeLayout questionLayout1;
    RelativeLayout timeLayout1;
    TextView countTime;
    NestedScrollView themeGrid1;
    Button playAgain1;
    TextView userNameTextView;
    MediaPlayer exitSound =new MediaPlayer(),stuckSound=new MediaPlayer(),correctSound=new MediaPlayer(), wrongSound=new MediaPlayer();
    User user;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            FirebaseAuth mAuth=FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            Toast.makeText(QuestionsActivity.this, "Logged Out", Toast.LENGTH_LONG).show();
            return true;
        }
        if(id==R.id.accountSettigs)
        {
            Intent intent=new Intent(QuestionsActivity.this,AccountSettings.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("User Data", user);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
//    public void onCreateOptionsMenu(Menu menu) {
//        menuInflater.inflate(R.menu.mrd_toolbar_menu, menu);
//        super.onCreateOptionsMenu(menu, );
//    }

    void getData()
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        //        User user=databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
//        databaseReference.child("Users").child(id).child("username").setValue("user@priyansh");
//        databaseReference.child("Users").addListenerForSingleValueEvent();
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<String> a=Collections.emptyList();
               User temp;
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    temp = ds.getValue(User.class);
                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(ds.getKey()))
                    {
                       user=ds.getValue(User.class);
                       userNameTextView.setText("Hi "+user.getFirstName());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds: dataSnapshot.getChildren())
//                {
//                    Log.d("name",ds.child(id).getValue(User.class).getFirstName());
//
//                    if(ds.getKey().equals(id))
//                    {
//                        Log.d("anything","any");
//                        User user=new User(ds.child(id).getValue(User.class).getFirstName(),ds.child(id).getValue(User.class).getLastName(),ds.child(id).getValue(User.class).getBest(),ds.child(id).getValue(User.class).getUsername(),ds.child(id).getValue(User.class).getPassword(),ds.child(id).getValue(User.class).getEmail(),ds.child(id).getValue(User.class).getPhoneNo());
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {

//       final  MediaPlayer mp=MediaPlayer.create(getApplicationContext(),exitSound);

        try {
            if(questionLayout1.getVisibility()==View.VISIBLE)
            {
                //TODO: play dino sound
                message=exitMessage;
                positiveMessage1=positiveMessage;
                negativeMessage1=negativeMessage;
                //sound code
                if(exitSound!=null)
                exitSound.start();
                messageNumber=1;
            }
            else if(loadingLayout1.getVisibility()==View.VISIBLE)
            {
                message="Are you sure you want to exit?";
                positiveMessage1="Yes";
                negativeMessage1="No";
                messageNumber=0;
            }
            else if(gameOverLayout1.getVisibility()==View.VISIBLE)
            {
                messageNumber=1;
                playAgain1.setSoundEffectsEnabled(false);
                playAgain1.performClick();
                return;
            }
            else if(themeGrid1.getVisibility()==View.VISIBLE)
            {
                messageNumber=0;
                positiveMessage1="Yes";
                negativeMessage1="No";
                message="Are you sure you want to exit?";
            }
            else
            {
                message="Are you sure you want to exit?";
                if(timeLayout1.getVisibility()==View.VISIBLE)
                {

                    messageNumber=1;
                }
                else
                    messageNumber=0;
                positiveMessage1="Yes";
                negativeMessage1="No";
            }
            new AlertDialog.Builder(this).setTitle("Exit")
                    .setMessage(message)
                    .setPositiveButton(positiveMessage1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(messageNumber==0)
                            {
                                finish();
                            }
                            else
                            {
                                playAgain1.setSoundEffectsEnabled(false);
                                playAgain1.performClick();
                            }
                            if(exitSound.isPlaying()&&exitSound!=null)
                                exitSound.stop();
                            exitMessage="Are you sure you want to leave the game without completing?";
                            positiveMessage1="Yes";
                            negativeMessage1="No";
                        }
                    }).setNegativeButton(negativeMessage1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(exitSound.isPlaying()&&exitSound!=null)
                        exitSound.stop();
                }
            }).show();
        } catch (Exception e) {
            super.onBackPressed();
        }
    }
    void startCountDownTimer(NestedScrollView themeGrid,RelativeLayout questionLayout,LinearLayout loadingLayout,LinearLayout gameOverLayout,ImageView imageView,CardView imageCard,TextView question,TextView answer1,TextView answer2,TextView answer3,TextView answer4,CardView cardView1,CardView cardView2,CardView cardView3,CardView cardView4)
    {
        themeGrid.setVisibility(View.GONE);
//setcard
//        setCards();

        if (received==0)
        {
            loadingLayout.setVisibility(View.VISIBLE);
            received=3;

        }
        else if(received==1)
        {
            timeLayout1.setVisibility(View.VISIBLE);
            received=2;
            setCards(questionLayout,gameOverLayout,imageView,imageCard,question,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);


        }


    }

    void clickableFalse(CardView cardView1,CardView cardView2,CardView cardView3,CardView cardView4)
    {
        cardView1.setClickable(false);
        cardView2.setClickable(false);
        cardView3.setClickable(false);
        cardView4.setClickable(false);

    }
    void clickableTrue(CardView cardView1,CardView cardView2,CardView cardView3,CardView cardView4)
    {
        cardView1.setClickable(true);
        cardView2.setClickable(true);
        cardView3.setClickable(true);
        cardView4.setClickable(true);

    }
    void setCards(RelativeLayout questionLayout,LinearLayout gameOverLayout,ImageView imageView,CardView imageCard,TextView question,TextView answer1,TextView answer2,TextView answer3,TextView answer4,CardView cardView1,CardView cardView2,CardView cardView3,CardView cardView4)
    {
        extra1=0;
        if(counter1==1)
        counter1=questionTimer;
        else
            counter1=questionTimer+1;
        questionTimerLayout.setText(Integer.toString(counter1));
        while(i<questionsList.size()&&!questionsList.get(i).getThemeName().equals(theme))
            i++;

        if(i<questionsList.size()) {
            questionNumber.setText("Ques"+Integer.toString(questionnumber)+" ");
            questionnumber++;
            question.setText(questionsList.get(i).getQuestion());
            answer1.setText(questionsList.get(i).getAnswer()[0]);
            answer2.setText(questionsList.get(i).getAnswer()[1]);
            answer3.setText(questionsList.get(i).getAnswer()[2]);
            answer4.setText(questionsList.get(i).getAnswer()[3]);
            cardNumber = questionsList.get(i).getNumber();
            correctAnswer=questionsList.get(i).getCorrectAnswer();
            answer1.setTextColor(getColor(R.color.Primary_Color1));
            answer2.setTextColor(getColor(R.color.Primary_Color1));
            answer3.setTextColor(getColor(R.color.Primary_Color1));
            answer4.setTextColor(getColor(R.color.Primary_Color1));
            answer1.setBackgroundColor(Color.WHITE);
            answer2.setBackgroundColor(Color.WHITE);
            answer3.setBackgroundColor(Color.WHITE);
            answer4.setBackgroundColor(Color.WHITE);

            try {
                imageString = questionsList.get(i).getImageUrl();
                if (!imageString.equals(""))
                { imageCard.setVisibility(View.VISIBLE);
                imageCard.setCardElevation(5);
                    imageCard.setElevation(5);
                    imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    imageView.setImageBitmap(decodedImage);
                }
                else
                {
                    imageCard.setVisibility(View.GONE);
                    imageCard.setCardElevation(0);
                    imageCard.setElevation(0);
                }



            } catch (IllegalArgumentException e) {
                imageCard.setVisibility(View.GONE);
                imageCard.setCardElevation(0);
                imageCard.setElevation(0);
            }

        }
        else
        {
            questionLayout.setVisibility(View.GONE);
            exitSound.release();
            wrongSound.release();
            correctSound.release();
            stuckSound.release();
            gameOverLayout.setVisibility(View.VISIBLE);
            if(scoreKeeper>0)
            scoreKeeper+=counter/timePenalty;
            displayScore.setText("Score : "+Integer.toString(scoreKeeper));
            displayTime.setText("Time Taken : "+Integer.toString((total_time/1000-counter)/60)+":"+Integer.toString((total_time/1000-counter)%60));
            displayBestScore.setText(Long.toString(extra));
        }

    }
    void setGreen(int number,TextView answer1,TextView answer2,TextView answer3,TextView answer4,CardView cardView1,CardView cardView2,CardView cardView3,CardView cardView4)
    {
        if (number == 0)
            return;
        if(number==1)
        {
            answer1.setBackgroundColor(Color.GREEN);
            answer1.setTextColor(Color.WHITE);
        }
        else if(number==2)
        {
            answer2.setBackgroundColor(Color.GREEN);
            answer2.setTextColor(Color.WHITE);
        }
        else if(number==3)
        {
            answer3.setBackgroundColor(Color.GREEN);
            answer3.setTextColor(Color.WHITE);
        }
        else if(number==4)
        {
            answer4.setBackgroundColor(Color.GREEN);
            answer4.setTextColor(Color.WHITE);
        }
    }

    int setCardNumber(TextView answer1,TextView answer2,TextView answer3,TextView answer4,String correctAnswer1)
    {
        if(correctAnswer1.equals(answer1.getText()))
            return 1;
        else if(correctAnswer1.equals(answer2.getText()))
            return 2;
       else if(correctAnswer1.equals(answer3.getText()))
            return 3;
       else if(correctAnswer1.equals(answer4.getText()))
            return 4;
       else
           return 0;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        getData();
        Toolbar toolbar=findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        final TextView answer1=findViewById(R.id.answer1);
        final TextView answer2=findViewById(R.id.answer2);
        final TextView answer3=findViewById(R.id.answer3);
        final TextView counttime= findViewById(R.id.timer);
        userNameTextView=findViewById(R.id.userNameTextView);
        userNameTextView.setVisibility(View.GONE);
        countTime=counttime;
        final TextView answer4=findViewById(R.id.answer4);
        final TextView question=findViewById(R.id.question);
        final ImageView imageView=findViewById(R.id.image);

        final LinearLayout loadingLayout=findViewById(R.id.loadingLayout);
        loadingLayout1=loadingLayout;
        final LinearLayout gameOverLayout=findViewById(R.id.gameOverLayout);
        gameOverLayout1=gameOverLayout;
        final CardView cardView1=findViewById(R.id.answer1Card);
        final CardView cardView2=findViewById(R.id.answer2Card);
        final CardView cardView3=findViewById(R.id.answer3Card);
        final CardView cardView4=findViewById(R.id.answer4Card);
        final CardView imageCard=findViewById(R.id.imageCard);
        questionNumber=findViewById(R.id.questionNumber);
        questionTimerLayout=findViewById(R.id.questionTimerTextView);
        final TextView score=findViewById(R.id.score);
        final NestedScrollView themeGrid=findViewById(R.id.themeGrid);
        themeGrid1=themeGrid;
        final RelativeLayout questionLayout=findViewById(R.id.questionLayout);
        final RelativeLayout timeLayout=findViewById(R.id.timeLayout);
        timeLayout1=timeLayout;
        questionLayout1=questionLayout;
        final TextView bestScore=findViewById(R.id.bestScore);
        Button five=findViewById(R.id.five);
        Button ten=findViewById(R.id.ten);
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total_time=300000;
                counter=total_time/1000+1;
                timeLayout.setVisibility(View.GONE);
                questionLayout.setVisibility(View.VISIBLE);
                counter1=questionTimer+1;
                countDownTimer=countDownTimer2( questionLayout,gameOverLayout,imageView,imageCard,question,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);
                countDownTimer.start();
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total_time=600000;
                counter=total_time/1000+1;
                timeLayout.setVisibility(View.GONE);
                questionLayout.setVisibility(View.VISIBLE);
                counter1=questionTimer+1;
                countDownTimer=countDownTimer2( questionLayout,gameOverLayout,imageView,imageCard,question,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);
                countDownTimer.start();
//
            }
        });

        ImageView gifImage=findViewById(R.id.gifImage);
        loadGif(gifImage,R.drawable.loadingone);


        displayScore=findViewById(R.id.displayScore);
        displayTime=findViewById(R.id.timeTaken);
        displayBestScore=findViewById(R.id.displayBestScore);
        Retrofit retrofit= new Retrofit.Builder().baseUrl("https://gist.githubusercontent.com/priyanshagarwal18/").addConverterFactory(GsonConverterFactory.create()).build();

        QuestionsApi questionsApi=retrofit.create(QuestionsApi.class);
        Call<List<Questions>> call=questionsApi.getQuestions();
        Retrofit retrofit1= new Retrofit.Builder().baseUrl("https://gist.githubusercontent.com/priyanshagarwal18/").addConverterFactory(GsonConverterFactory.create()).build();

        ThemeApi themeApi=retrofit1.create(ThemeApi.class);
        Call<List<ThemeData>> callTheme=themeApi.getThemeData();
        callTheme.enqueue(new Callback<List<ThemeData>>() {
            @Override
            public void onResponse(Call<List<ThemeData>> call, Response<List<ThemeData>> response) {
                if(!response.isSuccessful()){
                    Log.d("Error Code : ",Integer.toString(response.code()));
                    return;
                }
                themeDataList=response.body();
//                try {
//                    Log.d("imageuri:", themeDataList.get(0).getImageURI());
//                }
//                catch (Exception e)
//                {
//                    Log.d("Exception1:",e.toString());
//                }
                loadingLayout.setVisibility(View.GONE);
                themeGrid.setVisibility(View.VISIBLE);

                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.HORIZONTAL, false);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return position % 3 == 2 ? 2 : 1;
                    }
                });
                recyclerView.setLayoutManager(gridLayoutManager);
                ThemeCardAdapter adapter=new ThemeCardAdapter(themeDataList,QuestionsActivity.this,themeGrid,questionLayout,loadingLayout,gameOverLayout, imageView, imageCard,question, answer1, answer2, answer3, answer4, cardView1, cardView2, cardView3, cardView4);
                recyclerView.setAdapter(adapter);
                int largePadding = 30;
                int smallPadding = 26;
                recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
            }

            @Override
            public void onFailure(Call<List<ThemeData>> call, Throwable t) {
                Log.d("Error getting question:",t.getMessage());
            }
        });

//        final Context context =getApplicationContext();
//        LayoutInflater inflater = LayoutInflater.from(context);

//        View view= inflater.inflate(R.layout.mrdr_product_card, container, false);
//        setUpToolbar(view);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            view.findViewById(R.id.product_grid).setBackgroundResource(R.drawable.shr_product_grid_background_shape);
//        }
//        return view;

//        theme=getIntent().getStringExtra("Theme Name");
        final Button skip=findViewById(R.id.skip);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip.setClickable(false);
                clickableFalse(cardView1,cardView2,cardView3,cardView4);

                try{
                    if(stuckSound.isPlaying())
                        stuckSound.stop();}
                catch (IllegalStateException e)
                {

                }
                timer_flag=0;
                countDownTimer.cancel();
//                if(cardNumber==1)
                cardNumber=setCardNumber(answer1,answer2,answer3,answer4,correctAnswer);

                if(correctAnswer.equals(answer1.getText()))
                {
                    scoreKeeper+=correctPoint;
                    score.setText(Integer.toString(scoreKeeper));
                    correctSound.start();
                    delay1=(correctSound.getDuration()>delay)?correctSound.getDuration():delay;
//                    displayScore.setText(Integer.toString(scoreKeeper));
                    answer1.setBackgroundColor(Color.GREEN);
                    answer1.setTextColor(Color.WHITE);

                }
                else
                {
                    scoreKeeper-=wrongPenalty;
                    wrongSound.start();
                    delay1=(wrongSound.getDuration()>delay)?wrongSound.getDuration():delay;
                    score.setText(Integer.toString(scoreKeeper));
                    answer1.setBackgroundColor(Color.RED);
                    answer1.setTextColor(Color.WHITE);
                    setGreen(cardNumber,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);
                }

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //do something

                        i++;
                        setCards(questionLayout,gameOverLayout,imageView,imageCard,question,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);

                        timer_flag=1;
                        countDownTimer.start(extra);
                        clickableTrue(cardView1,cardView2,cardView3,cardView4);
                        skip.setClickable(true);
                    }
                }, delay1 );
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip.setClickable(false);
                clickableFalse(cardView1,cardView2,cardView3,cardView4);

                timer_flag=0;
                try{
                    if(stuckSound.isPlaying())
                        stuckSound.stop();}
                catch (IllegalStateException e)
                {

                }
//                if(cardNumber==2)
                cardNumber=setCardNumber(answer1,answer2,answer3,answer4,correctAnswer);

                if(correctAnswer.equals(answer2.getText())){
                    scoreKeeper+=correctPoint;
                    score.setText(Integer.toString(scoreKeeper));
                    answer2.setBackgroundColor(Color.GREEN);
                    answer2.setTextColor(Color.WHITE);
                    correctSound.start();
                    delay1=(correctSound.getDuration()>delay)?correctSound.getDuration():delay;


                }
                else
                {   wrongSound.start();
                    scoreKeeper-=wrongPenalty;
                    score.setText(Integer.toString(scoreKeeper));
                    answer2.setBackgroundColor(Color.RED);
                    answer2.setTextColor(Color.WHITE);
                    delay1=(wrongSound.getDuration()>delay)?wrongSound.getDuration():delay;
                    setGreen(cardNumber,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);
                }


//                cardView2.setElevation(2);
//                cardView2.setCardElevation(4);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //do something
                        i++;
                        setCards(questionLayout,gameOverLayout,imageView,imageCard,question,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);

                        timer_flag=1;
                        countDownTimer.start(extra);
                        clickableTrue(cardView1,cardView2,cardView3,cardView4);
                        skip.setClickable(true);
                    }
                }, delay1);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip.setClickable(false);
                clickableFalse(cardView1,cardView2,cardView3,cardView4);

                timer_flag=0;
                try{
                if(stuckSound.isPlaying())
                    stuckSound.stop();}
                catch (IllegalStateException e)
                {

                }
                countDownTimer.cancel();
//                if(cardNumber==3)
                cardNumber=setCardNumber(answer1,answer2,answer3,answer4,correctAnswer);
                if((correctAnswer).equals(answer3.getText())){
                    scoreKeeper+=correctPoint;
                    score.setText(Integer.toString(scoreKeeper));
                    answer3.setTextColor(Color.WHITE);
                    answer3.setBackgroundColor(Color.GREEN);
                    correctSound.start();
                    delay1=(correctSound.getDuration()>delay)?correctSound.getDuration():delay;                }
                else
                {
                    wrongSound.start();
                    scoreKeeper-=wrongPenalty;
                    delay1=(wrongSound.getDuration()>delay)?wrongSound.getDuration():delay;
                    score.setText(Integer.toString(scoreKeeper));
                    answer3.setBackgroundColor(Color.RED);
                    answer3.setTextColor(Color.WHITE);
                    setGreen(cardNumber,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);
                }


//                cardView3.setElevation(2);
//                cardView3.setCardElevation(4);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //do something
                        i++;
                        setCards(questionLayout,gameOverLayout,imageView,imageCard,question,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);

                        timer_flag=1;
                        countDownTimer.start(extra);
                        clickableTrue(cardView1,cardView2,cardView3,cardView4);
                        skip.setClickable(true);
                    }
                }, delay1 );
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip.setClickable(false);
                clickableFalse(cardView1,cardView2,cardView3,cardView4);


                timer_flag=0;
//                try{
                countDownTimer.cancel();
                try{
                    if(stuckSound.isPlaying())
                        stuckSound.stop();}
                catch (IllegalStateException e)
                {

                }
//                catch (InterruptedException e)
//                {
//
//                }
//                if(cardNumber==4)
                cardNumber=setCardNumber(answer1,answer2,answer3,answer4,correctAnswer);

                if(correctAnswer.equals(answer4.getText())){
                    scoreKeeper+=correctPoint;
                    score.setText(Integer.toString(scoreKeeper));
                    answer4.setTextColor(Color.WHITE);

                    answer4.setBackgroundColor(Color.GREEN);
                    correctSound.start();
                    delay1=(correctSound.getDuration()>delay)?correctSound.getDuration():delay;
                }
                else
                {
                    scoreKeeper-=wrongPenalty;
                    score.setText(Integer.toString(scoreKeeper));
                    answer4.setBackgroundColor(Color.RED);
                    answer4.setTextColor(Color.WHITE);
                    wrongSound.start();
                    delay1=(wrongSound.getDuration()>delay)?wrongSound.getDuration():delay;
                    setGreen(cardNumber,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);
                }
//                clickableFalse(cardView1,cardView2,cardView3,cardView4);
                //skip.setClickable(false);

//                cardView4.setElevation(2);
//                cardView4.setCardElevation(4);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //do something
                        i++;
                        setCards(questionLayout,gameOverLayout,imageView,imageCard,question,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);


                        timer_flag=1;
                        countDownTimer.start(extra);
                        clickableTrue(cardView1,cardView2,cardView3,cardView4);
                        skip.setClickable(true);
                    }
                }, delay1);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wrongSound.isPlaying()||correctSound.isPlaying())
                {
                    wrongSound.stop();
                    correctSound.stop();

                }
                else {


                    scoreKeeper -= skipPenalty;
                    score.setText(Integer.toString(scoreKeeper));
                    i++;
                    setCards(questionLayout, gameOverLayout, imageView, imageCard, question, answer1, answer2, answer3, answer4, cardView1, cardView2, cardView3, cardView4);
                }
            }
        });
        final Button playAgain=findViewById(R.id.playAgain);
        playAgain1=playAgain;
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extra=0;
                received=1;
                scoreKeeper=0;
                questionnumber=1;
                score.setText("0");
                i=0;
                Collections.shuffle(questionsList);
                for(int k=0;k<questionsList.size();k++)
                    shuffleArray(questionsList.get(k).getAnswer());
                countDownTimer.cancel();
                counter=total_time/1000+1;
                gameOverLayout.setVisibility(View.GONE);
                themeGrid.setVisibility(View.VISIBLE);
                questionLayout.setVisibility(View.GONE);
                timeLayout.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.GONE);
                playAgain.setSoundEffectsEnabled(true);

            }
        });
        //background thread
        call.enqueue(new Callback<List<Questions>>() {
            @Override
            public void onResponse(Call<List<Questions>> call, Response<List<Questions>> response) {
//                questionLayout.setVisibility(View.VISIBLE);
                questionsList=response.body();
                Collections.shuffle(questionsList);
                for(int k=0;k<questionsList.size();k++)
                    shuffleArray(questionsList.get(k).getAnswer());

//                countDownTimer.start();
                if(!response.isSuccessful()){
                Log.d("Error Code : ",Integer.toString(response.code()));
                return;
            }
                if(received==0)
                received=1;

                countDownTimer=new CountDownTimer1(total_time+2000,1) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(extra%1000==0)
                        {
                            if(timer_flag==1)
                        {
                            counter--;
                            counttime.setText(Integer.toString(counter/60)+":"+String.valueOf(counter%60));
                        }

                        }

                            extra++;
                    }
                    @Override
                    public void onFinish() {
                        questionLayout.setVisibility(View.GONE);
                        gameOverLayout.setVisibility(View.VISIBLE);
                        exitSound.release();
                        wrongSound.release();
                        correctSound.release();
                        stuckSound.release();
                        if(scoreKeeper>0)
                        scoreKeeper+=counter/timePenalty;
                        displayScore.setText("Score : "+Integer.toString(scoreKeeper));
                        displayTime.setText("Time Taken : "+Integer.toString((total_time/1000-counter)/60)+":"+Integer.toString((total_time/1000-counter)%60));
                        displayBestScore.setText(Long.toString(extra));
                    }
                };
                if(received==3) {
                    loadingLayout.setVisibility(View.GONE);
                    questionLayout.setVisibility(View.VISIBLE);
                    countDownTimer.start();
                    setCards(questionLayout,gameOverLayout,imageView,imageCard,question,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);


//                    setCards(questionLayout, gameOverLayout, imageView, imageCard, question, answer1, answer2, answer3, answer4, cardView1, cardView2, cardView3, cardView4);
                }
            }

            @Override
            public void onFailure(Call<List<Questions>> call, Throwable t) {
                Log.d("Error getting question:",t.getMessage());
            }
        });
    }
    void setTheme(CharSequence theme)
    {
        final StringBuilder sb = new StringBuilder(theme.length());
        sb.append(theme);
        this.theme=sb.toString();
    }
    void shuffleArray(String[] array)
    {
        List<String> intList = Arrays.asList(array);

        Collections.shuffle(intList);

        intList.toArray(array);

    }
    private void loadGif(ImageView iv,int imageint){
        if(android.os.Build.VERSION.SDK_INT>=28)
        try {
            ImageDecoder.Source source =
                    ImageDecoder.createSource(getResources(),imageint );

            Drawable drawable = ImageDecoder.decodeDrawable(source);
            iv.setImageDrawable(drawable);

            if (drawable instanceof AnimatedImageDrawable) {
                ((AnimatedImageDrawable) drawable).start();

            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        else
        {
            iv.setImageResource(R.drawable.loadingone);
        }
    }
//    CountDownTimer1 co
    CountDownTimer1 countDownTimer2(final RelativeLayout questionLayout,final LinearLayout gameOverLayout,final ImageView imageView,final CardView imageCard,final TextView question,final TextView answer1,final TextView answer2,final TextView answer3,final TextView answer4,final CardView cardView1,final CardView cardView2,final CardView cardView3,final CardView cardView4)
    {
        return new CountDownTimer1(total_time+1000,1) {

            @Override
            public void onTick(long millisUntilFinished) {
                if(extra%1000==0)
                {
                    if(timer_flag==1)
                    {
                        counter--;
                        countTime.setText(Integer.toString(counter/60)+":"+String.valueOf(counter%60));
                    }

                }

                extra++;
                if(extra1%1000==0)
                {
                    if(timer_flag==1)
                        if(counter1==1)
                        {

                            i++;
                            setCards(questionLayout,gameOverLayout,imageView,imageCard,question,answer1,answer2,answer3,answer4,cardView1,cardView2,cardView3,cardView4);

                        }
                        else
                        {
                            counter1--;

                        }
                        if(counter1==10&&questionLayout.getVisibility()==View.VISIBLE)
                            stuckSound.start();
                    questionTimerLayout.setText(Integer.toString(counter1));

                }
                extra1++;
            }
            @Override
            public void onFinish() {
                questionLayout1.setVisibility(View.GONE);
                gameOverLayout1.setVisibility(View.VISIBLE);
                if(scoreKeeper>0)
                    scoreKeeper+=counter/timePenalty;
                displayScore.setText("Score : "+Integer.toString(scoreKeeper));
                displayTime.setText("Time Taken : "+Integer.toString((total_time/1000-counter)/60)+":"+Integer.toString((total_time/1000-counter)%60));
                displayBestScore.setText(Long.toString(extra));
            }
        };
    }
}
