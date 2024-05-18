package com.example.songrecommend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "song_list")
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title; // 노래이름

    @Column
    private String artist; // 작곡가

    @Column
    private String gender; // 성별

    @Column
    private String lowest_note; // 최저음

    @Column
    private String highest_note; // 최고음

    @Column
    private long lowest_int; // 최저음 정수값

    @Column
    private long highest_int; // 최고음 정수값




}
