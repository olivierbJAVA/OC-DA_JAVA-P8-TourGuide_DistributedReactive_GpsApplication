package gpsModule.controller;

import gpsModule.service.IGpsService;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
public class GpsController {
    private Logger logger = LoggerFactory.getLogger(GpsController.class);

    @Autowired
    IGpsService gpsService;
/*
    @RequestMapping("/getUserLocation")
    public VisitedLocation getUserLocation(@RequestParam String userId){
        logger.debug("Request getUserLocation");
        VisitedLocation visitedLocation = gpsService.getUserLocation(UUID.fromString(userId));
        logger.debug("Response : UUID=" + visitedLocation.userId +" Lat=" + visitedLocation.location.latitude + " Lon=" + visitedLocation.location.longitude + " Date=" + visitedLocation.timeVisited +"/n");
        return visitedLocation;
    }
*/
    @RequestMapping("/getUserLocation")
    public Mono<VisitedLocation> getUserLocation(@RequestParam String userId){
        logger.debug("Request getUserLocation");
        VisitedLocation visitedLocation = gpsService.getUserLocation(UUID.fromString(userId));
        logger.debug("Response : UUID=" + visitedLocation.userId +" Lat=" + visitedLocation.location.latitude + " Lon=" + visitedLocation.location.longitude + " Date=" + visitedLocation.timeVisited +"/n");
        return Mono.just(visitedLocation);
    }
    @RequestMapping("/getAttractions")
    public List<Attraction> getAttractions() {
        logger.debug("Request getAttractions");
        List<Attraction> allAttractions = gpsService.getAttractions();
        logger.debug("Response : Nb Attractions=" + allAttractions.size());
        return allAttractions;
    }
}
