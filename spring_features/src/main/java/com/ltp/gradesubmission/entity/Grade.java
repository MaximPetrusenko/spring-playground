package com.ltp.gradesubmission.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;
    @Column (name = "score", nullable = false)
    private String score;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "id", name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "id", name = "grade_id")
    private Course course;

}
