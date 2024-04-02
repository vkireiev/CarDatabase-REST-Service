package ua.foxmided.foxstudent103852.cardatabaserestservice.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;

public final class ConstantsTestVehicleMake {
    public static final Long MAKE_ID_VALID_1 = 3L;
    public static final Long MAKE_ID_VALID_2 = 5L;
    public static final Long MAKE_ID_FOR_UPDATE = 5L;
    public static final Long MAKE_ID_FOR_DELETE = 24L;
    public static final Long MAKE_ID_INVALID = 0L;
    public static final Long MAKE_ID_NOT_EXIST = 99L;

    public static final String MAKE_NAME_VALID_1 = "Cadillac";
    public static final String MAKE_NAME_VALID_2 = "BMW";
    public static final String MAKE_NAME_VALID_FOR_ADD = "BMW (New)";
    public static final String MAKE_NAME_VALID_FOR_UPDATE = "Cadillac (New)";
    public static final String MAKE_NAME_INVALID_1 = "A";
    public static final String MAKE_NAME_INVALID_2 = "Audi-Chevrolet-Jaguar-BMW-Ferrari-Mercedes-Benz-Toyota";

    public static final VehicleMake NOT_EXIST_MAKE = getTestVehicleMake(MAKE_ID_NOT_EXIST);
    public static final VehicleMake MAKE_VALID_1 = getTestVehicleMake(MAKE_ID_VALID_1);
    public static final VehicleMake MAKE_VALID_2 = getTestVehicleMake(MAKE_ID_VALID_2);

    private ConstantsTestVehicleMake() {
    }

    public static VehicleMake getTestVehicleMake(@NotNull Long id) {
        Map<Long, Long> idMap = new HashMap<>();
        Map<Long, String> nameMap = new HashMap<>();

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

        nameMap.put(1L, "Audi");
        nameMap.put(2L, "Chevrolet");
        nameMap.put(3L, "Cadillac");
        nameMap.put(4L, "Acura");
        nameMap.put(5L, "BMW");
        nameMap.put(6L, "GMC");
        nameMap.put(7L, "Honda");
        nameMap.put(8L, "Hyundai");
        nameMap.put(9L, "Jaguar");
        nameMap.put(10L, "Lexus");
        nameMap.put(11L, "Porsche");
        nameMap.put(12L, "Volvo");
        nameMap.put(13L, "Ford");
        nameMap.put(14L, "Lincoln");
        nameMap.put(15L, "Mercedes-Benz");
        nameMap.put(16L, "Volkswagen");
        nameMap.put(17L, "Bentley");
        nameMap.put(18L, "Ferrari");
        nameMap.put(19L, "INFINITI");
        nameMap.put(20L, "Rolls-Royce");
        nameMap.put(21L, "Toyota");
        nameMap.put(22L, "Subaru");
        nameMap.put(23L, "Saab");
        nameMap.put(24L, "Dodge");
        nameMap.put(25L, "HUMMER");
        nameMap.put(26L, "Nissan");

        // id = MAKE_ID_NOT_EXIST
        idMap.put(MAKE_ID_NOT_EXIST, MAKE_ID_NOT_EXIST);
        nameMap.put(MAKE_ID_NOT_EXIST, "NotExistMake");

        VehicleMake make = new VehicleMake();
        make.setId(idMap.get(id));
        make.setName(nameMap.get(id));
        return make;
    }

    public static List<VehicleMake> getAllTestVehicleMakes() {
        return new ArrayList<VehicleMake>(Arrays.asList(

                getTestVehicleMake(1L),
                getTestVehicleMake(2L),
                getTestVehicleMake(3L),
                getTestVehicleMake(4L),
                getTestVehicleMake(5L),
                getTestVehicleMake(6L),
                getTestVehicleMake(7L),
                getTestVehicleMake(8L),
                getTestVehicleMake(9L),
                getTestVehicleMake(10L),
                getTestVehicleMake(11L),
                getTestVehicleMake(12L),
                getTestVehicleMake(13L),
                getTestVehicleMake(14L),
                getTestVehicleMake(15L),
                getTestVehicleMake(16L),
                getTestVehicleMake(17L),
                getTestVehicleMake(18L),
                getTestVehicleMake(19L),
                getTestVehicleMake(20L),
                getTestVehicleMake(21L),
                getTestVehicleMake(22L),
                getTestVehicleMake(23L),
                getTestVehicleMake(24L),
                getTestVehicleMake(25L),
                getTestVehicleMake(26L)

        ));
    }

    public static VehicleMake newValidVehicleMake(Long id) {
        VehicleMake make = newValidVehicleMake();
        make.setId(id);
        return make;
    }

    public static VehicleMake newValidVehicleMake() {
        VehicleMake make = new VehicleMake();
        make.setName(MAKE_NAME_VALID_1);
        return make;
    }

}
