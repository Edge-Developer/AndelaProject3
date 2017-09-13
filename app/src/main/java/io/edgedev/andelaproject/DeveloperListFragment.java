package io.edgedev.andelaproject;

import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import io.edgedev.andelaproject.databinding.SingleDevBinding;



public class DeveloperListFragment extends Fragment implements SearchView.OnQueryTextListener {
    private static final String TAG = "DeveloperListFragment";
    private final String LagosJavaDevURL = "https://api.github.com/search/users?q=location:lagos+language:java&page=1";
    private RecyclerViewAdapter mAdapter;
    private DeveloperClick mDeveloperClick;
    private View mView;
    private RecyclerView mRecyclerView;


    public DeveloperListFragment() {
    }

    public static DeveloperListFragment newInstance() {
        Bundle args = new Bundle();
        DeveloperListFragment fragment = new DeveloperListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDeveloperClick = (DeveloperClick) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDeveloperClick = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void download_profiles(String URL, final View view) {
        final List<Developer> developers = new ArrayList<>();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Model model = new Gson().fromJson(response, Model.class);
                Developer developer;
                Item item;

                for (int i = 0; i < model.getItems().size(); i++) {
                    item = model.getItems().get(i);
                    developer = new Developer(
                            item.get_Id(),
                            item.getLogin(),
                            item.getHtmlUrl(),
                            item.getAvatarUrl(),
                            item.getScore()
                    );

                    developers.add(developer);
                }
                Singleton.newInstance().setDevelopers(developers);
                mAdapter.setDevelopers(developers);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, R.string.network_error, Snackbar.LENGTH_LONG).show();
            }
        });

        ConnectionManager.getInstance(getContext()).add(mStringRequest);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_developer_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.developers_recycler_view);
        if (isTablet(getContext())) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), CalculateNoOfColumns(getContext())));
        }
        mAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
        if (!(Singleton.newInstance().size() == 0)) {
            mAdapter.setDevelopers(Singleton.newInstance().getDevelopers());
        } else {
            download_profiles(LagosJavaDevURL, view);
        }

        mView = view;
        return view;
    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    private int CalculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.search_dev);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.retry) {
            if (Singleton.newInstance().size() == 0)
                download_profiles(LagosJavaDevURL, mView);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO: implement Search Feature!
        Snackbar.make(mView, R.string.implement_later, Snackbar.LENGTH_LONG).show();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }


    interface DeveloperClick {
        void setOnDeveloperClickedListener(Developer developer);
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<DeveloperHolder> {
        private List<Developer> mDevelopers;

        public RecyclerViewAdapter() {
            mDevelopers = new ArrayList<>();
        }

        @Override
        public DeveloperHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            SingleDevBinding binding = DataBindingUtil.inflate(inflater, R.layout.single_dev, parent, false);
            return new DeveloperHolder(binding);
        }

        @Override
        public void onBindViewHolder(DeveloperHolder holder, int position) {
            holder.bind(mDevelopers.get(position));
        }

        @Override
        public int getItemCount() {
            return mDevelopers.size();
        }

        public void setDevelopers(List<Developer> developers) {
            mDevelopers = developers;
            notifyDataSetChanged();
        }
    }
    private class DeveloperHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Developer mDeveloper;
        private SingleDevBinding mBinding;
        private NetworkImageView mNetworkImageView;

        public DeveloperHolder(SingleDevBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mNetworkImageView = binding.profileImage;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mDeveloperClick.setOnDeveloperClickedListener(mDeveloper);
        }

        public void bind(Developer developer) {
            mDeveloper = developer;
            mNetworkImageView.setImageUrl(developer.getImage(), ConnectionManager.getImageLoader(getContext()));
            mBinding.setDeveloper(developer);
        }
    }
}