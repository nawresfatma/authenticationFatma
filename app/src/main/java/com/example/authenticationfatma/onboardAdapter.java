package com.example.authenticationfatma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class onboardAdapter extends RecyclerView.Adapter<onboardAdapter.onboardingViewHolder>{
    private List<onboardItem>onboardItems;

    public onboardAdapter(List<onboardItem> onboardItems) {
        this.onboardItems = onboardItems;
    }

    @NonNull
    @Override
    public onboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new onboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_onboard,parent,false
                )
        );
    }


    @Override
    public void onBindViewHolder(@NonNull onboardingViewHolder holder, int position) {
        holder.setOnBoardingData(onboardItems.get(position));

    }

    @Override
    public int getItemCount() {
        return onboardItems.size();
    }

    class onboardingViewHolder extends RecyclerView.ViewHolder{
        private TextView textTitle , textDesciption;
        private ImageView imageOnboarding;

         onboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle =itemView.findViewById(R.id.textTitle);
            textDesciption=itemView.findViewById(R.id.textDescription);
            imageOnboarding=itemView.findViewById(R.id.imageOnBoard);
        }
        void setOnBoardingData(onboardItem onboardingItem){
            textTitle.setText(onboardingItem.getTitle());
            textDesciption.setText(onboardingItem.getDescription());
            imageOnboarding.setImageResource(onboardingItem.getImage());

        }
    }
}
