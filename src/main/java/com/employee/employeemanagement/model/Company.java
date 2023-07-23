package com.employee.employeemanagement.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Company {
    private String name ="The circle";
    private String description="We are a start up to make your life easier, we  develop a modern solution" +
            " to share money ";
    private String slogan="Time is money";
    private String address="LOT IVE 7 TSIAZOTAFO STAND N° C 403";
    private String email="thecircle@circle.com";
    private String phoneNumber="+261347895241, +261324598763, +261339678512";
    private String NIF="3002458203";
    private String STAT="46101112016010800";
    private String RCS="2016B00791";

}
