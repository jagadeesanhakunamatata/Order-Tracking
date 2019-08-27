package com.inrange.trackapplication.module.orders;

import com.inrange.trackapplication.dto.Assignment;
import com.inrange.trackapplication.dto.Order;

import java.util.List;

public class OrderPresenterImpl implements OrderPresenter, OrderModelImpl.OrderListModelListener {

//    private static final String TAG = AssignmentDetailsPresenterImpl.class.getSimpleName();
    private OrderView mOrderView;
    private OrderModel mOrderModel;


    private OrderPresenterImpl(OrderView orderView) {
        this.mOrderView = orderView;
        mOrderModel = OrderModelImpl.getInstance(this);
//        if(SLBPVTApp.getInstance().hasNetworkConnection()) {
//            mOrderView.showProgressbar();
            mOrderModel.getAssignmentList();
//        }
    }

    public static OrderPresenter getInstance(OrderView orderView) {
        return new OrderPresenterImpl(orderView);
    }

    @Override
    public void gotOrderList(List<Order> orders) {
//        mOrderView.dismissProgressbar();
        mOrderView.populateOrderList(orders);

    }
}
