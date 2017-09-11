package net.cuongmai.shoeclothingsizeconverter;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to the TextView and DrawerLayout
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Replace the default action bar with a Toolbar so the navigation drawer appears above it
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // These lines are needed to display the top-left hamburger button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Make the hamburger button work
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.app_name, R.string.app_name){
            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }
        };

        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.shoe_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        addFragment(adapter, DatabaseHelper.TABLE_MEN);
        addFragment(adapter, DatabaseHelper.TABLE_WOMEN);
        addFragment(adapter, DatabaseHelper.TABLE_YOUTH);
        addFragment(adapter, DatabaseHelper.TABLE_KIDS);
        addFragment(adapter, DatabaseHelper.TABLE_BABIES);

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.shoes_tab);
        tabLayout.setupWithViewPager(viewPager);

    } // End of onCreate()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                showRatingDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addFragment(ViewPagerAdapter adapter, String tableName) {
        Bundle bundle = new Bundle();
        bundle.putString(SizeChartFragment.STRING_TABLE_NAME, tableName);

        SizeChartFragment sizeChartFragment = new SizeChartFragment();
        sizeChartFragment.setArguments(bundle);

        adapter.addFragment(sizeChartFragment, tableName);
    }

    // Show Feedback/Rating Dialog (Code reference: Ram Satish - javarticles.com)
    private void showRatingDialog() {
        String ratingDialogMessage = getString(R.string.rating_dialog_message1) + " " + getString(R.string.app_name) + " " +
                getString(R.string.by) + " " + getString(R.string.author_name) + "\n \n" +
                getString(R.string.rating_dialog_message2) + "\n";

        createRatingDialog(getString(R.string.rating_dialog_title), ratingDialogMessage).show();
    }


    // Create Feedback/Rating Dialog (Code reference: Ram Satish - javarticles.com)
    private AlertDialog createRatingDialog(String rateAppTitle, String rateAppMessage) {
        AlertDialog dialog = new AlertDialog.Builder(this).setPositiveButton(getString(R.string.dialog_rate_app), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                openAppInPlayStore(MainActivity.this);
            }
        }).setNegativeButton(getString(R.string.dialog_feedback), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                openFeedback(MainActivity.this);
            }
        }).setNeutralButton(getString(R.string.dialog_ask_later), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                paramAnonymousDialogInterface.dismiss();
            }
        }).setMessage(rateAppMessage).setTitle(rateAppTitle).create();
        return dialog;
    }

    // Open app in Play Store (Code reference: Ram Satish - javarticles.com)
    public static void openAppInPlayStore(Context paramContext) {
        paramContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=")));
    }

    // Open email app to give feedback (Code reference: Ram Satish - javarticles.com)
    public static void openFeedback(Context paramContext) {
        Intent localIntent = new Intent(Intent.ACTION_SEND);
        localIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"cuong@cuongmai.net"});
        localIntent.putExtra(Intent.EXTRA_CC, "");
        String str = null;
        try {
            str = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
            localIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Your Android App");
            localIntent.putExtra(Intent.EXTRA_TEXT, "\n\n----------------------------------\n Device OS: Android \n Device OS version: " +
                    Build.VERSION.RELEASE + "\n App Version: " + str + "\n Device Brand: " + Build.BRAND +
                    "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER);
            localIntent.setType("message/rfc822");
            paramContext.startActivity(Intent.createChooser(localIntent, "Choose an Email client :"));
        } catch (Exception e) {
            Log.d("OpenFeedback", e.getMessage());
        }
    }


    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
