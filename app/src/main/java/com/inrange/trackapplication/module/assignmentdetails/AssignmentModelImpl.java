package com.inrange.trackapplication.module.assignmentdetails;

import com.inrange.trackapplication.CodeSnippet;
import com.inrange.trackapplication.Constants;
import com.inrange.trackapplication.dto.Assignment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AssignmentModelImpl implements AssignmentModel {

    private final AssignmentListModelListener mModelListener;

    public static AssignmentModel getInstance(AssignmentListModelListener assignmentListPresenter) {
        return new AssignmentModelImpl(assignmentListPresenter);
    }

    private AssignmentModelImpl(AssignmentListModelListener assignmentListModelListener) {

        mModelListener = assignmentListModelListener;
    }

    @Override
    public void getAssignmentList() {


        List<Assignment> assignments = new ArrayList<>();
        Calendar currentCalender = Calendar.getInstance();
//        currentCalender.add(Calendar.DATE, -3);
        currentCalender.add(Calendar.MINUTE, 30);
        Assignment assignment = new Assignment();
        assignment.setName("Job 1");
        assignment.setPriority("High");
        assignment.setStartdt(CodeSnippet.getDateStringFromDate(currentCalender.getTime(), Constants.DateFormat.SERVER_DATE_FORMAT));
        assignment.setCompleteddt(CodeSnippet.getDateStringFromDate(currentCalender.getTime(), Constants.DateFormat.SERVER_DATE_FORMAT));
        assignment.setLocation("adyar, chennai");
        assignment.setType("Glass");
        assignment.setIscancelled(false);

        currentCalender.add(Calendar.DATE, -1);
        currentCalender.add(Calendar.HOUR, -1);
        Assignment assignment1 = new Assignment();
        assignment1.setName("Job 2");
        assignment1.setIscancelled(false);
        assignment.setPriority("High");
        assignment1.setType("Document");
        assignment1.setIscompleted(true);
        assignment1.setLocation("Guindy, chennai");
        assignment1.setStartdt(CodeSnippet.getDateStringFromDate(currentCalender.getTime(), Constants.DateFormat.SERVER_DATE_FORMAT));
        assignment1.setCompleteddt(CodeSnippet.getDateStringFromDate(currentCalender.getTime(), Constants.DateFormat.SERVER_DATE_FORMAT));

        currentCalender.add(Calendar.DATE, 5);
//        assignment1.setClosedt(CodeSnippet.getDateStringFromDate(currentCalender.getTime(), Constants.DateFormat.SERVER_DATE_FORMAT));
        assignment1.setIsrecurrence(false);
        assignment1.setPriority("Medium");

        Assignment assignment3 = new Assignment();
        assignment3.setName("Job 3");
        assignment3.setIscancelled(false);
        assignment3.setType("Crystal");
        assignment3.setLocation("Saidapet, chennai");
        assignment3.setStartdt(CodeSnippet.getDateStringFromDate(currentCalender.getTime(), Constants.DateFormat.SERVER_DATE_FORMAT));
        assignment3.setPriority("Medium");

        Assignment assignment2 = new Assignment();
        assignment2.setName("Job 4");
        assignment2.setIscancelled(true);
        assignment2.setStartdt(CodeSnippet.getDateStringFromDate(currentCalender.getTime(), Constants.DateFormat.SERVER_DATE_FORMAT));
        assignment2.setCanceldt(CodeSnippet.getDateStringFromDate(Calendar.getInstance().getTime(), Constants.DateFormat.SERVER_DATE_FORMAT));
        assignment2.setLocation("Vellore");
        assignment2.setType("Book");
        assignment2.setPriority("Low");

        assignments.add(assignment);
        assignments.add(assignment1);
        assignments.add(assignment2);
        assignments.add(assignment3);

        mModelListener.gotAssignmentList(assignments);
//        SmartJoRoomDB.getAppDatabase().assignmentDao().insertFiles(assignments);
//        List<Assignment> assignments1 = SmartJoRoomDB.getAppDatabase().assignmentDao().getFileList();
    }

    public interface AssignmentListModelListener{
        void gotAssignmentList(List<Assignment> assignments);
    }
}
