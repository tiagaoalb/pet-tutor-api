package com.peti9engineeringlab.repositories;

import com.peti9engineeringlab.model.Tutor;
import lombok.Data;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Data
public class FakeTutorRepository implements TutorRepository {

    private final List<Tutor> tutors = new ArrayList<>();

    @Override
    public List<Tutor> findByNameContainingIgnoreCase(String name) {
        return tutors.stream()
                .filter(pet -> pet.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }

    public void addTutor(Tutor tutor) {
        tutors.add(tutor);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Tutor> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Tutor> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Tutor> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Tutor getOne(Long aLong) {
        return null;
    }

    @Override
    public Tutor getById(Long aLong) {
        return null;
    }

    @Override
    public Tutor getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Tutor> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Tutor> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Tutor> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Tutor> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Tutor> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Tutor> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Tutor, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Tutor> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Tutor> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Tutor> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Tutor> findAll() {
        return null;
    }

    @Override
    public List<Tutor> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Tutor entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Tutor> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Tutor> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Tutor> findAll(Pageable pageable) {
        return null;
    }
}