
package com.inrange.trackapplication.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.inrange.trackapplication.CodeSnippet;
import com.inrange.trackapplication.Constants;

import java.util.Date;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "assignmentid",
        "userid",
        "name",
        "priority",
        "isrecurrence",
        "location",
        "type",
        "startdt",
        "closedt",
        "canceldt",
        "completeddt",
        "iscancelled",
        "iscompleted",
        "cancelledby",
        "cancelledreason",
        "assignedby"
})
public class Assignment implements Parcelable {

    @JsonProperty("assignmentid")
    private String assignmentid;
    @JsonProperty("userid")
    private String userid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("priority")
    private String priority;
    @JsonProperty("isrecurrence")
    private boolean isrecurrence;
    @JsonProperty("location")
    private String location;
    @JsonProperty("type")
    private String type;
    @JsonProperty("startdt")
    private String startdt;
    @JsonProperty("closedt")
    private String closedt;
    @JsonProperty("canceldt")
    private String canceldt;
    @JsonProperty("completeddt")
    private String completeddt;
    @JsonProperty("iscancelled")
    private boolean iscancelled;
    @JsonProperty("iscompleted")
    private boolean iscompleted;
    @JsonProperty("cancelledby")
    private String cancelledby;
    @JsonProperty("cancelledreason")
    private String cancelledreason;
    @JsonProperty("assignedby")
    private String assignedby;
    private double distance;
    private String timeMessage, distanceMessage;

    public Assignment(){

    }
    protected Assignment(Parcel in) {
        assignmentid = in.readString();
        userid = in.readString();
        name = in.readString();
        priority = in.readString();
        isrecurrence = in.readByte() != 0;
        location = in.readString();
        type = in.readString();
        startdt = in.readString();
        closedt = in.readString();
        canceldt = in.readString();
        completeddt = in.readString();
        iscancelled = in.readByte() != 0;
        iscompleted = in.readByte() != 0;
        cancelledby = in.readString();
        cancelledreason = in.readString();
        assignedby = in.readString();
        distance = in.readDouble();
    }

    public static final Creator<Assignment> CREATOR = new Creator<Assignment>() {
        @Override
        public Assignment createFromParcel(Parcel in) {
            return new Assignment(in);
        }

        @Override
        public Assignment[] newArray(int size) {
            return new Assignment[size];
        }
    };

    @JsonProperty("assignmentid")
    public String getAssignmentid() {
        return assignmentid;
    }

    @JsonProperty("assignmentid")
    public void setAssignmentid(String assignmentid) {
        this.assignmentid = assignmentid;
    }

    @JsonProperty("userid")
    public String getUserid() {
        return userid;
    }

    @JsonProperty("userid")
    public void setUserid(String userid) {
        this.userid = userid;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("priority")
    public String getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @JsonProperty("isrecurrence")
    public boolean getIsrecurrence() {
        return isrecurrence;
    }

    @JsonProperty("isrecurrence")
    public void setIsrecurrence(boolean isrecurrence) {
        this.isrecurrence = isrecurrence;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("startdt")
    public String getStartdt() {
        return startdt;
    }

    @JsonProperty("startdt")
    public void setStartdt(String startdt) {
        this.startdt = startdt;
    }

    @JsonProperty("closedt")
    public String getClosedt() {
        return closedt;
    }

    @JsonProperty("closedt")
    public void setClosedt(String closedt) {
        this.closedt = closedt;
    }

    @JsonProperty("canceldt")
    public String getCanceldt() {
        return canceldt;
    }

    @JsonProperty("canceldt")
    public void setCanceldt(String canceldt) {
        this.canceldt = canceldt;
    }

    @JsonProperty("completeddt")
    public String getCompleteddt() {
        return completeddt;
    }

    @JsonProperty("completeddt")
    public void setCompleteddt(String completeddt) {
        this.completeddt = completeddt;
    }

    @JsonProperty("iscancelled")
    public boolean getIscancelled() {
        return iscancelled;
    }

    @JsonProperty("iscancelled")
    public void setIscancelled(boolean iscancelled) {
        this.iscancelled = iscancelled;
    }

    @JsonProperty("iscompleted")
    public boolean getIscompleted() {
        return iscompleted;
    }

    @JsonProperty("iscompleted")
    public void setIscompleted(boolean iscompleted) {
        this.iscompleted = iscompleted;
    }

    @JsonProperty("cancelledby")
    public String getCancelledby() {
        return cancelledby;
    }

    @JsonProperty("cancelledby")
    public void setCancelledby(String cancelledby) {
        this.cancelledby = cancelledby;
    }

    @JsonProperty("cancelledreason")
    public String getCancelledreason() {
        return cancelledreason;
    }

    @JsonProperty("cancelledreason")
    public void setCancelledreason(String cancelledreason) {
        this.cancelledreason = cancelledreason;
    }

    @JsonProperty("assignedby")
    public String getAssignedby() {
        return assignedby;
    }

    @JsonProperty("assignedby")
    public void setAssignedby(String assignedby) {
        this.assignedby = assignedby;
    }

    public Date getStartDate() {
        if(startdt != null){
            return CodeSnippet.getDateFromDateString(startdt, Constants.DateFormat.SERVER_DATE_FORMAT);
        }
        return null;
    }

    public Date getClosedDate() {
        if(closedt != null){
            return CodeSnippet.getDateFromDateString(closedt, Constants.DateFormat.SERVER_DATE_FORMAT);
        }
        return null;
    }


    public Date getCanceledDate() {
        if(canceldt != null){
            return CodeSnippet.getDateFromDateString(canceldt, Constants.DateFormat.SERVER_DATE_FORMAT);
        }
        return null;
    }

    public Date getCompletedDate() {
        if(completeddt != null){
            return CodeSnippet.getDateFromDateString(completeddt, Constants.DateFormat.SERVER_DATE_FORMAT);
        }
        return null;
    }


    public void setDistance(double distance) {

        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(assignmentid);
        dest.writeString(userid);
        dest.writeString(name);
        dest.writeString(priority);
        dest.writeByte((byte) (isrecurrence ? 1 : 0));
        dest.writeString(location);
        dest.writeString(type);
        dest.writeString(startdt);
        dest.writeString(closedt);
        dest.writeString(canceldt);
        dest.writeString(completeddt);
        dest.writeByte((byte) (iscancelled ? 1 : 0));
        dest.writeByte((byte) (iscompleted ? 1 : 0));
        dest.writeString(cancelledby);
        dest.writeString(cancelledreason);
        dest.writeString(assignedby);
        dest.writeDouble(distance);
    }

    public String getTimeMessage() {
        return timeMessage;
    }

    public void setTimeMessage(String timeMessage) {
        this.timeMessage = timeMessage;
    }

    public String getDistanceMessage() {
        return distanceMessage;
    }

    public void setDistanceMessage(String distanceMessage) {
        this.distanceMessage = distanceMessage;
    }
}