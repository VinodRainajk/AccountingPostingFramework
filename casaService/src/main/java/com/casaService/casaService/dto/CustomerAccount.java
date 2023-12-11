package com.casaService.casaService.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerAccount {

    @Id
    @Column
    @GeneratedValue(strategy = SEQUENCE)
    Integer customerAccNo;

    @Column
    String customerName;

    @Column
    String photo;

}
