package com.example.g2_se1630_swd392.repository;

import com.example.g2_se1630_swd392.entity.Subject;
import com.example.g2_se1630_swd392.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends BaseRepository<User, Integer> {
//    User findByUsernameAndActiveTrue(String username);
    User findByEmailAndActiveTrue(String email);
    Optional<User> findByIdAndActiveTrue(Integer id);
    Optional<User> findByUsernameAndActiveTrue(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User findByIdAndRoleId(Integer id, Integer roleId);
    int countByIdInAndRoleId(List<Integer> ids, Integer roleId);
    List<User> findAllByIdIn(List<Integer> ids);
    List<User> findByActiveTrue();
    @Query("select u from User u join ClassStudent ct on ct.studentId = u.id where ct.classId = ?1")
    List<User> getAllStudentsByClassId(Integer classId);
}
