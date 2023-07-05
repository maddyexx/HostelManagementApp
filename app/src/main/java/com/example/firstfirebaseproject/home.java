package com.example.firstfirebaseproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class home extends Fragment {
    CardView card;
    CardView card1;
    public home() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        card = view.findViewById(R.id.card_view);
        card1 = view.findViewById(R.id.card_view1);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav_room fragment = new nav_room();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFragmentUser fragment = new PaymentFragmentUser();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel("https://source.unsplash.com/1000x600/?room,interior", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://source.unsplash.com/1000x600/?room,bed", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://source.unsplash.com/1000x600/?room,sharing", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://source.unsplash.com/1000x600/?room", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://source.unsplash.com/1000x600/?room,house", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://source.unsplash.com/1000x600/?room,hotel", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://source.unsplash.com/1000x600/?room,roommates", ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);

        return view;
    }
}
