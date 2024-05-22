package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * this pager adapter inflates the corresponding pages
 * like demonstrated, fragments are created but nothing holds reference
 * to the created fragments, that's because such thing is risky during configuration changes
 * hence a hack way was devised to retrieve fragments/child views when they're needed
 */
public class FragmentPagerAdapter extends FragmentStateAdapter {

    final String[] webUrls;

    public FragmentPagerAdapter(@NonNull FragmentManager fragmentManager, String[] webUrls, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.webUrls = webUrls;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return WebViewContainer.newInstance(webUrls[position]);
    }

    @Override
    public int getItemCount() {
        return webUrls.length;
    }

    //fragment/view is retrieved from the fragment manager using the naming convention
    //displayed in manager.findFragmentByTag
    public Fragment getFragAt(int currentItem, FragmentManager manager){
        return manager.findFragmentByTag("f" + currentItem);
    }
}
