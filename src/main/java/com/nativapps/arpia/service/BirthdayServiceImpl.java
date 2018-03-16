package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.BirthdayDao;
import com.nativapps.arpia.database.entity.Particular;
import com.nativapps.arpia.model.adapter.BirthdayPeriod;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.ParticularResponse;
import com.nativapps.arpia.service.converter.ParticularSimpleConverter;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1-SNAPSHOT
 */
public class BirthdayServiceImpl extends GenericService implements BirthdayService {

    private final BirthdayDao controller;

    public BirthdayServiceImpl(BirthdayDao birthdayDao) {
        this.controller = birthdayDao;
    }

    @Override
    public ListResponse<ParticularResponse> getAll(int start, int size, BirthdayPeriod period) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));
        if (period == null)
            throw new InternalServerErrorException("The birthday period is null");

        List<ParticularResponse> response = new ArrayList<>();
        for (Particular particular : controller.findAll(start, size, period))
            response.add(ParticularSimpleConverter.instance()
                    .convertToDto(particular));

        long total = controller.count(period);
        return new ListResponse<>(total, response);
    }

}
