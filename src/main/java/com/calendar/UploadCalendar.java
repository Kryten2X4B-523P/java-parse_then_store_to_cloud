package com.calendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.TimeZone;

import static com.calendar.DefineCalendarIcs.stringConvertToDate;
import static com.calendar.DefineCalendarIcs.stringConvertToTime;

public class UploadCalendar {
    final static Logger log = Logger.getLogger(UploadCalendar.class);
    private final String GOOGLE_CALENDAR_NAME = "primary";
    private Calendar service;

    public void deleteAllEvents() throws IOException {
        DateTime now = new DateTime("2020-05-28T17:00:00-07:00");
        Events events = this.service.events().list(GOOGLE_CALENDAR_NAME).setMaxResults(9000).setTimeMin(now).setOrderBy("startTime").setSingleEvents(true).execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            log.info("No upcoming events found.");
        } else {
            log.info("Events deleted:");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                this.service.events().delete();
                log.info(event.getSummary() + " " + start);
            }
        }
    }

    public void createEvent(String time, String year, String dateWithDot, Integer addMinutes, String summary) throws ParseException, IOException {
        Event event = new Event().setSummary(summary);

        boolean containsAlphas = dateWithDot.matches(".*[a-zA-Z]+.*");
        if (containsAlphas) {
            log.error("Chyba v excelu");
            throw new RuntimeException("Chyba v excelu");
        }


        event = this.service.events().insert(GOOGLE_CALENDAR_NAME, event).execute();
        log.info("Event created: " + event.getHtmlLink());
        //System.out.printf("Event created: %s\n", event.getHtmlLink());
    }


    public void build() throws IOException, GeneralSecurityException, ParseException {
        this.service = new GoogleApi().build();

        deleteAllEvents();
    }
}