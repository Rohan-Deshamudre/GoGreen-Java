package server;

import communication.AddFoodRequest;
import communication.AddFoodResponse;
import model.CO2;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import repository.CO2Repository;
import repository.UserRepository;
import util.CarbonUtil;

import java.util.List;


@RestController
public class ActivityController {

    @Autowired
    private CO2Repository co2Repository;

    @Autowired
    private UserRepository userRepository;
    /**
     * SpringBoot automatically wires the CO2Repository instance.
     * into this class using this setter method
     * @param co2Repository - the CO@Repository instance
     */


    private Logger log = LogManager.getLogger(ActivityController.class.getName());

    /**
     * Handle the addition of carbon footprint.
     * @param req requests the co2
     * @return returns te method
     */

    @RequestMapping(value = "/food/add",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<AddFoodResponse> handleFoodAdd(@RequestBody AddFoodRequest req) {
        log.info(req.toString());
        AddFoodResponse res = addFoodData(req);
        return new ResponseEntity<AddFoodResponse>(res, HttpStatus.OK);
    }

    /**
     * Add the data of co2.
     * @param request Co2 request
     * @return returns the method
     */
    public AddFoodResponse addFoodData(AddFoodRequest request) {

        AddFoodResponse response = new AddFoodResponse();
        String user = request.getLoginData().getUsername();

        //Validate whether the input user exists in the user table
        List<User> userList = userRepository.findByUsername(user);
        if (userList == null || userList.isEmpty()) {
            //This condition gets executed if user is not available in the system.
            //Hence the carbon footprint cannot be added.
            response.setResult(false);
            return response;
        }
        //Calculate the pre-defined carbon footprint
        int currentCarbonfootprint = CarbonUtil.getCarbonfootprint(request.getChoiceBoxValue());

        List<CO2> dbUserList = co2Repository.findByCusername(user);
        if (dbUserList != null && !dbUserList.isEmpty()) {
            //This condition gets executed if a user is already having a carbon footprint
            CO2 dbUser = dbUserList.get(0);
            int oldCarbonfootprint = dbUser.getCo2reduc();
            int newCarbonfootprint = oldCarbonfootprint + currentCarbonfootprint;
            dbUser.setCo2reduc(newCarbonfootprint);
            co2Repository.save(dbUser);
            response.setNewCarbonfootprint(newCarbonfootprint);
            response.setOldCarbonfootprint(oldCarbonfootprint);
            response.setResult(true);
        } else {
            //This condition gets executed if user is creating their first carbon footprint
            int oldCarbonfootprint = 0;
            int newCarbonfootprint = oldCarbonfootprint + currentCarbonfootprint;
            co2Repository.save(new CO2(user, 0, 0, 0, newCarbonfootprint));
            response.setNewCarbonfootprint(newCarbonfootprint);
            response.setOldCarbonfootprint(oldCarbonfootprint);
            response.setResult(true);
        }

        return response;
    }

    /**
     * Find all the carbon.
     * @return returns the carbon
     */

    @RequestMapping("/carbon/findAll")
    @ResponseBody
    public String carbonFindAll() {
        String result = "";

        for (CO2 user : co2Repository.findAll()) {
            result += user.toString() + "<br>";
        }

        return result;
    }


}
