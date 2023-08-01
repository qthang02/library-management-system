package com.qthang.employeeservice.query.controller;


import com.qthang.employeeservice.query.model.EmployeeResponseModel;
import com.qthang.employeeservice.query.queries.GetAllEmployeeQuery;
import com.qthang.employeeservice.query.queries.GetEmployeesQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{employeeId}")
    public EmployeeResponseModel getEmployeeDetail(@PathVariable String employeeId) {
        GetEmployeesQuery getEmployeesQuery = new GetEmployeesQuery();
        getEmployeesQuery.setEmployeeId(employeeId);

        EmployeeResponseModel employeeReponseModel =
                queryGateway.query(getEmployeesQuery,
                                ResponseTypes.instanceOf(EmployeeResponseModel.class))
                        .join();

        return employeeReponseModel;
    }
    @GetMapping
    public List<EmployeeResponseModel> getAllEmployee(){
        List<EmployeeResponseModel> list = queryGateway.query(new GetAllEmployeeQuery(), ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class))
                .join();
        return list;
    }
}
