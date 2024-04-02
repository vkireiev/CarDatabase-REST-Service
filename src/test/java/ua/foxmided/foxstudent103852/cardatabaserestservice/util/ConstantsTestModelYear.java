package ua.foxmided.foxstudent103852.cardatabaserestservice.util;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;

public final class ConstantsTestModelYear {
    public static final Long YEAR_ID_VALID_1 = 3L;
    public static final Long YEAR_ID_VALID_2 = 4L;
    public static final Long YEAR_ID_VALID_3 = 2L;
    public static final Long YEAR_ID_FOR_UPDATE = 5L;
    public static final Long YEAR_ID_FOR_DELETE = 5L;
    public static final Long YEAR_ID_INVALID = 0L;
    public static final Long YEAR_ID_NOT_EXIST = 99L;

    public static final Year YEAR_YEAR_VALID_1 = Year.of(2018);
    public static final Year YEAR_YEAR_VALID_2 = Year.of(2017);
    public static final Year YEAR_YEAR_VALID_3 = Year.of(2019);
    public static final Year YEAR_YEAR_VALID_FOR_ADD = Year.of(2024);
    public static final Year YEAR_YEAR_VALID_FOR_UPDATE = Year.of(2023);
    public static final Year YEAR_YEAR_INVALID_1 = Year.of(-1000);
    public static final Year YEAR_YEAR_INVALID_2 = Year.of(0);

    public static final ModelYear NOT_EXIST_YEAR = getTestModelYear(YEAR_ID_NOT_EXIST);
    public static final ModelYear YEAR_VALID_1 = getTestModelYear(YEAR_ID_VALID_1);
    public static final ModelYear YEAR_VALID_2 = getTestModelYear(YEAR_ID_VALID_2);
    public static final ModelYear YEAR_VALID_3 = getTestModelYear(YEAR_ID_VALID_3);

    private ConstantsTestModelYear() {
    }

    public static ModelYear getTestModelYear(@NotNull Long id) {
        Map<Long, Long> idMap = new HashMap<>();
        Map<Long, Year> yearMap = new HashMap<>();

        idMap.put(1L, 1L);
        idMap.put(2L, 2L);
        idMap.put(3L, 3L);
        idMap.put(4L, 4L);
        idMap.put(5L, 5L);
        idMap.put(6L, 6L);
        idMap.put(7L, 7L);
        idMap.put(8L, 8L);
        idMap.put(9L, 9L);
        idMap.put(10L, 10L);
        idMap.put(11L, 11L);
        idMap.put(12L, 12L);
        idMap.put(13L, 13L);
        idMap.put(14L, 14L);
        idMap.put(15L, 15L);
        idMap.put(16L, 16L);
        idMap.put(17L, 17L);
        idMap.put(18L, 18L);
        idMap.put(19L, 19L);
        idMap.put(20L, 20L);
        idMap.put(21L, 21L);
        idMap.put(22L, 22L);
        idMap.put(23L, 23L);
        idMap.put(24L, 24L);
        idMap.put(25L, 25L);
        idMap.put(26L, 26L);

        yearMap.put(1L, Year.of(2020));
        yearMap.put(2L, Year.of(2019));
        yearMap.put(3L, Year.of(2018));
        yearMap.put(4L, Year.of(2017));
        yearMap.put(5L, Year.of(2016));
        yearMap.put(6L, Year.of(2015));
        yearMap.put(7L, Year.of(2014));
        yearMap.put(8L, Year.of(2013));
        yearMap.put(9L, Year.of(2012));
        yearMap.put(10L, Year.of(2011));
        yearMap.put(11L, Year.of(2010));
        yearMap.put(12L, Year.of(2009));
        yearMap.put(13L, Year.of(2008));
        yearMap.put(14L, Year.of(2007));
        yearMap.put(15L, Year.of(2006));
        yearMap.put(16L, Year.of(2005));
        yearMap.put(17L, Year.of(2002));
        yearMap.put(18L, Year.of(2001));
        yearMap.put(19L, Year.of(2000));
        yearMap.put(20L, Year.of(1999));
        yearMap.put(21L, Year.of(1998));
        yearMap.put(22L, Year.of(1997));
        yearMap.put(23L, Year.of(1994));
        yearMap.put(24L, Year.of(1993));
        yearMap.put(25L, Year.of(1992));
        yearMap.put(26L, Year.of(2021));

        // id = YEAR_ID_NOT_EXIST
        idMap.put(YEAR_ID_NOT_EXIST, YEAR_ID_NOT_EXIST);
        yearMap.put(YEAR_ID_NOT_EXIST, Year.of(0));

        ModelYear year = new ModelYear();
        year.setId(idMap.get(id));
        year.setYear(yearMap.get(id));
        return year;
    }

    public static List<ModelYear> getAllTestModelYears() {
        return new ArrayList<ModelYear>(Arrays.asList(

                getTestModelYear(1L),
                getTestModelYear(2L),
                getTestModelYear(3L),
                getTestModelYear(4L),
                getTestModelYear(5L),
                getTestModelYear(6L),
                getTestModelYear(7L),
                getTestModelYear(8L),
                getTestModelYear(9L),
                getTestModelYear(10L),
                getTestModelYear(11L),
                getTestModelYear(12L),
                getTestModelYear(13L),
                getTestModelYear(14L),
                getTestModelYear(15L),
                getTestModelYear(16L),
                getTestModelYear(17L),
                getTestModelYear(18L),
                getTestModelYear(19L),
                getTestModelYear(20L),
                getTestModelYear(21L),
                getTestModelYear(22L),
                getTestModelYear(23L),
                getTestModelYear(24L),
                getTestModelYear(25L),
                getTestModelYear(26L)

        ));
    }

    public static ModelYear newValidModelYear(Long id) {
        ModelYear year = newValidModelYear();
        year.setId(id);
        return year;
    }

    public static ModelYear newValidModelYear() {
        ModelYear year = new ModelYear();
        year.setYear(ConstantsTestModelYear.YEAR_YEAR_VALID_1);
        return year;
    }

}
