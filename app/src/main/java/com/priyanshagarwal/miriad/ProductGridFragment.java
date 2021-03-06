package com.priyanshagarwal.miriad;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

//import com.google.codelabs.mdc.java.shrine.network.ProductEntry;
//import com.google.codelabs.mdc.java.shrine.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductGridFragment extends Fragment {

//    private void setUpToolbar(View view) {
//        Toolbar toolbar = view.findViewById(R.id.app_bar);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        if (activity != null) {
//            activity.setSupportActionBar(toolbar);
//        }
//        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(getContext(), view.findViewById(R.id.product_grid), new AccelerateDecelerateInterpolator(),getContext().getResources().getDrawable(R.drawable.shr_branded_menu), // Menu open icon
//                getContext().getResources().getDrawable(R.drawable.mrd_close_menu)));
//    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
//        menuInflater.inflate(R.menu.shr_toolbar_menu, menu);
//        super.onCreateOptionsMenu(menu, menuInflater);
//    }
//    @Override
//    public View onCreateView(
//            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view= inflater.inflate(R.layout.shr_product_grid_fragment, container, false);
//        setUpToolbar(view);
//        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return position % 3 == 2 ? 2 : 1;
//            }
//        });
//        recyclerView.setLayoutManager(gridLayoutManager);
//        StaggeredProductCardRecyclerViewAdapter adapter = new StaggeredProductCardRecyclerViewAdapter(
//                ProductEntry.initProductEntryList(getResources()));
//        recyclerView.setAdapter(adapter);
//        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_large);
//        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_small);
//        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            view.findViewById(R.id.product_grid).setBackgroundResource(R.drawable.shr_product_grid_background_shape);
//        }
//        return view;
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
}
