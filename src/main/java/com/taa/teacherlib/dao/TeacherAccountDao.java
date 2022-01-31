package com.taa.teacherlib.dao;

import com.taa.teacherlib.business.TeacherAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface TeacherAccountDao extends JpaRepository<TeacherAccount, Long> {

    Optional<TeacherAccount> getByUsername(String username);

    boolean existsTeacherAccountByUsername(String username);

}
