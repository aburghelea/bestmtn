package com.endava.task;

import com.endava.service.SpamService;
import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component
public class SpamServiceTask extends Task {

    @Autowired
    private SpamService spamService;

    public SpamServiceTask() {
        super("spam-service-management");
    }

    @Override
    public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {
        boolean activate = parameters.get("activate").contains("true");
        boolean deactivate = parameters.get("deactivate").contains("true");

        if (activate) {
            spamService.setActive(true);
            String message = String.format("Spam service activated.\n");
            output.print(message);
        } else if (deactivate) {
            spamService.setActive(false);
            String message = String.format("Spam service deactivated.\n");
            output.print(message);
        } else {
            String message;
            if (spamService.isActive()) {
                message = String.format("Spam service is active.\n");
            } else {
                message = String.format("Spam service is inactive.\n");
            }
            output.print(message);
        }
    }

}