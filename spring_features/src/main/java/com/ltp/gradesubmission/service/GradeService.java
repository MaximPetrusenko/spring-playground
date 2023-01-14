package com.ltp.gradesubmission.service;

import com.ltp.gradesubmission.entity.Grade;
import jakarta.validation.Valid;

import java.util.List;

public interface GradeService {
    Grade getGrade(Long studentId, Long courseId);
    Grade saveGrade(Grade grade, Long studentId, Long courseId);
    Grade updateGrade(String score, Long studentId, Long courseId);
    void deleteGrade(Long studentId, Long courseId);
    List<Grade> getStudentGrades(Long studentId);
    List<Grade> getCourseGrades(Long courseId);
    List<Grade> getAllGrades();
    Grade getGradeById(String gradeId);

    void submitForm(com.ltp.gradesubmission.pojo.@Valid Grade grade);
}
