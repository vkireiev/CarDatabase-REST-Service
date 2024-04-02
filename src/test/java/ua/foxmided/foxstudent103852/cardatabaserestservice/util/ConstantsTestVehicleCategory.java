package ua.foxmided.foxstudent103852.cardatabaserestservice.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleCategory;

public final class ConstantsTestVehicleCategory {
    public static final Long CATEGORY_ID_VALID_1 = 3L;
    public static final Long CATEGORY_ID_VALID_2 = 4L;
    public static final Long CATEGORY_ID_FOR_UPDATE = 5L;
    public static final Long CATEGORY_ID_FOR_DELETE = 5L;
    public static final Long CATEGORY_ID_INVALID = 0L;
    public static final Long CATEGORY_ID_NOT_EXIST = 99L;

    public static final String CATEGORY_NAME_VALID_1 = "Coupe";
    public static final String CATEGORY_NAME_VALID_2 = "Pickup";
    public static final String CATEGORY_NAME_VALID_FOR_ADD = "Sedan (New)";
    public static final String CATEGORY_NAME_VALID_FOR_UPDATE = "Wagon (New)";
    public static final String CATEGORY_NAME_INVALID_1 = "A";
    public static final String CATEGORY_NAME_INVALID_2 = "SUV-Sedan-Coupe-Pickup-Hatchback-Convertible-Van/Minivan-Wagon";

    public static final VehicleCategory NOT_EXIST_CATEGORY = getTestVehicleCategory(CATEGORY_ID_NOT_EXIST);
    public static final VehicleCategory CATEGORY_VALID_1 = getTestVehicleCategory(CATEGORY_ID_VALID_1);
    public static final VehicleCategory CATEGORY_VALID_2 = getTestVehicleCategory(CATEGORY_ID_VALID_2);

    private ConstantsTestVehicleCategory() {
    }

    public static VehicleCategory getTestVehicleCategory(@NotNull Long id) {
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

        nameMap.put(1L, "SUV");
        nameMap.put(2L, "Sedan");
        nameMap.put(3L, "Coupe");
        nameMap.put(4L, "Pickup");
        nameMap.put(5L, "Hatchback");
        nameMap.put(6L, "Convertible");
        nameMap.put(7L, "Van/Minivan");
        nameMap.put(8L, "Wagon");

        // id = CATEGORY_ID_NOT_EXIST
        idMap.put(CATEGORY_ID_NOT_EXIST, CATEGORY_ID_NOT_EXIST);
        nameMap.put(CATEGORY_ID_NOT_EXIST, "NotExistCategory");

        VehicleCategory category = new VehicleCategory();
        category.setId(idMap.get(id));
        category.setName(nameMap.get(id));
        return category;
    }

    public static List<VehicleCategory> getAllTestVehicleCategories() {
        return new ArrayList<VehicleCategory>(Arrays.asList(

                getTestVehicleCategory(1L),
                getTestVehicleCategory(2L),
                getTestVehicleCategory(3L),
                getTestVehicleCategory(4L),
                getTestVehicleCategory(5L),
                getTestVehicleCategory(6L),
                getTestVehicleCategory(7L),
                getTestVehicleCategory(8L)

        ));
    }

    public static VehicleCategory newValidVehicleCategory(Long id) {
        VehicleCategory category = newValidVehicleCategory();
        category.setId(id);
        return category;
    }

    public static VehicleCategory newValidVehicleCategory() {
        VehicleCategory category = new VehicleCategory();
        category.setName(CATEGORY_NAME_VALID_1);
        return category;
    }

}
