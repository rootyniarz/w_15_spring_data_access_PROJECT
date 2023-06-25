package pl.zajavka.business;

import pl.zajavka.domain.Opinion;

import java.util.List;

public interface OpinionRepository {
    Opinion create(Opinion opinion);

    void removeAll();

    void remove(String email);

    List<Opinion> findAll();

    List<Opinion> findAll(String email);

    List<Opinion> findUnwantedOpinions();

    void removeUnwantedOpinions();
}
