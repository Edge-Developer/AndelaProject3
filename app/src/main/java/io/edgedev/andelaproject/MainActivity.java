package io.edgedev.andelaproject;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity implements DeveloperListFragment.DeveloperClick {

    @Override
    protected Fragment createFragment() {
        return DeveloperListFragment.newInstance();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void setOnDeveloperClickedListener(Developer developer) {
        if (findViewById(R.id.detail_fragment_container) == null){
            Intent intent = DeveloperPagerActivity.newIntent(this, developer.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = DevDetailFragment.newInstance(developer.getId());
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, newDetail).commit();
        }

    }
}
