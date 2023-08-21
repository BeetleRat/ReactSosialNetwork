package ru.beetlerat.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.beetlerat.socialnetwork.models.ImageModel;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Integer> {
    ImageModel findByName(String name);
}
