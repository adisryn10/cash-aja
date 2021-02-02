package mtf.project.controller;

import mtf.project.model.ClickModel;
import mtf.project.service.ClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/click")
public class ClickController {

    @Autowired
    ClickService clickService;

    @PostMapping(value = "/add")
    public ResponseEntity<Object> addClick(@RequestBody HashMap<String, String> payload) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date today = dateFormat.parse(dateFormat.format(new Date()));

        System.out.println(payload);

        String category = payload.get("category");
        try {
            List<ClickModel> todayClicks = clickService.getAllClickByDate(today);
            boolean found = false;
            for (ClickModel item : todayClicks) {
                Date itemDate = dateFormat.parse(dateFormat.format(item.getClickDate()));
                if (itemDate.equals(today) && item.getCategory().equals(category)) {
                    item.setTotalClick(item.getTotalClick() + 1);
                    clickService.updateClick(item);
                    found = true;
                    break;
                }
            }
            if (!found) {
                ClickModel click = new ClickModel();
                click.setClickDate(today);
                click.setCategory(category);
                click.setTotalClick(1);
                clickService.createClick(click);
            }
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception handlerException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerException);
        }
    }
}
