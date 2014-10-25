package se.lightside.placeholder.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public class DemoFragment extends Fragment {
    public static final String EXTRA_TITLE  = "title";
    public static final String EXTRA_LAYOUT = "layoutRes";
    public static final String EXTRA_MENU   = "menuRes";

    public static DemoFragment newInstance(String title, int layoutRes, int menuRes) {
        DemoFragment frag = new DemoFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TITLE, title);
        args.putInt(EXTRA_LAYOUT, layoutRes);
        args.putInt(EXTRA_MENU, menuRes);
        frag.setArguments(args);
        return frag;
    }


    private String mTitle;
    private int mLayoutRes;
    private int mMenuRes;
    private DemoHostActivity mDemoHostActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DemoHostActivity) {
            mDemoHostActivity = (DemoHostActivity) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        mTitle = args.getString(EXTRA_TITLE);
        mLayoutRes  = args.getInt(EXTRA_LAYOUT);
        mMenuRes    = args.getInt(EXTRA_MENU);


        if (mDemoHostActivity != null && !TextUtils.isEmpty(mTitle)) {
            mDemoHostActivity.setTitle(mTitle);
        }
        if (mMenuRes != 0) {
            setHasOptionsMenu(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(mLayoutRes, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mMenuRes != 0) {
            inflater.inflate(R.menu.demo_icon, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDetach() {
        mDemoHostActivity = null;
        super.onDetach();
    }
}
