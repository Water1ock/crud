package com.crud.crud.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "provider")
public class Provider {
    @Id
    @Column(name = "providerName")
    private String providerName;
    @Column(name = "flowName")
    private String flowName;
    @Column(name = "downFrom")
    private LocalDateTime downFrom;
    @Column(name = "downTo")
    private LocalDateTime downTo;
}
