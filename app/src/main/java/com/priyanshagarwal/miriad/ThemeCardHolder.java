package com.priyanshagarwal.miriad;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ThemeCardHolder extends RecyclerView.ViewHolder {
    TextView themeName;
    ImageView themeImage;
    String exitMessage,positiveMessage,negativeMessage;
    String exitSound,stuckSound,correctSound, wrongSound;
   ThemeCardHolder(View itemView)
    {
        super(itemView);
        themeName= (TextView)itemView.findViewById(R.id.product_title);
        themeImage=(ImageView) itemView.findViewById((R.id.product_image));

    }
}
