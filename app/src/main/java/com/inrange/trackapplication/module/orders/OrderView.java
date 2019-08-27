package com.inrange.trackapplication.module.orders;

import com.inrange.trackapplication.dto.Assignment;
import com.inrange.trackapplication.dto.Order;

import java.util.List;


public interface OrderView {

    void populateOrderList(List<Order> orders);
}
