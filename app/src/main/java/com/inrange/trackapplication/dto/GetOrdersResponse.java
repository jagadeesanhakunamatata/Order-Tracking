
package com.inrange.trackapplication.dto;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "order",
    "totalElements",
    "totalPages",
    "last",
    "sort",
    "first",
    "numberOfElements",
    "size",
    "number"
})
public class GetOrdersResponse  implements Serializable {

    @JsonProperty("content")
    private List<Order> content = null;
    @JsonProperty("totalElements")
    private Integer totalElements;
    @JsonProperty("totalPages")
    private Integer totalPages;
    @JsonProperty("last")
    private Boolean last;
    @JsonProperty("sort")
    private Object sort;
    @JsonProperty("first")
    private Boolean first;
    @JsonProperty("numberOfElements")
    private Integer numberOfElements;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("number")
    private Integer number;

    @JsonProperty("content")
    public List<Order> getOrder() {
        return content;
    }

    @JsonProperty("content")
    public void setOrder(List<Order> order) {
        this.content = order;
    }

    @JsonProperty("totalElements")
    public Integer getTotalElements() {
        return totalElements;
    }

    @JsonProperty("totalElements")
    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    @JsonProperty("totalPages")
    public Integer getTotalPages() {
        return totalPages;
    }

    @JsonProperty("totalPages")
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @JsonProperty("last")
    public Boolean getLast() {
        return last;
    }

    @JsonProperty("last")
    public void setLast(Boolean last) {
        this.last = last;
    }

    @JsonProperty("sort")
    public Object getSort() {
        return sort;
    }

    @JsonProperty("sort")
    public void setSort(Object sort) {
        this.sort = sort;
    }

    @JsonProperty("first")
    public Boolean getFirst() {
        return first;
    }

    @JsonProperty("first")
    public void setFirst(Boolean first) {
        this.first = first;
    }

    @JsonProperty("numberOfElements")
    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    @JsonProperty("numberOfElements")
    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    @JsonProperty("number")
    public Integer getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(Integer number) {
        this.number = number;
    }

}
