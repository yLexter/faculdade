package interfaces;

import java.util.List;
import java.util.Map;

public interface IBenchmark<T extends Comparable<T>> {
    Map<String, List<List<T>>> getMassTestForInsert ();
    Map<String, List<List<T>>> getMassTestForSearch();
}
