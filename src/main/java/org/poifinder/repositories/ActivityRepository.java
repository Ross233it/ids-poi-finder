package org.poifinder.repositories;

import org.poifinder.models.activities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {


//    @Override
//    public List<Activity> search(Map<String, String> filters) throws Exception {
//        return List.of();
//    }
}
