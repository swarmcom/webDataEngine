package controllers;

import api.domain.BeanDomain;
import api.domain.Meeting;
import api.service.MeetingService;
import auth.SessionAuthenticatedAction;
import com.fasterxml.jackson.databind.node.ArrayNode;
import managers.AppProfileManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.pac4j.play.java.Secure;
import org.springframework.stereotype.Component;
import play.Logger;
import play.libs.Json;
import play.mvc.*;
import security.encoder.SecurityPasswordEncoder;
import security.util.EncoderUtil;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@With(SessionAuthenticatedAction.class)
@Secure(clients = "DirectBasicAuthClient, DirectDigestAuthClient", authorizers = "admin")
public class Meetings extends SimpleEntityController {

    @Inject
    MeetingService meetingService;

    @Inject
    private SecurityPasswordEncoder passwordEncoder;

    @Inject
    protected AppProfileManager appProfileManager;

    @Override
    protected BeanDomain addAbstract() throws Exception {
        Meeting meetingToAdd = new Meeting();
        mergeDefaults(meetingToAdd, getDefaultsJSON());
        merge(meetingToAdd);
        meetingToAdd.setPassword(meetingToAdd.getPassword());
        return meetingService.saveMeeting(appProfileManager.getSessionAccountId(ctx()), meetingToAdd);
        //return null;
    }

    @Override
    protected BeanDomain getByNameAbstract(String name) throws Exception {
        return meetingService.getMeeting(appProfileManager.getSessionAccountId(ctx()), name);
    }

    @Override
    protected BeanDomain getByIdAbstract(String id) throws Exception {
        return meetingService.getMeetingById(appProfileManager.getSessionAccountId(ctx()), id);
    }

    @Override
    protected Long deleteByNameAbstract(String name) throws Exception {
        return meetingService.deleteMeeting(appProfileManager.getSessionAccountId(ctx()), name);
    }

    @Override
    protected Long deleteByAccountNameAbstract(String accountName) throws Exception {
        return meetingService.deleteMeetings(accountName);
    }

    @Override
    protected Long deleteListAbstract() throws Exception {
        List<String> idsArray = convertIds();
        return meetingService.deleteMeetings(appProfileManager.getSessionAccountId(ctx()), idsArray);
    }

    @Override
    protected BeanDomain modifyByNameAbstract(String name) throws Exception {
        Meeting existingMeeting = meetingService.getMeeting(appProfileManager.getSessionAccountId(ctx()), name);
        merge(existingMeeting);
        existingMeeting.setPassword(passwordEncoder.encode(existingMeeting.getPassword()));
        return (existingMeeting != null ? meetingService.saveMeeting(appProfileManager.getSessionAccountId(ctx()), existingMeeting) : null);
    }

    @Override
    protected BeanDomain modifyByIdAbstract(String id) throws Exception {
        Meeting existingMeeting = meetingService.getMeetingById(appProfileManager.getSessionAccountId(ctx()), id);
        merge(existingMeeting);
        existingMeeting.setPassword(existingMeeting.getPassword());
        return (existingMeeting != null ? meetingService.saveMeeting(appProfileManager.getSessionAccountId(ctx()), existingMeeting) : null);
    }

    @Override
    protected List<? extends BeanDomain> listAbstract() throws Exception {
        return meetingService.getMeetings(appProfileManager.getSessionAccountId(ctx()));
    }

    @Override
    protected ArrayNode listArrayAbstract() throws Exception {
        List<? extends Meeting> meetings = meetingService.getMeetings(appProfileManager.getSessionAccountId(ctx()));
        return createMeetingArrayJson(meetings);
    }

    protected ArrayNode createMeetingArrayJson(List<? extends Meeting> meetings) {
        ArrayNode node = Json.newArray();
        for (Meeting meeting : meetings) {
            ArrayNode itemNode = Json.newArray();
            itemNode.add(meeting.getId());
            itemNode.add(meeting.getAccountId());
            //itemNode.add(meeting.getRoomId());
            itemNode.add(meeting.getMeetingName());
            itemNode.add(meeting.getPassword());
            itemNode.add(meeting.getDuration());
            itemNode.add(meeting.getParticipants());
            node.add(itemNode);
        }
        return node;
    }

    public Result getTemplate(String key) {
        return getTemplate(key,"/public/app/templates/meetings-template.json");
    }

    @Override
    protected String getDefaultsJSON() {
        return getTemplateJSON("settings_defaults", "/public/app/templates/meetings-template.json");
    }
}
