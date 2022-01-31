package com.taa.teacherlib.dao;

import com.taa.teacherlib.business.Availability;
import com.taa.teacherlib.business.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AvailabilityDao extends JpaRepository<Availability, Long> {

     Optional<List<Availability>> getAvailabilityByTeacherIdAndStartIsAfter(Long id, Date now);
}
