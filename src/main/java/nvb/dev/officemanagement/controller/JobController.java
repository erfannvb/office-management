package nvb.dev.officemanagement.controller;

import lombok.RequiredArgsConstructor;
import nvb.dev.officemanagement.service.UserService;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class JobController {

    private final UserService userService;

    @PostMapping(path = "/job")
    public ResponseEntity<HttpStatus> launchJob() throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        userService.launchJob();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
