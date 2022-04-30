package com.donations2go.donations2go.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Foodbank {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String numberOfVisitorsPerWeek;

    public Foodbank(String name, String numberOfVisitorsPerWeek) {
        this.name = name;
        this.numberOfVisitorsPerWeek = numberOfVisitorsPerWeek;
    }

    public Foodbank() {

    }
}
