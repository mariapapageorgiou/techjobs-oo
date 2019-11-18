package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job job = jobData.findById(id);

        model.addAttribute("Job", job);
        model.addAttribute("name", job.getName());
        model.addAttribute("employers", job.getEmployer());
        model.addAttribute("locations", job.getLocation());
        model.addAttribute("coreCompetencies", job.getCoreCompetency());
        model.addAttribute("positionTypes", job.getPositionType());

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors, RedirectAttributes attributes) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            return "new-job";

        } else {

            Job job = new Job();

            String name = jobForm.getName();
            Employer employers = jobData.getEmployers().findById(jobForm.getEmployerId());
            Location locations = jobData.getLocations().findById(jobForm.getLocationsId());
            CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId());
            PositionType positionType = jobData.getPositionTypes().findById(jobForm.getPositionTypesId());

            job.setName(name);
            job.setEmployer(employers);
            job.setLocation(locations);
            job.setCoreCompetency(coreCompetency);
            job.setPositionType(positionType);

            Job aJob = new Job(name, employers, locations, positionType, coreCompetency);

            jobData.add(aJob);

            attributes.addAttribute("id", aJob.getId());

            return "redirect:";
        }
    }
}
