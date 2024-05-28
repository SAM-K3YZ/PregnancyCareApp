package com.finalYearProject.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.finalYearProject.myapplication.R;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;

    //get the images, title, description and set them as an array
    int [] images ={
            R.drawable.pregnancy_calender,
            R.drawable.pregnancy_symtops_list,
            R.drawable.pregnant_woman
    };

    int [] title = {
            R.string.slideTitle1,
            R.string.slideTitle2,
            R.string.slideTitle3
    };

    int [] description ={
            R.string.slideDescription1,
            R.string.slideDescription2,
            R.string.slideDescription3,
    };

    public ViewPagerAdapter(Context context){
        this.context = context;
    }

    //how many layouts to display
    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    //joining the sliderLayout with the text, images and description arrays
    //inflating
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        //setting the arrays according to how they were placed above
        ImageView slideImage = view.findViewById(R.id.slideImage);
        TextView slideTitle = view.findViewById(R.id.slideTitle);
        TextView slideDesc = view.findViewById(R.id.slideDescription);

        //setting the images and text to show in the onBoardingScreen
        slideImage.setImageResource(images[position]);
        slideTitle.setText(title[position]);
        slideDesc.setText(description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
