package com.example.demo6.soap;

import com.example.demo6.entity.EmployeeEntity;
import com.example.demo6.wsdl.StoreEmployeeRequest;
import com.example.demo6.wsdl.StoreEmployeeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.Date;

public class EmployeeClient  extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(EmployeeClient.class);

    public StoreEmployeeResponse storeEmployee(EmployeeEntity employee) throws DatatypeConfigurationException {

        StoreEmployeeRequest request = new StoreEmployeeRequest();
        request.setDocumentNumber(employee.getDocumentNumber());
        request.setName(employee.getName());
        request.setLastName(employee.getLastName());
        request.setDocumentType(employee.getDocumentType());

        String dateTimeString = employee.getBirthday().toInstant().toString();

        XMLGregorianCalendar dateBirthday
                = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);
        request.setBirthday(dateBirthday);

        XMLGregorianCalendar dateLinkingDate
                = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);

        request.setLinkingDate(dateLinkingDate);
        request.setRole(employee.getRole());
        request.setSalary(BigDecimal.valueOf(employee.getSalary()));

        log.info("Requesting location for ");

        StoreEmployeeResponse response = (StoreEmployeeResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/employees", request);

        return response;
    }

}
