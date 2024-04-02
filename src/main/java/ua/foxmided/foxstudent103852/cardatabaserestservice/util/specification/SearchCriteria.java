package ua.foxmided.foxstudent103852.cardatabaserestservice.util.specification;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Getter
@NoArgsConstructor
public class SearchCriteria {

    private String key;

    private String operation;

    private Object value;

    private boolean ascending = true;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SearchCriteria(String key, boolean ascending) {
        this.key = key;
        this.operation = "sort";
        this.ascending = ascending;
    }

    public static SearchCriteria of(String key, String operation, Object value) {
        return new SearchCriteria(key, operation, value);
    }

}
