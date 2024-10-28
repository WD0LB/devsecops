package com.walidlaanibi.devsecops.controller;

import com.walidlaanibi.devsecops.model.EmpWL;
import com.walidlaanibi.devsecops.repo.EmpRepWL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmpControllerWL {
    @Autowired
    private EmpRepWL empRepWL;

    @GetMapping("/emp")
    public List<EmpWL> getAllEmp() {
        return empRepWL.findAll();
    }
    @GetMapping("/emp/{id}")
    public ResponseEntity<EmpWL> getEmployeeById(@PathVariable(value = "id") Long empId) throws ResourceNotFoundException {
            EmpWL empWL = empRepWL.findById(empId)
                    .orElseThrow(() -> new ResourceNotFoundException("Emp not found"));
            return ResponseEntity.ok().body(empWL);
    }
    @PostMapping("/emp")
    public EmpWL createEmp(@RequestBody EmpWL empWL) {
        return empRepWL.save(empWL);
    }
    @PutMapping("/emp/{id}")
    public ResponseEntity<EmpWL> updateEmp(@PathVariable(value = "id") long empId, @RequestBody EmpWL empWL) throws ResourceNotFoundException {
        EmpWL emp = empRepWL.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("not fou"));
        emp.setEmail(empWL.getEmail());
        emp.setFullName(empWL.getFullName());
        final EmpWL empfinal = empRepWL.save(emp);
        return ResponseEntity.ok().body(empfinal);
    }
    @DeleteMapping("/emp/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Long empId) throws ResourceNotFoundException{
        EmpWL emp = empRepWL.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
        empRepWL.delete(emp);
        Map<String, Boolean> response = new HashMap<> ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
