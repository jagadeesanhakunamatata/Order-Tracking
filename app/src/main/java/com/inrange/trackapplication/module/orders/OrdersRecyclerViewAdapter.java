package com.inrange.trackapplication.module.orders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inrange.trackapplication.CodeSnippet;
import com.inrange.trackapplication.Constants;
import com.inrange.trackapplication.R;
import com.inrange.trackapplication.dto.Order;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrdersRecyclerViewAdapter extends RecyclerView.Adapter<OrdersRecyclerViewAdapter.ViewHolder> {

    private List<Order> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;
    private List<Order> originalData;
    private ItemFilter mFilter = new ItemFilter();

    public OrdersRecyclerViewAdapter(Context context, List<Order> items, OnListFragmentInteractionListener listener) {
        originalData = mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvTitle.setText(mValues.get(position).getDescription());
        holder.tvFrom.setText(mValues.get(position).getFromAddress());
        holder.tvTo.setText(mValues.get(position).getToAddress());
        if (null != holder.mItem.getAddressTo() && null != holder.mItem.getAddressTo().getName()) {
            holder.tvToName.setText(holder.mItem.getAddressTo().getName());
        }
        if (null != holder.mItem.getAddressFrom() && null != holder.mItem.getAddressFrom().getName()) {
            holder.tvFromName.setText(holder.mItem.getAddressFrom().getName());
        }

        if (holder.mItem.getIscancelled()) {
            if (null != holder.mItem.getCanceledDate()) {
                holder.tvStatusDate.setText(CodeSnippet.getDateStringFromDate(holder.mItem.getCanceledDate(), Constants.DateFormat.ASSIGNMENT_CLOSED_DATE_FORMAT));
            }
            holder.llCallDirections.setVisibility(View.GONE);
            holder.llTransit.setVisibility(View.GONE);
            holder.tvStatus.setText("cancelled on ");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.offline_color));
            holder.llCompleted.setVisibility(View.VISIBLE);
        } else if (holder.mItem.getIscompleted()) {
            if (null != holder.mItem.getCompletedDate()) {
                holder.tvStatusDate.setText(CodeSnippet.getDateStringFromDate(holder.mItem.getCompletedDate(), Constants.DateFormat.ASSIGNMENT_CLOSED_DATE_FORMAT));
            }
            holder.llCallDirections.setVisibility(View.GONE);
            holder.llTransit.setVisibility(View.GONE);
            holder.tvStatus.setText("Completed on ");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.availablecolor));
            holder.llCompleted.setVisibility(View.VISIBLE);
        } else {
            holder.llCallDirections.setVisibility(View.VISIBLE);
            holder.llTransit.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(holder.mItem.getStatus());
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.offline_color));
            holder.llCompleted.setVisibility(View.GONE);
        }

        if (null != holder.mItem.getPriority() && holder.mItem.getPriority().toUpperCase().equals("HIGH")) {
            holder.vwStatus.setBackgroundColor(mContext.getResources().getColor(R.color.riskcolor));
        } else if (null != holder.mItem.getPriority() && holder.mItem.getPriority().toUpperCase().equals("MEDIUM")) {
            holder.vwStatus.setBackgroundColor(mContext.getResources().getColor(R.color.mediumcolor));
        } else if (null != holder.mItem.getPriority() && holder.mItem.getPriority().toUpperCase().equals("LOW")) {
            holder.vwStatus.setBackgroundColor(mContext.getResources().getColor(R.color.lowcolor));
        }

        if (0.0 != holder.mItem.getDistance()) {
            holder.tvDistance.setText(roundTwoDecimals(holder.mItem.getDistance()) + " km");
        } else {
            holder.tvDistance.setVisibility(View.GONE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.llDirections.setTag(holder.mItem);
        holder.llDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + ((Order) holder.llDirections.getTag()).getAddressTo().getAddress());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mContext.startActivity(mapIntent);
            }
        });
        holder.llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != holder.mItem && null != holder.mItem.getAddressTo() && null != holder.mItem.getAddressTo().getPhone()) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + holder.mItem.getAddressTo().getPhone()));//change the number
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mContext.startActivity(callIntent);
                }
            }
        });
    }

    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.#");
        return Double.valueOf(twoDForm.format(d));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView, vwStatus;
        public final TextView tvTitle, tvFrom, tvTo, tvDistance, tvStatusDate, tvStatus, tvFromName, tvToName;
        public final LinearLayout llLocations, llTransit, llCompleted, llCallDirections, llCall, llDirections;
        public Order mItem;
        public final ImageView ivBreak, ivDrug;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            vwStatus = (View) view.findViewById(R.id.item_order_vw_status);
            tvTitle = (TextView) view.findViewById(R.id.item_order_ctv_title);
            ivBreak = (ImageView) view.findViewById(R.id.item_order_iv_break);
            ivDrug = (ImageView) view.findViewById(R.id.item_order_iv_drug);
            llLocations = (LinearLayout) view.findViewById(R.id.item_order_ll_locations);
            tvFrom = (TextView) view.findViewById(R.id.item_order_tv_from);
            tvTo = (TextView) view.findViewById(R.id.item_order_tv_to);
            tvFromName = (TextView) view.findViewById(R.id.item_order_tv_from_name);
            tvToName = (TextView) view.findViewById(R.id.item_order_tv_to_name);
            llTransit = (LinearLayout) view.findViewById(R.id.item_order_ll_transit);
            tvDistance = (TextView) view.findViewById(R.id.item_order_tv_distance);
            llCompleted = (LinearLayout) view.findViewById(R.id.item_order_ll_completed);
            tvStatus = (TextView) view.findViewById(R.id.item_order_tv_status);
            tvStatusDate = (TextView) view.findViewById(R.id.item_order_tv_statusdate);
            llCallDirections = (LinearLayout) view.findViewById(R.id.item_order_ll_calldirections);
            llCall = (LinearLayout) view.findViewById(R.id.item_order_ll_call);
            llDirections = (LinearLayout) view.findViewById(R.id.item_order_ll_directions);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitle.getText() + "'";
        }
    }


    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Order> list = originalData;

            int count = list.size();
            final ArrayList<Order> nlist = new ArrayList<Order>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getDescription();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mValues = (ArrayList<Order>) results.values;
            notifyDataSetChanged();
        }

    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Order item);
    }
}
