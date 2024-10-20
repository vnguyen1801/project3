package com.example.project3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Repository
@Transactional
public class vehicleDao {
    @PersistenceContext
    private EntityManager entityManager;
    public void create(Vehicle vehicle) {
        entityManager.persist(vehicle);
    }
    public Vehicle getById(int id) {
        return entityManager.find(Vehicle.class, id);
    }
    public void update(Vehicle vehicle) {
        entityManager.merge(vehicle);
    }

    public void delete(int id) {
        Vehicle vehicle = getById(id);
        if (vehicle != null) {
            entityManager.remove(vehicle);
        }
    }
    public List<Vehicle> getLatestVehicles(int limit) {
        TypedQuery<Vehicle> query = entityManager.createQuery("SELECT v FROM Vehicle v ORDER BY v.year DESC", Vehicle.class);
        query.setMaxResults(limit); // Limit to the number of recent vehicles
        return query.getResultList();
    }
}
