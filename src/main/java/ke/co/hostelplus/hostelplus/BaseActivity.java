package ke.co.hostelplus.hostelplus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.List;

import ke.co.hostelplus.hostelplus.db.UserDb;
import ke.co.hostelplus.hostelplus.security.UserFunctions;


/**
 * Created by  on 4/4/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Drawer result = null;

    Toolbar toolbar;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getLayoutResourceId());
        activityOutput();

        toolbar = (Toolbar) findViewById(getToolbarResourceId());
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitleName());
        // Handle Toolbar

        refresh(bundle);
    }

    //Methods that are abbstracted
    public void refresh(Bundle bundle) {
        List<UserDb> user = UserDb.getAll();

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.toolbarbg)
                .addProfiles(
                        new ProfileDrawerItem().withName(user.get(0).getUsername()).withEmail(user.get(0).getEmail()).withIcon(new IconicsDrawable(this)
                                .icon(Ionicons.Icon.ion_person)
                                .color(Color.WHITE)
                                .sizeDp(32))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSavedInstance(bundle)
                .withAccountHeader(headerResult)
                .addDrawerItems(


                        new PrimaryDrawerItem().withName("All Hostels").withIcon(Ionicons.Icon.ion_android_list).withIdentifier(4).withSelectable(false),
                        new PrimaryDrawerItem().withName("Search").withIcon(Ionicons.Icon.ion_search).withIdentifier(2).withSelectable(false),

                        new SectionDrawerItem().withName("Settings"),
                        new PrimaryDrawerItem().withName("Logout").withIcon(Ionicons.Icon.ion_log_out).withIdentifier(3).withSelectable(false)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {

                           if (drawerItem.getIdentifier() == 2) {
                                SearchPopup p = new SearchPopup();
                                p.show(getSupportFragmentManager(), "SearchPopup");
                            } else if (drawerItem.getIdentifier() == 3) {
                                UserFunctions.logout();
                                Intent x = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(x);
                            } else if (drawerItem.getIdentifier() == 4) {
                                Intent x = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(x);
                            }
                        }

                        return false;
                    }
                })
                .build();

    }

    protected abstract int getLayoutResourceId();

    protected abstract String getTitleName();

    protected abstract int getToolbarResourceId();

    protected abstract void activityOutput();
}