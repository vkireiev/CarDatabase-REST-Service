package ua.foxmided.foxstudent103852.cardatabaserestservice.util;

import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SortHelper {

    public static boolean isValidSortProperties(Sort sort, Set<String> allowedParameters) {
        return sort.stream()
                .map(Order::getProperty)
                .allMatch(allowedParameters::contains);
    }

}