package io.edgedev.andelaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class DeveloperPagerActivity extends AppCompatActivity {

    private static final String TAG = "DeveloperPagerActivity";
    private static final String DEVELOPER_ID = "io.edgedev.andelaproject.developer_id";

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, DeveloperPagerActivity.class);
        intent.putExtra(DEVELOPER_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_pager);
        Intent intent = getIntent();
        int id = intent.getIntExtra(DEVELOPER_ID, 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final List<Developer> developers = Singleton.newInstance().getDevelopers();

        ViewPager viewPager = (ViewPager) findViewById(R.id.developer_viewpager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Developer developer = developers.get(position);
                return DevDetailFragment.newInstance(developer.getId());
            }

            @Override
            public int getCount() {
                return developers.size();
            }
        });

        for (int i = 0; i < developers.size(); i++) {
            if (developers.get(i).getId() == id) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
