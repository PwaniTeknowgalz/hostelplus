package ke.co.hostelplus.hostelplus;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import ke.co.hostelplus.hostelplus.data.Hostel;
import ke.co.hostelplus.hostelplus.db.HostelDb;

/**
 * Created by  on 21/04/2017.
 */

public class ViewPopup  extends DialogFragment {

    Button cancel,call,text, email;
    TextView name, location,amenities,occupants, contact, cost;
    ImageView photo;

    public static ViewPopup newInstance(int id) {
        ViewPopup frag = new ViewPopup();
        Bundle args = new Bundle();
        args.putInt("id", id);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        int id = getArguments().getInt("id");


        List<HostelDb> all3 = HostelDb.getAll();
        ArrayList<Hostel> hostels = fetchAll(all3.get(0).getData());

        final Hostel hostel = findById(id, hostels);
        String url="/files/Hostels/photo";
        String baseurl = getContext().getResources().getString(R.string.baseurl);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();



        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View dialog= inflater.inflate(R.layout.activity_popup, null);

        name=(TextView) dialog.findViewById(R.id.name);
        location=(TextView) dialog.findViewById(R.id.location);
        amenities=(TextView) dialog.findViewById(R.id.amenities);
        occupants=(TextView) dialog.findViewById(R.id.occupants);
        contact=(TextView) dialog.findViewById(R.id.contact);
        cost=(TextView) dialog.findViewById(R.id.cost);
    photo = (ImageView) dialog.findViewById(R.id.photo);
        Ion.with(getContext()).load(baseurl+url+"/"+hostel.getPhoto()).intoImageView(photo);
        name.setText(hostel.getName());
        location.setText(hostel.getLocation());
        amenities.setText(hostel.getAmenities());
        occupants.setText(hostel.getPersons());
        contact.setText(hostel.getContact_name());
        cost.setText("KSH. "+hostel.getCost());


        cancel=(Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPopup.this.getDialog().cancel();

            }
        });
        call=(Button) dialog.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + hostel.getPhone()));
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                }

                Toast.makeText(getContext(), "Calling....", Toast.LENGTH_SHORT).show();
                ViewPopup.this.getDialog().cancel();

            }
        });
        text=(Button) dialog.findViewById(R.id.text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:"+hostel.getPhone()));  // This ensures only SMS apps respond
                intent.putExtra("sms_body", "Hello "+hostel.getContact_name()+",I would like to inquire about the hostel posted on hostel plus.");
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                }


                Toast.makeText(getContext(), "Creating Text Message", Toast.LENGTH_SHORT).show();
                ViewPopup.this.getDialog().cancel();

            }
        });
        email=(Button) dialog.findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{hostel.getEmail().trim()} );
                intent.putExtra(Intent.EXTRA_SUBJECT, "Request for space at "+hostel.getName());
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                }

                Toast.makeText(getContext(), "Creating Email Message", Toast.LENGTH_SHORT).show();
                ViewPopup.this.getDialog().cancel();

            }
        });



        builder.setView(dialog);

        return builder.create();
    }
    public ArrayList<Hostel> fetchAll(String dataset){
        JsonElement jelements = new JsonParser().parse(dataset);
        ArrayList<Hostel> rowItems=new ArrayList<>();
        JsonArray j=jelements.getAsJsonArray();
        for (int i=0;i<j.size();i++) {
            Hostel dta = new Gson().fromJson(j.get(i).getAsJsonObject(),Hostel.class);
            rowItems.add(dta);
        }
        return rowItems;
    }
    public Hostel findById(int id, ArrayList<Hostel> list){
        Hostel w = null;
        for (Hostel dealer:list ){
            if (dealer.getId()==id) {
                w=dealer;
            }

        }
        return  w;
    }


}