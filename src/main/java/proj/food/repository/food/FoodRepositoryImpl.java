package proj.food.repository.food;

import jakarta.persistence.EntityManager;
import proj.food.entity.FoodEntity;
import proj.food.repository.abs.BaseRepository;

public class FoodRepositoryImpl extends BaseRepository<FoodEntity, Long>
        implements FoodRepository {

    public FoodRepositoryImpl(EntityManager em) {
        super(em, FoodEntity.class);
    }

    @Override
    protected Long extractId(FoodEntity entity) {
        return entity.getId();
    }
}
