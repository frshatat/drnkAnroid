package com.drnkmobile.drnkAndroid.drnk;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.drnkmobile.drnkAndroid.app.R;

public class SpecialActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager viewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs;
    public static String POSITION = "POSITION";
    TabLayout tabLayout;
    private Toolbar toolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ImageView imageview;
    private TextView business;
    private Typeface face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);

        checkForTypeOfBusinessToDisplayProperTabsName();
        viewPager = (ViewPager) findViewById(R.id.pager);
        int image = getIntent().getExtras().getInt("image");
        String businessName = getIntent().getExtras().getString("businessName");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), SpecialActivity.this);
        viewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        imageview = (ImageView) findViewById(R.id.imageView4);
        business = (TextView) findViewById(R.id.businessName);
        imageview.setImageResource(image);
        business.setText(businessName);
        face=Typeface.createFromAsset(viewPager.getContext().getAssets(),"fonts/AvenirLTStd-Light.ttf");
        business.setTypeface(face);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mSectionsPagerAdapter.getTabView(i));
        }
    }


    private void checkForTypeOfBusinessToDisplayProperTabsName() {
        if (drnk.section.equals("stores")) {
            tabs = new String[]{"Current Week", "Info"};
        } else {
            tabs = new String[]{"Today", "Week", "Info"};
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private Context context;

        public SectionsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }


        @Override
        public int getCount() {
            if (drnk.section.equals("stores")) {
                return 2;
            } else {
                return 3;
            }

        }


        @Override
        public Fragment getItem(int index) {

            if (drnk.section.equals("stores")) {
                switch (index) {
                    case 0:

                        return new TodayFragment();
                    case 1:

                        return new InfoFragment();
                }
            } else {
                switch (index) {
                    case 0:

                        return new TodayFragment();
                    case 1:

                        return new WeekActivity();
                    case 2:

                        return new InfoFragment();
                }
            }

            return null;
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View v = LayoutInflater.from(context).inflate(R.layout.custom_tab_layout, null);
            TextView tv = (TextView) v.findViewById(R.id.textView3);
            tv.setText(tabs[position]);
            tv.setTypeface(face);
//            ImageView img = (ImageView) v.findViewById(R.id.imageView3);
//            img.setImageResource(R.drawable.ic_logo);
            return v;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
//            Drawable image = context.getResources().getDrawable(R.drawable.ic_logo);
//            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            // Replace blank spaces with image icon
            SpannableString sb = new SpannableString("   " + tabs[position]);
//            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }
    }


}



