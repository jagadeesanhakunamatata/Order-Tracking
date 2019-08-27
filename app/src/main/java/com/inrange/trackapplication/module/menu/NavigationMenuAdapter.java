package com.inrange.trackapplication.module.menu;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.inrange.trackapplication.InRangeApplication;
import com.inrange.trackapplication.R;
import com.inrange.trackapplication.dto.MenuItemDto;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Devendran on 27/1/17.
 */
public class NavigationMenuAdapter extends RecyclerView.Adapter<NavigationMenuAdapter.ViewHolder> {

    private List<MenuItemDto> mMenuList;
    private HashMap<Integer, MenuItemDto> mMenuMap = new HashMap<>();
    private int mPrevMenuId = -1;

    private NavigationMenuAdapterListener mNavigationMenuAdapterListener;

    public NavigationMenuAdapter(List<MenuItemDto> menuItems, NavigationMenuAdapterListener navigationMenuAdapterListener) {
        mMenuList = menuItems;
        setMenuMap(menuItems);
        mNavigationMenuAdapterListener = navigationMenuAdapterListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final int currentMenuId = mMenuList.get(position).getMenuId();
        final MenuItemDto menuItemDto = mMenuMap.get(currentMenuId);
        boolean isSelected = menuItemDto.isSelected();
        Resources resources = InRangeApplication.getApplication().getResources();
        int textColor;
        if (isSelected) {
//                textColor = resources.getColor(R.color.colorPrimaryDark);
            holder.mParentLayout.setBackgroundColor(resources.getColor(R.color.guardsmanRedAlpha10));
            textColor = resources.getColor(R.color.colorPrimary);
        } else {
            holder.mParentLayout.setBackgroundColor(resources.getColor(R.color.white));
            textColor = resources.getColor(R.color.tundora);
        }
        Drawable menuDrawable = menuItemDto.getSelectedDrawable();
        holder.mMenuTv.setTextColor(textColor);
        holder.mMenuTv.setText(menuItemDto.getTitle());
        holder.mMenuIv.setImageDrawable(menuDrawable);
        holder.mMenuIv.setColorFilter(textColor);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPrevMenuId != currentMenuId) {
                    if (mPrevMenuId != -1) {
                        MenuItemDto prevMenuItemDto = mMenuMap.get(mPrevMenuId);
                        prevMenuItemDto.setSelected(false);
                        updateItem(prevMenuItemDto, mPrevMenuId);
                    }
                    menuItemDto.setSelected(true);
                    updateItem(menuItemDto, currentMenuId);
                    mNavigationMenuAdapterListener.onClickMenuItem(menuItemDto.getMenuId());
                } else {
                    mNavigationMenuAdapterListener.onSameMenuSelected();
                }
                mPrevMenuId = currentMenuId;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenuMap.values().size();
    }

    private void updateItem(MenuItemDto menuItemDto, int id) {
        mMenuMap.put(id, menuItemDto);
        notifyDataSetChanged();
    }

    public void updateMenuOnFragmentChange(int currentMenuId) {
        if (mPrevMenuId != currentMenuId) {
            if (mPrevMenuId != -1) {
                MenuItemDto prevMenuItemDto = mMenuMap.get(mPrevMenuId);
                prevMenuItemDto.setSelected(false);
                updateItem(prevMenuItemDto, mPrevMenuId);
            }
            if (currentMenuId != -1) {
                MenuItemDto menuItemDto = mMenuMap.get(currentMenuId);
                menuItemDto.setSelected(true);
                updateItem(menuItemDto, currentMenuId);
            }
        }
        mPrevMenuId = currentMenuId;
    }

    public void setInitialSelection(int menuPosition) {
        mPrevMenuId = menuPosition;
    }

    private void setMenuMap(List<MenuItemDto> menuList) {
        if (null != menuList) {
            for (MenuItemDto menuItemDto : menuList) {
                mMenuMap.put(menuItemDto.getMenuId(), menuItemDto);
            }
        }
    }


    public interface NavigationMenuAdapterListener {
        void onClickMenuItem(int menuId);

        void onSameMenuSelected();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mMenuIv;
        TextView mMenuTv;
        RelativeLayout mParentLayout;

        ViewHolder(View view, int viewType) {
            super(view);
            mMenuTv = (TextView) view.findViewById(R.id.inflater_menu_item_tv);
            mMenuIv = (ImageView) view.findViewById(R.id.inflater_menu_item_iv);
            mParentLayout = (RelativeLayout) view.findViewById(R.id.item_menu_parent_layout);
        }

    }
}