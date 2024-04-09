package example.roommate.WebControllers;

import example.roommate.Data.Arbeitsplatz.Arbeitsplatz;
import example.roommate.Data.Arbeitsplatz.Ausstatung;
import example.roommate.Data.Reservation;
import example.roommate.Data.Room.Room;
import example.roommate.Service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.unbescape.java.JavaEscape;




import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("roomId")
public class WebController {
    private final RoomService roomService;
    private final ArbeitsplatzService arbeitsplatzService;
    private final PersonService personService;
    private final ReservationService reservationService;
    public WebController(RoomService roomService, ArbeitsplatzService arbeitsplatzService,
                         PersonService personService, ReservationService reservationService) {
        this.roomService = roomService;
        this.arbeitsplatzService = arbeitsplatzService;
        this.personService=personService;
        this.reservationService = reservationService;
    }
    @GetMapping("/")
    public String init(OAuth2AuthenticationToken auth, Model model ){
        if (auth != null) {
            String username = auth.getPrincipal().getAttribute("login");
            Integer id = auth.getPrincipal().getAttribute("id");
            model.addAttribute("username", username);
            personService.getOrCreatePerson(id, username);
        }
        return "initPage";
    }


    @GetMapping("/chooseroom")
    public String chooseRoom(Model model){
        List<Room> allRooms = roomService.getAllRooms();
        model.addAttribute("allRooms",allRooms);
        return "raumübersicht";
    }
    @PostMapping("/chooseroom")
    public String chooseraum(@RequestParam Long selectedRoom){

        return "redirect:/chooseplatz/"+selectedRoom;
    }
    @GetMapping("/chooseplatz/{roomId}")
    public String chooseArbeitsplatz(Model model,
                                     @PathVariable Long roomId,
                                     @RequestParam(required = false) LocalDate datum,
                                     @RequestParam(required = false) LocalTime startzeit,
                                     @RequestParam(required = false) LocalTime endzeit,
                                     @RequestParam(required = false) Long arbeitsplatzId) {

        model.addAttribute("roomId",roomId);
        List<Arbeitsplatz> arbeitsplatzs = arbeitsplatzService.allArbeitsplatzeInRaumMitId(roomId);
        model.addAttribute("arbeitsplatzs", arbeitsplatzs);
        model.addAttribute("arbeitsplatzId", arbeitsplatzId);
        datum = defaultToDate(datum);
        startzeit = defaultToStartTime(startzeit);
        endzeit = defaultToEndTime(endzeit);

        model.addAttribute("datum", datum);
        model.addAttribute("startzeit", startzeit);
        model.addAttribute("endzeit", endzeit);

        return "platzwählen";
    }

    @PostMapping("/chooseplatz")
    public String chooseArbeitsplatzPost(Reservation reservationDetails,
                                         Model model) {
        Reservation reservation = new Reservation(reservationDetails.getTag(),
                                                  reservationDetails.getStartTime(),
                                                  reservationDetails.getEndTime(),
                                                  reservationDetails.getArbeitsplatzId());
        if (reservationService.checkReservationExists(reservation)) {
            return displayArbeitsplatzs(model, reservationDetails.getArbeitsplatzId(), "This place is already booked for the selected time");
        }
        reservationService.saveReservation(reservation);
        return "confirm";
    }


    @GetMapping("/search")
    public String searchMenu(Model model){
        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("currentTime", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        return "suchen";
    }
    @PostMapping("/search")
    public String search(
                         @RequestParam(value = "equipment", required = false) List<String> equipment,
                         WantedPeriod wantedPeriod,
                         Model model)
    {

        wantedPeriod.date = defaultToDate(wantedPeriod.date);
        wantedPeriod.timeFrom = defaultToStartTime(wantedPeriod.timeFrom);
        wantedPeriod.timeTo = defaultToEndTime(wantedPeriod.timeTo);
        List<Arbeitsplatz> availableArbeitsplatz = arbeitsplatzService.findAvailableArbeitsplatz(wantedPeriod.date,
                                                                                                 wantedPeriod.timeFrom,
                                                                                                 wantedPeriod.timeTo);
        model.addAttribute("datum", wantedPeriod.date);
        model.addAttribute("startzeit", wantedPeriod.timeFrom);
        model.addAttribute("endzeit", wantedPeriod.timeTo);
        if(equipment!=null){
            availableArbeitsplatz = arbeitsplatzService.filterArbeitsplatzByEquipment(availableArbeitsplatz, equipment);
        }
        model.addAttribute("availableArbeitsplatz",availableArbeitsplatz);
        return "suchenErgebnisse";
    }
    private LocalDate defaultToDate(LocalDate date){
        return (date == null) ? LocalDate.now() : date;
    }

    private LocalTime defaultToStartTime(LocalTime timeFrom) {
        return (timeFrom == null) ? LocalTime.now().truncatedTo(ChronoUnit.MINUTES) : timeFrom;
    }

    private LocalTime defaultToEndTime(LocalTime timeTo) {
        return (timeTo == null) ? LocalTime.now().truncatedTo(ChronoUnit.MINUTES).plusHours(1) : timeTo;
    }
    private String displayArbeitsplatzs(Model model, Long selectedRoom, String error) {
        model.addAttribute("error", error);
        List<Arbeitsplatz> arbeitsplatzs = arbeitsplatzService.allArbeitsplatzeInRaumMitId(selectedRoom);
        model.addAttribute("arbeitsplatzs", arbeitsplatzs);
        return "platzwählen";
    }




}
