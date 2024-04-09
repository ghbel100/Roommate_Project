package example.roommate.WebControllers;

import example.roommate.Data.Arbeitsplatz.Arbeitsplatz;
import example.roommate.Data.Arbeitsplatz.Ausstatung;
import example.roommate.Data.Room.Room;
import example.roommate.Service.ArbeitsplatzService;
import example.roommate.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@OnlyAdmin
@Controller
public class AdminController {
    private final RoomService roomService;
    private final ArbeitsplatzService arbeitsplatzService;


    public AdminController(RoomService roomService,
                           ArbeitsplatzService arbeitsplatzService) {
        this.roomService = roomService;
        this.arbeitsplatzService = arbeitsplatzService;
    }


    @GetMapping("/addroom")
    public  String addRoomMenu(){
        return "addraum";
    }


    @PostMapping("/addroom")
    public String addRoom(@RequestParam String roomName, Model model) {
        Room room = new Room(roomName);
        Long id = roomService.addRoomAndReturnID(room);
        model.addAttribute("roomId",id);
        return "redirect:/addarbeitsplatz/"+id;
    }


    @GetMapping("/addarbeitsplatz/{roomId}")
    public  String addArbeitplatzMenu(Model model , @PathVariable Long roomId){
        model.addAttribute("roomId", roomId);
        return "addarbeitsplatz";
    }

    @PostMapping("/addarbeitsplatz/{roomId}")
    public String addArbeitsplatz(@RequestParam String arbeitsplatzName, Model model,
                                  @PathVariable Long roomId) {
        Arbeitsplatz arbeitsplatz = new Arbeitsplatz(arbeitsplatzName, roomId);
        Long id = arbeitsplatzService.addPlatzAndReturnID(arbeitsplatz);
        model.addAttribute("arbeitsplatzId",id);
        return "redirect:/addaustattung/"+id;

    }

    @GetMapping("/addaustattung/{arbeitsplatzId}")
    public String addAustattungMenu(Model model, @PathVariable Long arbeitsplatzId) {
        // to show schon existierte Ausstatungs
        Arbeitsplatz arbeitsplatz = arbeitsplatzService
                .getArbeitsplatzById(arbeitsplatzId);
        List<Ausstatung> ausstatungs = arbeitsplatz.getAusstatungs();
        model.addAttribute("ausstatungs", ausstatungs);
        model.addAttribute("arbeitsplatzId", arbeitsplatzId);
        System.out.println(arbeitsplatzId);

        return "addausstattung";
    }

    @PostMapping("/addaustattung/{arbeitsplatzId}")
    public String  addAustattung(@RequestParam String austattungName,
                                 @PathVariable Long arbeitsplatzId) {
        Arbeitsplatz arbeitsplatz = arbeitsplatzService.getArbeitsplatzById(arbeitsplatzId);
        arbeitsplatz.addAusstatung(new Ausstatung(austattungName.trim().toLowerCase()));
        arbeitsplatzService.updateArbeitsplatz(arbeitsplatz);
        return "redirect:/addaustattung/"+arbeitsplatzId;
    }

    @GetMapping("/roomoverview")
    public String roomOverview(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "roomoverview";
    }
        //no Longer needed
//    @PostMapping("/deleteroom/{roomId}")
//    public String deleteRoom(@PathVariable Long roomId) {
//        System.out.println(roomId);
//        arbeitsplatzService.deleteAllArbeitsplatzeByRoomId(roomId);
//        roomService.deleteRoombyId(roomId);
//        return "redirect:/roomoverview";
//    }

    @GetMapping("/modifyroom/{roomId}")
    public String modifyRoom(@PathVariable Long roomId, Model  model) {
        Room room = roomService.getRoomById(roomId);
        model.addAttribute("room", room);
        model.addAttribute("roomId",roomId);
        List<Arbeitsplatz> arbeitsplatze = arbeitsplatzService.allArbeitsplatzeInRaumMitId(roomId);
        model.addAttribute("arbeitsplatze",arbeitsplatze);
        return "arbeitsplatzeübersichtAdmin";
    }

    @PostMapping("/room/{roomId}/platz/{arbeitsplatzId}")
    public String deleteArbeitsplatz(@PathVariable Long arbeitsplatzId,
                                     @PathVariable Long roomId) {
        arbeitsplatzService.deleteArbeitsplatzById(arbeitsplatzId);
        return "redirect:/modifyroom/" + roomId;
    }

    @GetMapping("/modifyarbeitsplatz/{arbeitsplatzId}")
    public String modifyArbeitsplatz(@PathVariable Long arbeitsplatzId, Model model){
        Arbeitsplatz arbeitsplatz = arbeitsplatzService.getArbeitsplatzById(arbeitsplatzId);
        model.addAttribute("arbeitsplatz", arbeitsplatz);
        List<Ausstatung> ausstatungList=arbeitsplatz.getAusstatungs();
        model.addAttribute("ausstatungList",ausstatungList);
        return "ausstatungübersichtAdmin";
    }

    @PostMapping("/platz/{arbeitsplatzId}/ausstatung/{ausstatungName}")
    public String deleteAusstattung(@PathVariable String ausstatungName,
                                    @PathVariable Long arbeitsplatzId){
        Arbeitsplatz arbeitsplatz = arbeitsplatzService.deleteAusstatung(ausstatungName, arbeitsplatzId);
        arbeitsplatzService.updateArbeitsplatz(arbeitsplatz);
        return "redirect:/modifyarbeitsplatz/" + arbeitsplatzId;
    }




}



