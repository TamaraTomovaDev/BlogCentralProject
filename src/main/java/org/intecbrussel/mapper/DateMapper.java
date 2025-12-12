package org.intecbrussel.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateMapper {

    private DateMapper() { }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date == null ? null :
                date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
    }
}
