
package com.inrange.trackapplication.dto;

import android.support.constraint.ConstraintLayout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.inrange.trackapplication.CodeSnippet;
import com.inrange.trackapplication.Constants;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "date",
    "description",
    "price",
    "status",
    "addressFrom",
    "addressTo",
    "courier",
    "customer"
})
public class Order implements Serializable {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("date")
    private BigInteger date;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("status")
    private String status;
    @JsonProperty("addressFrom")
    private AddressFrom addressFrom;
    @JsonProperty("addressTo")
    private AddressTo addressTo;
    @JsonProperty("courier")
    private Courier courier;
    @JsonProperty("customer")
    private Customer customer;
    private String canceldt;
    private String completeddt;
    private double distance;
    private String distanceMessage;
    private String priority;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("date")
    public BigInteger getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(BigInteger date) {
        this.date = date;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("price")
    public Integer getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Integer price) {
        this.price = price;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("addressFrom")
    public AddressFrom getAddressFrom() {
        return addressFrom;
    }

    @JsonProperty("addressFrom")
    public void setAddressFrom(AddressFrom addressFrom) {
        this.addressFrom = addressFrom;
    }

    @JsonProperty("addressTo")
    public AddressTo getAddressTo() {
        return addressTo;
    }

    @JsonProperty("addressTo")
    public void setAddressTo(AddressTo addressTo) {
        this.addressTo = addressTo;
    }

    @JsonProperty("courier")
    public Courier getCourier() {
        return courier;
    }

    @JsonProperty("courier")
    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @JsonProperty("customer")
    public Customer getCustomer() {
        return customer;
    }

    @JsonProperty("customer")
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean getIscancelled() {
        return null != getStatus() && getStatus().toLowerCase().equals(Constants.Status.CANCELLED.toLowerCase());
    }

    public boolean getIscompleted() {
        return null != getStatus() && getStatus().toLowerCase().equals(Constants.Status.COMPLETED.toLowerCase());
    }

    public String getCanceleddt() {
        return null;
    }

    public String getCompleteddt() {
        return null;
    }

    public String getPriority() {
        return priority;
    }

    public double getDistance() {
        return distance;
    }

    public String getFromAddress() {
        String address = "";
        if (null != addressFrom) {
            if (null != addressFrom.getName()) {
                address = addressFrom.getName()+",";
            }
            if (null != addressFrom.getAddress()) {
                address = addressFrom.getAddress()+",";
            }
            if (null != addressFrom.getCity()) {
                address += "\n" + addressFrom.getCity()+",";
            }
            if (null != addressFrom.getCountry()) {
                address += "\n" + addressFrom.getCountry();
            }
            if (null != addressFrom.getPostalCode()) {
                address += "-" + addressFrom.getPostalCode();
            }
//            if (null != addressFrom.getPhone()) {
//                address += "\n" + addressFrom.getPhone();
//            }
        }
        return address;
    }

    public String getToAddress() {
        String address = "";
        if (null != addressTo) {
            if (null != addressTo.getName()) {
                address = addressTo.getName()+",";
            }
            if (null != addressTo.getAddress()) {
                address = addressTo.getAddress()+",";
            }
            if (null != addressTo.getCity()) {
                address += "\n" + addressTo.getCity()+",";
            }
            if (null != addressTo.getCountry()) {
                address += "\n" + addressTo.getCountry();
            }
            if (null != addressTo.getPostalCode()) {
                address += "-" + addressTo.getPostalCode();
            }
//            if (null != addressTo.getPhone()) {
//                address += "\n" + addressTo.getPhone();
//            }
        }
        return address;
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
        this.distance =distance;
    }

    public String getDistanceMessage() {
        return distanceMessage;
    }

    public void setDistanceMessage(String distanceMessage) {
        this.distanceMessage = distanceMessage;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
