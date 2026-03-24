package com.example.management_service.repositories;

import com.example.management_service.document.AppointmentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDB repository for managing active appointment documents.
 *
 * <p>
 * This repository handles CRUD operations for
 * {@link AppointmentDocument} stored in the
 * <b>appointments</b> collection.
 * </p>
 *
 * <p>
 * Extends {@link MongoRepository} which provides:
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
 * Spring Data MongoDB automatically generates the implementation
 * at runtime.
 * </p>
 *
 * @since 1.0
 */
@Repository
public interface AppointmentMongoRepository
        extends MongoRepository<AppointmentDocument, String> {

}
