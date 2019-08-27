package com.inrange.trackapplication.module.orders;

import com.inrange.trackapplication.CodeSnippet;
import com.inrange.trackapplication.Constants;
import com.inrange.trackapplication.dto.AddressFrom;
import com.inrange.trackapplication.dto.AddressTo;
import com.inrange.trackapplication.dto.Assignment;
import com.inrange.trackapplication.dto.Order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class OrderModelImpl implements OrderModel {

    private final OrderListModelListener mModelListener;

    public static OrderModel getInstance(OrderListModelListener orderListPresenter) {
        return new OrderModelImpl(orderListPresenter);
    }

    private OrderModelImpl(OrderListModelListener orderListModelListener) {

        mModelListener = orderListModelListener;
    }

    @Override
    public void getAssignmentList() {

Random random = new Random();
        List<Order> orders = new ArrayList<>();
        for(double i=0;i<5;i++) {
            Order order = new Order();
            order.setDescription("Description for Job "+i);
            order.setPriority("High");
            AddressFrom from = new AddressFrom();
            from.setAddress("Valluvan Street");
            from.setCity("chennai");
            from.setCountry("India");
            from.setPostalCode(600011);
            from.setName("Anand");
            from.setPhone("9879879878");
            from.setLatitude(13.0012 + random.nextFloat()/100);
            from.setLongitude(80.2565 + random.nextFloat()/100);

            AddressTo to = new AddressTo();
            to.setAddress("Valluvan Street");
            to.setCity("chennai");
            to.setCountry("India");
            to.setPostalCode(600011);
            to.setName("Anand");
            to.setPhone("9879879878");
            to.setLatitude(13.0012 + random.nextFloat()/100);
            to.setLongitude(80.2565 + random.nextFloat()/100);
            order.setAddressFrom(from);
            order.setAddressTo(to);

            orders.add(order);
        }
        orders.get(1).setPriority("Medium");
        orders.get(2).setPriority("Low");
        orders.get(3).setStatus(Constants.Status.COMPLETED);
        orders.get(4).setStatus(Constants.Status.COMPLETED);

        mModelListener.gotOrderList(orders);
//        SmartJoRoomDB.getAppDatabase().orderDao().insertFiles(orders);
//        List<Assignment> orders1 = SmartJoRoomDB.getAppDatabase().orderDao().getFileList();
    }

    public interface OrderListModelListener{
        void gotOrderList(List<Order> orders);
    }
}
