package org.repositories;

import org.models.Municipality;

public class MunicipalityRepository extends BaseRepository implements Repository{
        public void create(Municipality municipality) {
            String name = municipality.getName();
        }

}
