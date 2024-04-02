package ua.foxmided.foxstudent103852.cardatabaserestservice.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;

public final class ConstantsTestVehicleModel {
    public static final Long MODEL_ID_VALID_1 = 3L;
    public static final Long MODEL_ID_VALID_2 = 7L;
    public static final Long MODEL_ID_FOR_UPDATE = 5L;
    public static final Long MODEL_ID_FOR_DELETE = 5L;
    public static final Long MODEL_ID_INVALID = 0L;
    public static final Long MODEL_ID_NOT_EXIST = 99L;

    public static final String MODEL_NAME_VALID_1 = "Escalade ESV";
    public static final String MODEL_NAME_VALID_2 = "3 Series";
    public static final String MODEL_NAME_VALID_FOR_ADD = "3 Series (New)";
    public static final String MODEL_NAME_VALID_FOR_UPDATE = "Escalade ESV (New)";
    public static final String MODEL_NAME_INVALID_1 = "Malibu-Corvette-Civic-Continental-Golf-Boxster-Venza-Thunderbird";

    public static final VehicleMake MODEL_MAKE_VALID_1 = ConstantsTestVehicleMake
            .getTestVehicleMake(ConstantsTestVehicleMake.MAKE_ID_VALID_1);
    public static final VehicleMake MODEL_MAKE_VALID_2 = ConstantsTestVehicleMake
            .getTestVehicleMake(ConstantsTestVehicleMake.MAKE_ID_VALID_2);
    public static final VehicleMake MODEL_MAKE_VALID_FOR_ADD = ConstantsTestVehicleMake
            .getTestVehicleMake(ConstantsTestVehicleMake.MAKE_ID_FOR_DELETE);
    public static final VehicleMake MODEL_MAKE_VALID_FOR_UPDATE = ConstantsTestVehicleMake
            .getTestVehicleMake(ConstantsTestVehicleMake.MAKE_ID_FOR_UPDATE);
    public static final VehicleMake MODEL_MAKE_INVALID_1 = ConstantsTestVehicleMake
            .getTestVehicleMake(ConstantsTestVehicleMake.MAKE_ID_NOT_EXIST);

    public static final Set<ModelYear> MODEL_YEARS_VALID_1 = Set.of(ConstantsTestModelYear.getTestModelYear(1L));
    public static final Set<ModelYear> MODEL_YEARS_VALID_2 = Set.of(
            ConstantsTestModelYear.getTestModelYear(1L),
            ConstantsTestModelYear.getTestModelYear(2L),
            ConstantsTestModelYear.getTestModelYear(7L),
            ConstantsTestModelYear.getTestModelYear(8L),
            ConstantsTestModelYear.getTestModelYear(15L));
    public static final Set<ModelYear> MODEL_YEARS_FOR_ADD = Set.of(
            ConstantsTestModelYear.getTestModelYear(1L),
            ConstantsTestModelYear.getTestModelYear(2L),
            ConstantsTestModelYear.getTestModelYear(3L),
            ConstantsTestModelYear.getTestModelYear(4L),
            ConstantsTestModelYear.getTestModelYear(7L),
            ConstantsTestModelYear.getTestModelYear(8L),
            ConstantsTestModelYear.getTestModelYear(15L));
    public static final Set<ModelYear> MODEL_YEARS_FOR_DELETE = Set.of(ConstantsTestModelYear.getTestModelYear(2L));
    public static final Set<ModelYear> MODEL_YEARS_INVALID_1 = Set.of(ConstantsTestModelYear.NOT_EXIST_YEAR);

    public static final VehicleModel NOT_EXIST_MODEL = getTestVehicleModel(MODEL_ID_NOT_EXIST);
    public static final VehicleModel MODEL_VALID_1 = getTestVehicleModel(MODEL_ID_VALID_1);
    public static final VehicleModel MODEL_VALID_2 = getTestVehicleModel(MODEL_ID_VALID_2);

    private ConstantsTestVehicleModel() {
    }

