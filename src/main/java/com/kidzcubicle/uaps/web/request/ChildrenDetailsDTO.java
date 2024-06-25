package com.kidzcubicle.uaps.web.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kidzcubicle.uaps.entity.enumeration.SessionStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ChildrenDetailsDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate dob;

    private String address;

    private String postcode;

    private String city;

    private String country;

    private String medicalInfo;

    private byte[] picture;

    private String gender;

    private Integer availableMinutes;

    private LocalTime sessionStartTime;

    private LocalTime sessionEndTime;

    private SessionStatus sessionStatus;


    private Boolean deleted = false;

    private String createdBy;

    private Instant createdDate = Instant.now();

    private String lastModifiedBy;

    private Instant lastModifiedDate = Instant.now();

    private Long parentId;


}
