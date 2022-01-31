package com.taa.teacherlib.dao;

import com.taa.teacherlib.business.StudentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface StudentAccountDao extends JpaRepository<StudentAccount, Long> {

    Optional<StudentAccount> getByUsername(String username);

    boolean existsStudentAccountByUsername(String username);


}
