package com.taa.teacherlib.business;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Availability {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Teacher teacher;

    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    public Availability(){}

    public Availability(Date start, Date end, Teacher teacher) {
        this.start = start;
        this.end= end;
        this.teacher = teacher;
    }


    public Availability(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Availability)) {
            return false;
        }
        Availability availability = (Availability) obj;
        return this.start.equals(availability.start) && this.end.equals(availability.end);
    }

    @Override
    public String toString() {
        return "Availability{" +
                "id=" + id +
                ", teacher=" + teacher +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    @JsonIgnore
    public boolean isValid() {
        Date now = new Date();
        if (this.getStart().compareTo(now) >= 0) {
            return true;
        } else {
            return false;
        }
    }
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
