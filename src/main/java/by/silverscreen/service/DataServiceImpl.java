package by.silverscreen.service;

import by.silverscreen.DAO.JooqRepository;
import by.silverscreen.DAO.PhoneDAO;
import by.silverscreen.Entities.NotificationEntity;
import by.silverscreen.Entities.PushEntity;
import by.silverscreen.Entities.TicketEntity;
import by.silverscreen.Entities.TokenEntity;
import by.silverscreen.Utils.Cookie;
import by.silverscreen.Utils.Pusher;
import org.jooq.Record;
import org.jooq.Result;
import static by.silverscreen.jooq.tables.Phone.PHONE;
import static by.silverscreen.jooq.tables.Notifications.NOTIFICATIONS;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by sbaranau on 11/25/2016.
 */
@Service("dataService")
class DataServiceImpl implements DataService {

    private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);
    static LocalDate today = LocalDate.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
    @Autowired
    private Pusher pusher;

    @Autowired
    JooqRepository jooqRepository;
    /**
     *  call only if change token on mobile called
     *  If user install push and use it with auth - update token(password)
     *  if user install push and use it without auth - save token if it not exist
     */
    @Override
    public boolean persist(TokenEntity tokenEntity) {
        try {
            PhoneDAO phoneDAO = new PhoneDAO(tokenEntity);
                if (tokenEntity.getLogin() != null && tokenEntity.getLogin().length() > 0) {
                    jooqRepository.deleteByLogin(phoneDAO.getLogin());
                } else {
                    setAnonymous(phoneDAO);
                }
            jooqRepository.deleteByToken(phoneDAO.getToken());
            jooqRepository.persist(phoneDAO);
            return true;
        } catch (Exception e) {
            LOG.error("ERROR SAVING DATA: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public TokenEntity checkName(String name) {
        return convertSingleRecord.apply(jooqRepository.checkName(name));
    }

    @Override
    public TokenEntity checkToken(String token) {
        return convertSingleRecord.apply(jooqRepository.checkToken(token));
    }

    @Override
    public Set<TokenEntity> getAllTokens() {
        Result<Record> resultJooq = jooqRepository.getAllUsers();
        return convertRecords.apply(resultJooq);
    }

    @Override
    public Set<NotificationEntity> getNotificationForLogin(TokenEntity token) {
        Result<Record> recordRecord = jooqRepository.getNotificationsForUser(token.getLogin());
        return convertNotificationRecords.apply(recordRecord);
    }

    @Override
    public String checkFilms(TokenEntity tokenEntity, Set<NotificationEntity> notificationEntities) {
        return null;
    }

    private Predicate<Record> isNotNull = (s2) -> s2 != null;
    private BiFunction<List<String>,String, List<String>> getPhoneDAO = (tokens, type) -> {
        Predicate<Record> isIos = ((s) -> (s.getValue(PHONE.SYSTEM).contains(type)));
        return  tokens.stream().filter(token -> isNotNull.and(isIos).test((jooqRepository.checkToken(token)))).collect(Collectors.toList());
    };

    @Override
    public boolean sendPush(PushEntity pushEntity) {
        //https://github.com/Raudius/Pushraven
        if (pushEntity.getTokens().size() == 0) {
            return false;
        }
        PushEntity andPush = new PushEntity();
        andPush.setMessage(pushEntity.getMessage());
        andPush.setTitle(pushEntity.getTitle());
        PushEntity iosPush = new PushEntity();
        iosPush.setMessage(pushEntity.getMessage());
        iosPush.setTitle(pushEntity.getTitle());

        List<String> iosTokens = getPhoneDAO.apply(pushEntity.getTokens(), "ios");
        List<String> andTokens = getPhoneDAO.apply(pushEntity.getTokens(), "android");
        if (andTokens.size() > 0) {
            andPush.setTokens(andTokens);
            pusher.sendAndr(andPush);
        }
        if (iosTokens.size() > 0) {
            iosPush.setTokens(iosTokens);
            pusher.sendIos(iosPush);
        }
        // pusher.send(pushEntity);
        return true;
    }

    private Function<Result<Record>, Set<TokenEntity>> convertRecords = result -> {
        Set<TokenEntity> phoneDAOs = new LinkedHashSet<>();
        result.stream().forEach(record -> {
            TokenEntity tokenEntity = getTokenEntity(record);
            phoneDAOs.add(tokenEntity);
        });
        return  phoneDAOs;
    };

    private Function<Result<Record>, Set<NotificationEntity>> convertNotificationRecords = result -> {
        Set<NotificationEntity> notificationEntities = new HashSet<>();
        result.stream().forEach(record -> {
            NotificationEntity notificationEntity = convertNotificationEntity(record);
            notificationEntities.add(notificationEntity);
        });
        return  notificationEntities;
    };

    private TokenEntity getTokenEntity(Record record) {
        TokenEntity tokenEntity = new TokenEntity();
        if (record.getValue(PHONE.PASSWORD) != null) {
            tokenEntity.setPassword((record.getValue(PHONE.PASSWORD)));
        }
        if (record.getValue(PHONE.NAME) != null) {
            tokenEntity.setUser((record.getValue(PHONE.NAME)));
        }
        if (record.getValue(PHONE.BIRTH) != null) {
            tokenEntity.setDateOfBirth((record.getValue(PHONE.BIRTH)));
        }
        if (record.getValue(PHONE.TOKEN) != null) {
            tokenEntity.setToken((record.getValue(PHONE.TOKEN)));
        }
        if (record.getValue(PHONE.LOGIN) != null) {
            tokenEntity.setLogin(String.valueOf(record.getValue(PHONE.LOGIN)));
        }
        if (tokenEntity.getLogin().length() == 0) {
            tokenEntity.setIsman(0);
        } else {
            if (record.getValue(PHONE.ISMAN) != null) {
                tokenEntity.setIsman((record.getValue(PHONE.ISMAN).intValue()));
            }
        }
        if (record.getValue(PHONE.DATE) != null) {
            tokenEntity.setDate((record.getValue(PHONE.DATE)));
        }
        if (record.getValue(PHONE.SYSTEM) != null) {
            tokenEntity.setSystem((record.getValue(PHONE.SYSTEM)));
        }
        return tokenEntity;
    }

    private NotificationEntity convertNotificationEntity(Record record) {
        NotificationEntity notificationEntity = new NotificationEntity();
        if (record.getValue(NOTIFICATIONS.LOGIN) != null) {
            notificationEntity.setLogin((record.getValue(NOTIFICATIONS.LOGIN)));
        }
        if (record.getValue(NOTIFICATIONS.TIKETS) != null) {
            notificationEntity.setTikets((record.getValue(NOTIFICATIONS.TIKETS)));
        }
        if (record.getValue(NOTIFICATIONS.FILMS) != null && record.getValue(NOTIFICATIONS.FILMS).length() > 0) {
            notificationEntity.setFilm(Arrays.asList(record.getValue(NOTIFICATIONS.FILMS).split(",")).stream().filter(s->isNumber.test(s)).collect(Collectors.toSet()));
        }
        if (record.getValue(NOTIFICATIONS.STARTNOTIF) != null) {
            notificationEntity.setStartNotification((record.getValue(NOTIFICATIONS.STARTNOTIF)));
        }
        if (record.getValue(NOTIFICATIONS.ENDNOTIF) != null) {
            notificationEntity.setStartNotification((record.getValue(NOTIFICATIONS.ENDNOTIF)));
        }
        if (record.getValue(NOTIFICATIONS.WANTRECIEVE) != null) {
            notificationEntity.setWantReceive((record.getValue(NOTIFICATIONS.WANTRECIEVE)));
        }
        return notificationEntity;
    }

    private Function <Record, TokenEntity> convertSingleRecord = record -> {
        if (record == null) {
            return  null;
        }
        return getTokenEntity(record);
    };

    private void setAnonymous (PhoneDAO phoneDAO) {
        if (phoneDAO.getLogin() == null || phoneDAO.getLogin().length() == 0) {
            phoneDAO.setUser("Anonymous");
            phoneDAO.setPassword("-");
            phoneDAO.setLogin("");
            phoneDAO.setIsman("0");
            phoneDAO.setDateOfBirth("0");
        }
    }

    Predicate<String> isNumber = string -> {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    };

    public Set<TicketEntity> getTickets(TokenEntity tokenEntity) {
        Set<TicketEntity> result = new HashSet<>();
        if (login(tokenEntity.getLogin(), tokenEntity.getPassword())) {
            LOG.info("User:" + tokenEntity.getLogin() + " login");
            Document doc = simpleGet("/MyPage/Tickets/","/");
            if (doc != null ) {
                result = fillTickets(doc);
                LOG.info(result.toString());
            }
            if (logout()) {
                LOG.info("User:" + tokenEntity.getLogin() + " logout");
            }
        }
        return result;
    }

    private String getCodeLogin() {
        try {
            Connection.Response res = Jsoup.connect("http://www.silverscreen.by").timeout(3 * 1000)
                    .referrer("http://www.silverscreen.by/MyPage/Register/")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0 FirePHP/0.7.")
                    .header("Host", "www.silverscreen.by")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
                    //  conn.setRequestProperty("Accept-Encoding","gzip, deflate");
                    .header("x-insight", "activate")
                    .header("Referer", "http://www.silverscreen.by/MyPage/Register/")
                    .header("Connection", "close")
                    .cookies(Cookie.getInstance().getCookies()).method(Connection.Method.GET).execute();
            if (res == null) {
                return "9D19746455E9FC21A37CE28EA4378EDC";
            }
            Cookie.getInstance().updateCookie(res.cookies());
            Document doc = res.parse();
            Elements rvt = doc.getElementsByAttributeValue("name", "rvt");
            if (rvt != null && rvt.size() > 0) {
                return rvt.first().attr("value");
            }
            return "9D19746455E9FC21A37CE28EA4378EDC";
        } catch (Exception t) {
            t.printStackTrace();
        }
        return "9D19746455E9FC21A37CE28EA4378EDC";
    }

    public boolean login (String mail, String pass) {

        try {
            Connection.Response res = Jsoup.connect("http://silverscreen.by/MyPage/logon/")
                    .data("password", pass)
                    .data("ReturnUrl", "/")
                    .data("userName", mail)
                    .data("rvt", getCodeLogin())
                    .referrer("http://silverscreen.by/").header("Connection", "close")
                    .method(Connection.Method.POST)
                    .execute();
            Cookie.getInstance().updateCookie(res.cookies());
            return Cookie.getInstance().getCookies().containsKey("UserAuthentication") && Cookie.getInstance().getCookies().get("UserAuthentication").length() > 0;
        } catch (IOException e) {
            e.printStackTrace();
            Cookie.getInstance().delete("UserAuthentication");
            return false;
        }
    }

    public boolean logout() {

        String link = "http://www.silverscreen.by/MyPage/logoff/";
        Map<String, String> postParams = new HashMap<>();
        postParams.put("ReturnUrl", "http://www.silverscreen.by/MyPage/Index/");
        try {
            Connection.Response res = Jsoup.connect(link).timeout(3 * 1000)
                    .referrer("http://silverscreen.by/MyPage/Index/")
                    .cookies(Cookie.getInstance().getCookies()).header("Connection", "close")
                    .data(postParams).method(Connection.Method.POST).execute();
            if (res == null) {
                return false;
            }
            Cookie.getInstance().updateCookie(res.cookies());
            return !Cookie.getInstance().getCookies().containsKey("UserAuthentication") ||
                    (Cookie.getInstance().getCookies().containsKey("UserAuthentication")
                            && Cookie.getInstance().getCookies().get("UserAuthentication").length() == 0);
        } catch (Exception t) {
            t.printStackTrace();
            Cookie.getInstance().delete("UserAuthentication");
        }
        return false;
    }

    public static Document simpleGet(String link, String referer) {

        if (!link.contains("silverscreen.by")) {
            link = "http://www.silverscreen.by" + link;
        }
        if (referer.length() > 0 && !referer.contains("http://silverscreen.by")) {
            referer = "http://silverscreen.by" + referer;
        }
        try {
            Connection jsoupCon;
            Connection.Response res = null;
            jsoupCon = Jsoup.connect(link).timeout(3 * 1000).cookies(Cookie.getInstance().getCookies()).ignoreContentType(true).header("Connection", "close");
            if (referer.length() > 0) {
                jsoupCon.referrer(referer);
            }
            res = jsoupCon.method(Connection.Method.GET).execute();
            if (res == null) {
                return null;
            }
            Cookie.getInstance().updateCookie(res.cookies());
            return res.parse();

        } catch (Exception t) {
            t.printStackTrace();
        }
        return null;
    }

    private Set<TicketEntity> fillTickets(Element s) {
        Set<TicketEntity> result = new HashSet<>();
        Consumer<Element> getFilm = colunm -> {
            String descr = colunm.getElementsByClass("no-bottom-margin").text();
            String title = colunm.getElementsByTag("a").first().text();
            int count = 0;
            String[] allMatches = new String[2];
            Matcher m = Pattern.compile("([01][0-9]|2[0-3])[:]([0-5][0-9])").matcher(descr);
            while (m.find()) {
                allMatches[count] = m.group();
                count++;
            }
            int hours = 0;
            int minutes = 0;
            if (count == 1) {
                String time = allMatches[0];
                if (time.length() >0) {
                    try {
                        hours = Integer.parseInt(time.split(":")[0]);
                        minutes = Integer.parseInt(time.split(":")[1]);
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            allMatches = new String[2];
            count = 0;
            m = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d").matcher(descr);
            while (m.find()) {
                allMatches[count] = m.group();
                count++;
            }
            if (count == 1) {
                String date = allMatches[0];
                LocalDate localDate = LocalDate.parse(date, formatter);
                if (localDate.equals(today)) {
                    result.add(new TicketEntity(title + ":" + descr, LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), hours, minutes).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
                }
            }

        };
        s.getElementsByTag("table").stream().map(Optional::ofNullable).filter(Optional::isPresent).map(Optional::get).forEach(table -> {
            table.getElementsByTag("tr").stream().filter(line -> line.getElementsByTag("td") != null).forEach(row -> {
                try {
                    row.getElementsByTag("td").stream().filter(cell -> cell.getElementsByClass("btn").size() == 0 && cell.getAllElements().size() > 1).forEach(getFilm::accept);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
        return result;
    }

    @Override
    public void addTicketByLogin(TicketEntity notification, String login) {
        jooqRepository.persistTicketNotification(login, notification);
    }

    @Override
    public void clearNotifications() {
        jooqRepository.clearNotifications();
    }

    @Override
    public Set<NotificationEntity> getAllNotifications() {
        Result<Record> resultJooq = jooqRepository.getAllNotification();
        return convertNotificationRecords.apply(resultJooq);
    }

    @Override
    public Set<NotificationEntity> getAllNotificationsByTime(LocalDateTime start, LocalDateTime finish) {
        Result<Record> resultJooq = jooqRepository.getAllNotificationByTime(start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), finish.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        return convertNotificationRecords.apply(resultJooq);
    }
}
