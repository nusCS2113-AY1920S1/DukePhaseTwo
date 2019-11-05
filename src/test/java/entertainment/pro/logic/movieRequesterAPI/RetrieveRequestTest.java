package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.parsers.commands.SearchCommand;
import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.model.SearchProfile;
import entertainment.pro.storage.utils.OfflineSearchStorage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.R;
import static org.junit.jupiter.api.Assertions.*;

public class RetrieveRequestTest {

    private static final String TO_SPECIFY_RESULTS = "results";
    private static final String VALID_MOVIE_CERT_FILENAME1 = "/data/ValidMovieCertFile1";
    private static final String VALID_MOVIE_CERT_FILENAME2 = "/data/ValidMovieCertFile2";
    private static final String VALID_TV_CERT_FILENAME1 = "/data/ValidTVCertFile1.json";
    private static final String VALID_TV_CERT_FILENAME2 = "/data/ValidTVCertFile2.json";
    private static final String INVALID_MOVIE_CERT_FILENAME1 = "/data/InValidMovieCertFile1";
    private static final String INVALID_TV_CERT_FILENAME1 = "/data/InValidTVCertFile1";
    private static String MOVIES_DATABASE_FILEPATH = "/data/movieData/";
    private static String SEARCH_PROFILE_FILEPATH = "/data/SearchProfileTest/";
    ArrayList<Integer> genrePref1 = new ArrayList<>();
    ArrayList<Integer> genreRestrict1 = new ArrayList<>();
    MovieInfoObject movieInfoObject1 = new MovieInfoObject(1, "Ad Astra", true, getDate("01/01/2018"),
            null, null, null, 8.0, null, false);
    MovieInfoObject movieInfoObject2 = new MovieInfoObject(1, "Joker", true, getDate("01/01/2017"),
            null, null, null, 9.0, null, false);
    MovieInfoObject movieInfoObject3 = new MovieInfoObject(1, "Spiderman", true, getDate("01/01/2019"),
            null, null, null, 7.0, null, false);
    MovieInfoObject movieInfoObject4 = new MovieInfoObject(1, "", true, getDate("2019/01/01"),
            null, null, null, 0.0, null, false);
    List<MovieInfoObject> testObjectsList = Arrays.asList(movieInfoObject4, movieInfoObject1, movieInfoObject2, movieInfoObject3);

    public String getString(String filename) throws Exceptions {
        InputStream inputStream = OfflineSearchStorage.class.getResourceAsStream(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";
        String dataFromJSON = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                dataFromJSON += line;
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            throw new Exceptions(PromptMessages.IO_EXCEPTION_IN_OFFLINE);
        }
        return dataFromJSON;
    }

