package com.example.movies.models;

import java.time.LocalDate;

public class Dates {

    private LocalDate maximum;
    private LocalDate minimum;

    public Dates(){}
    public Dates(LocalDate maximum, LocalDate minimum) {
        this.maximum = maximum;
        this.minimum = minimum;
    }

    public LocalDate getMaximum() {
        return maximum;
    }

    public void setMaximum(LocalDate maximum) {
        this.maximum = maximum;
    }

    public LocalDate getMinimum() {
        return minimum;
    }

    public void setMinimum(LocalDate minimum) {
        this.minimum = minimum;
    }
}
