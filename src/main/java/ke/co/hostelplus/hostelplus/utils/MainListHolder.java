package ke.co.hostelplus.hostelplus.utils;

import android.app.Activity;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.mikepenz.iconics.view.IconicsButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


import ke.co.hostelplus.hostelplus.R;
import ke.co.hostelplus.hostelplus.ViewPopup;
import ke.co.hostelplus.hostelplus.data.AppusersHostels;
import ke.co.hostelplus.hostelplus.data.Hostel;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by  on 03-08-2016.
 */
@LayoutId(R.layout.listitem)

public class MainListHolder extends ItemViewHolder<Hostel> {
String url="/files/Hostels/photo";
String baseurl = getContext().getResources().getString(R.string.baseurl);
    @ViewId(R.id.name)
    TextView name;

    @ViewId(R.id.location)
    TextView location;

    @ViewId(R.id.cost)
    TextView cost;

    @ViewId(R.id.amenities)
    TextView amenities;

    @ViewId(R.id.photo)
    ImageView photo;

    @ViewId(R.id.avrating)
    RatingBar avrating;


    @ViewId(R.id.details)
    IconicsButton details;

    public MainListHolder (View view) {
        super(view);
    }
    @Override
    public void onSetValues(final Hostel s, final PositionInfo positionInfo) {
        name.setText(s.getName());
        cost.setText("KSH. "+s.getCost());
        location.setText(s.getLocation());
        amenities.setText(s.getAmenities());
        Ion.with(getContext()).load(baseurl+url+"/"+s.getPhoto()).intoImageView(photo);

        avrating.setRating(getAverage(s.getAppusersHostels()));
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPopup frag = ViewPopup.newInstance(s.getId());
                frag.show(((AppCompatActivity)getContext()).getSupportFragmentManager(), "ViewPopup");

            }
        });
    };

    public  float getAverage(List <AppusersHostels> appusersHostels){
        float average = (float)0.0;

        ArrayList<Integer> all = new ArrayList<>();
        if(!appusersHostels.isEmpty()){
        for(AppusersHostels ah :appusersHostels){
          all.add(ah.getRating());
        }
        if(!all.isEmpty()){
            average = (float)calculateAverage(all);
        }}
        return average;

    }


    private double calculateAverage(List <Integer> marks) {
        Integer sum = 0;
            if(!marks.isEmpty()) {
            for (Integer mark : marks) {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
    }



}
