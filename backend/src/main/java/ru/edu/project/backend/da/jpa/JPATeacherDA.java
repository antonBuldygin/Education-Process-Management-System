package ru.edu.project.backend.da.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.teachers.Teacher;
import ru.edu.project.backend.da.TeachersDALayer;
import ru.edu.project.backend.da.jpa.converter.TeacherMapper;
import ru.edu.project.backend.da.jpa.entity.TeacherEntity;
import ru.edu.project.backend.da.jpa.entity.TeacherLinkEntity;
import ru.edu.project.backend.da.jpa.repository.TeacherEntityRepository;
import ru.edu.project.backend.da.jpa.repository.TeacherLinkEntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//import java.util.stream.Collectors;

@Service
@Profile("SPRING_DATA")
public class JPATeacherDA implements TeachersDALayer {

    /**
     * Зависимость на репозиторий.
     */
    @Autowired
    private TeacherEntityRepository teacherRepo;

    /**
     * Зависимость на репозиторий.
     */
    @Autowired
    private TeacherLinkEntityRepository linkRepo;

    /**
     * Зависимость на маппер.
     */
    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * Связывание группы и учителя.
     *
     * @param groupId
     * @param teacherId
     */
    @Override
    public void linkRequest(final long groupId, final long teacherId) {
        TeacherLinkEntity entityDraft = new TeacherLinkEntity();
        entityDraft.setPk(TeacherLinkEntity.pk(groupId, teacherId));
        entityDraft.setJob(getTeacherEntityById(teacherId));

        linkRepo.save(entityDraft);
    }

    private TeacherEntity getTeacherEntityById(final Long teacherId) {
        Optional<TeacherEntity> teacherEntity = teacherRepo.findById(teacherId);
        if (!teacherEntity.isPresent()) {
            throw new RuntimeException("Job not found by jobId " + teacherId);
        }
        return teacherEntity.get();
    }


    /**
     * Получение услуги по id.
     *
     * @param id
     * @return job
     */
    @Override
    public Teacher getById(final Long id) {
        return teacherMapper.map(getTeacherEntityById(id));
    }

    /**
     * Получение списка услуг по ids.
     *
     * @param ids
     * @return list job
     */
    @Override
    public List<Teacher> getByIds(final List<Long> ids) {
        return teacherMapper.map(teacherRepo.findAllById(ids));
    }

    /**
     * Получение списка доступных услуг.
     *
     * @return list job
     */
    @Override
    public List<Teacher> getAvailable() {
        return teacherMapper.map(teacherRepo.findAllByEnabled(true));
    }

    /**
     * Получение списка услуг заявки по requestId.
     *
     * @param requestId
     * @return list job
     */
    @Override
    public List<Teacher> getLinksByRequestId(final long requestId) {
        List<TeacherLinkEntity> jobsEntities = linkRepo.findAllByPkRequestId(requestId);
        List<TeacherEntity> list = new ArrayList<>();
        for (TeacherLinkEntity jobsEntity : jobsEntities) {
            TeacherEntity job = jobsEntity.getJob();
            list.add(job);
        }
        return teacherMapper.map(list);
    }
}
