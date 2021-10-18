package com.fibikky.vehicle.web.pivot.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 16861
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class DefaultQueryParam extends BaseEntity {
    @ApiModelProperty(value = "当前页")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "总页数")
    private Integer pageSize = 10;
}
