package com.berdibekov.domain.mapper;

import com.berdibekov.domain.Person;
import com.berdibekov.domain.dto.PersonDto;
import lombok.SneakyThrows;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Component
public class PersonMapper {
    private final ModelMapper mapper;
    private final DateFormat format;

    public PersonMapper(ModelMapper mapper) {
        this.mapper = mapper;
        format = new SimpleDateFormat("dd.MM.yyyy");
//        mapper.createTypeMap(String.class, Date.class);
        mapper.addConverter(convertStringToDate);

        mapper.addConverter(convertDateToString);
    }

    public PersonDto toDto(Person entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, PersonDto.class);
    }

    public Person toEntity(PersonDto personDto) {
        return Objects.isNull(personDto) ? null : mapper.map(personDto, Person.class);
    }

    Converter<String, Date> convertStringToDate = new AbstractConverter<>() {
        @SneakyThrows
        @Override
        protected Date convert(String source) {
            return format.parse(source);
        }
    };

    Converter<Date, String> convertDateToString = new AbstractConverter<>() {
        @Override
        protected String convert(Date source) {
            if (source != null) {
                return format.format(source);
            }
            return "";
        }
    };
}
