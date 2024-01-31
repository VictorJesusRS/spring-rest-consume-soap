package com.example.demo6.service;

import com.example.demo6.dto.EmployeeResponseStoredDto;
import com.example.demo6.entity.EmployeeEntity;
import com.example.demo6.repository.EmployeeRepository;
import com.example.demo6.soap.EmployeeClient;
import com.example.demo6.wsdl.StoreEmployeeResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Getter
@Setter
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeClient employeeClient;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeClient employeeClient) {
        this.employeeRepository = employeeRepository;
        this.employeeClient = employeeClient;
    }

    public EmployeeResponseStoredDto store(EmployeeEntity employee) throws DatatypeConfigurationException {
            //EmployeeEntity stored = this.employeeRepository.save(employee);
            //System.out.println(stored.toString());

        String yearsOld = this.getDiffDateFull(this.getDiffDate(employee.getBirthday()));

        try{

            StoreEmployeeResponse response = employeeClient.storeEmployee(employee);

            System.out.println(response.isSuccess());
            return new EmployeeResponseStoredDto(
                    this.getDiffDateFull(this.getDiffDate(employee.getLinkingDate())),
                    yearsOld
            );
        }catch (RuntimeException e) {
            throw new RuntimeException("Employee store service failed");
        }
    }

    public Period getDiffDate(Date date) {
        Date today = new Date();
        LocalDate dateLocalDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate todayLocalDate = today.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return Period.between(dateLocalDate, todayLocalDate);
    }

    public String getDiffDateFull(Period period) {
        return period.getYears() + " years, " + period.getMonths() + " months, " + period.getDays() + " days";
    }

    public int  getDiffYears(Period period) {
        return period.getYears();
    }

    public boolean isAdult(Date date) {
        int yearsOld = this.getDiffYears(this.getDiffDate(date));
        return yearsOld >= 18;
    }

}
