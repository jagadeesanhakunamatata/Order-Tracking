package com.inrange.trackapplication.module.assignmentdetails;

import com.inrange.trackapplication.dto.Assignment;

import java.util.List;

public class AssignmentPresenterImpl implements AssignmentPresenter,
        AssignmentModelImpl.AssignmentListModelListener {

//    private static final String TAG = AssignmentDetailsPresenterImpl.class.getSimpleName();
    private AssignmentView mAssignmentView;
    private AssignmentModel mAssignmentModel;


    private AssignmentPresenterImpl(AssignmentView assignmentView) {
        this.mAssignmentView = assignmentView;
        mAssignmentModel = AssignmentModelImpl.getInstance(this);
//        if(SLBPVTApp.getInstance().hasNetworkConnection()) {
//            mAssignmentView.showProgressbar();
            mAssignmentModel.getAssignmentList();
//        }
    }

    public static AssignmentPresenter getInstance(AssignmentView assignmentView) {
        return new AssignmentPresenterImpl(assignmentView);
    }

    @Override
    public void gotAssignmentList(List<Assignment> assignments) {
//        mAssignmentView.dismissProgressbar();
        mAssignmentView.populateAssignmentList(assignments);
    }
}
