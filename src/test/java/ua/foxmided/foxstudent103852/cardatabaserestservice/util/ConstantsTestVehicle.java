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
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.Vehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleCategory;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;

public final class ConstantsTestVehicle {
    public static final Long VEHICLE_ID_VALID_1 = 3L;
    public static final Long VEHICLE_ID_VALID_2 = 7L;
    public static final Long VEHICLE_ID_FOR_UPDATE = 5L;
    public static final Long VEHICLE_ID_FOR_DELETE = 5L;
    public static final Long VEHICLE_ID_INVALID = 0L;
    public static final Long VEHICLE_ID_NOT_EXIST = 99L;

    public static final String VEHICLE_OBJECT_ID_VALID_1 = "ElhqsRZDnP";
    public static final String VEHICLE_OBJECT_ID_VALID_2 = "7G1VT2pSNO";
    public static final String VEHICLE_OBJECT_ID_VALID_FOR_ADD = "Elh0000DnP";
    public static final String VEHICLE_OBJECT_ID_VALID_FOR_UPDATE = "7G18888SNO";
    public static final String VEHICLE_OBJECT_ID_INVALID_1 = "Sierra2500HDCrewCab";

    public static final VehicleModel VEHICLE_MODEL_VALID_1 = ConstantsTestVehicleModel
            .getTestVehicleModel(ConstantsTestVehicleModel.MODEL_ID_VALID_1);
    public static final VehicleModel VEHICLE_MODEL_VALID_2 = ConstantsTestVehicleModel
            .getTestVehicleModel(ConstantsTestVehicleModel.MODEL_ID_VALID_2);
    public static final VehicleModel VEHICLE_MODEL_VALID_FOR_ADD = ConstantsTestVehicleModel
            .getTestVehicleModel(ConstantsTestVehicleModel.MODEL_ID_FOR_DELETE);
    public static final VehicleModel VEHICLE_MODEL_VALID_FOR_UPDATE = ConstantsTestVehicleModel
            .getTestVehicleModel(ConstantsTestVehicleModel.MODEL_ID_FOR_DELETE);
    public static final VehicleModel VEHICLE_MODEL_INVALID_1 = ConstantsTestVehicleModel.NOT_EXIST_MODEL;

    public static final ModelYear VEHICLE_YEAR_VALID_1 = ConstantsTestModelYear.getTestModelYear(1L);
    public static final ModelYear VEHICLE_YEAR_VALID_2 = ConstantsTestModelYear.getTestModelYear(2L);
    public static final ModelYear VEHICLE_YEAR_FOR_ADD = ConstantsTestModelYear.getTestModelYear(2L);
    public static final ModelYear VEHICLE_YEAR_INVALID_1 = ConstantsTestModelYear.NOT_EXIST_YEAR;

    public static final Set<VehicleCategory> VEHICLE_CATEGORIES_VALID_1 = Set
            .of(ConstantsTestVehicleCategory.getTestVehicleCategory(1L));
    public static final Set<VehicleCategory> VEHICLE_CATEGORIES_VALID_2 = Set.of(
            ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
            ConstantsTestVehicleCategory.getTestVehicleCategory(8L));
    public static final Set<VehicleCategory> VEHICLE_CATEGORIES_FOR_ADD = Set.of(
            ConstantsTestVehicleCategory.getTestVehicleCategory(1L),
            ConstantsTestVehicleCategory.getTestVehicleCategory(4L),
            ConstantsTestVehicleCategory.getTestVehicleCategory(5L));
    public static final Set<VehicleCategory> VEHICLE_CATEGORIES_INVALID_1 = Set
            .of(ConstantsTestVehicleCategory.NOT_EXIST_CATEGORY);

    public static final Vehicle NOT_EXIST_VEHICLE = getTestVehicle(VEHICLE_ID_NOT_EXIST);
    public static final Vehicle VEHICLE_VALID_1 = getTestVehicle(VEHICLE_ID_VALID_1);
    public static final Vehicle VEHICLE_VALID_2 = getTestVehicle(VEHICLE_ID_VALID_2);

    private ConstantsTestVehicle() {
    }

