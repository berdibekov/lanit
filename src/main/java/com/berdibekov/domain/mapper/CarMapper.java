package com.berdibekov.domain.mapper;

import com.berdibekov.domain.Car;
import com.berdibekov.domain.dto.CarDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CarMapper {

    private final ModelMapper modelMapper;

    public CarMapper(ModelMapper mapper) {
        this.modelMapper = mapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(Car.class, CarDto.class).addMapping(car -> car.getOwner().getId(), CarDto::setOwnerId)
                   .addMappings(
                           new PropertyMap<>() {
                               @Override
                               protected void configure() {
                                   using(ctx -> composeModelDtoFromVendorAndModel(
                                           ((Car) ctx.getSource()).getVendor(),
                                           ((Car) ctx.getSource()).getModel())
                                        )
                                           .map(source, destination.getModel());
                               }
                           });
    }

    private String composeModelDtoFromVendorAndModel(String vendor, String model) {
        return vendor + "-" + model;
    }

    public Car toEntity(CarDto carDto) {
        if (Objects.isNull(carDto)) {
            return null;
        }
        Car car = new Car();
        String[] vendorAndModel = carDto.getModel().split("-", 2);
        car.setId(Long.parseLong(carDto.getId()));
        car.setVendor(vendorAndModel[0]);
        car.setModel(vendorAndModel[1]);
        car.setHorsePower(Integer.parseInt(carDto.getHoursPower()));
        return car;
    }

    public CarDto toDto(Car entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return modelMapper.map(entity, CarDto.class);
    }
}