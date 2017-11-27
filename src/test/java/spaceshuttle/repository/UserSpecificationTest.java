package spaceshuttle.repository;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class UserSpecificationTest {
    @Test
    public void toPredicate() throws Exception {
        SearchCriteria nullSearchCriteria = null;
        Optional<SearchCriteria> searchCriteriaOptional = Optional.ofNullable(nullSearchCriteria);
        if (searchCriteriaOptional.isPresent()) {
            SearchCriteria searchCriteria = searchCriteriaOptional.get();
        } else {
            System.out.println("null search criteria");
        }

    }

    @Test(expected = NullPointerException.class)
    public void testException() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse("1990-01-01");
        } catch (ParseException e) {
            ///
        }
        throwNullPointException();
    }

    private void throwNullPointException() throws NullPointerException {
        throw new NullPointerException();
    }

}