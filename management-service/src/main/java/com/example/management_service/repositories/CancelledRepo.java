package com.example.management_service.repositories;

import com.example.management_service.document.CancelledCheckupDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDB repository for managing cancelled appointment records.
 *
 * <p>
 * This repository handles CRUD operations for
 * {@link CancelledCheckupDocument} stored in the
 * <b>cancelled_checkups</b> collection.
 * </p>
 *
 * <p>
 * Extends {@link MongoRepository} which provides
 * built-in methods such as:
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
public interface CancelledRepo
        extends MongoRepository<CancelledCheckupDocument, String> {

}
