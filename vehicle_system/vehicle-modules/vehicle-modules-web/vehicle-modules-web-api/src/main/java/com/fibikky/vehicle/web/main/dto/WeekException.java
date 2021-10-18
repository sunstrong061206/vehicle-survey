package com.fibikky.vehicle.web.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeekException {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exceptionTime;

    private Integer exceptionCount;

}
