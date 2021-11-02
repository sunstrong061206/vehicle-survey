package vehicle_survey_springboot.New.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vehicle_survey_springboot.New.mapper.GetDetailMapper;

@Service
public class GetDetailService {
    @Autowired
    GetDetailMapper getDetailMapper;
}
