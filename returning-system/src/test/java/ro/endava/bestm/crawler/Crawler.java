package ro.endava.bestm.crawler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/17/14
 */
public class Crawler {


    private static final String BASE_PATH = "src/test/resources/";
    private static final String TRAKTV_FOLDER_PATH = "src/test/resources/traktvinfo/";
    private static final String IMDB_FOLDER_PATH = "src/test/resources/imdbinfo/";
    private static final String TVRAGE_FOLDER_PATH = "src/test/resources/tvrageinfo/";
    private static final String TRAKTV_API = "http://api.trakt.tv/show/summary.json/3778fe16b8f32d7403923f1b5dc58eb6/%s/extended";
    private static final String OMDBAPI_API = "http://www.omdbapi.com/?i=%s";
    private static final String TVRAGE_API = "http://services.tvrage.com/feeds/showinfo.php?sid=%s";

    @Test
    public void testFilePath() throws Exception {

//        getInfoFromTrakTv();
//        getInfoFromImdb();
//        getInfoFromTvRage();

    }

    private void getInfoFromImdb() throws IOException {
        for (String id: getImdbIds()) {
            String sumar = getSummary(id, OMDBAPI_API);
            writeToFile(id, sumar, IMDB_FOLDER_PATH, ".json");
        }
    }

    private void getInfoFromTrakTv() throws IOException {
        for (String id: getImdbIds()) {
            String sumar = getSummary(id, TRAKTV_API);
            writeToFile(id, sumar, TRAKTV_FOLDER_PATH, ".json");
        }
    }

    private void getInfoFromTvRage() throws IOException {
        for (String id: getTvRageIds()) {
            try {
                String sumar = getSummary(id, TVRAGE_API);
                writeToFile(id, sumar, TVRAGE_FOLDER_PATH, ".xml");
            } catch (Exception e) {
                System.out.println(id);
            }
        }
    }

    private Map<String, TraktTvSeriesDTO> getSeriesMap() throws java.io.IOException {
        Map<String, TraktTvSeriesDTO> aggregator = new HashMap<String, TraktTvSeriesDTO>();
        for (int i = 1; i < 15; i++) {

            File file = new File(BASE_PATH + "initialjson/seed" + i + ".json");

            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TraktTvSeriesDTO[] series = objectMapper.readValue(line, TraktTvSeriesDTO[].class);
            for (TraktTvSeriesDTO serie : series) {
                aggregator.put(serie.title, serie);
            }
        }
        return aggregator;
    }

    private Set<String> getTvDbIds() throws IOException {
        Set<String> list = new HashSet<String>();
        Map<String, TraktTvSeriesDTO> seriesDTOMap = getSeriesMap();
        for (String key : seriesDTOMap.keySet()) {
            list.add(seriesDTOMap.get(key).getTvdb_id());
        }

        return list;
    }

    private Set<String> getTvRageIds() throws IOException {
        Set<String> list = new HashSet<String>();
        Map<String, TraktTvSeriesDTO> seriesDTOMap = getSeriesMap();
        for (String key : seriesDTOMap.keySet()) {
            list.add(seriesDTOMap.get(key).getTvrage_id());
        }

        return list;
    }

    private Set<String> getImdbIds() throws IOException {
        Set<String> list = new HashSet<String>();
        Map<String, TraktTvSeriesDTO> seriesDTOMap = getSeriesMap();
        for (String key : seriesDTOMap.keySet()) {
            if (seriesDTOMap.get(key).getImdb_id() != null && seriesDTOMap.get(key).getImdb_id().trim().length() > 0) {
                list.add(seriesDTOMap.get(key).getImdb_id());
            }
        }

        return list;
    }

    private void writeToFile(String namePreffix, String content, String base, String extension) throws FileNotFoundException {
        File file = new File(base + namePreffix + extension);
        PrintWriter printer = new PrintWriter(file);
        printer.write(content);
        printer.flush();
        printer.close();

    }

    private String getSummary(String id, String API) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(String.format(API, id), String.class);
    }
}