    public static VehicleModel getTestVehicleModel(@NotNull Long id) {
        Map<Long, Long> idMap = new HashMap<>();
        Map<Long, String> nameMap = new HashMap<>();
        Map<Long, VehicleMake> makeMap = new HashMap<>();
        Map<Long, Set<ModelYear>> yearsMap = new HashMap<>();

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
        idMap.put(27L, 27L);
        idMap.put(28L, 28L);
        idMap.put(29L, 29L);
        idMap.put(30L, 30L);
        idMap.put(31L, 31L);
        idMap.put(32L, 32L);
        idMap.put(33L, 33L);
        idMap.put(34L, 34L);
        idMap.put(35L, 35L);
        idMap.put(36L, 36L);
        idMap.put(37L, 37L);
        idMap.put(38L, 38L);
        idMap.put(39L, 39L);
        idMap.put(40L, 40L);
        idMap.put(41L, 41L);
        idMap.put(42L, 42L);
        idMap.put(43L, 43L);
        idMap.put(44L, 44L);
        idMap.put(45L, 45L);
        idMap.put(46L, 46L);
        idMap.put(47L, 47L);
        idMap.put(48L, 48L);
        idMap.put(49L, 49L);
        idMap.put(50L, 50L);
        idMap.put(51L, 51L);
        idMap.put(52L, 52L);
        idMap.put(53L, 53L);
        idMap.put(54L, 54L);
        idMap.put(55L, 55L);
        idMap.put(56L, 56L);
        idMap.put(57L, 57L);
        idMap.put(58L, 58L);
        idMap.put(59L, 59L);

        nameMap.put(1L, "Q3");
        nameMap.put(2L, "Malibu");
        nameMap.put(3L, "Escalade ESV");
        nameMap.put(4L, "Corvette");
        nameMap.put(5L, "RLX");
        nameMap.put(6L, "Silverado 2500 HD Crew Cab");
        nameMap.put(7L, "3 Series");
        nameMap.put(8L, "Sierra 2500 HD Crew Cab");
        nameMap.put(9L, "Civic");
        nameMap.put(10L, "Elantra GT");
        nameMap.put(11L, "F-TYPE");
        nameMap.put(12L, "NX");
        nameMap.put(13L, "911");
        nameMap.put(14L, "A3");
        nameMap.put(15L, "S5");
        nameMap.put(16L, "e-tron");
        nameMap.put(17L, "A5");
        nameMap.put(18L, "M6");
        nameMap.put(19L, "Z4");
        nameMap.put(20L, "Express 2500 Cargo");
        nameMap.put(21L, "Transit 150 Wagon");
        nameMap.put(22L, "Continental");
        nameMap.put(23L, "Mercedes-AMG E-Class");
        nameMap.put(24L, "718 Boxster");
        nameMap.put(25L, "Golf SportWagen");
        nameMap.put(26L, "e-Golf");
        nameMap.put(27L, "Golf");
        nameMap.put(28L, "Continental");
        nameMap.put(29L, "V60");
        nameMap.put(30L, "California");
        nameMap.put(31L, "488 Spider");
        nameMap.put(32L, "Q60");
        nameMap.put(33L, "Dawn");
        nameMap.put(34L, "Boxster");
        nameMap.put(35L, "Phantom");
        nameMap.put(36L, "Venza");
        nameMap.put(37L, "Outback");
        nameMap.put(38L, "E-Class");
        nameMap.put(39L, "45538");
        nameMap.put(40L, "Azure T");
        nameMap.put(41L, "SC");
        nameMap.put(42L, "S4");
        nameMap.put(43L, "Azure");
        nameMap.put(44L, "S2000");
        nameMap.put(45L, "SLR McLaren");
        nameMap.put(46L, "H2");
        nameMap.put(47L, "Carrera GT");
        nameMap.put(48L, "Thunderbird");
        nameMap.put(49L, "Z8");
        nameMap.put(50L, "Golf (New)");
        nameMap.put(51L, "Cabriolet");
        nameMap.put(52L, "Z3");
        nameMap.put(53L, "Cavalier");
        nameMap.put(54L, "500 SL");
        nameMap.put(55L, "600 SL");
        nameMap.put(56L, "NX");
        nameMap.put(57L, "Cabriolet");
        nameMap.put(58L, "300 SL");
        nameMap.put(59L, "718 Spyder");

        makeMap.put(1L, ConstantsTestVehicleMake.getTestVehicleMake(1L));
        makeMap.put(2L, ConstantsTestVehicleMake.getTestVehicleMake(2L));
        makeMap.put(3L, ConstantsTestVehicleMake.getTestVehicleMake(3L));
        makeMap.put(4L, ConstantsTestVehicleMake.getTestVehicleMake(2L));
        makeMap.put(5L, ConstantsTestVehicleMake.getTestVehicleMake(4L));
        makeMap.put(6L, ConstantsTestVehicleMake.getTestVehicleMake(2L));
        makeMap.put(7L, ConstantsTestVehicleMake.getTestVehicleMake(5L));
        makeMap.put(8L, ConstantsTestVehicleMake.getTestVehicleMake(6L));
        makeMap.put(9L, ConstantsTestVehicleMake.getTestVehicleMake(7L));
        makeMap.put(10L, ConstantsTestVehicleMake.getTestVehicleMake(8L));
        makeMap.put(11L, ConstantsTestVehicleMake.getTestVehicleMake(9L));
        makeMap.put(12L, ConstantsTestVehicleMake.getTestVehicleMake(10L));
        makeMap.put(13L, ConstantsTestVehicleMake.getTestVehicleMake(11L));
        makeMap.put(14L, ConstantsTestVehicleMake.getTestVehicleMake(1L));
        makeMap.put(15L, ConstantsTestVehicleMake.getTestVehicleMake(1L));
        makeMap.put(16L, ConstantsTestVehicleMake.getTestVehicleMake(1L));
        makeMap.put(17L, ConstantsTestVehicleMake.getTestVehicleMake(1L));
        makeMap.put(18L, ConstantsTestVehicleMake.getTestVehicleMake(5L));
        makeMap.put(19L, ConstantsTestVehicleMake.getTestVehicleMake(5L));
        makeMap.put(20L, ConstantsTestVehicleMake.getTestVehicleMake(2L));
        makeMap.put(21L, ConstantsTestVehicleMake.getTestVehicleMake(13L));
        makeMap.put(22L, ConstantsTestVehicleMake.getTestVehicleMake(14L));
        makeMap.put(23L, ConstantsTestVehicleMake.getTestVehicleMake(15L));
        makeMap.put(24L, ConstantsTestVehicleMake.getTestVehicleMake(11L));
        makeMap.put(25L, ConstantsTestVehicleMake.getTestVehicleMake(16L));
        makeMap.put(26L, ConstantsTestVehicleMake.getTestVehicleMake(16L));
        makeMap.put(27L, ConstantsTestVehicleMake.getTestVehicleMake(16L));
        makeMap.put(28L, ConstantsTestVehicleMake.getTestVehicleMake(17L));
        makeMap.put(29L, ConstantsTestVehicleMake.getTestVehicleMake(12L));
        makeMap.put(30L, ConstantsTestVehicleMake.getTestVehicleMake(18L));
        makeMap.put(31L, ConstantsTestVehicleMake.getTestVehicleMake(18L));
        makeMap.put(32L, ConstantsTestVehicleMake.getTestVehicleMake(19L));
        makeMap.put(33L, ConstantsTestVehicleMake.getTestVehicleMake(20L));
        makeMap.put(34L, ConstantsTestVehicleMake.getTestVehicleMake(11L));
        makeMap.put(35L, ConstantsTestVehicleMake.getTestVehicleMake(20L));
        makeMap.put(36L, ConstantsTestVehicleMake.getTestVehicleMake(21L));
        makeMap.put(37L, ConstantsTestVehicleMake.getTestVehicleMake(22L));
        makeMap.put(38L, ConstantsTestVehicleMake.getTestVehicleMake(15L));
        makeMap.put(39L, ConstantsTestVehicleMake.getTestVehicleMake(23L));
        makeMap.put(40L, ConstantsTestVehicleMake.getTestVehicleMake(17L));
        makeMap.put(41L, ConstantsTestVehicleMake.getTestVehicleMake(10L));
        makeMap.put(42L, ConstantsTestVehicleMake.getTestVehicleMake(1L));
        makeMap.put(43L, ConstantsTestVehicleMake.getTestVehicleMake(17L));
        makeMap.put(44L, ConstantsTestVehicleMake.getTestVehicleMake(7L));
        makeMap.put(45L, ConstantsTestVehicleMake.getTestVehicleMake(15L));
        makeMap.put(46L, ConstantsTestVehicleMake.getTestVehicleMake(25L));
        makeMap.put(47L, ConstantsTestVehicleMake.getTestVehicleMake(11L));
        makeMap.put(48L, ConstantsTestVehicleMake.getTestVehicleMake(13L));
        makeMap.put(49L, ConstantsTestVehicleMake.getTestVehicleMake(5L));
        makeMap.put(50L, ConstantsTestVehicleMake.getTestVehicleMake(16L));
        makeMap.put(51L, ConstantsTestVehicleMake.getTestVehicleMake(1L));
        makeMap.put(52L, ConstantsTestVehicleMake.getTestVehicleMake(5L));
        makeMap.put(53L, ConstantsTestVehicleMake.getTestVehicleMake(2L));
        makeMap.put(54L, ConstantsTestVehicleMake.getTestVehicleMake(15L));
        makeMap.put(55L, ConstantsTestVehicleMake.getTestVehicleMake(15L));
        makeMap.put(56L, ConstantsTestVehicleMake.getTestVehicleMake(26L));
        makeMap.put(57L, ConstantsTestVehicleMake.getTestVehicleMake(16L));
        makeMap.put(58L, ConstantsTestVehicleMake.getTestVehicleMake(15L));
        makeMap.put(59L, ConstantsTestVehicleMake.getTestVehicleMake(11L));

        yearsMap.put(1L, Set.of(
                ConstantsTestModelYear.getTestModelYear(1L),
                ConstantsTestModelYear.getTestModelYear(5L)));
        yearsMap.put(2L, Set.of(ConstantsTestModelYear.getTestModelYear(1L)));
        yearsMap.put(3L, Set.of(ConstantsTestModelYear.getTestModelYear(1L)));
        yearsMap.put(4L, Set.of(ConstantsTestModelYear.getTestModelYear(1L)));
        yearsMap.put(5L, Set.of(ConstantsTestModelYear.getTestModelYear(1L)));
        yearsMap.put(6L, Set.of(ConstantsTestModelYear.getTestModelYear(1L)));
        yearsMap.put(7L, Set.of(
                ConstantsTestModelYear.getTestModelYear(1L),
                ConstantsTestModelYear.getTestModelYear(2L),
                ConstantsTestModelYear.getTestModelYear(7L),
                ConstantsTestModelYear.getTestModelYear(8L),
                ConstantsTestModelYear.getTestModelYear(15L)));
        yearsMap.put(8L, Set.of(ConstantsTestModelYear.getTestModelYear(1L)));
        yearsMap.put(9L, Set.of(ConstantsTestModelYear.getTestModelYear(1L)));
        yearsMap.put(10L, Set.of(ConstantsTestModelYear.getTestModelYear(1L)));
        yearsMap.put(11L, Set.of(ConstantsTestModelYear.getTestModelYear(1L)));
        yearsMap.put(12L, Set.of(
                ConstantsTestModelYear.getTestModelYear(1L),
                ConstantsTestModelYear.getTestModelYear(2L),
                ConstantsTestModelYear.getTestModelYear(26L)));
        yearsMap.put(13L, Set.of(ConstantsTestModelYear.getTestModelYear(1L)));
        yearsMap.put(14L, Set.of(
                ConstantsTestModelYear.getTestModelYear(2L),
                ConstantsTestModelYear.getTestModelYear(3L),
                ConstantsTestModelYear.getTestModelYear(10L)));
        yearsMap.put(15L, Set.of(ConstantsTestModelYear.getTestModelYear(2L)));
        yearsMap.put(16L, Set.of(ConstantsTestModelYear.getTestModelYear(2L)));
        yearsMap.put(17L, Set.of(ConstantsTestModelYear.getTestModelYear(2L)));
        yearsMap.put(18L, Set.of(
                ConstantsTestModelYear.getTestModelYear(2L),
                ConstantsTestModelYear.getTestModelYear(9L)));
        yearsMap.put(19L, Set.of(ConstantsTestModelYear.getTestModelYear(2L)));
        yearsMap.put(20L, Set.of(ConstantsTestModelYear.getTestModelYear(2L)));
        yearsMap.put(21L, Set.of(ConstantsTestModelYear.getTestModelYear(2L)));
        yearsMap.put(22L, Set.of(ConstantsTestModelYear.getTestModelYear(2L)));
        yearsMap.put(23L, Set.of(ConstantsTestModelYear.getTestModelYear(2L)));
        yearsMap.put(24L, Set.of(ConstantsTestModelYear.getTestModelYear(2L)));
        yearsMap.put(25L, Set.of(ConstantsTestModelYear.getTestModelYear(2L)));
        yearsMap.put(26L, Set.of(ConstantsTestModelYear.getTestModelYear(2L)));
        yearsMap.put(27L, Set.of(ConstantsTestModelYear.getTestModelYear(2L)));
        yearsMap.put(28L, Set.of(
                ConstantsTestModelYear.getTestModelYear(3L),
                ConstantsTestModelYear.getTestModelYear(8L)));
        yearsMap.put(29L, Set.of(ConstantsTestModelYear.getTestModelYear(3L)));
        yearsMap.put(30L, Set.of(ConstantsTestModelYear.getTestModelYear(4L)));
        yearsMap.put(31L, Set.of(ConstantsTestModelYear.getTestModelYear(4L)));
        yearsMap.put(32L, Set.of(
                ConstantsTestModelYear.getTestModelYear(4L),
                ConstantsTestModelYear.getTestModelYear(6L)));
        yearsMap.put(33L, Set.of(ConstantsTestModelYear.getTestModelYear(4L)));
        yearsMap.put(34L, Set.of(ConstantsTestModelYear.getTestModelYear(5L)));
        yearsMap.put(35L, Set.of(ConstantsTestModelYear.getTestModelYear(5L)));
        yearsMap.put(36L, Set.of(ConstantsTestModelYear.getTestModelYear(6L)));
        yearsMap.put(37L, Set.of(ConstantsTestModelYear.getTestModelYear(8L)));
        yearsMap.put(38L, Set.of(ConstantsTestModelYear.getTestModelYear(10L)));
        yearsMap.put(39L, Set.of(ConstantsTestModelYear.getTestModelYear(10L)));
        yearsMap.put(40L, Set.of(ConstantsTestModelYear.getTestModelYear(11L)));
        yearsMap.put(41L, Set.of(ConstantsTestModelYear.getTestModelYear(11L)));
        yearsMap.put(42L, Set.of(ConstantsTestModelYear.getTestModelYear(12L)));
        yearsMap.put(43L, Set.of(ConstantsTestModelYear.getTestModelYear(12L)));
        yearsMap.put(44L, Set.of(
                ConstantsTestModelYear.getTestModelYear(12L),
                ConstantsTestModelYear.getTestModelYear(16L),
                ConstantsTestModelYear.getTestModelYear(19L)));
        yearsMap.put(45L, Set.of(ConstantsTestModelYear.getTestModelYear(13L)));
        yearsMap.put(46L, Set.of(ConstantsTestModelYear.getTestModelYear(14L)));
        yearsMap.put(47L, Set.of(ConstantsTestModelYear.getTestModelYear(16L)));
        yearsMap.put(48L, Set.of(ConstantsTestModelYear.getTestModelYear(17L)));
        yearsMap.put(49L, Set.of(ConstantsTestModelYear.getTestModelYear(18L)));
        yearsMap.put(50L, Set.of(ConstantsTestModelYear.getTestModelYear(20L)));
        yearsMap.put(51L, Set.of(
                ConstantsTestModelYear.getTestModelYear(21L),
                ConstantsTestModelYear.getTestModelYear(23L)));
        yearsMap.put(52L, Set.of(ConstantsTestModelYear.getTestModelYear(22L)));
        yearsMap.put(53L, Set.of(ConstantsTestModelYear.getTestModelYear(23L)));
        yearsMap.put(54L, Set.of(ConstantsTestModelYear.getTestModelYear(24L)));
        yearsMap.put(55L, Set.of(ConstantsTestModelYear.getTestModelYear(24L)));
        yearsMap.put(56L, Set.of(
                ConstantsTestModelYear.getTestModelYear(24L),
                ConstantsTestModelYear.getTestModelYear(25L)));
        yearsMap.put(57L, Set.of(
                ConstantsTestModelYear.getTestModelYear(24L),
                ConstantsTestModelYear.getTestModelYear(25L)));
        yearsMap.put(58L, Set.of(ConstantsTestModelYear.getTestModelYear(25L)));
        yearsMap.put(59L, Set.of(ConstantsTestModelYear.getTestModelYear(26L)));

        // id = MODEL_ID_NOT_EXIST
        idMap.put(MODEL_ID_NOT_EXIST, MODEL_ID_NOT_EXIST);
        nameMap.put(MODEL_ID_NOT_EXIST, "NotExistModel");
        makeMap.put(MODEL_ID_NOT_EXIST, ConstantsTestVehicleMake
                .getTestVehicleMake(ConstantsTestVehicleMake.MAKE_ID_NOT_EXIST));
        yearsMap.put(MODEL_ID_NOT_EXIST, Set.of(ConstantsTestModelYear.getTestModelYear(
                ConstantsTestModelYear.YEAR_ID_NOT_EXIST)));

        VehicleModel model = new VehicleModel();
        model.setId(idMap.get(id));
        model.setName(nameMap.get(id));
        model.setMake(makeMap.get(id));
        model.setYears(new HashSet<>(yearsMap.get(id)));
        return model;
    }

