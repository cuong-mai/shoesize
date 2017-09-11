package net.cuongmai.shoeclothingsizeconverter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    private void addFragment(ViewPagerAdapter adapter, String tableName) {
        Bundle bundle = new Bundle();
        bundle.putString(SizeChartFragment.STRING_TABLE_NAME, tableName);

        SizeChartFragment sizeChartFragment = new SizeChartFragment();
        sizeChartFragment.setArguments(bundle);

        adapter.addFragment(sizeChartFragment, tableName);
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
