package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class WebViewFragment extends Fragment{

    private Toolbar toolbar;
    private ViewPager2 pager2;
    private TabLayout tabLayout;
    private FragmentPagerAdapter adapter;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    private final String[] webUrls = new String[5];
    private final String[] titles = new String[5];

    public WebViewFragment() {

    }

    public static WebViewFragment newInstance(){
        return new WebViewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titles[0] = getString(R.string.Rapture_Ready);
        titles[1] = getString(R.string.Rapture_Radio);
        titles[2] = getString(R.string.Rapture_TV);
        titles[3] = getString(R.string.Rapture_FB);
        titles[4] = getString(R.string.Rapture_Donate);


        webUrls[0] = getString(R.string.Rapture_Ready_link);
        webUrls[1] = getString(R.string.Rapture_Radio_link);
        webUrls[2] = getString(R.string.Rapture_TV_link);
        webUrls[3] = getString(R.string.Rapture_FB_link);
        webUrls[4] = getString(R.string.Rapture_Donate_link);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new FragmentPagerAdapter(getChildFragmentManager(), webUrls, getLifecycle());
        return inflater.inflate(R.layout.fragment_web_view, container, false);
        //webView.setMixedContentAllowed(true);
        //webView.setCookiesEnabled(true);
        //webView.setGeolocationEnabled(true);
        //webView.getSettings().setSupportZoom(true);
        //webView.getSettings().setBuiltInZoomControls(false);

        //webView.loadUrl(webUrl);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tabs);
        pager2 = view.findViewById(R.id.viewpager2);
        toolbar = view.findViewById(R.id.toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        int pos = preferences.getInt(ThemeModel.COLOR_KEY, 0);
        ThemeModel model = ThemeModel.get(pos);

        pager2.setNestedScrollingEnabled(true);
        pager2.setAdapter(adapter);
        pager2.setOffscreenPageLimit(5);
        pager2.setUserInputEnabled(false);//disables user swiping functions

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                toolbar.setTitle( titles[position] );
            }
        });

        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, pager2, (tabs, position) -> tabs.setText(titles[position]) );
        mediator.attach();

        toolbar.setBackgroundColor(model.TOOLBAR_COLOR);
        tabLayout.setBackgroundColor(model.TAB_LAYOUT_BACKGROUND);

        toolbar.setTitle( titles[pager2.getCurrentItem()] );
        listener = (sharedPreferences, key) -> {
            assert key != null;
            if (key.equals(ThemeModel.COLOR_KEY)) {
                int index = sharedPreferences.getInt(key, 0);
                ThemeModel themeModel = ThemeModel.get(index);
                toolbar.setBackgroundColor(themeModel.TOOLBAR_COLOR);
                tabLayout.setBackgroundColor(themeModel.TAB_LAYOUT_BACKGROUND);
            }
        };
        preferences.registerOnSharedPreferenceChangeListener(listener);

        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener((menuItem) -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.menu_settings) {
                DialogFragment dialogFragment = new ThemeDialogFragment();
                dialogFragment.show(getParentFragmentManager(), "Theme Dialog");
            } else if (itemId == R.id.menu_share) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Download Rapture Ready at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            } else if (itemId == R.id.menu_exit) {
                requireActivity().finish();
            } else if (itemId == R.id.menu_about) {
                startActivity(new Intent(requireActivity(), About.class));
            } else if (itemId == R.id.menu_refresh) {
                Fragment fragment = adapter.getFragAt(pager2.getCurrentItem(), getChildFragmentManager());
                if (fragment instanceof WebViewContainer)
                    ((WebViewContainer) fragment).tryReload();
            } else if (itemId == R.id.menu_rate) {
                Uri uri = Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID);
                Intent rateIntent = new Intent(Intent.ACTION_VIEW, uri);
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(rateIntent);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
                }
            }
            return true;
        });
    }
}