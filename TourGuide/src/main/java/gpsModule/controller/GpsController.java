package gpsModule.controller;

import gpsModule.service.IGpsService;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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

/*
    @RequestMapping("/getUserLocation")
    public Mono<VisitedLocation> getUserLocation(@RequestParam String userId){
        logger.debug("Request getUserLocation");
        VisitedLocation visitedLocation = gpsService.getUserLocation(UUID.fromString(userId));
        logger.debug("Response : UUID=" + visitedLocation.userId +" Lat=" + visitedLocation.location.latitude + " Lon=" + visitedLocation.location.longitude + " Date=" + visitedLocation.timeVisited +"/n");
        return Mono.just(visitedLocation);
    }
*/

    @Async
    @GetMapping("/getUserLocation")
    public CompletableFuture<VisitedLocation> getUserLocation(@RequestParam String userId){
        logger.debug("Track Location - Thread entrant : " + Thread.currentThread().getName());

        logger.debug("Request getUserLocation");
        VisitedLocation visitedLocation = gpsService.getUserLocation(UUID.fromString(userId));
        logger.debug("Response : UUID=" + visitedLocation.userId +" Lat=" + visitedLocation.location.latitude + " Lon=" + visitedLocation.location.longitude + " Date=" + visitedLocation.timeVisited +"/n");

        //return CompletableFuture.completedFuture(visitedLocation);
        logger.debug("Track Location - Thread sortant : " + Thread.currentThread().getName());

        return CompletableFuture.supplyAsync(( ()-> {
            logger.debug("Track Location - Thread CF : " + Thread.currentThread().getName());
            return gpsService.getUserLocation(UUID.fromString(userId));
        }
        ));
    }

/*
    @Async
    @GetMapping("/getUserLocation")
    public DeferredResult<VisitedLocation> getUserLocation(@RequestParam String userId){
        logger.debug("Track Location - Thread entrant : " + Thread.currentThread().getName());

        logger.debug("Request getUserLocation");
        VisitedLocation visitedLocation = gpsService.getUserLocation(UUID.fromString(userId));
        logger.debug("Response : UUID=" + visitedLocation.userId +" Lat=" + visitedLocation.location.latitude + " Lon=" + visitedLocation.location.longitude + " Date=" + visitedLocation.timeVisited +"/n");

        logger.debug("Track Location - Thread sortant : " + Thread.currentThread().getName());

        DeferredResult<VisitedLocation> deferredResult = new DeferredResult<>();

        CompletableFuture.runAsync(() -> gpsService.getUserLocation(UUID.fromString(userId)))
                .whenComplete((p, throwable) ->
                        {
                            logger.debug("Current Thread Name :{}", Thread.currentThread().getName());
                            deferredResult.setResult(visitedLocation);
                        }


                );

        return deferredResult;
    }
*/

    @RequestMapping("/getAttractions")
    public List<Attraction> getAttractions() {
        logger.debug("Request getAttractions");
        List<Attraction> allAttractions = gpsService.getAttractions();
        logger.debug("Response : Nb Attractions=" + allAttractions.size());
        return allAttractions;
    }
}
