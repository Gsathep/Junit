package com.kidzcubicle.uaps.web.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class ParentDTO implements Serializable {

    private Long id;

    private String name;

    @JsonAlias({"phone_number"})
    private String phoneNumber;

    private String email;

    private boolean active = true;

    private String createdBy;

    private Instant createdDate = Instant.now();

    private String lastModifiedBy;

    private Instant lastModifiedDate = Instant.now();

    @JsonAlias({"sub"})
    private String subId;

}
