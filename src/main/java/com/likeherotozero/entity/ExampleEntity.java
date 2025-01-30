package com.likeherotozero.entity;

import javax.persistence.*;

@Entity
@Table(name = "example_table")
public class ExampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data")
    private String data;

    public ExampleEntity() {}

    public ExampleEntity(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}