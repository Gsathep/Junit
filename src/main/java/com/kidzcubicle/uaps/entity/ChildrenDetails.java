package com.kidzcubicle.uaps.entity;

import com.kidzcubicle.uaps.entity.enumeration.SessionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "children_details")
public class ChildrenDetails extends AbstractAuditingEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "postcode", nullable = false)
    private String postcode;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "medical_info")
    private String medicalInfo;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "available_minutes")
    private Integer availableMinutes;

    @Column(name = "session_start_time")
    private LocalTime sessionStartTime;

    @Column(name = "session_end_time")
    private LocalTime sessionEndTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_status")
    private SessionStatus sessionStatus;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;
}
