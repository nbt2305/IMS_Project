package com.example.g2_se1630_swd392.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Trung Nguyễn Bá
 * @created 9/27/2023
 * @project IMS_G2_SWD392
 */

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
}
