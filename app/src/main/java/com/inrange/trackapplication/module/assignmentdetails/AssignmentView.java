package com.inrange.trackapplication.module.assignmentdetails;

import com.inrange.trackapplication.dto.Assignment;

import java.util.List;


public interface AssignmentView  {

    void populateAssignmentList(List<Assignment> assignments);
}
