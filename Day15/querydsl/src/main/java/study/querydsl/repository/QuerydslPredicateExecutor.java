package study.querydsl.repository;

import com.querydsl.core.types.Predicate;

import java.util.Optional;

public interface QuerydslPredicateExecutor<T> {

    Optional<T> findById(Predicate predicate);
    Iterable<T> findAll(Predicate predicate);
    long count(Predicate predicate);
    boolean exists(Predicate predicate);

}
