package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Opinion;

@Service
@AllArgsConstructor
public class OpinionService {

    private OpinionRepository opinionRepository;

    @Transactional
    public Opinion create(Opinion opinion) {
        return opinionRepository.create(opinion);
    }

    public void removeAll(){
        opinionRepository.removeAll();
    }

    @Transactional
    public void removeAll(String email) {

    }
}




