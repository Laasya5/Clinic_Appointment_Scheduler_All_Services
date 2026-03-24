package com.example.management_service.repositories;

import com.example.management_service.document.CompletedCheckupDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDB repository for managing completed appointment records.
 *
 * <p>
 * This repository handles CRUD operations for
 * {@link CompletedCheckupDocument} stored in the
 * <b>completed_checkups</b> collection.
 * </p>
 *
 * <p>
 * Extends {@link MongoRepository}, which provides
 * built-in data access methods such as:
 * </p>
 * <ul>
 *     <li>save()</li>
 *     <li>findById()</li>
 *     <li>findAll()</li>
 *     <li>delete()</li>
 *     <li>deleteAll()</li>
 * </ul>
 *
 * <p>
 * Spring Data MongoDB automatically generates
 * the implementation at runtime.
 * </p>
 *
 * @since 1.0
 */
@Repository
public interface CompletedRepo
        extends MongoRepository<CompletedCheckupDocument, String> {

}
