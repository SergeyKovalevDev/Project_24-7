package ru.sf.comparators;

import org.apache.commons.lang3.StringUtils;
import ru.sf.models.University;

import java.net.URL;

public class UniversityWebsiteComparator implements UniversityComparatorInterface {
    @Override
    public int compare(University o1, University o2) {
        URL u1 = o1.getWebsite();
        URL u2 = o2.getWebsite();
        if (u1 == null && u2 == null) return 0;
        else if (u1 == null) return -1;
        else if (u2 == null) return 1;
        else return StringUtils.compare(o1.getWebsite().toString(), o2.getWebsite().toString());
    }
}
