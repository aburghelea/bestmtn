package com.endava.datacontainer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.endava.entity.ShowInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/24/14
 */
public class TvRageInfoContainerImpl implements DataContainer {

    private Logger logger = LoggerFactory.getLogger(TvRageInfoContainerImpl.class);

    private Multimap<String, ShowInfo> multimap;

    public TvRageInfoContainerImpl(String name) {
        initMap(name);
    }

    private void initMap(String name) {

        multimap = ArrayListMultimap.create();
        Splitter nameSplitter = Splitter.on(" ").omitEmptyStrings().trimResults();
        try {
            List<ShowInfo> showInfos = readJsonFile("/"+name);
            for (ShowInfo showInfo: showInfos) {
                System.out.println(showInfo.getShowName());
                for (String key: nameSplitter.split(showInfo.getShowName())) {
                    multimap.put(key.toLowerCase(), showInfo);
                }
            }
        } catch (IOException e) {
            logger.error("Could not read json database file", e);
        }

    }

    private List<ShowInfo> readJsonFile(String name) throws IOException {
        List<ShowInfo> data = new ArrayList<ShowInfo>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        InputStream jsonStream = getClass().getResourceAsStream("/tvrage.json");

        if (jsonStream != null) {
            try {
                data = objectMapper.readValue(jsonStream, new TypeReference<List<ShowInfo>>() {});
            } finally {
                jsonStream.close();
            }
        }

        return  data;
    }


    public Collection<ShowInfo> get(String name) {
        return multimap.get(name.toLowerCase());
    }
}
