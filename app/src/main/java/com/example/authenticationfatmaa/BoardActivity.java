package com.example.authenticationfatmaa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.authenticationfatmaa.R;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {
    private onboardAdapter onboardingAdapter;
    private LinearLayout layoutonboardingIndicator;
    private TextView skip;
    private Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        //FirstTimeInstall
        SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        String FirstTime=preferences.getString("FirstTimeInstall","");

        if(FirstTime.equals("yes")) {
            Intent intent=new Intent(BoardActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall", "yes");
            editor.apply();
        }

        layoutonboardingIndicator = findViewById(R.id.lay_onbord);
        setupOnboardingItem();
        setLayoutonboardingIndicator();
        setCurrentonboardingIndicator(0);
        skip=findViewById(R.id.skip);
        next=findViewById(R.id.nextBut);

        final ViewPager2 onboardingViewPager =findViewById(R.id.viewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);
        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentonboardingIndicator(position);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
              onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem()+1);
            }else{
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }
    private void setupOnboardingItem(){
        List<onboardItem> onBoardItems = new ArrayList<>();
        onboardItem interface1 = new onboardItem();
        interface1.setTitle("Never Get Lost");
        interface1.setDescription("We are more than happy that you are using our app so gets start to explore Tunisia in your native language");
        interface1.setImage(R.drawable.onboard1);

        onboardItem interface2 = new onboardItem();
        interface2.setTitle(" new best friend");
        interface2.setDescription("We are more than happy that you are using our app so gets start to explore Tunisia in your native language");
        interface2.setImage(R.drawable.onboard2);

        onboardItem interface3 = new onboardItem();
        interface3.setTitle(" Explore it");
        interface3.setDescription("We are more than happy that you are using our app so gets start to explore Tunisia in your native language");
        interface3.setImage(R.drawable.onboard3);

        onBoardItems.add(interface1);
        onBoardItems.add(interface2);
        onBoardItems.add(interface3);

        onboardingAdapter = new onboardAdapter(onBoardItems);
    }

    private void setLayoutonboardingIndicator(){
        ImageView[] indicators=new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i=0; i<indicators.length;i++){
            indicators[i]=new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboard_indicater_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutonboardingIndicator.addView(indicators[i]);
        }
    }
    private void setCurrentonboardingIndicator(int index){
        int childCount= layoutonboardingIndicator.getChildCount();
        for (int i=0; i<childCount;i++){
            ImageView imageView =(ImageView)layoutonboardingIndicator.getChildAt(i);
            if(i==index){
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.onboard_indicater
                )

                );

            }else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.onboard_indicater_inactive
                ));

            }
        }

    }
}