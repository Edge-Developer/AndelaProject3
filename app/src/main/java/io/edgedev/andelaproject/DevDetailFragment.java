package io.edgedev.andelaproject;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;

import io.edgedev.andelaproject.databinding.FragmentDevDetailBinding;

public class DevDetailFragment extends Fragment {

    private static final String DEV_DETAIL_ID = "io.edgedev.andelaproject.devdetailid";
    private int DEVELOPER_ID;

    public DevDetailFragment() {
    }

    public static DevDetailFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(DEV_DETAIL_ID, id);
        DevDetailFragment fragment = new DevDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DEVELOPER_ID = getArguments().getInt(DEV_DETAIL_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentDevDetailBinding devDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dev_detail, container, false);
        Developer developer = Singleton.newInstance().findDeveloper(DEVELOPER_ID);
        NetworkImageView networkImageView = devDetailBinding.profileImageFragment;
        networkImageView.setImageUrl(developer.getImage(), ConnectionManager.getImageLoader(getContext()));
        devDetailBinding.setDev(developer);
        return devDetailBinding.getRoot();
    }
}
