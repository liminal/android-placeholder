package se.lightside.placeholder.sample;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;


public class DemoHostActivity extends ActionBarActivity {

    @InjectView(R.id.navigation_drawer) RelativeLayout mNavigationDrawerView;
    @InjectView(R.id.nav_drawer_list)   ListView mDrawerListView;
    @InjectView(R.id.drawer_layout)     DrawerLayout mDrawerLayout;
    @InjectView(R.id.toolbar)           Toolbar mToolbar;

    private ActionBarDrawerToggle mDrawerToggle;
    private DemoNavAdapter mDemos;

    //public static List<DemoNavEntry> getDemos() { return sDemos; }

    /** Used to store the last screen title. */
    private CharSequence mTitle;

    /*
     * LIFECYCLE CALLBACKS
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_host);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        // set up the drawer's list view with items and click listener
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDemos = new DemoNavAdapter(this);
        mDemos.add(new DemoNavEntry("Backgrounds", R.layout.demo_backgrounds, 0));
        mDemos.add(new DemoNavEntry("Icon",        R.layout.demo_icon_activity, R.menu.demo_icon));
        mDemos.add(new DemoNavEntry("StockPhoto",  R.layout.demo_stockphoto, 0));
        mDemos.add(new DemoNavEntry("Dimensions",  R.layout.demo_dimensions, 0));
        mDemos.add(new DemoNavEntry("Text",        R.layout.demo_text, 0));
        mDrawerListView.setAdapter(mDemos);

//                new ArrayAdapter<>(
//                getSupportActionBar().getThemedContext(),
//                android.R.layout.simple_list_item_1,
//                android.R.id.text1,
//                getDemos()));
        selectItem(0);

        mTitle = getTitle();

        // Set up the drawer.
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new DemoDrawerToggle(this, mDrawerLayout, mToolbar);
        mDrawerLayout.openDrawer(mNavigationDrawerView);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        if (!isDrawerOpen()) {
            setActionBarTitle(mTitle);
            return true;
        }

        setActionBarTitle(getString(R.string.app_name));
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /*
     * METHODS
     */
    public void setTitle(CharSequence title) { mTitle = title; }

    private void setActionBarTitle(CharSequence title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }

    public boolean isDrawerOpen() { return mDrawerLayout.isDrawerOpen(mNavigationDrawerView); }

    @OnItemClick(R.id.nav_drawer_list)
    public void selectItem(int position) {
        mDrawerListView.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mNavigationDrawerView);
        DemoNavEntry demo = mDemos.getItem(position);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, DemoFragment.newInstance(demo.getTitle(), demo.getLayoutRes(), demo.getMenuRes()))
                .commit();
    }

    /**
     * Navigation Drawer toggle handler for the DemoHostActivity
     */
    public class DemoDrawerToggle extends ActionBarDrawerToggle {
        public DemoDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar) {
            super(activity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
        }
    }

    /**
     * Entry in the Navigation drawer menu
     */
    public static class DemoNavEntry {
        private String  mTitle;
        private int     mLayoutRes;
        private int     mMenuRes;

        public DemoNavEntry(String title, int layoutRes, int menuRes) {
            mTitle = title;
            mLayoutRes = layoutRes;
            mMenuRes = menuRes;
        }

        public String getTitle() { return mTitle; }
        public int getLayoutRes() { return mLayoutRes; }
        public int getMenuRes() { return mMenuRes; }

        @Override public String toString() {
            return mTitle;
        }
    }

    /**
     * Demo Navdrawer Adapter
     */
    public static class DemoNavAdapter extends BaseAdapter {
        private final LayoutInflater mInflater;
        private List<DemoNavEntry> mDemos;

        public DemoNavAdapter(Context ctx) {
            mDemos = new ArrayList<>();
            mInflater = LayoutInflater.from(ctx);
        }

        public void add(DemoNavEntry entry) {
            mDemos.add(entry);
        }

        @Override
        public int getCount() {
            return mDemos.size();
        }

        @Override
        public DemoNavEntry getItem(int position) {
            return mDemos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (convertView == null) {
                v = mInflater.inflate(R.layout.nav_menu_entry, parent, false);
            }

            TextView tv = (TextView) v.findViewById(R.id.nav_entry_title);
            tv.setText(getItem(position).getTitle());

            return v;
        }
    }

}