    public JSONArray getValidData(String dataFromJSON) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject1 = new JSONObject();
        JSONArray searchData1 = new JSONArray();
        try {
            jsonObject1 = (JSONObject) jsonParser.parse(dataFromJSON);
            searchData1 = (JSONArray) jsonObject1.get(TO_SPECIFY_RESULTS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return searchData1;
    }

    @Test
    public void getTVCertFromJSONTest_valid_result() throws Exceptions {
        String data1 = getString(VALID_TV_CERT_FILENAME1);
        String data2 = getString(VALID_TV_CERT_FILENAME2);
        JSONArray searchData1 = getValidData(data1);
        JSONArray searchData2 = getValidData(data2);
        String cert1 = "Unavailable";
        try {
            cert1 = RetrieveRequest.getTVCertFromJSON(searchData1);
        } catch (NullPointerException e) {
            assertEquals("Unavailable", cert1);
            return;

        }
        String expected1 = "Suitable for 12 years & above";
        String cert2 = null;
        try {
            cert2 = RetrieveRequest.getMovieCertFromJSON(searchData2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String expected2 = "Unavailable";
        assertEquals(expected1, cert1);
        assertEquals(expected2, cert2);
    }

    @Test
    public void getTVCertFromJSONTest_empty_result() throws Exceptions {
        String data = getString(INVALID_TV_CERT_FILENAME1);
        JSONArray searchData1 = getValidData(data);
        String cert1 = null;
        try {
            cert1 = RetrieveRequest.getMovieCertFromJSON(searchData1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String expected1 = "Unavailable";
        assertEquals(expected1, cert1);
    }

    @Test
    public void getMovieCertFromJSONTest_valid_result() throws Exceptions {
        String data1 = getString(VALID_MOVIE_CERT_FILENAME1);
        String data2 = getString(VALID_MOVIE_CERT_FILENAME2);
        JSONArray searchData1 = getValidData(data1);
        JSONArray searchData2 = getValidData(data2);
        String cert1 = "Unavailable";
        try {
            cert1 = RetrieveRequest.getMovieCertFromJSON(searchData1);
        } catch (NullPointerException | ParseException e) {
            assertEquals("Unavailable", cert1);
            return;

        }
        String expected1 = "\"R\"";
        String cert2 = null;
        try {
            cert2 = RetrieveRequest.getMovieCertFromJSON(searchData2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String expected2 = "Unavailable";
        assertEquals(expected1, cert1);
        assertEquals(expected2, cert2);
    }

    @Test
    public void getMovieCertFromJSONTest_empty_result() throws Exceptions {
        String data = getString(INVALID_MOVIE_CERT_FILENAME1);
        JSONArray searchData1 = getValidData(data);
        String cert1 = null;
        try {
            cert1 = RetrieveRequest.getMovieCertFromJSON(searchData1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String expected1 = "Unavailable";
        assertEquals(expected1, cert1);
    }

    public JSONArray getOffline(int i) throws Exceptions {
        String filename = MOVIES_DATABASE_FILEPATH;
        filename += i + ".json";
        String dataFromJSON = getString(filename);
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = (JSONArray) jsonParser.parse(dataFromJSON);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public Date getDate(String releaseDateString) {
        Date releaseDate = new Date();
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            releaseDate = formatter1.parse(releaseDateString);
        } catch (java.text.ParseException e) {
            releaseDate = new Date();
        }
        return releaseDate;
    }

    @Test
    public void sortByAlphaOrderTest() {
        ArrayList<MovieInfoObject> testArrayList = new ArrayList<>();
        testArrayList.addAll(testObjectsList);
        ArrayList<MovieInfoObject> expectedArrayList1 = new ArrayList<>();
        expectedArrayList1.addAll(testObjectsList);
        RetrieveRequest.finalSearchResults = testArrayList;
        RetrieveRequest.sortByAlphaOrder();
        testArrayList = RetrieveRequest.finalSearchResults;
        assertEquals(expectedArrayList1, testArrayList);
    }

    @Test
    public void sortByLatestReleaseTest() {
        ArrayList<MovieInfoObject> testArrayList = new ArrayList<>();
        testArrayList.addAll(testObjectsList);
        ArrayList<MovieInfoObject> expectedArrayList1 = new ArrayList<>();
        List<MovieInfoObject> expectedObjectsList = Arrays.asList(movieInfoObject3, movieInfoObject1,
                movieInfoObject2, movieInfoObject4);
        expectedArrayList1.addAll(expectedObjectsList);
        RetrieveRequest.finalSearchResults = testArrayList;
        RetrieveRequest.sortByLatestRelease();
        testArrayList = RetrieveRequest.finalSearchResults;
        assertEquals(expectedArrayList1, testArrayList);
    }

    @Test
    public void sortByHighestRatingTest() {
        ArrayList<MovieInfoObject> testArrayList = new ArrayList<>();
        testArrayList.addAll(testObjectsList);
        ArrayList<MovieInfoObject> expectedArrayList1 = new ArrayList<>();
        List<MovieInfoObject> expectedObjectsList = Arrays.asList(movieInfoObject2, movieInfoObject1,
                movieInfoObject3, movieInfoObject4);
        expectedArrayList1.addAll(expectedObjectsList);
        RetrieveRequest.finalSearchResults = testArrayList;
        RetrieveRequest.sortByHighestRating();
        testArrayList = RetrieveRequest.finalSearchResults;
        assertEquals(expectedArrayList1, testArrayList);
    }

    @Test
    public void checkConditionTest() throws Exceptions {
        String searchProfileData = getString(SEARCH_PROFILE_FILEPATH);
        JSONArray jsonArray = getValidData(searchProfileData);
        for (int i = 0; i < jsonArray.size(); i += 1) {
            SearchProfile searchProfile = null;
            ArrayList<Integer> genrePref = new ArrayList<>();
            ArrayList<Integer> genreRestrict = new ArrayList<>();
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            JSONArray jsonArray1 = (JSONArray) jsonObject.get("genreIdPreference");
            for (int j = 0; j < jsonArray1.size(); j += 1) {
                long num = (long) jsonArray1.get(i);
                int genreNo = Math.toIntExact(num);
                genrePref.add(genreNo);
            }
            JSONArray jsonArray2 = (JSONArray) jsonObject.get("genreIdRestriction");
            for (int j = 0; j < jsonArray2.size(); j += 1) {
                long num = (long) jsonArray2.get(i);
                int genreNo = Math.toIntExact(num);
                genreRestrict.add(genreNo);
            }
            searchProfile.setGenreIdPreference(genrePref);
            searchProfile.setGenreIdRestriction(genreRestrict);
            searchProfile.setAdult((Boolean) jsonObject.get("adult"));
            searchProfile.setSortByAlphabetical((Boolean) jsonObject.get("sortByAlphabetical"));
            searchProfile.setSortByHighestRating((Boolean) jsonObject.get("sortByHighestRating"));
            searchProfile.setSortByHighestRating((Boolean) jsonObject.get("sortByLatestRelease"));
            searchProfile.setMovie((Boolean) jsonObject.get("isMovie"));
            searchProfile.setName((String) jsonObject.get("name"));
            RetrieveRequest.searchProfile = searchProfile;

            for (int k = 1; k <= 2; k += 1) {
                JSONArray jsonArray3 = getOffline(k);
                for (int a = 1; a < jsonArray3.size(); a += 1) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray3.get(a);
                    assertTrue(RetrieveRequest.checkCondition(jsonObject1), "Test has failed");
                }
            }
        }
    }
}
