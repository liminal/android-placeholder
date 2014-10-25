package se.lightside.placeholder.sample;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.support.v4.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;


public class DemoHostActivity extends ActionBarActivity
        implements DemoNavDrawerFragment.NavigationDrawerCallbacks {

    private DemoNavDrawerFragment mNavigationDrawerFragment;

    private static List<DemoNavEntry> sDemos;
    static {
        sDemos = new ArrayList<DemoNavEntry>();
        sDemos.add(new DemoNavEntry("Backgrounds Demo", R.layout.demo_backgrounds, 0));
        sDemos.add(new DemoNavEntry("Icon Demo",        R.layout.demo_icon_activity, R.menu.demo_icon));
        sDemos.add(new DemoNavEntry("StockPhoto Demo",  R.layout.demo_stockphoto, 0));
        sDemos.add(new DemoNavEntry("Dimensions Demo",  R.layout.demo_dimensions, 0));
        sDemos.add(new DemoNavEntry("Text Demo",        R.layout.demo_text, 0));
    }

    public static List<DemoNavEntry> getDemos() { return sDemos; }

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_host);


        mNavigationDrawerFragment = (DemoNavDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        DemoNavEntry demo = sDemos.get(position);
        fragmentManager.beginTransaction()
                .replace(R.id.container, DemoFragment.newInstance(demo.getTitle(), demo.getLayoutRes(), demo.getMenuRes()))
                .commit();
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

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

        @Override
        public String toString() {
            return mTitle;
        }
    }

}