    public static Vehicle getTestVehicle(@NotNull Long id) {
        Map<Long, Long> idMap = new HashMap<>();
        Map<Long, String> objectIdMap = new HashMap<>();
        Map<Long, VehicleModel> modelMap = new HashMap<>();
        Map<Long, ModelYear> yearMap = new HashMap<>();
        Map<Long, Set<VehicleCategory>> categoriesMap = new HashMap<>();

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
        idMap.put(60L, 60L);
        idMap.put(61L, 61L);
        idMap.put(62L, 62L);
        idMap.put(63L, 63L);
        idMap.put(64L, 64L);
        idMap.put(65L, 65L);
        idMap.put(66L, 66L);
        idMap.put(67L, 67L);
        idMap.put(68L, 68L);
        idMap.put(69L, 69L);
        idMap.put(70L, 70L);
        idMap.put(71L, 71L);
        idMap.put(72L, 72L);
        idMap.put(73L, 73L);
        idMap.put(74L, 74L);
        idMap.put(75L, 75L);
        idMap.put(76L, 76L);

        objectIdMap.put(1L, "ZRgPP9dBMm");
        objectIdMap.put(2L, "cptB1C1NSL");
        objectIdMap.put(3L, "ElhqsRZDnP");
        objectIdMap.put(4L, "LUzyWMYJpW");
        objectIdMap.put(5L, "rDkHakOBKP");
        objectIdMap.put(6L, "1JARpJ2AUB");
        objectIdMap.put(7L, "ATAeU1NdOi");
        objectIdMap.put(8L, "92pfQo6pA4");
        objectIdMap.put(9L, "MTvnyKqtYJ");
        objectIdMap.put(10L, "70EmSYGau7");
        objectIdMap.put(11L, "xxKBCB6qe4");
        objectIdMap.put(12L, "KdEmV75BEr");
        objectIdMap.put(13L, "HPTe5REepK");
        objectIdMap.put(14L, "sLLtnh5CNL");
        objectIdMap.put(15L, "HrfJ77bDEq");
        objectIdMap.put(16L, "WHqkPTraez");
        objectIdMap.put(17L, "juIznkRAZE");
        objectIdMap.put(18L, "7G1VT2pSNO");
        objectIdMap.put(19L, "aKCqI0lYGI");
        objectIdMap.put(20L, "red9UTu8fA");
        objectIdMap.put(21L, "YP2Mf2anan");
        objectIdMap.put(22L, "SlPnx4lV12");
        objectIdMap.put(23L, "iyy1mCT0X7");
        objectIdMap.put(24L, "AAcjf3pwAe");
        objectIdMap.put(25L, "4QggYHUIyn");
        objectIdMap.put(26L, "TlAKQyIkbO");
        objectIdMap.put(27L, "5kvP2xK8Zc");
        objectIdMap.put(28L, "ke58pHKolD");
        objectIdMap.put(29L, "YQn0OjR3Vp");
        objectIdMap.put(30L, "nOwbBojNYK");
        objectIdMap.put(31L, "MDE6nmecu4");
        objectIdMap.put(32L, "EZ6OHmNdq3");
        objectIdMap.put(33L, "90ckH5XUlB");
        objectIdMap.put(34L, "fZfrBTKgOD");
        objectIdMap.put(35L, "ooBVQPY9Md");
        objectIdMap.put(36L, "wCe6mXS7cu");
        objectIdMap.put(37L, "I1fCpSi5vr");
        objectIdMap.put(38L, "EyIxl3I9JF");
        objectIdMap.put(39L, "W8yyG8Jvfv");
        objectIdMap.put(40L, "FLGvMfrdhi");
        objectIdMap.put(41L, "tATlV3NErc");
        objectIdMap.put(42L, "0iYfvjSVVA");
        objectIdMap.put(43L, "2T8tswBY7h");
        objectIdMap.put(44L, "HuhKrgTRRk");
        objectIdMap.put(45L, "AL4d4Lr9yM");
        objectIdMap.put(46L, "WpVAtULJ1x");
        objectIdMap.put(47L, "PDtoCcGUWV");
        objectIdMap.put(48L, "cTsu4dqerf");
        objectIdMap.put(49L, "BeTPG4DWB9");
        objectIdMap.put(50L, "d1GoCyrJ5P");
        objectIdMap.put(51L, "ma1qSx0NyR");
        objectIdMap.put(52L, "S8oUQpqe2A");
        objectIdMap.put(53L, "OJ6LZaOkpc");
        objectIdMap.put(54L, "i8m4jphbMM");
        objectIdMap.put(55L, "jRm7mynJ1f");
        objectIdMap.put(56L, "rwWR90EA8f");
        objectIdMap.put(57L, "GRyD0Bk2Qa");
        objectIdMap.put(58L, "fdOB0cbBIV");
        objectIdMap.put(59L, "xcDB2sPg3Z");
        objectIdMap.put(60L, "9JdzZZJpGn");
        objectIdMap.put(61L, "K0i2OP3LuX");
        objectIdMap.put(62L, "oEFrxR8Ds3");
        objectIdMap.put(63L, "sUYixsCRCS");
        objectIdMap.put(64L, "qtKp8KTMGE");
        objectIdMap.put(65L, "HOCGeAGgks");
        objectIdMap.put(66L, "3oiBJNFbT8");
        objectIdMap.put(67L, "zhx5QK2JQH");
        objectIdMap.put(68L, "li2lH2NchB");
        objectIdMap.put(69L, "5LE24mfM5W");
        objectIdMap.put(70L, "MGR1WnJ3pP");
        objectIdMap.put(71L, "Ak3WrP6JcW");
        objectIdMap.put(72L, "5J7tDHJ3S4");
        objectIdMap.put(73L, "lgJX6MSDwC");
        objectIdMap.put(74L, "VIBnngsUrr");
        objectIdMap.put(75L, "IIjsbeK3Ay");
        objectIdMap.put(76L, "nfGJu6tXoS");

        modelMap.put(1L, ConstantsTestVehicleModel.getTestVehicleModel(1L));
        modelMap.put(2L, ConstantsTestVehicleModel.getTestVehicleModel(2L));
        modelMap.put(3L, ConstantsTestVehicleModel.getTestVehicleModel(3L));
        modelMap.put(4L, ConstantsTestVehicleModel.getTestVehicleModel(4L));
        modelMap.put(5L, ConstantsTestVehicleModel.getTestVehicleModel(5L));
        modelMap.put(6L, ConstantsTestVehicleModel.getTestVehicleModel(6L));
        modelMap.put(7L, ConstantsTestVehicleModel.getTestVehicleModel(7L));
        modelMap.put(8L, ConstantsTestVehicleModel.getTestVehicleModel(8L));
        modelMap.put(9L, ConstantsTestVehicleModel.getTestVehicleModel(9L));
        modelMap.put(10L, ConstantsTestVehicleModel.getTestVehicleModel(10L));
        modelMap.put(11L, ConstantsTestVehicleModel.getTestVehicleModel(11L));
        modelMap.put(12L, ConstantsTestVehicleModel.getTestVehicleModel(12L));
        modelMap.put(13L, ConstantsTestVehicleModel.getTestVehicleModel(13L));
        modelMap.put(14L, ConstantsTestVehicleModel.getTestVehicleModel(14L));
        modelMap.put(15L, ConstantsTestVehicleModel.getTestVehicleModel(15L));
        modelMap.put(16L, ConstantsTestVehicleModel.getTestVehicleModel(16L));
        modelMap.put(17L, ConstantsTestVehicleModel.getTestVehicleModel(17L));
        modelMap.put(18L, ConstantsTestVehicleModel.getTestVehicleModel(7L));
        modelMap.put(19L, ConstantsTestVehicleModel.getTestVehicleModel(18L));
        modelMap.put(20L, ConstantsTestVehicleModel.getTestVehicleModel(19L));
        modelMap.put(21L, ConstantsTestVehicleModel.getTestVehicleModel(20L));
        modelMap.put(22L, ConstantsTestVehicleModel.getTestVehicleModel(21L));
        modelMap.put(23L, ConstantsTestVehicleModel.getTestVehicleModel(12L));
        modelMap.put(24L, ConstantsTestVehicleModel.getTestVehicleModel(22L));
        modelMap.put(25L, ConstantsTestVehicleModel.getTestVehicleModel(23L));
        modelMap.put(26L, ConstantsTestVehicleModel.getTestVehicleModel(24L));
        modelMap.put(27L, ConstantsTestVehicleModel.getTestVehicleModel(25L));
        modelMap.put(28L, ConstantsTestVehicleModel.getTestVehicleModel(26L));
        modelMap.put(29L, ConstantsTestVehicleModel.getTestVehicleModel(27L));
        modelMap.put(30L, ConstantsTestVehicleModel.getTestVehicleModel(14L));
        modelMap.put(31L, ConstantsTestVehicleModel.getTestVehicleModel(28L));
        modelMap.put(32L, ConstantsTestVehicleModel.getTestVehicleModel(29L));
        modelMap.put(33L, ConstantsTestVehicleModel.getTestVehicleModel(30L));
        modelMap.put(34L, ConstantsTestVehicleModel.getTestVehicleModel(31L));
        modelMap.put(35L, ConstantsTestVehicleModel.getTestVehicleModel(32L));
        modelMap.put(36L, ConstantsTestVehicleModel.getTestVehicleModel(33L));
        modelMap.put(37L, ConstantsTestVehicleModel.getTestVehicleModel(1L));
        modelMap.put(38L, ConstantsTestVehicleModel.getTestVehicleModel(34L));
        modelMap.put(39L, ConstantsTestVehicleModel.getTestVehicleModel(35L));
        modelMap.put(40L, ConstantsTestVehicleModel.getTestVehicleModel(32L));
        modelMap.put(41L, ConstantsTestVehicleModel.getTestVehicleModel(36L));
        modelMap.put(42L, ConstantsTestVehicleModel.getTestVehicleModel(7L));
        modelMap.put(43L, ConstantsTestVehicleModel.getTestVehicleModel(7L));
        modelMap.put(44L, ConstantsTestVehicleModel.getTestVehicleModel(28L));
        modelMap.put(45L, ConstantsTestVehicleModel.getTestVehicleModel(37L));
        modelMap.put(46L, ConstantsTestVehicleModel.getTestVehicleModel(18L));
        modelMap.put(47L, ConstantsTestVehicleModel.getTestVehicleModel(14L));
        modelMap.put(48L, ConstantsTestVehicleModel.getTestVehicleModel(38L));
        modelMap.put(49L, ConstantsTestVehicleModel.getTestVehicleModel(39L));
        modelMap.put(50L, ConstantsTestVehicleModel.getTestVehicleModel(40L));
        modelMap.put(51L, ConstantsTestVehicleModel.getTestVehicleModel(41L));
        modelMap.put(52L, ConstantsTestVehicleModel.getTestVehicleModel(42L));
        modelMap.put(53L, ConstantsTestVehicleModel.getTestVehicleModel(43L));
        modelMap.put(54L, ConstantsTestVehicleModel.getTestVehicleModel(44L));
        modelMap.put(55L, ConstantsTestVehicleModel.getTestVehicleModel(45L));
        modelMap.put(56L, ConstantsTestVehicleModel.getTestVehicleModel(46L));
        modelMap.put(57L, ConstantsTestVehicleModel.getTestVehicleModel(7L));
        modelMap.put(58L, ConstantsTestVehicleModel.getTestVehicleModel(44L));
        modelMap.put(59L, ConstantsTestVehicleModel.getTestVehicleModel(47L));
        modelMap.put(60L, ConstantsTestVehicleModel.getTestVehicleModel(48L));
        modelMap.put(61L, ConstantsTestVehicleModel.getTestVehicleModel(49L));
        modelMap.put(62L, ConstantsTestVehicleModel.getTestVehicleModel(44L));
        modelMap.put(63L, ConstantsTestVehicleModel.getTestVehicleModel(50L));
        modelMap.put(64L, ConstantsTestVehicleModel.getTestVehicleModel(51L));
        modelMap.put(65L, ConstantsTestVehicleModel.getTestVehicleModel(52L));
        modelMap.put(66L, ConstantsTestVehicleModel.getTestVehicleModel(51L));
        modelMap.put(67L, ConstantsTestVehicleModel.getTestVehicleModel(53L));
        modelMap.put(68L, ConstantsTestVehicleModel.getTestVehicleModel(54L));
        modelMap.put(69L, ConstantsTestVehicleModel.getTestVehicleModel(55L));
        modelMap.put(70L, ConstantsTestVehicleModel.getTestVehicleModel(56L));
        modelMap.put(71L, ConstantsTestVehicleModel.getTestVehicleModel(57L));
        modelMap.put(72L, ConstantsTestVehicleModel.getTestVehicleModel(58L));
        modelMap.put(73L, ConstantsTestVehicleModel.getTestVehicleModel(56L));
        modelMap.put(74L, ConstantsTestVehicleModel.getTestVehicleModel(57L));
        modelMap.put(75L, ConstantsTestVehicleModel.getTestVehicleModel(12L));
        modelMap.put(76L, ConstantsTestVehicleModel.getTestVehicleModel(59L));

        yearMap.put(1L, ConstantsTestModelYear.getTestModelYear(1L));
        yearMap.put(2L, ConstantsTestModelYear.getTestModelYear(1L));
        yearMap.put(3L, ConstantsTestModelYear.getTestModelYear(1L));
        yearMap.put(4L, ConstantsTestModelYear.getTestModelYear(1L));
        yearMap.put(5L, ConstantsTestModelYear.getTestModelYear(1L));
        yearMap.put(6L, ConstantsTestModelYear.getTestModelYear(1L));
        yearMap.put(7L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(8L, ConstantsTestModelYear.getTestModelYear(1L));
        yearMap.put(9L, ConstantsTestModelYear.getTestModelYear(1L));
        yearMap.put(10L, ConstantsTestModelYear.getTestModelYear(1L));
        yearMap.put(11L, ConstantsTestModelYear.getTestModelYear(1L));
        yearMap.put(12L, ConstantsTestModelYear.getTestModelYear(1L));
        yearMap.put(13L, ConstantsTestModelYear.getTestModelYear(1L));
        yearMap.put(14L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(15L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(16L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(17L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(18L, ConstantsTestModelYear.getTestModelYear(1L));
        yearMap.put(19L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(20L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(21L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(22L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(23L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(24L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(25L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(26L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(27L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(28L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(29L, ConstantsTestModelYear.getTestModelYear(2L));
        yearMap.put(30L, ConstantsTestModelYear.getTestModelYear(3L));
        yearMap.put(31L, ConstantsTestModelYear.getTestModelYear(3L));
        yearMap.put(32L, ConstantsTestModelYear.getTestModelYear(3L));
        yearMap.put(33L, ConstantsTestModelYear.getTestModelYear(4L));
        yearMap.put(34L, ConstantsTestModelYear.getTestModelYear(4L));
        yearMap.put(35L, ConstantsTestModelYear.getTestModelYear(4L));
        yearMap.put(36L, ConstantsTestModelYear.getTestModelYear(4L));
        yearMap.put(37L, ConstantsTestModelYear.getTestModelYear(5L));
        yearMap.put(38L, ConstantsTestModelYear.getTestModelYear(5L));
        yearMap.put(39L, ConstantsTestModelYear.getTestModelYear(5L));
        yearMap.put(40L, ConstantsTestModelYear.getTestModelYear(6L));
        yearMap.put(41L, ConstantsTestModelYear.getTestModelYear(6L));
        yearMap.put(42L, ConstantsTestModelYear.getTestModelYear(7L));
        yearMap.put(43L, ConstantsTestModelYear.getTestModelYear(8L));
        yearMap.put(44L, ConstantsTestModelYear.getTestModelYear(8L));
        yearMap.put(45L, ConstantsTestModelYear.getTestModelYear(8L));
        yearMap.put(46L, ConstantsTestModelYear.getTestModelYear(9L));
        yearMap.put(47L, ConstantsTestModelYear.getTestModelYear(10L));
        yearMap.put(48L, ConstantsTestModelYear.getTestModelYear(10L));
        yearMap.put(49L, ConstantsTestModelYear.getTestModelYear(10L));
        yearMap.put(50L, ConstantsTestModelYear.getTestModelYear(11L));
        yearMap.put(51L, ConstantsTestModelYear.getTestModelYear(11L));
        yearMap.put(52L, ConstantsTestModelYear.getTestModelYear(12L));
        yearMap.put(53L, ConstantsTestModelYear.getTestModelYear(12L));
        yearMap.put(54L, ConstantsTestModelYear.getTestModelYear(12L));
        yearMap.put(55L, ConstantsTestModelYear.getTestModelYear(13L));
        yearMap.put(56L, ConstantsTestModelYear.getTestModelYear(14L));
        yearMap.put(57L, ConstantsTestModelYear.getTestModelYear(15L));
        yearMap.put(58L, ConstantsTestModelYear.getTestModelYear(16L));
        yearMap.put(59L, ConstantsTestModelYear.getTestModelYear(16L));
        yearMap.put(60L, ConstantsTestModelYear.getTestModelYear(17L));
        yearMap.put(61L, ConstantsTestModelYear.getTestModelYear(18L));
        yearMap.put(62L, ConstantsTestModelYear.getTestModelYear(19L));
        yearMap.put(63L, ConstantsTestModelYear.getTestModelYear(20L));
        yearMap.put(64L, ConstantsTestModelYear.getTestModelYear(21L));
        yearMap.put(65L, ConstantsTestModelYear.getTestModelYear(22L));
        yearMap.put(66L, ConstantsTestModelYear.getTestModelYear(23L));
        yearMap.put(67L, ConstantsTestModelYear.getTestModelYear(23L));
        yearMap.put(68L, ConstantsTestModelYear.getTestModelYear(24L));
        yearMap.put(69L, ConstantsTestModelYear.getTestModelYear(24L));
        yearMap.put(70L, ConstantsTestModelYear.getTestModelYear(24L));
        yearMap.put(71L, ConstantsTestModelYear.getTestModelYear(24L));
        yearMap.put(72L, ConstantsTestModelYear.getTestModelYear(25L));
        yearMap.put(73L, ConstantsTestModelYear.getTestModelYear(25L));
        yearMap.put(74L, ConstantsTestModelYear.getTestModelYear(25L));
        yearMap.put(75L, ConstantsTestModelYear.getTestModelYear(26L));
        yearMap.put(76L, ConstantsTestModelYear.getTestModelYear(26L));

        categoriesMap.put(1L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(1L)));
        categoriesMap.put(2L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(2L)));
        categoriesMap.put(3L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(1L)));
        categoriesMap.put(4L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(3L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(5L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(2L)));
        categoriesMap.put(6L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(4L)));
        categoriesMap.put(7L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(8L)));
        categoriesMap.put(8L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(4L)));
        categoriesMap.put(9L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(5L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(3L)));
        categoriesMap.put(10L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(5L)));
        categoriesMap.put(11L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(12L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(1L)));
        categoriesMap.put(13L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(14L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(2L)));
        categoriesMap.put(15L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(3L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(16L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(1L)));
        categoriesMap.put(17L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(3L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(18L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(2L)));
        categoriesMap.put(19L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(3L)));
        categoriesMap.put(20L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(21L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(7L)));
        categoriesMap.put(22L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(7L)));
        categoriesMap.put(23L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(1L)));
        categoriesMap.put(24L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(2L)));
        categoriesMap.put(25L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(3L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(6L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(8L)));
        categoriesMap.put(26L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(27L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(8L)));
        categoriesMap.put(28L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(5L)));
        categoriesMap.put(29L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(5L)));
        categoriesMap.put(30L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(31L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(32L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(8L)));
        categoriesMap.put(33L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(34L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(35L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(3L)));
        categoriesMap.put(36L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(37L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(1L)));
        categoriesMap.put(38L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(39L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(6L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(3L)));
        categoriesMap.put(40L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(3L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(41L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(8L)));
        categoriesMap.put(42L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(8L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(2L)));
        categoriesMap.put(43L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(3L)));
        categoriesMap.put(44L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(6L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(3L)));
        categoriesMap.put(45L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(8L)));
        categoriesMap.put(46L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(47L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(8L)));
        categoriesMap.put(48L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(3L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(6L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(8L)));
        categoriesMap.put(49L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(6L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(8L)));
        categoriesMap.put(50L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(51L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(52L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(53L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(54L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(55L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(56L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(1L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(4L)));
        categoriesMap.put(57L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(3L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(6L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(8L)));
        categoriesMap.put(58L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(59L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(60L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(61L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(62L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(63L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(5L)));
        categoriesMap.put(64L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(65L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(66L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(67L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(2L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(3L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(8L),
                ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(68L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(69L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(70L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(5L)));
        categoriesMap.put(71L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(72L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(73L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(5L)));
        categoriesMap.put(74L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));
        categoriesMap.put(75L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(1L)));
        categoriesMap.put(76L, Set.of(ConstantsTestVehicleCategory.getTestVehicleCategory(6L)));

        // id = VEHICLE_ID_NOT_EXIST
        idMap.put(VEHICLE_ID_NOT_EXIST, VEHICLE_ID_NOT_EXIST);
        objectIdMap.put(VEHICLE_ID_NOT_EXIST, "NotExist");
        modelMap.put(VEHICLE_ID_NOT_EXIST, ConstantsTestVehicleModel.NOT_EXIST_MODEL);
        yearMap.put(VEHICLE_ID_NOT_EXIST, ConstantsTestModelYear.NOT_EXIST_YEAR);
        categoriesMap.put(VEHICLE_ID_NOT_EXIST, Set.of(ConstantsTestVehicleCategory.NOT_EXIST_CATEGORY));

        Vehicle vehicle = new Vehicle();
        vehicle.setId(idMap.get(id));
        vehicle.setObjectId(objectIdMap.get(id));
        vehicle.setModel(modelMap.get(id));
        vehicle.setYear(yearMap.get(id));
        vehicle.setCategories(new HashSet<>(categoriesMap.get(id)));
        return vehicle;
    }

    public static List<Vehicle> getAllTestVehicles() {
        return new ArrayList<Vehicle>(Arrays.asList(

                getTestVehicle(1L),
                getTestVehicle(2L),
                getTestVehicle(3L),
                getTestVehicle(4L),
                getTestVehicle(5L),
                getTestVehicle(6L),
                getTestVehicle(7L),
                getTestVehicle(8L),
                getTestVehicle(9L),
                getTestVehicle(10L),
                getTestVehicle(11L),
                getTestVehicle(12L),
                getTestVehicle(13L),
                getTestVehicle(14L),
                getTestVehicle(15L),
                getTestVehicle(16L),
                getTestVehicle(17L),
                getTestVehicle(18L),
                getTestVehicle(19L),
                getTestVehicle(20L),
                getTestVehicle(21L),
                getTestVehicle(22L),
                getTestVehicle(23L),
                getTestVehicle(24L),
                getTestVehicle(25L),
                getTestVehicle(26L),
                getTestVehicle(27L),
                getTestVehicle(28L),
                getTestVehicle(29L),
                getTestVehicle(30L),
                getTestVehicle(31L),
                getTestVehicle(32L),
                getTestVehicle(33L),
                getTestVehicle(34L),
                getTestVehicle(35L),
                getTestVehicle(36L),
                getTestVehicle(37L),
                getTestVehicle(38L),
                getTestVehicle(39L),
                getTestVehicle(40L),
                getTestVehicle(41L),
                getTestVehicle(42L),
                getTestVehicle(43L),
                getTestVehicle(44L),
                getTestVehicle(45L),
                getTestVehicle(46L),
                getTestVehicle(47L),
                getTestVehicle(48L),
                getTestVehicle(49L),
                getTestVehicle(50L),
                getTestVehicle(51L),
                getTestVehicle(52L),
                getTestVehicle(53L),
                getTestVehicle(54L),
                getTestVehicle(55L),
                getTestVehicle(56L),
                getTestVehicle(57L),
                getTestVehicle(58L),
                getTestVehicle(59L),
                getTestVehicle(60L),
                getTestVehicle(61L),
                getTestVehicle(62L),
                getTestVehicle(63L),
                getTestVehicle(64L),
                getTestVehicle(65L),
                getTestVehicle(66L),
                getTestVehicle(67L),
                getTestVehicle(68L),
                getTestVehicle(69L),
                getTestVehicle(70L),
                getTestVehicle(71L),
                getTestVehicle(72L),
                getTestVehicle(73L),
                getTestVehicle(74L),
                getTestVehicle(75L),
                getTestVehicle(76L)

        ));
    }

    public static Vehicle newValidVehicle(Long id) {
        Vehicle vehicle = newValidVehicle();
        vehicle.setId(id);
        return vehicle;
    }

    public static Vehicle newValidVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setObjectId(VEHICLE_OBJECT_ID_VALID_1);
        vehicle.setModel(VEHICLE_MODEL_VALID_1);
        vehicle.setYear(VEHICLE_YEAR_VALID_1);
        vehicle.setCategories(new HashSet<>(VEHICLE_CATEGORIES_VALID_1));
        return vehicle;
    }

}
