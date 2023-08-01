package com.qthang.employeeservice.command.controller;

import com.qthang.employeeservice.command.command.CreateEmployeeCommand;
import com.qthang.employeeservice.command.command.UpdateEmployeeCommand;
import com.qthang.employeeservice.command.model.EmployeeRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addEmployee(@RequestBody EmployeeRequestModel model) {
        CreateEmployeeCommand command =
                new CreateEmployeeCommand(UUID.randomUUID().toString(), model.getFirstName(), model.getLastName(), model.getKin(), false);

        commandGateway.sendAndWait(command);

        return "added employee";
    }

    @PutMapping
    public String updateEmployee(@RequestBody EmployeeRequestModel model) {
        UpdateEmployeeCommand command =
                new UpdateEmployeeCommand(model.getEmployeeId(), model.getFirstName(), model.getLastName(), model.getKin(), model.getIsDisciplined());

        commandGateway.sendAndWait(command);

        return "updated employee";
    }
}
