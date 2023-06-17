package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OpinionService {

    private OpinionRepository opinionRepository;

    public void removeAll(){
        opinionRepository.removeAll();
    }
}




