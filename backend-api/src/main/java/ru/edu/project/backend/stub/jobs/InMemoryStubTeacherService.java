package ru.edu.project.backend.stub.jobs;

import ru.edu.project.backend.api.teachers.Teacher;
import ru.edu.project.backend.api.teachers.TeacherService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class InMemoryStubTeacherService implements TeacherService {

    /**
     * @inheritDoc
     */
    @Override
    public List<Teacher> getAvailable() {
        return Arrays.stream(TeachersEnum.values())
                .map(TeachersEnum::getTeacher)
                .collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Teacher> getByIds(final List<Long> ids) {
        return Arrays.stream(TeachersEnum.values())
                .filter(e -> ids.contains(e.getTeacher().getId()))
                .map(TeachersEnum::getTeacher)
                .collect(Collectors.toList());
    }


    public enum TeachersEnum {

        /**
         * Отладочная услуга.
         */
        Teacher_1(1L, "Преподаватель 1"),

        /**
         * Отладочная услуга.
         */
        Teacher_2(2L, "Преподаватель 2"),

        /**
         * Отладочная услуга.
         */
        Teacher_3(3L, "Преподаватель 3"),

        /**
         * Отладочная услуга.
         */
        Teacher_4(4L, "Преподаватель 4");


        /**
         * Связанный объект.
         */
        private Teacher teacher;

        TeachersEnum(final Long code, final String title) {
            teacher = Teacher.builder()
                    .id(code)
                    .teacherName(title)
                    .build();
        }

        /**
         * Получение объекта.
         *
         * @return job
         */
        public Teacher getTeacher() {
            return teacher;
        }
    }

}
