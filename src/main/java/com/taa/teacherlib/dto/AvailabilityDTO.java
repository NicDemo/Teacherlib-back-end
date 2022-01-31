package com.taa.teacherlib.dto;

import java.util.Date;

public class AvailabilityDTO {

    private Date start;
    private Date end;


    public AvailabilityDTO(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStartDate() {
        return start;
    }

    public void setStartDate(Date startDate) {
        this.start = startDate;
    }

    public Date getEndDate() {
        return end;
    }

    public void setEndDate(Date endDate) {
        this.end = endDate;
    }
}