    public static List<VehicleModel> getAllTestVehicleModels() {
        return new ArrayList<VehicleModel>(Arrays.asList(

                getTestVehicleModel(1L),
                getTestVehicleModel(2L),
                getTestVehicleModel(3L),
                getTestVehicleModel(4L),
                getTestVehicleModel(5L),
                getTestVehicleModel(6L),
                getTestVehicleModel(7L),
                getTestVehicleModel(8L),
                getTestVehicleModel(9L),
                getTestVehicleModel(10L),
                getTestVehicleModel(11L),
                getTestVehicleModel(12L),
                getTestVehicleModel(13L),
                getTestVehicleModel(14L),
                getTestVehicleModel(15L),
                getTestVehicleModel(16L),
                getTestVehicleModel(17L),
                getTestVehicleModel(18L),
                getTestVehicleModel(19L),
                getTestVehicleModel(20L),
                getTestVehicleModel(21L),
                getTestVehicleModel(22L),
                getTestVehicleModel(23L),
                getTestVehicleModel(24L),
                getTestVehicleModel(25L),
                getTestVehicleModel(26L),
                getTestVehicleModel(27L),
                getTestVehicleModel(28L),
                getTestVehicleModel(29L),
                getTestVehicleModel(30L),
                getTestVehicleModel(31L),
                getTestVehicleModel(32L),
                getTestVehicleModel(33L),
                getTestVehicleModel(34L),
                getTestVehicleModel(35L),
                getTestVehicleModel(36L),
                getTestVehicleModel(37L),
                getTestVehicleModel(38L),
                getTestVehicleModel(39L),
                getTestVehicleModel(40L),
                getTestVehicleModel(41L),
                getTestVehicleModel(42L),
                getTestVehicleModel(43L),
                getTestVehicleModel(44L),
                getTestVehicleModel(45L),
                getTestVehicleModel(46L),
                getTestVehicleModel(47L),
                getTestVehicleModel(48L),
                getTestVehicleModel(49L),
                getTestVehicleModel(50L),
                getTestVehicleModel(51L),
                getTestVehicleModel(52L),
                getTestVehicleModel(53L),
                getTestVehicleModel(54L),
                getTestVehicleModel(55L),
                getTestVehicleModel(56L),
                getTestVehicleModel(57L),
                getTestVehicleModel(58L),
                getTestVehicleModel(59L)

        ));
    }

    public static VehicleModel newValidVehicleModel(Long id) {
        VehicleModel model = newValidVehicleModel();
        model.setId(id);
        return model;
    }

    public static VehicleModel newValidVehicleModel() {
        VehicleModel model = new VehicleModel();
        model.setName(MODEL_NAME_VALID_1);
        model.setMake(MODEL_MAKE_VALID_1);
        model.setYears(new HashSet<>(MODEL_YEARS_VALID_1));
        return model;
    }

}
