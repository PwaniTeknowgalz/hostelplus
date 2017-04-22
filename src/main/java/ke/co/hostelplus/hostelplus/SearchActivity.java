package ke.co.hostelplus.hostelplus;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


import ke.co.hostelplus.hostelplus.data.Hostel;
import ke.co.hostelplus.hostelplus.db.HostelDb;
import ke.co.hostelplus.hostelplus.utils.MainListHolder;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

/**
 * Created by Badru on 4/6/2016.
 */
public class SearchActivity extends BaseActivity{
    String url;
    RecyclerView list ;
    RelativeLayout error;
    TextView errortxt,time;
    private Menu menu;

    RecyclerView.LayoutManager m=null;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.searchactionactivity_layout;
    }

    @Override
    protected String getTitleName() {
        return "Search Hostel List";
    }

    @Override
    protected int getToolbarResourceId() {
        return R.id.holder;
    }

    @Override
    protected void activityOutput() {

        url=getResources().getString(R.string.baseurl)+getResources().getString(R.string.api);
        list=(RecyclerView)findViewById(R.id.list);
        error=(RelativeLayout) findViewById(R.id.error);
        errortxt=(TextView) findViewById(R.id.errortext);
        time=(TextView) findViewById(R.id.time);
        m= new LinearLayoutManager(getApplicationContext());

       




        if(HostelDb.getAll().isEmpty()){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);

        }else{
            List<HostelDb> all2 = HostelDb.getAll();
            ArrayList<Hostel> ps = fetchAll(all2.get(0).getData());

            ArrayList<Hostel> sch= null;
            try {
                sch = search(ps,getIntent().getStringExtra("searchterm"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(sch.isEmpty()){
                list.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                errortxt.setText("Your search query doesn't match any data in our database. Please search again.");
            }else{
            EasyRecyclerAdapter e = new EasyRecyclerAdapter(SearchActivity.this, MainListHolder.class, sch);
            list.setLayoutManager(m);

            list.setAdapter(e);
        }}

    }
    public ArrayList<Hostel> search(ArrayList<Hostel> s,String content) throws ParseException {
        //ArrayList<ProductsSupermarkets> newlist= new ArrayList<>();

        ArrayList<Hostel> retain = new ArrayList<Hostel>(s.size());
        for (Hostel dealer : s) {
            if (dealer.getName().contains(content)) {
                retain.add(dealer);
            }else if (dealer.getLocation().contains(content)) {
                retain.add(dealer);
            }else if (dealer.getAmenities().contains(content)) {
                retain.add(dealer);
            }else if (dealer.getCost().contains(content)) {
                retain.add(dealer);
            }
        }

        // either assign 'retain' to 'wsResponse.Dealers' or ...
        s.clear();
        s.addAll(retain);
        return s;
    }
    public ArrayList<Hostel> fetchAll(String dataset){
        JsonElement jelements = new JsonParser().parse(dataset);
        ArrayList<Hostel > rowItems=new ArrayList<>();
        JsonArray j=jelements.getAsJsonArray();
        for (int i=0;i<j.size();i++) {
            Hostel dta = new Gson().fromJson(j.get(i).getAsJsonObject(),Hostel .class);
            rowItems.add(dta);
        }
        return rowItems;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        menu.getItem(0).setIcon(new IconicsDrawable(this).icon(Ionicons.Icon.ion_search).color(Color.WHITE).sizeDp(24));
        menu.getItem(1).setIcon(new IconicsDrawable(this).icon(Ionicons.Icon.ion_android_add).color(Color.WHITE).sizeDp(24));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            SearchPopup p = new SearchPopup();
            p.show(getSupportFragmentManager(),"SearchPopup");
            return true;
        }else if (id == R.id.action_add) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@hostelplus.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Request to post my hostel");
            if (intent.resolveActivity(this.getPackageManager()) != null) {
                startActivity(intent);
            }

            Toast.makeText(this, "Creating Email Message", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
