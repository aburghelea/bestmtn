package com.endava.task;

import com.endava.repository.BlackListRepository;
import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component
public class PurgeBlacklistTask extends Task {

    @Autowired
    private BlackListRepository blackList;

    public PurgeBlacklistTask() {
        super("purge-black-list");
    }

    @Override
    public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {

        String message = String.format("Removed %d entries from blacklist.\n", blackList.getEntries().size());

        blackList.purge();

        output.print(message);
    }

}
