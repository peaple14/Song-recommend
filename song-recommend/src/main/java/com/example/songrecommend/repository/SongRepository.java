package com.example.songrecommend.repository;

import com.example.songrecommend.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SongRepository extends JpaRepository<SongEntity,Long> {


}
