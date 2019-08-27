package com.inrange.trackapplication.module.menu;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inrange.trackapplication.R;
import com.inrange.trackapplication.dto.MenuItemDto;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements MenuView, NavigationMenuAdapter.NavigationMenuAdapterListener {

    private MenuPresenter mMenuPresenter;
    private MenuFragmentListener mMenuFragmentListener;
    private NavigationMenuAdapter mNavigationMenuAdapter;
    private boolean isHelpSelected;
    private boolean isAboutSelected;
    private View mParentView;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMenuPresenter = MenuPresenterImpl.getInstance(this, getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fragment_menu, container, false);
        return mParentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMenuPresenter.onCreateMenu();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMenuFragmentListener = (MenuFragmentListener) context;
    }

    @Override
    public void initMenuAdapter(List<MenuItemDto> menuList) {
        RecyclerView menuListView = (RecyclerView)getView().findViewById(R.id.fragment_menu_list_view);
        mNavigationMenuAdapter = new NavigationMenuAdapter(menuList, this);
        menuListView.setAdapter(mNavigationMenuAdapter);

        final Context context = getContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        menuListView.setLayoutManager(layoutManager);

//        TextView textView = (TextView) mParentView.findViewById(R.id.fragment_menu_version_tv);
//        textView.setText(String.format("%s %s", context.getString(R.string.app_name), AppSnippet.getVersionName(context)));

    }

    @Override
    public void setInitialSelection(int menuId) {
        onClickMenuItem(menuId);
        mNavigationMenuAdapter.setInitialSelection(menuId);
    }

    @Override
    public void onClickMenuItem(int menuId) {
        isHelpSelected = false;
        isAboutSelected = false;
        mMenuFragmentListener.onMenuClick(menuId);
    }

    public void onFragmentChange(int menuPosition) {
        mNavigationMenuAdapter.updateMenuOnFragmentChange(menuPosition);
    }

    @Override
    public void onSameMenuSelected() {
        mMenuFragmentListener.onSameMenuSelected();
    }

    public interface MenuFragmentListener {
        void onMenuClick(int menuId);

        void onSameMenuSelected();
    }
}
