package com.inrange.trackapplication.module.assignmentdetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.inrange.trackapplication.dto.Assignment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AssignmentRecyclerViewAdapter extends RecyclerView.Adapter<AssignmentRecyclerViewAdapter.ViewHolder> {

    private List<Assignment> mValues;
    private final AssignmentListFragment.OnListFragmentInteractionListener mListener;
    private final Context mContext;
    private List<Assignment> originalData;
    private ItemFilter mFilter = new ItemFilter();

    public AssignmentRecyclerViewAdapter(Context context, List<Assignment> items, AssignmentListFragment.OnListFragmentInteractionListener listener) {
        originalData = mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_assignment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.ctvTitle.setText(mValues.get(position).getName());
        holder.ctvType.setText(holder.mItem.getType());
        holder.ctvLocation.setText(holder.mItem.getLocation());
        if (null != holder.mItem.getStartDate()) {
            if(null != holder.mItem.getClosedDate() && holder.mItem.getIsrecurrence()) {
                holder.ctvDatetime.setText(CodeSnippet.getDateStringFromDate(holder.mItem.getStartDate(), Constants.DateFormat.ASSIGNMENT_DATETIME_FORMAT)
                        +" - "+CodeSnippet.getDateStringFromDate(holder.mItem.getClosedDate(), Constants.DateFormat.ASSIGNMENT_DATE_FORMAT));
            } else {
                holder.ctvDatetime.setText(CodeSnippet.getDateStringFromDate(holder.mItem.getStartDate(), Constants.DateFormat.ASSIGNMENT_DATETIME_FORMAT));
            }
        } else {
            holder.ctvDatetime.setText(holder.mItem.getStartdt());
        }

        if (holder.mItem.getIscancelled()) {
            holder.ctvAlert.setText("Cancelled");
            holder.ctvAlert.setTextColor(mContext.getResources().getColor(R.color.offline_color));
            holder.llStatus.setVisibility(View.VISIBLE);
            holder.llAttachments.setVisibility(View.GONE);
            holder.ctvStatusAlert.setTextColor(mContext.getResources().getColor(R.color.offline_color));
            if(null != holder.mItem.getCanceledDate()) {
                holder.ctvStatusDate.setText(CodeSnippet.getDateStringFromDate(holder.mItem.getCanceledDate(), Constants.DateFormat.ASSIGNMENT_CLOSED_DATE_FORMAT));
            }
            holder.llAlert.setVisibility(View.GONE);
            holder.llAttachments.setVisibility(View.GONE);
            holder.ctvStatusAlert.setText("cancelled on ");
            holder.btnDirections.setVisibility(View.GONE);
        } else if (holder.mItem.getIscompleted()) {
            holder.ctvAlert.setText("Completed");
            holder.llAttachments.setVisibility(View.GONE);
            holder.ctvAlert.setTextColor(mContext.getResources().getColor(R.color.availablecolor));
            holder.ctvStatusAlert.setTextColor(mContext.getResources().getColor(R.color.availablecolor));
            holder.llStatus.setVisibility(View.VISIBLE);
            holder.ctvStatusAlert.setText("completed on ");
            holder.llAttachments.setVisibility(View.GONE);
            holder.llAlert.setVisibility(View.GONE);
            holder.btnDirections.setVisibility(View.GONE);
            holder.ivChat.setVisibility(View.GONE);
            if(null != holder.mItem.getCompletedDate()) {
                holder.ctvStatusDate.setText(CodeSnippet.getDateStringFromDate(holder.mItem.getCompletedDate(), Constants.DateFormat.ASSIGNMENT_CLOSED_DATE_FORMAT));
            }
        } else {
            holder.llStatus.setVisibility(View.GONE);
            holder.llAttachments.setVisibility(View.GONE);
            holder.ctvAlert.setText("Active");
            holder.ctvAlert.setTextColor(mContext.getResources().getColor(R.color.availablecolor));
            Calendar currentCalender = Calendar.getInstance();
            currentCalender.add(Calendar.MINUTE, 30);
            if (null != holder.mItem.getStartDate() && holder.mItem.getStartDate().getTime() < currentCalender.getTimeInMillis()) {
                holder.ctvAlert.setTextColor(mContext.getResources().getColor(R.color.availablecolor));
                if (holder.mItem.getStartDate().getTime() > Calendar.getInstance().getTimeInMillis()) {
                    holder.ctvAlert.setText("Starts in " + String.valueOf((holder.mItem.getStartDate().getTime() - Calendar.getInstance().getTimeInMillis()) / 60000) + " Mins");
                    holder.btnDirections.setVisibility(View.VISIBLE);
                } else if (holder.mItem.getIsrecurrence() && null != holder.mItem.getClosedDate()
                        && holder.mItem.getStartDate().before(Calendar.getInstance().getTime()) && holder.mItem.getClosedDate().after(Calendar.getInstance().getTime())) {
                    Calendar startCalender = Calendar.getInstance();
                    startCalender.setTime(holder.mItem.getStartDate());
                    startCalender.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE));
                    if (startCalender.getTimeInMillis() < currentCalender.getTimeInMillis() && startCalender.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
                        holder.ctvAlert.setText("Starts in " + String.valueOf((startCalender.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 60000) + " Mins");
                        holder.btnDirections.setVisibility(View.VISIBLE);
                    }
                }
            } else if (null != holder.mItem.getStartDate()) {

                holder.ctvAlert.setTextColor(mContext.getResources().getColor(R.color.availablecolor));
                holder.btnDirections.setVisibility(View.GONE);
                currentCalender.add(Calendar.DATE, 1);
                if (CodeSnippet.getDateStringFromDate(holder.mItem.getStartDate(), Constants.DateFormat.DHINAMALAR_DATE_FORMAT)
                        .equals(CodeSnippet.getDateStringFromDate(currentCalender.getTime(), Constants.DateFormat.DHINAMALAR_DATE_FORMAT))) {
                    holder.ctvAlert.setText("Starts Tomorrow");
                } else {
                    holder.ctvAlert.setText("Starts " + CodeSnippet.getDateStringFromDate(currentCalender.getTime(), Constants.DateFormat.DHINAMALAR_DATE_FORMAT));
                }
            } else {
                holder.btnDirections.setVisibility(View.GONE);
            }
        }



        holder.tvPriorityText.setText(holder.mItem.getPriority());
        if (null != holder.mItem.getPriority() && holder.mItem.getPriority().toUpperCase().equals("HIGH")) {
            holder.ivPriorityIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.riskcolor), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (null != holder.mItem.getPriority() && holder.mItem.getPriority().toUpperCase().equals("MEDIUM")) {
            holder.ivPriorityIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.mediumcolor), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (null != holder.mItem.getPriority() && holder.mItem.getPriority().toUpperCase().equals("LOW")) {
            holder.ivPriorityIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.lowcolor), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        if (0.0 != holder.mItem.getDistance()) {
            holder.llLocation.setVisibility(View.VISIBLE);
            holder.ctvDistance.setText(roundTwoDecimals(holder.mItem.getDistance()) + " km");
        } else {

            holder.llLocation.setVisibility(View.GONE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.btnDirections.setTag(holder.mItem.getLocation());
        holder.btnDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + holder.btnDirections.getTag());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mContext.startActivity(mapIntent);
            }
        });
    }
    double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.#");
        return Double.valueOf(twoDForm.format(d));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView ctvTitle;
        public final LinearLayout llpriority;
        public final ImageView ivPriorityIcon;
        public final TextView tvPriorityText;
        public final TextView ctvLocation;
        public final TextView ctvType;
        public final TextView ctvDatetime;
        public final LinearLayout llAlert, llLocation, llStatus,llAttachments;
        public final TextView ctvAlert;
        public final TextView ctvDistance,ctvStatusAlert,ctvStatusDate,ctvAttachmentCount;
        public final Button btnDirections;
        public Assignment mItem;
        public final ImageView ivType,ivChat;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ctvTitle = (TextView) view.findViewById(R.id.item_assignment_list_ctv_title);
            llpriority = (LinearLayout) view.findViewById(R.id.item_assignment_list_ll_priority);
            ivPriorityIcon = (ImageView) view.findViewById(R.id.item_assignment_list_iv_priority_icon);
            tvPriorityText = (TextView) view.findViewById(R.id.item_assignment_list_tv_priority_text);
            ctvLocation = (TextView) view.findViewById(R.id.item_assignment_list_ctv_location);
            ctvType = (TextView) view.findViewById(R.id.item_assignment_list_ctv_type);
            ctvDatetime = (TextView) view.findViewById(R.id.item_assignment_list_ctv_datetime);
            llAlert = (LinearLayout) view.findViewById(R.id.item_assignment_list_ll_alert);
            llLocation = (LinearLayout) view.findViewById(R.id.item_assignment_list_ll_location);
            ctvAlert = (TextView) view.findViewById(R.id.item_assignment_list_ctv_alert);
            ctvDistance = (TextView) view.findViewById(R.id.item_assignment_list_ctv_distance);
            btnDirections = (Button) view.findViewById(R.id.item_assignment_list_btn_directions);
            ivType = (ImageView) view.findViewById(R.id.item_assignment_list_iv_type);
            llStatus = (LinearLayout) view.findViewById(R.id.item_assignment_list_ll_status);
            ctvStatusAlert = (TextView) view.findViewById(R.id.item_assignment_list_ctv_status_alert);
            ctvStatusDate = (TextView) view.findViewById(R.id.item_assignment_list_ctv_status_date);
            ctvAttachmentCount = (TextView) view.findViewById(R.id.item_assignment_ctv_attachment_count);
            llAttachments = (LinearLayout) view.findViewById(R.id.item_assignment_ll_attachments);
            ivChat = (ImageView) view.findViewById(R.id.item_assignment_list_iv_chat);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + ctvTitle.getText() + "'";
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

            final List<Assignment> list = originalData;

            int count = list.size();
            final ArrayList<Assignment> nlist = new ArrayList<Assignment>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getName();
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
            mValues = (ArrayList<Assignment>) results.values;
            notifyDataSetChanged();
        }

    }
}
